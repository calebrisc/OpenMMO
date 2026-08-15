package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class TradeServiceTest :
    FunSpec({
      class Fixture(scope: CoroutineScope) {
        val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), scope)
        val sessions = SessionRegistry()
        val trades = TradeService(store, sessions)

        suspend fun player(name: String, dexId: Int): Triple<FakeSession, Long, List<Pokemon>> {
          val created = store.createCharacter(1, name, CharacterGender.MALE, Region.HOENN)
          val id = created.info.id
          // Two each, since nobody may trade away their last monster.
          val first = monster(id, dexId, 0)
          val second = monster(id, dexId + 1, 1)
          store.addPokemon(id, first)
          store.addPokemon(id, second)
          val session = FakeSession(id)
          sessions.register(session)
          sessions.bindCharacter(session, id)
          return Triple(session, id, listOf(first, second))
        }

        fun partyOf(charId: Long): List<Pokemon> =
            store.getCharacter(charId)?.pokemon?.toList() ?: emptyList()
      }

      test("a confirmed trade moves one monster each way and duplicates nothing") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (aSession, aId, aMons) = fx.player("Ash", 1)
          val (bSession, bId, bMons) = fx.player("Gary", 100)

          fx.trades.invite(aId, "Gary")
          fx.trades.accept(bSession, bId)
          fx.trades.offer(aId, 1)
          fx.trades.offer(bId, 1)
          fx.trades.confirm(aId)
          fx.trades.confirm(bId)

          val ashParty = fx.partyOf(aId)
          val garyParty = fx.partyOf(bId)
          // Each keeps two: the one they held back and the one they were given.
          ashParty.size shouldBe 2
          garyParty.size shouldBe 2
          ashParty.any { it.id == bMons[0].id } shouldBe true
          ashParty.any { it.id == aMons[0].id } shouldBe false
          garyParty.any { it.id == aMons[0].id } shouldBe true
          garyParty.any { it.id == bMons[0].id } shouldBe false
          // The monster that moved belongs to its new owner, or it would not survive a reload.
          ashParty.first { it.id == bMons[0].id }.ownerId shouldBe aId
          garyParty.first { it.id == aMons[0].id }.ownerId shouldBe bId
        }
      }

      test("confirming twice cannot run the exchange twice") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, aId, aMons) = fx.player("Ash", 1)
          val (bSession, bId, bMons) = fx.player("Gary", 100)

          fx.trades.invite(aId, "Gary")
          fx.trades.accept(bSession, bId)
          fx.trades.offer(aId, 1)
          fx.trades.offer(bId, 1)
          fx.trades.confirm(aId)
          fx.trades.confirm(bId)
          // An impatient second confirm must not start a second exchange over the same two
          // monsters, which is how one of them would be duplicated or lost.
          fx.trades.confirm(aId) shouldBe "You are not trading."

          fx.partyOf(aId).size shouldBe 2
          fx.partyOf(bId).size shouldBe 2
          fx.partyOf(aId).count { it.id == bMons[0].id } shouldBe 1
          fx.partyOf(bId).count { it.id == aMons[0].id } shouldBe 1
        }
      }

      test("one confirmation is not enough to move anything") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, aId, aMons) = fx.player("Ash", 1)
          val (bSession, bId, _) = fx.player("Gary", 100)

          fx.trades.invite(aId, "Gary")
          fx.trades.accept(bSession, bId)
          fx.trades.offer(aId, 1)
          fx.trades.offer(bId, 1)
          fx.trades.confirm(aId)

          fx.partyOf(aId).any { it.id == aMons[0].id } shouldBe true
        }
      }

      test("changing the offer withdraws both confirmations") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, aId, aMons) = fx.player("Ash", 1)
          val (bSession, bId, _) = fx.player("Gary", 100)

          fx.trades.invite(aId, "Gary")
          fx.trades.accept(bSession, bId)
          fx.trades.offer(aId, 1)
          fx.trades.offer(bId, 1)
          fx.trades.confirm(aId)
          // Ash swaps what is on the table after confirming, so Gary's own confirm must not be
          // enough to complete a trade he has not seen.
          fx.trades.offer(aId, 2)
          fx.trades.confirm(bId)

          fx.partyOf(aId).any { it.id == aMons[0].id } shouldBe true
          fx.partyOf(aId).size shouldBe 2
        }
      }

      test("nobody can trade away their last monster") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, aId, aMons) = fx.player("Ash", 1)
          val (bSession, bId, _) = fx.player("Gary", 100)
          fx.store.removePokemon(aId, aMons[1].id)

          fx.trades.invite(aId, "Gary")
          fx.trades.accept(bSession, bId)
          fx.trades.offer(aId, 1) shouldBe "You cannot trade away your last monster."
          fx.partyOf(aId).size shouldBe 1
        }
      }

      test("a disconnect ends the trade for the other player too") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, aId, _) = fx.player("Ash", 1)
          val (bSession, bId, _) = fx.player("Gary", 100)
          fx.trades.invite(aId, "Gary")
          fx.trades.accept(bSession, bId)

          fx.trades.onDisconnect(aId)

          fx.trades.confirm(bId) shouldBe "You are not trading."
        }
      }

      test("removing a monster hands back exactly what it took") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, aId, aMons) = fx.player("Ash", 1)

          val taken = fx.store.removePokemon(aId, aMons[0].id)

          taken shouldNotBe null
          taken!!.id shouldBe aMons[0].id
          fx.partyOf(aId).any { it.id == aMons[0].id } shouldBe false
          // A second removal finds nothing rather than reporting a phantom success.
          fx.store.removePokemon(aId, aMons[0].id) shouldBe null
        }
      }
    })

private fun monster(ownerId: Long, dexId: Int, slot: Short): Pokemon =
    Pokemon(
        id = EntityIdService().newMonsterId(),
        ownerId = ownerId,
        container = PokemonContainer.PARTY,
        containerSlot = slot,
        dexId = dexId,
        seed = 0,
        ot = "Trainer",
        nickname = "",
        level = 10,
        hp = 30,
        xp = 0,
        eVs = EVs(),
        iVs = IVs(),
        moves = listOf(PokemonMove(33, 35), PokemonMove(0, 0), PokemonMove(0, 0), PokemonMove(0, 0)),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.now(),
    )
