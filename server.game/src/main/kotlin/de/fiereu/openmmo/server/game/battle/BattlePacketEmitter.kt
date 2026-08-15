package de.fiereu.openmmo.server.game.battle

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.common.utils.hexToBytes
import de.fiereu.openmmo.net.game.packets.EntityMovePpPacket
import de.fiereu.openmmo.net.game.packets.EntityPresencePacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionEvent
import de.fiereu.openmmo.net.game.packets.battle.BattleAddPokemon
import de.fiereu.openmmo.net.game.packets.battle.BattleBulkStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEffectTarget
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEventBody
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleOpponentBlock
import de.fiereu.openmmo.net.game.packets.battle.BattleQueuedEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSideAddPokemonPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSidePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSlotEventEnumPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSlotFlagEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleStartScenePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleStatCountersPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTileMapPacket
import de.fiereu.openmmo.net.game.packets.battle.Experience
import de.fiereu.openmmo.net.game.packets.battle.MoveSlots
import de.fiereu.openmmo.net.game.packets.battle.OpposingSide
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.typechart.TypeChart
import javax.inject.Inject
import javax.inject.Singleton

private const val ACTION_PROMPT: Byte = -128 // 0x80
private const val MOVE_EVENT_KIND: Byte = 1
// Slot-event type shown when the player gets away from a wild battle.
private const val FLED_EVENT: Byte = 0

// The player's overworld entity is hidden while the battle scene is up and shown again when it
// ends.
private const val PRESENCE_IN_BATTLE: Byte = 1
private const val PRESENCE_OVERWORLD: Byte = 0

// The active battle side reported to the client so the bag knows which monster an item targets.
/**
 * Which half of the field a switch in lands on, which is not the same numbering as [PLAYER_SIDE].
 * That one names the local side for [de.fiereu.openmmo.net.game.packets.battle.BattleSidePacket] and
 * happens to share a value with [OPPONENT_SIDE], so picking between those two decides nothing.
 */
private const val SWITCH_IN_NEAR: Byte = 0
private const val SWITCH_IN_FAR: Byte = 1

private const val PLAYER_SIDE: Byte = 1

// The side byte a switch-in carries, which is not the same numbering as BattleSidePacket.
private const val OPPONENT_SIDE: Byte = 1

private val CAPTURED_APPEARANCE = "00024c031aac0f00038001a40004".hexToBytes()

// The target's outcome word, which picks the line the client prints for that target. A damaging
// hit is 0x0200, a miss 1, a failure 4, and a status move that only moves a stat carries none of
// them. The events under the target are read either way.
private const val HP_TARGET_MOVE: Short = 0x0200
private const val MISSED_TARGET_MOVE: Short = 1
private const val FAILED_TARGET_MOVE: Short = 4
private const val DEFAULT_TARGET_MOVE: Short = 0
private const val SUPER_EFFECTIVE_BIT = 0x20
private const val NOT_VERY_EFFECTIVE_BIT = 0x10

/**
 * Turns battle state and [BattleEvent]s into packets. Everything battle wide goes through the
 * battle's interest key, so spectators and later trainer opponents receive it too. Packets tied to
 * one viewer (side, bag, party sync) go to that session directly.
 */
@Singleton
class BattlePacketEmitter @Inject constructor(private val interestManager: InterestManager) {

  fun sendStart(battle: BattleInstance, playerName: String) {
    battle.playerName = playerName
    battle.session.send(EntityPresencePacket(entityId = battle.charId, status = PRESENCE_IN_BATTLE))
    // Tell the client which side is local so the battle bag knows which monster an item targets.
    // Without it, opening the bag crashes. Opcode 0x40 is left alone here, since re-sending it
    // would wipe the balls out of the battle bag.
    battle.session.send(BattleSidePacket(side = PLAYER_SIDE))
    if (BattleFieldTuning.announceDouble) {
      // Never sent before. If a side holds two monsters only when the battle says so up front,
      // this is where it has to be said.
      broadcast(
          battle,
          BattleStartScenePacket(
              battleType = BattleFieldTuning.battleType.toByte(),
              doubleBattle = true,
              perspective = BattleFieldTuning.perspective.toByte(),
          ),
      )
    }
    broadcast(battle, fieldState(battle, playerName))
    // A monster that walked in already poisoned or asleep carries no status in its battle
    // block, so the icon has to be sent separately.
    battle.party.forEach { sendCarriedStatus(battle, it) }
    battle.opponent.forEach { sendCarriedStatus(battle, it) }
    sendPrompt(battle)
  }

