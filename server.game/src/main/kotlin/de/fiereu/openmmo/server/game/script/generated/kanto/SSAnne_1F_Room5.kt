package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_1F_Room5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GENTLEMAN_ARTHUR = 422

internal object SSAnne_1F_Room5_EventScript_Arthur : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GENTLEMAN_ARTHUR, SSAnne_1F_Room5.ArthurIntro, SSAnne_1F_Room5.ArthurDefeat))
        return
    ctx.say(SSAnne_1F_Room5.ArthurPostBattle)
  }
}

internal val SSAnne_1F_Room5Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room5_EventScript_Arthur" to SSAnne_1F_Room5_EventScript_Arthur,
    )
