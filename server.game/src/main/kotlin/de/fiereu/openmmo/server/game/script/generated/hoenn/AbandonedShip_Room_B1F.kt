package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object AbandonedShip_Room_B1F_EventScript_ItemTMIceBeam : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM13)
}

internal val AbandonedShip_Room_B1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Room_B1F_EventScript_ItemTMIceBeam" to
            AbandonedShip_Room_B1F_EventScript_ItemTMIceBeam,
    )
