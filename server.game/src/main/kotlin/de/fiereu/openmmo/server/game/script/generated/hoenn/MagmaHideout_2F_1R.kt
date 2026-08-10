package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MagmaHideout_2F_1R
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_MAGMA_HIDEOUT_14 = 729
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_3 = 718
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_4 = 719
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_5 = 720

internal object MagmaHideout_2F_1R_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_4,
        MagmaHideout_2F_1R.Grunt4Intro,
        MagmaHideout_2F_1R.Grunt4Defeat))
        return
    ctx.say(MagmaHideout_2F_1R.Grunt4PostBattle)
  }
}

internal object MagmaHideout_2F_1R_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_5,
        MagmaHideout_2F_1R.Grunt5Intro,
        MagmaHideout_2F_1R.Grunt5Defeat))
        return
    ctx.say(MagmaHideout_2F_1R.Grunt5PostBattle)
  }
}

internal object MagmaHideout_2F_1R_EventScript_Grunt14 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_14,
        MagmaHideout_2F_1R.Grunt14Intro,
        MagmaHideout_2F_1R.Grunt14Defeat))
        return
    ctx.say(MagmaHideout_2F_1R.Grunt14PostBattle)
  }
}

internal object MagmaHideout_2F_1R_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_3,
        MagmaHideout_2F_1R.Grunt3Intro,
        MagmaHideout_2F_1R.Grunt3Defeat))
        return
    ctx.say(MagmaHideout_2F_1R.Grunt3PostBattle)
  }
}

internal val MagmaHideout_2F_1RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_2F_1R_EventScript_Grunt4" to MagmaHideout_2F_1R_EventScript_Grunt4,
        "MagmaHideout_2F_1R_EventScript_Grunt5" to MagmaHideout_2F_1R_EventScript_Grunt5,
        "MagmaHideout_2F_1R_EventScript_Grunt14" to MagmaHideout_2F_1R_EventScript_Grunt14,
        "MagmaHideout_2F_1R_EventScript_Grunt3" to MagmaHideout_2F_1R_EventScript_Grunt3,
    )
