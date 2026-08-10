package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_B1F_Room4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SAILOR_DUNCAN = 137
private const val TRAINER_SAILOR_LEONARD = 136

internal object SSAnne_B1F_Room4_EventScript_Duncan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_DUNCAN, SSAnne_B1F_Room4.DuncanIntro, SSAnne_B1F_Room4.DuncanDefeat))
        return
    ctx.say(SSAnne_B1F_Room4.DuncanPostBattle)
  }
}

internal object SSAnne_B1F_Room4_EventScript_Leonard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_LEONARD, SSAnne_B1F_Room4.LeonardIntro, SSAnne_B1F_Room4.LeonardDefeat))
        return
    ctx.say(SSAnne_B1F_Room4.LeonardPostBattle)
  }
}

internal val SSAnne_B1F_Room4Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room4_EventScript_Duncan" to SSAnne_B1F_Room4_EventScript_Duncan,
        "SSAnne_B1F_Room4_EventScript_Leonard" to SSAnne_B1F_Room4_EventScript_Leonard,
    )
