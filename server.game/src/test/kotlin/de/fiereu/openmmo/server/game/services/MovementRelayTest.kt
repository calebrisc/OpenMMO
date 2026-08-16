package de.fiereu.openmmo.server.game.services

import de.fiereu.bytecodec.ByteArrayReadBuffer
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.net.game.packets.GbaEntityMovePacket
import de.fiereu.openmmo.net.game.packets.GbaEntityMovePacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * What a live server tells everyone else about one player's step.
 *
 * The bytes are a real relay lifted out of the archive, of a player walking east across map 3:20:
 * `00 90 48 71 C4 80 D0 1A 03 14 07 3B 02 03`. It pins the two things that were guesses — the
 * direction encoding and the movement mode — against a capture instead of against trial and error.
 */
class MovementRelayTest :
    FunSpec({
      fun read(hex: String): GbaEntityMovePacket {
        val bytes = hex.split(" ").map { it.toInt(16).toByte() }.toByteArray()
        return GbaEntityMovePacketCodec.read(ByteArrayReadBuffer(bytes))
      }

      test("a captured relay decodes to the step it describes") {
        val packet = read("00 90 48 71 C4 80 D0 1A 03 14 07 3B 02 03")

        packet.bankId shouldBe 3
        packet.mapId shouldBe 20
        packet.x shouldBe 7
        packet.y shouldBe 59
        packet.movementMode shouldBe 2
        packet.direction shouldBe Direction.RIGHT
        // Player entities carry this tag in the low bits of their id.
        (packet.entityId and 0xFFFF) shouldBe 0x9000L
      }

      test("the direction byte is our own ordinals, confirmed by where the player ended up") {
        // Four consecutive relays for one player: down once, then east three times.
        read("00 90 48 71 C4 80 D0 1A 03 14 06 3B 02 00").direction shouldBe Direction.DOWN
        read("00 90 48 71 C4 80 D0 1A 03 14 07 3B 02 03").direction shouldBe Direction.RIGHT
        read("00 90 48 71 C4 80 D0 1A 03 14 06 3A 02 02").direction shouldBe Direction.LEFT
        read("00 90 08 5A B9 22 D1 1A 04 03 06 0B 02 01").direction shouldBe Direction.UP
      }

      test("a running step carries the same mode as a walking one") {
        // Captured 80ms after the step before it, which is a run, and still a 2.
        read("00 90 48 17 A5 A1 D3 1A 03 14 07 05 02 03").movementMode shouldBe 2

        // So the server has no reason to hold two values, and holds two equal ones.
        MovementTuning.walk shouldBe 2
        MovementTuning.run shouldBe 2
      }
    })
