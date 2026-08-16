package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.net.game.packets.PlayerVariableEntry
import de.fiereu.openmmo.net.game.packets.StoryFlagUpdatePacket
import de.fiereu.openmmo.net.game.packets.SystemFlagBatchPacket
import de.fiereu.openmmo.net.game.packets.SystemFlagEntry
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars
import java.io.ByteArrayOutputStream
import java.util.zip.Deflater

/** Converts persisted story keys to client ids. */
internal object StoryClientState {

  /**
   * The system flags of one region, which a login sends on a packet of their own.
   *
   * A live server only ever puts flags at [SYSTEM_FLAG_START] and above on this packet, and only
   * ever sends a single flag update afterwards for one of those too. Everything below lives in the
   * flag table, which is why pushing a low flag one at a time was refused every time: it was being
   * offered on the wrong packet.
   */
  fun systemFlags(regionId: Byte, flags: Collection<String>): SystemFlagBatchPacket =
      SystemFlagBatchPacket(
          regionId,
          flags
              .mapNotNull { flagId(regionId, it) }
              .filter { it >= SYSTEM_FLAG_START }
              .sorted()
              .map { SystemFlagEntry(it.toShort(), 1) },
      )

  /**
   * The flag table: one bitmap per region of every flag below [SYSTEM_FLAG_START] that is set.
   *
   * Captured tables carry six groups, each a zlib stream trimmed to the last byte holding anything,
   * and a region the player has never been to is present but empty. This server sent four groups
   * copied out of one capture, so every player wore a stranger's progress and none of their own
   * hidden items, moved NPCs or opened doors were ever known to the client.
   */
  fun flagTable(flags: Collection<String>): List<ByteArray> =
      (0 until FLAG_TABLE_GROUPS).map { region ->
        val ids = flags.mapNotNull { flagId(region.toByte(), it) }.filter { it < SYSTEM_FLAG_START }
        if (ids.isEmpty()) ByteArray(0) else deflate(bitmap(ids))
      }

  /** Bit [id] of byte [id] / 8, counting from the low bit, as the captured tables are packed. */
  private fun bitmap(ids: List<Int>): ByteArray {
    val bytes = ByteArray(ids.max() / 8 + 1)
    for (id in ids) {
      val index = id / 8
      bytes[index] = (bytes[index].toInt() or (1 shl (id % 8))).toByte()
    }
    return bytes
  }

  private fun deflate(data: ByteArray): ByteArray {
    val deflater = Deflater()
    deflater.setInput(data)
    deflater.finish()
    val out = ByteArrayOutputStream()
    val buffer = ByteArray(256)
    while (!deflater.finished()) out.write(buffer, 0, deflater.deflate(buffer))
    deflater.end()
    return out.toByteArray()
  }

  fun flags(regionId: Byte, flags: Collection<String>): List<StoryFlagUpdatePacket> =
      flags.mapNotNull { flagUpdate(regionId, it, enabled = true) }.sortedBy { it.flagId }

  fun flagUpdate(regionId: Byte, key: String, enabled: Boolean): StoryFlagUpdatePacket? {
    val id = flagId(regionId, key) ?: return null
    return StoryFlagUpdatePacket(regionId, id, enabled)
  }

  fun variables(regionId: Byte, vars: Map<String, Int>): List<PlayerVariableEntry> =
      vars
          .mapNotNull { (key, value) ->
            val id = varId(regionId, key) ?: return@mapNotNull null
            if (id !in GBA_VARS_START..GBA_VARS_END) return@mapNotNull null
            PlayerVariableEntry((id - GBA_VARS_START).toByte(), value.toShort())
          }
          .sortedBy { it.key.toInt() and 0xff }

  /**
   * Every var in the GBA range, zeros included. A var back at 0 is stored as absent, so sending
   * only what is stored would leave the client holding the old value. There is no var equivalent of
   * [de.fiereu.openmmo.net.game.packets.WorldFlagTableResetPacket] to clear it first.
   */
  fun allVariables(regionId: Byte, vars: Map<String, Int>): List<PlayerVariableEntry> {
    val byId = variables(regionId, vars).associate { it.key.toInt() and 0xff to it.value }
    return (0..(GBA_VARS_END - GBA_VARS_START)).map {
      PlayerVariableEntry(it.toByte(), byId[it] ?: 0)
    }
  }

  private fun flagId(regionId: Byte, key: String): Int? =
      when (regionId.toInt()) {
        KANTO_REGION -> KantoFlags.numericId(key)
        HOENN_REGION -> HoennFlags.numericId(key)
        else -> null
      }

  private fun varId(regionId: Byte, key: String): Int? =
      when (regionId.toInt()) {
        KANTO_REGION -> KantoVars.numericId(key)
        HOENN_REGION -> HoennVars.numericId(key)
        else -> null
      }

  /** FLAG_SYS_ begins here. Below it is the table's, above it is the system flag packets'. */
  private const val SYSTEM_FLAG_START = 0x800

  /** Every captured table has six, however few of them hold anything. */
  private const val FLAG_TABLE_GROUPS = 6

  private const val KANTO_REGION = 0
  private const val HOENN_REGION = 1
  private const val GBA_VARS_START = 0x4000
  private const val GBA_VARS_END = 0x40ff
}