  /**
   * Opens a duel. Each player is the local side of their own screen, so neither view can be
   * broadcast: the challenged player receives the same battle mirrored, with the two parties the
   * other way round.
   */
  fun sendDuelStart(battle: BattleInstance, hostName: String) {
    val duel = battle.duel ?: return
    battle.playerName = hostName

    battle.session.send(EntityPresencePacket(entityId = battle.charId, status = PRESENCE_IN_BATTLE))
    battle.session.send(BattleSidePacket(side = PLAYER_SIDE))
    battle.session.send(fieldState(battle, hostName))

    duel.session.send(EntityPresencePacket(entityId = duel.charId, status = PRESENCE_IN_BATTLE))
    duel.session.send(BattleSidePacket(side = PLAYER_SIDE))
    duel.session.send(mirroredFieldState(battle, duel))

    battle.party.forEach { sendCarriedStatus(battle, it) }
    battle.opponent.forEach { sendCarriedStatus(battle, it) }
    sendPrompt(battle)
  }

  /**
   * A switch in a duel, sent to each player from their own point of view.
   *
   * The side byte says whether the monster belongs to the near or far half of the screen, so one
   * broadcast cannot serve both players: the same packet would put one player's switch on the other
   * player's side of the field. Only the owner is sent the moves, and only the watcher is ever sent
   * a full block, since the owner was given their whole party when the battle opened.
   */
  fun sendDuelSwitchIn(battle: BattleInstance, hostSwitched: Boolean) {
    val duel = battle.duel ?: return
    val mon = if (hostSwitched) battle.activeMon() else battle.opponentMon()
    val slot = if (hostSwitched) battle.activeSlot else battle.opponentSlot
    val seen = if (hostSwitched) battle.seenActive else battle.opponentSeen
    val firstSighting = slot !in seen

    fun packetFor(owner: Boolean) =
        BattleSwitchInPacket(
            mon = mon.toBlock(slot, movesPresent = owner),
            fullBlock = !owner && firstSighting,
            side = if (owner) SWITCH_IN_NEAR else SWITCH_IN_FAR,
        )

    battle.session.send(packetFor(hostSwitched))
    duel.session.send(packetFor(!hostSwitched))
    seen.add(slot)
  }

  /** The battle as the challenged player sees it, with the sides swapped. */
  private fun mirroredFieldState(battle: BattleInstance, duel: DuelSide): BattleFieldStatePacket =
      BattleFieldStatePacket(
          playerName = duel.name,
          playerId = duel.charId,
          playerAppearance = CAPTURED_APPEARANCE,
          background = 0,
          opposing = OpposingSide.TRAINER,
          trainerId = 0,
          playerParty = battle.opponent.mapIndexed { slot, mon -> mon.toBlock(slot, true) },
          activeSlot = battle.opponentSlot,
          // The host's bench stays hidden the way a trainer's does, so only what has been sent out
          // is described.
          opponentParty =
              battle.party.mapIndexed { slot, mon ->
                if (slot in battle.seenActive) mon.toOpponentBlock(slot)
                else BattleOpponentBlock(slot = slot, revealed = false)
              },
          opponentActiveSlot = battle.activeSlot,
      )

  /**
   * Catches a session up on a battle already in progress, for somebody who just started watching.
   * The side packet goes out too: without it the client's battle bag has no local side and crashes
   * when opened.
   */
  fun sendSnapshotTo(session: SessionContext, battle: BattleInstance) {
    session.send(BattleSidePacket(side = PLAYER_SIDE))
    session.send(fieldState(battle, battle.playerName))
    (battle.party + battle.opponent)
        .filter { it.status.isSet }
        .forEach { session.send(statusDelta(it)) }
  }

