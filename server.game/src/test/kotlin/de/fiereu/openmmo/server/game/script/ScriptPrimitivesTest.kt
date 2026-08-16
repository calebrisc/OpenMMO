package de.fiereu.openmmo.server.game.script

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.MapLoadedAckPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleRewards
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.DuelService
import de.fiereu.openmmo.server.game.services.NpcService
import de.fiereu.openmmo.server.game.services.PokedexService
import de.fiereu.openmmo.server.game.services.ScriptMovementService
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.testsupport.scriptWarpFor
import de.fiereu.openmmo.server.game.testsupport.storyPlayerFor
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.trainer.TrainerRegistry
import de.fiereu.openmmo.typechart.TypeChart
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

private const val KANTO = 0
private const val PALLET_TOWN_BANK = 3
private const val VIRIDIAN_CITY_MAP = 1
private const val LOCALID_VIRIDIAN_POTION_BALL = 8

// Brock: the lowest-level Kanto leader, a sure win for a level fifty starter.
private const val BROCK = 414
private const val TACKLE: Short = 33

@OptIn(ExperimentalCoroutinesApi::class)
class ScriptPrimitivesTest :
    FunSpec({
      test("trainerBattleSingle skips a beaten trainer without consulting the battle service") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val story = StoryService(store)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
          // No battle service on purpose: touching it would throw, so a green run proves the
          // beaten-trainer path never reaches a battle.
          val ctx = ScriptContext(session, state, -1, DialogService(), story, movement)
          ctx.setFlag("kanto/TRAINER_DEFEATED_$BROCK")

          ctx.trainerBattleSingle(BROCK).shouldBeTrue()
          session.sent.shouldBe(emptyList<Any>())
        }
      }

      test("a trainerBattleSingle victory sets the defeated flag and later calls skip the fight") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val story = StoryService(store)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val movement = ScriptMovementService(mapManager, NpcService(mapManager, store), store)
          val interest = InterestManager()
          val registry = BattleRegistry()
          val battles =
              BattleService(
                  characterStore = store,
                  battles = registry,
                  engine = TurnEngine(MoveRegistry(), TypeChart()),
                  wildMons =
                      WildMonFactory(
                          SpeciesRegistry(), MoveRegistry(), LearnsetRegistry(), EntityIdService()),
                  emitter = BattlePacketEmitter(interest),
                  rewards = BattleRewards(),
                  moveLearner = MoveLearner(LearnsetRegistry(), MoveRegistry()),
                  interestManager = interest,
                  speciesRegistry = SpeciesRegistry(),
                  moveRegistry = MoveRegistry(),
                  trainers = TrainerRegistry(),
                  items = ItemRegistry(),
                  pokedex = PokedexService(store),
                  duels =
                      DuelService(
                          store,
                          registry,
                          TurnEngine(MoveRegistry(), TypeChart()),
                          BattlePacketEmitter(interest),
                          SpeciesRegistry(),
                          interest,
                          SessionRegistry(),
                          ItemRegistry(),
                      ),
                  storyPlayer = storyPlayerFor(store),
                  scriptWarp = scriptWarpFor(store),
              )
          val player =
              StoryPlayerService(
                  store,
                  WildMonFactory(
                      SpeciesRegistry(), MoveRegistry(), LearnsetRegistry(), EntityIdService()),
                  SpeciesRegistry(),
                  MoveRegistry(),
                  ItemRegistry(),
              )
          val ctx =
              ScriptContext(
                  session,
                  state,
                  -1,
                  DialogService(),
                  story,
                  movement,
                  player = player,
                  battles = battles,
                  characters = store)
          player.givePokemon(session, state, 1, 50, listOf(TACKLE.toInt()))

          var won: Boolean? = null
          val script = launch { won = ctx.trainerBattleSingle(BROCK) }
          advanceUntilIdle()
          var rounds = 0
          while (registry.byChar(charId)?.pendingResult == null && rounds < 30) {
            battles.onBattleAction(
                PacketEvent(BattleActionSelectPacket(0, BattleAction.MOVE, TACKLE, 0L, 0), session))
            rounds += 1
          }
          battles.onClientReady(PacketEvent(MapLoadedAckPacket(), session))
          advanceUntilIdle()
          script.join()

          won.shouldBe(true)
          ctx.isTrainerDefeated(BROCK).shouldBeTrue()
          // The rematch is skipped without another battle.
          ctx.trainerBattleSingle(BROCK).shouldBeTrue()
          registry.byChar(charId) shouldBe null
        }
      }

      test("findItem grants once, hides the ball, and refuses a second grab") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val story = StoryService(store)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          store.updatePosition(charId, 0, 0, PALLET_TOWN_BANK.toByte(), VIRIDIAN_CITY_MAP.toByte())
          val session =
              FakeSession(
                  characterId = charId,
                  regionId = KANTO,
                  bankId = PALLET_TOWN_BANK,
                  mapId = VIRIDIAN_CITY_MAP)
          val state = session.attributes[PLAYER_STATE]!!
          val mapManager = MapManager()
          val npcs = NpcService(mapManager, store)
          val movement = ScriptMovementService(mapManager, npcs, store)
          val player =
              StoryPlayerService(
                  store,
                  WildMonFactory(
                      SpeciesRegistry(), MoveRegistry(), LearnsetRegistry(), EntityIdService()),
                  SpeciesRegistry(),
                  MoveRegistry(),
                  ItemRegistry(),
              )
          val ballEntityId =
              npcs.entityIdFor(
                  KANTO, PALLET_TOWN_BANK, VIRIDIAN_CITY_MAP, LOCALID_VIRIDIAN_POTION_BALL)
          val ctx =
              ScriptContext(
                  session,
                  state,
                  ballEntityId,
                  DialogService(),
                  story,
                  movement,
                  player = player,
                  characters = store)

          ctx.findItem(Items.POTION)
          val after = store.getCharacter(charId)!!.items.toMap()

          ctx.findItem(Items.POTION)
          store.getCharacter(charId)!!.items.toMap() shouldBe after
          ctx.isFlagSet("kanto/FLAG_HIDE_VIRIDIAN_CITY_POTION").shouldBeTrue()
          after.values.sum() shouldBe 1
        }
      }
    })
