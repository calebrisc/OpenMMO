package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SeafloorCavern_Room9_EventScript_ItemTMEarthquake : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM26)
}

internal val SeafloorCavern_Room9Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room9_EventScript_ItemTMEarthquake" to
            SeafloorCavern_Room9_EventScript_ItemTMEarthquake,
    )
