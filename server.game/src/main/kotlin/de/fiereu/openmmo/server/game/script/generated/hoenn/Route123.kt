package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route123
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_MIU_AND_YUKI = 484

private const val TRAINER_ALBERTO = 12
private const val TRAINER_BRAXTON = 75
private const val TRAINER_DAVIS = 539
private const val TRAINER_ED = 13
private const val TRAINER_FREDRICK = 29
private const val TRAINER_JAZMYN = 503
private const val TRAINER_JONAS = 504
private const val TRAINER_KAYLEY = 505
private const val TRAINER_KINDRA = 106
private const val TRAINER_VIOLET = 39
private const val TRAINER_WENDY = 92

internal object Route123_EventScript_Wendy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_WENDY, Route123.WendyIntro, Route123.WendyDefeat)) return
    ctx.say(Route123.WendyPostBattle)
  }
}

internal object Route123_EventScript_Braxton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BRAXTON, Route123.BraxtonIntro, Route123.BraxtonDefeat))
        return
    ctx.say(Route123.BraxtonPostBattle)
  }
}

internal object Route123_EventScript_ItemCalcium : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CALCIUM)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_GIGA_DRAIN, Route123_EventScript_ReceivedGigaDrain
 * msgbox Route123_Text_LoveGrassMonsHaveAny, MSGBOX_DEFAULT
 * special IsGrassTypeInParty
 * goto_if_eq VAR_RESULT, FALSE, Route123_EventScript_NoGrassMons
 * msgbox Route123_Text_YouLikeGrassMonsTooHaveThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_GIGA_DRAIN
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_GIGA_DRAIN
 * msgbox Route123_Text_CheckTreesWithMyGrassMon, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route123_EventScript_GigaDrainGirl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route123_EventScript_GigaDrainGirl")
}

internal object Route123_EventScript_Violet : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_VIOLET, Route123.VioletIntro, Route123.VioletDefeat))
        return
    ctx.say(Route123.VioletPostBattle)
  }
}

internal object Route123_EventScript_Yuki : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MIU_AND_YUKI, Route123.YukiIntro, Route123.YukiDefeat))
        return
    ctx.say(Route123.YukiPostBattle)
  }
}

internal object Route123_EventScript_Miu : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MIU_AND_YUKI, Route123.MiuIntro, Route123.MiuDefeat))
        return
    ctx.say(Route123.MiuPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMERON_1, Route123_Text_CameronIntro, Route123_Text_CameronDefeat, Route123_EventScript_RegisterCameron
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route123_EventScript_RematchCameron
 * msgbox Route123_Text_CameronPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route123_EventScript_Cameron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route123_EventScript_Cameron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JACKI_1, Route123_Text_JackiIntro, Route123_Text_JackiDefeat, Route123_EventScript_RegisterJacki
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route123_EventScript_RematchJacki
 * msgbox Route123_Text_JackiPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route123_EventScript_Jacki : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route123_EventScript_Jacki")
}

internal object Route123_EventScript_Kindra : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KINDRA, Route123.KindraIntro, Route123.KindraDefeat))
        return
    ctx.say(Route123.KindraPostBattle)
  }
}

internal object Route123_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

internal object Route123_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ELIXIR)
}

internal object Route123_EventScript_Jonas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JONAS, Route123.JonasIntro, Route123.JonasDefeat)) return
    ctx.say(Route123.JonasPostBattle)
  }
}

internal object Route123_EventScript_Kayley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KAYLEY, Route123.KayleyIntro, Route123.KayleyDefeat))
        return
    ctx.say(Route123.KayleyPostBattle)
  }
}

internal object Route123_EventScript_Ed : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ED, Route123.EdIntro, Route123.EdDefeat)) return
    ctx.say(Route123.EdPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FERNANDO_1, Route123_Text_FernandoIntro, Route123_Text_FernandoDefeat, Route123_EventScript_RegisterFernando
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route123_EventScript_RematchFernando
 * msgbox Route123_Text_FernandoPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route123_EventScript_Fernando : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route123_EventScript_Fernando")
}

internal object Route123_EventScript_Alberto : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALBERTO, Route123.AlbertoIntro, Route123.AlbertoDefeat))
        return
    ctx.say(Route123.AlbertoPostBattle)
  }
}

internal object Route123_EventScript_Frederick : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FREDRICK, Route123.FrederickIntro, Route123.FrederickDefeat))
        return
    ctx.say(Route123.FrederickPostBattle)
  }
}

internal object Route123_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

internal object Route123_EventScript_Jazmyn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JAZMYN, Route123.JazmynIntro, Route123.JazmynDefeat))
        return
    ctx.say(Route123.JazmynPostBattle)
  }
}

internal object Route123_EventScript_Davis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DAVIS, Route123.DavisIntro, Route123.DavisDefeat)) return
    ctx.say(Route123.DavisPostBattle)
  }
}

internal object Route123_EventScript_ItemRevivalHerb : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.REVIVAL_HERB)
}

internal object Route123_EventScript_RouteSignMtPyre : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route123.RouteSignMtPyre)
}

internal object Route123_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route123.RouteSign)
}

internal object Route123_EventScript_BerryMastersHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route123.BerryMastersHouse)
}

internal val Route123Scripts: Map<String, Script> =
    mapOf(
        "Route123_EventScript_Wendy" to Route123_EventScript_Wendy,
        "Route123_EventScript_Braxton" to Route123_EventScript_Braxton,
        "Route123_EventScript_ItemCalcium" to Route123_EventScript_ItemCalcium,
        "Route123_EventScript_GigaDrainGirl" to Route123_EventScript_GigaDrainGirl,
        "Route123_EventScript_Violet" to Route123_EventScript_Violet,
        "Route123_EventScript_Yuki" to Route123_EventScript_Yuki,
        "Route123_EventScript_Miu" to Route123_EventScript_Miu,
        "Route123_EventScript_Cameron" to Route123_EventScript_Cameron,
        "Route123_EventScript_Jacki" to Route123_EventScript_Jacki,
        "Route123_EventScript_Kindra" to Route123_EventScript_Kindra,
        "Route123_EventScript_ItemUltraBall" to Route123_EventScript_ItemUltraBall,
        "Route123_EventScript_ItemElixir" to Route123_EventScript_ItemElixir,
        "Route123_EventScript_Jonas" to Route123_EventScript_Jonas,
        "Route123_EventScript_Kayley" to Route123_EventScript_Kayley,
        "Route123_EventScript_Ed" to Route123_EventScript_Ed,
        "Route123_EventScript_Fernando" to Route123_EventScript_Fernando,
        "Route123_EventScript_Alberto" to Route123_EventScript_Alberto,
        "Route123_EventScript_Frederick" to Route123_EventScript_Frederick,
        "Route123_EventScript_ItemPPUp" to Route123_EventScript_ItemPPUp,
        "Route123_EventScript_Jazmyn" to Route123_EventScript_Jazmyn,
        "Route123_EventScript_Davis" to Route123_EventScript_Davis,
        "Route123_EventScript_ItemRevivalHerb" to Route123_EventScript_ItemRevivalHerb,
        "Route123_EventScript_RouteSignMtPyre" to Route123_EventScript_RouteSignMtPyre,
        "Route123_EventScript_RouteSign" to Route123_EventScript_RouteSign,
        "Route123_EventScript_BerryMastersHouseSign" to Route123_EventScript_BerryMastersHouseSign,
    )
