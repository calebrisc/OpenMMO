package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_House3
import de.fiereu.openmmo.dialog.generated.kanto.Trade
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object CeruleanCity_House3_EventScript_Dontae : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.JYNX,
          flag = KantoFlags.FLAG_DID_ZYNX_TRADE,
          offer = Trade.DoYouHaveMonWouldYouTradeForMon,
          decline = Trade.WellIfYouDontWantTo,
          wrongMon = Trade.ThisIsntMon,
          thanks = Trade.Thanks,
          afterwards = Trade.HasTradedMonGrownStronger,
      )
}

internal object CeruleanCity_House3_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_House3.PleaseTradeWithMyHusband)
}

internal val CeruleanCity_House3Scripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_House3_EventScript_Dontae" to CeruleanCity_House3_EventScript_Dontae,
        "CeruleanCity_House3_EventScript_OldWoman" to CeruleanCity_House3_EventScript_OldWoman,
    )
