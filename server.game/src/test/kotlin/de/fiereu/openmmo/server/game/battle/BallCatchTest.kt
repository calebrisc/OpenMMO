package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.StatusCondition
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

// Real Gen 3 catch rates: a Snorlax is stubborn, a Caterpie is not, a Master Ball ignores both.
private const val SNORLAX_RATE = 25
private const val CATERPIE_RATE = 255
private const val POKE_BALL = 10
private const val ULTRA_BALL = 20

private fun caughtOutOf(
    tries: Int,
    catchRate: Int,
    ballMultiplier: Int = POKE_BALL,
    maxHp: Int = 100,
    currentHp: Int = 100,
    status: StatusCondition = StatusCondition.NONE,
): Int =
    (1..tries).count {
      BallCatch.shakes(
          catchRate = catchRate,
          ballMultiplier = ballMultiplier,
          maxHp = maxHp,
          currentHp = currentHp,
          status = status,
          rng = BattleRng(seed = it.toLong()),
      ) == BallCatch.SHAKES_FOR_A_CATCH
    }

class BallCatchTest :
    FunSpec({
      test("a full health Snorlax does not go into a Poke Ball every time") {
        // The whole point: this used to be 1000 out of 1000, for every species and every ball.
        val caught = caughtOutOf(1000, SNORLAX_RATE)

        (caught < 100) shouldBe true
      }

      test("hurting it and putting it to sleep is worth more than a better ball") {
        val plain = caughtOutOf(1000, SNORLAX_RATE)
        val better = caughtOutOf(1000, SNORLAX_RATE, ballMultiplier = ULTRA_BALL)
        val weakened = caughtOutOf(1000, SNORLAX_RATE, currentHp = 10)
        val asleep = caughtOutOf(1000, SNORLAX_RATE, currentHp = 10, status = StatusCondition.SLEEP)

        (better > plain) shouldBe true
        (weakened > better) shouldBe true
        (asleep > weakened) shouldBe true
      }

      test("a weakened Caterpie is a certainty, and the odds cap rather than overflow") {
        BallCatch.shakes(
            catchRate = CATERPIE_RATE,
            ballMultiplier = ULTRA_BALL,
            maxHp = 100,
            currentHp = 1,
            status = StatusCondition.SLEEP,
            rng = BattleRng(seed = 1),
        ) shouldBe BallCatch.SHAKES_FOR_A_CATCH
      }

      test("a Master Ball never misses, however hopeless the odds") {
        repeat(50) {
          BallCatch.shakes(
              catchRate = 3,
              ballMultiplier = POKE_BALL,
              maxHp = 300,
              currentHp = 300,
              status = StatusCondition.NONE,
              rng = BattleRng(seed = it.toLong()),
              isMasterBall = true,
          ) shouldBe BallCatch.SHAKES_FOR_A_CATCH
        }
      }

      test("a failed throw still shakes, so the animation has something to say") {
        val shakeCounts =
            (1..200)
                .map {
                  BallCatch.shakes(
                      catchRate = SNORLAX_RATE,
                      ballMultiplier = POKE_BALL,
                      maxHp = 100,
                      currentHp = 100,
                      status = StatusCondition.NONE,
                      rng = BattleRng(seed = it.toLong()),
                  )
                }
                .toSet()

        // Not every miss is a flat zero: the ball rocks a varying number of times.
        shakeCounts shouldNotBe setOf(0)
        shakeCounts.all { it in 0..BallCatch.SHAKES_FOR_A_CATCH } shouldBe true
      }

      test("a fainted target cannot be caught and does not divide by zero") {
        BallCatch.shakes(
            catchRate = CATERPIE_RATE,
            ballMultiplier = POKE_BALL,
            maxHp = 0,
            currentHp = 0,
            status = StatusCondition.NONE,
            rng = BattleRng(seed = 1),
        ) shouldBe 0
      }
    })
