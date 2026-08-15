package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.LocalPlayerStatePacket
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.storage.StoredCharacter
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

private fun mon(ownerId: Long, dexId: Int, slot: Short, container: PokemonContainer) =
    Pokemon(
        id = sharedIds.newMonsterId(),
        ownerId = ownerId,
        container = container,
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

private val sharedIds = EntityIdService()

class WorldStateDexTest :
    FunSpec({
      fun character(): StoredCharacter {
        val id = 42L
        return StoredCharacter(
            info =
                CharacterInfo(
                    id = id,
                    name = "Ash",
                    namePrefix = "",
                    userId = 1,
                    rivalSex = 1,
                    lastLogin = LocalDateTime.now(),
                    createdAt = LocalDateTime.now(),
                    money = 0,
                    permissions = 0,
                    remainingSafariSteps = 0,
                    remainingSafariBalls = 0,
                    pcExtraSlots = 0,
                    battleBoxExtraSlots = 0,
                    templateAmount = 0,
                    positionRegionId = 1,
                    positionBankId = 51,
                    positionMapId = 3,
                    positionX = 4,
                    positionY = 4,
                    repelLeft = 0,
                    repelItemId = 0,
                    lureLeft = 0,
                    lureItemId = 0,
                ),
            pokemon =
                mutableListOf(
                    mon(id, 1, 0, PokemonContainer.PARTY),
                    mon(id, 19, 1, PokemonContainer.PARTY),
                ),
            pcStorage = mutableListOf(mon(id, 10, 0, PokemonContainer.PC)),
            items = mutableMapOf(),
        )
      }

      test("the login state carries a dex built from what the player owns") {
        val session = FakeSession(42L)

        WorldStateService().send(session, character())

        val state = session.sent.filterIsInstance<LocalPlayerStatePacket>().last()
        // Everything owned counts as caught, which is what backfills a dex nobody ever recorded.
        state.pokedexCaught shouldContainAll listOf<Short>(1, 10, 19)
        state.pokedexSeen shouldContainAll listOf<Short>(1, 10, 19)
        // Empty lists are what the client was sent before any of this existed.
        (state.pokedexCaught.isEmpty()) shouldBe false
      }
    })
