package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route118
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BARNY = 343
private const val TRAINER_CHESTER = 408
private const val TRAINER_DEANDRE = 715
private const val TRAINER_PERRY = 398
private const val TRAINER_WADE = 344

internal object Route118_EventScript_Perry : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PERRY, Route118.PerryIntro, Route118.PerryDefeat)) return
    ctx.say(Route118.PerryPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_2, GabbyAndTy_Text_GabbyIntro, GabbyAndTy_Text_GabbyDefeat, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_2, GabbyAndTy_Text_TyIntro, GabbyAndTy_Text_TyDefeat, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle2")
}

internal object Route118_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route118.CanCrossRiversWithSurf)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_5, GabbyAndTy_Text_GabbyIntro, GabbyAndTy_Text_GabbyDefeat, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_5, GabbyAndTy_Text_TyIntro, GabbyAndTy_Text_TyDefeat, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_GOOD_ROD, Route118_EventScript_ReceivedGoodRod
 * msgbox Route118_Text_YouAgreeGoodRodIsGood, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route118_EventScript_ReceiveGoodRod
 * goto_if_eq VAR_RESULT, NO, Route118_EventScript_DeclineGoodRod
 * end
 * ```
 */
internal object Route118_EventScript_GoodRodFisherman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route118_EventScript_GoodRodFisherman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ROSE_1, Route118_Text_RoseIntro, Route118_Text_RoseDefeat, Route118_EventScript_RegisterRose
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route118_EventScript_RematchRose
 * msgbox Route118_Text_RosePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route118_EventScript_Rose : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route118_EventScript_Rose")
}

internal object Route118_EventScript_Wade : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_WADE, Route118.WadeIntro, Route118.WadeDefeat)) return
    ctx.say(Route118.WadePostBattle)
  }
}

internal object Route118_EventScript_Chester : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CHESTER, Route118.ChesterIntro, Route118.ChesterDefeat))
        return
    ctx.say(Route118.ChesterPostBattle)
  }
}

internal object Route118_EventScript_Barny : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BARNY, Route118.BarnyIntro, Route118.BarnyDefeat)) return
    ctx.say(Route118.BarnyPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DALTON_1, Route118_Text_DaltonIntro, Route118_Text_DaltonDefeat, Route118_EventScript_RegisterDalton
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route118_EventScript_RematchDalton
 * msgbox Route118_Text_DaltonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route118_EventScript_Dalton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route118_EventScript_Dalton")
}

internal object Route118_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HYPER_POTION)
}

internal object Route118_EventScript_Deandre : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DEANDRE, Route118.DeandreIntro, Route118.DeandreDefeat))
        return
    ctx.say(Route118.DeandrePostBattle)
  }
}

internal object Route118_EventScript_RouteSignMauville : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route118.RouteSignMauville)
}

internal object Route118_EventScript_RouteSign119 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route118.RouteSign119)
}

internal val Route118Scripts: Map<String, Script> =
    mapOf(
        "Route118_EventScript_Perry" to Route118_EventScript_Perry,
        "GabbyAndTy_EventScript_GabbyBattle2" to GabbyAndTy_EventScript_GabbyBattle2,
        "GabbyAndTy_EventScript_TyBattle2" to GabbyAndTy_EventScript_TyBattle2,
        "Route118_EventScript_Girl" to Route118_EventScript_Girl,
        "GabbyAndTy_EventScript_GabbyBattle5" to GabbyAndTy_EventScript_GabbyBattle5,
        "GabbyAndTy_EventScript_TyBattle5" to GabbyAndTy_EventScript_TyBattle5,
        "Route118_EventScript_GoodRodFisherman" to Route118_EventScript_GoodRodFisherman,
        "Route118_EventScript_Rose" to Route118_EventScript_Rose,
        "Route118_EventScript_Wade" to Route118_EventScript_Wade,
        "Route118_EventScript_Chester" to Route118_EventScript_Chester,
        "Route118_EventScript_Barny" to Route118_EventScript_Barny,
        "Route118_EventScript_Dalton" to Route118_EventScript_Dalton,
        "Route118_EventScript_ItemHyperPotion" to Route118_EventScript_ItemHyperPotion,
        "Route118_EventScript_Deandre" to Route118_EventScript_Deandre,
        "Route118_EventScript_RouteSignMauville" to Route118_EventScript_RouteSignMauville,
        "Route118_EventScript_RouteSign119" to Route118_EventScript_RouteSign119,
    )
