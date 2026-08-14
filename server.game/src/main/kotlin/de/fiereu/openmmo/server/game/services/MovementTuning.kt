package de.fiereu.openmmo.server.game.services

/**
 * The movement mode stamped on a step relayed to everyone else.
 *
 * Both values were found by trying them against two live clients. At 0 a player walks, runs and
 * moves over water correctly on everyone else's screen; 1, 3 and 4 leave the observer frozen after
 * a single step, and the old run value of 2 broke movement on water. Earlier guesses of 1 for
 * walking and 2 for running are what left players standing still on each other's screens.
 *
 * So the byte is not the walk-or-run selector it was taken for. It is kept as two values, and
 * tunable with /probe mm, only because what it really means is still unknown.
 */
object MovementTuning {
  /** Confirmed live. */
  @Volatile var walk: Int = 0

  /** Confirmed live, including over water. */
  @Volatile var run: Int = 0
}
