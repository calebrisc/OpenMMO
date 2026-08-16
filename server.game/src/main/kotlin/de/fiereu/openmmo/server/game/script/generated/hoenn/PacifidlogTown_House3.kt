package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PacifidlogTown_House3
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

internal object PacifidlogTown_House3_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PacifidlogTown_House3.IsThatAPokedex)
}

internal object PacifidlogTown_House3_EventScript_Trader : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.HORSEA,
          flag = HoennFlags.FLAG_PACIFIDLOG_NPC_TRADE_COMPLETED,
          offer = PacifidlogTown_House3.WillingToTradeIt,
          decline = PacifidlogTown_House3.NotDesperateOrAnything,
          wrongMon = PacifidlogTown_House3.WontAcceptAnyLessThanRealMon,
          thanks = PacifidlogTown_House3.ItsSubtlyDifferentThankYou,
          afterwards = PacifidlogTown_House3.ReallyWantedToGetBagon,
      )
}

internal val PacifidlogTown_House3Scripts: Map<String, Script> =
    mapOf(
        "PacifidlogTown_House3_EventScript_Girl" to PacifidlogTown_House3_EventScript_Girl,
        "PacifidlogTown_House3_EventScript_Trader" to PacifidlogTown_House3_EventScript_Trader,
    )
