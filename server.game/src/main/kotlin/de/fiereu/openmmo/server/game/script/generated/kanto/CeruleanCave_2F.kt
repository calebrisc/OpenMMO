package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeruleanCave_2F_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

internal object CeruleanCave_2F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

internal object CeruleanCave_2F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_RESTORE)
}

internal val CeruleanCave_2FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCave_2F_EventScript_ItemPPUp" to CeruleanCave_2F_EventScript_ItemPPUp,
        "CeruleanCave_2F_EventScript_ItemUltraBall" to CeruleanCave_2F_EventScript_ItemUltraBall,
        "CeruleanCave_2F_EventScript_ItemFullRestore" to
            CeruleanCave_2F_EventScript_ItemFullRestore,
    )
