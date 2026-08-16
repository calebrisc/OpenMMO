package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ShoalCave_LowTideIceRoom_EventScript_ItemTMHail : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM07)
}

internal object ShoalCave_LowTideIceRoom_EventScript_ItemNeverMeltIce : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NEVERMELTICE)
}

internal val ShoalCave_LowTideIceRoomScripts: Map<String, Script> =
    mapOf(
        "ShoalCave_LowTideIceRoom_EventScript_ItemTMHail" to
            ShoalCave_LowTideIceRoom_EventScript_ItemTMHail,
        "ShoalCave_LowTideIceRoom_EventScript_ItemNeverMeltIce" to
            ShoalCave_LowTideIceRoom_EventScript_ItemNeverMeltIce,
    )
