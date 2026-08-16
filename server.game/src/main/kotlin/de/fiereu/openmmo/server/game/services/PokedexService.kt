package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.PokedexSpeciesUnlockPacket
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.StoredCharacter
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/**
 * Where a player's dex progress is kept and how it is read back.
 *
 * There is no dex table. Story flags are already a durable per character set of opaque strings that
 * ignores keys it does not recognise, so the two dex sets ride in there under their own prefixes
 * and need no migration.
 */
object Pokedex {
  private const val SEEN_PREFIX = "dex.seen."
  private const val CAUGHT_PREFIX = "dex.caught."

  fun seenKey(speciesId: Int): String = "$SEEN_PREFIX$speciesId"

  fun caughtKey(speciesId: Int): String = "$CAUGHT_PREFIX$speciesId"

  private fun idsWithPrefix(flags: Collection<String>, prefix: String): List<Int> =
      flags.mapNotNull {
        if (it.startsWith(prefix)) it.removePrefix(prefix).toIntOrNull() else null
      }

  /**
   * Everything the player owns counts as caught whether or not a flag was ever written, which
   * backfills every monster caught before the dex was recorded at all.
   */
  fun caughtOf(stored: StoredCharacter): List<Short> =
      (idsWithPrefix(stored.storyFlags, CAUGHT_PREFIX) +
              stored.pokemon.map { it.dexId } +
              stored.pcStorage.map { it.dexId })
          .distinct()
          .sorted()
          .map { it.toShort() }

  /** Seen covers caught, so a monster can never be caught without also being seen. */
  fun seenOf(stored: StoredCharacter): List<Short> =
      (idsWithPrefix(stored.storyFlags, SEEN_PREFIX).map { it.toShort() } + caughtOf(stored))
          .distinct()
          .sorted()
}

/**
 * Registers species in the dex as they are met.
 *
 * Nothing recorded the dex before this: the packet that unlocks a species existed and was never
 * sent from anywhere, and the login state hardcoded both dex lists to empty, so no amount of
 * catching ever filled it.
 */
@Singleton
class PokedexService @Inject constructor(private val characterStore: CharacterStore) {

  /**
   * Unlocks everything this character already knows, which a login has to do for itself.
   *
   * The unlock packet was only ever sent at the moment a species was first met, so a dex filled
   * itself in over a session and read zero again on the next login: nothing replayed what was
   * already known. The flags were right the whole time -- 57 seen and 28 caught sat in the database
   * while the screen showed nothing.
   *
   * The packet carries a species and no more, so it cannot say seen from caught on its own; the
   * split is meant to come from the login state's two lists.
   */
  fun sendKnown(charId: Long, ctx: SessionContext) {
    val stored = characterStore.getCharacter(charId) ?: return
    val known = Pokedex.seenOf(stored)
    known.forEach { ctx.send(PokedexSpeciesUnlockPacket(it)) }
    log.info { "char=$charId dex replayed ${known.size} known species on login" }
  }

  /** Met in a battle. */
  fun recordSeen(charId: Long, ctx: SessionContext, speciesId: Int) =
      record(charId, ctx, speciesId, Pokedex.seenKey(speciesId), "seen")

  /** Added to the player's own monsters, by a catch or otherwise. */
  fun recordCaught(charId: Long, ctx: SessionContext, speciesId: Int) {
    record(charId, ctx, speciesId, Pokedex.seenKey(speciesId), "seen")
    record(charId, ctx, speciesId, Pokedex.caughtKey(speciesId), "caught")
  }

  private fun record(
      charId: Long,
      ctx: SessionContext,
      speciesId: Int,
      flag: String,
      what: String,
  ) {
    if (speciesId <= 0) return
    val stored = characterStore.getCharacter(charId) ?: return
    if (flag in stored.storyFlags) return
    characterStore.setStoryFlag(charId, flag)
    log.info { "char=$charId dex $what species $speciesId" }
    // One shared unlock packet covers both, since the wire carries only the species. The seen and
    // caught split is what the login state's two lists are for.
    ctx.send(PokedexSpeciesUnlockPacket(speciesId.toShort()))
  }
}
