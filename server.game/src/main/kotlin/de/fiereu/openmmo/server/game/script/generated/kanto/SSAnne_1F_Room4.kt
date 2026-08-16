package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_1F_Room4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_1F_Room4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) {
    val gender1 = if (ctx.isFemale) 1 else 0
    if (gender1 == 0) {
      ctx.say(SSAnne_1F_Room4.WaiterCherryPiePlease)
      return
    }
    ctx.say(SSAnne_1F_Room4.WaitressCherryPiePlease)
  }
}

internal val SSAnne_1F_Room4Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room4_EventScript_Woman" to SSAnne_1F_Room4_EventScript_Woman,
    )
