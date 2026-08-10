package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FiveIsland_LostCave_Room14_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal val FiveIsland_LostCave_Room14Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room14_EventScript_ItemRareCandy" to
            FiveIsland_LostCave_Room14_EventScript_ItemRareCandy,
    )