  private fun fieldState(battle: BattleInstance, playerName: String): BattleFieldStatePacket =
      BattleFieldStatePacket(
          playerName = playerName,
          playerId = battle.charId,
          // TODO Send the player's own appearance and the map's battle backdrop
          //  These are the captured values, so every player appears as the captured character.
          playerAppearance = CAPTURED_APPEARANCE,
          background = 0,
          opposing = if (battle.trainer == null) OpposingSide.WILD else OpposingSide.TRAINER,
          // TODO Check whether Hoenn needs a region tag, both decomps number trainers from 1
          trainerId = (battle.trainer?.id ?: 0).toShort(),
          playerParty = battle.party.mapIndexed { slot, mon -> mon.toBlock(slot, true) },
          activeSlot = battle.activeSlot,
          opponentParty =
              battle.opponent.mapIndexed { slot, mon ->
                if (slot in battle.opponentSeen) mon.toOpponentBlock(slot)
                else BattleOpponentBlock(slot = slot, revealed = false)
              },
          opponentActiveSlot = battle.opponentSlot,
      )

  /** Pushes the result of a bag item: the new hp, and the status if the item cleared one. */
  fun sendItemUsed(battle: BattleInstance, mon: BattleMonState, cured: Boolean) {
    broadcast(
        battle,
        BattleEntityDeltaPacket(entityId = mon.entityId, currentHp = mon.currentHp.toShort()))
    if (cured) {
      sendStatus(battle, mon.entityId, StatusCondition.NONE, 0)
    }
  }

  /** Adds a monster to a side of a running battle, in a slot of its own. */
  fun addToSide(battle: BattleInstance, side: Int, slot: Int, mon: BattleMonState) {
    broadcast(
        battle,
        BattleSideAddPokemonPacket(
            side = side.toByte(),
            pokemon =
                BattleAddPokemon(
                    entityId = mon.entityId,
                    frontSpriteId = mon.species.id.toShort(),
                    backSpriteId = mon.species.id.toShort(),
                    side = side.toByte(),
                    slot = slot.toByte(),
                    partyIndex = (mon.partyIndex ?: 0).toByte(),
                    statusEffect = null,
                ),
        ),
    )
  }

  /** Sends [mon]'s status if it has one, for a battle start or a switch in. */
  fun sendCarriedStatus(battle: BattleInstance, mon: BattleMonState) {
    if (!mon.status.isSet) return
    sendStatus(battle, mon.entityId, mon.status, mon.sleepTurns)
  }

  fun sendEvents(battle: BattleInstance, events: List<BattleEvent>) {
    var i = 0
    while (i < events.size) {
      val event = events[i]
      when (event) {
        is BattleEvent.MoveUsed -> {
          if (battle.isPlayerSide(event.attackerId)) {
            battle.session.send(
                EntityMovePpPacket(
                    event.attackerId, event.moveSlot.toByte(), event.ppLeft.toByte()))
          }
          // The move's outcome rides inside the move event so the client animates it. A hit carries
          // the target's resulting hp, a stat change carries the affected stat and its signed stage
          // delta. A capped change reports no delta, so it stays unanimated.
          val targets =
              when (val next = events.getOrNull(i + 1)) {
                is BattleEvent.DamageDealt -> {
                  i++
                  // The client faints the target on hp reaching 0, as the real server does, so no
                  // faint sub-event is sent here.
                  val subEvents =
                      mutableListOf(
                          BattleActionEvent(
                              null, null, BattleEventBody.HpUpdate(next.newHp.toShort())))
                  // A secondary stage change rides under the same target as the damage, the way
                  // the captured Rock Tomb does. One aimed elsewhere gets a target of its own.
                  var elsewhere = emptyList<BattleEffectTarget>()
                  val secondary = events.getOrNull(i + 1)
                  if (secondary is BattleEvent.StageChanged && !secondary.failed) {
                    i++
                    val body =
                        BattleEventBody.StatChange(
                            statIndex(secondary.stat), secondary.delta.toShort())
                    if (secondary.targetId == next.targetId) {
                      subEvents += BattleActionEvent(null, null, body)
                    } else {
                      elsewhere = listOf(target(secondary.targetId, DEFAULT_TARGET_MOVE, body))
                    }
                  }
                  val outcome = HP_TARGET_MOVE.toInt() or effectivenessBit(next.effectiveness)
                  listOf(BattleEffectTarget(next.targetId, outcome.toShort(), subEvents)) +
                      elsewhere
                }
                is BattleEvent.StageChanged ->
                    if (!next.failed) {
                      i++
                      listOf(
                          target(
                              next.targetId,
                              DEFAULT_TARGET_MOVE,
                              BattleEventBody.StatChange(
                                  statIndex(next.stat), next.delta.toShort())))
                    } else {
                      emptyList()
                    }
                is BattleEvent.MoveWithoutTarget -> {
                  i++
                  listOf(failTarget(battle, event.attackerId, next))
                }
                else -> emptyList()
              }
          broadcast(
              battle,
              BattleEntityMoveEventPacket(event.attackerId, event.moveId, MOVE_EVENT_KIND, targets))
        }
        is BattleEvent.StatusInflicted ->
            sendStatus(battle, event.targetId, event.status, event.sleepTurns)
        is BattleEvent.StatusCleared -> sendStatus(battle, event.targetId, StatusCondition.NONE, 0)
        is BattleEvent.StatusDamage ->
            broadcast(
                battle,
                BattleEntityDeltaPacket(
                    entityId = event.targetId, currentHp = event.newHp.toShort()))
        // Nothing is animated for a turn lost to sleep, freeze or paralysis: the status icon the
        // client already shows is what explains it. Worth revisiting with a live client.
        is BattleEvent.StatusBlockedMove -> Unit
        is BattleEvent.DamageDealt -> Unit
        is BattleEvent.StageChanged -> Unit
        is BattleEvent.Fainted -> Unit
        is BattleEvent.MoveWithoutTarget -> Unit
      }
      i++
    }
  }

