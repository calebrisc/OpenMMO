package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.Route8_WestEntrance
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object Route8_WestEntrance_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route8_WestEntrance.HiHowsItGoing)
}

// The tea itself is a FireRed-only item the Emerald-sourced item table lacks, so the flag from
// the tea lady is the whole gate and no bag item changes hands.
private suspend fun thirstyGuard(ctx: ScriptContext) {
  if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TEA)) {
    ctx.sign(Route8_WestEntrance.ThirstyOnGuardDuty)
    ctx.moveSelf(
        when (ctx.facingDirection) {
          Direction.UP -> MovementStep.WALK_DOWN
          Direction.DOWN -> MovementStep.WALK_UP
          Direction.LEFT -> MovementStep.WALK_RIGHT
          else -> MovementStep.WALK_LEFT
        })
    return
  }
  ctx.sign(Route8_WestEntrance.ThatTeaLooksTasty)
  ctx.sign(Route8_WestEntrance.ThanksIllShareTeaWithGuards)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_ROUTE5_ROUTE6_ROUTE7_ROUTE8_GATES, 1)
}

internal object Route8_WestEntrance_EventScript_GuardTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route8_WestEntrance_EventScript_GuardTriggerMid : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route8_WestEntrance_EventScript_GuardTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal val Route8_WestEntranceScripts: Map<String, Script> =
    mapOf(
        "Route8_WestEntrance_EventScript_GuardTriggerRight" to
            Route8_WestEntrance_EventScript_GuardTriggerRight,
        "Route8_WestEntrance_EventScript_GuardTriggerMid" to
            Route8_WestEntrance_EventScript_GuardTriggerMid,
        "Route8_WestEntrance_EventScript_GuardTriggerLeft" to
            Route8_WestEntrance_EventScript_GuardTriggerLeft,
        "Route8_WestEntrance_EventScript_Guard" to Route8_WestEntrance_EventScript_Guard,
    )
