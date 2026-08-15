package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.MAX_PARTY_SIZE
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.MarketListing
import de.fiereu.openmmo.server.game.storage.MarketRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val MAX_PRICE = 9_999_999
private const val PAGE_SIZE = 10

/**
 * Buying and selling monsters between players who are never online together.
 *
 * A listed monster leaves the seller's hands the moment it is listed and lives in the GTS container
 * until somebody buys it or the seller takes it back, so it cannot be sold twice, traded away while
 * listed, or fought with. The database owns that move, not this service: the row and the monster
 * change together or not at all.
 *
 * Money is taken from the buyer before the monster moves and returned if the move fails, so a
 * failed sale costs nobody anything.
 */
@Singleton
class MarketService
@Inject
constructor(
    private val market: MarketRepository,
    private val characterStore: CharacterStore,
    private val sessionRegistry: SessionRegistry,
    private val species: SpeciesRegistry,
) {

  private fun label(listing: MarketListing): String {
    val name = species.get(listing.dexId)?.name ?: "#${listing.dexId}"
    return "[${listing.id}] $name for ${listing.price} from ${listing.sellerName}"
  }

  /** Everything on sale, cheapest first. */
  suspend fun browse(page: Int): String {
    val all = market.all()
    if (all.isEmpty()) return "Nothing is for sale. /market sell <party slot> <price> to list one."
    val pages = (all.size + PAGE_SIZE - 1) / PAGE_SIZE
    val index = (page - 1).coerceIn(0, pages - 1)
    val shown = all.drop(index * PAGE_SIZE).take(PAGE_SIZE)
    return "Market (page ${index + 1}/$pages): " +
        shown.joinToString("; ") { label(it) } +
        ". /market buy <id> to buy."
  }

  suspend fun mine(charId: Long): String {
    val listings = market.bySeller(charId)
    if (listings.isEmpty()) return "You have nothing for sale."
    return "Your listings: " +
        listings.joinToString("; ") { label(it) } +
        ". /market cancel <id> to take one back."
  }

  /** Lists the monster in [partySlot], counted from one. */
  suspend fun sell(charId: Long, partySlot: Int, price: Int): String {
    if (price !in 1..MAX_PRICE) return "Pick a price between 1 and $MAX_PRICE."
    val stored = characterStore.getCharacter(charId) ?: return "You are not in world."
    val party = stored.pokemon.sortedBy { it.containerSlot }
    val mon = party.getOrNull(partySlot - 1) ?: return "You have nothing in slot $partySlot."
    if (party.size <= 1) return "You cannot sell your last monster."

    // Out of the cache first. The store is authoritative while a player is online and would
    // otherwise write the monster straight back into the party it was just sold out of.
    val taken = characterStore.removePokemon(charId, mon.id) ?: return "That could not be listed."
    val listing = market.list(mon.id, charId, stored.info.name, price)
    if (listing == null) {
      characterStore.addPokemon(charId, taken)
      return "That could not be listed."
    }
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId listed monster ${mon.id} (dex ${mon.dexId}) for $price" }
    return "Listed ${species.get(mon.dexId)?.name ?: "it"} for $price. It is id ${listing.id}."
  }

  suspend fun buy(charId: Long, listingId: Long): String {
    val listing = market.byId(listingId) ?: return "That listing is gone."
    if (listing.sellerId == charId) return "That is your own listing. /market cancel $listingId."
    val stored = characterStore.getCharacter(charId) ?: return "You are not in world."
    if (stored.info.money < listing.price) {
      return "That costs ${listing.price} and you have ${stored.info.money}."
    }
    val toParty = stored.pokemon.size < MAX_PARTY_SIZE
    val slot = freeSlot(charId, toParty)

    // Paid before the monster moves, so a sale can never hand it over for nothing.
    if (!characterStore.addMoney(charId, -listing.price)) return "Your money could not be taken."
    val sold = market.sell(listingId, charId, slot, toParty)
    if (!sold) {
      characterStore.addMoney(charId, listing.price)
      return "Somebody bought it first. You were not charged."
    }

    // The seller is paid whether or not they are online: the store loads them if they are not.
    characterStore.getOrLoadCharacter(listing.sellerId)
    characterStore.addMoney(listing.sellerId, listing.price)
    characterStore.flushCharacterAsync(listing.sellerId)

    val name = species.get(listing.dexId)?.name ?: "#${listing.dexId}"
    log.info { "char=$charId bought listing $listingId ($name) from ${listing.sellerId}" }
    sessionRegistry
        .getByCharacterId(listing.sellerId)
        ?.send(notice("$name sold for ${listing.price}. The money is yours."))
    // Reloaded rather than patched in memory, since the database moved the monster, not the cache.
    reload(charId)
    return "Bought $name for ${listing.price}. It is in your " +
        (if (toParty) "party" else "PC") +
        "."
  }

  suspend fun cancel(charId: Long, listingId: Long): String {
    val listing = market.byId(listingId) ?: return "That listing is gone."
    if (listing.sellerId != charId) return "That is not your listing."
    val slot = freeSlot(charId, toParty = false)
    if (!market.cancel(listingId, charId, slot)) return "That listing is gone."
    log.info { "char=$charId cancelled listing $listingId" }
    reload(charId)
    return "Taken back. It is in your PC."
  }

  private fun freeSlot(charId: Long, toParty: Boolean): Short {
    val stored = characterStore.getCharacter(charId) ?: return 0
    val used = if (toParty) stored.pokemon.map { it.containerSlot } else stored.pcStorage.map { it.containerSlot }
    return (generateSequence(0) { it + 1 }.first { it.toShort() !in used }).toShort()
  }

  /**
   * Pulls a character's monsters back out of the database after the market moved one behind the
   * cache's back, and tells the client what it now holds.
   */
  private suspend fun reload(charId: Long) {
    characterStore.unloadCharacterAsync(charId)
    val reloaded = characterStore.getOrLoadCharacter(charId) ?: return
    val session = sessionRegistry.getByCharacterId(charId) ?: return
    sendContainers(session, reloaded)
  }

  private fun sendContainers(
      session: de.fiereu.network.SessionContext,
      stored: de.fiereu.openmmo.server.game.storage.StoredCharacter,
  ) {
    session.send(
        de.fiereu.openmmo.net.game.packets.PokemonContainerPacket(
            container = de.fiereu.openmmo.common.enums.PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = stored.pokemon.sortedBy { it.containerSlot },
        ))
    session.send(
        de.fiereu.openmmo.net.game.packets.PokemonContainerPacket(
            container = de.fiereu.openmmo.common.enums.PokemonContainer.PC,
            hasChange = true,
            delete = false,
            pokemon = stored.pcStorage.sortedBy { it.containerSlot },
        ))
  }
}
