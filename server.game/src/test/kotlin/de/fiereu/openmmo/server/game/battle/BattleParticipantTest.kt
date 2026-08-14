package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

private val speciesRegistry = SpeciesRegistry()

private fun mon(dexId: Int, entityId: Long, hp: Short = 30): BattleMonState {
  val def = speciesRegistry.get(dexId)!!
  val pokemon =
      Pokemon(
          id = entityId,
          ownerId = 0,
          container = PokemonContainer.PARTY,
          containerSlot = 0,
          dexId = dexId,
          seed = 0,
          ot = "",
          nickname = "",
          level = 20,
          hp = hp,
          xp = 0,
          eVs = EVs(),
          iVs = IVs(),
          moves = List(4) { PokemonMove(0, 0) },
          isShiny = false,
          hasHiddenAbility = false,
          isAlpha = false,
          isSecret = false,
          isFatefulEncounter = false,
          isRaidEncounter = false,
          caughtAt = LocalDateTime.now(),
      )
  return BattleMonState(entityId, def, 0, pokemon, StatCalculator.computeAll(def, pokemon))
}

private fun participant(charId: Long, vararg mons: BattleMonState) =
    BattleParticipant(charId, FakeSession(charId), mons.toList())

class BattleParticipantTest :
    FunSpec({
      test("a single player battle reads exactly as it did before participants existed") {
        val own = mon(1, 100L)
        val wild = mon(19, 900L)
        val battle =
            BattleInstance(1L, 7L, FakeSession(7L), listOf(own), listOf(wild), BattleRng(1))

        battle.coop.shouldBeFalse()
        battle.charId shouldBe 7L
        battle.party shouldBe listOf(own)
        battle.activeMon() shouldBe own
        battle.opponentMon() shouldBe wild
        battle.isPlayerSide(100L).shouldBeTrue()
        battle.isPlayerSide(900L).shouldBeFalse()
      }

      test("the host's active slot is what the battle level accessor reads and writes") {
        val first = mon(1, 100L)
        val second = mon(4, 101L)
        val battle =
            BattleInstance(
                1L, 7L, FakeSession(7L), listOf(first, second), listOf(mon(19, 900L)), BattleRng(1))

        battle.activeSlot = 1

        battle.host.activeSlot shouldBe 1
        battle.activeMon() shouldBe second
      }

      test("a co-op battle keeps each player's party and active monster apart") {
        val aliceMon = mon(1, 100L)
        val bobFirst = mon(4, 200L)
        val bobSecond = mon(7, 201L)
        val alice = participant(1L, aliceMon)
        val bob = participant(2L, bobFirst, bobSecond)
        val battle = BattleInstance(1L, listOf(alice, bob), listOf(mon(143, 900L)), BattleRng(1))

        battle.coop.shouldBeTrue()
        bob.activeSlot = 1

        battle.activeMons() shouldBe listOf(aliceMon, bobSecond)
        battle.participantFor(2L) shouldBe bob
        battle.participantOwning(201L) shouldBe bob
        battle.participantOwning(100L) shouldBe alice
        battle.isPlayerSide(200L).shouldBeTrue()
        // The host accessors still answer for the player who started it.
        battle.charId shouldBe 1L
        battle.activeMon() shouldBe aliceMon
      }

      test("the human side is only beaten once every player is out of monsters") {
        val alice = participant(1L, mon(1, 100L, hp = 0))
        val bob = participant(2L, mon(4, 200L))
        val battle = BattleInstance(1L, listOf(alice, bob), listOf(mon(143, 900L)), BattleRng(1))

        alice.defeated.shouldBeTrue()
        battle.allParticipantsDefeated.shouldBeFalse()

        bob.party.single().currentHp = 0
        battle.allParticipantsDefeated.shouldBeTrue()
      }

      test("the registry finds one co-op battle from any of its players") {
        val registry = BattleRegistry()
        val alice = participant(1L, mon(1, 100L))
        val bob = participant(2L, mon(4, 200L))
        val battle = registry.create(listOf(alice, bob), listOf(mon(143, 900L)), BattleRng(1))

        registry.byChar(1L) shouldBe battle
        registry.byChar(2L) shouldBe battle
        registry.byChar(3L) shouldBe null
      }

      test("removing a co-op battle clears every player, not just the one who left") {
        val registry = BattleRegistry()
        val alice = participant(1L, mon(1, 100L))
        val bob = participant(2L, mon(4, 200L))
        registry.create(listOf(alice, bob), listOf(mon(143, 900L)), BattleRng(1))

        registry.remove(2L)

        registry.byChar(1L) shouldBe null
        registry.byChar(2L) shouldBe null
      }

      test("removing one battle leaves another player's battle alone") {
        val registry = BattleRegistry()
        val solo =
            registry.create(
                1L, FakeSession(1L), listOf(mon(1, 100L)), listOf(mon(19, 900L)), BattleRng(1))
        registry.create(
            2L, FakeSession(2L), listOf(mon(4, 200L)), listOf(mon(19, 901L)), BattleRng(1))

        registry.remove(2L)

        registry.byChar(1L) shouldBe solo
      }
    })