  /** Tells the client a monster's status changed, which is what draws the icon. */
  fun sendStatus(
      battle: BattleInstance,
      entityId: Long,
      status: StatusCondition,
      sleepTurns: Int,
  ) {
    broadcast(
        battle,
        BattleEntityDeltaPacket(
            entityId = entityId,
            status = StatusRules.wireValue(status, sleepTurns),
        ),
    )
  }

  private fun statusDelta(mon: BattleMonState): BattleEntityDeltaPacket =
      BattleEntityDeltaPacket(
          entityId = mon.entityId,
          status = StatusRules.wireValue(mon.status, mon.sleepTurns),
      )

  fun sendSwitchIn(battle: BattleInstance, fullBlock: Boolean) {
    broadcast(
        battle,
        BattleSwitchInPacket(
            // The party position rides in the block. It has no other home on the wire.
            mon = battle.activeMon().toBlock(slot = battle.activeSlot, movesPresent = true),
            fullBlock = fullBlock,
        ),
    )
  }

  /** The opposing side sends out its next monster. Its moves stay hidden from the player. */
  fun sendOpponentSwitchIn(battle: BattleInstance, fullBlock: Boolean) {
    broadcast(
        battle,
        BattleSwitchInPacket(
            mon = battle.opponentMon().toBlock(battle.opponentSlot, movesPresent = false),
            fullBlock = fullBlock,
            side = OPPONENT_SIDE,
        ),
    )
  }

  fun sendPrompt(battle: BattleInstance) {
    broadcast(battle, BattleTileMapPacket(groupId = battle.turn.toShort(), slotTiles = null))
    // Only the players choose an action. A spectator joined to the same key must not be
    // handed the action UI.
    battle.sessions().forEach { it.send(BattleQueuedEventPacket(packed = ACTION_PROMPT)) }
  }

  /** Opens the party switch screen after the active mon faints, in place of the action prompt. */
  fun sendSwitchPrompt(battle: BattleInstance) {
    broadcast(battle, BattleSlotFlagEventPacket(slot = 0, flag = false, immediate = false))
  }

  /** Confirms the forced replacement choice just before its switch-in. */
  fun sendSwitchConfirm(battle: BattleInstance) {
    broadcast(battle, BattleSlotFlagEventPacket(slot = 0, flag = false, immediate = true))
  }

  fun sendFled(battle: BattleInstance) {
    broadcast(battle, BattleSlotEventEnumPacket(slot = 0, eventType = FLED_EVENT))
    broadcast(battle, BattleBulkStatePacket.fled())
    battle.session.send(EntityPresencePacket(entityId = battle.charId, status = PRESENCE_OVERWORLD))
  }

