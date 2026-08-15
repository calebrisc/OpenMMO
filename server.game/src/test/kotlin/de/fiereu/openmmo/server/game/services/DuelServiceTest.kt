package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import java.time.LocalDateTime
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacket
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.typechart.TypeChart
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private const val TACKLE: Short = 33

@OptIn(ExperimentalCoroutinesApi::class)
class DuelServiceTest :
    FunSpec({
      /** Two players, each holding a party, and the duel service wired over both. */
      class Fixture(scope: CoroutineScope) {
        val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), scope)
        val interest = InterestManager()
        val registry = BattleRegistry()
        val emitter = BattlePacketEmitter(interest)
        val duels =
            DuelService(
                store,
                registry,
                TurnEngine(MoveRegistry(), TypeChart()),
                emitter,
                SpeciesRegistry(),
                interest,
                SessionRegistry(),
                ItemRegistry(),
            )

        suspend fun player(name: String): Pair<FakeSession, Long> {
          val created = store.createCharacter(1, name, CharacterGender.MALE, Region.HOENN)
          val id = created.info.id
          store.addPokemon(id, bulbasaurFor(id))
          store.addPokemon(id, bulbasaurFor(id).copy(containerSlot = 1))
          return FakeSession(id) to id
        }
      }

      test("both players are the local side of their own screen") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (aSession, aId) = fx.player("Ash")
          val (bSession, bId) = fx.player("Gary")

          fx.duels.start(aSession, aId, "Ash", bSession, bId, "Gary") shouldBe true

          // Neither view can be broadcast: each player has to see their own party as the near side,
          // so the challenged player receives the same battle mirrored.
          val aField = aSession.sent.filterIsInstance<BattleFieldStatePacket>()
          val bField = bSession.sent.filterIsInstance<BattleFieldStatePacket>()
          aField.shouldNotBeEmpty()
          bField.shouldNotBeEmpty()
          aField.last().playerName shouldBe "Ash"
          aField.last().playerId shouldBe aId
          bField.last().playerName shouldBe "Gary"
          bField.last().playerId shouldBe bId
        }
      }

      test("a turn waits for both players before it resolves") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (aSession, aId) = fx.player("Ash")
          val (bSession, bId) = fx.player("Gary")
          fx.duels.start(aSession, aId, "Ash", bSession, bId, "Gary")
          val battle = fx.registry.byChar(aId)!!
          val startingHp = battle.opponentMon().currentHp

          fx.duels.onAction(battle, aId, move(TACKLE)) shouldBe true
          // One player choosing settles nothing, so the other's monster cannot have been hit yet.
          battle.opponentMon().currentHp shouldBe startingHp

          fx.duels.onAction(battle, bId, move(TACKLE)) shouldBe true
          battle.turn shouldBe 2
        }
      }

      test("a switch reaches each player with the side byte from their own point of view") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (aSession, aId) = fx.player("Ash")
          val (bSession, bId) = fx.player("Gary")
          fx.duels.start(aSession, aId, "Ash", bSession, bId, "Gary")
          val battle = fx.registry.byChar(aId)!!
          aSession.sent.clear()
          bSession.sent.clear()

          // Ash switches while Gary attacks, so the turn carries one switch and one move.
          fx.duels.onAction(battle, aId, switchTo(1))
          fx.duels.onAction(battle, bId, move(TACKLE))

          battle.activeSlot shouldBe 1
          // The same switch is the near side to the player who made it and the far side to the one
          // watching. One broadcast would have put it on the wrong half of somebody's screen.
          aSession.sent.filterIsInstance<BattleSwitchInPacket>().last().side shouldBe 0.toByte()
          bSession.sent.filterIsInstance<BattleSwitchInPacket>().last().side shouldBe 1.toByte()
        }
      }

      test("an ordinary battle is left to the ordinary path") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (aSession, aId) = fx.player("Ash")
          val (bSession, bId) = fx.player("Gary")
          fx.duels.start(aSession, aId, "Ash", bSession, bId, "Gary")
          val duel = fx.registry.byChar(aId)!!
          duel.duel = null

          // Without a duel side the service declines the action rather than swallowing it.
          fx.duels.onAction(duel, aId, move(TACKLE)) shouldBe false
        }
      }
    })

private fun move(id: Short) = BattleActionSelectPacket(0, BattleAction.MOVE, id, 0L, 0)

private fun switchTo(slot: Int) =
    BattleActionSelectPacket(0, BattleAction.SWITCH, slot.toShort(), 0L, 0)

private fun bulbasaurFor(ownerId: Long): Pokemon =
    Pokemon(
        id = EntityIdService().newMonsterId(),
        ownerId = ownerId,
        container = PokemonContainer.PARTY,
        containerSlot = 0,
        dexId = 1,
        seed = 0,
        ot = "Trainer",
        nickname = "",
        level = 50,
        hp = 999,
        xp = 0,
        eVs = EVs(),
        iVs = IVs(),
        moves =
            listOf(
                PokemonMove(TACKLE, 35), PokemonMove(0, 0), PokemonMove(0, 0), PokemonMove(0, 0)),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.now(),
    )
