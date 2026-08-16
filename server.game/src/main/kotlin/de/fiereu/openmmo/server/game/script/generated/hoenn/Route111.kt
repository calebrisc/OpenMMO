package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route111
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BEAU = 212
private const val TRAINER_BECKY = 470
private const val TRAINER_BIANCA = 706
private const val TRAINER_BRANDEN = 745
private const val TRAINER_BRYAN = 744
private const val TRAINER_CELIA = 743
private const val TRAINER_CELINA = 705
private const val TRAINER_DAISUKE = 189
private const val TRAINER_DREW = 211
private const val TRAINER_HAYDEN = 707
private const val TRAINER_HEIDI = 469
private const val TRAINER_IRENE = 476
private const val TRAINER_TRAVIS = 218
private const val TRAINER_TYRON = 704

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setflag FLAG_LANDMARK_WINSTRATE_FAMILY
 * msgbox Route111_Text_BattleOurFamily, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route111_EventScript_BattleWinstrates
 * msgbox Route111_Text_IsThatSo, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Victor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Victor")
}

internal object Route111_EventScript_Heidi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HEIDI, Route111.HeidiIntro, Route111.HeidiDefeat)) return
    ctx.say(Route111.HeidiPostBattle)
  }
}

internal object Route111_EventScript_Man1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route111.ToughToKeepWinningUpTheRanks)
}

internal object Route111_EventScript_Drew : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DREW, Route111.DrewIntro, Route111.DrewDefeat)) return
    ctx.say(Route111.DrewPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DUSTY_1, Route111_Text_DustyIntro, Route111_Text_DustyDefeat, Route111_EventScript_RegisterDusty
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route111_EventScript_RematchDusty
 * msgbox Route111_Text_DustyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Dusty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Dusty")
}

internal object Route111_EventScript_Beau : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BEAU, Route111.BeauIntro, Route111.BeauDefeat)) return
    ctx.say(Route111.BeauPostBattle)
  }
}

internal object Route111_EventScript_Becky : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BECKY, Route111.BeckyIntro, Route111.BeckyDefeat)) return
    ctx.say(Route111.BeckyPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_1, GabbyAndTy_Text_TyPreFirstBattle, GabbyAndTy_Text_TyDefeatFirstTime, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_FirstInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_1, GabbyAndTy_Text_GabbyPreFirstBattle, GabbyAndTy_Text_GabbyDefeatFirstTime, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_FirstInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_ROUTE_111_RECEIVED_BERRY, Route111_EventScript_ReceivedBerry
 * msgbox Route111_Text_WateredPlantsEveryDayTakeBerry, MSGBOX_DEFAULT
 * giveitem ITEM_RAZZ_BERRY
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_DAILY_ROUTE_111_RECEIVED_BERRY
 * special GetPlayerBigGuyGirlString
 * msgbox Route111_Text_GoingToTryToMakeDifferentColorBerries, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Girl")
}

internal object Route111_EventScript_ItemTMSandstorm : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM37)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_4, GabbyAndTy_Text_GabbyIntro, GabbyAndTy_Text_GabbyDefeat, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_4, GabbyAndTy_Text_TyIntro, GabbyAndTy_Text_TyDefeat, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_6, GabbyAndTy_Text_GabbyIntro, GabbyAndTy_Text_GabbyDefeat, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle6 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_6, GabbyAndTy_Text_TyIntro, GabbyAndTy_Text_TyDefeat, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle6 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle6")
}

internal object Route111_EventScript_ItemStardust : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.STARDUST)
}

internal object Route111_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HP_UP)
}

internal object Route111_EventScript_Irene : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_IRENE, Route111.IreneIntro, Route111.IreneDefeat)) return
    ctx.say(Route111.IrenePostBattle)
  }
}

internal object Route111_EventScript_Travis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TRAVIS, Route111.TravisIntro, Route111.TravisDefeat))
        return
    ctx.say(Route111.TravisPostBattle)
  }
}

