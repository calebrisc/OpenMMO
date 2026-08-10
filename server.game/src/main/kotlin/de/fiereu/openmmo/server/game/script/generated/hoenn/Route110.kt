package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALYSSA = 701
private const val TRAINER_ANTHONY = 352
private const val TRAINER_DALE = 341
private const val TRAINER_EDWARD = 232
private const val TRAINER_JACLYN = 243
private const val TRAINER_JACOB = 351
private const val TRAINER_JASMINE = 359
private const val TRAINER_JOSEPH = 700
private const val TRAINER_KALEB = 699
private const val TRAINER_TIMMY = 334

internal object Route110_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.WhichShouldIChoose)
}

internal object Route110_EventScript_CyclingGuy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.BikeTechniques)
}

internal object Route110_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.WalkOnTheLowRoad)
}

internal object Route110_EventScript_CyclingGuy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.YouGotBikeFromRydel)
}

internal object Route110_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.TwoRoads)
}

internal object Route110_EventScript_CyclingGirl1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.HairStreamsBehindMe)
}

internal object Route110_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.RideBikeAtFullSpeed)
}

internal object Route110_EventScript_Jasmine : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JASMINE, Route110.JasmineIntro, Route110.JasmineDefeated))
        return
    ctx.say(Route110.JasminePostBattle)
  }
}

internal object Route110_EventScript_Anthony : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ANTHONY, Route110.AnthonyIntro, Route110.AnthonyDefeated))
        return
    ctx.say(Route110.AnthonyPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ABIGAIL_1, Route110_Text_AbigailIntro, Route110_Text_AbigailDefeated, Route110_EventScript_AbigailRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_AbigailRematch
 * msgbox Route110_Text_AbigailPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Abigail : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Abigail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BENJAMIN_1, Route110_Text_BenjaminIntro, Route110_Text_BenjaminDefeated, Route110_EventScript_BenjaminRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_BenjaminRematch
 * msgbox Route110_Text_BenjaminPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Benjamin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Benjamin")
}

internal object Route110_EventScript_Edward : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_EDWARD, Route110.EdwardIntro, Route110.EdwardDefeated))
        return
    ctx.say(Route110.EdwardPostBattle)
  }
}

internal object Route110_EventScript_Jaclyn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JACLYN, Route110.JaclynIntro, Route110.JaclynDefeated))
        return
    ctx.say(Route110.JaclynPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_EDWIN_1, Route110_Text_EdwinIntro, Route110_Text_EdwinDefeated, Route110_EventScript_EdwinRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_EdwinRematch
 * msgbox Route110_Text_EdwinPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Edwin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Edwin")
}

internal object Route110_EventScript_Dale : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DALE, Route110.DaleIntro, Route110.DaleDefeated)) return
    ctx.say(Route110.DalePostBattle)
  }
}

internal object Route110_EventScript_ItemDireHit : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.DIRE_HIT)
}

internal object Route110_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, GetPlayerAvatarBike
 * goto_if_eq VAR_RESULT, 1, Route110_EventScript_PlayerRidingAcroBike
 * goto_if_eq VAR_CYCLING_CHALLENGE_STATE, 0, Route110_EventScript_PlayerNotRidingBike
 * msgbox Route110_Text_AlwaysAimHigher, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_ChallengeGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_ChallengeGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_WeCantTalkAboutAquaActivities, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_KickUpARuckus, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_MyFirstJobInAqua, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_AquaActionsBringSmiles, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt4")
}

internal object Route110_EventScript_Jacob : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JACOB, Route110.JacobIntro, Route110.JacobDefeated)) return
    ctx.say(Route110.JacobPostBattle)
  }
}

internal object Route110_EventScript_Timmy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TIMMY, Route110.TimmyIntro, Route110.TimmyDefeated)) return
    ctx.say(Route110.TimmyPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ISABEL_1, Route110_Text_IsabelIntro, Route110_Text_IsabelDefeated, Route110_EventScript_IsabelRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_IsabelRematch
 * msgbox Route110_Text_IsabelPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Isabel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Isabel")
}

internal object Route110_EventScript_Kaleb : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KALEB, Route110.KalebIntro, Route110.KalebDefeated)) return
    ctx.say(Route110.KalebPostBattle)
  }
}

internal object Route110_EventScript_Alyssa : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALYSSA, Route110.AlyssaIntro, Route110.AlyssaDefeated))
        return
    ctx.say(Route110.AlyssaPostBattle)
  }
}

internal object Route110_EventScript_Joseph : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JOSEPH, Route110.JosephIntro, Route110.JosephDefeated))
        return
    ctx.say(Route110.JosephPostBattle)
  }
}

internal object Route110_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ELIXIR)
}

internal object Route110_EventScript_VandalizedSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.AquaWasHere)
}

