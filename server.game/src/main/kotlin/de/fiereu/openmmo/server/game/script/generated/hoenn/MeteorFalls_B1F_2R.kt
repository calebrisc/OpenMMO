package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object MeteorFalls_B1F_2R_EventScript_ItemTMDragonClaw : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM02)
}

internal val MeteorFalls_B1F_2RScripts: Map<String, Script> =
    mapOf(
        "MeteorFalls_B1F_2R_EventScript_ItemTMDragonClaw" to
            MeteorFalls_B1F_2R_EventScript_ItemTMDragonClaw,
    )
