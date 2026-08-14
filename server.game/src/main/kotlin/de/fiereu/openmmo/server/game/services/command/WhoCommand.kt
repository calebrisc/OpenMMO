package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WhoCommand
@Inject
constructor(
    private val sessionRegistry: SessionRegistry,
    private val characterStore: CharacterStore,
) : ChatCommand {
  override val name = "who"
  override val usage = "/who"
  override val description = "lists everyone online"

  override suspend fun run(ctx: CommandContext) {
    val names =
        sessionRegistry
            .onlineCharacterIds()
            .mapNotNull { characterStore.getCharacter(it)?.info?.name }
            .sorted()
    if (names.isEmpty()) {
      ctx.reply("Nobody is online.")
      return
    }
    ctx.reply("Online (${names.size}): ${names.joinToString(", ")}")
  }
}
