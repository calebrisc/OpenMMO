package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route15_WestEntrance_2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.AideGift
import de.fiereu.openmmo.server.game.script.AideLines
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object Route15_WestEntrance_2F_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.oaksAide(
          AideGift(
              item = Items.EXP_SHARE,
              required = 50,
              countCaught = true,
              flag = KantoFlags.FLAG_GOT_EXP_SHARE_FROM_OAKS_AIDE,
          ),
          AideLines(
              offer = Route15_WestEntrance_2F.GiveItemIfCaughtEnough,
              greatHereYouGo = Route15_WestEntrance_2F.GreatHereYouGo,
              received = Route15_WestEntrance_2F.ReceivedItemFromAide,
              explain = Route15_WestEntrance_2F.ExplainExpShare,
          ),
      )
}

internal object Route15_WestEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(Route15_WestEntrance_2F.LargeShiningBird)
    ctx.setVar(KantoVars.VAR_0x8004, 144)
  }
}

internal object Route15_WestEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(Route15_WestEntrance_2F.SmallIslandOnHorizon)
}

internal val Route15_WestEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route15_WestEntrance_2F_EventScript_Aide" to Route15_WestEntrance_2F_EventScript_Aide,
        "Route15_WestEntrance_2F_EventScript_LeftBinoculars" to
            Route15_WestEntrance_2F_EventScript_LeftBinoculars,
        "Route15_WestEntrance_2F_EventScript_RightBinoculars" to
            Route15_WestEntrance_2F_EventScript_RightBinoculars,
    )
