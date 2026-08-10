package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_1F_Room7
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GENTLEMAN_THOMAS = 421

internal object SSAnne_1F_Room7_EventScript_Thomas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GENTLEMAN_THOMAS, SSAnne_1F_Room7.ThomasIntro, SSAnne_1F_Room7.ThomasDefeat))
        return
    ctx.say(SSAnne_1F_Room7.ThomasPostBattle)
  }
}

internal val SSAnne_1F_Room7Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room7_EventScript_Thomas" to SSAnne_1F_Room7_EventScript_Thomas,
    )
