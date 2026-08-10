package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_CaptainsOffice
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object SSAnne_CaptainsOffice_EventScript_Captain : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_HM01)) {
      return ctx.say(SSAnne_CaptainsOffice.HowToConquerSeasickness)
    }
    ctx.say(SSAnne_CaptainsOffice.CaptainIFeelSeasick)
    ctx.sign(SSAnne_CaptainsOffice.RubbedCaptainsBack)
    ctx.say(SSAnne_CaptainsOffice.ThankYouHaveHMForCut)
    if (!ctx.giveItem(Items.HM01)) {
      return ctx.say(SSAnne_CaptainsOffice.YouHaveNoRoomForThis)
    }
    ctx.sign(SSAnne_CaptainsOffice.ObtainedHM01FromCaptain)
    ctx.say(SSAnne_CaptainsOffice.ExplainCut)
    ctx.setFlag(KantoFlags.FLAG_GOT_HM01)
    ctx.setVar(KantoVars.VAR_MAP_SCENE_VERMILION_CITY, 1)
  }
}

internal object SSAnne_CaptainsOffice_EventScript_Book : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SSAnne_CaptainsOffice.HowToConquerSeasickness)
}

internal object SSAnne_CaptainsOffice_EventScript_TrashCan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SSAnne_CaptainsOffice.YuckShouldntHaveLooked)
}

internal val SSAnne_CaptainsOfficeScripts: Map<String, Script> =
    mapOf(
        "SSAnne_CaptainsOffice_EventScript_Captain" to SSAnne_CaptainsOffice_EventScript_Captain,
        "SSAnne_CaptainsOffice_EventScript_Book" to SSAnne_CaptainsOffice_EventScript_Book,
        "SSAnne_CaptainsOffice_EventScript_TrashCan" to SSAnne_CaptainsOffice_EventScript_TrashCan,
    )
