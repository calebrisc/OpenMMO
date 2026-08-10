package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AquaHideout_B2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

private const val TRAINER_MATT = 30

private const val TRAINER_GRUNT_AQUA_HIDEOUT_6 = 28
private const val TRAINER_GRUNT_AQUA_HIDEOUT_8 = 193

internal object AquaHideout_B2F_EventScript_Matt : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_MATT)
    if (!ctx.trainerBattleSingle(
        TRAINER_MATT, AquaHideout_B2F.MattIntro, AquaHideout_B2F.MattDefeat))
        return
    if (firstWin) {
      ctx.sign(AquaHideout_B2F.OurBossGotThroughHisPreparations)
      ctx.say(AquaHideout_B2F.MattPostBattle)
      ctx.setFlag(HoennFlags.FLAG_TEAM_AQUA_ESCAPED_IN_SUBMARINE)
      ctx.setFlag(HoennFlags.FLAG_HIDE_LILYCOVE_CITY_AQUA_GRUNTS)
    } else {
      ctx.say(AquaHideout_B2F.MattPostBattle)
    }
  }
}

internal object AquaHideout_B2F_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(AquaHideout_B2F.MattPostBattle)
}

internal object AquaHideout_B2F_EventScript_ItemNestBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NEST_BALL)
}

internal object AquaHideout_B2F_EventScript_Grunt6 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_AQUA_HIDEOUT_6, AquaHideout_B2F.Grunt6Intro, AquaHideout_B2F.Grunt6Defeat))
        return
    ctx.say(AquaHideout_B2F.Grunt6PostBattle)
  }
}

internal object AquaHideout_B2F_EventScript_Grunt8 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_AQUA_HIDEOUT_8, AquaHideout_B2F.Grunt8Intro, AquaHideout_B2F.Grunt8Defeat))
        return
    ctx.say(AquaHideout_B2F.Grunt8PostBattle)
  }
}

internal val AquaHideout_B2FScripts: Map<String, Script> =
    mapOf(
        "AquaHideout_B2F_EventScript_Matt" to AquaHideout_B2F_EventScript_Matt,
        "AquaHideout_B2F_EventScript_Grunt4" to AquaHideout_B2F_EventScript_Grunt4,
        "AquaHideout_B2F_EventScript_ItemNestBall" to AquaHideout_B2F_EventScript_ItemNestBall,
        "AquaHideout_B2F_EventScript_Grunt6" to AquaHideout_B2F_EventScript_Grunt6,
        "AquaHideout_B2F_EventScript_Grunt8" to AquaHideout_B2F_EventScript_Grunt8,
    )
