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
import de.fiereu.openmmo.net.game.packets.PcMove
import de.fiereu.openmmo.net.game.packets.PcMovePacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.battle.PcTogglePacket
import de.fiereu.openmmo.net.game.packets.battle.StoragePlacement
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

      test("asking for the box opens it and sends what the player holds") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(2)

          fx.boxes.open(session, id)

          // The request carries nothing, so the answer is the whole of it: true shows the box.
          val toggles = session.sent.filterIsInstance<PcTogglePacket>()
          toggles.last().shown shouldBe true
          // Drawn from what the player actually holds, not whatever the client had cached.
          val containers = session.sent.filterIsInstance<PokemonContainerPacket>()
          containers.filter { !it.delete }.map { it.container } shouldBe
              listOf(PokemonContainer.PARTY, PokemonContainer.PC)
        }
      }

      test("each container is emptied before it is filled, so a stale entry cannot survive") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(2)

          fx.boxes.open(session, id)

          // The box draws correctly at login and wrongly after a move, so what the client does
          // with a container it already holds is the open question. Emptying it first is the
          // current answer, and /probe box is what settles it against a real client.
          BoxSyncTuning.mode shouldBe 1
          val containers = session.sent.filterIsInstance<PokemonContainerPacket>()
          containers.map { it.container to it.delete } shouldBe
              listOf(
                  PokemonContainer.PARTY to true,
                  PokemonContainer.PARTY to false,
                  PokemonContainer.PC to true,
                  PokemonContainer.PC to false,
              )
        }
      }

      test("a drag between two party slots swaps them") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(3)
          val first = fx.party(id).first { it.containerSlot.toInt() == 0 }
          val third = fx.party(id).first { it.containerSlot.toInt() == 2 }

          // party 0 -> party 2, which the client sends as container 1 at both ends.
          fx.boxes.onMove(PacketEvent(PcMovePacket(listOf(PcMove(1, 0, 1, 2))), session))

          fx.party(id).first { it.id == first.id }.containerSlot shouldBe 2.toShort()
          fx.party(id).first { it.id == third.id }.containerSlot shouldBe 0.toShort()
        }
      }

      test("a drag from the party into an empty box slot moves it across") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(2)
          val moving = fx.party(id).first { it.containerSlot.toInt() == 1 }

          fx.boxes.onMove(PacketEvent(PcMovePacket(listOf(PcMove(1, 1, 0, 27))), session))

          fx.party(id).any { it.id == moving.id } shouldBe false
          val inBox = fx.pc(id).first { it.id == moving.id }
          inBox.container shouldBe PokemonContainer.PC
          inBox.containerSlot shouldBe 27.toShort()
        }
      }

      // The shape that was being dropped on the wire: one packet, four monsters.
      test("a drag of several monsters at once moves every one of them") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(6)
          val moving =
              (2..5).map { slot -> fx.party(id).first { it.containerSlot.toInt() == slot } }

          fx.boxes.onMove(
              PacketEvent(
                  PcMovePacket(
                      listOf(
                          PcMove(1, 2, 0, 24),
                          PcMove(1, 3, 0, 25),
                          PcMove(1, 4, 0, 26),
                          PcMove(1, 5, 0, 27),
                      )),
                  session))

          fx.party(id).size shouldBe 2
          moving.forEachIndexed { i, monster ->
            fx.pc(id).first { it.id == monster.id }.containerSlot shouldBe (24 + i).toShort()
          }
        }
      }

      test("one bad move in a drag does not stop the rest") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(3)
          val moving = fx.party(id).first { it.containerSlot.toInt() == 2 }

          fx.boxes.onMove(
              PacketEvent(PcMovePacket(listOf(PcMove(1, 99, 0, 24), PcMove(1, 2, 0, 25))), session))

          fx.pc(id).first { it.id == moving.id }.containerSlot shouldBe 25.toShort()
          fx.party(id).size shouldBe 2
        }
      }

      // A live server answers a party reorder with deltas alone, but this client draws its two
      // panes from different things: the box only moves for a delta, and a party slot only fills
      // in from the containers. A monster dragged into the party sat in a slot that drew as empty
      // until the screen was shut, so both go out.
      test("a drag is answered by where each moved monster now is") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(3)
          val first = fx.party(id).first { it.containerSlot.toInt() == 0 }
          val third = fx.party(id).first { it.containerSlot.toInt() == 2 }

          fx.boxes.onMove(PacketEvent(PcMovePacket(listOf(PcMove(1, 0, 1, 2))), session))

          val deltas = session.sent.filterIsInstance<BattleEntityDeltaPacket>()
          deltas.map { it.entityId }.toSet() shouldBe setOf(first.id, third.id)
          deltas.first { it.entityId == first.id }.placement shouldBe
              StoragePlacement(PokemonContainer.PARTY.ordinal.toByte(), 2)
          deltas.first { it.entityId == third.id }.placement shouldBe
              StoragePlacement(PokemonContainer.PARTY.ordinal.toByte(), 0)
          // Both panes: the deltas above move the box, these fill the party slots.
          session.sent.filterIsInstance<PokemonContainerPacket>().map { it.container } shouldBe
              listOf(
                  PokemonContainer.PARTY,
                  PokemonContainer.PARTY,
                  PokemonContainer.PC,
                  PokemonContainer.PC,
              )
        }
      }

      test("a drag that would empty the party is refused") {
        runTest {
          val fx = Fixture(backgroundScope)
          val (session, id) = fx.player(1)

          fx.boxes.onMove(PacketEvent(PcMovePacket(listOf(PcMove(1, 0, 0, 0))), session))

          fx.party(id).size shouldBe 1
          fx.pc(id).size shouldBe 0
        }
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
        id = sharedEntityIds.newMonsterId(),
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
        moves =
            listOf(PokemonMove(33, 35), PokemonMove(0, 0), PokemonMove(0, 0), PokemonMove(0, 0)),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.now(),
    )

/** One generator for the whole file: a fresh one per monster can hand out the same id twice. */
private val sharedEntityIds = EntityIdService()
