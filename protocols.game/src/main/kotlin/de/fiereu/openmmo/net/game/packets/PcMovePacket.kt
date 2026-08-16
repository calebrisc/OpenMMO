package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

/**
 * Moving a monster between storage slots (opcode 0x09 client to server), seven bytes naming where
 * it came from and where it is going.
 *
 * The same opcode carries a chat line the other way, and registering it both ways meant every drag
 * in the box was decoded as chat, read past its end and threw. Chat is only ever sent on 0x08, so
 * this direction is the box's.
 *
 * The container numbering is [de.fiereu.openmmo.common.enums.PokemonContainer]'s own: 0 the pc and
 * 1 the party.
 */
data class PcMovePacket(
    val action: Byte,
    val fromContainer: Byte,
    val fromSlot: Int,
    val toContainer: Byte,
    val toSlot: Int,
)

object PcMovePacketCodec : PacketCodec<PcMovePacket>() {
  override fun CodecScope<PcMovePacket>.body(): PcMovePacket {
    val action = field(S8) { it.action }
    val fromContainer = field(S8) { it.fromContainer }
    val fromSlot = field(S16LE) { it.fromSlot.toShort() }.toInt()
    val toContainer = field(S8) { it.toContainer }
    val toSlot = field(S16LE) { it.toSlot.toShort() }.toInt()
    return PcMovePacket(action, fromContainer, fromSlot, toContainer, toSlot)
  }
}
