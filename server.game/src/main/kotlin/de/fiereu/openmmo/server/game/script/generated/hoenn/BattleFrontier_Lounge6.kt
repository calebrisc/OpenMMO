package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Lounge6
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

internal object BattleFrontier_Lounge6_EventScript_Trader : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.MEOWTH,
          flag = HoennFlags.FLAG_BATTLE_FRONTIER_TRADE_DONE,
          offer = BattleFrontier_Lounge6.WouldYouLikeToTrade,
          decline = BattleFrontier_Lounge6.WellThatsFineToo,
          wrongMon = BattleFrontier_Lounge6.DontTradeForAnythingButMon,
          thanks = BattleFrontier_Lounge6.PromiseIllBeGoodToIt,
          afterwards = BattleFrontier_Lounge6.SkittySoMuchCuterThanImagined,
      )
}

internal val BattleFrontier_Lounge6Scripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Lounge6_EventScript_Trader" to BattleFrontier_Lounge6_EventScript_Trader,
    )
