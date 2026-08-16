package de.fiereu.openmmo.server.game.services

/**
 * Which move each machine teaches, in the order the games keep them.
 *
 * Taken from the decomp's sTMHMMoves table and keyed by the machine's own name, because the
 * generated item data carries a name and a price and nothing else -- an item cannot say what it
 * does, so this is where a machine's move has to live.
 *
 * HM07 and HM08 are here for completeness. Neither exists as an item in this game's table, which
 * carries HM01 to HM06 only, so nothing can hand one over yet.
 */
internal object MachineMoves {

  private val byMachine: Map<String, Int> =
      mapOf(
          "TM01" to 264, // Focus Punch
          "TM02" to 337, // Dragon Claw
          "TM03" to 352, // Water Pulse
          "TM04" to 347, // Calm Mind
          "TM05" to 46, // Roar
          "TM06" to 92, // Toxic
          "TM07" to 258, // Hail
          "TM08" to 339, // Bulk Up
          "TM09" to 331, // Bullet Seed
          "TM10" to 237, // Hidden Power
          "TM11" to 241, // Sunny Day
          "TM12" to 269, // Taunt
          "TM13" to 58, // Ice Beam
          "TM14" to 59, // Blizzard
          "TM15" to 63, // Hyper Beam
          "TM16" to 113, // Light Screen
          "TM17" to 182, // Protect
          "TM18" to 240, // Rain Dance
          "TM19" to 202, // Giga Drain
          "TM20" to 219, // Safeguard
          "TM21" to 218, // Frustration
          "TM22" to 76, // Solar Beam
          "TM23" to 231, // Iron Tail
          "TM24" to 85, // Thunderbolt
          "TM25" to 87, // Thunder
          "TM26" to 89, // Earthquake
          "TM27" to 216, // Return
          "TM28" to 91, // Dig
          "TM29" to 94, // Psychic
          "TM30" to 247, // Shadow Ball
          "TM31" to 280, // Brick Break
          "TM32" to 104, // Double Team
          "TM33" to 115, // Reflect
          "TM34" to 351, // Shock Wave
          "TM35" to 53, // Flamethrower
          "TM36" to 188, // Sludge Bomb
          "TM37" to 201, // Sandstorm
          "TM38" to 126, // Fire Blast
          "TM39" to 317, // Rock Tomb
          "TM40" to 332, // Aerial Ace
          "TM41" to 259, // Torment
          "TM42" to 263, // Facade
          "TM43" to 290, // Secret Power
          "TM44" to 156, // Rest
          "TM45" to 213, // Attract
          "TM46" to 168, // Thief
          "TM47" to 211, // Steel Wing
          "TM48" to 285, // Skill Swap
          "TM49" to 289, // Snatch
          "TM50" to 315, // Overheat
          "HM01" to 15, // Cut
          "HM02" to 19, // Fly
          "HM03" to 57, // Surf
          "HM04" to 70, // Strength
          "HM05" to 148, // Flash
          "HM06" to 249, // Rock Smash
          "HM07" to 127, // Waterfall
          "HM08" to 291, // Dive
      )

  /** The move [machine] teaches, by its item name, or null when the name is not a machine. */
  fun moveFor(machine: String): Int? = byMachine[machine.uppercase()]

  /** True when this item name is a machine at all, which is how a bag item is recognised. */
  fun isMachine(machine: String): Boolean = machine.uppercase() in byMachine
}
