package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route106
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_DOUGLAS = 153
private const val TRAINER_KYLA = 443
private const val TRAINER_NED = 340

internal object Route106_EventScript_Douglas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DOUGLAS, Route106.DouglasIntro, Route106.DouglasDefeated))
        return
    ctx.say(Route106.DouglasPostBattle)
  }
}

internal object Route106_EventScript_Kyla : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KYLA, Route106.KylaIntro, Route106.KylaDefeated)) return
    ctx.say(Route106.KylaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ELLIOT_1, Route106_Text_ElliotIntro, Route106_Text_ElliotDefeated, Route106_EventScript_ElliotRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route106_EventScript_ElliotRematch
 * msgbox Route106_Text_ElliotPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route106_EventScript_Elliot : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route106_EventScript_Elliot")
}

internal object Route106_EventScript_Ned : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_NED, Route106.NedIntro, Route106.NedDefeated)) return
    ctx.say(Route106.NedPostBattle)
  }
}

internal object Route106_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PROTEIN)
}

internal object Route106_EventScript_TrainerTipsSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route106.TrainerTips)
}

internal val Route106Scripts: Map<String, Script> =
    mapOf(
        "Route106_EventScript_Douglas" to Route106_EventScript_Douglas,
        "Route106_EventScript_Kyla" to Route106_EventScript_Kyla,
        "Route106_EventScript_Elliot" to Route106_EventScript_Elliot,
        "Route106_EventScript_Ned" to Route106_EventScript_Ned,
        "Route106_EventScript_ItemProtein" to Route106_EventScript_ItemProtein,
        "Route106_EventScript_TrainerTipsSign" to Route106_EventScript_TrainerTipsSign,
    )
