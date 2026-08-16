package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.PartyMemberSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnPromptPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnReplyPacket
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private const val MEGA_PUNCH = 5
private const val POUND = 1

/**
 * A tutor offers, the player picks from their own party, and the monster learns. There is no
 * decoded way to make the client open its party for a choice, so the pick arrives on the packet it
 * already sends when a party member is selected.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MoveTeachingServiceTest :
    FunSpec({
      fun monster(ownerId: Long, moves: List<Int>) =
          Pokemon(
              id = EntityIdService().newMonsterId(),
              ownerId = ownerId,
              container = PokemonContainer.PARTY,
              containerSlot = 0,
              dexId = 1,
              seed = 7,
              ot = "Ash",
              nickname = "",
              level = 10,
              hp = 20,
              xp = 100,
              eVs = EVs(),
              iVs = IVs(),
              moves = List(4) { PokemonMove((moves.getOrNull(it) ?: 0).toShort(), 20) },
              isShiny = false,
              hasHiddenAbility = false,
              isAlpha = false,
              isSecret = false,
              isFatefulEncounter = false,
              isRaidEncounter = false,
              caughtAt = LocalDateTime.now(),
          )

      test("a monster with a free slot simply learns it") {
        runTest {
          val moves = MoveRegistry()
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val teaching = MoveTeachingService(store, moves, MoveLearner(LearnsetRegistry(), moves))
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.KANTO).info.id
          val mon = monster(id, listOf(POUND))
          store.addPokemon(id, mon)
          val session = FakeSession(id)

          teaching.offer(session, id, MEGA_PUNCH, "a test", spendFlag = "kanto/FLAG_TUTOR_TEST")
          teaching.onPartySelect(PacketEvent(PartyMemberSelectPacket(0, mon.id), session)) shouldBe
              true

          val taught = store.getCharacter(id)!!.pokemon.single()
          taught.moves.map { it.id.toInt() } shouldBe listOf(POUND, MEGA_PUNCH, 0, 0)
          // The tutor is spent by the teaching, not by the offering.
          store.getCharacter(id)!!.storyFlags.contains("kanto/FLAG_TUTOR_TEST") shouldBe true
        }
      }

      test("a full moveset is asked what to drop instead of silently refusing") {
        runTest {
          val moves = MoveRegistry()
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val teaching = MoveTeachingService(store, moves, MoveLearner(LearnsetRegistry(), moves))
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.KANTO).info.id
          val mon = monster(id, listOf(1, 2, 3, 4))
          store.addPokemon(id, mon)
          val session = FakeSession(id)

          teaching.offer(session, id, MEGA_PUNCH, "a test", spendFlag = "kanto/FLAG_TUTOR_TEST")
          teaching.onPartySelect(PacketEvent(PartyMemberSelectPacket(0, mon.id), session))

          val prompt = session.sent.filterIsInstance<MoveLearnPromptPacket>().single()
          prompt.entityId shouldBe mon.id
          prompt.offered shouldBe listOf(MEGA_PUNCH.toShort())
          // Nothing is spent until the player answers the prompt.
          store.getCharacter(id)!!.storyFlags.contains("kanto/FLAG_TUTOR_TEST") shouldBe false

          teaching.onMoveLearnReply(
              PacketEvent(
                  MoveLearnReplyPacket(
                      mon.id, listOf(MEGA_PUNCH.toShort(), 2, 3, 4), listOf(MEGA_PUNCH.toShort())),
                  session)) shouldBe true

          store.getCharacter(id)!!.pokemon.single().moves.map { it.id.toInt() } shouldBe
              listOf(MEGA_PUNCH, 2, 3, 4)
          store.getCharacter(id)!!.storyFlags.contains("kanto/FLAG_TUTOR_TEST") shouldBe true
        }
      }

      test("a party pick with no offer outstanding is left for the swap handler") {
        runTest {
          val moves = MoveRegistry()
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val teaching = MoveTeachingService(store, moves, MoveLearner(LearnsetRegistry(), moves))
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.KANTO).info.id
          val mon = monster(id, listOf(POUND))
          store.addPokemon(id, mon)
          val session = FakeSession(id)

          teaching.onPartySelect(PacketEvent(PartyMemberSelectPacket(0, mon.id), session)) shouldBe
              false
          session.sent.filterIsInstance<MoveLearnPromptPacket>().shouldBeEmpty()
        }
      }

      test("a monster that already knows it is told so, and the tutor is not spent") {
        runTest {
          val moves = MoveRegistry()
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val teaching = MoveTeachingService(store, moves, MoveLearner(LearnsetRegistry(), moves))
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.KANTO).info.id
          val mon = monster(id, listOf(MEGA_PUNCH))
          store.addPokemon(id, mon)
          val session = FakeSession(id)

          teaching.offer(session, id, MEGA_PUNCH, "a test", spendFlag = "kanto/FLAG_TUTOR_TEST")
          teaching.onPartySelect(PacketEvent(PartyMemberSelectPacket(0, mon.id), session))

          store.getCharacter(id)!!.storyFlags.contains("kanto/FLAG_TUTOR_TEST") shouldBe false
        }
      }
    })
