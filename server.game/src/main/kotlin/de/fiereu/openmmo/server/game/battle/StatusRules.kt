package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.MoveEffect
import de.fiereu.openmmo.common.enums.PokemonType
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.pokemon.SpeciesDef

/** The Gen 3 rules for the major status conditions, kept apart from the turn loop that applies. */
object StatusRules {

  /** Sleep lasts between these many turns, picked when it lands. */
  const val MIN_SLEEP_TURNS = 1
  const val MAX_SLEEP_TURNS = 3

  /** A paralysed monster loses one turn in four. */
  const val FULL_PARALYSIS_PERCENT = 25

  /** A frozen monster thaws on its own one turn in five. */
  const val THAW_PERCENT = 20

  /** Paralysis quarters speed. */
  const val PARALYSIS_SPEED_DIVISOR = 4

  /** Burn halves physical attack. */
  const val BURN_ATTACK_DIVISOR = 2

  private const val POISON_DIVISOR = 8
  private const val BURN_DIVISOR = 16
  private const val TOXIC_DIVISOR = 16

  /** The status a move inflicts, or null when it inflicts none. */
  fun inflicted(effect: MoveEffect): StatusCondition? =
      when (effect) {
        MoveEffect.SLEEP -> StatusCondition.SLEEP
        MoveEffect.POISON,
        MoveEffect.POISON_HIT,
        MoveEffect.POISON_TAIL -> StatusCondition.POISON
        MoveEffect.TOXIC,
        MoveEffect.POISON_FANG -> StatusCondition.TOXIC
        MoveEffect.BURN_HIT -> StatusCondition.BURN
        MoveEffect.FREEZE_HIT -> StatusCondition.FREEZE
        MoveEffect.PARALYZE,
        MoveEffect.PARALYZE_HIT -> StatusCondition.PARALYSIS
        else -> null
      }

  /** True when [effect] rides on a damaging hit and so rolls against the secondary chance. */
  fun isSecondary(effect: MoveEffect): Boolean =
      effect == MoveEffect.POISON_HIT ||
          effect == MoveEffect.BURN_HIT ||
          effect == MoveEffect.FREEZE_HIT ||
          effect == MoveEffect.PARALYZE_HIT ||
          effect == MoveEffect.POISON_FANG ||
          effect == MoveEffect.POISON_TAIL

  /** A type immune to the condition cannot catch it. */
  fun isImmune(species: SpeciesDef, status: StatusCondition): Boolean =
      when (status) {
        StatusCondition.BURN -> species.hasType(PokemonType.FIRE)
        StatusCondition.FREEZE -> species.hasType(PokemonType.ICE)
        StatusCondition.PARALYSIS -> species.hasType(PokemonType.ELECTRIC)
        StatusCondition.POISON,
        StatusCondition.TOXIC ->
            species.hasType(PokemonType.POISON) || species.hasType(PokemonType.STEEL)
        StatusCondition.SLEEP,
        StatusCondition.NONE -> false
      }

  /**
   * Damage taken at the end of a turn. [toxicCounter] counts the turns this monster has been badly
   * poisoned, starting at 1. Always at least 1 so a status never stalls at zero damage.
   */
  fun endOfTurnDamage(status: StatusCondition, maxHp: Int, toxicCounter: Int): Int =
      when (status) {
        StatusCondition.POISON -> (maxHp / POISON_DIVISOR).coerceAtLeast(1)
        StatusCondition.BURN -> (maxHp / BURN_DIVISOR).coerceAtLeast(1)
        StatusCondition.TOXIC -> (maxHp * toxicCounter / TOXIC_DIVISOR).coerceAtLeast(1)
        else -> 0
      }

  /**
   * The byte the client reads for a monster's status, using the decomp's status1 bit layout: the
   * low three bits hold the remaining sleep turns and each other condition owns one bit.
   *
   * Not verified against the live client yet. Every rule above is server side and correct either
   * way, so a wrong value here shows the wrong icon without changing how a battle plays.
   */
  fun wireValue(status: StatusCondition, sleepTurnsLeft: Int): Byte =
      when (status) {
        StatusCondition.NONE -> 0
        StatusCondition.SLEEP -> sleepTurnsLeft.coerceIn(1, 7).toByte()
        StatusCondition.POISON -> 0x08
        StatusCondition.BURN -> 0x10
        StatusCondition.FREEZE -> 0x20
        StatusCondition.PARALYSIS -> 0x40
        // 0x80 as a signed byte.
        StatusCondition.TOXIC -> -128
      }
}
