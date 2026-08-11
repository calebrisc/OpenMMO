package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route109
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_MEL_AND_PAUL = 680

private const val TRAINER_ALICE = 448
private const val TRAINER_AUSTINA = 58
private const val TRAINER_CARTER = 345
private const val TRAINER_CHANDLER = 698
private const val TRAINER_DAVID = 158
private const val TRAINER_EDMOND = 491
private const val TRAINER_ELIJAH = 742
private const val TRAINER_GWEN = 59
private const val TRAINER_HAILEY = 697
private const val TRAINER_HUEY = 490

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_DELIVERED_DEVON_GOODS, Route109_EventScript_HaveNotDeliveredDevonGood
 * goto Route109_EventScript_DeliveredDevonGoods
 * end
 * ```
 */
internal object Route109_EventScript_MrBriney : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route109_EventScript_MrBriney")
}

internal object Route109_EventScript_David : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DAVID, Route109.DavidIntro, Route109.DavidDefeated)) return
    ctx.say(Route109.DavidPostBattle)
  }
}

internal object Route109_EventScript_Alice : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALICE, Route109.AliceIntro, Route109.AliceDefeated)) return
    ctx.say(Route109.AlicePostBattle)
  }
}

internal object Route109_EventScript_Huey : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HUEY, Route109.HueyIntro, Route109.HueyDefeated)) return
    ctx.say(Route109.HueyPostBattle)
  }
}

internal object Route109_EventScript_Edmond : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_EDMOND, Route109.EdmondIntro, Route109.EdmondDefeated))
        return
    ctx.say(Route109.EdmondPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RICKY_1, Route109_Text_RickyIntro, Route109_Text_RickyDefeated, Route109_EventScript_RickyRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route109_EventScript_RickyRematch
 * msgbox Route109_Text_RickyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route109_EventScript_Ricky : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route109_EventScript_Ricky")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LOLA_1, Route109_Text_LolaIntro, Route109_Text_LolaDefeated, Route109_EventScript_LolaRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route109_EventScript_LolaRematch
 * msgbox Route109_Text_LolaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route109_EventScript_Lola : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route109_EventScript_Lola")
}

internal object Route109_EventScript_SeashoreHouseGirl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route109.ChillAtMyPapasSpot)
}

internal object Route109_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

internal object Route109_EventScript_Gwen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GWEN, Route109.GwenIntro, Route109.GwenDefeated)) return
    ctx.say(Route109.GwenPostBattle)
  }
}

internal object Route109_EventScript_Austina : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_AUSTINA, Route109.AustinaIntro, Route109.AustinaDefeated))
        return
    ctx.say(Route109.AustinaPostBattle)
  }
}

internal object Route109_EventScript_Carter : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CARTER, Route109.CarterIntro, Route109.CarterDefeated))
        return
    ctx.say(Route109.CarterPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route109_Text_SandCastleTakingLongTime, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route109_EventScript_SandCastleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route109_EventScript_SandCastleBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special GetPlayerBigGuyGirlString
 * goto_if_set FLAG_RECEIVED_SOFT_SAND, Route109_EventScript_AlreadyReceivedSoftSand
 * msgbox Route109_Text_YouCanHaveThis, MSGBOX_DEFAULT
 * giveitem ITEM_SOFT_SAND
 * goto_if_eq VAR_RESULT, 0, Common_EventScript_ShowBagIsFull
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag FLAG_RECEIVED_SOFT_SAND
 * release
 * end
 * ```
 */
internal object Route109_EventScript_SoftSandGirl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route109_EventScript_SoftSandGirl")
}

internal object Route109_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route109.LittleKidsDartAround)
}

internal object Route109_EventScript_Mel : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MEL_AND_PAUL, Route109.MelIntro, Route109.MelDefeated))
        return
    ctx.say(Route109.MelPostBattle)
  }
}

internal object Route109_EventScript_Paul : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MEL_AND_PAUL, Route109.PaulIntro, Route109.PaulDefeated))
        return
    ctx.say(Route109.PaulPostBattle)
  }
}

internal object Route109_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route109.ZigzagoonPicksUpLitter)
}

internal object Route109_EventScript_Zigzagoon : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route109.ZigzagoonCry)
}

internal object Route109_EventScript_Hailey : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HAILEY, Route109.HaileyIntro, Route109.HaileyDefeated))
        return
    ctx.say(Route109.HaileyPostBattle)
  }
}

internal object Route109_EventScript_Chandler : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANDLER, Route109.ChandlerIntro, Route109.ChandlerDefeated))
        return
    ctx.say(Route109.ChandlerPostBattle)
  }
}

internal object Route109_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POTION)
}

internal object Route109_EventScript_Elijah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ELIJAH, Route109.ElijahIntro, Route109.ElijahDefeated))
        return
    ctx.say(Route109.ElijahPostBattle)
  }
}

internal object Route109_EventScript_SeashoreHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route109.SeashoreHouseSign)
}

internal object Route109_EventScript_TrainerTipsSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route109.TrainerTipsSign)
}

internal val Route109Scripts: Map<String, Script> =
    mapOf(
        "Route109_EventScript_MrBriney" to Route109_EventScript_MrBriney,
        "Route109_EventScript_David" to Route109_EventScript_David,
        "Route109_EventScript_Alice" to Route109_EventScript_Alice,
        "Route109_EventScript_Huey" to Route109_EventScript_Huey,
        "Route109_EventScript_Edmond" to Route109_EventScript_Edmond,
        "Route109_EventScript_Ricky" to Route109_EventScript_Ricky,
        "Route109_EventScript_Lola" to Route109_EventScript_Lola,
        "Route109_EventScript_SeashoreHouseGirl" to Route109_EventScript_SeashoreHouseGirl,
        "Route109_EventScript_ItemPPUp" to Route109_EventScript_ItemPPUp,
        "Route109_EventScript_Gwen" to Route109_EventScript_Gwen,
        "Route109_EventScript_Austina" to Route109_EventScript_Austina,
        "Route109_EventScript_Carter" to Route109_EventScript_Carter,
        "Route109_EventScript_SandCastleBoy" to Route109_EventScript_SandCastleBoy,
        "Route109_EventScript_SoftSandGirl" to Route109_EventScript_SoftSandGirl,
        "Route109_EventScript_Woman" to Route109_EventScript_Woman,
        "Route109_EventScript_Mel" to Route109_EventScript_Mel,
        "Route109_EventScript_Paul" to Route109_EventScript_Paul,
        "Route109_EventScript_OldMan" to Route109_EventScript_OldMan,
        "Route109_EventScript_Zigzagoon" to Route109_EventScript_Zigzagoon,
        "Route109_EventScript_Hailey" to Route109_EventScript_Hailey,
        "Route109_EventScript_Chandler" to Route109_EventScript_Chandler,
        "Route109_EventScript_ItemPotion" to Route109_EventScript_ItemPotion,
        "Route109_EventScript_Elijah" to Route109_EventScript_Elijah,
        "Route109_EventScript_SeashoreHouseSign" to Route109_EventScript_SeashoreHouseSign,
        "Route109_EventScript_TrainerTipsSign" to Route109_EventScript_TrainerTipsSign,
    )
