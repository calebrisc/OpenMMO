package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity_House2
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object FuchsiaCity_House2_EventScript_FishingGurusBrother : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_GOOD_ROD)) {
      ctx.say(FuchsiaCity_House2.HowAreTheFishBiting)
      return
    }
    val said1 = ctx.askYesNo(FuchsiaCity_House2.DoYouLikeToFish)
    if (said1) {
      ctx.say(FuchsiaCity_House2.LikeYourStyleTakeThis)
      if (!ctx.giveItem(Items.GOOD_ROD)) {
        ctx.say(FuchsiaCity_House2.YouHaveNoRoomForGift)
        return
      }
      ctx.say(FuchsiaCity_House2.ReceivedGoodRod)
      ctx.say(FuchsiaCity_House2.GoodRodCanCatchBetterMons)
      ctx.setFlag(KantoFlags.FLAG_GOT_GOOD_ROD)
      return
    }
    ctx.say(FuchsiaCity_House2.OhThatsDisappointing)
  }
}

internal val FuchsiaCity_House2Scripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_House2_EventScript_FishingGurusBrother" to
            FuchsiaCity_House2_EventScript_FishingGurusBrother,
    )
