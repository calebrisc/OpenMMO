package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route2_House
import de.fiereu.openmmo.dialog.generated.kanto.Trade
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route2_House_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route2_House.FaintedMonsCanUseFieldMoves)
}

internal object Route2_House_EventScript_Reyley : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.MR_MIME,
          flag = KantoFlags.FLAG_DID_MIMIEN_TRADE,
          offer = Trade.LookingForMonWannaTradeForMon,
          decline = Trade.AwwOhWell,
          wrongMon = Trade.WhatThatsNoMon,
          thanks = Trade.HeyThanks,
          afterwards = Trade.IsntMyOldMonGreat,
      )
}

internal val Route2_HouseScripts: Map<String, Script> =
    mapOf(
        "Route2_House_EventScript_Scientist" to Route2_House_EventScript_Scientist,
        "Route2_House_EventScript_Reyley" to Route2_House_EventScript_Reyley,
    )
