package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route2_EastBuilding
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.AideGift
import de.fiereu.openmmo.server.game.script.AideLines
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route2_EastBuilding_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.oaksAide(
          AideGift(
              item = Items.HM05,
              required = 10,
              countCaught = false,
              flag = KantoFlags.FLAG_GOT_HM05,
          ),
          AideLines(
              offer = Route2_EastBuilding.GiveHM05IfSeen10Mons,
              greatHereYouGo = Route2_EastBuilding.GreatHereYouGo,
              received = Route2_EastBuilding.ReceivedHM05FromAide,
              explain = Route2_EastBuilding.ExplainHM05,
          ),
      )
}

internal object Route2_EastBuilding_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route2_EastBuilding.CanGetThroughRockTunnel)
}

internal val Route2_EastBuildingScripts: Map<String, Script> =
    mapOf(
        "Route2_EastBuilding_EventScript_Aide" to Route2_EastBuilding_EventScript_Aide,
        "Route2_EastBuilding_EventScript_Rocker" to Route2_EastBuilding_EventScript_Rocker,
    )
