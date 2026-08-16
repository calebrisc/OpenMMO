package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Restaurant
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object CeladonCity_Restaurant_EventScript_Chef : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Restaurant.TakingBreakRightNow)
}

internal object CeladonCity_Restaurant_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Restaurant.OftenGoToDrugstore)
}

internal object CeladonCity_Restaurant_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Restaurant.ManLostItAllAtSlots)
}

internal object CeladonCity_Restaurant_EventScript_CoinCaseMan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_COIN_CASE)) {
      ctx.say(CeladonCity_Restaurant.ThoughtIdWinItBack)
      return
    }
    ctx.say(CeladonCity_Restaurant.TakeThisImBusted)
    if (!ctx.giveItem(Items.COIN_CASE)) {
      ctx.say(CeladonCity_Restaurant.MakeRoomForThis)
      return
    }
    ctx.say(CeladonCity_Restaurant.ReceivedCoinCaseFromMan)
    ctx.setFlag(KantoFlags.FLAG_GOT_COIN_CASE)
  }
}

internal object CeladonCity_Restaurant_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_Restaurant.PsstBasementUnderGameCorner)
}

internal val CeladonCity_RestaurantScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Restaurant_EventScript_Chef" to CeladonCity_Restaurant_EventScript_Chef,
        "CeladonCity_Restaurant_EventScript_Woman" to CeladonCity_Restaurant_EventScript_Woman,
        "CeladonCity_Restaurant_EventScript_FatMan" to CeladonCity_Restaurant_EventScript_FatMan,
        "CeladonCity_Restaurant_EventScript_CoinCaseMan" to
            CeladonCity_Restaurant_EventScript_CoinCaseMan,
        "CeladonCity_Restaurant_EventScript_WorkerM" to CeladonCity_Restaurant_EventScript_WorkerM,
    )
