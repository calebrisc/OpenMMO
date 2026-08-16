package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8

/** One monster changing places, six bytes of a [PcMovePacket]. */
data class PcMove(
    val fromContainer: Byte,
    val fromSlot: Int,
    val toContainer: Byte,
    val toSlot: Int,
)

/**
 * Moving monsters between storage slots (opcode 0x09 client to server).
 *
 * The leading byte is a count, not a kind: the box lets a player select several monsters and drag
 * them at once, and each one is six more bytes. Reading it as a single move meant a four-monster
 * drag arrived with eighteen bytes left over, and a codec that cannot finish its packet drops it,
 * so the server never heard the move at all while the client had already drawn it. A single drag
 * sends a count of one and happens to fit the old seven-byte reading, which is why one monster at a
 * time always worked and several never did.
 *
 * The same opcode carries a chat line the other way, and registering it both ways meant every drag
 * in the box was decoded as chat, read past its end and threw. Chat is only ever sent on 0x08, so
 * this direction is the box's.
 *
 * The container numbering is [de.fiereu.openmmo.common.enums.PokemonContainer]'s own: 0 the pc and
 * 1 the party.
 */
data class PcMovePacket(val moves: List<PcMove>)

object PcMovePacketCodec : PacketCodec<PcMovePacket>() {
  override fun CodecScope<PcMovePacket>.body(): PcMovePacket {
    val count = field(U8) { it.moves.size }
    val moves =
        List(count) { i ->
          val fromContainer = field(S8) { it.moves[i].fromContainer }
          val fromSlot = field(S16LE) { it.moves[i].fromSlot.toShort() }.toInt()
          val toContainer = field(S8) { it.moves[i].toContainer }
          val toSlot = field(S16LE) { it.moves[i].toSlot.toShort() }.toInt()
          PcMove(fromContainer, fromSlot, toContainer, toSlot)
        }
    return PcMovePacket(moves)
  }
}
