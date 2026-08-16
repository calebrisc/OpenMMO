package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

/**
 * One story flag below `FLAG_SYS_` changing mid-session, for the region it belongs to.
 *
 * Read off a live server: a player pushing their way through Seafoam Islands produced `00 45 00
 * FF`, `00 4A 00 FF` and `00 4D 00 FF`, which are `FLAG_HIDE_SEAFOAM_B2F_BOULDER_2`,
 * `_B3F_BOULDER_5` and `_B4F_BOULDER_2` — the raw decomp flag id, not an index into anything. A
 * login clears the temp flags with the same packet, once per region: `00 04 00 00`, `01 04 00 00`,
 * `02 04 00 00`.
 *
 * The flags at `FLAG_SYS_` and above travel on [SystemFlagBatchPacket] and [StoryFlagUpdatePacket]
 * instead; offering a low flag there is answered with "Attempt to set non-client aware flag".
 */
data class WorldFlagSetPacket(
    val region: Byte,
    val flagId: Short,
    val value: Byte,
) {
  companion object {
    /** A set flag. Every capture writes the whole byte, never 1. */
    const val SET: Byte = -1

    /** A cleared flag. */
    const val CLEAR: Byte = 0

    fun of(region: Byte, flagId: Short, enabled: Boolean) =
        WorldFlagSetPacket(region, flagId, if (enabled) SET else CLEAR)
  }
}

object WorldFlagSetPacketCodec : PacketCodec<WorldFlagSetPacket>() {
  override fun CodecScope<WorldFlagSetPacket>.body(): WorldFlagSetPacket {
    val region = field(S8) { it.region }
    val flagId = field(S16LE) { it.flagId }
    val value = field(S8) { it.value }
    return WorldFlagSetPacket(region, flagId, value)
  }
}
