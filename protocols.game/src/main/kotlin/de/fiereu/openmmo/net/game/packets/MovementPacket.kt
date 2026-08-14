package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.U8
import de.fiereu.openmmo.common.enums.Direction

/**
 * A step the player took.
 *
 * [state] is kept whole as well as decoded. Only the direction and the running flag are understood,
 * and the five bits between them are dropped, which is where the bike almost certainly rides: the
 * client binds a bike key and nothing ever comes of it. Keeping the byte lets the server notice a
 * bit it does not know rather than silently discarding it.
 */
data class MovementPacket(
    val x: Int,
    val y: Int,
    val direction: Direction,
    val running: Boolean = false,
    val state: Int = direction.ordinal or (if (running) RUNNING_BIT else 0),
) {
  /** Bits set beyond the direction and the running flag. Zero for an ordinary step. */
  val unknownStateBits: Int
    get() = state and (DIRECTION_MASK or RUNNING_BIT).inv() and 0xFF

  companion object {
    const val DIRECTION_MASK = 0x03
    const val RUNNING_BIT = 0x80
  }
}

object MovementPacketCodec : PacketCodec<MovementPacket>() {
  override fun CodecScope<MovementPacket>.body(): MovementPacket {
    val x = field(S16LE) { it.x.toShort() }
    val y = field(S16LE) { it.y.toShort() }
    val state = field(U8) { it.state }
    val direction = Direction.entries[state and MovementPacket.DIRECTION_MASK]
    val running = state and MovementPacket.RUNNING_BIT != 0
    return MovementPacket(x.toInt(), y.toInt(), direction, running, state)
  }
}
