package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_B1F_Room1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_FISHERMAN_BARNY = 224
private const val TRAINER_SAILOR_PHILLIP = 140

internal object SSAnne_B1F_Room1_EventScript_Barny : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FISHERMAN_BARNY, SSAnne_B1F_Room1.BarnyIntro, SSAnne_B1F_Room1.BarnyDefeat))
        return
    ctx.say(SSAnne_B1F_Room1.BarnyPostBattle)
  }
}

internal object SSAnne_B1F_Room1_EventScript_Phillip : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_PHILLIP, SSAnne_B1F_Room1.PhillipIntro, SSAnne_B1F_Room1.PhillipDefeat))
        return
    ctx.say(SSAnne_B1F_Room1.PhillipPostBattle)
  }
}

internal val SSAnne_B1F_Room1Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room1_EventScript_Barny" to SSAnne_B1F_Room1_EventScript_Barny,
        "SSAnne_B1F_Room1_EventScript_Phillip" to SSAnne_B1F_Room1_EventScript_Phillip,
    )
