package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route18_EastEntrance_2F
import de.fiereu.openmmo.dialog.generated.kanto.Trade
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route18_EastEntrance_2F_EventScript_Haden : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.LICKITUNG,
          flag = KantoFlags.FLAG_DID_MARC_TRADE,
          offer = Trade.LookingForMonWannaTradeForMon,
          decline = Trade.AwwOhWell,
          wrongMon = Trade.WhatThatsNoMon,
          thanks = Trade.HeyThanks,
          afterwards = Trade.IsntMyOldMonGreat,
      )
}

internal object Route18_EastEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route18_EastEntrance_2F.PalletTownInWest)
}

internal object Route18_EastEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route18_EastEntrance_2F.PeopleSwimming)
}

internal val Route18_EastEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route18_EastEntrance_2F_EventScript_Haden" to Route18_EastEntrance_2F_EventScript_Haden,
        "Route18_EastEntrance_2F_EventScript_LeftBinoculars" to
            Route18_EastEntrance_2F_EventScript_LeftBinoculars,
        "Route18_EastEntrance_2F_EventScript_RightBinoculars" to
            Route18_EastEntrance_2F_EventScript_RightBinoculars,
    )
