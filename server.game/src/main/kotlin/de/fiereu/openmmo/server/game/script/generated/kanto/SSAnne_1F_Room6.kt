package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_1F_Room6
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_1F_Room6_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) {
    val said1 = ctx.askYesNo(SSAnne_1F_Room6.TakeAShortRest)
    if (!said1) {
      val gender2 = if (ctx.isFemale) 1 else 0
      if (gender2 == 0) {
        ctx.say(SSAnne_1F_Room6.SorryYouLookLikeMyBrother)
        return
      }
      ctx.say(SSAnne_1F_Room6.SorryYouLookLikeMySister)
      return
    }
    ctx.healParty()
    ctx.say(SSAnne_1F_Room6.GladEveryoneIsRefreshed)
  }
}

internal val SSAnne_1F_Room6Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room6_EventScript_Woman" to SSAnne_1F_Room6_EventScript_Woman,
    )
