package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonLeague_AgathasRoom
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val AGATHA_ID = 412

internal object PokemonLeague_AgathasRoom_EventScript_Agatha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isTrainerDefeated(AGATHA_ID)) return ctx.say(PokemonLeague_AgathasRoom.PostBattle)
    if (!ctx.trainerBattleSingle(
        AGATHA_ID, PokemonLeague_AgathasRoom.Intro, PokemonLeague_AgathasRoom.Defeat))
        return
    ctx.say(PokemonLeague_AgathasRoom.PostBattle)
  }
}

internal val PokemonLeague_AgathasRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_AgathasRoom_EventScript_Agatha" to
            PokemonLeague_AgathasRoom_EventScript_Agatha,
    )
