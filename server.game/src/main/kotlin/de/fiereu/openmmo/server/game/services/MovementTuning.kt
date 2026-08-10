package de.fiereu.openmmo.server.game.services

/** pokeserver-only: live-tunable relayed movement modes, adjusted via /shoplab mm. */
object MovementTuning {
  @Volatile var walk: Int = 1
  @Volatile var run: Int = 2
}
