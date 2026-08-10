package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object MagmaHideout_3F_3R_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ESCAPE_ROPE)
}

internal val MagmaHideout_3F_3RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_3F_3R_EventScript_ItemEscapeRope" to
            MagmaHideout_3F_3R_EventScript_ItemEscapeRope,
    )
