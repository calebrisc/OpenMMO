package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MagmaHideout_2F_2R
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_MAGMA_HIDEOUT_15 = 730
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_6 = 721
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_7 = 722
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_8 = 723

internal object MagmaHideout_2F_2R_EventScript_Grunt8 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_8,
        MagmaHideout_2F_2R.Grunt8Intro,
        MagmaHideout_2F_2R.Grunt8Defeat))
        return
    ctx.say(MagmaHideout_2F_2R.Grunt8PostBattle)
  }
}

internal object MagmaHideout_2F_2R_EventScript_Grunt7 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_7,
        MagmaHideout_2F_2R.Grunt7Intro,
        MagmaHideout_2F_2R.Grunt7Defeat))
        return
    ctx.say(MagmaHideout_2F_2R.Grunt7PostBattle)
  }
}

internal object MagmaHideout_2F_2R_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ELIXIR)
}

internal object MagmaHideout_2F_2R_EventScript_Grunt6 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_6,
        MagmaHideout_2F_2R.Grunt6Intro,
        MagmaHideout_2F_2R.Grunt6Defeat))
        return
    ctx.say(MagmaHideout_2F_2R.Grunt6PostBattle)
  }
}

internal object MagmaHideout_2F_2R_EventScript_Grunt15 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_15,
        MagmaHideout_2F_2R.Grunt15Intro,
        MagmaHideout_2F_2R.Grunt15Defeat))
        return
    ctx.say(MagmaHideout_2F_2R.Grunt15PostBattle)
  }
}

internal object MagmaHideout_2F_2R_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_RESTORE)
}

internal val MagmaHideout_2F_2RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_2F_2R_EventScript_Grunt8" to MagmaHideout_2F_2R_EventScript_Grunt8,
        "MagmaHideout_2F_2R_EventScript_Grunt7" to MagmaHideout_2F_2R_EventScript_Grunt7,
        "MagmaHideout_2F_2R_EventScript_ItemMaxElixir" to
            MagmaHideout_2F_2R_EventScript_ItemMaxElixir,
        "MagmaHideout_2F_2R_EventScript_Grunt6" to MagmaHideout_2F_2R_EventScript_Grunt6,
        "MagmaHideout_2F_2R_EventScript_Grunt15" to MagmaHideout_2F_2R_EventScript_Grunt15,
        "MagmaHideout_2F_2R_EventScript_ItemFullRestore" to
            MagmaHideout_2F_2R_EventScript_ItemFullRestore,
    )
