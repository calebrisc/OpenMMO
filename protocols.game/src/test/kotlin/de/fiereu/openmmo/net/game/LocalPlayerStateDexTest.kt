package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.net.game.packets.LocalPlayerStatePacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class LocalPlayerStateDexTest :
    FunSpec({
      // A real login state from the archive, carrying a dex somebody actually filled in.
      test("the dex lists are national dex numbers, not indexes into anything") {
        val decoded =
            LocalPlayerStatePacketCodec.decodeBytes(
                fixture("game/s2c/f3/local_player_state_with_dex.bin"))

        decoded.pokedexSeen.size shouldBe 167
        decoded.pokedexCaught.size shouldBe 18
        // Every starter of every generation, at its national number. Anything counting species
        // some other way would put these somewhere else entirely.
        decoded.pokedexCaught shouldContainAll
            listOf<Short>(152, 155, 158, 252, 258, 387, 390, 393, 495, 498, 501)
        // Reading it wrong leaves bytes over, so the whole packet has to land to prove the shape.
        decoded.badges.size shouldBe 0
        decoded.variables.size shouldBe 0
      }
    })
