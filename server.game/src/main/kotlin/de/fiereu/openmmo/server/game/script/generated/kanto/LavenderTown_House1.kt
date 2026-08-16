package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.LavenderTown_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object LavenderTown_House1_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_RESCUED_MR_FUJI)) {
      ctx.say(LavenderTown_House1.GhostOfPokemonTowerIsGone)
      return
    }
    ctx.say(LavenderTown_House1.RocketsKilledCubonesMother)
  }
}

internal object LavenderTown_House1_EventScript_Cubone : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LavenderTown_House1.Cubone)
}

internal val LavenderTown_House1Scripts: Map<String, Script> =
    mapOf(
        "LavenderTown_House1_EventScript_CooltrainerF" to
            LavenderTown_House1_EventScript_CooltrainerF,
        "LavenderTown_House1_EventScript_Cubone" to LavenderTown_House1_EventScript_Cubone,
    )
