package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route18
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BIRD_KEEPER_JACOB = 309
private const val TRAINER_BIRD_KEEPER_RAMIRO = 308
private const val TRAINER_BIRD_KEEPER_WILTON = 307

internal object Route18_EventScript_Jacob : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_JACOB, Route18.JacobIntro, Route18.JacobDefeat))
        return
    ctx.say(Route18.JacobPostBattle)
  }
}

internal object Route18_EventScript_Ramiro : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_RAMIRO, Route18.RamiroIntro, Route18.RamiroDefeat))
        return
    ctx.say(Route18.RamiroPostBattle)
  }
}

internal object Route18_EventScript_Wilton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_WILTON, Route18.WiltonIntro, Route18.WiltonDefeat))
        return
    ctx.say(Route18.WiltonPostBattle)
  }
}

internal object Route18_EventScript_CyclingRoadSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route18.CyclingRoadSign)
}

internal object Route18_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route18.RouteSign)
}

internal val Route18Scripts: Map<String, Script> =
    mapOf(
        "Route18_EventScript_Jacob" to Route18_EventScript_Jacob,
        "Route18_EventScript_Ramiro" to Route18_EventScript_Ramiro,
        "Route18_EventScript_Wilton" to Route18_EventScript_Wilton,
        "Route18_EventScript_CyclingRoadSign" to Route18_EventScript_CyclingRoadSign,
        "Route18_EventScript_RouteSign" to Route18_EventScript_RouteSign,
    )
