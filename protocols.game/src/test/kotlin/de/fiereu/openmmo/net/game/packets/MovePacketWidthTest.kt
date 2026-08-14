package de.fiereu.openmmo.net.game.packets

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * Why a DS region cannot broadcast steps through the GBA packet. HeartGold addresses tiles across a
 * whole map matrix and reaches four figures, and the GBA form packs each coordinate into one byte.
 */
class MovePacketWidthTest :
    FunSpec({
      val johtoX = 1045
      val johtoY = 239

      test("the gba step packet refuses a coordinate past a byte") {
        val packet =
            GbaEntityMovePacket(
                entityId = 1L,
                bankId = 0,
                mapId = 0,
                x = johtoX,
                y = johtoY,
                direction = Direction.DOWN,
            )

        // It throws rather than wrapping, so a Johto step sent this way fails at encode instead of
        // quietly landing the player on the wrong tile.
        shouldThrow<IllegalArgumentException> { GbaEntityMovePacketCodec.encodeToBytes(packet) }
      }

      test("the wider step packet carries it intact") {
        val packet =
            EntityMovePacket(entityId = 1L, x = johtoX, y = johtoY, direction = Direction.DOWN)
        val roundTripped =
            EntityMovePacketCodec.decodeBytes(EntityMovePacketCodec.encodeToBytes(packet))

        roundTripped.x shouldBe johtoX
        roundTripped.y shouldBe johtoY
      }
    })
