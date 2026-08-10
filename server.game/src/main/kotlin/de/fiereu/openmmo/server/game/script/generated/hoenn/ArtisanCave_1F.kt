package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ArtisanCave_1F_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CARBOS)
}

internal val ArtisanCave_1FScripts: Map<String, Script> =
    mapOf(
        "ArtisanCave_1F_EventScript_ItemCarbos" to ArtisanCave_1F_EventScript_ItemCarbos,
    )
