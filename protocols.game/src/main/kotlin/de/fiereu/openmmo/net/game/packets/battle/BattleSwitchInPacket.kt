package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

/**
 * Brings a monster onto the field after a switch (opcode 0x35).
 *
 * The two bytes after the side were read as the new and previous party slots, and are not: across
 * every capture they carry 1 and 0 when a full block follows and 0 and 1 when it does not, whatever
 * monster is coming out. They say which of the two shapes this is. The party position rides in the
 * block, and writing it into the flag instead is what killed a live client on a switch to the fifth
 * party member: the client only ever sees a 1 there.
 *
 * A monster coming out for the first time carries its full block, the same layout as the field
 * state. One already seen active is sent as just its 21-byte active detail, and the decode then
 * fills only species, level and gender.
 */
data class BattleSwitchInPacket(
    val mon: BattleMonBlock,
    val fullBlock: Boolean,
    /** 0 sends out one of the player's own, 1 one of the opponent's. */
    val side: Byte = 0,
)

private val NO_MOVES = List(BattleMonBlock.MOVE_SLOTS) { 0.toShort() }

private data class SwitchInMon(val mon: BattleMonBlock, val fullBlock: Boolean)

// Whether the full block precedes the active detail is not flagged on the wire. Only the
// remaining length tells the two shapes apart, so this section cannot go through the scope.
private val SwitchInMonCodec: Codec<SwitchInMon> =
    object : Codec<SwitchInMon> {
      override fun read(buf: ReadBuffer): SwitchInMon {
        val fullBlock = buf.remaining() > BattleActiveDetailCodec.WIRE_SIZE
        val block = if (fullBlock) BattleFullBlockCodec.read(buf) else null
        if (fullBlock) S8.read(buf) // last-block flag before the active detail
        val active = BattleActiveDetailCodec.read(buf)
        val mon =
            block
                ?: BattleMonBlock(
                    slot = active.slot,
                    entityId = 0,
                    species = active.species,
                    level = active.level,
                    gender = active.gender,
                    abilityId = 0,
                    maxHp = 0,
                    currentHp = 0,
                    movesPresent = false,
                    moveIds = NO_MOVES,
                )
        return SwitchInMon(mon, fullBlock)
      }

      override fun write(buf: WriteBuffer, value: SwitchInMon) {
        if (value.fullBlock) {
          BattleFullBlockCodec.write(buf, value.mon)
          S8.write(buf, 1) // last-block flag before the active detail
        }
        BattleActiveDetailCodec.write(buf, BattleActiveDetail.of(value.mon.slot, value.mon))
      }
    }

object BattleSwitchInPacketCodec : PacketCodec<BattleSwitchInPacket>() {
  override fun CodecScope<BattleSwitchInPacket>.body(): BattleSwitchInPacket {
    val side = field(S8) { it.side }
    reserved(0)
    // The pair is complementary in every capture, so one of them decides the shape and the other
    // follows it.
    val hasFullBlock = field(U8) { if (it.fullBlock) 1 else 0 } == 1
    field(U8) { if (it.fullBlock) 0 else 1 }
    val section = field(SwitchInMonCodec) { SwitchInMon(it.mon, it.fullBlock) }
    return BattleSwitchInPacket(section.mon, section.fullBlock || hasFullBlock, side)
  }
}
