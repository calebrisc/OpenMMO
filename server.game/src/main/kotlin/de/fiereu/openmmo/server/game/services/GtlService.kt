package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.packets.gtl.GtlConfirmPurchasePacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlCreateListingPacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlListKind
import de.fiereu.openmmo.net.game.packets.gtl.GtlListingActionPacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlListingCancelPacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlListingsPagePacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlListingsPageRequestPacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlOpenSessionPacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlPokemonListing
import de.fiereu.openmmo.net.game.packets.gtl.GtlPurchaseListingPacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlPurchasePacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlSearchListing
import de.fiereu.openmmo.net.game.packets.gtl.GtlSearchPagePacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlSearchPageRequestPacket
import de.fiereu.openmmo.net.game.packets.gtl.GtlSpeciesFilter
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.MarketListing
import de.fiereu.openmmo.server.game.storage.MarketRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/** Rows per page, taken from the captured boards, which all carry ten. */
private const val PAGE_SIZE = 10

/**
 * The low bits every captured listing id carries, the same shape a character (0x9000) and a monster
 * (0xC000) carry. Two captures from different boards both end in it.
 */
private const val LISTING_ID_TAG = 0x2000L

/** Our listings never expire, but the board draws a date, so it is given one it will not reach. */
private const val SHOWN_LIFETIME_SECONDS = 365L * 24 * 60 * 60

/**
 * The client's own trade board, answered from the market behind the /market commands.
 *
 * The board is entirely the client's: it opens itself, pages itself and draws its own rows, and the
 * server's only job is to answer what it asks. Nothing did, so the window sat empty. Every packet
 * it sends is now at least read and logged, and the monster board is filled in for real.
 *
 * A listing is addressed on the wire by an id carrying [LISTING_ID_TAG], derived from the row id
 * rather than stored, so the two are the same fact and either can be recovered from the other. The
 * row id stays small because that is what a player types into `/market buy`.
 */
