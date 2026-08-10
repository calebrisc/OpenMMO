package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VictoryRoad_B2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CAROLINE = 99
private const val TRAINER_DIANNE = 417
private const val TRAINER_FELIX = 38
private const val TRAINER_JULIE = 100
private const val TRAINER_OWEN = 83
private const val TRAINER_VITO = 82

internal object VictoryRoad_B2F_EventScript_Vito : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_VITO, VictoryRoad_B2F.VitoIntro, VictoryRoad_B2F.VitoDefeat))
        return
    ctx.say(VictoryRoad_B2F.VitoPostBattle)
  }
}

internal object VictoryRoad_B2F_EventScript_Owen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_OWEN, VictoryRoad_B2F.OwenIntro, VictoryRoad_B2F.OwenDefeat))
        return
    ctx.say(VictoryRoad_B2F.OwenPostBattle)
  }
}

internal object VictoryRoad_B2F_EventScript_Caroline : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CAROLINE, VictoryRoad_B2F.CarolineIntro, VictoryRoad_B2F.CarolineDefeat))
        return
    ctx.say(VictoryRoad_B2F.CarolinePostBattle)
  }
}

internal object VictoryRoad_B2F_EventScript_Julie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JULIE, VictoryRoad_B2F.JulieIntro, VictoryRoad_B2F.JulieDefeat))
        return
    ctx.say(VictoryRoad_B2F.JuliePostBattle)
  }
}

internal object VictoryRoad_B2F_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_HEAL)
}

internal object VictoryRoad_B2F_EventScript_Dianne : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_DIANNE, VictoryRoad_B2F.DianneIntro, VictoryRoad_B2F.DianneDefeat))
        return
    ctx.say(VictoryRoad_B2F.DiannePostBattle)
  }
}

internal object VictoryRoad_B2F_EventScript_Felix : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FELIX, VictoryRoad_B2F.FelixIntro, VictoryRoad_B2F.FelixDefeat))
        return
    ctx.say(VictoryRoad_B2F.FelixPostBattle)
  }
}

internal val VictoryRoad_B2FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_B2F_EventScript_Vito" to VictoryRoad_B2F_EventScript_Vito,
        "VictoryRoad_B2F_EventScript_Owen" to VictoryRoad_B2F_EventScript_Owen,
        "VictoryRoad_B2F_EventScript_Caroline" to VictoryRoad_B2F_EventScript_Caroline,
        "VictoryRoad_B2F_EventScript_Julie" to VictoryRoad_B2F_EventScript_Julie,
        "VictoryRoad_B2F_EventScript_ItemFullHeal" to VictoryRoad_B2F_EventScript_ItemFullHeal,
        "VictoryRoad_B2F_EventScript_Dianne" to VictoryRoad_B2F_EventScript_Dianne,
        "VictoryRoad_B2F_EventScript_Felix" to VictoryRoad_B2F_EventScript_Felix,
    )
