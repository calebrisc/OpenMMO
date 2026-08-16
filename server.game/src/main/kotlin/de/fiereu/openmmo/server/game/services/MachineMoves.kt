package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.moves.MoveRegistry

/**
 * Which move each machine teaches.
 *
 * **This is the Gen 5 list, not the GBA one, because the client is the one that has to agree.** It
 * was the GBA table first, taken from the decomp that every map here comes from, and that was
 * wrong: the item table carries TM01 to TM95 and HM01 to HM06, which is the Gen 5 set exactly, and
 * the client labels TM09 Venoshock. The GBA table calls TM09 Bullet Seed, so using one taught a
 * move that had nothing to do with the name on the box, over the top of a move the monster already
 * knew.
 *
 * Moves are named rather than numbered so the mapping can be read against the client's own labels,
 * and are resolved through [MoveRegistry] at the point of use. The registry is a GBA one holding
 * about 355 moves, so every machine whose move arrived after Gen 3 -- Venoshock among them --
 * resolves to nothing. That is reported to the player as the machine not being in the game yet,
 * which is the honest answer and, more to the point, is not the answer "here is a different move".
 */
internal object MachineMoves {

  private val machines: Map<String, String> =
      mapOf(
          "TM01" to "Hone Claws",
          "TM02" to "Dragon Claw",
          "TM03" to "Psyshock",
          "TM04" to "Calm Mind",
          "TM05" to "Roar",
          "TM06" to "Toxic",
          "TM07" to "Hail",
          "TM08" to "Bulk Up",
          "TM09" to "Venoshock",
          "TM10" to "Hidden Power",
          "TM11" to "Sunny Day",
          "TM12" to "Taunt",
          "TM13" to "Ice Beam",
          "TM14" to "Blizzard",
          "TM15" to "Hyper Beam",
          "TM16" to "Light Screen",
          "TM17" to "Protect",
          "TM18" to "Rain Dance",
          "TM19" to "Telekinesis",
          "TM20" to "Safeguard",
          "TM21" to "Frustration",
          "TM22" to "SolarBeam",
          "TM23" to "Smack Down",
          "TM24" to "Thunderbolt",
          "TM25" to "Thunder",
          "TM26" to "Earthquake",
          "TM27" to "Return",
          "TM28" to "Dig",
          "TM29" to "Psychic",
          "TM30" to "Shadow Ball",
          "TM31" to "Brick Break",
          "TM32" to "Double Team",
          "TM33" to "Reflect",
          "TM34" to "Sludge Wave",
          "TM35" to "Flamethrower",
          "TM36" to "Sludge Bomb",
          "TM37" to "Sandstorm",
          "TM38" to "Fire Blast",
          "TM39" to "Rock Tomb",
          "TM40" to "Aerial Ace",
          "TM41" to "Torment",
          "TM42" to "Facade",
          "TM43" to "Flame Charge",
          "TM44" to "Rest",
          "TM45" to "Attract",
          "TM46" to "Thief",
          "TM47" to "Low Sweep",
          "TM48" to "Round",
          "TM49" to "Echoed Voice",
          "TM50" to "Overheat",
          "TM51" to "Ally Switch",
          "TM52" to "Focus Blast",
          "TM53" to "Energy Ball",
          "TM54" to "False Swipe",
          "TM55" to "Scald",
          "TM56" to "Fling",
          "TM57" to "Charge Beam",
          "TM58" to "Sky Drop",
          "TM59" to "Incinerate",
          "TM60" to "Quash",
          "TM61" to "Will-O-Wisp",
          "TM62" to "Acrobatics",
          "TM63" to "Embargo",
          "TM64" to "Explosion",
          "TM65" to "Shadow Claw",
          "TM66" to "Payback",
          "TM67" to "Retaliate",
          "TM68" to "Giga Impact",
          "TM69" to "Rock Polish",
          "TM70" to "Flash",
          "TM71" to "Stone Edge",
          "TM72" to "Volt Switch",
          "TM73" to "Thunder Wave",
          "TM74" to "Gyro Ball",
          "TM75" to "Swords Dance",
          "TM76" to "Struggle Bug",
          "TM77" to "Psych Up",
          "TM78" to "Bulldoze",
          "TM79" to "Frost Breath",
          "TM80" to "Rock Slide",
          "TM81" to "X-Scissor",
          "TM82" to "Dragon Tail",
          "TM83" to "Work Up",
          "TM84" to "Poison Jab",
          "TM85" to "Dream Eater",
          "TM86" to "Grass Knot",
          "TM87" to "Swagger",
          "TM88" to "Pluck",
          "TM89" to "U-turn",
          "TM90" to "Substitute",
          "TM91" to "Flash Cannon",
          "TM92" to "Trick Room",
          "TM93" to "Wild Charge",
          "TM94" to "Rock Smash",
          "TM95" to "Snarl",
          "HM01" to "Cut",
          "HM02" to "Fly",
          "HM03" to "Surf",
          "HM04" to "Strength",
          "HM05" to "Waterfall",
          "HM06" to "Dive",
      )

  @Volatile private var byName: Map<String, Int>? = null

  /** The move this machine is labelled with, whether or not this game has it. */
  fun moveNameFor(machine: String): String? = machines[machine.uppercase()]

  /**
   * The move id this machine teaches, or null when the move is not one this server knows.
   *
   * Names are compared with the punctuation and spacing taken out, because a registry built from
   * one generation writes SolarBeam, Will-o-wisp and X-Scissor however that generation wrote them.
   */
  fun moveIdFor(machine: String, moves: MoveRegistry): Int? {
    val name = moveNameFor(machine) ?: return null
    val index = byName ?: moves.all().associate { normalise(it.name) to it.id }.also { byName = it }
    return index[normalise(name)]
  }

  private fun normalise(name: String) = name.lowercase().filter { it.isLetterOrDigit() }
}
