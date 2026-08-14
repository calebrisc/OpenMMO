package de.fiereu.openmmo.server.game.battle

/**
 * The byte opening the player's own side of a battle, kept tunable while its meaning is unknown.
 *
 * Every capture carries a 1 and the codec wrote one back without reading it, while the opposing
 * side carries a live value in the same position. If it counts how many monsters a side can have
 * out, a shared battle needs it above one, and that would explain why adding a second monster to a
 * side afterwards changes nothing: the client was told at the start there is room for one.
 */
object BattleFieldTuning {
  @Volatile var playerSideOpener: Int = 1

  /**
   * Whether to open a battle by declaring it a double, using the scene packet the server has never
   * sent. Its own field is named for it, which makes it the most direct way left to ask the client
   * for two monsters on a side after the opener byte turned out not to be a count.
   */
  @Volatile var announceDouble: Boolean = false

  /** Sent with the scene when [announceDouble] is on. Both are unknown, so both are tunable. */
  @Volatile var battleType: Int = 0

  @Volatile var perspective: Int = 0
}
