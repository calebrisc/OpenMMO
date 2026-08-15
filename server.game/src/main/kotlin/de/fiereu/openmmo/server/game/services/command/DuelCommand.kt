package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.DuelService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Challenges another player to a battle.
 *
 * The invite travels as chat rather than through the client's own challenge prompt, because that
 * packet has no opcode on this server and the neighbouring request packet is the one that opened a
 * captcha nobody could solve. Chat costs a typed word and cannot wedge a client.
 */
@Singleton
class DuelCommand @Inject constructor(private val duels: DuelService) : ChatCommand {
  override val name = "duel"
  override val usage = "/duel [<name> | accept | decline]"
  override val description = "challenges another player to a battle"

  override suspend fun run(ctx: CommandContext) {
    val action = ctx.args.firstOrNull()?.lowercase()
    val reply =
        when (action) {
          null,
          "" -> duels.describe(ctx.characterId)
          "accept" -> duels.accept(ctx.session, ctx.characterId)
          "decline" -> duels.decline(ctx.characterId)
          else -> duels.invite(ctx.session, ctx.characterId, ctx.args.joinToString(" "))
        }
    ctx.reply(reply)
  }
}
