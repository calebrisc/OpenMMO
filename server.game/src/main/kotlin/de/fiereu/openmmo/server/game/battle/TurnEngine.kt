package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.MoveEffect
import de.fiereu.openmmo.common.enums.PokemonType
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.moves.MoveDef
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.typechart.TypeChart
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private const val CRIT_DENOMINATOR = 16

/** A high critical ratio move, the games' second crit stage. */
private const val HIGH_CRIT_DENOMINATOR = 4

/** Recoil and drain are both a fraction of what was dealt. */
private const val RECOIL_DIVISOR = 4
private const val DRAIN_DIVISOR = 2

/** Dragon Rage always deals this, whatever it hits. */
private const val DRAGON_RAGE_DAMAGE = 40

/** Leech Seed takes an eighth of the maximum every turn. */
private const val SEED_DIVISOR = 8

/** Rest always sleeps exactly this long. */
private const val REST_SLEEP_TURNS = 2

private const val TACKLE_ID = 33

sealed interface BattleEvent {
  data class MoveUsed(val attackerId: Long, val moveId: Short, val moveSlot: Int, val ppLeft: Int) :
      BattleEvent

  /** A move that reached no target of its own, so its message lands on the opponent. */
  sealed interface MoveWithoutTarget : BattleEvent {
    val attackerId: Long
    val moveId: Short
  }

  data class MoveMissed(override val attackerId: Long, override val moveId: Short) :
      MoveWithoutTarget

  data class MoveFailed(override val attackerId: Long, override val moveId: Short) :
      MoveWithoutTarget

  data class DamageDealt(
      val targetId: Long,
      val newHp: Int,
      val crit: Boolean,
      val effectiveness: Int,
  ) : BattleEvent

  data class StageChanged(
      val targetId: Long,
      val stat: BattleStat,
      val stage: Int,
      val effectiveValue: Int,
      val delta: Int,
      val failed: Boolean,
  ) : BattleEvent

  data class Fainted(val targetId: Long) : BattleEvent

  /** A major status landed on [targetId]. */
  data class StatusInflicted(val targetId: Long, val status: StatusCondition, val sleepTurns: Int) :
      BattleEvent

  /** End of turn chip damage from poison, burn or toxic. */
  data class StatusDamage(val targetId: Long, val newHp: Int, val status: StatusCondition) :
      BattleEvent

  /** Hp that moved for a reason other than a hit: drained, drunk back, or paid as recoil. */
  data class HpChanged(val targetId: Long, val newHp: Int) : BattleEvent

  /** A move that flinched its target, or a turn lost to having been flinched. */
  data class Flinched(val targetId: Long) : BattleEvent

  /** The monster could not move this turn because it is asleep, frozen or fully paralysed. */
  data class StatusBlockedMove(val attackerId: Long, val status: StatusCondition) : BattleEvent

  /** Woke up or thawed out, so the status is gone. */
  data class StatusCleared(val targetId: Long, val previous: StatusCondition) : BattleEvent
}

private data class TurnAction(
    val attacker: BattleMonState,
    val defender: BattleMonState,
    val move: MoveDef?,
)

private data class StageEffect(val stat: BattleStat, val delta: Int, val onSelf: Boolean)

/**
 * Resolves one wild battle turn with the Gen 3 core rules: priority and speed order, accuracy
 * stages, the damage formula with crit, STAB, and type chart, and stat stage moves. Effects outside
 * that core fail gracefully without touching state.
 */
@Singleton private val log = KotlinLogging.logger {}

