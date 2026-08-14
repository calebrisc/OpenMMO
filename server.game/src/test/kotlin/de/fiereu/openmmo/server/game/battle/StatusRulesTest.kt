package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.MoveEffect
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

private val species = SpeciesRegistry()

// Dex numbers whose types carry the immunities.
private const val CHARMANDER = 4
private const val WEEDLE = 13
private const val PIKACHU = 25
private const val LAPRAS = 131
private const val BULBASAUR = 1

class StatusRulesTest :
    FunSpec({
      test("each status move maps to the condition it inflicts") {
        StatusRules.inflicted(MoveEffect.SLEEP) shouldBe StatusCondition.SLEEP
        StatusRules.inflicted(MoveEffect.POISON) shouldBe StatusCondition.POISON
        StatusRules.inflicted(MoveEffect.TOXIC) shouldBe StatusCondition.TOXIC
        StatusRules.inflicted(MoveEffect.PARALYZE) shouldBe StatusCondition.PARALYSIS
        StatusRules.inflicted(MoveEffect.BURN_HIT) shouldBe StatusCondition.BURN
        StatusRules.inflicted(MoveEffect.FREEZE_HIT) shouldBe StatusCondition.FREEZE
        StatusRules.inflicted(MoveEffect.POISON_FANG) shouldBe StatusCondition.TOXIC
        StatusRules.inflicted(MoveEffect.ATTACK_UP) shouldBe null
      }

      test("only the on-hit effects count as secondary") {
        StatusRules.isSecondary(MoveEffect.BURN_HIT).shouldBeTrue()
        StatusRules.isSecondary(MoveEffect.PARALYZE_HIT).shouldBeTrue()
        StatusRules.isSecondary(MoveEffect.SLEEP).shouldBeFalse()
        StatusRules.isSecondary(MoveEffect.TOXIC).shouldBeFalse()
      }

      test("a type shrugs off the condition it is immune to") {
        val charmander = species.get(CHARMANDER)!!
        val pikachu = species.get(PIKACHU)!!
        val lapras = species.get(LAPRAS)!!
        val weedle = species.get(WEEDLE)!!
        val bulbasaur = species.get(BULBASAUR)!!

        StatusRules.isImmune(charmander, StatusCondition.BURN).shouldBeTrue()
        StatusRules.isImmune(pikachu, StatusCondition.PARALYSIS).shouldBeTrue()
        StatusRules.isImmune(lapras, StatusCondition.FREEZE).shouldBeTrue()
        // Weedle is Bug and Poison, so neither kind of poison touches it.
        StatusRules.isImmune(weedle, StatusCondition.POISON).shouldBeTrue()
        StatusRules.isImmune(weedle, StatusCondition.TOXIC).shouldBeTrue()

        StatusRules.isImmune(charmander, StatusCondition.PARALYSIS).shouldBeFalse()
        StatusRules.isImmune(bulbasaur, StatusCondition.SLEEP).shouldBeFalse()
        // Nothing is immune to sleep by type.
        StatusRules.isImmune(lapras, StatusCondition.SLEEP).shouldBeFalse()
      }

      test("poison takes an eighth and burn a sixteenth") {
        StatusRules.endOfTurnDamage(StatusCondition.POISON, maxHp = 80, toxicCounter = 0) shouldBe
            10
        StatusRules.endOfTurnDamage(StatusCondition.BURN, maxHp = 80, toxicCounter = 0) shouldBe 5
        StatusRules.endOfTurnDamage(StatusCondition.SLEEP, maxHp = 80, toxicCounter = 0) shouldBe 0
        StatusRules.endOfTurnDamage(StatusCondition.NONE, maxHp = 80, toxicCounter = 0) shouldBe 0
      }

      test("toxic damage climbs every turn") {
        val damage =
            (1..4).map {
              StatusRules.endOfTurnDamage(StatusCondition.TOXIC, 160, toxicCounter = it)
            }
        damage shouldBe listOf(10, 20, 30, 40)
      }

      test("chip damage never rounds down to nothing") {
        StatusRules.endOfTurnDamage(StatusCondition.POISON, maxHp = 4, toxicCounter = 0) shouldBe 1
        StatusRules.endOfTurnDamage(StatusCondition.BURN, maxHp = 4, toxicCounter = 0) shouldBe 1
        StatusRules.endOfTurnDamage(StatusCondition.TOXIC, maxHp = 4, toxicCounter = 1) shouldBe 1
      }

      test("the wire byte follows the decomp status1 bit layout") {
        StatusRules.wireValue(StatusCondition.NONE, 0) shouldBe 0.toByte()
        StatusRules.wireValue(StatusCondition.POISON, 0) shouldBe 0x08.toByte()
        StatusRules.wireValue(StatusCondition.BURN, 0) shouldBe 0x10.toByte()
        StatusRules.wireValue(StatusCondition.FREEZE, 0) shouldBe 0x20.toByte()
        StatusRules.wireValue(StatusCondition.PARALYSIS, 0) shouldBe 0x40.toByte()
        StatusRules.wireValue(StatusCondition.TOXIC, 0) shouldBe 0x80.toByte()
        // Sleep carries its remaining turns in the low three bits, never zero.
        StatusRules.wireValue(StatusCondition.SLEEP, 3) shouldBe 3.toByte()
        StatusRules.wireValue(StatusCondition.SLEEP, 0) shouldBe 1.toByte()
        StatusRules.wireValue(StatusCondition.SLEEP, 99) shouldBe 7.toByte()
      }
    })
