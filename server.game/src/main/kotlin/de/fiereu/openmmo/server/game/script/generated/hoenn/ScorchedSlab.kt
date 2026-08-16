package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ScorchedSlab_EventScript_ItemTMSunnyDay : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM11)
}

internal val ScorchedSlabScripts: Map<String, Script> =
    mapOf(
        "ScorchedSlab_EventScript_ItemTMSunnyDay" to ScorchedSlab_EventScript_ItemTMSunnyDay,
    )
