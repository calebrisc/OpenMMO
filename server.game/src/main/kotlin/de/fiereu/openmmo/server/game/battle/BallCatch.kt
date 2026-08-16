package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.PokemonType
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.items.ItemDef
import de.fiereu.openmmo.items.generated.Items

/**
 * Whether a thrown ball holds, by the Gen 3 rules in `Cmd_handleballthrow`.
 *
 * Every ball caught everything before this: there was no catch rate anywhere on the server, so a
 * Poke Ball took a full health Snorlax, an Articuno, or anything else, first throw, every time.
 * [SpeciesDef.catchRate][de.fiereu.openmmo.pokemon.SpeciesDef.catchRate] was loaded for every
 * species all along and simply never read.
 */
object BallCatch {

  /** Four shakes is a catch. The games count the three you see plus the click. */
  const val SHAKES_FOR_A_CATCH = 4

  /** Multipliers are tenths, so a Poke Ball is 10 and an Ultra Ball is 20. */
  private const val ONE = 10

  /**
   * The ball's bonus, in tenths. [turn] is the battle's turn counter, which is what makes a Timer
   * Ball better the longer a fight has run, and [alreadyCaught] is whether the dex has this species
   * already, which is what a Repeat Ball rewards.
   */
  fun multiplier(
      ball: ItemDef?,
      target: BattleMonState,
      turn: Int,
      alreadyCaught: Boolean,
  ): Int =
      when (ball) {
        Items.ULTRA_BALL -> 20
        Items.GREAT_BALL,
        Items.SAFARI_BALL -> 15
        Items.NET_BALL ->
            if (target.species.hasType(PokemonType.WATER) ||
                target.species.hasType(PokemonType.BUG))
                30
            else ONE
        // A Dive Ball wants the underwater map type, which nothing here dives into yet.
        Items.DIVE_BALL -> ONE
        Items.NEST_BALL -> if (target.level < 40) (40 - target.level).coerceAtLeast(ONE) else ONE
        Items.REPEAT_BALL -> if (alreadyCaught) 30 else ONE
        Items.TIMER_BALL -> (turn + ONE).coerceAtMost(40)
        else -> ONE
      }

  /**
   * How many times the ball rocks, out of [SHAKES_FOR_A_CATCH].
   *
   * This is the decomp's arithmetic kept in its own integer order, truncation included, because the
   * truncation is load-bearing: `catchRate * ballMultiplier / 10` floors before it is scaled by the
   * health term, and doing the division last gives noticeably different odds.
   */
  fun shakes(
      catchRate: Int,
      ballMultiplier: Int,
      maxHp: Int,
      currentHp: Int,
      status: StatusCondition,
      rng: BattleRng,
      isMasterBall: Boolean = false,
  ): Int {
    if (isMasterBall) return SHAKES_FOR_A_CATCH
    if (maxHp <= 0) return 0
    var odds = (catchRate * ballMultiplier / ONE) * (maxHp * 3 - currentHp * 2) / (3 * maxHp)
    // A sleeping or frozen target is twice as easy; the other conditions are half again.
    if (status == StatusCondition.SLEEP || status == StatusCondition.FREEZE) {
      odds *= 2
    } else if (status == StatusCondition.POISON ||
        status == StatusCondition.TOXIC ||
        status == StatusCondition.BURN ||
        status == StatusCondition.PARALYSIS) {
      odds = odds * 15 / ONE
    }
    if (odds > 254) return SHAKES_FOR_A_CATCH
    if (odds <= 0) return 0

    val threshold = 1048560 / isqrt(isqrt(16711680 / odds))
    var shakes = 0
    // Random() is a 16 bit value in the games, so the roll has to share that range.
    while (shakes < SHAKES_FOR_A_CATCH && rng.pick(0x10000) < threshold) shakes++
    return shakes
  }

  /** The games' integer square root; the odds curve is built out of two of them. */
  private fun isqrt(value: Int): Int {
    if (value <= 0) return 1
    var root = Math.sqrt(value.toDouble()).toInt()
    while (root * root > value) root--
    while ((root + 1) * (root + 1) <= value) root++
    return root.coerceAtLeast(1)
  }
}
