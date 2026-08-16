package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private const val KANTO = 0
private const val ABRA = 63
private const val MR_MIME = 122
private const val KADABRA = 64

/**
 * Reyley's trade, which stands in for all thirteen: they share one implementation and differ only
 * in their text and their row of the table.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class InGameTradeTest :
    FunSpec({
      suspend fun harness(
          scope: kotlinx.coroutines.CoroutineScope
      ): Triple<CharacterStore, StoryPlayerService, Long> {
        val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), scope)
        val player =
            StoryPlayerService(
                store,
                WildMonFactory(
                    SpeciesRegistry(), MoveRegistry(), LearnsetRegistry(), EntityIdService()),
                SpeciesRegistry(),
                MoveRegistry(),
                ItemRegistry(),
            )
        val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
        return Triple(store, player, charId)
      }

      test("the trade takes the species asked for and leaves its own in the same slot") {
        runTest {
          val (store, player, charId) = harness(backgroundScope)
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          // A starter first, so the Abra is not in slot zero and the slot can be checked.
          player.givePokemon(session, state, KADABRA, 30, emptyList())
          val abra = player.givePokemon(session, state, ABRA, 17, emptyList())!!

          val received = player.tradePokemon(session, state, InGameTrades.MR_MIME)!!

          received.dexId shouldBe MR_MIME
          // Level for level, off the monster handed over.
          received.level shouldBe abra.level
          received.level shouldBe 17.toByte()
          // Same place in the party, and the party is no longer or shorter for it.
          received.containerSlot shouldBe abra.containerSlot
          store.getCharacter(charId)!!.pokemon.size shouldBe 2
          store.getCharacter(charId)!!.pokemon.none { it.dexId == ABRA } shouldBe true
        }
      }

      test("the traded monster carries the table's own trainer and IVs, not the player's") {
        runTest {
          val (_, player, charId) = harness(backgroundScope)
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          player.givePokemon(session, state, ABRA, 40, emptyList())

          val received = player.tradePokemon(session, state, InGameTrades.MR_MIME)!!

          received.nickname shouldBe "MIMIEN"
          received.ot shouldBe "REYLEY"
          received.ot shouldNotBe "Red"
          received.iVs.hp shouldBe 20
          received.iVs.atk shouldBe 15
          received.iVs.def shouldBe 17
          received.iVs.spd shouldBe 24
          received.iVs.spAtk shouldBe 23
          received.iVs.spDef shouldBe 22
        }
      }

      test("two players who do the same trade get the same monster") {
        runTest {
          val (_, player, charId) = harness(backgroundScope)
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          player.givePokemon(session, state, ABRA, 20, emptyList())
          val first = player.tradePokemon(session, state, InGameTrades.MR_MIME)!!

          val (_, otherPlayer, otherId) = harness(backgroundScope)
          val otherSession = FakeSession(characterId = otherId, regionId = KANTO)
          val otherState = otherSession.attributes[PLAYER_STATE]!!
          otherPlayer.givePokemon(otherSession, otherState, ABRA, 20, emptyList())
          val second = otherPlayer.tradePokemon(otherSession, otherState, InGameTrades.MR_MIME)!!

          // The personality is fixed by the table, so the nature that falls out of it is too.
          second.seed shouldBe first.seed
          second.nature shouldBe first.nature
          second.iVs shouldBe first.iVs
        }
      }

      test("a party without the requested species trades nothing away") {
        runTest {
          val (store, player, charId) = harness(backgroundScope)
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          // Kadabra is what an Abra becomes, and is not what Reyley asked for.
          player.givePokemon(session, state, KADABRA, 20, emptyList())
          val before = store.getCharacter(charId)!!.pokemon.map { it.id to it.dexId }

          player.tradePokemon(session, state, InGameTrades.MR_MIME).shouldBeNull()

          store.getCharacter(charId)!!.pokemon.map { it.id to it.dexId } shouldBe before
        }
      }
    })
