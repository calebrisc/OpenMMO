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

/**
 * The flags byte on the local player state, which the server has always sent as zero.
 *
 * Seven days of play produced no movement packet carrying a bit beyond direction and running, so
 * the client never enters a bike state at all rather than the server dropping one. Whatever refuses
 * is on the client's side, and this byte is the only thing the login state offers that could carry
 * a permission it is waiting for.
 */
object PlayerStateTuning {
  @Volatile var flags: Byte = 0
}

/**
 * How a container is sent again after its contents moved.
 *
 * The box draws correctly at login and wrongly after a move, which is the whole evidence for this
 * being about the resend rather than the record: the same monsters, through the same codec, land
 * right the first time. So what differs is how the client is told, and the login is the only shape
 * known to work. Whether a container packet replaces what the client holds or adds to it is
 * undecoded, and the difference only shows once there is something to replace.
 *
 * Tunable with /probe box so the three can be told apart in one sitting instead of one deploy each.
 */
object BoxSyncTuning {
  /**
   * 0 sends each container once, as the server always has. 1 empties the container first and then
   * fills it, for a client that adds rather than replaces. 2 sends every container the login sends,
   * in the login's order, which is the shape known to draw correctly. None of those three changed
   * anything on a live client, which is the evidence that the box is not drawn from a container
   * packet at all.
   *
   * 3 adds `StorageContextWindowPacket`, which the server has never sent. Its row is a slot, four
   * unnamed shorts, a quantity, a ball and a location -- a ball and a catch location are things a
   * monster has, and a compact row per cell is what a thirty cell grid would be painted from,
   * rather than the full record we send for each. The four shorts are a guess and the point of
   * sending it is to find out whether the grid moves at all.
   */
  @Volatile var mode: Int = 1
}

/**
 * Whether the trade board is answered at all.
 *
 * A page packet is a shape this client has never been sent by us, and the last time an undecoded
 * s2c was tried on a live client three of its five values killed the client outright. So the board
 * stays silent until somebody deliberately turns it on with /probe gtl, and the first look at it is
 * a decision rather than an accident.
 */
object GtlTuning {
  @Volatile var answerBoards: Boolean = false
}

/**
 * Whether the flag table is built from the player or copied from the capture.
 *
 * Building it is the correct thing and the fallback exists because getting it wrong is not a
 * cosmetic failure: the client reads the table while it builds the party, so a table it cannot make
 * sense of takes the client down on login, and a player cannot type a command to undo that. So it
 * is proven on one connection with /probe flagtable before it becomes what everybody gets.
 */
object FlagTuning {
  @Volatile var tableFromCharacter: Boolean = false
}
