package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.db.game.tables.references.MARKET_LISTING
import de.fiereu.openmmo.db.game.tables.references.POKEMON
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

/** A monster on sale, as the market screen and the chat listing both need it. */
data class MarketListing(
    val id: Long,
    val pokemonId: Long,
    val sellerId: Long,
    val sellerName: String,
    val price: Int,
    val dexId: Int,
    val listedAt: LocalDateTime,
)

interface MarketRepository {
  suspend fun all(): List<MarketListing>

  /**
   * The monsters behind [listings], keyed by listing id, for the trade board that draws the whole
   * record rather than just a species name. A listing whose monster has gone is left out.
   */
  suspend fun monsters(listings: List<MarketListing>): Map<Long, Pokemon>

  suspend fun byId(id: Long): MarketListing?

  suspend fun bySeller(sellerId: Long): List<MarketListing>

  /**
   * Lists a monster the seller owns. The monster moves to the GTS container in the same transaction
   * as the listing row, so it is never both on sale and in a party, and never on sale with no row.
   *
   * Null when the seller does not own the monster or it is already listed.
   */
  suspend fun list(pokemonId: Long, sellerId: Long, sellerName: String, price: Int): MarketListing?

  /**
   * Hands the monster to [buyerId] and removes the listing, both together. False when the listing
   * had already gone, which is what stops two buyers paying for the same monster.
   */
  suspend fun sell(listingId: Long, buyerId: Long, slot: Short, toParty: Boolean): Boolean

  /**
   * Takes a listing back, returning the monster to the seller's PC. False if it had already gone.
   */
  suspend fun cancel(listingId: Long, sellerId: Long, slot: Short): Boolean
}

@Singleton
class JooqMarketRepository
@Inject
constructor(
    private val dsl: DSLContext,
    @param:Named("db") private val dispatcher: CoroutineDispatcher,
) : MarketRepository {

  override suspend fun all(): List<MarketListing> =
      withContext(dispatcher) {
        dsl.selectFrom(MARKET_LISTING).orderBy(MARKET_LISTING.PRICE.asc()).fetch().map(::toListing)
      }

  override suspend fun monsters(listings: List<MarketListing>): Map<Long, Pokemon> =
      withContext(dispatcher) {
        if (listings.isEmpty()) return@withContext emptyMap()
        val byMonsterId =
            dsl.selectFrom(POKEMON)
                .where(POKEMON.ID.`in`(listings.map { it.pokemonId }))
                .fetch()
                .associate { it.id to it.toPokemon() }
        listings
            .mapNotNull { listing -> byMonsterId[listing.pokemonId]?.let { listing.id to it } }
            .toMap()
      }

  override suspend fun byId(id: Long): MarketListing? =
      withContext(dispatcher) {
        dsl.selectFrom(MARKET_LISTING).where(MARKET_LISTING.ID.eq(id)).fetchOne()?.let(::toListing)
      }

  override suspend fun bySeller(sellerId: Long): List<MarketListing> =
      withContext(dispatcher) {
        dsl.selectFrom(MARKET_LISTING)
            .where(MARKET_LISTING.SELLER_ID.eq(sellerId))
            .fetch()
            .map(::toListing)
      }

  override suspend fun list(
      pokemonId: Long,
      sellerId: Long,
      sellerName: String,
      price: Int,
  ): MarketListing? =
      withContext(dispatcher) {
        dsl.transactionResult { config ->
          val tx = config.dsl()
          val dexId =
              tx.select(POKEMON.DEX_ID)
                  .from(POKEMON)
                  .where(POKEMON.ID.eq(pokemonId))
                  .and(POKEMON.OWNER_ID.eq(sellerId))
                  .and(POKEMON.CONTAINER.ne(PokemonContainer.GTS.name))
                  .fetchOne(POKEMON.DEX_ID) ?: return@transactionResult null

          tx.update(POKEMON)
              .set(POKEMON.CONTAINER, PokemonContainer.GTS.name)
              .set(POKEMON.CONTAINER_SLOT, 0.toShort())
              .where(POKEMON.ID.eq(pokemonId))
              .execute()

          val listedAt = LocalDateTime.now()
          val id =
              tx.insertInto(MARKET_LISTING)
                  .set(MARKET_LISTING.POKEMON_ID, pokemonId)
                  .set(MARKET_LISTING.SELLER_ID, sellerId)
                  .set(MARKET_LISTING.SELLER_NAME, sellerName)
                  .set(MARKET_LISTING.PRICE, price)
                  .set(MARKET_LISTING.DEX_ID, dexId)
                  .set(MARKET_LISTING.LISTED_AT, listedAt)
                  .returningResult(MARKET_LISTING.ID)
                  .fetchOne(MARKET_LISTING.ID) ?: return@transactionResult null

          MarketListing(id, pokemonId, sellerId, sellerName, price, dexId, listedAt)
        }
      }

  override suspend fun sell(
      listingId: Long,
      buyerId: Long,
      slot: Short,
      toParty: Boolean,
  ): Boolean = move(listingId, buyerId, slot, toParty, sellerOnly = null)

  override suspend fun cancel(listingId: Long, sellerId: Long, slot: Short): Boolean =
      move(listingId, sellerId, slot, toParty = false, sellerOnly = sellerId)

  /**
   * Deletes the listing and rehomes the monster in one transaction. The delete is what decides the
   * race: whoever removes the row owns the outcome, and a second caller finds nothing to delete and
   * moves nothing.
   */
  private suspend fun move(
      listingId: Long,
      newOwnerId: Long,
      slot: Short,
      toParty: Boolean,
      sellerOnly: Long?,
  ): Boolean =
      withContext(dispatcher) {
        dsl.transactionResult { config ->
          val tx = config.dsl()
          val listing =
              tx.selectFrom(MARKET_LISTING)
                  .where(MARKET_LISTING.ID.eq(listingId))
                  .let {
                    if (sellerOnly != null) it.and(MARKET_LISTING.SELLER_ID.eq(sellerOnly)) else it
                  }
                  .forUpdate()
                  .fetchOne() ?: return@transactionResult false

          val deleted =
              tx.deleteFrom(MARKET_LISTING).where(MARKET_LISTING.ID.eq(listingId)).execute()
          if (deleted == 0) return@transactionResult false

          tx.update(POKEMON)
              .set(POKEMON.OWNER_ID, newOwnerId)
              .set(
                  POKEMON.CONTAINER,
                  if (toParty) PokemonContainer.PARTY.name else PokemonContainer.PC.name)
              .set(POKEMON.CONTAINER_SLOT, slot)
              .where(POKEMON.ID.eq(listing.pokemonId))
              .execute()
          true
        }
      }

  private fun toListing(r: org.jooq.Record) =
      MarketListing(
          id = r[MARKET_LISTING.ID]!!,
          pokemonId = r[MARKET_LISTING.POKEMON_ID]!!,
          sellerId = r[MARKET_LISTING.SELLER_ID]!!,
          sellerName = r[MARKET_LISTING.SELLER_NAME]!!,
          price = r[MARKET_LISTING.PRICE]!!,
          dexId = r[MARKET_LISTING.DEX_ID]!!,
          listedAt = r[MARKET_LISTING.LISTED_AT]!!,
      )
}
