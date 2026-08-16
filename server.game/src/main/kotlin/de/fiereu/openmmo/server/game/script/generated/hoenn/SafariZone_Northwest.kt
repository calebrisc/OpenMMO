package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SafariZone_Northwest
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_Northwest_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Northwest.Man)
}

internal object SafariZone_Northwest_EventScript_ItemTMSolarBeam : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM22)
}

internal val SafariZone_NorthwestScripts: Map<String, Script> =
    mapOf(
        "SafariZone_Northwest_EventScript_Man" to SafariZone_Northwest_EventScript_Man,
        "SafariZone_Northwest_EventScript_ItemTMSolarBeam" to
            SafariZone_Northwest_EventScript_ItemTMSolarBeam,
    )
