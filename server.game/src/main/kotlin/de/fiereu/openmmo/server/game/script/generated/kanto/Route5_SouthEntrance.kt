package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.Route5_SouthEntrance
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object Route5_SouthEntrance_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route5_SouthEntrance.HiHowsItGoing)
}

// The tea itself is a FireRed-only item the Emerald-sourced item table lacks, so the flag from
// the tea lady is the whole gate and no bag item changes hands.
private suspend fun thirstyGuard(ctx: ScriptContext) {
  if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TEA)) {
    ctx.sign(Route5_SouthEntrance.ThirstyOnGuardDuty)
    ctx.moveSelf(
        when (ctx.facingDirection) {
          Direction.UP -> MovementStep.WALK_DOWN
          Direction.DOWN -> MovementStep.WALK_UP
          Direction.LEFT -> MovementStep.WALK_RIGHT
          else -> MovementStep.WALK_LEFT
        })
    return
  }
  ctx.sign(Route5_SouthEntrance.ThatTeaLooksTasty)
  ctx.sign(Route5_SouthEntrance.ThanksIllShareTeaWithGuards)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_ROUTE5_ROUTE6_ROUTE7_ROUTE8_GATES, 1)
}

internal object Route5_SouthEntrance_EventScript_GuardTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route5_SouthEntrance_EventScript_GuardTriggerMid : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route5_SouthEntrance_EventScript_GuardTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal val Route5_SouthEntranceScripts: Map<String, Script> =
    mapOf(
        "Route5_SouthEntrance_EventScript_GuardTriggerRight" to
            Route5_SouthEntrance_EventScript_GuardTriggerRight,
        "Route5_SouthEntrance_EventScript_GuardTriggerMid" to
            Route5_SouthEntrance_EventScript_GuardTriggerMid,
        "Route5_SouthEntrance_EventScript_GuardTriggerLeft" to
            Route5_SouthEntrance_EventScript_GuardTriggerLeft,
        "Route5_SouthEntrance_EventScript_Guard" to Route5_SouthEntrance_EventScript_Guard,
    )
