package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeruleanCave_1F_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NUGGET)
}

internal object CeruleanCave_1F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_RESTORE)
}

internal object CeruleanCave_1F_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ELIXIR)
}

internal val CeruleanCave_1FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCave_1F_EventScript_ItemNugget" to CeruleanCave_1F_EventScript_ItemNugget,
        "CeruleanCave_1F_EventScript_ItemFullRestore" to
            CeruleanCave_1F_EventScript_ItemFullRestore,
        "CeruleanCave_1F_EventScript_ItemMaxElixir" to CeruleanCave_1F_EventScript_ItemMaxElixir,
    )
