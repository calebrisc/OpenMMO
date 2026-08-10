package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.Route6_NorthEntrance
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object Route6_NorthEntrance_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route6_NorthEntrance.HiHowsItGoing)
}

// The tea itself is a FireRed-only item the Emerald-sourced item table lacks, so the flag from
// the tea lady is the whole gate and no bag item changes hands.
private suspend fun thirstyGuard(ctx: ScriptContext) {
  if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TEA)) {
    ctx.sign(Route6_NorthEntrance.ThirstyOnGuardDuty)
    ctx.moveSelf(
        when (ctx.facingDirection) {
          Direction.UP -> MovementStep.WALK_DOWN
          Direction.DOWN -> MovementStep.WALK_UP
          Direction.LEFT -> MovementStep.WALK_RIGHT
          else -> MovementStep.WALK_LEFT
        })
    return
  }
  ctx.sign(Route6_NorthEntrance.ThatTeaLooksTasty)
  ctx.sign(Route6_NorthEntrance.ThanksIllShareTeaWithGuards)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_ROUTE5_ROUTE6_ROUTE7_ROUTE8_GATES, 1)
}

internal object Route6_NorthEntrance_EventScript_GuardTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route6_NorthEntrance_EventScript_GuardTriggerMid : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal object Route6_NorthEntrance_EventScript_GuardTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) = thirstyGuard(ctx)
}

internal val Route6_NorthEntranceScripts: Map<String, Script> =
    mapOf(
        "Route6_NorthEntrance_EventScript_GuardTriggerRight" to
            Route6_NorthEntrance_EventScript_GuardTriggerRight,
        "Route6_NorthEntrance_EventScript_GuardTriggerMid" to
            Route6_NorthEntrance_EventScript_GuardTriggerMid,
        "Route6_NorthEntrance_EventScript_GuardTriggerLeft" to
            Route6_NorthEntrance_EventScript_GuardTriggerLeft,
        "Route6_NorthEntrance_EventScript_Guard" to Route6_NorthEntrance_EventScript_Guard,
    )
