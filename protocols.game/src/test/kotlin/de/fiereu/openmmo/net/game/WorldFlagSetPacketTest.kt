package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.packets.WorldFlagSetPacket
import de.fiereu.openmmo.net.game.packets.WorldFlagSetPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * Every fixture here is a live server's own 0x0B, and together they settle what the packet is: the
 * flag id is the decomp's own number, and a set flag is written as the whole byte.
 *
 * The two region fixtures were filed as evolution prompts on the guess that 0x0020 was Nidoran♂. It
 * is flag 32, sent to two regions at once, which is what a temp flag looks like.
 */
class WorldFlagSetPacketTest :
    FunSpec({
      test("a captured flag set carries the raw decomp flag id") {
        val buf = fixtureBuffer("game/s2c/0b/flag_set_seafoam_boulder_32763.bin")
        val packet = WorldFlagSetPacketCodec.read(buf)

        // 0x4A is FLAG_HIDE_SEAFOAM_B3F_BOULDER_5, set as the player pushed it.
        packet shouldBe
            WorldFlagSetPacket(region = 0, flagId = 0x4A, value = WorldFlagSetPacket.SET)
        buf.remaining() shouldBe 0
      }

      test("a set flag is 0xFF, not 1") {
        val buf = fixtureBuffer("game/s2c/0b/flag_set_seafoam_boulder_32763.bin")

        WorldFlagSetPacketCodec.read(buf).value shouldBe (0xFF).toByte()
        WorldFlagSetPacket.of(region = 0, flagId = 0x4A, enabled = true).value shouldBe
            (0xFF).toByte()
      }

      test("a login clears a temp flag with the same packet") {
        val buf = fixtureBuffer("game/s2c/0b/flag_clear_temp_at_login.bin")
        val packet = WorldFlagSetPacketCodec.read(buf)

        // FLAG_TEMP_4, cleared. The capture sends the same three bytes for regions 0, 1 and 2.
        packet shouldBe WorldFlagSetPacket(region = 0, flagId = 4, value = WorldFlagSetPacket.CLEAR)
        buf.remaining() shouldBe 0
      }

      test("the same flag is sent to each region on its own packet") {
        val one =
            WorldFlagSetPacketCodec.read(fixtureBuffer("game/s2c/0b/flag_set_region1_32763.bin"))
        val two =
            WorldFlagSetPacketCodec.read(fixtureBuffer("game/s2c/0b/flag_set_region2_32763.bin"))

        one.region shouldBe 1.toByte()
        two.region shouldBe 2.toByte()
        one.flagId shouldBe two.flagId
        one.flagId shouldBe 32.toShort()
      }
    })
