package de.fiereu.openmmo.server.game.services

/**
 * The movement mode stamped on a step relayed to everyone else.
 *
 * Found by trying values against two live clients: at 0 both players see each other walk normally,
 * while 1, 3 and 4 leave the other player frozen after a single step. An earlier guess of 1 for
 * walking is what left players standing still on each other's screens.
 *
 * pokeserver-only, and tunable live with /probe mm because the remaining values are still guesses.
 */
object MovementTuning {
  /** Confirmed live: the only value that makes an observer animate an ordinary step. */
  @Volatile var walk: Int = 0

  /** Not yet confirmed the way [walk] was. Running may well want its own value. */
  @Volatile var run: Int = 2
}
