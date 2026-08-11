package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VictoryRoad_3F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_COOL_COUPLE_RAY_TYRA = 485

private const val TRAINER_COOLTRAINER_ALEXA = 404
private const val TRAINER_COOLTRAINER_CAROLINE = 403
private const val TRAINER_COOLTRAINER_COLBY = 394
private const val TRAINER_COOLTRAINER_GEORGE = 393

internal object VictoryRoad_3F_EventScript_George : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_GEORGE, VictoryRoad_3F.GeorgeIntro, VictoryRoad_3F.GeorgeDefeat))
        return
    ctx.say(VictoryRoad_3F.GeorgePostBattle)
  }
}

internal object VictoryRoad_3F_EventScript_Alexa : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_ALEXA, VictoryRoad_3F.AlexaIntro, VictoryRoad_3F.AlexaDefeat))
        return
    ctx.say(VictoryRoad_3F.AlexaPostBattle)
  }
}

internal object VictoryRoad_3F_EventScript_Colby : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_COLBY, VictoryRoad_3F.ColbyIntro, VictoryRoad_3F.ColbyDefeat))
        return
    ctx.say(VictoryRoad_3F.ColbyPostBattle)
  }
}

internal object VictoryRoad_3F_EventScript_Caroline : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_CAROLINE, VictoryRoad_3F.CarolineIntro, VictoryRoad_3F.CarolineDefeat))
        return
    ctx.say(VictoryRoad_3F.CarolinePostBattle)
  }
}

internal object VictoryRoad_3F_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_REVIVE)
}

internal object VictoryRoad_3F_EventScript_ItemTM50 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM50)
}

internal object VictoryRoad_3F_EventScript_Ray : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOL_COUPLE_RAY_TYRA, VictoryRoad_3F.RayIntro, VictoryRoad_3F.RayDefeat))
        return
    ctx.say(VictoryRoad_3F.RayPostBattle)
  }
}

internal object VictoryRoad_3F_EventScript_Tyra : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOL_COUPLE_RAY_TYRA, VictoryRoad_3F.TyraIntro, VictoryRoad_3F.TyraDefeat))
        return
    ctx.say(VictoryRoad_3F.TyraPostBattle)
  }
}

internal val VictoryRoad_3FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_3F_EventScript_George" to VictoryRoad_3F_EventScript_George,
        "VictoryRoad_3F_EventScript_Alexa" to VictoryRoad_3F_EventScript_Alexa,
        "VictoryRoad_3F_EventScript_Colby" to VictoryRoad_3F_EventScript_Colby,
        "VictoryRoad_3F_EventScript_Caroline" to VictoryRoad_3F_EventScript_Caroline,
        "VictoryRoad_3F_EventScript_ItemMaxRevive" to VictoryRoad_3F_EventScript_ItemMaxRevive,
        "VictoryRoad_3F_EventScript_ItemTM50" to VictoryRoad_3F_EventScript_ItemTM50,
        "VictoryRoad_3F_EventScript_Ray" to VictoryRoad_3F_EventScript_Ray,
        "VictoryRoad_3F_EventScript_Tyra" to VictoryRoad_3F_EventScript_Tyra,
    )
