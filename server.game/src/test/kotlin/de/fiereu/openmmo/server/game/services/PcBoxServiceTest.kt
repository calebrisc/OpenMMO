package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.net.game.packets.PcBoxStorePacket
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private val PC_ID = PokemonContainer.PC.ordinal.toByte()
private val PARTY_ID = PokemonContainer.PARTY.ordinal.toByte()

@OptIn(ExperimentalCoroutinesApi::class)
class PcBoxServiceTest :
    FunSpec({
      class Fixture(scope: CoroutineScope) {
        val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), scope)
        val boxes = PcBoxService(store)

        suspend fun player(count: Int): Triple<FakeSession, Long, List<Pokemon>> {
          val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          val id = created.info.id
          val mons = (0 until count).map { monster(id, it + 1, it.toShort()) }
          mons.forEach { store.addPokemon(id, it) }
          return Triple(FakeSession(id), id, mons)
        }

        suspend fun store(session: FakeSession, box: Byte, slot: Byte, monId: Long) =
            boxes.onStore(PacketEvent(PcBoxStorePacket(box, slot, monId), session))

        fun party(id: Long) = store.getCharacter(id)?.pokemon.orEmpty()

        fun pc(id: Long) = store.getCharacter(id)?.pcStorage.orEmpty()
      }

      test("depositing moves a monster from the party into the pc") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id, mons) = fx.player(2)

          fx.store(session, PC_ID, 0, mons[0].id)

          fx.party(id).any { it.id == mons[0].id } shouldBe false
          fx.pc(id).any { it.id == mons[0].id } shouldBe true
          // The container has to travel with it, or a reload would put it back in the party.
          fx.pc(id).first { it.id == mons[0].id }.container shouldBe PokemonContainer.PC
        }
      }

      test("withdrawing brings it back to the party") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id, mons) = fx.player(2)
          fx.store(session, PC_ID, 0, mons[0].id)

          fx.store(session, PARTY_ID, 0, mons[0].id)

          fx.party(id).any { it.id == mons[0].id } shouldBe true
          fx.pc(id).any { it.id == mons[0].id } shouldBe false
          fx.party(id).first { it.id == mons[0].id }.container shouldBe PokemonContainer.PARTY
        }
      }

      test("the last party monster cannot be put away") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id, mons) = fx.player(1)

          fx.store(session, PC_ID, 0, mons[0].id)

          fx.party(id).size shouldBe 1
          fx.pc(id).size shouldBe 0
        }
      }

      test("a full party refuses another monster") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id, mons) = fx.player(7)
          // Seven will not fit, so one goes to the pc and is then asked back into a full party.
          fx.store(session, PC_ID, 0, mons[6].id)
          fx.party(id).size shouldBe 6

          fx.store(session, PARTY_ID, 0, mons[6].id)

          fx.party(id).size shouldBe 6
          fx.pc(id).any { it.id == mons[6].id } shouldBe true
        }
      }

      test("a monster somebody does not own is left alone") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id, _) = fx.player(2)

          fx.store(session, PC_ID, 0, 999_999L)

          fx.party(id).size shouldBe 2
          fx.pc(id).size shouldBe 0
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
        ot = "Ash",
        nickname = "",
        level = 5,
        hp = 20,
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
