package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VictoryRoad_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_COOLTRAINER_NAOMI = 406
private const val TRAINER_COOLTRAINER_ROLANDO = 396

internal object VictoryRoad_1F_EventScript_Rolando : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_ROLANDO, VictoryRoad_1F.RolandoIntro, VictoryRoad_1F.RolandoDefeat))
        return
    ctx.say(VictoryRoad_1F.RolandoPostBattle)
  }
}

internal object VictoryRoad_1F_EventScript_Naomi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_NAOMI, VictoryRoad_1F.NaomiIntro, VictoryRoad_1F.NaomiDefeat))
        return
    ctx.say(VictoryRoad_1F.NaomiPostBattle)
  }
}

internal object VictoryRoad_1F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object VictoryRoad_1F_EventScript_ItemTM02 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM02)
}

internal val VictoryRoad_1FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_1F_EventScript_Rolando" to VictoryRoad_1F_EventScript_Rolando,
        "VictoryRoad_1F_EventScript_Naomi" to VictoryRoad_1F_EventScript_Naomi,
        "VictoryRoad_1F_EventScript_ItemRareCandy" to VictoryRoad_1F_EventScript_ItemRareCandy,
        "VictoryRoad_1F_EventScript_ItemTM02" to VictoryRoad_1F_EventScript_ItemTM02,
    )
