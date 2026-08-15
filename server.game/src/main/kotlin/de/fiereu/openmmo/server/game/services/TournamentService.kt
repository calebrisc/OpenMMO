package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val MIN_ENTRANTS = 2

/** One knockout tournament. The server runs one at a time, which is plenty for a private world. */
private class Tournament {
  val entrants = LinkedHashSet<Long>()

  /** Still in the running, in the order they will be paired. */
  val remaining = mutableListOf<Long>()

  /** Won their match this round, or were given a bye. */
  val advanced = mutableListOf<Long>()

  /** Matches started this round and not yet decided. */
  val pending = mutableSetOf<Long>()

  var round = 0
  var running = false
  var champion: Long? = null
}

/**
 * Knockout tournaments, run as a bracket of duels.
 *
 * A duel already settles two players fairly, so a tournament is only the bookkeeping around them:
 * who plays whom, who is left, and when the round is over. Matches within a round run at the same
 * time rather than one after another, so nobody waits on a fight they are not in.
 *
 * The bracket is announced in chat. The client has a packet for drawing one, but its fields carry
 * no names this server can read and no capture says what belongs in them, and a wrong guess at a
 * packet like that has taken clients down before.
 */
@Singleton
class TournamentService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val sessionRegistry: SessionRegistry,
    private val duels: DuelService,
) {

  private val lock = Any()
  private var current: Tournament? = null

  init {
    // A finished duel is what advances the bracket. Without this the winner of a match would be
    // decided and the tournament would never hear about it.
    duels.onDuelFinished { winner, loser -> onDuelFinished(winner, loser) }
  }

  fun describe(charId: Long): String =
      synchronized(lock) {
        val tournament = current ?: return "No tournament. /tourney join to open one."
        val names = tournament.entrants.mapNotNull { nameOf(it) }
        if (!tournament.running) {
          return "Sign-ups open: ${names.joinToString()}. /tourney start when everyone is in."
        }
        tournament.champion?.let {
          return "${nameOf(it)} won the tournament."
        }
        val left = tournament.remaining.mapNotNull { nameOf(it) }
        val fighting = tournament.pending.mapNotNull { nameOf(it) }
        "Round ${tournament.round}. Still in: ${left.joinToString()}. " +
            "Fighting now: ${fighting.joinToString().ifBlank { "nobody" }}."
      }

  fun join(charId: Long): String =
      synchronized(lock) {
        val tournament = current ?: Tournament().also { current = it }
        if (tournament.running) return "The tournament has already started."
        if (!tournament.entrants.add(charId)) return "You are already signed up."
        val name = nameOf(charId) ?: "Somebody"
        announce(tournament, "$name joined the tournament (${tournament.entrants.size} in).")
        "Signed up. ${tournament.entrants.size} in so far."
      }

  fun leave(charId: Long): String =
      synchronized(lock) {
        val tournament = current ?: return "No tournament is open."
        if (tournament.running) return "You cannot withdraw once it has started. Forfeit instead."
        if (!tournament.entrants.remove(charId)) return "You are not signed up."
        if (tournament.entrants.isEmpty()) current = null
        "Withdrawn."
      }

  fun cancel(): String =
      synchronized(lock) {
        val tournament = current ?: return "No tournament is open."
        announce(tournament, "The tournament was cancelled.")
        current = null
        "Tournament cancelled."
      }

  fun start(): String {
    val toRun: Tournament
    synchronized(lock) {
      val tournament = current ?: return "No tournament is open. /tourney join first."
      if (tournament.running) return "It has already started."
      val online = tournament.entrants.filter { sessionRegistry.getByCharacterId(it) != null }
      if (online.size < MIN_ENTRANTS) return "You need at least $MIN_ENTRANTS players online."
      tournament.entrants.retainAll(online.toSet())
      tournament.running = true
      tournament.remaining.clear()
      tournament.remaining += online.shuffled()
      toRun = tournament
    }
    beginRound(toRun)
    return "Tournament started."
  }

  /** Pairs everyone still in and starts every match of the round at once. */
  private fun beginRound(tournament: Tournament) {
    val matches: List<Pair<Long, Long>>
    val bye: Long?
    synchronized(lock) {
      tournament.round += 1
      tournament.advanced.clear()
      tournament.pending.clear()
      val queue = tournament.remaining.toMutableList()
      // An odd bracket gives one player a free pass rather than leaving them unpaired forever.
      bye = if (queue.size % 2 == 1) queue.removeAt(queue.size - 1) else null
      matches = queue.chunked(2).map { it[0] to it[1] }
      bye?.let { tournament.advanced += it }
      matches.forEach { (a, b) ->
        tournament.pending += a
        tournament.pending += b
      }
    }

    bye?.let { announce(tournament, "${nameOf(it)} has a bye this round.") }
    val summary =
        matches.joinToString("; ") { (a, b) -> "${nameOf(a)} vs ${nameOf(b)}" }
    announce(tournament, "Round ${tournament.round}: $summary")

    for ((a, b) in matches) {
      val aSession = sessionRegistry.getByCharacterId(a)
      val bSession = sessionRegistry.getByCharacterId(b)
      val aName = nameOf(a)
      val bName = nameOf(b)
      if (aSession == null || bSession == null || aName == null || bName == null) {
        // Somebody left between the draw and the bell, so their opponent goes through.
        val walkover = if (aSession != null) a else b
        log.info { "Tournament walkover to $walkover" }
        recordResult(tournament, winner = walkover, loser = if (walkover == a) b else a)
        continue
      }
      val started = duels.start(aSession, a, aName, bSession, b, bName)
      if (!started) {
        log.warn { "Tournament match $aName vs $bName could not start" }
        recordResult(tournament, winner = a, loser = b)
      }
    }
  }

  private fun onDuelFinished(winner: Long, loser: Long) {
    val tournament = synchronized(lock) { current } ?: return
    synchronized(lock) {
      if (!tournament.running) return
      if (winner !in tournament.pending || loser !in tournament.pending) return
    }
    recordResult(tournament, winner, loser)
  }

  private fun recordResult(tournament: Tournament, winner: Long, loser: Long) {
    val roundOver: Boolean
    synchronized(lock) {
      if (!tournament.pending.remove(winner)) return
      tournament.pending.remove(loser)
      tournament.advanced += winner
      tournament.remaining.remove(loser)
      roundOver = tournament.pending.isEmpty()
    }
    announce(tournament, "${nameOf(winner)} beat ${nameOf(loser)}.")
    if (!roundOver) return

    val finished: Long?
    synchronized(lock) {
      tournament.remaining.clear()
      tournament.remaining += tournament.advanced
      finished = tournament.remaining.singleOrNull()
      if (finished != null) tournament.champion = finished
    }
    if (finished != null) {
      announce(tournament, "${nameOf(finished)} wins the tournament!")
      log.info { "Tournament won by char=$finished" }
      synchronized(lock) { current = null }
      return
    }
    beginRound(tournament)
  }

  private fun nameOf(charId: Long): String? = characterStore.getCharacter(charId)?.info?.name

  private fun announce(tournament: Tournament, message: String) {
    val packet = notice(message)
    tournament.entrants.forEach { sessionRegistry.getByCharacterId(it)?.send(packet) }
  }
}
