package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VictoryRoad_B1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_HALLE = 546
private const val TRAINER_MICHELLE = 98
private const val TRAINER_MITCHELL = 540
private const val TRAINER_SAMUEL = 81
private const val TRAINER_SHANNON = 97

internal object VictoryRoad_B1F_EventScript_Samuel : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAMUEL, VictoryRoad_B1F.SamuelIntro, VictoryRoad_B1F.SamuelDefeat))
        return
    ctx.say(VictoryRoad_B1F.SamuelPostBattle)
  }
}

internal object VictoryRoad_B1F_EventScript_Shannon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SHANNON, VictoryRoad_B1F.ShannonIntro, VictoryRoad_B1F.ShannonDefeat))
        return
    ctx.say(VictoryRoad_B1F.ShannonPostBattle)
  }
}

internal object VictoryRoad_B1F_EventScript_Michelle : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_MICHELLE, VictoryRoad_B1F.MichelleIntro, VictoryRoad_B1F.MichelleDefeat))
        return
    ctx.say(VictoryRoad_B1F.MichellePostBattle)
  }
}

internal object VictoryRoad_B1F_EventScript_ItemTMPsychic : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM29)
}

internal object VictoryRoad_B1F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_RESTORE)
}

internal object VictoryRoad_B1F_EventScript_Mitchell : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_MITCHELL, VictoryRoad_B1F.MitchellIntro, VictoryRoad_B1F.MitchellDefeat))
        return
    ctx.say(VictoryRoad_B1F.MitchellPostBattle)
  }
}

internal object VictoryRoad_B1F_EventScript_Halle : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HALLE, VictoryRoad_B1F.HalleIntro, VictoryRoad_B1F.HalleDefeat))
        return
    ctx.say(VictoryRoad_B1F.HallePostBattle)
  }
}

internal val VictoryRoad_B1FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_B1F_EventScript_Samuel" to VictoryRoad_B1F_EventScript_Samuel,
        "VictoryRoad_B1F_EventScript_Shannon" to VictoryRoad_B1F_EventScript_Shannon,
        "VictoryRoad_B1F_EventScript_Michelle" to VictoryRoad_B1F_EventScript_Michelle,
        "VictoryRoad_B1F_EventScript_ItemTMPsychic" to VictoryRoad_B1F_EventScript_ItemTMPsychic,
        "VictoryRoad_B1F_EventScript_ItemFullRestore" to
            VictoryRoad_B1F_EventScript_ItemFullRestore,
        "VictoryRoad_B1F_EventScript_Mitchell" to VictoryRoad_B1F_EventScript_Mitchell,
        "VictoryRoad_B1F_EventScript_Halle" to VictoryRoad_B1F_EventScript_Halle,
    )
