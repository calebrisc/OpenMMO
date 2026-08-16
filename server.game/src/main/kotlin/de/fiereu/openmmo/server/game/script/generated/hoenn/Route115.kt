package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route115
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALIX = 750
private const val TRAINER_HECTOR = 513
private const val TRAINER_HELENE = 751
private const val TRAINER_JAIDEN = 749
private const val TRAINER_KOICHI = 182
private const val TRAINER_KYRA = 748
private const val TRAINER_MARLENE = 752

internal object Route115_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route115.NeverKnowWhenCavePokemonWillAppear)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TIMOTHY_1, Route115_Text_TimothyIntro, Route115_Text_TimothyDefeat, Route115_EventScript_RegisterTimothy
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route115_EventScript_RematchTimothy
 * msgbox Route115_Text_TimothyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route115_EventScript_Timothy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route115_EventScript_Timothy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NOB_1, Route115_Text_NobIntro, Route115_Text_NobDefeat, Route115_EventScript_RegisterNob
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route115_EventScript_RematchNob
 * msgbox Route115_Text_NobPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route115_EventScript_Nob : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route115_EventScript_Nob")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CYNDY_1, Route115_Text_CyndyIntro, Route115_Text_CyndyDefeat, Route115_EventScript_RegisterCyndy
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route115_EventScript_RematchCyndy
 * msgbox Route115_Text_CyndyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route115_EventScript_Cyndy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route115_EventScript_Cyndy")
}

internal object Route115_EventScript_Koichi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KOICHI, Route115.KoichiIntro, Route115.KoichiDefeat))
        return
    ctx.say(Route115.KoichiPostBattle)
  }
}

internal object Route115_EventScript_Hector : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HECTOR, Route115.HectorIntro, Route115.HectorDefeat))
        return
    ctx.say(Route115.HectorPostBattle)
  }
}

internal object Route115_EventScript_ItemSuperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SUPER_POTION)
}

internal object Route115_EventScript_ItemTMFocusPunch : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM01)
}

internal object Route115_EventScript_ItemIron : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.IRON)
}

internal object Route115_EventScript_ItemGreatBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.GREAT_BALL)
}

internal object Route115_EventScript_Kyra : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KYRA, Route115.KyraIntro, Route115.KyraDefeat)) return
    ctx.say(Route115.KyraPostBattle)
  }
}

internal object Route115_EventScript_Jaiden : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JAIDEN, Route115.JaidenIntro, Route115.JaidenDefeat))
        return
    ctx.say(Route115.JaidenPostBattle)
  }
}

internal object Route115_EventScript_Helene : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HELENE, Route115.HeleneIntro, Route115.HeleneDefeat))
        return
    ctx.say(Route115.HelenePostBattle)
  }
}

internal object Route115_EventScript_Alix : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALIX, Route115.AlixIntro, Route115.AlixDefeat)) return
    ctx.say(Route115.AlixPostBattle)
  }
}

internal object Route115_EventScript_Marlene : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MARLENE, Route115.MarleneIntro, Route115.MarleneDefeat))
        return
    ctx.say(Route115.MarlenePostBattle)
  }
}

internal object Route115_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

internal object Route115_EventScript_ItemHealPowder : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HEAL_POWDER)
}

internal object Route115_EventScript_RouteSignRustboro : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route115.RouteSignRustboro)
}

internal object Route115_EventScript_MeteorFallsSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route115.MeteorFallsSign)
}

internal val Route115Scripts: Map<String, Script> =
    mapOf(
        "Route115_EventScript_Woman" to Route115_EventScript_Woman,
        "Route115_EventScript_Timothy" to Route115_EventScript_Timothy,
        "Route115_EventScript_Nob" to Route115_EventScript_Nob,
        "Route115_EventScript_Cyndy" to Route115_EventScript_Cyndy,
        "Route115_EventScript_Koichi" to Route115_EventScript_Koichi,
        "Route115_EventScript_Hector" to Route115_EventScript_Hector,
        "Route115_EventScript_ItemSuperPotion" to Route115_EventScript_ItemSuperPotion,
        "Route115_EventScript_ItemTMFocusPunch" to Route115_EventScript_ItemTMFocusPunch,
        "Route115_EventScript_ItemIron" to Route115_EventScript_ItemIron,
        "Route115_EventScript_ItemGreatBall" to Route115_EventScript_ItemGreatBall,
        "Route115_EventScript_Kyra" to Route115_EventScript_Kyra,
        "Route115_EventScript_Jaiden" to Route115_EventScript_Jaiden,
        "Route115_EventScript_Helene" to Route115_EventScript_Helene,
        "Route115_EventScript_Alix" to Route115_EventScript_Alix,
        "Route115_EventScript_Marlene" to Route115_EventScript_Marlene,
        "Route115_EventScript_ItemPPUp" to Route115_EventScript_ItemPPUp,
        "Route115_EventScript_ItemHealPowder" to Route115_EventScript_ItemHealPowder,
        "Route115_EventScript_RouteSignRustboro" to Route115_EventScript_RouteSignRustboro,
        "Route115_EventScript_MeteorFallsSign" to Route115_EventScript_MeteorFallsSign,
    )
