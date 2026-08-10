package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VictoryRoad_2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BLACK_BELT_DAISUKE = 325
private const val TRAINER_JUGGLER_GREGORY = 290
private const val TRAINER_JUGGLER_NELSON = 287
private const val TRAINER_POKEMANIAC_DAWSON = 167
private const val TRAINER_TAMER_VINCENT = 298

internal object VictoryRoad_2F_EventScript_Dawson : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_POKEMANIAC_DAWSON, VictoryRoad_2F.DawsonIntro, VictoryRoad_2F.DawsonDefeat))
        return
    ctx.say(VictoryRoad_2F.DawsonPostBattle)
  }
}

internal object VictoryRoad_2F_EventScript_Daisuke : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BLACK_BELT_DAISUKE, VictoryRoad_2F.DaisukeIntro, VictoryRoad_2F.DaisukeDefeat))
        return
    ctx.say(VictoryRoad_2F.DaisukePostBattle)
  }
}

internal object VictoryRoad_2F_EventScript_Nelson : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_NELSON, VictoryRoad_2F.NelsonIntro, VictoryRoad_2F.NelsonDefeat))
        return
    ctx.say(VictoryRoad_2F.NelsonPostBattle)
  }
}

internal object VictoryRoad_2F_EventScript_Vincent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TAMER_VINCENT, VictoryRoad_2F.VincentIntro, VictoryRoad_2F.VincentDefeat))
        return
    ctx.say(VictoryRoad_2F.VincentPostBattle)
  }
}

internal object VictoryRoad_2F_EventScript_Gregory : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_GREGORY, VictoryRoad_2F.GregoryIntro, VictoryRoad_2F.GregoryDefeat))
        return
    ctx.say(VictoryRoad_2F.GregoryPostBattle)
  }
}

internal object VictoryRoad_2F_EventScript_ItemGuardSpec : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.GUARD_SPEC)
}

internal object VictoryRoad_2F_EventScript_ItemTM07 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM07)
}

internal object VictoryRoad_2F_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_HEAL)
}

internal object VictoryRoad_2F_EventScript_ItemTM37 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM37)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_DOUBLE_EDGE, EventScript_DoubleEdgeTaught
 * msgbox Text_DoubleEdgeTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_DoubleEdgeDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_DoubleEdgeDeclined
 * msgbox Text_DoubleEdgeWhichMon
 * setvar VAR_0x8005, MOVETUTOR_DOUBLE_EDGE
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_DoubleEdgeDeclined
 * setflag FLAG_TUTOR_DOUBLE_EDGE
 * goto EventScript_DoubleEdgeTaught
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_DoubleEdgeTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_2F_EventScript_DoubleEdgeTutor")
}

internal val VictoryRoad_2FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_2F_EventScript_Dawson" to VictoryRoad_2F_EventScript_Dawson,
        "VictoryRoad_2F_EventScript_Daisuke" to VictoryRoad_2F_EventScript_Daisuke,
        "VictoryRoad_2F_EventScript_Nelson" to VictoryRoad_2F_EventScript_Nelson,
        "VictoryRoad_2F_EventScript_Vincent" to VictoryRoad_2F_EventScript_Vincent,
        "VictoryRoad_2F_EventScript_Gregory" to VictoryRoad_2F_EventScript_Gregory,
        "VictoryRoad_2F_EventScript_ItemGuardSpec" to VictoryRoad_2F_EventScript_ItemGuardSpec,
        "VictoryRoad_2F_EventScript_ItemTM07" to VictoryRoad_2F_EventScript_ItemTM07,
        "VictoryRoad_2F_EventScript_ItemFullHeal" to VictoryRoad_2F_EventScript_ItemFullHeal,
        "VictoryRoad_2F_EventScript_ItemTM37" to VictoryRoad_2F_EventScript_ItemTM37,
        "VictoryRoad_2F_EventScript_DoubleEdgeTutor" to VictoryRoad_2F_EventScript_DoubleEdgeTutor,
    )
