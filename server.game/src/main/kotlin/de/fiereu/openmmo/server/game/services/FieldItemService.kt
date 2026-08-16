package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.items.ItemDef
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.net.game.packets.DialogOptionPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.pokemon.EvolutionTable
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleItems
import de.fiereu.openmmo.server.game.battle.ExpCurves
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.StoredCharacter
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/**
 * Using an item from the bag outside a battle.
 *
 * The client asks with what was read as a dialog option: the id is the item and the entity, when
 * there is one, is the monster it was used on. Nothing handled it, so every potion taken in the
 * overworld did nothing and was not consumed, and so did every press of a bike.
 */
@Singleton
class FieldItemService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val items: ItemRegistry,
    private val species: SpeciesRegistry,
    private val battles: BattleService,
    private val moveLearner: MoveLearner,
) {

  suspend fun onUseItem(event: PacketEvent<DialogOptionPacket>) {
    val ctx = event.session
    val charId = ctx.attributes[PLAYER_STATE]?.characterId ?: return
    val packet = event.packet
    val item = items.get(packet.optionId)
    val stored = characterStore.getCharacter(charId) ?: return

    // A battle has its own item handling, including throwing balls.
    if (battles.inBattle(charId)) return

    if (item == null) {
      log.info { "char=$charId used unknown item id ${packet.optionId}" }
      return
    }
    val held = stored.items[packet.optionId] ?: 0
    if (held <= 0) {
      log.info { "char=$charId used ${item.name} without holding it" }
      return
    }

    // A stone is neither a heal nor a cure, so it is answered before the healing items are.
    val evolvesInto =
        EvolutionTable.byStone(target(stored, packet.entityId)?.dexId ?: -1, item.name)
    if (evolvesInto != null) {
      evolveWithStone(ctx, charId, packet.entityId, packet.optionId, item, evolvesInto)
      return
    }

    if (RARE_CANDY_NAMES.contains(item.name)) {
      rareCandy(ctx, charId, packet.entityId, packet.optionId, item)
      return
    }

    val effect = BattleItems.effectOf(item)
    if (effect == null) {
      // The bike lands here: it is a real item with a real effect in the games and no model on this
      // server yet, so say so rather than leaving the player pressing a key that does nothing.
      log.info { "char=$charId used ${item.name} (id ${packet.optionId}), which is not modelled" }
      ctx.send(notice("${item.name} does not do anything yet."))
      return
    }

    val target = target(stored, packet.entityId)
    if (target == null) {
      ctx.send(notice("You have nothing to use that on."))
      return
    }
    val definition = species.get(target.dexId)
    if (definition == null) {
      ctx.send(notice("That monster is not covered by the battle data yet."))
      return
    }
    val maxHp = StatCalculator.computeAll(definition, target).hp
    val healed = BattleItems.healAmount(effect, target.hp.toInt(), maxHp)
    val cured = effect.cures.contains(target.status) && target.status.isSet
    if (healed <= 0 && !cured) {
      ctx.send(notice("It would have no effect."))
      return
    }

    val updated =
        target.copy(
            hp = (target.hp + healed).toShort(),
            status = if (cured) StatusCondition.NONE else target.status,
        )
    characterStore.updatePokemon(charId, updated)
    characterStore.addItem(charId, packet.optionId, -1)
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId used ${item.name} on ${target.id}: healed=$healed cured=$cured" }

    val party = characterStore.getCharacter(charId)?.pokemon?.toList() ?: return
    ctx.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ))
  }

  private fun target(stored: StoredCharacter, entityId: Long) =
      stored.pokemon.firstOrNull { it.id == entityId } ?: stored.pokemon.firstOrNull()

  /**
   * Turns a monster into what the stone makes of it.
   *
   * Twenty-one species evolve this way and none of them could before, since the only trigger this
   * server knew was levelling. The stone is spent whether or not the player is watching, as it is
   * in the games.
   */
  private suspend fun evolveWithStone(
      ctx: SessionContext,
      charId: Long,
      entityId: Long,
      itemId: Int,
      item: ItemDef,
      into: Int,
  ) {
    val stored = characterStore.getCharacter(charId) ?: return
    val monster = target(stored, entityId) ?: return
    val definition = species.get(into)
    if (definition == null) {
      ctx.send(notice("That is not covered by the battle data yet."))
      return
    }
    val was = species.get(monster.dexId)?.name ?: "It"
    characterStore.updatePokemon(charId, monster.copy(dexId = into))
    characterStore.addItem(charId, itemId, -1)
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId used ${item.name} to evolve $was into ${definition.name}" }
    ctx.send(notice("$was evolved into ${definition.name}!"))
    val party = characterStore.getCharacter(charId)?.pokemon?.toList() ?: return
    ctx.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ))
  }

  /**
   * A level in a wrapper.
   *
   * The candy was one of the items that landed on "does not do anything yet": it is worth a level
   * and this server had no way to grant one outside a battle. It is granted the way a won fight
   * grants one, so the moves of the new level, the recomputed stats and an evolution all follow
   * from it rather than only the number going up.
   */
  private suspend fun rareCandy(
      ctx: SessionContext,
      charId: Long,
      entityId: Long,
      itemId: Int,
      item: ItemDef,
  ) {
    val stored = characterStore.getCharacter(charId) ?: return
    val monster = target(stored, entityId) ?: return
    val definition = species.get(monster.dexId)
    if (definition == null) {
      ctx.send(notice("That monster is not covered by the battle data yet."))
      return
    }
    val was = monster.level.toInt()
    if (was >= ExpCurves.MAX_LEVEL) {
      ctx.send(notice("${definition.name} cannot grow any further."))
      return
    }
    val now = was + 1
    val known = monster.moves.toMutableList()
    val outcome = moveLearner.learn(known, monster.dexId, was, now)
    // Growing changes what the monster is made of, so the stats are recomputed from the new level
    // and the hp it was missing stays missing.
    val grown =
        monster.copy(
            level = now.toByte(),
            xp = ExpCurves.totalXpFor(definition.growthRate, now),
            moves = known,
        )
    val before = StatCalculator.computeAll(definition, monster)
    val after = StatCalculator.computeAll(definition, grown)
    val healed = (grown.hp + maxOf(0, after.hp - before.hp)).coerceAtMost(after.hp)
    characterStore.updatePokemon(charId, grown.copy(hp = healed.toShort()))
    characterStore.addItem(charId, itemId, -1)
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId used ${item.name} on ${monster.id}: level $was -> $now" }
    ctx.send(notice("${definition.name} grew to level $now!"))
    for (learned in outcome.learned) ctx.send(notice("It learned ${learned.name}!"))
    // A move it has no room for needs the prompt a battle uses, which nothing outside a battle
    // drives yet, so say so rather than dropping it in silence.
    for (missed in outcome.offered) {
      ctx.send(notice("It is ready to learn ${missed.name}, but its moves are full."))
    }
    val evolution = EvolutionTable.at(grown.dexId, now)
    if (evolution != null) {
      evolveTo(ctx, charId, monster.id, evolution.into, definition.name)
      return
    }
    sendParty(ctx, charId)
  }

  /** Sends the party back so the client redraws whatever just changed about it. */
  private fun sendParty(ctx: SessionContext, charId: Long) {
    val party = characterStore.getCharacter(charId)?.pokemon?.toList() ?: return
    ctx.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ))
  }

  private fun evolveTo(
      ctx: SessionContext,
      charId: Long,
      entityId: Long,
      into: Int,
      was: String,
  ) {
    val definition = species.get(into)
    if (definition == null) {
      sendParty(ctx, charId)
      return
    }
    val monster = characterStore.getCharacter(charId)?.pokemon?.firstOrNull { it.id == entityId }
    if (monster == null) {
      sendParty(ctx, charId)
      return
    }
    characterStore.updatePokemon(charId, monster.copy(dexId = into))
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId evolved $was into ${definition.name}" }
    ctx.send(notice("$was evolved into ${definition.name}!"))
    sendParty(ctx, charId)
  }
}

/** What the registry calls the candy, allowing for the way the name is punctuated. */
private val RARE_CANDY_NAMES = setOf("Rare Candy", "RareCandy", "RARE_CANDY")
