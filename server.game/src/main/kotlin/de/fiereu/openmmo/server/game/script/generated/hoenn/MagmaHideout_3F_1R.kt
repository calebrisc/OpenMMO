package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MagmaHideout_3F_1R
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_MAGMA_HIDEOUT_16 = 731
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_9 = 724

internal object MagmaHideout_3F_1R_EventScript_Grunt9 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_9,
        MagmaHideout_3F_1R.Grunt9Intro,
        MagmaHideout_3F_1R.Grunt9Defeat))
        return
    ctx.say(MagmaHideout_3F_1R.Grunt9PostBattle)
  }
}

internal object MagmaHideout_3F_1R_EventScript_Grunt16 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_16,
        MagmaHideout_3F_1R.Grunt16Intro,
        MagmaHideout_3F_1R.Grunt16Defeat))
        return
    ctx.say(MagmaHideout_3F_1R.Grunt16PostBattle)
  }
}

internal object MagmaHideout_3F_1R_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NUGGET)
}

internal val MagmaHideout_3F_1RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_3F_1R_EventScript_Grunt9" to MagmaHideout_3F_1R_EventScript_Grunt9,
        "MagmaHideout_3F_1R_EventScript_Grunt16" to MagmaHideout_3F_1R_EventScript_Grunt16,
        "MagmaHideout_3F_1R_EventScript_ItemNugget" to MagmaHideout_3F_1R_EventScript_ItemNugget,
    )
