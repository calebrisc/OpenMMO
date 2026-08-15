package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.TournamentService
import javax.inject.Inject
import javax.inject.Singleton

/** Runs a knockout tournament as a bracket of duels. */
@Singleton
class TourneyCommand @Inject constructor(private val tournaments: TournamentService) : ChatCommand {
  override val name = "tourney"
  override val usage = "/tourney [join | leave | start | cancel]"
  override val description = "runs a knockout tournament"

  override suspend fun run(ctx: CommandContext) {
    val reply =
        when (ctx.args.firstOrNull()?.lowercase()) {
          null,
          "" -> tournaments.describe(ctx.characterId)
          "join" -> tournaments.join(ctx.characterId)
          "leave" -> tournaments.leave(ctx.characterId)
          "start" -> tournaments.start()
          "cancel" -> tournaments.cancel()
          else -> "Usage: $usage"
        }
    ctx.reply(reply)
  }
}