@Singleton
class GtlService
@Inject
constructor(
    private val market: MarketRepository,
    private val marketService: MarketService,
    private val species: SpeciesRegistry,
) {

  private fun wireId(listingId: Long): Long = (listingId shl 16) or LISTING_ID_TAG

  private fun rowId(wireId: Long): Long = wireId ushr 16

  /** The board asked for a page. Only the monster board has anything to show. */
  suspend fun onSearchPage(event: PacketEvent<GtlSearchPageRequestPacket>) {
    val request = event.packet
    val charId = event.session.attributes[PLAYER_STATE]?.characterId
    if (!GtlTuning.answerBoards) {
      log.info {
        "char=$charId asked for a ${request.listKind} page, and the board is switched off"
      }
      return
    }
    if (request.listKind != GtlListKind.POKEMON) {
      // An item market and an own-listings row layout are both undecoded, and a captured
      // own-listings page has never had a row in it. An empty page draws an empty board rather
      // than a board of guesses.
      log.info { "char=$charId asked for a ${request.listKind} page, which is always empty" }
      event.session.send(emptyPage(request))
      return
    }

    val wanted = request.filters.filterIsInstance<GtlSpeciesFilter>().flatMap { it.speciesIds }
    val all = market.all().filter { wanted.isEmpty() || it.dexId.toShort() in wanted }
    val page = request.page.toInt().coerceAtLeast(0)
    val shown = all.drop(page * PAGE_SIZE).take(PAGE_SIZE)
    val monsters = market.monsters(shown)

    log.info {
      "char=$charId asked for ${request.listKind} page $page: " +
          "${shown.size} of ${all.size} rows, ${request.filters.size} filter(s)"
    }
    event.session.send(
        GtlSearchPagePacket(
            requestId = request.requestId,
            listKind = GtlListKind.POKEMON,
            page = request.page,
            totalMatches = all.size,
            listings =
                shown.mapNotNull { listing -> monsters[listing.id]?.let { row(listing, it) } },
            quotes = null,
        ))
  }

  private fun emptyPage(request: GtlSearchPageRequestPacket) =
      GtlSearchPagePacket(
          requestId = request.requestId,
          listKind = request.listKind,
          page = request.page,
          totalMatches = 0,
          listings = emptyList(),
          // The quote strip only exists on the item board, and it is a list even when empty.
          quotes = if (request.listKind == GtlListKind.ITEM) emptyList() else null,
      )

  private fun row(listing: MarketListing, monster: Pokemon): GtlSearchListing {
    val listedAt = listing.listedAt.toEpochSecond(ZoneOffset.UTC)
    val stats = species.get(monster.dexId)?.let { StatCalculator.computeAll(it, monster) }
    return GtlPokemonListing(
        listingId = wireId(listing.id),
        price = listing.price,
        listedAt = listedAt.toInt(),
        expiresAt = (listedAt + SHOWN_LIFETIME_SECONDS).toInt(),
        quantity = 1,
        pokemon = monster,
        // The board's own order, hp first, matching the record's hp on a healthy monster.
        stats =
            listOfNotNull(stats).flatMap {
              listOf(it.hp, it.atk, it.def, it.spd, it.spAtk, it.spDef).map(Int::toShort)
            },
    )
  }

  /** The board's buy button, once the player has confirmed the price. */
  suspend fun onConfirmPurchase(event: PacketEvent<GtlConfirmPurchasePacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val listingId = rowId(event.packet.listingEntityId)
    log.info { "char=$charId is buying listing $listingId from the board" }
    event.session.send(notice(marketService.buy(charId, listingId)))
  }

  /** The board's cancel button. It names the listing as text rather than as a number. */
  suspend fun onCancel(event: PacketEvent<GtlListingCancelPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val listingId = event.packet.listingId.trim().toLongOrNull()
    if (listingId == null) {
      log.info {
        "char=$charId cancelled listing '${event.packet.listingId}', which is not a number"
      }
      return
    }
    event.session.send(notice(marketService.cancel(charId, rowId(listingId))))
  }

  /**
   * The rest of the board's traffic, read and recorded rather than answered.
   *
   * Each of these either drives a screen we have nothing to put in (the item market, the trade log)
   * or carries a field whose meaning is not decoded yet (a listing's filter criteria, an entry
   * kind). Logging them is what turns the next session at a real client into evidence instead of
   * guesswork, and it beats the alternative: an unhandled packet is dropped with an error and tells
   * us nothing about what the player was doing.
   */
  fun onOpenSession(event: PacketEvent<GtlOpenSessionPacket>) {
    log.info { "${who(event)} opened the board on entry kind ${event.packet.entryKindId}" }
  }

  fun onListingsPage(event: PacketEvent<GtlListingsPageRequestPacket>) {
    val request = event.packet
    log.info { "${who(event)} asked for ${request.listKind} listings page ${request.page}" }
    if (!GtlTuning.answerBoards) return
    event.session.send(
        GtlListingsPagePacket(
            requestId = request.requestId,
            listKind = request.listKind,
            page = request.page,
            totalCount = 0,
            listings = emptyList(),
        ))
  }

  fun onCreateListing(event: PacketEvent<GtlCreateListingPacket>) {
    // The monster being sold is not in this packet anywhere we can read: the criteria blob is
    // undecoded. Selling stays on /market sell until a capture says which bytes name the monster.
    log.info {
      "${who(event)} tried to list from the board: category ${event.packet.categoryIndex}, " +
          "kind ${event.packet.itemKind}, price ${event.packet.priceShort}, " +
          "${event.packet.filterCriteria.size} criteria bytes"
    }
    event.session.send(notice("Selling from this screen is not wired up yet. Use /market sell."))
  }

  fun onPurchase(event: PacketEvent<GtlPurchasePacket>) {
    log.info {
      "${who(event)} sent the item buy: kind ${event.packet.entryKindId}, " +
          "item ${event.packet.itemTypeId} x${event.packet.quantity} for ${event.packet.totalPrice}"
    }
  }

  fun onPurchaseListing(event: PacketEvent<GtlPurchaseListingPacket>) {
    log.info {
      "${who(event)} sent listing action ${event.packet.action} on ${event.packet.listingId} " +
          "at ${event.packet.price}"
    }
  }

  fun onListingAction(event: PacketEvent<GtlListingActionPacket>) {
    log.info {
      "${who(event)} acted on kind ${event.packet.entryKindId} item ${event.packet.itemId}"
    }
  }

  private fun who(event: PacketEvent<*>): String =
      "char=${event.session.attributes[PLAYER_STATE]?.characterId}"
}
