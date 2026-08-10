package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.RocketHideout_B1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TEAM_ROCKET_GRUNT_10 = 360
private const val TRAINER_TEAM_ROCKET_GRUNT_11 = 361
private const val TRAINER_TEAM_ROCKET_GRUNT_8 = 358
private const val TRAINER_TEAM_ROCKET_GRUNT_9 = 359

internal object RocketHideout_B1F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_9, RocketHideout_B1F.Grunt2Intro, RocketHideout_B1F.Grunt2Defeat))
        return
    ctx.say(RocketHideout_B1F.Grunt2PostBattle)
  }
}

internal object RocketHideout_B1F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_8, RocketHideout_B1F.Grunt1Intro, RocketHideout_B1F.Grunt1Defeat))
        return
    ctx.say(RocketHideout_B1F.Grunt1PostBattle)
  }
}

internal object RocketHideout_B1F_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_11,
        RocketHideout_B1F.Grunt4Intro,
        RocketHideout_B1F.Grunt4Defeat))
        return
    ctx.say(RocketHideout_B1F.Grunt4PostBattle)
  }
}

internal object RocketHideout_B1F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_10,
        RocketHideout_B1F.Grunt3Intro,
        RocketHideout_B1F.Grunt3Defeat))
        return
    ctx.say(RocketHideout_B1F.Grunt3PostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_12, RocketHideout_B1F_Text_Grunt5Intro, RocketHideout_B1F_Text_Grunt5Defeat, RocketHideout_B1F_EventScript_DefeatedGrunt5
 * msgbox RocketHideout_B1F_Text_Grunt5PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B1F_EventScript_Grunt5")
}

internal object RocketHideout_B1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ESCAPE_ROPE)
}

internal object RocketHideout_B1F_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HYPER_POTION)
}

internal val RocketHideout_B1FScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_B1F_EventScript_Grunt2" to RocketHideout_B1F_EventScript_Grunt2,
        "RocketHideout_B1F_EventScript_Grunt1" to RocketHideout_B1F_EventScript_Grunt1,
        "RocketHideout_B1F_EventScript_Grunt4" to RocketHideout_B1F_EventScript_Grunt4,
        "RocketHideout_B1F_EventScript_Grunt3" to RocketHideout_B1F_EventScript_Grunt3,
        "RocketHideout_B1F_EventScript_Grunt5" to RocketHideout_B1F_EventScript_Grunt5,
        "RocketHideout_B1F_EventScript_ItemEscapeRope" to
            RocketHideout_B1F_EventScript_ItemEscapeRope,
        "RocketHideout_B1F_EventScript_ItemHyperPotion" to
            RocketHideout_B1F_EventScript_ItemHyperPotion,
    )
