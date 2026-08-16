package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Trade
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object UndergroundPath_NorthEntrance_EventScript_Saige : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.NIDORAN,
          flag = KantoFlags.FLAG_DID_MS_NIDO_TRADE,
          offer = Trade.DoYouHaveMonWantToTradeForMon,
          decline = Trade.ThatsTooBad,
          wrongMon = Trade.ThisIsNoMon,
          thanks = Trade.ThanksYoureAPal,
          afterwards = Trade.HowIsMyOldMon,
      )
}

internal val UndergroundPath_NorthEntranceScripts: Map<String, Script> =
    mapOf(
        "UndergroundPath_NorthEntrance_EventScript_Saige" to
            UndergroundPath_NorthEntrance_EventScript_Saige,
    )