class TurnEngine
@Inject
constructor(
    private val moves: MoveRegistry,
    private val typeChart: TypeChart,
) {

  /**
   * [enemyMoveId] is the move the opposing side chose. A duel supplies the other player's pick and
   * everything past this point is identical, since the two sides were always resolved the same way
   * and only differed in who decided.
   */
  fun resolveTurn(
      battle: BattleInstance,
      playerMoveId: Short,
      enemyMoveId: Short? = null,
  ): List<BattleEvent> {
    val events = mutableListOf<BattleEvent>()
    val player = battle.activeMon()
    val enemy = battle.opponentMon()

    val playerAction = TurnAction(player, enemy, moves.get(playerMoveId.toInt()))
    val enemyMove =
        if (enemyMoveId != null) moves.get(enemyMoveId.toInt()) else pickEnemyMove(battle, enemy)
    val enemyAction = TurnAction(enemy, player, enemyMove)

    for (action in order(battle, playerAction, enemyAction)) {
      if (action.attacker.fainted) continue
      execute(battle, action, events)
      if (player.fainted || enemy.fainted) break
    }
    endOfTurn(listOf(player, enemy), events)
    return events
  }

  /**
   * One side attacks and the other does not, which is the turn where a duellist switched or reached
   * for a bag item while their opponent struck.
   */
  fun resolveOneSided(
      battle: BattleInstance,
      attackerIsHost: Boolean,
      moveId: Short,
  ): List<BattleEvent> {
    val events = mutableListOf<BattleEvent>()
    val attacker = if (attackerIsHost) battle.activeMon() else battle.opponentMon()
    val defender = if (attackerIsHost) battle.opponentMon() else battle.activeMon()
    if (attacker.fainted) return events
    execute(battle, TurnAction(attacker, defender, moves.get(moveId.toInt())), events)
    endOfTurn(listOf(battle.activeMon(), battle.opponentMon()), events)
    return events
  }

  /** A voluntary switch spends the player's turn, so the enemy attacks the incoming monster. */
  fun resolveSwitchTurn(battle: BattleInstance): List<BattleEvent> {
    val events = mutableListOf<BattleEvent>()
    val enemy = battle.opponentMon()
    if (enemy.fainted) return events
    execute(battle, TurnAction(enemy, battle.activeMon(), pickEnemyMove(battle, enemy)), events)
    endOfTurn(listOf(battle.activeMon(), enemy), events)
    return events
  }

  /** Poison, burn and toxic bite after both sides have acted. */
  private fun endOfTurn(
      participants: List<BattleMonState>,
      events: MutableList<BattleEvent>,
  ) {
    for (mon in participants) {
      if (mon.fainted) continue
      val damage = StatusRules.endOfTurnDamage(mon.status, mon.stats.hp, mon.toxicCounter)
      if (damage <= 0) continue
      // Temporary, while a player reports poison taking one point a turn instead of an eighth of
      // the maximum: the maximum this read is the whole question.
      log.info {
        "STATUS TICK: entity=${mon.entityId} ${mon.status} max=${mon.stats.hp} " +
            "damage=$damage hp=${mon.currentHp} -> ${(mon.currentHp - damage).coerceAtLeast(0)}"
      }
      mon.currentHp = (mon.currentHp - damage).coerceAtLeast(0)
      events += BattleEvent.StatusDamage(mon.entityId, mon.currentHp, mon.status)
      if (mon.status == StatusCondition.TOXIC) mon.toxicCounter++
      if (mon.fainted) events += BattleEvent.Fainted(mon.entityId)
    }
    drainSeeds(participants, events)
    // A flinch only ever costs the turn it was caused on.
    for (mon in participants) mon.flinched = false
  }

  /** An eighth of the maximum, off the seeded monster and onto whoever seeded it. */
  private fun drainSeeds(
      participants: List<BattleMonState>,
      events: MutableList<BattleEvent>,
  ) {
    for (mon in participants) {
      if (mon.fainted || mon.seededBy == null) continue
      val drainer = participants.firstOrNull { it.entityId == mon.seededBy } ?: continue
      if (drainer.fainted) continue
      val drained = (mon.stats.hp / SEED_DIVISOR).coerceAtLeast(1)
      pay(mon, drained, events)
      heal(drainer, drained, events)
    }
  }

  /** The enemy AI picks a random usable move, falling back to Tackle with no pp left. */
  private fun pickEnemyMove(battle: BattleInstance, enemy: BattleMonState): MoveDef? {
    val usable = enemy.moves.filter { it.id.toInt() != 0 && it.pp > 0 }
    if (usable.isEmpty()) return moves.get(TACKLE_ID)
    return moves.get(usable[battle.rng.pick(usable.size)].id.toInt())
  }

  private fun order(
      battle: BattleInstance,
      a: TurnAction,
      b: TurnAction,
  ): List<TurnAction> {
    val pa = a.move?.priority ?: 0
    val pb = b.move?.priority ?: 0
    if (pa != pb) return if (pa > pb) listOf(a, b) else listOf(b, a)
    val sa = speed(a.attacker)
    val sb = speed(b.attacker)
    if (sa != sb) return if (sa > sb) listOf(a, b) else listOf(b, a)
    return if (battle.rng.coinFlip()) listOf(a, b) else listOf(b, a)
  }

  private fun speed(mon: BattleMonState): Int {
    val base = mon.effective(BattleStat.SPEED)
    return if (mon.status == StatusCondition.PARALYSIS) {
      base / StatusRules.PARALYSIS_SPEED_DIVISOR
    } else {
      base
    }
  }

  /**
   * Whether [attacker] gets to move. Sleep counts down, freeze and paralysis roll, and waking or
   * thawing clears the status before the move goes through.
   */
  private fun canAct(
      battle: BattleInstance,
      attacker: BattleMonState,
      events: MutableList<BattleEvent>,
  ): Boolean {
    // Flinching costs the turn outright, and only reaches a monster that had not moved yet.
    if (attacker.flinched) return false
    return statusAllowsAction(battle, attacker, events)
  }

  private fun statusAllowsAction(
      battle: BattleInstance,
      attacker: BattleMonState,
      events: MutableList<BattleEvent>,
  ): Boolean =
      when (attacker.status) {
        StatusCondition.SLEEP -> {
          // Test before spending the turn: decrementing first let a one turn sleep expire
          // without ever costing a move.
          if (attacker.sleepTurns <= 0) {
            attacker.clearStatus()
            events += BattleEvent.StatusCleared(attacker.entityId, StatusCondition.SLEEP)
            true
          } else {
            attacker.sleepTurns--
            events += BattleEvent.StatusBlockedMove(attacker.entityId, StatusCondition.SLEEP)
            false
          }
        }
        StatusCondition.FREEZE ->
            if (battle.rng.accuracyRoll() <= StatusRules.THAW_PERCENT) {
              attacker.clearStatus()
              events += BattleEvent.StatusCleared(attacker.entityId, StatusCondition.FREEZE)
              true
            } else {
              events += BattleEvent.StatusBlockedMove(attacker.entityId, StatusCondition.FREEZE)
              false
            }
        StatusCondition.PARALYSIS ->
            if (battle.rng.accuracyRoll() <= StatusRules.FULL_PARALYSIS_PERCENT) {
              events += BattleEvent.StatusBlockedMove(attacker.entityId, StatusCondition.PARALYSIS)
              false
            } else {
              true
            }
        else -> true
      }

  /** Lands [status] on [target], or returns false when something already holds it back. */
  private fun inflict(
      battle: BattleInstance,
      target: BattleMonState,
      status: StatusCondition,
      events: MutableList<BattleEvent>,
  ): Boolean {
    // One major status at a time, and a type that shrugs it off never catches it.
    if (target.status.isSet) return false
    if (StatusRules.isImmune(target.species, status)) return false
    val sleepTurns =
        if (status == StatusCondition.SLEEP) {
          StatusRules.MIN_SLEEP_TURNS +
              battle.rng.pick(StatusRules.MAX_SLEEP_TURNS - StatusRules.MIN_SLEEP_TURNS + 1)
        } else {
          0
        }
    target.applyStatus(status, sleepTurns)
    // Temporary, alongside the tick log: which monster caught what, so a status that is reported
    // in the battle text but never ticks can be told from one that was never inflicted.
    log.info {
      "STATUS ON: entity=${target.entityId} ${target.species.name} caught $status " +
          "sleep=$sleepTurns max=${target.stats.hp} hp=${target.currentHp}"
    }
    events += BattleEvent.StatusInflicted(target.entityId, status, sleepTurns)
    return true
  }

  private fun execute(
      battle: BattleInstance,
      action: TurnAction,
      events: MutableList<BattleEvent>
  ) {
    val attacker = action.attacker
    val move = action.move
    if (move == null) {
      events += BattleEvent.MoveFailed(attacker.entityId, 0)
      return
    }
    // Sleep, freeze and paralysis are checked before the move is announced or its pp is spent.
    if (!canAct(battle, attacker, events)) return
    val moveId = move.id.toShort()
    val slot = attacker.moves.indexOfFirst { it.id == moveId }
    if (slot >= 0 && attacker.moves[slot].pp > 0) {
      attacker.moves[slot].pp = (attacker.moves[slot].pp - 1).toByte()
    }
    events +=
        BattleEvent.MoveUsed(
            attacker.entityId,
            moveId,
            slot.coerceAtLeast(0),
            attacker.moves.getOrNull(slot)?.pp?.toInt() ?: 0)

    if (!accuracyCheck(battle, action, move)) {
      events += BattleEvent.MoveMissed(attacker.entityId, moveId)
      return
    }

    val stage = stageEffect(move.effect)
    val status = StatusRules.inflicted(move.effect)
    when {
      fixedDamage(battle, action, move, events) -> Unit
      move.effect == MoveEffect.LEECH_SEED -> seed(action, events)
      move.effect == MoveEffect.RESTORE_HP -> restore(action, move, events)
      move.effect == MoveEffect.REST -> rest(action, events)
      move.power > 0 -> struck(battle, action, move, events)
      stage != null -> applyStage(action, stage, events)
      // A status move whose condition will not stick reports a plain failure, as the games do.
      status != null ->
          if (!inflict(battle, action.defender, status, events)) {
            events += BattleEvent.MoveFailed(attacker.entityId, moveId)
          }
      else -> events += BattleEvent.MoveFailed(attacker.entityId, moveId)
    }
  }

  private fun accuracyCheck(battle: BattleInstance, action: TurnAction, move: MoveDef): Boolean {
    if (move.accuracy == 0) return true
    val stage =
        action.attacker.stage(BattleStat.ACCURACY) - action.defender.stage(BattleStat.EVASION)
    val threshold = StatStages.scaleAccuracy(move.accuracy, stage)
    return battle.rng.accuracyRoll() <= threshold
  }

  /**
   * A hit and everything that follows from having landed one: repeats, recoil, drain, a flinch, and
   * the user of an explosion going down with it.
   */
  private fun struck(
      battle: BattleInstance,
      action: TurnAction,
      move: MoveDef,
      events: MutableList<BattleEvent>,
  ) {
    var dealt = 0
    for (hit in 1..hitCount(battle, move)) {
      if (action.defender.fainted) break
      val landed = damage(battle, action, move, events) ?: return
      dealt += landed
    }
    if (dealt == 0) return
    when (move.effect) {
      MoveEffect.RECOIL,
      MoveEffect.RECOIL_IF_MISS -> pay(action.attacker, dealt / RECOIL_DIVISOR, events)
      MoveEffect.ABSORB,
      MoveEffect.DREAM_EATER -> heal(action.attacker, dealt / DRAIN_DIVISOR, events)
      MoveEffect.EXPLOSION -> pay(action.attacker, action.attacker.currentHp, events)
      MoveEffect.FLINCH_HIT,
      MoveEffect.FLINCH_MINIMIZE_HIT -> {
        if (!action.defender.fainted) {
          action.defender.flinched = true
          events += BattleEvent.Flinched(action.defender.entityId)
        }
      }
      else -> Unit
    }
  }

  /** How many times a move lands, which is once unless it is one of the few that repeat. */
  private fun hitCount(battle: BattleInstance, move: MoveDef): Int =
      when (move.effect) {
        MoveEffect.DOUBLE_HIT,
        MoveEffect.TWINEEDLE -> 2
        // Two and three hits are twice as likely as four and five, as the games weight it.
        MoveEffect.MULTI_HIT -> listOf(2, 2, 3, 3, 4, 5)[battle.rng.pick(6)]
        else -> 1
      }

  /**
   * The moves that ignore the damage formula and deal what they say they deal.
   *
   * True when this move was one of them and has now been resolved. They carry no power of their
   * own, so without this they fell through every branch and reported a plain failure.
   */
  private fun fixedDamage(
      battle: BattleInstance,
      action: TurnAction,
      move: MoveDef,
      events: MutableList<BattleEvent>,
  ): Boolean {
    val defender = action.defender
    val amount =
        when (move.effect) {
          MoveEffect.LEVEL_DAMAGE -> action.attacker.level
          MoveEffect.DRAGON_RAGE -> DRAGON_RAGE_DAMAGE
          // Half of what the target has left, which is never less than one point.
          MoveEffect.SUPER_FANG -> (defender.currentHp / 2).coerceAtLeast(1)
          MoveEffect.PSYWAVE -> (action.attacker.level * (battle.rng.pick(11) + 5) / 10)
          else -> return false
        }
    // A move still has to be able to touch the type it is aimed at.
    if (typeChart.effectiveness(move.type, defender.species.type1, defender.species.type2) == 0) {
      events += BattleEvent.MoveFailed(action.attacker.entityId, move.id.toShort())
      return true
    }
    defender.currentHp = (defender.currentHp - amount.coerceAtLeast(1)).coerceAtLeast(0)
    events +=
        BattleEvent.DamageDealt(defender.entityId, defender.currentHp, false, TypeChart.NEUTRAL)
    if (defender.fainted) events += BattleEvent.Fainted(defender.entityId)
    return true
  }

  /** Plants a seed, which drains at the end of every turn until the target leaves. */
  private fun seed(action: TurnAction, events: MutableList<BattleEvent>) {
    val defender = action.defender
    // Grass types cannot be seeded, and a seed cannot be planted twice.
    if (defender.species.hasType(PokemonType.GRASS) || defender.seededBy != null) {
      events += BattleEvent.MoveFailed(action.attacker.entityId, 0)
      return
    }
    defender.seededBy = action.attacker.entityId
  }

  private fun restore(action: TurnAction, move: MoveDef, events: MutableList<BattleEvent>) {
    val self = action.attacker
    if (self.currentHp >= self.stats.hp) {
      events += BattleEvent.MoveFailed(self.entityId, move.id.toShort())
      return
    }
    heal(self, self.stats.hp / 2, events)
  }

  /** Sleeps off everything: full hp, and two turns spent asleep. */
  private fun rest(action: TurnAction, events: MutableList<BattleEvent>) {
    val self = action.attacker
    if (self.currentHp >= self.stats.hp) {
      events += BattleEvent.MoveFailed(self.entityId, 0)
      return
    }
    self.status = StatusCondition.SLEEP
    self.sleepTurns = REST_SLEEP_TURNS
    events += BattleEvent.StatusInflicted(self.entityId, StatusCondition.SLEEP, REST_SLEEP_TURNS)
    heal(self, self.stats.hp, events)
  }

  private fun heal(mon: BattleMonState, amount: Int, events: MutableList<BattleEvent>) {
    if (amount <= 0 || mon.fainted) return
    mon.currentHp = (mon.currentHp + amount).coerceAtMost(mon.stats.hp)
    events += BattleEvent.HpChanged(mon.entityId, mon.currentHp)
  }

  private fun pay(mon: BattleMonState, amount: Int, events: MutableList<BattleEvent>) {
    if (amount <= 0) return
    mon.currentHp = (mon.currentHp - amount).coerceAtLeast(0)
    events += BattleEvent.HpChanged(mon.entityId, mon.currentHp)
    if (mon.fainted) events += BattleEvent.Fainted(mon.entityId)
  }

  private fun damage(
      battle: BattleInstance,
      action: TurnAction,
      move: MoveDef,
      events: MutableList<BattleEvent>,
  ): Int? {
    val attacker = action.attacker
    val defender = action.defender
    val eff = typeChart.effectiveness(move.type, defender.species.type1, defender.species.type2)
    if (eff == 0) {
      events += BattleEvent.MoveFailed(attacker.entityId, move.id.toShort())
      return null
    }
    val physical = MoveCategory.isPhysical(move.type)
    val atkStat = if (physical) BattleStat.ATTACK else BattleStat.SP_ATTACK
    val defStat = if (physical) BattleStat.DEFENSE else BattleStat.SP_DEFENSE
    val crit = battle.rng.critRoll(critDenominator(move))
    // A crit ignores the attacker's negative stages and the defender's positive stages.
    var atk =
        if (crit && attacker.stage(atkStat) < 0) attacker.unstaged(atkStat)
        else attacker.effective(atkStat)
    // A burn halves physical attack, and only physical.
    if (physical && attacker.status == StatusCondition.BURN) {
      atk /= StatusRules.BURN_ATTACK_DIVISOR
    }
    val def =
        if (crit && defender.stage(defStat) > 0) defender.unstaged(defStat)
        else defender.effective(defStat)

    var dmg = (2 * attacker.level / 5 + 2) * move.power * atk / def / 50 + 2
    if (crit) dmg *= 2
    if (attacker.species.hasType(move.type)) dmg = dmg * 3 / 2
    dmg = dmg * eff / TypeChart.NEUTRAL
    dmg = dmg * battle.rng.damageRoll() / 100
    if (dmg < 1) dmg = 1

    defender.currentHp = (defender.currentHp - dmg).coerceAtLeast(0)
    events += BattleEvent.DamageDealt(defender.entityId, defender.currentHp, crit, eff)
    if (defender.fainted) {
      events += BattleEvent.Fainted(defender.entityId)
      return dmg
    }
    secondaryEffect(move.effect)?.let { secondary ->
      if (move.secondaryEffectChance > 0 &&
          battle.rng.accuracyRoll() <= move.secondaryEffectChance) {
        applyStage(action, secondary, events)
      }
    }
    // A status riding on a damaging hit rolls against the same secondary chance.
    val status = StatusRules.inflicted(move.effect)
    if (status != null &&
        StatusRules.isSecondary(move.effect) &&
        move.secondaryEffectChance > 0 &&
        battle.rng.accuracyRoll() <= move.secondaryEffectChance) {
      inflict(battle, defender, status, events)
    }
    return dmg
  }

  /** Slash and its kind crit far more often, which is the whole point of them. */
  private fun critDenominator(move: MoveDef): Int =
      if (move.effect == MoveEffect.HIGH_CRITICAL) HIGH_CRIT_DENOMINATOR else CRIT_DENOMINATOR

  private fun applyStage(
      action: TurnAction,
      effect: StageEffect,
      events: MutableList<BattleEvent>,
  ) {
    val target = if (effect.onSelf) action.attacker else action.defender
    val applied = target.changeStage(effect.stat, effect.delta)
    val value =
        when (effect.stat) {
          BattleStat.ACCURACY,
          BattleStat.EVASION -> 0
          else -> target.effective(effect.stat)
        }
    events +=
        BattleEvent.StageChanged(
            target.entityId,
            effect.stat,
            target.stage(effect.stat),
            value,
            effect.delta,
            applied == 0)
  }

  private fun stageEffect(effect: MoveEffect): StageEffect? =
      when (effect) {
        MoveEffect.ATTACK_UP -> StageEffect(BattleStat.ATTACK, 1, true)
        MoveEffect.DEFENSE_UP -> StageEffect(BattleStat.DEFENSE, 1, true)
        MoveEffect.SPEED_UP -> StageEffect(BattleStat.SPEED, 1, true)
        MoveEffect.SPECIAL_ATTACK_UP -> StageEffect(BattleStat.SP_ATTACK, 1, true)
        MoveEffect.SPECIAL_DEFENSE_UP -> StageEffect(BattleStat.SP_DEFENSE, 1, true)
        MoveEffect.ACCURACY_UP -> StageEffect(BattleStat.ACCURACY, 1, true)
        MoveEffect.EVASION_UP -> StageEffect(BattleStat.EVASION, 1, true)
        MoveEffect.ATTACK_UP_2 -> StageEffect(BattleStat.ATTACK, 2, true)
        MoveEffect.DEFENSE_UP_2 -> StageEffect(BattleStat.DEFENSE, 2, true)
        MoveEffect.SPEED_UP_2 -> StageEffect(BattleStat.SPEED, 2, true)
        MoveEffect.SPECIAL_ATTACK_UP_2 -> StageEffect(BattleStat.SP_ATTACK, 2, true)
        MoveEffect.SPECIAL_DEFENSE_UP_2 -> StageEffect(BattleStat.SP_DEFENSE, 2, true)
        MoveEffect.ACCURACY_UP_2 -> StageEffect(BattleStat.ACCURACY, 2, true)
        MoveEffect.EVASION_UP_2 -> StageEffect(BattleStat.EVASION, 2, true)
        MoveEffect.ATTACK_DOWN -> StageEffect(BattleStat.ATTACK, -1, false)
        MoveEffect.DEFENSE_DOWN -> StageEffect(BattleStat.DEFENSE, -1, false)
        MoveEffect.SPEED_DOWN -> StageEffect(BattleStat.SPEED, -1, false)
        MoveEffect.SPECIAL_ATTACK_DOWN -> StageEffect(BattleStat.SP_ATTACK, -1, false)
        MoveEffect.SPECIAL_DEFENSE_DOWN -> StageEffect(BattleStat.SP_DEFENSE, -1, false)
        MoveEffect.ACCURACY_DOWN -> StageEffect(BattleStat.ACCURACY, -1, false)
        MoveEffect.EVASION_DOWN -> StageEffect(BattleStat.EVASION, -1, false)
        MoveEffect.ATTACK_DOWN_2 -> StageEffect(BattleStat.ATTACK, -2, false)
        MoveEffect.DEFENSE_DOWN_2 -> StageEffect(BattleStat.DEFENSE, -2, false)
        MoveEffect.SPEED_DOWN_2 -> StageEffect(BattleStat.SPEED, -2, false)
        MoveEffect.SPECIAL_ATTACK_DOWN_2 -> StageEffect(BattleStat.SP_ATTACK, -2, false)
        MoveEffect.SPECIAL_DEFENSE_DOWN_2 -> StageEffect(BattleStat.SP_DEFENSE, -2, false)
        MoveEffect.ACCURACY_DOWN_2 -> StageEffect(BattleStat.ACCURACY, -2, false)
        MoveEffect.EVASION_DOWN_2 -> StageEffect(BattleStat.EVASION, -2, false)
        else -> null
      }

  private fun secondaryEffect(effect: MoveEffect): StageEffect? =
      when (effect) {
        MoveEffect.ATTACK_DOWN_HIT -> StageEffect(BattleStat.ATTACK, -1, false)
        MoveEffect.DEFENSE_DOWN_HIT -> StageEffect(BattleStat.DEFENSE, -1, false)
        MoveEffect.SPEED_DOWN_HIT -> StageEffect(BattleStat.SPEED, -1, false)
        MoveEffect.SPECIAL_ATTACK_DOWN_HIT -> StageEffect(BattleStat.SP_ATTACK, -1, false)
        MoveEffect.SPECIAL_DEFENSE_DOWN_HIT -> StageEffect(BattleStat.SP_DEFENSE, -1, false)
        MoveEffect.ACCURACY_DOWN_HIT -> StageEffect(BattleStat.ACCURACY, -1, false)
        MoveEffect.EVASION_DOWN_HIT -> StageEffect(BattleStat.EVASION, -1, false)
        MoveEffect.ATTACK_UP_HIT -> StageEffect(BattleStat.ATTACK, 1, true)
        MoveEffect.DEFENSE_UP_HIT -> StageEffect(BattleStat.DEFENSE, 1, true)
        else -> null
      }
}
