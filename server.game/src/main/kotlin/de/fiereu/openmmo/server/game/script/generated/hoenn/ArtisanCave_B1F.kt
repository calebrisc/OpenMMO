package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ArtisanCave_B1F_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HP_UP)
}

internal val ArtisanCave_B1FScripts: Map<String, Script> =
    mapOf(
        "ArtisanCave_B1F_EventScript_ItemHPUp" to ArtisanCave_B1F_EventScript_ItemHPUp,
    )
