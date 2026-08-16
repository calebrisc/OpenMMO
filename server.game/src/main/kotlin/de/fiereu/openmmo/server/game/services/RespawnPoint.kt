package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.common.enums.Direction

/**
 * Where a player wakes up when their whole party faints.
 *
 * The games keep a heal location in the save block. This server has no column for one, so it is
 * kept in the story vars a character already carries: they are persisted, and a key that is not a
 * GBA var name is skipped when the vars are sent to the client, so these never reach the wire.
 *
 * A character who has never healed anywhere has no point, and [of] answers null rather than
 * guessing a map to drop them on.
 */
internal object RespawnPoint {

  const val REGION = "respawn/region"
  const val BANK = "respawn/bank"
  const val MAP = "respawn/map"
  const val X = "respawn/x"
  const val Y = "respawn/y"
  const val SET = "respawn/set"

  fun of(vars: Map<String, Int>): DynamicWarp? {
    if (vars[SET] != 1) return null
    val region = vars[REGION] ?: return null
    val bank = vars[BANK] ?: return null
    val map = vars[MAP] ?: return null
    val x = vars[X] ?: return null
    val y = vars[Y] ?: return null
    // Facing down is how every one of these rooms is entered and how the games leave you standing.
    return DynamicWarp(
        regionId = region.toByte(),
        bankId = bank.toByte(),
        mapId = map.toByte(),
        x = x.toShort(),
        y = y.toShort(),
        facing = Direction.DOWN,
    )
  }
}
