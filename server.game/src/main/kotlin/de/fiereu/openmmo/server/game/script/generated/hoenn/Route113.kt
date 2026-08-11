package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route113
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TORI_AND_TIA = 677

private const val TRAINER_COBY = 709
private const val TRAINER_DILLON = 327
private const val TRAINER_JAYLEN = 326
private const val TRAINER_LAWRENCE = 710
private const val TRAINER_LUNG = 420
private const val TRAINER_SOPHIE = 708
private const val TRAINER_WYATT = 711

internal object Route113_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route113.FunWalkingThroughAsh)
}

internal object Route113_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route113.AshCanBeFashionedIntoGlass)
}

internal object Route113_EventScript_Jaylen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JAYLEN, Route113.JaylenIntro, Route113.JaylenDefeat))
        return
    ctx.say(Route113.JaylenPostBattle)
  }
}

internal object Route113_EventScript_Dillon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DILLON, Route113.DillonIntro, Route113.DillonDefeat))
        return
    ctx.say(Route113.DillonPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MADELINE_1, Route113_Text_MadelineIntro, Route113_Text_MadelineDefeat, Route113_EventScript_RegisterMadeline
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route113_EventScript_RematchMadeline
 * msgbox Route113_Text_MadelinePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route113_EventScript_Madeline : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Madeline")
}

internal object Route113_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ETHER)
}

internal object Route113_EventScript_ItemSuperRepel : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SUPER_REPEL)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LAO_1, Route113_Text_LaoIntro, Route113_Text_LaoDefeat, Route113_EventScript_RegisterLao
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route113_EventScript_RematchLao
 * msgbox Route113_Text_LaoPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route113_EventScript_Lao : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Lao")
}

internal object Route113_EventScript_Lung : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LUNG, Route113.LungIntro, Route113.LungDefeat)) return
    ctx.say(Route113.LungPostBattle)
  }
}

internal object Route113_EventScript_Tori : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TORI_AND_TIA, Route113.ToriIntro, Route113.ToriDefeat))
        return
    ctx.say(Route113.ToriPostBattle)
  }
}

internal object Route113_EventScript_Tia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TORI_AND_TIA, Route113.TiaIntro, Route113.TiaDefeat))
        return
    ctx.say(Route113.TiaPostBattle)
  }
}

internal object Route113_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HYPER_POTION)
}

internal object Route113_EventScript_Wyatt : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_WYATT, Route113.WyattIntro, Route113.WyattDefeat)) return
    ctx.say(Route113.WyattPostBattle)
  }
}

internal object Route113_EventScript_Lawrence : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LAWRENCE, Route113.LawrenceIntro, Route113.LawrenceDefeat))
        return
    ctx.say(Route113.LawrencePostBattle)
  }
}

internal object Route113_EventScript_Sophie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SOPHIE, Route113.SophieIntro, Route113.SophieDefeat))
        return
    ctx.say(Route113.SophiePostBattle)
  }
}

internal object Route113_EventScript_Coby : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_COBY, Route113.CobyIntro, Route113.CobyDefeat)) return
    ctx.say(Route113.CobyPostBattle)
  }
}

internal object Route113_EventScript_RouteSign111 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.RouteSign111)
}

internal object Route113_EventScript_RouteSignFallarbor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.RouteSignFallarbor)
}

internal object Route113_EventScript_TrainerTipsRegisterKeyItems : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.TrainerTipsRegisterKeyItems)
}

internal object Route113_EventScript_GlassWorkshopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.GlassWorkshopSign)
}

internal val Route113Scripts: Map<String, Script> =
    mapOf(
        "Route113_EventScript_NinjaBoy" to Route113_EventScript_NinjaBoy,
        "Route113_EventScript_Gentleman" to Route113_EventScript_Gentleman,
        "Route113_EventScript_Jaylen" to Route113_EventScript_Jaylen,
        "Route113_EventScript_Dillon" to Route113_EventScript_Dillon,
        "Route113_EventScript_Madeline" to Route113_EventScript_Madeline,
        "Route113_EventScript_ItemMaxEther" to Route113_EventScript_ItemMaxEther,
        "Route113_EventScript_ItemSuperRepel" to Route113_EventScript_ItemSuperRepel,
        "Route113_EventScript_Lao" to Route113_EventScript_Lao,
        "Route113_EventScript_Lung" to Route113_EventScript_Lung,
        "Route113_EventScript_Tori" to Route113_EventScript_Tori,
        "Route113_EventScript_Tia" to Route113_EventScript_Tia,
        "Route113_EventScript_ItemHyperPotion" to Route113_EventScript_ItemHyperPotion,
        "Route113_EventScript_Wyatt" to Route113_EventScript_Wyatt,
        "Route113_EventScript_Lawrence" to Route113_EventScript_Lawrence,
        "Route113_EventScript_Sophie" to Route113_EventScript_Sophie,
        "Route113_EventScript_Coby" to Route113_EventScript_Coby,
        "Route113_EventScript_RouteSign111" to Route113_EventScript_RouteSign111,
        "Route113_EventScript_RouteSignFallarbor" to Route113_EventScript_RouteSignFallarbor,
        "Route113_EventScript_TrainerTipsRegisterKeyItems" to
            Route113_EventScript_TrainerTipsRegisterKeyItems,
        "Route113_EventScript_GlassWorkshopSign" to Route113_EventScript_GlassWorkshopSign,
    )