internal object Route110_EventScript_SeasideParkingSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.SeasideParkingSign)
}

internal object Route110_EventScript_CyclingRoadSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.CyclingRoadSign)
}

internal object Route110_EventScript_SlateportCitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.SlateportCitySign)
}

internal object Route110_EventScript_Route103Sign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.Route103Sign)
}

internal object Route110_EventScript_MauvilleCitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.MauvilleCitySign)
}

internal object Route110_EventScript_TrainerTipsPrlzSleep : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.TrainerTipsPrlzSleep)
}

internal object Route110_EventScript_TrainerTipsRegisterItems : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.TrainerTipsRegisterItems)
}

internal object Route110_EventScript_TrickHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.TrickHouseSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * specialvar VAR_RESULT, GetRecordedCyclingRoadResults
 * goto_if_eq VAR_RESULT, FALSE, Route110_EventScript_NoRecordSet
 * msgbox Route110_Text_BestRecord, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object Route110_EventScript_CyclingRoadResultsSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_EventScript_CyclingRoadResultsSign")
}

internal val Route110Scripts: Map<String, Script> =
    mapOf(
        "Route110_EventScript_Boy2" to Route110_EventScript_Boy2,
        "Route110_EventScript_CyclingGuy2" to Route110_EventScript_CyclingGuy2,
        "Route110_EventScript_OldWoman" to Route110_EventScript_OldWoman,
        "Route110_EventScript_CyclingGuy1" to Route110_EventScript_CyclingGuy1,
        "Route110_EventScript_OldMan" to Route110_EventScript_OldMan,
        "Route110_EventScript_CyclingGirl1" to Route110_EventScript_CyclingGirl1,
        "Route110_EventScript_Boy1" to Route110_EventScript_Boy1,
        "Route110_EventScript_Jasmine" to Route110_EventScript_Jasmine,
        "Route110_EventScript_Anthony" to Route110_EventScript_Anthony,
        "Route110_EventScript_Abigail" to Route110_EventScript_Abigail,
        "Route110_EventScript_Benjamin" to Route110_EventScript_Benjamin,
        "Route110_EventScript_Edward" to Route110_EventScript_Edward,
        "Route110_EventScript_Jaclyn" to Route110_EventScript_Jaclyn,
        "Route110_EventScript_Edwin" to Route110_EventScript_Edwin,
        "Route110_EventScript_Dale" to Route110_EventScript_Dale,
        "Route110_EventScript_ItemDireHit" to Route110_EventScript_ItemDireHit,
        "Route110_EventScript_ItemRareCandy" to Route110_EventScript_ItemRareCandy,
        "Route110_EventScript_ChallengeGuy" to Route110_EventScript_ChallengeGuy,
        "Route110_EventScript_AquaGrunt1" to Route110_EventScript_AquaGrunt1,
        "Route110_EventScript_AquaGrunt2" to Route110_EventScript_AquaGrunt2,
        "Route110_EventScript_AquaGrunt3" to Route110_EventScript_AquaGrunt3,
        "Route110_EventScript_AquaGrunt4" to Route110_EventScript_AquaGrunt4,
        "Route110_EventScript_Jacob" to Route110_EventScript_Jacob,
        "Route110_EventScript_Timmy" to Route110_EventScript_Timmy,
        "Route110_EventScript_Isabel" to Route110_EventScript_Isabel,
        "Route110_EventScript_Kaleb" to Route110_EventScript_Kaleb,
        "Route110_EventScript_Alyssa" to Route110_EventScript_Alyssa,
        "Route110_EventScript_Joseph" to Route110_EventScript_Joseph,
        "Route110_EventScript_ItemElixir" to Route110_EventScript_ItemElixir,
        "Route110_EventScript_VandalizedSign" to Route110_EventScript_VandalizedSign,
        "Route110_EventScript_SeasideParkingSign" to Route110_EventScript_SeasideParkingSign,
        "Route110_EventScript_CyclingRoadSign" to Route110_EventScript_CyclingRoadSign,
        "Route110_EventScript_SlateportCitySign" to Route110_EventScript_SlateportCitySign,
        "Route110_EventScript_Route103Sign" to Route110_EventScript_Route103Sign,
        "Route110_EventScript_MauvilleCitySign" to Route110_EventScript_MauvilleCitySign,
        "Route110_EventScript_TrainerTipsPrlzSleep" to Route110_EventScript_TrainerTipsPrlzSleep,
        "Route110_EventScript_TrainerTipsRegisterItems" to
            Route110_EventScript_TrainerTipsRegisterItems,
        "Route110_EventScript_TrickHouseSign" to Route110_EventScript_TrickHouseSign,
        "Route110_EventScript_CyclingRoadResultsSign" to
            Route110_EventScript_CyclingRoadResultsSign,
    )
