package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FiveIsland_LostCave_Room12_EventScript_ItemSeaIncense : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SEA_INCENSE)
}

internal val FiveIsland_LostCave_Room12Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room12_EventScript_ItemSeaIncense" to
            FiveIsland_LostCave_Room12_EventScript_ItemSeaIncense,
    )
