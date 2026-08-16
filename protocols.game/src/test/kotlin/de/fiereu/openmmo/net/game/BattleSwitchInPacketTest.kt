package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.encodeToBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.common.utils.toHex
import de.fiereu.openmmo.net.game.packets.battle.BattleMonBlock
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private fun patrat(): BattleMonBlock =
    BattleMonBlock(
        slot = 1,
        entityId = 0x000000000003C000L,
        species = 504,
        level = 2,
        gender = 1,
        abilityId = 50,
        maxHp = 14,
        currentHp = 14,
        movesPresent = true,
        moveIds = listOf(33, 0, 0, 0),
    )

class BattleSwitchInPacketTest :
    FunSpec({
      // Switching back to an already-active monster sends only the 21-byte active detail.
      // Reproduced byte for byte from a real capture (Snivy, slot 0, previous slot 1).
      test("encodes a return switch-in as just the active detail") {
        val packet =
            BattleSwitchInPacket(
                mon =
                    BattleMonBlock(
                        slot = 0,
                        entityId = 0,
                        species = 495,
                        level = 6,
                        gender = 0,
                        abilityId = 0,
                        maxHp = 0,
                        currentHp = 0,
                        movesPresent = false,
                        moveIds = listOf(0, 0, 0, 0),
                    ),
                fullBlock = false,
            )
        val bytes = BattleSwitchInPacketCodec.encodeToBytes(packet)
        bytes.toHex() shouldBe fixture("game/s2c/35/return_switch_in.bin").toHex()
      }

      // The opposing block carries no moves, so this is 55 bytes where the player's own is 65.
      test("round-trips a captured opponent switch-in") {
        val bytes = fixture("game/s2c/35/opponent_switch_in.bin")
        val decoded = BattleSwitchInPacketCodec.decodeBytes(bytes)
        decoded.side shouldBe 1.toByte()
        decoded.fullBlock shouldBe true
        decoded.mon.species shouldBe 13.toShort()
        decoded.mon.level shouldBe 7.toByte()
        decoded.mon.movesPresent shouldBe false
        BattleSwitchInPacketCodec.encodeToBytes(decoded).toHex() shouldBe bytes.toHex()
      }

      // A benched monster coming out carries its full block then its active detail. The block
      // matches the captured Patrat bytes now built from structured fields.
      test("round-trips a full-block switch-in") {
        val packet = BattleSwitchInPacket(mon = patrat(), fullBlock = true)
        val bytes = BattleSwitchInPacketCodec.encodeToBytes(packet)
        bytes.size shouldBe 65
        val decoded = BattleSwitchInPacketCodec.decodeBytes(bytes)
        decoded.fullBlock shouldBe true
        decoded.mon shouldBe patrat()
      }

      // The shape nobody had: a player's own monster coming out for the first time. Two real
      // captures, both with the flag set and the party position inside the block, which is what
      // three earlier readings of this packet got wrong.
      listOf(
              "player_first_switch_in_a.bin" to Triple(19, 3, 5),
              "player_first_switch_in_b.bin" to Triple(11, 7, 3),
          )
          .forEach { (name, expected) ->
            val (species, level, partySlot) = expected
            test("decodes $name, the player's own first switch") {
              val bytes = fixture("game/s2c/35/$name")
              val decoded = BattleSwitchInPacketCodec.decodeBytes(bytes)

              decoded.side shouldBe 0
              decoded.fullBlock shouldBe true
              decoded.mon.species shouldBe species.toShort()
              decoded.mon.level shouldBe level.toByte()
              // The party position rides here and nowhere else. Writing it into the flag byte
              // instead is what killed a live client on a switch to the fifth party member.
              decoded.mon.slot shouldBe partySlot
              BattleSwitchInPacketCodec.encodeToBytes(decoded).toHex() shouldBe bytes.toHex()
            }
          }
    })
