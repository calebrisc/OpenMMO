package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.net.game.packets.EntityPresencePacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleBulkStatePacket
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleInstance
import de.fiereu.openmmo.server.game.battle.BattleItems
import de.fiereu.openmmo.server.game.battle.BattleMonState
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleParticipant
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleRng
import de.fiereu.openmmo.server.game.battle.DuelSide
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val PRESENCE_OVERWORLD: Byte = 0

/** What one duellist chose to do with their turn. */
private sealed interface DuelChoice {
  data class Move(val moveId: Short) : DuelChoice

  data class Switch(val slot: Int) : DuelChoice

  data class Item(val itemId: Int) : DuelChoice
}

/** What each side of a duel has locked in, held until both have chosen. */
private class DuelTurn {
  var host: DuelChoice? = null
  var challenged: DuelChoice? = null

  val ready: Boolean
    get() = host != null && challenged != null

  fun clear() {
    host = null
    challenged = null
  }
}

/**
 * Battles between two players.
 *
 * The turn engine always resolved both sides the same way and differed only in who chose the
 * opposing move, so a duel supplies the other player's pick instead of the computer's. What a duel
 * does need on top is a turn that waits for both players and a second view of the battle, since
 * each of them is the local side of their own screen.
 *
 * Nothing is persisted. A duel neither heals nor wounds the party that fought it, which keeps a
 * disconnect mid duel from costing anybody progress.
 */
