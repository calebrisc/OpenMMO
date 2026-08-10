package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_Corridors_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CHARLIE = 66

internal object AbandonedShip_Corridors_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(AbandonedShip_Corridors_1F.IsntItFunHere)
}

internal object AbandonedShip_Corridors_1F_EventScript_Charlie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHARLIE,
        AbandonedShip_Corridors_1F.CharlieIntro,
        AbandonedShip_Corridors_1F.CharlieDefeat))
        return
    ctx.say(AbandonedShip_Corridors_1F.CharliePostBattle)
  }
}

internal val AbandonedShip_Corridors_1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Corridors_1F_EventScript_Youngster" to
            AbandonedShip_Corridors_1F_EventScript_Youngster,
        "AbandonedShip_Corridors_1F_EventScript_Charlie" to
            AbandonedShip_Corridors_1F_EventScript_Charlie,
    )
