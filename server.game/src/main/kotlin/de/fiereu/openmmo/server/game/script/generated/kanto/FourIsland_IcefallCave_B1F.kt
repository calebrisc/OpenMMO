package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FourIsland_IcefallCave_B1F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_RESTORE)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NEVER_MELT_ICE
 * end
 * ```
 */
internal object FourIsland_IcefallCave_B1F_EventScript_ItemNeverMeltIce : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_IcefallCave_B1F_EventScript_ItemNeverMeltIce")
}

internal val FourIsland_IcefallCave_B1FScripts: Map<String, Script> =
    mapOf(
        "FourIsland_IcefallCave_B1F_EventScript_ItemFullRestore" to
            FourIsland_IcefallCave_B1F_EventScript_ItemFullRestore,
        "FourIsland_IcefallCave_B1F_EventScript_ItemNeverMeltIce" to
            FourIsland_IcefallCave_B1F_EventScript_ItemNeverMeltIce,
    )
