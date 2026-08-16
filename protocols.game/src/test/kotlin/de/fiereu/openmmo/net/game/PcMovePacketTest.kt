package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.packets.PcMove
import de.fiereu.openmmo.net.game.packets.PcMovePacketCodec
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacketCodec
import de.fiereu.openmmo.net.game.packets.battle.StoragePlacement
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

/**
 * The other half of a box drag, captured from a live server: it answers the move with one entity
 * delta per monster that changed place, carrying only where that monster now is.
 */
class StoragePlacementDeltaTest :
    FunSpec({
      test("a captured placement delta names the container and slot a monster moved to") {
        val buf = fixtureBuffer("game/s2c/16/placement_after_box_move.bin")
        val delta = BattleEntityDeltaPacketCodec.read(buf)

        // Monster entity ids carry the 0xC000 tag in their low bits.
        (delta.entityId and 0xFFFF) shouldBe 0xC000L
        delta.placement shouldBe StoragePlacement(container = 1, slot = 4)
        // Nothing else rides along: a move says where it went and no more.
        delta.species shouldBe null
        delta.currentHp shouldBe null
        buf.remaining() shouldBe 0
      }
    })
