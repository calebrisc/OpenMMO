package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.packets.SystemFlagBatchPacketCodec
import de.fiereu.openmmo.net.game.packets.SystemFlagEntry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

/**
 * Both captured from a live server's login, one per region, right after the containers. Read as a
 * shop price table before there was a capture to check the names against.
 */
class SystemFlagBatchPacketTest :
    FunSpec({
      test("a captured batch carries the system flags a region starts with") {
        val buf = fixtureBuffer("game/s2c/29/system_flags_kanto_32763.bin")
        val packet = SystemFlagBatchPacketCodec.read(buf)

        packet.regionId shouldBe 0.toByte()
        packet.flags shouldBe listOf(SystemFlagEntry(2192, 1), SystemFlagEntry(2193, 1))
        // Everything here is FLAG_SYS_, which begins at 0x800. Nothing below it is ever sent.
        packet.flags.forEach { (it.flagId.toInt() >= 0x800) shouldBe true }
        buf.remaining() shouldBe 0
      }

      test("a region the player has not touched still gets its packet, with nothing in it") {
        val buf = fixtureBuffer("game/s2c/29/system_flags_hoenn_empty_32763.bin")
        val packet = SystemFlagBatchPacketCodec.read(buf)

        packet.regionId shouldBe 1.toByte()
        packet.flags.shouldBeEmpty()
        buf.remaining() shouldBe 0
      }
    })
