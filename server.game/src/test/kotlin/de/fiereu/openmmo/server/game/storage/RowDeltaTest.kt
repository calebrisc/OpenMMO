package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.db.game.tables.references.POKEMON
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import org.jooq.SQLDialect
import org.jooq.impl.DSL

class RowDeltaTest :
    FunSpec({
      test("a new key is changed") {
        rowDelta(mapOf(1 to "a"), mapOf(1 to "a", 2 to "b")) shouldBe
            RowDelta(mapOf(2 to "b"), emptySet())
      }

      test("a different value is changed") {
        rowDelta(mapOf(1 to "a"), mapOf(1 to "b")) shouldBe RowDelta(mapOf(1 to "b"), emptySet())
      }

      test("a dropped key is removed") {
        rowDelta(mapOf(1 to "a", 2 to "b"), mapOf(1 to "a")) shouldBe RowDelta(emptyMap(), setOf(2))
      }

      test("equal versions produce an empty delta") {
        val delta = rowDelta(mapOf(1 to "a"), mapOf(1 to "a"))
        delta.isEmpty shouldBe true
      }

      test("an empty base changes everything") {
        rowDelta(emptyMap<Int, String>(), mapOf(1 to "a")) shouldBe
            RowDelta(mapOf(1 to "a"), emptySet())
      }

      test("a key only table tracks added and dropped keys") {
        rowDelta(setOf("a", "b"), setOf("b", "c")) shouldBe RowDelta(mapOf("c" to Unit), setOf("a"))
      }

      test("an unchanged key only table produces an empty delta") {
        rowDelta(setOf("a"), setOf("a")).isEmpty shouldBe true
      }

      // Arbitrating on the slot as well would let one monster of a swapped pair overwrite the
      // other, since each of them matches where the other still sits.
      test("an upsert arbitrates on the primary key and not on the slot a monster sits in") {
        val dsl = DSL.using(SQLDialect.POSTGRES)
        val sql =
            dsl.upsertOnPrimaryKey(POKEMON, dsl.newRecord(POKEMON).apply { touched(true) }).getSQL()

        val target = sql.substringAfter("on conflict").substringBefore("do update")
        target shouldContain "id"
        target shouldNotContain "container_slot"
        target shouldNotContain "owner_id"
      }
    })
