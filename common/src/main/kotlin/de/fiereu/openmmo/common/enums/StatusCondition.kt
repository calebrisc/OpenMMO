package de.fiereu.openmmo.common.enums

/**
 * A major status condition. A monster carries at most one, it survives the battle, and only a
 * Pokemon Center or a healing item clears it.
 *
 * Confusion is deliberately absent: it is a volatile condition that ends with the battle and lives
 * alongside one of these rather than replacing it.
 */
enum class StatusCondition {
  NONE,
  SLEEP,
  POISON,
  BURN,
  FREEZE,
  PARALYSIS,
  /** Badly poisoned. The damage climbs every turn instead of staying at a fixed fraction. */
  TOXIC;

  val isSet: Boolean
    get() = this != NONE

  companion object {
    /** Parses a stored name, falling back to [NONE] rather than throwing on unknown data. */
    fun parse(name: String?): StatusCondition = entries.firstOrNull { it.name == name } ?: NONE
  }
}
