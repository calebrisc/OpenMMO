package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext

/**
 * One human taking part in a battle: their session, the party they brought, and which monster of it
 * they currently have out.
 *
 * A normal battle holds exactly one of these. Co-op holds several, which is why the per-player
 * state lives here rather than on [BattleInstance].
 */
class BattleParticipant(
    val charId: Long,
    val session: SessionContext,
    val party: List<BattleMonState>,
) {
  var activeSlot: Int = 0

  /**
   * Which party slots have been sent out. A monster's first appearance carries its full block, a
   * return only its active detail.
   */
  val seenActive: MutableSet<Int> = mutableSetOf(0)

  fun activeMon(): BattleMonState = party[activeSlot]

  fun owns(entityId: Long): Boolean = party.any { it.entityId == entityId }

  /** True once this player has nothing left to send out. */
  val defeated: Boolean
    get() = party.all { it.fainted }
}
