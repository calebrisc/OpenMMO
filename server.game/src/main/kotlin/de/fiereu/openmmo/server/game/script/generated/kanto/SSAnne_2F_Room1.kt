package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_2F_Room1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object SSAnne_2F_Room1_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(SSAnne_2F_Room1.SleepingMonLookedLikeThis)
    ctx.setVar(KantoVars.VAR_0x8004, 143)
  }
}

internal val SSAnne_2F_Room1Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Room1_EventScript_Gentleman" to SSAnne_2F_Room1_EventScript_Gentleman,
    )
