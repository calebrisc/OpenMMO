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
    private val teaching: MoveTeachingService,
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
    // Temporary, while a player reports picking fifty candies and being charged one: the client
    // is sending the count somewhere and these are the only two fields left to carry it.
    log.info {
      "ITEM USE: char=$charId ${item.name} id=${packet.optionId} entity=${packet.entityId} " +
          "flag=${packet.flag} trailing=${packet.trailing}"
    }
    val held = stored.items[packet.optionId] ?: 0
    // How many the player picked on the item screen, which the server had been ignoring. Never
    // more than the bag actually holds, whatever the client asks for.
    val count = packet.flag.coerceIn(1, held.coerceAtLeast(1))
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

    // A machine teaches its move and is not spent doing it. Every TM in the game was a keepsake
    // until now, including the ones gym leaders hand over, because nothing read them at all. They
    // are kept rather than consumed: the later games stopped spending them and a private server
    // with one copy of each is a worse game for making them single use.
    val machineMove = MachineMoves.moveFor(item.name)
    if (machineMove != null) {
      teaching.offer(ctx, charId, machineMove, item.name)
      return
    }

    if (RARE_CANDY_NAMES.contains(item.name)) {
      rareCandy(ctx, charId, packet.entityId, packet.optionId, item, count)
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
    sendBag(ctx, charId)
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

  /**
   * The bag as it now stands.
   *
   * Spending an item in the field took it out of the bag and never told the client, while the
   * script path had sent this all along. So the client went on showing an item the server had
   * already spent, and refused every later use of it, because the server knew the bag was empty
   * while the player could plainly see it was not: a Rare Candy worked once and then never again,
   * and a Moon Stone evolved a monster without ever leaving the bag.
   */
  private fun sendBag(ctx: SessionContext, charId: Long) {
    val bag: Map<Int, Int> = characterStore.getCharacter(charId)?.items ?: return
    ctx.send(storyItemStacksPacket(bag))
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
    val evolved = monster.copy(dexId = into)
    val room = StatCalculator.computeAll(definition, evolved).hp
    characterStore.updatePokemon(
        charId, evolved.copy(hp = evolved.hp.toInt().coerceAtMost(room).toShort()))
    characterStore.addItem(charId, itemId, -1)
    characterStore.flushCharacterAsync(charId)
    sendBag(ctx, charId)
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
  /**
   * Levels in a wrapper, as many as were asked for.
   *
   * The candy was one of the items that landed on "does not do anything yet": it is worth a level
   * and this server had no way to grant one outside a battle. Each is granted the way a won fight
   * grants one, so the moves of the new level, the recomputed stats and an evolution all follow
   * from it rather than only the number going up.
   *
   * [count] is what the player picked on the item screen. Only one was ever spent however many were
   * chosen, so picking fifty charged one and left the other forty nine to be picked again.
   */
  private suspend fun rareCandy(
      ctx: SessionContext,
      charId: Long,
      entityId: Long,
      itemId: Int,
      item: ItemDef,
      count: Int,
  ) {
    val learned = mutableListOf<String>()
    val missed = mutableListOf<String>()
    var from = 0
    var reached = 0
    var spent = 0
    var name = ""

    repeat(count) {
      val stored = characterStore.getCharacter(charId) ?: return@repeat
      val monster = stored.pokemon.firstOrNull { it.id == entityId } ?: return@repeat
      val definition = species.get(monster.dexId) ?: return@repeat
      name = definition.name
      val was = monster.level.toInt()
      if (was >= ExpCurves.MAX_LEVEL) return@repeat
      if (spent == 0) from = was
      val now = was + 1
      val known = monster.moves.toMutableList()
      val outcome = moveLearner.learn(known, monster.dexId, was, now)
      learned += outcome.learned.map { it.name }
      // A move it has no room for needs the prompt a battle uses, which nothing outside a battle
      // drives yet, so say so rather than dropping it in silence.
      missed += outcome.offered.map { it.name }
      // Growing changes what the monster is made of, so the stats are recomputed from the new
      // level and the hp it was missing stays missing.
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
      spent++
      reached = now
      // Evolving mid climb matters: the levels after it belong to what it became.
      EvolutionTable.at(grown.dexId, now)?.let {
        evolveTo(charId, entityId, it.into, definition.name)
      }
    }

    if (spent == 0) {
      ctx.send(notice("${name.ifEmpty { item.name }} cannot grow any further."))
      return
    }
    characterStore.flushCharacterAsync(charId)
    sendBag(ctx, charId)
    log.info { "char=$charId used $spent x ${item.name} on $entityId: level $from -> $reached" }
    val became = characterStore.getCharacter(charId)?.pokemon?.firstOrNull { it.id == entityId }
    val ended = became?.dexId?.let { species.get(it)?.name } ?: name
    ctx.send(notice("$ended grew to level $reached!"))
    if (ended != name) ctx.send(notice("It evolved into $ended!"))
    for (move in learned.distinct()) ctx.send(notice("It learned $move!"))
    for (move in missed.distinct()) {
      ctx.send(notice("It is ready to learn $move, but its moves are full."))
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

  private fun evolveTo(charId: Long, entityId: Long, into: Int, was: String) {
    val definition = species.get(into) ?: return
    val monster =
        characterStore.getCharacter(charId)?.pokemon?.firstOrNull { it.id == entityId } ?: return
    // What it became is built differently, so the hp it is carrying has to fit the new maximum.
    val evolved = monster.copy(dexId = into)
    val room = StatCalculator.computeAll(definition, evolved).hp
    characterStore.updatePokemon(
        charId, evolved.copy(hp = evolved.hp.toInt().coerceAtMost(room).toShort()))
    log.info { "char=$charId evolved $was into ${definition.name}" }
  }
}

/** What the registry calls the candy, allowing for the way the name is punctuated. */
private val RARE_CANDY_NAMES = setOf("Rare Candy", "RareCandy", "RARE_CANDY")
