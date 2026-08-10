package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonLeague_BrunosRoom
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val BRUNO_ID = 411

internal object PokemonLeague_BrunosRoom_EventScript_Bruno : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isTrainerDefeated(BRUNO_ID)) return ctx.say(PokemonLeague_BrunosRoom.PostBattle)
    if (!ctx.trainerBattleSingle(
        BRUNO_ID, PokemonLeague_BrunosRoom.Intro, PokemonLeague_BrunosRoom.Defeat))
        return
    ctx.say(PokemonLeague_BrunosRoom.PostBattle)
  }
}

internal val PokemonLeague_BrunosRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_BrunosRoom_EventScript_Bruno" to PokemonLeague_BrunosRoom_EventScript_Bruno,
    )
