package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.packets.PcMove
import de.fiereu.openmmo.net.game.packets.PcMovePacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * Both captures come from one player dragging four monsters at once, off a live client. The old
 * codec read the leading count as a kind and stopped after one move, leaving eighteen bytes it
 * could not account for, and the packet was dropped rather than applied.
 */
class PcMovePacketTest :
    FunSpec({
      test("a four monster drag into the party is consumed to the last byte") {
        val buf = fixtureBuffer("game/c2s/09/four_monsters_to_party_32763.bin")
        val packet = PcMovePacketCodec.read(buf)

        packet.moves shouldBe
            listOf(
                PcMove(fromContainer = 0, fromSlot = 4, toContainer = 1, toSlot = 2),
                PcMove(fromContainer = 0, fromSlot = 5, toContainer = 1, toSlot = 3),
                PcMove(fromContainer = 0, fromSlot = 6, toContainer = 1, toSlot = 4),
                PcMove(fromContainer = 0, fromSlot = 7, toContainer = 1, toSlot = 5),
            )
        buf.remaining() shouldBe 0
      }

      test("a four monster drag within the box carries slots past a byte") {
        val buf = fixtureBuffer("game/c2s/09/four_monsters_within_box_32763.bin")
        val packet = PcMovePacketCodec.read(buf)

        packet.moves.map { it.toSlot } shouldBe listOf(24, 25, 26, 27)
        packet.moves.map { it.toContainer } shouldBe listOf<Byte>(0, 0, 0, 0)
        buf.remaining() shouldBe 0
      }
    })
