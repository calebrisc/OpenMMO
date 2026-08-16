package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_House1
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

internal object FortreeCity_House1_EventScript_Trader : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.PLUSLE,
          flag = HoennFlags.FLAG_FORTREE_NPC_TRADE_COMPLETED,
          offer = FortreeCity_House1.YouWillTradeWontYou,
          decline = FortreeCity_House1.YouWontTradeMe,
          wrongMon = FortreeCity_House1.ThisIsntAMon,
          thanks = FortreeCity_House1.MonYouTakeCare,
          afterwards = FortreeCity_House1.GoingToMakeVolbeatStrong,
      )
}

internal object FortreeCity_House1_EventScript_Zigzagoon : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity_House1.Zigzagoon)
}

internal object FortreeCity_House1_EventScript_ExpertF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_House1.TradingMemoriesWithOthers)
}

internal val FortreeCity_House1Scripts: Map<String, Script> =
    mapOf(
        "FortreeCity_House1_EventScript_Trader" to FortreeCity_House1_EventScript_Trader,
        "FortreeCity_House1_EventScript_Zigzagoon" to FortreeCity_House1_EventScript_Zigzagoon,
        "FortreeCity_House1_EventScript_ExpertF" to FortreeCity_House1_EventScript_ExpertF,
    )
