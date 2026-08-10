package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object GraniteCave_B1F_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POKE_BALL)
}

internal val GraniteCave_B1FScripts: Map<String, Script> =
    mapOf(
        "GraniteCave_B1F_EventScript_ItemPokeBall" to GraniteCave_B1F_EventScript_ItemPokeBall,
    )
