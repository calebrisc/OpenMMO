package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U16LE

/**
 * Opcode 0x26. A selection made in a menu, optionally aimed at something.
 *
 * Captured live: option 360 with no entity, and option 5017 carrying an id whose low bytes are the
 * monster tag, so the entity is what the option was aimed at rather than who picked it. It is zero
 * when the option stands alone. The codec previously read only the first four of fourteen bytes, so
 * every one of these was dropped undecoded.
 *
 * The two trailing halfwords are constant across every capture so far and their meaning is unknown.
 */
data class DialogOptionPacket(
    val optionId: Int,
    val entityId: Long,
    val flag: Int,
    val trailing: Int,
)

object DialogOptionPacketCodec : PacketCodec<DialogOptionPacket>() {
  override fun CodecScope<DialogOptionPacket>.body(): DialogOptionPacket {
    val optionId = field(U16LE) { it.optionId }
    val entityId = field(S64LE) { it.entityId }
    val flag = field(S16LE) { it.flag.toShort() }.toInt()
    val trailing = field(S16LE) { it.trailing.toShort() }.toInt()
    return DialogOptionPacket(optionId, entityId, flag, trailing)
  }
}
