package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_Deck
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SAILOR_EDMOND = 134
private const val TRAINER_SAILOR_TREVOR = 135

internal object SSAnne_Deck_EventScript_Trevor : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_TREVOR, SSAnne_Deck.TrevorIntro, SSAnne_Deck.TrevorDefeat))
        return
    ctx.say(SSAnne_Deck.TrevorPostBattle)
  }
}

internal object SSAnne_Deck_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Deck.FeelSeasick)
}

internal object SSAnne_Deck_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Deck.ScrubbingDecksHardWork)
}

internal object SSAnne_Deck_EventScript_Edmond : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_EDMOND, SSAnne_Deck.EdmondIntro, SSAnne_Deck.EdmondDefeat))
        return
    ctx.say(SSAnne_Deck.EdmondPostBattle)
  }
}

internal object SSAnne_Deck_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Deck.ShipDepartingSoon)
}

internal val SSAnne_DeckScripts: Map<String, Script> =
    mapOf(
        "SSAnne_Deck_EventScript_Trevor" to SSAnne_Deck_EventScript_Trevor,
        "SSAnne_Deck_EventScript_BaldingMan" to SSAnne_Deck_EventScript_BaldingMan,
        "SSAnne_Deck_EventScript_Sailor" to SSAnne_Deck_EventScript_Sailor,
        "SSAnne_Deck_EventScript_Edmond" to SSAnne_Deck_EventScript_Edmond,
        "SSAnne_Deck_EventScript_Youngster" to SSAnne_Deck_EventScript_Youngster,
    )
