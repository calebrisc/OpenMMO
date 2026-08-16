package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.MapLoadedAckPacket
import de.fiereu.openmmo.net.game.packets.SocialListEntryAddPacket
import de.fiereu.openmmo.net.game.packets.SpectateRequestPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleListEventDetail
import de.fiereu.openmmo.net.game.packets.battle.BattleListEventPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnPromptPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnReplyPacket
import de.fiereu.openmmo.pokemon.EvolutionTable
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleInstance
import de.fiereu.openmmo.server.game.battle.BattleItems
import de.fiereu.openmmo.server.game.battle.BattleMonState
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.battle.BattleRewards
import de.fiereu.openmmo.server.game.battle.BattleRng
import de.fiereu.openmmo.server.game.battle.BattleRules
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.battle.acquiredMonsterDelta
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.interest.BattleInterestKey
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.trainer.TrainerDef
import de.fiereu.openmmo.trainer.TrainerRegistry
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/** The side byte the player's own monsters ride on in a switch in. */
private const val PLAYER_SIDE_WIRE = 0

/**
 * A prompt waiting for its answer, kept after the battle ends. A trainer battle can raise one
 * monster past a level more than once, so these are held per monster rather than per player.
 */
private data class PendingMoveLearn(
    val charId: Long,
    val entityId: Long,
    val offered: List<Short>,
)

/**
 * Orchestrates battles: builds the battle state from the party and the opposing side, routes client
 * actions through the [TurnEngine], and persists the outcome. Packets go out through the
 * [BattlePacketEmitter] over the battle's interest key.
 */