internal object Route111_EventScript_Daisuke : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DAISUKE, Route111.DaisukeIntro, Route111.DaisukeDefeat))
        return
    ctx.say(Route111.DaisukePostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BROOKE_1, Route111_Text_BrookeIntro, Route111_Text_BrookeDefeat, Route111_EventScript_RegisterBrooke
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route111_EventScript_RematchBrooke
 * msgbox Route111_Text_BrookePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Brooke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Brooke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WILTON_1, Route111_Text_WiltonIntro, Route111_Text_WiltonDefeat, Route111_EventScript_RegisterWilton
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route111_EventScript_RematchWilton
 * msgbox Route111_Text_WiltonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Wilton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Wilton")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route111_Text_MakingRoomUseTMToMakeYourOwn, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route111_EventScript_GiveSecretPower
 * msgbox Route111_Text_DontWantThis, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_SecretPowerMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_SecretPowerMan")
}

internal object Route111_EventScript_Man2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route111.WinstrateFamilyDestroyedMe)
}

internal object Route111_EventScript_Tyron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TYRON, Route111.TyronIntro, Route111.TyronDefeat)) return
    ctx.say(Route111.TyronPostBattle)
  }
}

internal object Route111_EventScript_Celina : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CELINA, Route111.CelinaIntro, Route111.CelinaDefeat))
        return
    ctx.say(Route111.CelinaPostBattle)
  }
}

internal object Route111_EventScript_Bianca : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIANCA, Route111.BiancaIntro, Route111.BiancaDefeat))
        return
    ctx.say(Route111.BiancaPostBattle)
  }
}

internal object Route111_EventScript_Hayden : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HAYDEN, Route111.HaydenIntro, Route111.HaydenDefeat))
        return
    ctx.say(Route111.HaydenPostBattle)
  }
}

internal object Route111_EventScript_Bryan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BRYAN, Route111.BryanIntro, Route111.BryanDefeat)) return
    ctx.say(Route111.BryanPostBattle)
  }
}

internal object Route111_EventScript_Celia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CELIA, Route111.CeliaIntro, Route111.CeliaDefeat)) return
    ctx.say(Route111.CeliaPostBattle)
  }
}

internal object Route111_EventScript_Branden : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BRANDEN, Route111.BrandenIntro, Route111.BrandenDefeat))
        return
    ctx.say(Route111.BrandenPostBattle)
  }
}

internal object Route111_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ELIXIR)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MIRAGE_TOWER_STATE, 3, Route111_EventScript_HikerMirageTowerGone
 * goto_if_eq VAR_MIRAGE_TOWER_STATE, 2, Route111_EventScript_HikerMirageTowerDisintegrated
 * goto_if_set FLAG_MIRAGE_TOWER_VISIBLE, Route111_EventScript_HikerMirageTowerVisible
 * msgbox Route111_Text_ShouldBeMirageTowerAroundHere, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Hiker")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_ROUTE111_ROCK_SMASH_MAN, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox Route111_Text_MauvilleUncleToldMeToTakeRockSmash, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE111_ROCK_SMASH_MAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object Route111_EventScript_RockSmashTipFatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route111_EventScript_RockSmashTipFatMan")
}

internal object Route111_EventScript_WinstrateHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.WinstrateHouseSign)
}

internal object Route111_EventScript_RouteSignMauville : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.RouteSignMauville)
}

internal object Route111_EventScript_RouteSign112 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.RouteSign112)
}

internal object Route111_EventScript_RouteSign113 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.RouteSign113)
}

internal object Route111_EventScript_OldLadysRestStopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.OldLadysRestStopSign)
}

internal object Route111_EventScript_TrainerTipsSpAtkSpDef : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.TrainerTipsSpAtkSpDef)
}

internal object Route111_EventScript_TrainerHillSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.TrainerHillSign)
}

