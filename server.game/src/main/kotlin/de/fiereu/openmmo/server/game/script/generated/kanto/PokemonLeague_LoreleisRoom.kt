package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonLeague_LoreleisRoom
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val LORELEI_ID = 410

internal object PokemonLeague_LoreleisRoom_EventScript_Lorelei : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isTrainerDefeated(LORELEI_ID)) return ctx.say(PokemonLeague_LoreleisRoom.PostBattle)
    if (!ctx.trainerBattleSingle(
        LORELEI_ID, PokemonLeague_LoreleisRoom.Intro, PokemonLeague_LoreleisRoom.Defeat))
        return
    ctx.say(PokemonLeague_LoreleisRoom.PostBattle)
  }
}

internal val PokemonLeague_LoreleisRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_LoreleisRoom_EventScript_Lorelei" to
            PokemonLeague_LoreleisRoom_EventScript_Lorelei,
    )
