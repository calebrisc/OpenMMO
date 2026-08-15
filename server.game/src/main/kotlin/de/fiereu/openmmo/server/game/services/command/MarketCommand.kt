package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.MarketService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The marketplace: selling a monster to somebody who need not be online.
 *
 * The client has its own market screens, but nothing that reaches them has a capture behind it and
 * a misread packet here would move a monster and its price together. Chat cannot be misread.
 */
@Singleton
class MarketCommand @Inject constructor(private val market: MarketService) : ChatCommand {
  override val name = "market"
  override val usage = "/market [page] | sell <party slot> <price> | buy <id> | cancel <id> | mine"
  override val description = "buys and sells monsters"

  override suspend fun run(ctx: CommandContext) {
    val action = ctx.args.firstOrNull()?.lowercase()
    val reply =
        when (action) {
          null,
          "" -> market.browse(1)
          "mine" -> market.mine(ctx.characterId)
          "sell" -> {
            val slot = ctx.args.getOrNull(1)?.toIntOrNull()
            val price = ctx.args.getOrNull(2)?.toIntOrNull()
            if (slot == null || slot !in 1..6 || price == null)
                "Usage: /market sell <party slot 1-6> <price>"
            else market.sell(ctx.characterId, slot, price)
          }
          "buy" -> {
            val id = ctx.args.getOrNull(1)?.toLongOrNull()
            if (id == null) "Usage: /market buy <id>" else market.buy(ctx.characterId, id)
          }
          "cancel" -> {
            val id = ctx.args.getOrNull(1)?.toLongOrNull()
            if (id == null) "Usage: /market cancel <id>" else market.cancel(ctx.characterId, id)
          }
          else -> {
            val page = action.toIntOrNull()
            if (page != null) market.browse(page) else "Usage: $usage"
          }
        }
    ctx.reply(reply)
  }
}
