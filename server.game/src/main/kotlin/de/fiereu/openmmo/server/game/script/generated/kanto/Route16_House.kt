package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route16_House
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route16_House_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_HM02)) {
      ctx.say(Route16_House.ExplainHM02)
      return
    }
    ctx.say(Route16_House.FoundMySecretRetreat)
    if (!ctx.giveItem(Items.HM02)) {
      ctx.say(Route16_House.DontHaveAnyRoomForThis)
      return
    }
    ctx.say(Route16_House.ReceivedHM02FromGirl)
    ctx.say(Route16_House.ExplainHM02)
    ctx.setFlag(KantoFlags.FLAG_GOT_HM02)
  }
}

internal object Route16_House_EventScript_Fearow : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route16_House.Fearow)
}

internal val Route16_HouseScripts: Map<String, Script> =
    mapOf(
        "Route16_House_EventScript_Woman" to Route16_House_EventScript_Woman,
        "Route16_House_EventScript_Fearow" to Route16_House_EventScript_Fearow,
    )
