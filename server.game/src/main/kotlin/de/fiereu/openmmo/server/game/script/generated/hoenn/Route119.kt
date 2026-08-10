package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route119
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BRENT = 223
private const val TRAINER_CHRIS = 693
private const val TRAINER_DAYTON = 760
private const val TRAINER_DONALD = 224
private const val TRAINER_DOUG = 618
private const val TRAINER_FABIAN = 759
private const val TRAINER_GREG = 619
private const val TRAINER_HIDEO = 651
private const val TRAINER_HUGH = 399
private const val TRAINER_KENT = 620
private const val TRAINER_PHIL = 400
private const val TRAINER_RACHEL = 761
private const val TRAINER_TAKASHI = 416
private const val TRAINER_TAYLOR = 225
private const val TRAINER_YASU = 415

internal object Route119_EventScript_Greg : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GREG, Route119.GregIntro, Route119.GregDefeat)) return
    ctx.say(Route119.GregPostBattle)
  }
}

internal object Route119_EventScript_Taylor : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TAYLOR, Route119.TaylorIntro, Route119.TaylorDefeat))
        return
    ctx.say(Route119.TaylorPostBattle)
  }
}

internal object Route119_EventScript_Donald : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DONALD, Route119.DonaldIntro, Route119.DonaldDefeat))
        return
    ctx.say(Route119.DonaldPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JACKSON_1, Route119_Text_JacksonIntro, Route119_Text_JacksonDefeat, Route119_EventScript_RegisterJackson
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route119_EventScript_RematchJackson
 * msgbox Route119_Text_JacksonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route119_EventScript_Jackson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Jackson")
}

internal object Route119_EventScript_Brent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BRENT, Route119.BrentIntro, Route119.BrentDefeat)) return
    ctx.say(Route119.BrentPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CATHERINE_1, Route119_Text_CatherineIntro, Route119_Text_CatherineDefeat, Route119_EventScript_RegisterCatherine
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route119_EventScript_RematchCatherine
 * msgbox Route119_Text_CatherinePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route119_EventScript_Catherine : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Catherine")
}

internal object Route119_EventScript_Doug : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DOUG, Route119.DougIntro, Route119.DougDefeat)) return
    ctx.say(Route119.DougPostBattle)
  }
}

internal object Route119_EventScript_Kent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KENT, Route119.KentIntro, Route119.KentDefeat)) return
    ctx.say(Route119.KentPostBattle)
  }
}

internal object Route119_EventScript_Yasu : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YASU, Route119.YasuIntro, Route119.YasuDefeat)) return
    ctx.say(Route119.YasuPostBattle)
  }
}

internal object Route119_EventScript_Takashi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TAKASHI, Route119.TakashiIntro, Route119.TakashiDefeat))
        return
    ctx.say(Route119.TakashiPostBattle)
  }
}

internal object Route119_EventScript_Hugh : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HUGH, Route119.HughIntro, Route119.HughDefeat)) return
    ctx.say(Route119.HughPostBattle)
  }
}

internal object Route119_EventScript_Phil : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PHIL, Route119.PhilIntro, Route119.PhilDefeat)) return
    ctx.say(Route119.PhilPostBattle)
  }
}

internal object Route119_EventScript_ItemSuperRepel : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SUPER_REPEL)
}

internal object Route119_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ZINC)
}

internal object Route119_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ELIXIR)
}

internal object Route119_EventScript_ItemLeafStone : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.LEAF_STONE)
}

internal object Route119_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object Route119_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HYPER_POTION)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route119_Text_StayAwayFromWeatherInstitute, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route119_EventScript_BridgeAquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_BridgeAquaGrunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route119_Text_DontGoNearWeatherInstitute, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route119_EventScript_BridgeAquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_BridgeAquaGrunt2")
}

internal object Route119_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route119.ThoughtFlyByCatchingBirdMons)
}

internal object Route119_EventScript_CyclingTriathleteM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route119.TallGrassSnaresBikeTires)
}

internal object Route119_EventScript_ItemHyperPotion2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HYPER_POTION)
}

internal object Route119_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route119.CanYourMonMakeSecretBase)
}

internal object Route119_EventScript_Hideo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIDEO, Route119.HideoIntro, Route119.HideoDefeat)) return
    ctx.say(Route119.HideoPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 6
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route119_EventScript_Kecleon1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Kecleon1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 7
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route119_EventScript_Kecleon2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Kecleon2")
}