internal val Route111Scripts: Map<String, Script> =
    mapOf(
        "Route111_EventScript_Victor" to Route111_EventScript_Victor,
        "Route111_EventScript_Heidi" to Route111_EventScript_Heidi,
        "Route111_EventScript_Man1" to Route111_EventScript_Man1,
        "Route111_EventScript_Drew" to Route111_EventScript_Drew,
        "Route111_EventScript_Dusty" to Route111_EventScript_Dusty,
        "Route111_EventScript_Beau" to Route111_EventScript_Beau,
        "Route111_EventScript_Becky" to Route111_EventScript_Becky,
        "GabbyAndTy_EventScript_TyBattle1" to GabbyAndTy_EventScript_TyBattle1,
        "GabbyAndTy_EventScript_GabbyBattle1" to GabbyAndTy_EventScript_GabbyBattle1,
        "Route111_EventScript_Girl" to Route111_EventScript_Girl,
        "Route111_EventScript_ItemTMSandstorm" to Route111_EventScript_ItemTMSandstorm,
        "GabbyAndTy_EventScript_GabbyBattle4" to GabbyAndTy_EventScript_GabbyBattle4,
        "GabbyAndTy_EventScript_TyBattle4" to GabbyAndTy_EventScript_TyBattle4,
        "GabbyAndTy_EventScript_GabbyBattle6" to GabbyAndTy_EventScript_GabbyBattle6,
        "GabbyAndTy_EventScript_TyBattle6" to GabbyAndTy_EventScript_TyBattle6,
        "Route111_EventScript_ItemStardust" to Route111_EventScript_ItemStardust,
        "Route111_EventScript_ItemHPUp" to Route111_EventScript_ItemHPUp,
        "Route111_EventScript_Irene" to Route111_EventScript_Irene,
        "Route111_EventScript_Travis" to Route111_EventScript_Travis,
        "Route111_EventScript_Daisuke" to Route111_EventScript_Daisuke,
        "Route111_EventScript_Brooke" to Route111_EventScript_Brooke,
        "Route111_EventScript_Wilton" to Route111_EventScript_Wilton,
        "Route111_EventScript_SecretPowerMan" to Route111_EventScript_SecretPowerMan,
        "Route111_EventScript_Man2" to Route111_EventScript_Man2,
        "Route111_EventScript_Tyron" to Route111_EventScript_Tyron,
        "Route111_EventScript_Celina" to Route111_EventScript_Celina,
        "Route111_EventScript_Bianca" to Route111_EventScript_Bianca,
        "Route111_EventScript_Hayden" to Route111_EventScript_Hayden,
        "Route111_EventScript_Bryan" to Route111_EventScript_Bryan,
        "Route111_EventScript_Celia" to Route111_EventScript_Celia,
        "Route111_EventScript_Branden" to Route111_EventScript_Branden,
        "Route111_EventScript_ItemElixir" to Route111_EventScript_ItemElixir,
        "Route111_EventScript_Hiker" to Route111_EventScript_Hiker,
        "Route111_EventScript_RockSmashTipFatMan" to Route111_EventScript_RockSmashTipFatMan,
        "Route111_EventScript_WinstrateHouseSign" to Route111_EventScript_WinstrateHouseSign,
        "Route111_EventScript_RouteSignMauville" to Route111_EventScript_RouteSignMauville,
        "Route111_EventScript_RouteSign112" to Route111_EventScript_RouteSign112,
        "Route111_EventScript_RouteSign113" to Route111_EventScript_RouteSign113,
        "Route111_EventScript_OldLadysRestStopSign" to Route111_EventScript_OldLadysRestStopSign,
        "Route111_EventScript_TrainerTipsSpAtkSpDef" to Route111_EventScript_TrainerTipsSpAtkSpDef,
        "Route111_EventScript_TrainerHillSign" to Route111_EventScript_TrainerHillSign,
    )
