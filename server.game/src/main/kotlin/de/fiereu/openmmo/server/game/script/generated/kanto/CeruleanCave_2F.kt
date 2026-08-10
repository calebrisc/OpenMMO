package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeruleanCave_2F_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ULTRA_BALL
 * end
 * ```
 */
internal object CeruleanCave_2F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCave_2F_EventScript_ItemUltraBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_RESTORE
 * end
 * ```
 */
internal object CeruleanCave_2F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCave_2F_EventScript_ItemFullRestore")
}

internal val CeruleanCave_2FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCave_2F_EventScript_ItemPPUp" to CeruleanCave_2F_EventScript_ItemPPUp,
        "CeruleanCave_2F_EventScript_ItemUltraBall" to CeruleanCave_2F_EventScript_ItemUltraBall,
        "CeruleanCave_2F_EventScript_ItemFullRestore" to
            CeruleanCave_2F_EventScript_ItemFullRestore,
    )
