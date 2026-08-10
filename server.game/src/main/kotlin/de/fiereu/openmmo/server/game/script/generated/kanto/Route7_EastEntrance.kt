package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.Route7_EastEntrance
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object Route7_EastEntrance_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route7_EastEntrance.HiHowsItGoing)
}

// The tea itself is a FireRed-only item the Emerald-sourced item table lacks, so the flag from
// the tea lady is the whole gate and no bag item changes hands.
private suspend fun thirstyGuard(ctx: ScriptContext) {
  if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TEA)) {
    ctx.sign(Route7_EastEntrance.ThirstyOnGuardDuty)
    ctx.moveSelf(
        when (ctx.facingDirection) {
          Direction.UP -> MovementStep.WALK_DOWN
          Direction.DOWN -> MovementStep.WALK_UP
          Direction.LEFT -> MovementStep.WALK_RIGHT
          else -> MovementStep.WALK_LEFT
        })
    return
  }
  ctx.sign(Route7_EastEntrance.ThatTeaLooksTasty)
  ctx.sign(Route7_EastEntrance.ThanksIllShareTeaWithGuards)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_ROUTE5_ROUTE6_ROUTE7_ROUTE8_GATES, 1)
}

internal object Route7_EastEntrance_EventScript_GuardTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route7_EastEntrance_EventScript_GuardTriggerMid : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route7_EastEntrance_EventScript_GuardTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal val Route7_EastEntranceScripts: Map<String, Script> =
    mapOf(
        "Route7_EastEntrance_EventScript_GuardTriggerRight" to
            Route7_EastEntrance_EventScript_GuardTriggerRight,
        "Route7_EastEntrance_EventScript_GuardTriggerMid" to
            Route7_EastEntrance_EventScript_GuardTriggerMid,
        "Route7_EastEntrance_EventScript_GuardTriggerLeft" to
            Route7_EastEntrance_EventScript_GuardTriggerLeft,
        "Route7_EastEntrance_EventScript_Guard" to Route7_EastEntrance_EventScript_Guard,
    )
