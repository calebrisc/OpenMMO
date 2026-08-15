package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.RaidService
import javax.inject.Inject
import javax.inject.Singleton

/** Starts a boss fight for a squad, or for one player willing to try it alone. */
@Singleton
class RaidCommand @Inject constructor(private val raids: RaidService) : ChatCommand {
  override val name = "raid"
  override val usage = "/raid [start [<species id> [level]] | leave]"
  override val description = "fights a raid boss with your squad"

  override suspend fun run(ctx: CommandContext) {
    val action = ctx.args.firstOrNull()?.lowercase()
    val reply =
        when (action) {
          null,
          "" -> raids.describe(ctx.characterId)
          "leave" -> {
            raids.leave(ctx.characterId)
            "Left the raid."
          }
          "start" -> {
            val dexId = ctx.args.getOrNull(1)?.toIntOrNull()
            val level = ctx.args.getOrNull(2)?.toIntOrNull()
            raids.start(ctx.characterId, dexId, level)
          }
          else -> "Usage: $usage"
        }
    ctx.reply(reply)
  }
}