internal object Route119_EventScript_Chris : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CHRIS, Route119.ChrisIntro, Route119.ChrisDefeat)) return
    ctx.say(Route119.ChrisPostBattle)
  }
}

internal object Route119_EventScript_Rachel : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RACHEL, Route119.RachelIntro, Route119.RachelDefeat))
        return
    ctx.say(Route119.RachelPostBattle)
  }
}

internal object Route119_EventScript_Dayton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DAYTON, Route119.DaytonIntro, Route119.DaytonDefeat))
        return
    ctx.say(Route119.DaytonPostBattle)
  }
}

internal object Route119_EventScript_Fabian : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_FABIAN, Route119.FabianIntro, Route119.FabianDefeat))
        return
    ctx.say(Route119.FabianPostBattle)
  }
}

internal object Route119_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NUGGET)
}

internal object Route119_EventScript_ItemElixir2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ELIXIR)
}

internal object Route119_EventScript_WeatherInstituteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route119.WeatherInstitute)
}

internal object Route119_EventScript_RouteSignFortree : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route119.RouteSignFortree)
}

internal object Route119_EventScript_TrainerTipsDecoration : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route119.TrainerTipsDecoration)
}

internal val Route119Scripts: Map<String, Script> =
    mapOf(
        "Route119_EventScript_Greg" to Route119_EventScript_Greg,
        "Route119_EventScript_Taylor" to Route119_EventScript_Taylor,
        "Route119_EventScript_Donald" to Route119_EventScript_Donald,
        "Route119_EventScript_Jackson" to Route119_EventScript_Jackson,
        "Route119_EventScript_Brent" to Route119_EventScript_Brent,
        "Route119_EventScript_Catherine" to Route119_EventScript_Catherine,
        "Route119_EventScript_Doug" to Route119_EventScript_Doug,
        "Route119_EventScript_Kent" to Route119_EventScript_Kent,
        "Route119_EventScript_Yasu" to Route119_EventScript_Yasu,
        "Route119_EventScript_Takashi" to Route119_EventScript_Takashi,
        "Route119_EventScript_Hugh" to Route119_EventScript_Hugh,
        "Route119_EventScript_Phil" to Route119_EventScript_Phil,
        "Route119_EventScript_ItemSuperRepel" to Route119_EventScript_ItemSuperRepel,
        "Route119_EventScript_ItemZinc" to Route119_EventScript_ItemZinc,
        "Route119_EventScript_ItemElixir" to Route119_EventScript_ItemElixir,
        "Route119_EventScript_ItemLeafStone" to Route119_EventScript_ItemLeafStone,
        "Route119_EventScript_ItemRareCandy" to Route119_EventScript_ItemRareCandy,
        "Route119_EventScript_ItemHyperPotion" to Route119_EventScript_ItemHyperPotion,
        "Route119_EventScript_BridgeAquaGrunt1" to Route119_EventScript_BridgeAquaGrunt1,
        "Route119_EventScript_BridgeAquaGrunt2" to Route119_EventScript_BridgeAquaGrunt2,
        "Route119_EventScript_Boy1" to Route119_EventScript_Boy1,
        "Route119_EventScript_CyclingTriathleteM" to Route119_EventScript_CyclingTriathleteM,
        "Route119_EventScript_ItemHyperPotion2" to Route119_EventScript_ItemHyperPotion2,
        "Route119_EventScript_Boy2" to Route119_EventScript_Boy2,
        "Route119_EventScript_Hideo" to Route119_EventScript_Hideo,
        "Route119_EventScript_Kecleon1" to Route119_EventScript_Kecleon1,
        "Route119_EventScript_Kecleon2" to Route119_EventScript_Kecleon2,
        "Route119_EventScript_Chris" to Route119_EventScript_Chris,
        "Route119_EventScript_Rachel" to Route119_EventScript_Rachel,
        "Route119_EventScript_Dayton" to Route119_EventScript_Dayton,
        "Route119_EventScript_Fabian" to Route119_EventScript_Fabian,
        "Route119_EventScript_ItemNugget" to Route119_EventScript_ItemNugget,
        "Route119_EventScript_ItemElixir2" to Route119_EventScript_ItemElixir2,
        "Route119_EventScript_WeatherInstituteSign" to Route119_EventScript_WeatherInstituteSign,
        "Route119_EventScript_RouteSignFortree" to Route119_EventScript_RouteSignFortree,
        "Route119_EventScript_TrainerTipsDecoration" to Route119_EventScript_TrainerTipsDecoration,
    )
