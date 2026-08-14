package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.LinkService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Forms the player group a raid is built from.
 *
 * Named squad rather than link because the client owns /link itself and never forwards it, so the
 * obvious name is the one name that cannot work. Its own command list also claims /party, /trade,
 * /spectate, /team, /talk and /shout.
 */
@Singleton
class LinkCommand @Inject constructor(private val links: LinkService) : ChatCommand {
  override val name = "squad"
  override val usage = "/squad [invite <name> | accept | decline | leave | kick <name>]"
  override val description = "forms a group of up to four players"

  override suspend fun run(ctx: CommandContext) {
    val action = ctx.args.firstOrNull()?.lowercase()
    val argument = ctx.args.drop(1).joinToString(" ")
    val reply =
        when (action) {
          null,
          "" -> links.describe(ctx.characterId)
          "invite" ->
              if (argument.isBlank()) "Usage: /squad invite <name>"
              else links.invite(ctx.session, ctx.characterId, argument)
          "accept" -> links.accept(ctx.characterId)
          "decline" -> links.decline(ctx.characterId)
          "leave" -> links.leave(ctx.characterId)
          "kick" ->
              if (argument.isBlank()) "Usage: /squad kick <name>"
              else links.kick(ctx.characterId, argument)
          else -> "Usage: $usage"
        }
    ctx.reply(reply)
  }
}
