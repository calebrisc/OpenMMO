package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SeafoamIslands_B1F_EventScript_ItemWaterStone : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.WATER_STONE)
}

internal object SeafoamIslands_B1F_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.REVIVE)
}

internal val SeafoamIslands_B1FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_B1F_EventScript_ItemWaterStone" to
            SeafoamIslands_B1F_EventScript_ItemWaterStone,
        "SeafoamIslands_B1F_EventScript_ItemRevive" to SeafoamIslands_B1F_EventScript_ItemRevive,
    )