  fun sendBattleEnd(battle: BattleInstance, party: List<Pokemon>, prizeMoney: Int = 0) {
    broadcast(battle, BattleBulkStatePacket.battleEnd(prizeMoney))
    battle.session.send(EntityPresencePacket(entityId = battle.charId, status = PRESENCE_OVERWORLD))
    battle.session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ),
    )
  }

  fun sendVictoryDelta(battle: BattleInstance, entityId: Long, reward: RewardResult) {
    broadcast(
        battle,
        BattleEntityDeltaPacket(
            entityId = entityId,
            experience = Experience(reward.newLevel.toByte(), reward.newXp),
        ),
    )
    // The delta above moves the bar, the reward text reads its number from here.
    broadcast(battle, experienceReward(entityId, reward.xpGained))
    if (!reward.leveled) return
    broadcast(
        battle,
        BattleEntityDeltaPacket(
            entityId = entityId,
            statValues = reward.newStats.asWireList(),
            currentHp = reward.newCurrentHp.toShort(),
            evValues = reward.newEvs.asWireList(),
        ),
    )
  }

  // The other six counters are rewards we do not award yet.
  private fun experienceReward(entityId: Long, gained: Int): BattleStatCountersPacket =
      BattleStatCountersPacket(
          entityId = entityId,
          baseCounter = gained,
          counter1 = null,
          counter2 = null,
          counter3 = null,
          counter4 = null,
          counter5 = null,
          counter6 = null,
      )

  fun sendNotice(battle: BattleInstance, message: String) {
    battle.session.send(notice(message))
  }

  private fun target(entityId: Long, targetMove: Short, body: BattleEventBody): BattleEffectTarget =
      BattleEffectTarget(entityId, targetMove, listOf(BattleActionEvent(null, null, body)))

  // A missed or failed move carries no target of its own, so it lands on the attacker's opponent.
  // A miss is the target move word on its own with no events under it. The client writes the miss
  // line from that, so sending an event as well makes it print an unrelated message.
  private fun failTarget(
      battle: BattleInstance,
      attackerId: Long,
      event: BattleEvent.MoveWithoutTarget,
  ): BattleEffectTarget {
    val defender =
        if (battle.isPlayerSide(attackerId)) battle.opponentMon().entityId
        else battle.activeMon().entityId
    return when (event) {
      is BattleEvent.MoveMissed -> BattleEffectTarget(defender, MISSED_TARGET_MOVE, emptyList())
      is BattleEvent.MoveFailed -> BattleEffectTarget(defender, FAILED_TARGET_MOVE, emptyList())
    }
  }

  /** The effectiveness line rides in the outcome word rather than in an event of its own. */
  private fun effectivenessBit(effectiveness: Int): Int =
      when {
        effectiveness > TypeChart.NEUTRAL -> SUPER_EFFECTIVE_BIT
        effectiveness in 1..<TypeChart.NEUTRAL -> NOT_VERY_EFFECTIVE_BIT
        else -> 0
      }

  fun broadcast(battle: BattleInstance, packet: Any) {
    interestManager.broadcast(battle.key, packet)
  }

  /** The delta that tells the client a monster's moveset changed. */
  fun moveSlotsDelta(
      entityId: Long,
      moveSlots: List<Pair<Short, Byte>>,
      ppUps: Byte,
  ): BattleEntityDeltaPacket =
      BattleEntityDeltaPacket(entityId = entityId, moves = MoveSlots(moveSlots, ppUps))

  // The decomp battle stat order. Not verified against the live client yet.
  private fun statIndex(stat: BattleStat): Byte =
      when (stat) {
        BattleStat.ATTACK -> 1
        BattleStat.DEFENSE -> 2
        BattleStat.SPEED -> 3
        BattleStat.SP_ATTACK -> 4
        BattleStat.SP_DEFENSE -> 5
        BattleStat.ACCURACY -> 6
        BattleStat.EVASION -> 7
      }
}

// The decomp in-game stat order. Not verified against the live client yet.
private fun statOrder(hp: Int, atk: Int, def: Int, spd: Int, spAtk: Int, spDef: Int): List<Short> =
    listOf(
        hp.toShort(), atk.toShort(), def.toShort(), spd.toShort(), spAtk.toShort(), spDef.toShort())

internal fun ComputedStats.asWireList(): List<Short> = statOrder(hp, atk, def, spd, spAtk, spDef)

private fun EVs.asWireList(): List<Short> = statOrder(hp, atk, def, spd, spAtk, spDef)
