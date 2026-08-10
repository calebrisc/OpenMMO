package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MagmaHideout_3F_2R
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_MAGMA_HIDEOUT_10 = 725

internal object MagmaHideout_3F_2R_EventScript_Grunt10 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_10,
        MagmaHideout_3F_2R.Grunt10Intro,
        MagmaHideout_3F_2R.Grunt10Defeat))
        return
    ctx.say(MagmaHideout_3F_2R.Grunt10PostBattle)
  }
}

internal object MagmaHideout_3F_2R_EventScript_ItemPPMax : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_MAX)
}

internal val MagmaHideout_3F_2RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_3F_2R_EventScript_Grunt10" to MagmaHideout_3F_2R_EventScript_Grunt10,
        "MagmaHideout_3F_2R_EventScript_ItemPPMax" to MagmaHideout_3F_2R_EventScript_ItemPPMax,
    )
