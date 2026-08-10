package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FiveIsland_LostCave_Room10_EventScript_ItemSilkScarf : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SILK_SCARF)
}

internal val FiveIsland_LostCave_Room10Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room10_EventScript_ItemSilkScarf" to
            FiveIsland_LostCave_Room10_EventScript_ItemSilkScarf,
    )
