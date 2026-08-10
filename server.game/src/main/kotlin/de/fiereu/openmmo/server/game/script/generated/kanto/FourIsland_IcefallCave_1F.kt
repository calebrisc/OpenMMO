package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FourIsland_IcefallCave_1F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HM07
 * end
 * ```
 */
internal object FourIsland_IcefallCave_1F_EventScript_ItemHM07 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_IcefallCave_1F_EventScript_ItemHM07")
}

internal val FourIsland_IcefallCave_1FScripts: Map<String, Script> =
    mapOf(
        "FourIsland_IcefallCave_1F_EventScript_ItemUltraBall" to
            FourIsland_IcefallCave_1F_EventScript_ItemUltraBall,
        "FourIsland_IcefallCave_1F_EventScript_ItemHM07" to
            FourIsland_IcefallCave_1F_EventScript_ItemHM07,
    )
