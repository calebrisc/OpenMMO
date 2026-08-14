package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject
import javax.inject.Singleton

/** Watches somebody else's battle. The first step towards fighting one together. */
@Singleton
class WatchCommand
@Inject
constructor(
    private val battles: BattleService,
    private val characterStore: CharacterStore,
) : ChatCommand {
  override val name = "spectate"
  override val usage = "/spectate <name> | /spectate stop"
  override val description = "watches another player's battle"

  override suspend fun run(ctx: CommandContext) {
    val argument = ctx.args.joinToString(" ").trim()
    if (argument.isEmpty()) {
      ctx.reply("Usage: $usage")
      return
    }
    if (argument.equals("stop", ignoreCase = true)) {
      ctx.reply(battles.stopWatching(ctx.session))
      return
    }
    val target = characterStore.findCachedByName(argument)
    if (target == null) {
      ctx.reply("$argument is not online.")
      return
    }
    ctx.reply(battles.watch(ctx.session, target.info.id))
  }
}
