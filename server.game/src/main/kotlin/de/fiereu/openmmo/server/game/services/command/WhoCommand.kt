package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.server.game.world.interest.MapInterestKey
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Who is online and, for each of them, the map the SERVER believes they are on and whether they
 * share your visibility group.
 *
 * Two players only see each other's movement while they sit in the same map group, so when somebody
 * is standing frozen on your screen the question is whether the server agrees you are together.
 * That is invisible from in game, which is what this answers.
 */
@Singleton
class WhoCommand
@Inject
constructor(
    private val sessionRegistry: SessionRegistry,
    private val characterStore: CharacterStore,
    private val interestManager: InterestManager,
) : ChatCommand {
  override val name = "players"
  override val usage = "/players"
  override val description = "lists everyone online, with the map the server has them on"

  override suspend fun run(ctx: CommandContext) {
    val mine = ctx.state
    val myKey = MapInterestKey(mine.regionId, mine.bankId, mine.mapId)
    val sharing = interestManager.members(myKey)

    val lines =
        sessionRegistry
            .onlineCharacterIds()
            .mapNotNull { id ->
              val name = characterStore.getCharacter(id)?.info?.name ?: return@mapNotNull null
              val session = sessionRegistry.getByCharacterId(id)
              val state = session?.attributes?.get(PLAYER_STATE)
              val where =
                  if (state == null) "?" else "${state.regionId}:${state.bankId}:${state.mapId}"
              val together =
                  when {
                    id == ctx.characterId -> "you"
                    session != null && session in sharing -> "visible"
                    else -> "NOT VISIBLE"
                  }
              "$name at $where ($together)"
            }
            .sorted()

    if (lines.isEmpty()) {
      ctx.reply("Nobody is online.")
      return
    }
    ctx.reply("Online (${lines.size}): ${lines.joinToString("; ")}")
  }
}
