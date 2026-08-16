package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.listPrefixed

/** One system flag and what it is set to. */
data class SystemFlagEntry(val flagId: Short, val value: Short)

/**
 * The system flags a region starts a session with (opcode 0x29 server to client).
 *
 * A live server sends one of these per region right after the containers, carrying only flags at
 * 0x800 and above -- the FLAG_SYS_ block. Everything below that lives in the flag table instead,
 * and a single flag changing later goes out on its own afterwards.
 *
 * This was read as a shop price table, a category and a list of paired shorts. The shape was right
 * and the names were not: the category is the region and each pair is a flag and its value. The
 * captured payload `00 0200 9008 0100 9108 0100` is region 0 with flags 2192 and 2193 set.
 */
data class SystemFlagBatchPacket(
    val regionId: Byte,
    val flags: List<SystemFlagEntry>,
)

private val SystemFlagEntryCodec: Codec<SystemFlagEntry> =
    object : PacketCodec<SystemFlagEntry>() {
      override fun CodecScope<SystemFlagEntry>.body(): SystemFlagEntry =
          SystemFlagEntry(field(S16LE) { it.flagId }, field(S16LE) { it.value })
    }

object SystemFlagBatchPacketCodec : PacketCodec<SystemFlagBatchPacket>() {
  override fun CodecScope<SystemFlagBatchPacket>.body(): SystemFlagBatchPacket {
    val regionId = field(S8) { it.regionId }
    val flags = field(SystemFlagEntryCodec.listPrefixed(U16LE)) { it.flags }
    return SystemFlagBatchPacket(regionId, flags)
  }
}
