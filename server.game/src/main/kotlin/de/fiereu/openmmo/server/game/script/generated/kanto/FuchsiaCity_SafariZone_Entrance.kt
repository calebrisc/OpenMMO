package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity_SafariZone_Entrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FuchsiaCity_SafariZone_Entrance_EventScript_InfoAttendant : Script {
  override suspend fun run(ctx: ScriptContext) {
    val said1 = ctx.askYesNo(FuchsiaCity_SafariZone_Entrance.FirstTimeAtSafariZone)
    if (said1) {
      ctx.say(FuchsiaCity_SafariZone_Entrance.ExplainSafariZone)
      return
    }
    ctx.say(FuchsiaCity_SafariZone_Entrance.SorryYoureARegularHere)
  }
}

internal val FuchsiaCity_SafariZone_EntranceScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_SafariZone_Entrance_EventScript_InfoAttendant" to
            FuchsiaCity_SafariZone_Entrance_EventScript_InfoAttendant,
    )
