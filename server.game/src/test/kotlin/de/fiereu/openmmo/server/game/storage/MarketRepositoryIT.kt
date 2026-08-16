package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.test.DockerAvailable
import de.fiereu.openmmo.db.game.tables.references.MARKET_LISTING
import de.fiereu.openmmo.db.game.tables.references.POKEMON
import io.kotest.core.annotation.EnabledIf
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.testcontainers.containers.PostgreSQLContainer

@EnabledIf(DockerAvailable::class)
class MarketRepositoryIT :
    FunSpec({
      val container = PostgreSQLContainer<Nothing>("postgres:18")
      val entityIds = EntityIdService()
      lateinit var dsl: DSLContext
      lateinit var characters: JooqCharacterRepository
      lateinit var market: JooqMarketRepository

      fun monster(ownerId: Long, slot: Short, dexId: Int = 25): Pokemon =
          Pokemon(
              id = entityIds.newMonsterId(),
              ownerId = ownerId,
              container = PokemonContainer.PARTY,
              containerSlot = slot,
              dexId = dexId,
              seed = 1,
              ot = "Ash",
              nickname = "",
              level = 5,
              hp = 20,
              xp = 0,
              eVs = EVs(),
              iVs = IVs(),
              moves =
                  listOf(
                      PokemonMove(33, 35),
                      PokemonMove(0, 0),
                      PokemonMove(0, 0),
                      PokemonMove(0, 0),
                  ),
              isShiny = false,
              hasHiddenAbility = false,
              isAlpha = false,
              isSecret = false,
              isFatefulEncounter = false,
              isRaidEncounter = false,
              caughtAt = LocalDateTime.now(),
          )

      suspend fun character(userId: Int, name: String, mons: Int = 2): StoredCharacter {
        val id = entityIds.newCharacterId()
        val info =
            CharacterInfo(
                id = id,
                name = name,
                namePrefix = "",
                userId = userId,
                rivalSex = 1,
                lastLogin = LocalDateTime.now(),
                createdAt = LocalDateTime.now(),
                money = 30000,
                permissions = 8,
                remainingSafariSteps = 0,
                remainingSafariBalls = 0,
                pcExtraSlots = 0,
                battleBoxExtraSlots = 0,
                templateAmount = 0,
                positionRegionId = 1,
                positionBankId = 51,
                positionMapId = 3,
                positionX = 4,
                positionY = 4,
                repelLeft = 0,
                repelItemId = 0,
                lureLeft = 0,
                lureItemId = 0,
            )
        val stored =
            StoredCharacter(
                info = info,
                pokemon = (0 until mons).map { monster(id, it.toShort()) }.toMutableList(),
                pcStorage = mutableListOf(),
                items = mutableMapOf(),
            )
        characters.insertAggregate(stored)
        return stored
      }

      // Testcontainers cannot reach the docker socket on every machine, and a spec that silently
      // skips is worse than no spec at all. MARKET_IT_JDBC_URL points the same tests at a database
      // that is already running, so they can be made to prove something anywhere.
      val externalUrl: String? = System.getenv("MARKET_IT_JDBC_URL")
      val externalUser: String = System.getenv("MARKET_IT_DB_USER") ?: "postgres"
      val externalPassword: String = System.getenv("MARKET_IT_DB_PASSWORD") ?: "postgres"

      beforeSpec {
        val url: String
        val user: String
        val password: String
        if (externalUrl != null) {
          url = externalUrl
          user = externalUser
          password = externalPassword
        } else {
          container.start()
          url = container.jdbcUrl
          user = container.username
          password = container.password
        }
        Flyway.configure()
            .dataSource(url, user, password)
            .locations("classpath:db/migration", "classpath:db/dev")
            .load()
            .migrate()
        dsl = DSL.using(url, user, password)
        characters = JooqCharacterRepository(dsl, Dispatchers.IO)
        market = JooqMarketRepository(dsl, Dispatchers.IO)
      }

      afterSpec { if (externalUrl == null) container.stop() }

      test("listing takes the monster out of the party and into the gts") {
        val seller = character(userId = 900, name = "Seller")
        val mon = seller.pokemon.first()

        val listing = market.list(mon.id, seller.info.id, "Seller", price = 500).shouldNotBeNull()

        listing.price shouldBe 500
        listing.dexId shouldBe mon.dexId
        // The monster has to leave the party in the same breath, or it is both on sale and usable.
        dsl.select(POKEMON.CONTAINER)
            .from(POKEMON)
            .where(POKEMON.ID.eq(mon.id))
            .fetchOne(POKEMON.CONTAINER) shouldBe PokemonContainer.GTS.name
      }

      test("a monster already listed cannot be listed again") {
        val seller = character(userId = 901, name = "Seller2")
        val mon = seller.pokemon.first()
        market.list(mon.id, seller.info.id, "Seller2", price = 100).shouldNotBeNull()

        market.list(mon.id, seller.info.id, "Seller2", price = 200).shouldBeNull()

        dsl.selectFrom(MARKET_LISTING)
            .where(MARKET_LISTING.POKEMON_ID.eq(mon.id))
            .fetch() shouldHaveSize 1
      }

      test("a monster the seller does not own cannot be listed") {
        val seller = character(userId = 902, name = "Seller3")
        val other = character(userId = 903, name = "Other")

        market.list(other.pokemon.first().id, seller.info.id, "Seller3", price = 100).shouldBeNull()
      }

      test("buying moves the monster to the buyer and clears the listing") {
        val seller = character(userId = 904, name = "Seller4")
        val buyer = character(userId = 905, name = "Buyer")
        val mon = seller.pokemon.first()
        val listing = market.list(mon.id, seller.info.id, "Seller4", price = 300).shouldNotBeNull()

        market.sell(listing.id, buyer.info.id, slot = 5, toParty = true) shouldBe true

        val row =
            dsl.select(POKEMON.OWNER_ID, POKEMON.CONTAINER, POKEMON.CONTAINER_SLOT)
                .from(POKEMON)
                .where(POKEMON.ID.eq(mon.id))
                .fetchOne()
                .shouldNotBeNull()
        row[POKEMON.OWNER_ID] shouldBe buyer.info.id
        row[POKEMON.CONTAINER] shouldBe PokemonContainer.PARTY.name
        row[POKEMON.CONTAINER_SLOT] shouldBe 5.toShort()
        market.byId(listing.id).shouldBeNull()
      }

      test("two buyers racing the same listing cannot both win it") {
        val seller = character(userId = 906, name = "Seller5")
        val first = character(userId = 907, name = "First")
        val second = character(userId = 908, name = "Second")
        val mon = seller.pokemon.first()
        val listing = market.list(mon.id, seller.info.id, "Seller5", price = 250).shouldNotBeNull()

        // Both press buy at once. Exactly one may be told it worked, or a monster is sold twice and
        // one of them has paid for something the other is holding.
        val results = coroutineScope {
          listOf(
                  async { market.sell(listing.id, first.info.id, slot = 5, toParty = true) },
                  async { market.sell(listing.id, second.info.id, slot = 5, toParty = true) },
              )
              .awaitAll()
        }

        results.count { it } shouldBe 1
        market.byId(listing.id).shouldBeNull()
      }

      test("cancelling returns the monster to the seller's pc") {
        val seller = character(userId = 909, name = "Seller6")
        val mon = seller.pokemon.first()
        val listing = market.list(mon.id, seller.info.id, "Seller6", price = 400).shouldNotBeNull()

        market.cancel(listing.id, seller.info.id, slot = 3) shouldBe true

        val row =
            dsl.select(POKEMON.OWNER_ID, POKEMON.CONTAINER)
                .from(POKEMON)
                .where(POKEMON.ID.eq(mon.id))
                .fetchOne()
                .shouldNotBeNull()
        row[POKEMON.OWNER_ID] shouldBe seller.info.id
        row[POKEMON.CONTAINER] shouldBe PokemonContainer.PC.name
        market.byId(listing.id).shouldBeNull()
      }

      test("somebody else's listing cannot be cancelled") {
        val seller = character(userId = 910, name = "Seller7")
        val thief = character(userId = 911, name = "Thief")
        val mon = seller.pokemon.first()
        val listing = market.list(mon.id, seller.info.id, "Seller7", price = 400).shouldNotBeNull()

        market.cancel(listing.id, thief.info.id, slot = 0) shouldBe false

        market.byId(listing.id).shouldNotBeNull()
      }

      test("browsing shows every listing cheapest first") {
        val seller = character(userId = 912, name = "Seller8", mons = 3)
        market.list(seller.pokemon[0].id, seller.info.id, "Seller8", price = 900)
        market.list(seller.pokemon[1].id, seller.info.id, "Seller8", price = 50)

        val mine = market.bySeller(seller.info.id)
        mine shouldHaveSize 2
        market.all().first().price shouldBe 50
      }
    })
