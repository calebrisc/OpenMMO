package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SeafoamIslands_B2F_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.BIG_PEARL)
}

internal val SeafoamIslands_B2FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_B2F_EventScript_ItemBigPearl" to
            SeafoamIslands_B2F_EventScript_ItemBigPearl,
    )
