package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.ChatService
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * The client resolves its own /whisper and /w, so those never reach us. This is the name the server
 * can actually be reached on.
 */
@Singleton
class PmCommand
@Inject
constructor(
    // ChatService depends on the command set, so the edge back to it has to be lazy.
    private val chatService: Provider<ChatService>,
) : ChatCommand {
  override val name = "pm"
  override val usage = "/pm <name> <message>"
  override val description = "sends a private message to one player"

  override suspend fun run(ctx: CommandContext) {
    val target = ctx.args.firstOrNull()
    val message = ctx.args.drop(1).joinToString(" ")
    if (target == null || message.isBlank()) {
      ctx.reply("Usage: $usage")
      return
    }
    chatService.get().whisper(ctx.session, ctx.character.info.name, target, message)
  }
}
