package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.typechart.TypeChart
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class TournamentServiceTest :
    FunSpec({
      class Fixture(scope: CoroutineScope) {
        val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), scope)
        val sessions = SessionRegistry()
        val interest = InterestManager()
        val registry = BattleRegistry()
        val moves = MoveRegistry()
        val duels =
            DuelService(
                store,
                registry,
                TurnEngine(moves, TypeChart()),
                BattlePacketEmitter(interest),
                SpeciesRegistry(),
                interest,
                sessions,
                ItemRegistry(),
            )
        val tournaments = TournamentService(store, sessions, duels)

        suspend fun player(name: String): Long {
          val created = store.createCharacter(1, name, CharacterGender.MALE, Region.HOENN)
          val id = created.info.id
          store.addPokemon(id, monster(id))
          val session = FakeSession(id)
          sessions.register(session)
          sessions.bindCharacter(session, id)
          return id
        }

        /**
         * Settles every match currently running. Dropping out loses a duel, which is the shortest
         * way to hand a bracket a decided result without playing the turns out.
         */
        fun decideRunningMatches() {
          val running =
              sessions.onlineCharacterIds().mapNotNull { registry.byChar(it) }.distinctBy {
                it.battleId
              }
          running.forEach { battle -> duels.onDisconnect(battle.duel!!.charId) }
        }
      }

      test("a tournament needs at least two players") {
        runTest {
          val fx = Fixture(backgroundScope)
          val ash = fx.player("Ash")
          fx.tournaments.join(ash)

          fx.tournaments.start() shouldContain "at least"
        }
      }

      test("signing up twice is refused and reported") {
        runTest {
          val fx = Fixture(backgroundScope)
          val ash = fx.player("Ash")
          fx.tournaments.join(ash)
          fx.tournaments.join(ash) shouldBe "You are already signed up."
        }
      }

      test("four players resolve to one champion over two rounds") {
        runTest {
          val fx = Fixture(backgroundScope)
          val ids = listOf("Ash", "Gary", "Brock", "Misty").map { fx.player(it) }
          ids.forEach { fx.tournaments.join(it) }

          fx.tournaments.start() shouldBe "Tournament started."
          // Two matches open the bracket, and each one settled sends its winner through.
          fx.decideRunningMatches()
          // The semi-finals leave two, whose match decides the tournament.
          fx.decideRunningMatches()

          fx.tournaments.describe(ids[0]) shouldContain "No tournament"
        }
      }

      test("an odd bracket gives somebody a bye instead of stranding them") {
        runTest {
          val fx = Fixture(backgroundScope)
          val ids = listOf("Ash", "Gary", "Brock").map { fx.player(it) }
          ids.forEach { fx.tournaments.join(it) }

          fx.tournaments.start()

          // One match runs and the third player waits a round rather than being left unpaired.
          fx.tournaments.describe(ids[0]) shouldContain "Round 1"
        }
      }

      test("nobody can join once it is under way") {
        runTest {
          val fx = Fixture(backgroundScope)
          val ids = listOf("Ash", "Gary").map { fx.player(it) }
          ids.forEach { fx.tournaments.join(it) }
          fx.tournaments.start()

          val late = fx.player("Latecomer")
          fx.tournaments.join(late) shouldBe "The tournament has already started."
        }
      }
    })

private fun monster(ownerId: Long): Pokemon =
    Pokemon(
        id = EntityIdService().newMonsterId(),
        ownerId = ownerId,
        container = PokemonContainer.PARTY,
        containerSlot = 0,
        dexId = 1,
        seed = 0,
        ot = "Trainer",
        nickname = "",
        level = 20,
        hp = 60,
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
