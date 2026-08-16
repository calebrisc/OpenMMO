package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VermilionCity_House2
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object VermilionCity_House2_EventScript_Elyssa : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.FARFETCHD,
          flag = KantoFlags.FLAG_DID_CH_DING_TRADE,
          offer = VermilionCity_House2.DoYouHaveMonWantToTradeForMyMon,
          decline = VermilionCity_House2.ThatsTooBad,
          wrongMon = VermilionCity_House2.ThisIsNoMon,
          thanks = VermilionCity_House2.ThankYou,
          afterwards = VermilionCity_House2.HowIsMyOldMon,
      )
}

internal val VermilionCity_House2Scripts: Map<String, Script> =
    mapOf(
        "VermilionCity_House2_EventScript_Elyssa" to VermilionCity_House2_EventScript_Elyssa,
    )
