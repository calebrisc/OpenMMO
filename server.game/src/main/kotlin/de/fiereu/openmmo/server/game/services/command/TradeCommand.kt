package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.TradeService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Trading monsters with another player.
 *
 * Picking can be done in the client's own trade window, but the exchange is committed here, because
 * the window reports every button press as the same value and a monster handed to the wrong person
 * cannot be taken back.
 */
@Singleton
class TradeCommand @Inject constructor(private val trades: TradeService) : ChatCommand {
  override val name = "trade"
  override val usage = "/trade [<name> | accept | decline | offer <slot> | confirm | cancel]"
  override val description = "trades a monster with another player"

  override suspend fun run(ctx: CommandContext) {
    val action = ctx.args.firstOrNull()?.lowercase()
    val rest = ctx.args.drop(1)
    val reply =
        when (action) {
          null,
          "" -> trades.describe(ctx.characterId)
          "accept" -> trades.accept(ctx.session, ctx.characterId)
          "decline" -> trades.decline(ctx.characterId)
          "cancel" -> trades.cancel(ctx.characterId)
          "confirm" -> trades.confirm(ctx.characterId)
          "offer" -> {
            val slot = rest.firstOrNull()?.toIntOrNull()
            if (slot == null || slot !in 1..6) "Usage: /trade offer <party slot 1-6>"
            else trades.offer(ctx.characterId, slot)
          }
          else -> trades.invite(ctx.characterId, ctx.args.joinToString(" "))
        }
    ctx.reply(reply)
  }
}
