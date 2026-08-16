package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route11_EastEntrance_2F
import de.fiereu.openmmo.dialog.generated.kanto.Trade
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.AideGift
import de.fiereu.openmmo.server.game.script.AideLines
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route11_EastEntrance_2F_EventScript_Turner : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.NIDORINOA,
          flag = KantoFlags.FLAG_DID_NINA_TRADE,
          offer = Trade.LookingForMonWannaTradeForMon,
          decline = Trade.AwwOhWell,
          wrongMon = Trade.WhatThatsNoMon,
          thanks = Trade.HeyThanks,
          afterwards = Trade.IsntMyOldMonGreat,
      )
}

internal object Route11_EastEntrance_2F_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.oaksAide(
          AideGift(
              item = Items.DOWSING_MCHN,
              required = 30,
              countCaught = true,
              flag = KantoFlags.FLAG_GOT_ITEMFINDER,
          ),
          AideLines(
              offer = Route11_EastEntrance_2F.GiveItemfinderIfCaught30,
              greatHereYouGo = Route11_EastEntrance_2F.GreatHereYouGo,
              received = Route11_EastEntrance_2F.ReceivedItemfinderFromAide,
              explain = Route11_EastEntrance_2F.ExplainItemfinder,
          ),
      )
}

internal object Route11_EastEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_WOKE_UP_ROUTE_12_SNORLAX)) {
      ctx.say(Route11_EastEntrance_2F.WhatABreathtakingView)
      return
    }
    ctx.say(Route11_EastEntrance_2F.BigMonAsleepOnRoad)
  }
}

internal object Route11_EastEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(Route11_EastEntrance_2F.RockTunnelGoodRouteToLavender)
}

internal val Route11_EastEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route11_EastEntrance_2F_EventScript_Turner" to Route11_EastEntrance_2F_EventScript_Turner,
        "Route11_EastEntrance_2F_EventScript_Aide" to Route11_EastEntrance_2F_EventScript_Aide,
        "Route11_EastEntrance_2F_EventScript_LeftBinoculars" to
            Route11_EastEntrance_2F_EventScript_LeftBinoculars,
        "Route11_EastEntrance_2F_EventScript_RightBinoculars" to
            Route11_EastEntrance_2F_EventScript_RightBinoculars,
    )
