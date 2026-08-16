package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.MAX_PARTY_SIZE
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.items.ItemDef
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.SocialListEntryAddPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleAddPokemon
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSideAddPokemonPacket
import de.fiereu.openmmo.net.game.packets.battle.ItemStack
import de.fiereu.openmmo.net.game.packets.battle.MoveSlots
import de.fiereu.openmmo.net.game.packets.battle.itemStacksPacket
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleRng
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.battle.acquiredMonsterDelta
import de.fiereu.openmmo.server.game.script.InGameTrade
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject
import javax.inject.Singleton

/** Party, healing, and bag operations used by ROM-derived overworld scripts. */
@Singleton
class StoryPlayerService
@Inject
constructor(
    private val characters: CharacterStore,
    private val pokemonFactory: WildMonFactory,
    private val species: SpeciesRegistry,
    private val moves: MoveRegistry,
    private val items: ItemRegistry,
) {

  /** Gives a story Pokemon and syncs it. */
  suspend fun givePokemon(
      session: SessionContext,
      state: PlayerState,
      dexId: Int,
      level: Int,
      moveIds: List<Int>,
  ): Pokemon? {
    val characterId = state.characterId ?: return null
    val stored = characters.getCharacter(characterId) ?: return null
    if (stored.pokemon.size >= MAX_PARTY_SIZE) return null
    val rolled = pokemonFactory.create(dexId, level, BattleRng()) ?: return null
    val pokemon =
        rolled.copy(
            ownerId = characterId,
            container = PokemonContainer.PARTY,
            containerSlot = stored.pokemon.size.toShort(),
            ot = stored.info.name,
            moves = paddedMoves(moveIds),
        )
    // Only tell the client about it once the database has it.
    if (!characters.addPokemon(characterId, pokemon)) return null
    // Send the granted Pokemon's full record.
    session.send(SocialListEntryAddPacket(pokemon))
    species.get(dexId)?.let { session.send(acquiredMonsterDelta(pokemon, it)) }
    session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = characters.getCharacter(characterId)?.pokemon ?: listOf(pokemon),
        ))
    return pokemon
  }

  fun healParty(session: SessionContext, state: PlayerState) {
    val characterId = state.characterId ?: return
    val stored = characters.getCharacter(characterId) ?: return
    val healed =
        stored.pokemon.map { pokemon ->
          val definition = species.get(pokemon.dexId) ?: return@map pokemon
          pokemon.copy(
              hp = StatCalculator.computeAll(definition, pokemon).hp.toShort(),
              moves =
                  pokemon.moves.map { move ->
                    val maxPp = moves.get(move.id.toInt())?.pp ?: move.pp.toInt()
                    PokemonMove(move.id, maxPp.toByte())
                  },
              status = StatusCondition.NONE,
          )
        }
    healed.forEach { characters.updatePokemon(characterId, it) }
    healed.forEach { session.send(healedDelta(it)) }
  }

  /**
   * How a live server says a monster has been healed: one entity delta per monster, carrying its
   * moves with their pp back up, its hp, and a cleared faint flag.
   *
   * Read off the archive's whiteout capture, where the two deltas that follow the transition are
   * `1C 00 00 00` masks holding `21 00 23` — move 33 with 35 pp, which is Tackle full — then hp and
   * a zero faint byte. This used to answer a heal with the whole party container instead, which is
   * the bulk load a login does; a container resend is inert mid-session, which is the same thing
   * that made a box drag never redraw.
   */
  private fun healedDelta(pokemon: Pokemon): BattleEntityDeltaPacket {
    val slots =
        (0 until MAX_MOVES).map {
          val move = pokemon.moves.getOrNull(it)
          (move?.id ?: 0) to (move?.pp ?: 0)
        }
    return BattleEntityDeltaPacket(
        entityId = pokemon.id,
        moves = MoveSlots(slots, ppUps = 0),
        currentHp = pokemon.hp,
        faintFlag = 0,
    )
  }

  /**
   * The townsfolk trade: takes the monster [trade] asked for out of the party and leaves its own
   * monster standing in the same slot.
   *
   * The decomp replaces the party entry rather than removing and adding, and so does this, for a
   * blunter reason: a remove followed by an add is two durable writes with a moment in between
   * where the player owns neither, and a failure there used to lose the monster for good. One write
   * cannot half-happen.
   *
   * Level, and only level, comes from what the player handed over —
   * `CreateInGameTradePokemonInternal` reads `MON_DATA_LEVEL` off their monster before it builds
   * the trade's. Everything the trade fixes (IVs, nickname, original trainer, personality) is taken
   * from the table, so two players who do the same trade get the same monster.
   *
   * Returns null when the party holds nothing of the requested species, which is the script's cue
   * to say so rather than an error.
   */
  internal suspend fun tradePokemon(
      session: SessionContext,
      state: PlayerState,
      trade: InGameTrade,
  ): Pokemon? {
    val characterId = state.characterId ?: return null
    val stored = characters.getCharacter(characterId) ?: return null
    // An egg has no species to the trader, exactly as GetTradeSpecies reports it.
    val given =
        stored.pokemon.firstOrNull {
          it.container == PokemonContainer.PARTY && it.dexId == trade.requested && !it.isEgg
        } ?: return null
    val definition = species.get(trade.offered) ?: return null
    val rolled =
        pokemonFactory.create(trade.offered, given.level.toInt(), BattleRng()) ?: return null
    val received =
        rolled.copy(
            id = given.id,
            ownerId = characterId,
            container = given.container,
            containerSlot = given.containerSlot,
            seed = trade.personality,
            ot = trade.otName,
            nickname = trade.nickname,
            level = given.level,
            iVs = trade.ivs(),
            eVs = EVs(),
            isShiny = false,
            status = StatusCondition.NONE,
        )
    val healthy = received.copy(hp = StatCalculator.computeAll(definition, received).hp.toShort())
    characters.updatePokemon(characterId, healthy)
    // Durable before the thank-you dialog: anything after an awaited box is lost on a drop.
    characters.flushCharacterAsync(characterId)
    session.send(SocialListEntryAddPacket(healthy))
    session.send(acquiredMonsterDelta(healthy, definition))
    session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = characters.getCharacter(characterId)?.pokemon ?: listOf(healthy),
        ))
    return healthy
  }

  suspend fun giveItem(
      session: SessionContext,
      state: PlayerState,
      item: ItemDef,
      quantity: Int
  ): Boolean {
    val characterId = state.characterId ?: return false
    if (!characters.addItem(characterId, items.idOf(item), quantity)) return false
    val bag: Map<Int, Int> = characters.getCharacter(characterId)?.items ?: return false
    session.send(storyItemStacksPacket(bag))
    return true
  }

  private fun paddedMoves(moveIds: List<Int>): List<PokemonMove> =
      moveIds.take(MAX_MOVES).map { id ->
        PokemonMove(id.toShort(), (moves.get(id)?.pp ?: 0).toByte())
      } + List((MAX_MOVES - moveIds.size).coerceAtLeast(0)) { PokemonMove(0, 0) }

  private companion object {
    const val MAX_MOVES = 4
  }
}

/** Builds a stable full bag snapshot. */
fun storyItemStacksPacket(items: Map<Int, Int>) =
    itemStacksPacket(
        items.entries
            .sortedBy { it.key }
            .map { (itemId, quantity) ->
              ItemStack(
                  objectId = (itemId.toLong() shl 16) or ITEM_ENTITY_TAG,
                  itemId = itemId.toShort(),
                  quantity = quantity.toShort(),
              )
            })

/**
 * A bag stack, not a monster. The open shop window only refreshes its count when the update arrives
 * as this single stack rather than as a whole new bag.
 */
fun itemStackUpdatePacket(itemId: Int, quantity: Int) =
    BattleSideAddPokemonPacket(
        side = 1,
        pokemon =
            BattleAddPokemon(
                entityId = (itemId.toLong() shl 16) or ITEM_ENTITY_TAG,
                frontSpriteId = itemId.toShort(),
                backSpriteId = quantity.toShort(),
                side = 1,
                slot = 0,
                partyIndex = -1,
                statusEffect = null,
            ),
    )

private const val ITEM_ENTITY_TAG = 0x5000L
