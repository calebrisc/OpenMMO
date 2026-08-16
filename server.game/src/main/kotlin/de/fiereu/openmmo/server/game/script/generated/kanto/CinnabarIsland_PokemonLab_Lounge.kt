package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CinnabarIsland_PokemonLab_Lounge
import de.fiereu.openmmo.dialog.generated.kanto.Trade
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object CinnabarIsland_PokemonLab_Lounge_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland_PokemonLab_Lounge.FoundFossilInMtMoon)
}

internal object CinnabarIsland_PokemonLab_Lounge_EventScript_Clifton : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.ELECTRODE,
          flag = KantoFlags.FLAG_DID_ESPHERE_TRADE,
          offer = Trade.DoYouHaveMonWouldYouTradeForMon,
          decline = Trade.WellIfYouDontWantTo,
          wrongMon = Trade.ThisIsntMon,
          thanks = Trade.Thanks,
          afterwards = Trade.HasTradedMonGrownStronger,
      )
}

internal object CinnabarIsland_PokemonLab_Lounge_EventScript_Norma : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.TANGELA,
          flag = KantoFlags.FLAG_DID_TANGENY_TRADE,
          offer = Trade.DoYouHaveMonWantToTradeForMon,
          decline = Trade.ThatsTooBad,
          wrongMon = Trade.ThisIsNoMon,
          thanks = Trade.ThanksYoureAPal,
          afterwards = Trade.HowIsMyOldMon,
      )
}

internal val CinnabarIsland_PokemonLab_LoungeScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_PokemonLab_Lounge_EventScript_Scientist" to
            CinnabarIsland_PokemonLab_Lounge_EventScript_Scientist,
        "CinnabarIsland_PokemonLab_Lounge_EventScript_Clifton" to
            CinnabarIsland_PokemonLab_Lounge_EventScript_Clifton,
        "CinnabarIsland_PokemonLab_Lounge_EventScript_Norma" to
            CinnabarIsland_PokemonLab_Lounge_EventScript_Norma,
    )
