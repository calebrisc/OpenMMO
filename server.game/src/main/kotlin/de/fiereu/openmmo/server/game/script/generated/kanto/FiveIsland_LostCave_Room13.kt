package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FiveIsland_LostCave_Room13_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_REVIVE)
}

internal val FiveIsland_LostCave_Room13Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room13_EventScript_ItemMaxRevive" to
            FiveIsland_LostCave_Room13_EventScript_ItemMaxRevive,
    )
