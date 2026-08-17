package de.fiereu.openmmo.server.game.script

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

/**
 * The Vermilion gym cans, five across and three down:
 * ```
 *  1  2  3  4  5
 *  6  7  8  9 10
 * 11 12 13 14 15
 * ```
 *
 * `SetVermilionTrashCans` writes the second switch out as a case per starting can. The cases are
 * the orthogonal neighbours on this grid, so the rule is checked against the numbers the decomp
 * hard-codes rather than against itself.
 */
class TrashCanPuzzleTest :
    FunSpec({
      // Reimplemented here rather than exposed, so the test pins the shape the script relies on.
      fun touching(can: Int): List<Int> {
        val index = can - 1
        val row = index / 5
        val column = index % 5
        return buildList {
          if (column > 0) add(can - 1)
          if (column < 4) add(can + 1)
          if (row > 0) add(can - 5)
          if (row < 2) add(can + 5)
        }
      }

      test("a corner can offers the two the decomp offers") {
        // case 1: +1 or +5.
        touching(1) shouldContainExactlyInAnyOrder listOf(2, 6)
        // case 5: +5 or -1.
        touching(5) shouldContainExactlyInAnyOrder listOf(10, 4)
        touching(11) shouldContainExactlyInAnyOrder listOf(12, 6)
        touching(15) shouldContainExactlyInAnyOrder listOf(14, 10)
      }

      test("a top edge can offers three, a middle can offers four") {
        // cases 2, 3, 4: +1, +5 or -1.
        touching(3) shouldContainExactlyInAnyOrder listOf(2, 4, 8)
        // case 6: -5, +1, +5.
        touching(6) shouldContainExactlyInAnyOrder listOf(1, 7, 11)
        // cases 7, 8, 9: -5, +1, +5, -1.
        touching(8) shouldContainExactlyInAnyOrder listOf(3, 9, 13, 7)
      }

      test("the second switch always touches the first, for every can in the room") {
        (1..15).forEach { first ->
          val second = touching(first)
          second.isNotEmpty() shouldBe true
          second.forEach { (it in 1..15) shouldBe true }
          // Touching runs both ways, which is what makes checking the next can along a strategy.
          second.forEach { touching(it).contains(first) shouldBe true }
        }
      }
    })
