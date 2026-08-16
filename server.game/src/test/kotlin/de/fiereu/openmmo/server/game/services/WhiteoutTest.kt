package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private const val KANTO = 0
private const val BULBASAUR = 1

/**
 * The heal half of a whiteout, and the point it sends the player back to.
 *
 * The shape here is the archive's whiteout capture: the two deltas that follow the map transition
 * carry restored moves, hp and a cleared faint flag, and no container is resent.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WhiteoutTest :
    FunSpec({
      test("a heal is one entity delta per monster, and no container resend") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
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
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          player.givePokemon(session, state, BULBASAUR, 10, emptyList())
          player.givePokemon(session, state, BULBASAUR, 12, emptyList())
          // Knock both of them out and drain a move, the way a lost battle leaves them.
          store.getCharacter(charId)!!.pokemon.forEach {
            store.updatePokemon(charId, it.copy(hp = 0))
          }
          session.sent.clear()

          player.healParty(session, state)

          val deltas = session.sent.filterIsInstance<BattleEntityDeltaPacket>()
          deltas.size shouldBe 2
          deltas.forEach { delta ->
            // The mask a live server sends is moves, hp and faint together.
            delta.moves.shouldNotBeNull()
            delta.faintFlag shouldBe 0.toByte()
            (delta.currentHp!! > 0) shouldBe true
            // Nothing else rides along.
            delta.experience.shouldBeNull()
            delta.statValues.shouldBeNull()
          }
          // The party container is the bulk load a login does, and is inert mid-session.
          session.sent.filterIsInstance<PokemonContainerPacket>().shouldBeEmpty()
        }
      }

      test("a healed monster comes back with its move pp full") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val moveRegistry = MoveRegistry()
          val player =
              StoryPlayerService(
                  store,
                  WildMonFactory(
                      SpeciesRegistry(), moveRegistry, LearnsetRegistry(), EntityIdService()),
                  SpeciesRegistry(),
                  moveRegistry,
                  ItemRegistry(),
              )
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId, regionId = KANTO)
          val state = session.attributes[PLAYER_STATE]!!
          val given = player.givePokemon(session, state, BULBASAUR, 10, emptyList())!!
          val drained = given.moves.map { it.copy(pp = 0) }
          store.updatePokemon(charId, given.copy(hp = 0, moves = drained))
          session.sent.clear()

          player.healParty(session, state)

          val slots =
              session.sent.filterIsInstance<BattleEntityDeltaPacket>().single().moves!!.slots
          // Four slots always go out, empty ones included, as the capture does.
          slots.size shouldBe 4
          val real = slots.filter { it.first != 0.toShort() }
          real.forEach { (id, pp) -> pp shouldBe moveRegistry.get(id.toInt())!!.pp.toByte() }
        }
      }

      test("a character who has never healed has no place to be sent back to") {
        RespawnPoint.of(emptyMap()).shouldBeNull()
        RespawnPoint.of(mapOf(RespawnPoint.SET to 0, RespawnPoint.REGION to 0)).shouldBeNull()
      }

      test("the healing place is read back as the warp it was recorded from") {
        val warp =
            RespawnPoint.of(
                mapOf(
                    RespawnPoint.SET to 1,
                    RespawnPoint.REGION to 0,
                    RespawnPoint.BANK to 3,
                    RespawnPoint.MAP to 1,
                    RespawnPoint.X to 7,
                    RespawnPoint.Y to 4,
                ))!!

        warp.regionId shouldBe 0.toByte()
        warp.bankId shouldBe 3.toByte()
        warp.mapId shouldBe 1.toByte()
        warp.x shouldBe 7.toShort()
        warp.y shouldBe 4.toShort()
        warp.facing shouldBe Direction.DOWN
      }
    })
