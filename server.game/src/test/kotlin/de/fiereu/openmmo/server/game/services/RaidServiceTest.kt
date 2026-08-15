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
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleRewards
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.storage.LinkStore
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.trainer.TrainerRegistry
import de.fiereu.openmmo.typechart.TypeChart
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class RaidServiceTest :
    FunSpec({
      class Fixture(scope: CoroutineScope) {
        val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), scope)
        val interest = InterestManager()
        val registry = BattleRegistry()
        val sessions = SessionRegistry()
        val species = SpeciesRegistry()
        val moves = MoveRegistry()
        val wildMons = WildMonFactory(species, moves, LearnsetRegistry(), EntityIdService())
        val battles =
            BattleService(
                characterStore = store,
                battles = registry,
                engine = TurnEngine(moves, TypeChart()),
                wildMons = wildMons,
                emitter = BattlePacketEmitter(interest),
                rewards = BattleRewards(),
                moveLearner = MoveLearner(LearnsetRegistry(), moves),
                interestManager = interest,
                speciesRegistry = species,
                moveRegistry = moves,
                trainers = TrainerRegistry(),
                items = ItemRegistry(),
                pokedex = PokedexService(store),
                duels =
                    DuelService(
                        store,
                        registry,
                        TurnEngine(moves, TypeChart()),
                        BattlePacketEmitter(interest),
                        species,
                        interest,
                        sessions,
                        ItemRegistry(),
                    ),
            )
        val raids =
            RaidService(store, battles, sessions, species, wildMons, LinkStore(), registry)

        /** Puts [joiner] into whatever raid [existing] is already in. */
        fun raidsJoin(joiner: Long, existing: Long) {
          raids.enrol(joiner, existing)
        }

        suspend fun player(name: String): Pair<FakeSession, Long> {
          val created = store.createCharacter(1, name, CharacterGender.MALE, Region.HOENN)
          val id = created.info.id
          store.addPokemon(id, monster(id))
          val session = FakeSession(id)
          sessions.register(session)
          sessions.bindCharacter(session, id)
          return session to id
        }
      }

      test("a raid boss carries far more health than the monster it is built from") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, id) = fx.player("Ash")

          fx.raids.start(id, dexId = 143, level = 30) shouldContain "Raid started"

          val boss = fx.registry.byChar(id)!!.opponentMon()
          val ordinary = fx.wildMons.create(143, 30, de.fiereu.openmmo.server.game.battle.BattleRng())!!
          // Eight times the pool, so one player cannot end it in the turns they get.
          (boss.stats.hp > ordinary.hp * 4) shouldBe true
          boss.currentHp shouldBe boss.stats.hp
        }
      }

      test("a raider is told what the boss has left when a teammate hits it") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (ashSession, ashId) = fx.player("Ash")
          val (garySession, garyId) = fx.player("Gary")
          fx.raids.start(ashId, dexId = 143, level = 30)
          // Gary joins the same boss, which is what a squad member would have done.
          fx.battles.startSharedBossBattle(garySession, fx.registry.byChar(ashId)!!.opponentMon())
          fx.raidsJoin(garyId, ashId)
          garySession.sent.clear()

          // Ash lands a hit. The damage happens in his battle, so Gary would otherwise never see it.
          fx.registry.byChar(ashId)!!.opponentMon().currentHp -= 100
          fx.battles.onBattleAction(
              de.fiereu.network.PacketEvent(
                  de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket(
                      0, de.fiereu.openmmo.common.enums.BattleAction.MOVE, 33, 0L, 0),
                  ashSession))

          val told =
              garySession.sent.filterIsInstance<
                  de.fiereu.openmmo.net.game.packets.ChatMessagePacket>()
          told.any { it.message.contains("left") } shouldBe true
        }
      }

      test("a raid whose battle has ended does not block the next one") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, id) = fx.player("Ash")
          fx.raids.start(id, dexId = 143, level = 30)

          // The battle ending is what says the raid is over. Without noticing that, the entry would
          // outlive the fight and every later raid would be refused.
          fx.registry.remove(id)

          fx.raids.start(id, dexId = 143, level = 30) shouldContain "Raid started"
        }
      }

      test("a raid cannot be started twice while its battle is running") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (_, id) = fx.player("Ash")
          fx.raids.start(id, dexId = 143, level = 30)

          fx.raids.start(id, dexId = 143, level = 30) shouldBe "You are already in a raid."
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
        ot = "Ash",
        nickname = "",
        level = 50,
        hp = 999,
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
