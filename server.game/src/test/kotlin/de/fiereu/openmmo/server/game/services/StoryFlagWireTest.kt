package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.util.zip.Inflater

private fun inflate(data: ByteArray): ByteArray {
  val inflater = Inflater()
  inflater.setInput(data)
  val out = ByteArrayOutputStream()
  val buffer = ByteArray(256)
  while (!inflater.finished()) out.write(buffer, 0, inflater.inflate(buffer))
  inflater.end()
  return out.toByteArray()
}

private fun bitsSet(data: ByteArray): List<Int> = buildList {
  data.forEachIndexed { index, byte ->
    for (bit in 0 until 8) if (byte.toInt() and (1 shl bit) != 0) add(index * 8 + bit)
  }
}

/**
 * The split a live server keeps: everything below 0x800 in the table, everything above it on the
 * system flag packets. Reading a low flag off a packet of its own is what the client refused.
 */
class StoryFlagWireTest :
    FunSpec({
      test("a system flag rides the batch and a story flag does not") {
        val batch =
            StoryClientState.systemFlags(
                0, setOf(KantoFlags.FLAG_SYS_POKEDEX_GET, KantoFlags.FLAG_GOT_POTION_ON_ROUTE_1))

        batch.regionId shouldBe 0.toByte()
        batch.flags.size shouldBe 1
        (batch.flags.single().flagId.toInt() >= 0x800) shouldBe true
      }

      test("a region the player has never been to still answers, with nothing in it") {
        StoryClientState.systemFlags(1, setOf(KantoFlags.FLAG_SYS_POKEDEX_GET))
            .flags
            .shouldBeEmpty()
      }

      test("the table carries a bit per story flag and leaves the system ones out") {
        val groups =
            StoryClientState.flagTable(
                setOf(KantoFlags.FLAG_GOT_POTION_ON_ROUTE_1, KantoFlags.FLAG_SYS_POKEDEX_GET))

        // Six groups, as every captured table carries, however few of them hold anything.
        groups.size shouldBe 6
        bitsSet(inflate(groups[0])).size shouldBe 1
        groups.drop(1).forEach { it.size shouldBe 0 }
      }

      test("the table is trimmed to the last flag that is set, the way captures are") {
        val groups = StoryClientState.flagTable(setOf(KantoFlags.FLAG_GOT_POTION_ON_ROUTE_1))
        val kanto = inflate(groups[0])
        val highest = bitsSet(kanto).max()

        // One byte past the last bit, no tail of zeroes: a captured table was 19 bytes for a
        // highest bit of 144.
        kanto.size shouldBe highest / 8 + 1
      }

      test("a flag both regions define lands in each region's own group") {
        val groups =
            StoryClientState.flagTable(
                setOf(KantoFlags.FLAG_GOT_POTION_ON_ROUTE_1, "hoenn/FLAG_RECEIVED_RUNNING_SHOES"))

        bitsSet(inflate(groups[0])).size shouldBe 1
        bitsSet(inflate(groups[1])).size shouldBe 1
      }
    })
