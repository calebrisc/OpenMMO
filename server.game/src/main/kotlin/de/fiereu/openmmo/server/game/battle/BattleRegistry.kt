package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The running battles. A character is in at most one, but a battle can hold several characters, so
 * the index maps every participant to the same instance.
 */
@Singleton
class BattleRegistry @Inject constructor() {

  private val byChar = ConcurrentHashMap<Long, BattleInstance>()
  private val ids = AtomicLong(1)

  fun create(
      charId: Long,
      session: SessionContext,
      party: List<BattleMonState>,
      opponent: List<BattleMonState>,
      rng: BattleRng,
      rules: BattleRules = BattleRules(),
  ): BattleInstance =
      create(listOf(BattleParticipant(charId, session, party)), opponent, rng, rules)

  fun create(
      participants: List<BattleParticipant>,
      opponent: List<BattleMonState>,
      rng: BattleRng,
      rules: BattleRules = BattleRules(),
  ): BattleInstance {
    val battle =
        BattleInstance(
            ids.getAndIncrement(),
            participants,
            opponent,
            rng,
            rules.catchable,
            rules.escapable,
            rules.trainer,
        )
    participants.forEach { byChar[it.charId] = battle }
    return battle
  }

  /**
   * A battle between two players. The challenger hosts and the challenged owns the opposing side,
   * so both are indexed onto the one instance and either one's actions find it.
   */
  fun createDuel(
      host: BattleParticipant,
      opponentSide: DuelSide,
      opponentParty: List<BattleMonState>,
      rng: BattleRng,
  ): BattleInstance {
    val battle =
        create(listOf(host), opponentParty, rng, BattleRules(catchable = false, escapable = false))
    battle.duel = opponentSide
    byChar[opponentSide.charId] = battle
    return battle
  }

  fun byChar(charId: Long): BattleInstance? = byChar[charId]

  /** Removes the battle [charId] is in, and with it every other participant's entry. */
  fun remove(charId: Long): BattleInstance? {
    val battle = byChar.remove(charId) ?: return null
    battle.participants.forEach { byChar.remove(it.charId, battle) }
    battle.duel?.let { byChar.remove(it.charId, battle) }
    return battle
  }
}
