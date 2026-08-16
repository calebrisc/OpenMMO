package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_House1
import de.fiereu.openmmo.server.game.script.InGameTrades
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

internal object RustboroCity_House1_EventScript_Trader : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.inGameTrade(
          trade = InGameTrades.SEEDOT,
          flag = HoennFlags.FLAG_RUSTBORO_NPC_TRADE_COMPLETED,
          offer = RustboroCity_House1.IllTradeIfYouWant,
          decline = RustboroCity_House1.YouDontWantToThatsOkay,
          wrongMon = RustboroCity_House1.DoesntLookLikeMonToMe,
          thanks = RustboroCity_House1.PleaseBeGoodToMyPokemon,
          afterwards = RustboroCity_House1.AnyPokemonCanBeCute,
      )
}

internal object RustboroCity_House1_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity_House1.AllSortsOfPlaces)
}

internal val RustboroCity_House1Scripts: Map<String, Script> =
    mapOf(
        "RustboroCity_House1_EventScript_Trader" to RustboroCity_House1_EventScript_Trader,
        "RustboroCity_House1_EventScript_Hiker" to RustboroCity_House1_EventScript_Hiker,
    )
