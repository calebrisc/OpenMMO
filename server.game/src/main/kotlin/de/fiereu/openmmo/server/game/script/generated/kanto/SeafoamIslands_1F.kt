package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SeafoamIslands_1F_EventScript_ItemIceHeal : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ICE_HEAL)
}

internal val SeafoamIslands_1FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_1F_EventScript_ItemIceHeal" to SeafoamIslands_1F_EventScript_ItemIceHeal,
    )
