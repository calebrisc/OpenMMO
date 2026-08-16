package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route11_EastEntrance_2F
import de.fiereu.openmmo.dialog.generated.kanto.Trade
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

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Route11_EastEntrance_2F_EventScript_GetAideRequestInfo
 * goto_if_set FLAG_GOT_ITEMFINDER, Route11_EastEntrance_2F_EventScript_AlreadyGotItemfinder
 * msgbox Route11_EastEntrance_2F_Text_GiveItemfinderIfCaught30, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Aide_EventScript_DeclineCheckMons
 * setvar VAR_0x8004, 0
 * specialvar VAR_RESULT, GetPokedexCount
 * buffernumberstring STR_VAR_3, VAR_0x8006
 * call Route11_EastEntrance_2F_EventScript_GetAideRequestInfo
 * goto_if_lt VAR_0x8006, REQUIRED_CAUGHT_MONS, Aide_EventScript_HaventCaughtEnough
 * msgbox Route11_EastEntrance_2F_Text_GreatHereYouGo
 * checkitemspace ITEM_ITEMFINDER
 * goto_if_eq VAR_RESULT, FALSE, Aide_EventScript_NoRoomForItem
 * giveitem_msg Route11_EastEntrance_2F_Text_ReceivedItemfinderFromAide, ITEM_ITEMFINDER
 * setflag FLAG_GOT_ITEMFINDER
 * msgbox Route11_EastEntrance_2F_Text_ExplainItemfinder
 * release
 * end
 * ```
 */
internal object Route11_EastEntrance_2F_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route11_EastEntrance_2F_EventScript_Aide")
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
