package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.server.game.world.interest.BattleInterestKey
import de.fiereu.openmmo.trainer.TrainerDef
import kotlinx.coroutines.CompletableDeferred

enum class BattleResult {
  VICTORY,
  DEFEAT,
  FLED,
  CAUGHT,
  DISCONNECTED,
  FAILED,
}

/** What kind of battle this is, beyond the two teams. A [trainer] owns the opposing side. */
data class BattleRules(
    val catchable: Boolean = true,
    val escapable: Boolean = true,
    val trainer: TrainerDef? = null,
)

/**
 * One running battle. A wild encounter is the case where [opponent] holds a single monster.
 *
 * [participants] is the human side. Everything with one player goes through [host] and the
 * delegating accessors below, so the ordinary case reads exactly as it did when a battle could only
 * ever hold one player.
 */
class BattleInstance(
    val battleId: Long,
    val participants: List<BattleParticipant>,
    val opponent: List<BattleMonState>,
    val rng: BattleRng,
    val catchable: Boolean = true,
    val escapable: Boolean = true,
    /** The trainer who owns [opponent], or null for a wild encounter. */
    val trainer: TrainerDef? = null,
) {
  /** The single-player battle, which is still how every battle starts today. */
  constructor(
      battleId: Long,
      charId: Long,
      session: SessionContext,
      party: List<BattleMonState>,
      opponent: List<BattleMonState>,
      rng: BattleRng,
      catchable: Boolean = true,
      escapable: Boolean = true,
      trainer: TrainerDef? = null,
  ) : this(
      battleId,
      listOf(BattleParticipant(charId, session, party)),
      opponent,
      rng,
      catchable,
      escapable,
      trainer,
  )

  init {
    require(participants.isNotEmpty()) { "A battle needs at least one participant" }
  }

  val key: BattleInterestKey = BattleInterestKey(battleId)
  var turn: Int = 1
  var opponentSlot: Int = 0

  val opponentSeen: MutableSet<Int> = mutableSetOf(0)

  /** Completes when the battle leaves the registry. */
  val completion = CompletableDeferred<BattleResult>()

  /** Result held until the client confirms that its battle-to-map transition has finished. */
  var pendingResult: BattleResult? = null

  /** The player who started the battle, and the only one in an ordinary battle. */
  val host: BattleParticipant
    get() = participants.first()

  val coop: Boolean
    get() = participants.size > 1

  val charId: Long
    get() = host.charId

  val session: SessionContext
    get() = host.session

  val party: List<BattleMonState>
    get() = host.party

  var activeSlot: Int
    get() = host.activeSlot
    set(value) {
      host.activeSlot = value
    }

  val seenActive: MutableSet<Int>
    get() = host.seenActive

  fun activeMon(): BattleMonState = host.activeMon()

  fun opponentMon(): BattleMonState = opponent[opponentSlot]

  fun participantFor(charId: Long): BattleParticipant? =
      participants.firstOrNull { it.charId == charId }

  fun participantOwning(entityId: Long): BattleParticipant? =
      participants.firstOrNull { it.owns(entityId) }

  fun isPlayerSide(entityId: Long): Boolean = participants.any { it.owns(entityId) }

  /** Every monster the human side currently has out. */
  fun activeMons(): List<BattleMonState> = participants.map { it.activeMon() }

  /** True once no participant has anything left to send out. */
  val allParticipantsDefeated: Boolean
    get() = participants.all { it.defeated }
}