@Singleton
class BattleService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val battles: BattleRegistry,
    private val engine: TurnEngine,
    private val wildMons: WildMonFactory,
    private val emitter: BattlePacketEmitter,
    private val rewards: BattleRewards,
    private val moveLearner: MoveLearner,
    private val interestManager: InterestManager,
    private val speciesRegistry: SpeciesRegistry,
    private val moveRegistry: MoveRegistry,
    private val trainers: TrainerRegistry,
    private val items: ItemRegistry,
    private val pokedex: PokedexService,
    private val duels: DuelService,
    private val storyPlayer: StoryPlayerService,
    private val scriptWarp: ScriptWarpService,
) {

  private val pokeBallItemId: Short by lazy { items.idOf(Items.POKE_BALL).toShort() }

  private val pendingLearns = ConcurrentHashMap<Long, PendingMoveLearn>()

  /**
   * Told which character just finished a turn. A raid listens on this to show every raider what the
   * shared boss has left, since the damage they do to it lands in somebody else's battle.
   */
  private val turnListeners = mutableListOf<(Long) -> Unit>()

  fun onTurnResolved(listener: (charId: Long) -> Unit) {
    synchronized(turnListeners) { turnListeners += listener }
  }

  private fun fireTurnResolved(charId: Long) {
    val listeners = synchronized(turnListeners) { turnListeners.toList() }
    listeners.forEach {
      runCatching { it(charId) }.onFailure { e -> log.warn(e) { "turn listener" } }
    }
  }

  fun onBattlePacket(event: PacketEvent<*>) {
    log.info { "Battle packet ${event.packet::class.simpleName} received: ${event.packet}" }
  }

  suspend fun onBattleAction(event: PacketEvent<BattleActionSelectPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val battle = battles.byChar(charId) ?: return
    if (battle.pendingResult != null) return
    val action = event.packet
    log.info { "Battle action char=$charId: $action" }
    // A duel resolves only once both players have chosen, so it owns the whole turn.
    if (duels.onAction(battle, charId, action)) return
    // While the active mon is fainted the player owes a replacement and may only switch.
    if (battle.activeMon().fainted && action.action != BattleAction.SWITCH) return
    when (action.action) {
      BattleAction.MOVE -> resolveTurn(battle, action.moveOrItemId)
      BattleAction.ITEM -> useItem(battle, action.moveOrItemId)
      BattleAction.SWITCH -> switchMon(battle, action.moveOrItemId)
      BattleAction.RUN -> flee(battle)
    }
  }

  /** Applies the moveset the player picked after a level up. */
  fun onMoveLearnReply(event: PacketEvent<MoveLearnReplyPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val reply = event.packet
    val pending = pendingLearns[reply.entityId] ?: return
    if (pending.charId != charId) return
    pendingLearns.remove(reply.entityId)
    val stored =
        characterStore.getCharacter(charId)?.pokemon?.firstOrNull { it.id == reply.entityId }
            ?: return
    val moves = stored.moves.toMutableList()
    if (!moveLearner.apply(moves, reply.moveIds, pending.offered)) {
      log.warn { "char=$charId picked an invalid moveset for ${reply.entityId}: ${reply.moveIds}" }
      return
    }
    if (moves == stored.moves) return
    characterStore.updatePokemon(charId, stored.copy(moves = moves))
    characterStore.flushCharacterAsync(charId)
    // A battle still running holds its own copy, and the next reward writes that copy back over
    // the store. Move the live one across so the pick survives the rest of the battle.
    battles
        .byChar(charId)
        ?.party
        ?.firstOrNull { it.entityId == reply.entityId }
        ?.let { live ->
          live.moves.clear()
          live.moves.addAll(moves.map { PokemonMove(it.id, it.pp) })
          live.source = live.source.copy(moves = moves)
        }
    event.session.send(emitter.moveSlotsDelta(reply.entityId, moves.map { it.id to it.pp }, 0))
  }

  /** Throws a ball at the monster. False when the character is not in a battle. */
  suspend fun catchActiveWild(charId: Long): Boolean {
    val battle = battles.byChar(charId) ?: return false
    catchWild(battle)
    return true
  }

  /** Ends a running battle when the player disconnects, keeping the last hp and pp state. */
  fun onDisconnect(session: SessionContext) {
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return
    pendingLearns.values.removeIf { it.charId == charId }
    val battle = battles.byChar(charId) ?: return
    persistParty(battle)
    finishBattle(battle, BattleResult.DISCONNECTED)
  }

  /** Resumes scripts after returning to the overworld. */
  fun onClientReady(event: PacketEvent<MapLoadedAckPacket>) {
    if (event.packet.data.isNotEmpty()) return
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val battle = battles.byChar(charId) ?: return
    val result = battle.pendingResult ?: return
    finishBattle(battle, result)
  }

  /** True while the character has a battle running, so callers can skip starting another. */
  fun inBattle(charId: Long): Boolean = battles.byChar(charId) != null

  /**
   * Puts a second monster on the player's own side of a running battle.
   *
   * The battle state packet describes exactly one active monster per side, which is what stands
   * between this server and a shared battle. This packet adds one to a side independently, so if
   * the client draws it there is a way to show two people fighting together, and if it does not the
   * limit is real. Nothing else can answer that.
   */
  fun probeAddToOwnSide(charId: Long, slot: Int): String {
    val battle = battles.byChar(charId) ?: return "Start a battle first."
    val extra = battle.party.getOrNull(1) ?: return "You need a second monster in your party."
    emitter.addToSide(battle, PLAYER_SIDE_WIRE, slot, extra)
    return "Added ${extra.species.name} to your side in slot $slot. Does it appear?"
  }

  fun onSpectateRequest(event: PacketEvent<SpectateRequestPacket>) {
    watch(event.session, event.packet.targetEntityId)
  }

  /**
   * Joins [session] to the battle [targetCharId] is fighting, as a viewer. Returns what to tell the
   * watcher.
   */
  fun watch(session: SessionContext, targetCharId: Long): String {
    val watcherId = session.attributes[PLAYER_STATE]?.characterId
    if (watcherId != null && battles.byChar(watcherId) != null) {
      return "You cannot watch a battle while you are in one."
    }
    val battle = battles.byChar(targetCharId) ?: return "They are not in a battle."
    if (watcherId != null && battle.participantFor(watcherId) != null) {
      return "That is your own battle."
    }
    interestManager.join(session, battle.key)
    emitter.sendSnapshotTo(session, battle)
    log.info { "char=$watcherId is watching battle ${battle.battleId}" }
    return "Watching. It ends when the battle does."
  }

  /** Drops a watcher out again. */
  fun stopWatching(session: SessionContext): String {
    val charId = session.attributes[PLAYER_STATE]?.characterId
    // Never unsubscribe somebody from a battle they are fighting: they would stop receiving
    // their own moves and the client would hang with the fight still running.
    val own = charId?.let { battles.byChar(it) }?.key
    val keys =
        interestManager.keysOf(session).filterIsInstance<BattleInterestKey>().filter { it != own }
    if (keys.isEmpty()) return "You are not watching a battle."
    keys.forEach { interestManager.leave(session, it) }
    return "Stopped watching."
  }

  fun startWildBattle(session: SessionContext, dexId: Int, level: Int) {
    createWildBattle(session, dexId, level, catchable = true, escapable = true)
  }

  /**
   * A battle against a boss that other raiders are fighting at the same time. Every raider gets
   * their own battle holding the very same monster, so each of them sees an ordinary fight while
   * the damage all lands on one pool of health.
   */
  fun startSharedBossBattle(
      session: SessionContext,
      boss: BattleMonState,
  ): BattleInstance? =
      createBattle(session, emptyList(), catchable = false, escapable = true, shared = listOf(boss))

  /** Runs a story battle and waits for its scene. */
  suspend fun startScriptedBattle(
      session: SessionContext,
      dexId: Int,
      level: Int,
      moveIds: List<Int> = emptyList(),
      catchable: Boolean = false,
      escapable: Boolean = false,
  ): BattleResult {
    val battle =
        createWildBattle(
            session,
            dexId,
            level,
            catchable = catchable,
            escapable = escapable,
            moveIds = moveIds,
        ) ?: return BattleResult.FAILED
    return battle.completion.await()
  }

  /** Runs a battle against the decomp trainer with this id and waits for its scene. */
  suspend fun startTrainerBattle(
      session: SessionContext,
      region: Region,
      trainerId: Int,
  ): BattleResult {
    val trainer = trainers.get(region, trainerId)
    if (trainer == null) {
      log.warn { "No $region trainer with id $trainerId" }
      return BattleResult.FAILED
    }
    return startTrainerBattle(session, trainer)
  }

  /** Runs a battle against a trainer's whole team and waits for its scene. */
  suspend fun startTrainerBattle(session: SessionContext, trainer: TrainerDef): BattleResult {
    val battle =
        createBattle(
            session,
            trainer.party.map { OpponentSpec(it.dexId, it.level, it.moveIds, it.iv) },
            catchable = false,
            escapable = false,
            trainer = trainer,
        ) ?: return BattleResult.FAILED
    return battle.completion.await()
  }

  /** Empty [moveIds] keeps the level up moveset, a null [iv] rolls one like a wild encounter. */
  private data class OpponentSpec(
      val dexId: Int,
      val level: Int,
      val moveIds: List<Int>,
      val iv: Int? = null,
  )

  private fun createWildBattle(
      session: SessionContext,
      dexId: Int,
      level: Int,
      catchable: Boolean,
      escapable: Boolean,
      moveIds: List<Int> = emptyList(),
  ): BattleInstance? =
      createBattle(session, listOf(OpponentSpec(dexId, level, moveIds)), catchable, escapable)

  private fun createBattle(
      session: SessionContext,
      opponents: List<OpponentSpec>,
      catchable: Boolean,
      escapable: Boolean,
      trainer: TrainerDef? = null,
      /**
       * An opponent that has already been built. A raid passes the one boss to every raider's
       * battle, so the same monster takes all of their damage and its health is one shared pool.
       */
      shared: List<BattleMonState>? = null,
  ): BattleInstance? {
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return null
    if (battles.byChar(charId) != null) {
      session.send(notice("You are already in a battle."))
      return null
    }
    val stored = characterStore.getCharacter(charId) ?: return null
    if (stored.pokemon.isEmpty()) {
      session.send(notice("You need a monster in your party to battle."))
      return null
    }
    val party = mutableListOf<BattleMonState>()
    for ((index, mon) in stored.pokemon.withIndex()) {
      val def = speciesRegistry.get(mon.dexId)
      if (def == null) {
        session.send(notice("Your party has a species the battle data does not cover yet."))
        return null
      }
      party += BattleMonState(mon.id, def, index, mon, StatCalculator.computeAll(def, mon))
    }
    if (party.all { it.fainted }) {
      session.send(notice("All of your monsters have fainted."))
      return null
    }
    val rng = BattleRng()
    val enemies = mutableListOf<BattleMonState>()
    if (shared != null) enemies += shared
    for (spec in if (shared != null) emptyList() else opponents) {
      var rolled = wildMons.create(spec.dexId, spec.level, rng)
      if (rolled == null) {
        session.send(notice("Unknown species ${spec.dexId}."))
        return null
      }
      if (spec.moveIds.isNotEmpty()) {
        rolled =
            rolled.copy(
                moves =
                    spec.moveIds.take(4).map { id ->
                      PokemonMove(id.toShort(), (moveRegistry.get(id)?.pp ?: 0).toByte())
                    } + List((4 - spec.moveIds.size).coerceAtLeast(0)) { PokemonMove(0, 0) })
      }
      val def = speciesRegistry.get(spec.dexId)!!
      // A trainer's monsters are built to a fixed difficulty, so they must not keep the rolled
      // IVs. Max hp moves with them, and the monster comes out full.
      if (spec.iv != null) {
        val ivs =
            IVs().apply {
              hp = spec.iv
              atk = spec.iv
              this.def = spec.iv
              spAtk = spec.iv
              spDef = spec.iv
              spd = spec.iv
            }
        val fixed = rolled.copy(iVs = ivs)
        rolled = fixed.copy(hp = StatCalculator.computeAll(def, fixed).hp.toShort())
      }
      enemies +=
          BattleMonState(rolled.id, def, null, rolled, StatCalculator.computeAll(def, rolled))
    }
    log.info {
      "Starting battle for char=$charId (${stored.info.name}) against " +
          enemies.joinToString { "${it.species.name} level ${it.level}" }
    }
    val battle =
        battles.create(
            charId, session, party, enemies, rng, BattleRules(catchable, escapable, trainer))
    val firstAlive = party.indexOfFirst { !it.fainted }
    battle.activeSlot = firstAlive
    battle.seenActive.clear()
    battle.seenActive.add(firstAlive)
    interestManager.join(session, battle.key)
    emitter.sendStart(battle, stored.info.name)
    // After the opening sequence, which is capture proven and holds nothing else between its
    // packets. Meeting one in a battle is what registers it as seen, the trainer's own included.
    enemies.forEach { pokedex.recordSeen(charId, session, it.species.id) }
    return battle
  }

  private suspend fun resolveTurn(battle: BattleInstance, moveId: Short) {
    val events = engine.resolveTurn(battle, moveId)
    emitter.sendEvents(battle, events)
    afterTurn(battle)
  }

  private suspend fun afterTurn(battle: BattleInstance) {
    fireTurnResolved(battle.charId)
    when {
      battle.opponent.all { it.fainted } -> endVictory(battle)
      battle.party.all { it.fainted } -> endDefeat(battle)
      else -> {
        if (battle.opponentMon().fainted) {
          awardXp(battle, battle.opponentMon())
          sendOutNextOpponent(battle)
        }
        // The active mon fainted with a live backup. Open the switch screen instead of the action
        // prompt. The replacement arrives as a normal SWITCH action.
        if (battle.activeMon().fainted) {
          emitter.sendSwitchPrompt(battle)
        } else {
          battle.turn += 1
          emitter.sendPrompt(battle)
        }
      }
    }
  }

  /**
   * Uses a bag item on the active monster. Every item used to throw a ball regardless of what was
   * picked, so a Potion did nothing and was not consumed.
   */
  private suspend fun useItem(battle: BattleInstance, itemId: Short) {
    val id = itemId.toInt()
    val item = items.get(id)
    val effect = item?.let { BattleItems.effectOf(it) }
    // Only a recognised healing item diverts. Everything else throws a ball, which is what every
    // item did before, so catching cannot regress on an id we have not accounted for.
    if (item == null || effect == null) {
      catchWild(battle)
      return
    }
    val stored = characterStore.getCharacter(battle.charId)
    if ((stored?.items?.get(id) ?: 0) <= 0) {
      emitter.sendNotice(battle, "You have no ${item.name} left.")
      emitter.sendPrompt(battle)
      return
    }
    val mon = battle.activeMon()
    val healed = BattleItems.healAmount(effect, mon.currentHp, mon.stats.hp)
    val cured = effect.cures.contains(mon.status) && mon.status.isSet
    if (healed <= 0 && !cured) {
      // Refusing here keeps the item rather than spending a turn for nothing.
      emitter.sendNotice(battle, "It would have no effect.")
      emitter.sendPrompt(battle)
      return
    }
    if (healed > 0) mon.currentHp += healed
    if (cured) mon.clearStatus()
    characterStore.addItem(battle.charId, id, -1)
    log.info {
      "char=${battle.charId} used ${item.name}: healed=$healed cured=$cured " +
          "hp=${mon.currentHp}/${mon.stats.hp}"
    }
    emitter.sendItemUsed(battle, mon, cured)
    // Using an item spends the turn, so the opposing side gets to act.
    emitter.sendEvents(battle, engine.resolveSwitchTurn(battle))
    afterTurn(battle)
  }

  private suspend fun switchMon(battle: BattleInstance, partyIndex: Short) {
    val target = partyIndex.toInt()
    val mon = battle.party.getOrNull(target)
    val forced = battle.activeMon().fainted
    if (mon == null || mon.fainted || target == battle.activeSlot) {
      // Reopen the switch screen on an invalid forced choice, otherwise re-prompt for an action.
      if (forced) {
        emitter.sendSwitchPrompt(battle)
      } else {
        battle.turn += 1
        emitter.sendPrompt(battle)
      }
      return
    }
    // A forced switch confirms the choice before the switch-in. The captures pair the confirm with
    // a full block for a new mon and with a return block for a mon that was already active.
    if (forced) emitter.sendSwitchConfirm(battle)
    performSwitch(battle, target)
    if (forced) {
      // Replacing a fainted mon does not spend a turn, the new mon acts next.
      battle.turn += 1
      emitter.sendPrompt(battle)
    } else {
      // A voluntary switch spends the turn, so the wild attacks the incoming mon.
      emitter.sendEvents(battle, engine.resolveSwitchTurn(battle))
      afterTurn(battle)
    }
  }

  private fun sendOutNextOpponent(battle: BattleInstance) {
    val next = battle.opponent.indexOfFirst { !it.fainted }
    if (next < 0) return
    val fullBlock = next !in battle.opponentSeen
    battle.opponentSlot = next
    battle.opponentSeen.add(next)
    log.info { "Opponent sends out slot $next for char=${battle.charId}" }
    emitter.sendOpponentSwitchIn(battle, fullBlock)
  }

  private fun performSwitch(battle: BattleInstance, target: Int) {
    val oldSlot = battle.activeSlot
    // Asked before the monster is marked seen, or it is always already seen and never described.
    val fullBlock = target !in battle.seenActive
    // Leaving the field resets the toxic damage ramp, so a switch out and back in starts over.
    battle.party.getOrNull(oldSlot)?.resetToxicRamp()
    battle.activeSlot = target
    battle.seenActive.add(target)
    log.info { "Switch char=${battle.charId} slot $oldSlot -> $target" }
    // A monster coming out for the first time carries its full block, as every capture of this
    // packet does. The crash was never about which description to send: the party position was
    // going into the byte that says whether one follows.
    emitter.sendSwitchIn(battle, fullBlock)
    emitter.sendCarriedStatus(battle, battle.activeMon())
  }

  private fun flee(battle: BattleInstance) {
    if (!battle.escapable) {
      emitter.sendNotice(battle, "You can't run from this battle.")
      emitter.sendPrompt(battle)
      return
    }
    emitter.sendFled(battle)
    persistParty(battle)
    battle.pendingResult = BattleResult.FLED
  }

  private suspend fun catchWild(battle: BattleInstance) {
    if (!battle.catchable) {
      emitter.sendNotice(battle, "You can't catch this monster.")
      emitter.sendPrompt(battle)
      return
    }
    val stored = characterStore.getCharacter(battle.charId) ?: return
    // A full party overflows to the PC: CharacterEntry rejects a 7th party monster, so persisting
    // one would make every future login throw while building the character list.
    val destination = if (stored.pokemon.size < 6) PokemonContainer.PARTY else PokemonContainer.PC
    val pool =
        if (destination == PokemonContainer.PC) stored.pcStorage.toList()
        else stored.pokemon.toList()
    val nextSlot = ((pool.maxOfOrNull { it.containerSlot } ?: -1) + 1).toShort()
    val caught =
        battle
            .opponentMon()
            .source
            .copy(
                ownerId = battle.charId,
                container = destination,
                containerSlot = nextSlot,
                ot = stored.info.name,
                hp = battle.opponentMon().currentHp.toShort(),
                moves = battle.opponentMon().moves.map { PokemonMove(it.id, it.pp) },
                // Sleeping one first is how a catch is meant to go, so it keeps the status.
                status = battle.opponentMon().status,
                caughtAt = LocalDateTime.now(),
            )
    log.info { "Caught wild ${battle.opponentMon().species.name} for char=${battle.charId}" }
    pokedex.recordCaught(battle.charId, battle.session, battle.opponentMon().species.id)
    // The caught monster is sent as a full 148-byte record on opcode 0x14 before the ball-throw
    // event, so the client can resolve the monster when the throw lands.
    battle.session.send(SocialListEntryAddPacket(caught))
    battle.session.send(acquiredMonsterDelta(caught, battle.opponentMon().species))
    // "Player threw a Poke Ball" event.
    battle.session.send(
        BattleListEventPacket(
            kind = 0,
            value = pokeBallItemId,
            subKind = 4,
            detail = BattleListEventDetail(listType = 1, value = 1),
        ),
    )
    if (!characterStore.addPokemon(battle.charId, caught)) {
      log.error { "Could not persist the monster char=${battle.charId} just caught" }
    }
    if (destination == PokemonContainer.PC) {
      emitter.sendNotice(battle, "Your party is full, so it was sent to your PC.")
    }
    endBattle(battle, BattleResult.CAUGHT)
  }

  private suspend fun endVictory(battle: BattleInstance) {
    awardXp(battle, battle.opponentMon())
    val prize = battle.trainer?.let { rewards.trainerPrize(it, battle.opponent.last().level) } ?: 0
    val paid = prize > 0 && characterStore.addMoney(battle.charId, prize)
    if (prize > 0 && !paid) {
      log.error { "Could not pay char=${battle.charId} the $prize prize" }
    }
    endBattle(battle, BattleResult.VICTORY, battle.activeMon().entityId, if (paid) prize else 0)
  }

  /**
   * Pays the active monster for knocking [defeated] out. A trainer's team is paid for one at a
   * time, as each faints, which is when the captures show the delta going out.
   */
  /**
   * Shares the win with the whole party rather than only the monster that was out.
   *
   * The games these maps come from pay whichever monsters were sent out, and paying the party is a
   * later generation's rule, so this is a deliberate house rule rather than accuracy. A fainted
   * monster still earns nothing.
   */
  private fun awardXp(battle: BattleInstance, defeated: BattleMonState) {
    val active = battle.activeMon()
    val earners = battle.party.filter { !it.fainted }
    for (earner in earners) {
      awardXpTo(battle, earner, defeated, promptMoves = earner === active)
    }
  }

  private fun awardXpTo(
      battle: BattleInstance,
      winner: BattleMonState,
      defeated: BattleMonState,
      promptMoves: Boolean,
  ) {
    val reward = rewards.apply(winner, defeated.species, defeated.level, battle.trainer != null)
    log.info {
      "char=${battle.charId} won: +${reward.xpGained} xp, level ${winner.level} -> ${reward.newLevel}"
    }
    winner.currentHp = reward.newCurrentHp
    val outcome =
        moveLearner.learn(winner.moves, winner.source.dexId, winner.level, reward.newLevel)
    emitter.sendVictoryDelta(battle, winner.entityId, reward)
    for (move in outcome.learned) {
      emitter.sendNotice(battle, "${winner.species.name} learned ${move.name}!")
    }
    // Only the monster that fought is asked which move to drop. Several prompts at once would
    // stack windows on the player with no way to tell them apart.
    if (promptMoves && outcome.offered.isNotEmpty()) {
      val offered = outcome.offered.map { it.moveId.toShort() }
      pendingLearns[winner.entityId] = PendingMoveLearn(battle.charId, winner.entityId, offered)
      battle.session.send(MoveLearnPromptPacket(winner.entityId, offered))
    }
    val grown =
        winner.source.copy(
            level = reward.newLevel.toByte(),
            xp = reward.newXp,
            hp = reward.newCurrentHp.toShort(),
            eVs = reward.newEvs,
            moves = winner.moves.map { PokemonMove(it.id, it.pp) },
            status = winner.status,
        )
    winner.source = grown
    winner.stats = reward.newStats
    characterStore.updatePokemon(battle.charId, grown)
    evolveIfDue(battle, winner, reward.newLevel)
  }

  /**
   * Grows a monster into what it becomes, if this level is the one.
   *
   * Nothing here ever evolved: the table the games use was never carried across, so a monster
   * levelled past its threshold and stayed as it was forever. The species is changed on the live
   * battle state as well as in the store, so the rest of the battle is fought by what it became.
   */
  private fun evolveIfDue(battle: BattleInstance, winner: BattleMonState, level: Int) {
    val evolution = EvolutionTable.at(winner.source.dexId, level) ?: return
    val into = speciesRegistry.get(evolution.into)
    if (into == null) {
      log.warn { "No species data for ${evolution.into}, leaving ${winner.species.name} as it is" }
      return
    }
    val was = winner.species.name
    val evolved = winner.source.copy(dexId = evolution.into)
    winner.source = evolved
    winner.species = into
    winner.stats = StatCalculator.computeAll(into, evolved)
    winner.currentHp = winner.currentHp.coerceAtMost(winner.stats.hp)
    characterStore.updatePokemon(battle.charId, evolved)
    characterStore.flushCharacterAsync(battle.charId)
    pokedex.recordCaught(battle.charId, battle.session, evolution.into)
    log.info { "char=${battle.charId} evolved $was into ${into.name} at level $level" }
    emitter.sendNotice(battle, "$was evolved into ${into.name}!")
  }

  /**
   * The whole party is down, so the player is picked up, patched up and put back at the last place
   * they healed. Losing used to do nothing at all beyond ending the battle: it left the player
   * standing where they fell with a party of fainted monsters and no way to fight anything on the
   * walk back.
   *
   * The archive's whiteout capture is the shape followed here. A live server sends the map
   * transition, then the destination map, then one entity delta per monster carrying restored
   * moves, hp and a cleared faint flag — the same warp and the same heal this does, in that order.
   *
   * It does not take any money. The games charge for a whiteout, but nothing in the capture shows
   * what that is said with, and inventing a punishment is worse than owing one.
   */
  private suspend fun endDefeat(battle: BattleInstance) {
    endBattle(battle, BattleResult.DEFEAT)
    whiteout(battle)
  }

  private suspend fun whiteout(battle: BattleInstance) {
    val session = battle.session
    val state = session.attributes[PLAYER_STATE] ?: return
    val stored = characterStore.getCharacter(battle.charId) ?: return
    // Heal first and durably: an interrupted warp must not leave a party that is still knocked out.
    storyPlayer.healParty(session, state)
    characterStore.flushCharacterAsync(battle.charId)
    val destination = RespawnPoint.of(stored.storyVars)
    if (destination == null) {
      // Nobody has healed them anywhere yet, so there is nowhere to send them back to. They keep
      // the heal and stay put rather than being dropped on a map picked out of the air.
      log.info { "char=${battle.charId} whited out with no healing place recorded" }
      return
    }
    log.info {
      "char=${battle.charId} whited out, returning to " +
          "${destination.regionId}:${destination.bankId}:${destination.mapId}"
    }
    scriptWarp.warp(session, state, destination)
  }

  // Known issue: the caught monster does not show up in the party until the client reopens it.
  private fun endBattle(
      battle: BattleInstance,
      result: BattleResult,
      skip: Long? = null,
      prizeMoney: Int = 0,
  ) {
    persistParty(battle, skip)
    val party = characterStore.getCharacter(battle.charId)?.pokemon ?: emptyList()
    emitter.sendBattleEnd(battle, party, prizeMoney)
    battle.pendingResult = result
  }

  /** Write the battle's live hp and pp back into the party and flush the character. */
  private fun persistParty(battle: BattleInstance, skip: Long? = null) {
    for (state in battle.party) {
      if (state.entityId == skip) continue
      val updated =
          state.source.copy(
              hp = state.currentHp.toShort(),
              moves = state.moves.map { PokemonMove(it.id, it.pp) },
              status = state.status,
          )
      characterStore.updatePokemon(battle.charId, updated)
    }
    characterStore.flushCharacterAsync(battle.charId)
  }

  private fun finishBattle(battle: BattleInstance, result: BattleResult) {
    // Spectators are joined to the same key, so clear the whole bucket rather than only the
    // player: a watcher left behind would keep receiving the next battle on a reused id.
    interestManager.members(battle.key).forEach { interestManager.leave(it, battle.key) }
    interestManager.leave(battle.session, battle.key)
    battles.remove(battle.charId)
    battle.completion.complete(result)
  }
}
