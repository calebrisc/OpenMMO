package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SSTidalLowerDeck
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_LEONARD = 495
private const val TRAINER_PHILLIP = 494

internal object SSTidalLowerDeck_EventScript_Phillip : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PHILLIP, SSTidalLowerDeck.PhillipIntro, SSTidalLowerDeck.PhillipDefeat))
        return
    ctx.say(SSTidalLowerDeck.PhillipPostBattle)
  }
}

internal object SSTidalLowerDeck_EventScript_Leonard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LEONARD, SSTidalLowerDeck.LeonardIntro, SSTidalLowerDeck.LeonardDefeat))
        return
    ctx.say(SSTidalLowerDeck.LeonardPostBattle)
  }
}

internal val SSTidalLowerDeckScripts: Map<String, Script> =
    mapOf(
        "SSTidalLowerDeck_EventScript_Phillip" to SSTidalLowerDeck_EventScript_Phillip,
        "SSTidalLowerDeck_EventScript_Leonard" to SSTidalLowerDeck_EventScript_Leonard,
    )