@Singleton
class DuelService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val battles: BattleRegistry,
    private val engine: TurnEngine,
    private val emitter: BattlePacketEmitter,
    private val speciesRegistry: SpeciesRegistry,
    private val interestManager: InterestManager,
    private val sessionRegistry: SessionRegistry,
    private val items: ItemRegistry,
) {

  private val turns = ConcurrentHashMap<Long, DuelTurn>()

  /**
   * Told who won each duel. A tournament runs its bracket on these, since a duel otherwise ends
   * quietly and nothing outside it would know a match had been decided.
   */
  private val outcomeListeners = mutableListOf<(winner: Long, loser: Long) -> Unit>()

  fun onDuelFinished(listener: (winner: Long, loser: Long) -> Unit) {
    synchronized(outcomeListeners) { outcomeListeners += listener }
  }

  /** Who has been challenged, and by whom. Keyed by the player who owes an answer. */
  private val invites = ConcurrentHashMap<Long, Long>()

  fun describe(charId: Long): String {
    val from = invites[charId] ?: return "No pending challenge. Use /duel <name> to send one."
    val name = characterStore.getCharacter(from)?.info?.name ?: "Somebody"
    return "$name challenged you. /duel accept or /duel decline."
  }

  fun invite(session: SessionContext, charId: Long, targetName: String): String {
    val target = characterStore.findCachedByName(targetName) ?: return "$targetName is not online."
    val targetId = target.info.id
    if (targetId == charId) return "You cannot duel yourself."
    val targetSession =
        sessionRegistry.getByCharacterId(targetId) ?: return "${target.info.name} is not online."
    if (battles.byChar(charId) != null) return "You are already in a battle."
    if (battles.byChar(targetId) != null) return "${target.info.name} is already in a battle."
    val myName = characterStore.getCharacter(charId)?.info?.name ?: return "You are not in world."

    invites[targetId] = charId
    targetSession.send(
        notice("$myName has challenged you to a duel. Type /duel accept or /duel decline."))
    return "Challenge sent to ${target.info.name}."
  }

  fun accept(session: SessionContext, charId: Long): String {
    val challengerId = invites.remove(charId) ?: return "Nobody has challenged you."
    val challenger = characterStore.getCharacter(challengerId) ?: return "They are no longer here."
    val challengerSession =
        sessionRegistry.getByCharacterId(challengerId) ?: return "They are no longer online."
    val me = characterStore.getCharacter(charId) ?: return "You are not in world."
    challengerSession.send(notice("${me.info.name} accepted your challenge."))
    val started =
        start(
            hostSession = challengerSession,
            hostId = challengerId,
            hostName = challenger.info.name,
            targetSession = session,
            targetId = charId,
            targetName = me.info.name,
        )
    return if (started) "Duel starting." else "The duel could not be started."
  }

  fun decline(charId: Long): String {
    val challengerId = invites.remove(charId) ?: return "Nobody has challenged you."
    val me = characterStore.getCharacter(charId)?.info?.name ?: "They"
    sessionRegistry.getByCharacterId(challengerId)?.send(notice("$me declined your challenge."))
    return "Challenge declined."
  }

  /** Builds a battle-ready copy of a party, or null with the reason already sent. */
  private fun partyOf(session: SessionContext, charId: Long): List<BattleMonState>? {
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
    return party
  }

  /** True once the duel is running. Both sides are checked before anything is created. */
  fun start(
      hostSession: SessionContext,
      hostId: Long,
      hostName: String,
      targetSession: SessionContext,
      targetId: Long,
      targetName: String,
  ): Boolean {
    if (battles.byChar(hostId) != null) {
      hostSession.send(notice("You are already in a battle."))
      return false
    }
    if (battles.byChar(targetId) != null) {
      hostSession.send(notice("$targetName is already in a battle."))
      return false
    }
    val hostParty = partyOf(hostSession, hostId) ?: return false
    val targetParty = partyOf(targetSession, targetId) ?: return false

    val battle =
        battles.createDuel(
            host = BattleParticipant(hostId, hostSession, hostParty),
            opponentSide = DuelSide(targetId, targetSession, targetName),
            opponentParty = targetParty,
            rng = BattleRng(),
        )
    battle.activeSlot = hostParty.indexOfFirst { !it.fainted }
    battle.opponentSlot = targetParty.indexOfFirst { !it.fainted }
    battle.seenActive.clear()
    battle.seenActive.add(battle.activeSlot)
    battle.opponentSeen.clear()
    battle.opponentSeen.add(battle.opponentSlot)
    turns[battle.battleId] = DuelTurn()

    interestManager.join(hostSession, battle.key)
    interestManager.join(targetSession, battle.key)
    log.info { "Duel started: $hostName (char=$hostId) against $targetName (char=$targetId)" }
    emitter.sendDuelStart(battle, hostName)
    return true
  }

  /**
   * A duel action. Returns false when the battle is not a duel, so the ordinary path handles it.
   *
   * Only moves and forfeits are taken for now. Items and switches are answered rather than dropped,
   * so a player pressing them is told why nothing happened instead of watching the battle hang.
   */
  suspend fun onAction(
      battle: BattleInstance,
      charId: Long,
      action: BattleActionSelectPacket,
  ): Boolean {
    val duel = battle.duel ?: return false
    val isHost = charId == battle.charId
    val session = if (isHost) battle.session else duel.session
    val side = if (isHost) battle.party else battle.opponent

    if (action.action == BattleAction.RUN) {
      forfeit(battle, charId)
      return true
    }

    val choice =
        when (action.action) {
          BattleAction.MOVE -> DuelChoice.Move(action.moveOrItemId)
          BattleAction.ITEM -> DuelChoice.Item(action.moveOrItemId.toInt())
          BattleAction.SWITCH -> {
            val slot = action.moveOrItemId.toInt()
            val target = side.getOrNull(slot)
            when {
              target == null -> {
                session.send(notice("That is not one of your monsters."))
                emitter.sendPrompt(battle)
                return true
              }
              target.fainted -> {
                session.send(notice("${target.species.name} has fainted."))
                emitter.sendPrompt(battle)
                return true
              }
              slot == (if (isHost) battle.activeSlot else battle.opponentSlot) -> {
                session.send(notice("${target.species.name} is already out."))
                emitter.sendPrompt(battle)
                return true
              }
              else -> DuelChoice.Switch(slot)
            }
          }
          BattleAction.RUN -> return true
        }

    val turn = turns.getOrPut(battle.battleId) { DuelTurn() }
    // The pair is taken under the lock and resolved outside it, so the turn cannot be settled twice
    // when both players choose at the same moment.
    val both =
        synchronized(turn) {
          if (isHost) turn.host = choice else turn.challenged = choice
          if (!turn.ready) null
          else {
            val pair = turn.host!! to turn.challenged!!
            turn.clear()
            pair
          }
        }
    if (both == null) {
      session.send(notice("Waiting for your opponent..."))
      return true
    }
    resolve(battle, both.first, both.second)
    return true
  }

  /**
   * Switches and bag items happen before anybody attacks, the way they do in the games, so a
   * monster sent out this turn is the one that takes the hit.
   */
  private suspend fun resolve(
      battle: BattleInstance,
      host: DuelChoice,
      challenged: DuelChoice,
  ) {
    applyPreAction(battle, host, isHost = true)
    applyPreAction(battle, challenged, isHost = false)

    val hostMove = (host as? DuelChoice.Move)?.moveId
    val challengedMove = (challenged as? DuelChoice.Move)?.moveId
    val events =
        when {
          hostMove != null && challengedMove != null ->
              engine.resolveTurn(battle, hostMove, challengedMove)
          hostMove != null -> engine.resolveOneSided(battle, attackerIsHost = true, hostMove)
          challengedMove != null ->
              engine.resolveOneSided(battle, attackerIsHost = false, challengedMove)
          // Both stepped aside, so the turn passes with nothing thrown.
          else -> emptyList()
        }
    emitter.sendEvents(battle, events)
    battle.turn += 1

    val hostDown = battle.activeMon().fainted
    val challengedDown = battle.opponentMon().fainted
    if (!hostDown && !challengedDown) {
      emitter.sendPrompt(battle)
      return
    }

    // Whichever side lost its monster falls back to the next one that can still fight. A side with
    // nothing left has lost the duel.
    val hostNext = battle.party.indexOfFirst { !it.fainted }
    val challengedNext = battle.opponent.indexOfFirst { !it.fainted }
    if (hostNext < 0 || challengedNext < 0) {
      finish(battle, hostWon = challengedNext < 0 && hostNext >= 0)
      return
    }
    // Replacing a fallen monster is automatic and takes the next one that can still fight, rather
    // than asking, so a duel never stalls waiting on a menu.
    if (hostDown) {
      battle.activeSlot = hostNext
      emitter.sendDuelSwitchIn(battle, hostSwitched = true)
    }
    if (challengedDown) {
      battle.opponentSlot = challengedNext
      emitter.sendDuelSwitchIn(battle, hostSwitched = false)
    }
    emitter.sendPrompt(battle)
  }

  /** A switch or a bag item, both of which land before either side attacks. */
  private suspend fun applyPreAction(
      battle: BattleInstance,
      choice: DuelChoice,
      isHost: Boolean,
  ) {
    val duel = battle.duel ?: return
    when (choice) {
      is DuelChoice.Move -> Unit
      is DuelChoice.Switch -> {
        if (isHost) battle.activeSlot = choice.slot else battle.opponentSlot = choice.slot
        emitter.sendDuelSwitchIn(battle, hostSwitched = isHost)
      }
      is DuelChoice.Item -> {
        val charId = if (isHost) battle.charId else duel.charId
        val session = if (isHost) battle.session else duel.session
        val mon = if (isHost) battle.activeMon() else battle.opponentMon()
        val item = items.get(choice.itemId)
        val effect = item?.let { BattleItems.effectOf(it) }
        if (item == null || effect == null) {
          session.send(notice("That item does nothing in a battle."))
          return
        }
        val held = characterStore.getCharacter(charId)?.items?.get(choice.itemId) ?: 0
        if (held <= 0) {
          session.send(notice("You do not have a ${item.name}."))
          return
        }
        val healed = BattleItems.healAmount(effect, mon.currentHp, mon.stats.hp)
        val cured = effect.cures.contains(mon.status) && mon.status.isSet
        if (healed <= 0 && !cured) {
          session.send(notice("It would have no effect."))
          return
        }
        mon.currentHp = (mon.currentHp + healed).coerceAtMost(mon.stats.hp)
        if (cured) mon.status = StatusCondition.NONE
        // The bag is real even though the duel is not, so the item is spent the way it would be
        // anywhere else.
        characterStore.addItem(charId, choice.itemId, -1)
        emitter.sendItemUsed(battle, mon, cured)
        battle.sessions().forEach { it.send(notice("${item.name} was used.")) }
      }
    }
  }

  private fun forfeit(battle: BattleInstance, charId: Long) {
    val hostForfeited = charId == battle.charId
    finish(battle, hostWon = !hostForfeited, forfeited = true)
  }

  private fun finish(battle: BattleInstance, hostWon: Boolean, forfeited: Boolean = false) {
    val duel = battle.duel ?: return
    val hostName = battle.playerName
    val winner = if (hostWon) hostName else duel.name
    val loser = if (hostWon) duel.name else hostName
    val how = if (forfeited) "$loser forfeited" else "$winner won"
    log.info { "Duel between $hostName and ${duel.name} ended: $how" }

    battle.sessions().forEach { it.send(BattleBulkStatePacket.battleEnd(0)) }
    endFor(battle.session, battle.charId)
    endFor(duel.session, duel.charId)

    battles.remove(battle.charId)
    turns.remove(battle.battleId)
    interestManager.leave(battle.session, battle.key)
    interestManager.leave(duel.session, battle.key)

    val result = notice("Duel over: $how.")
    battle.sessions().forEach { it.send(result) }

    // Last, with the battle already out of the registry, so a listener starting the next match
    // finds both players free.
    val winnerId = if (hostWon) battle.charId else duel.charId
    val loserId = if (hostWon) duel.charId else battle.charId
    val listeners = synchronized(outcomeListeners) { outcomeListeners.toList() }
    listeners.forEach {
      runCatching { it(winnerId, loserId) }
          .onFailure { e -> log.warn(e) { "duel listener failed" } }
    }
  }

  /**
   * Returns a player to the overworld with the party they actually own. A duel writes nothing back,
   * so this also undoes the damage it did.
   */
  private fun endFor(session: SessionContext, charId: Long) {
    session.send(EntityPresencePacket(entityId = charId, status = PRESENCE_OVERWORLD))
    val party = characterStore.getCharacter(charId)?.pokemon?.toList() ?: return
    session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ))
  }

  /** Ends a duel whose player vanished, so the other one is not left waiting on a dead session. */
  fun onDisconnect(charId: Long) {
    val battle = battles.byChar(charId) ?: return
    if (!battle.isDuel) return
    finish(battle, hostWon = charId != battle.charId, forfeited = true)
  }
}
