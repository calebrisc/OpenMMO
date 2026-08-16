package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.moves.MoveRegistry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

/**
 * The machines are labelled by the client, which is a Gen 5 one: the item table carries TM01 to
 * TM95 and HM01 to HM06, exactly that set. The move registry behind them is a GBA one, so some of
 * those machines name a move this game does not have, and those must come back empty rather than
 * resolving to whatever sits at that number in another generation.
 */
class MachineMovesTest :
    FunSpec({
      val moves = MoveRegistry()

      test("a machine teaches the move the client says it does") {
        // The one that went wrong: the GBA table calls TM09 Bullet Seed and taught it to a
        // Venusaur over a move it already knew, while the client's box says Venoshock.
        MachineMoves.moveNameFor("TM09") shouldBe "Venoshock"

        MachineMoves.moveNameFor("HM01") shouldBe "Cut"
        MachineMoves.moveNameFor("TM06") shouldBe "Toxic"
        MachineMoves.moveNameFor("TM94") shouldBe "Rock Smash"
      }

      test("a move this game does not have resolves to nothing at all") {
        // Venoshock is a Gen 5 move and the registry is a GBA one, so there is nothing to teach.
        MachineMoves.moveIdFor("TM09", moves).shouldBeNull()
        // Rather than the id that a GBA table would have handed back for the same machine.
        MachineMoves.moveIdFor("TM09", moves) shouldNotBe
            moves.all().first { it.name.equals("Bullet Seed", ignoreCase = true) }.id
      }

      test("the machines whose moves are old enough resolve to them by name") {
        fun idOf(name: String) = moves.all().first { it.name.equals(name, ignoreCase = true) }.id

        MachineMoves.moveIdFor("HM01", moves) shouldBe idOf("Cut")
        MachineMoves.moveIdFor("HM03", moves) shouldBe idOf("Surf")
        MachineMoves.moveIdFor("TM06", moves) shouldBe idOf("Toxic")
        MachineMoves.moveIdFor("TM26", moves) shouldBe idOf("Earthquake")
        MachineMoves.moveIdFor("TM30", moves) shouldBe idOf("Shadow Ball")
      }

      test("every machine in the item table is accounted for") {
        (1..95).forEach { MachineMoves.moveNameFor("TM%02d".format(it)) shouldNotBe null }
        (1..6).forEach { MachineMoves.moveNameFor("HM%02d".format(it)) shouldNotBe null }
      }

      test("an unknown machine is not a machine") {
        MachineMoves.moveNameFor("POTION").shouldBeNull()
        MachineMoves.moveIdFor("POTION", moves).shouldBeNull()
      }
    })
