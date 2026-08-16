package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.OneIsland_KindleRoad_EmberSpa
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object OneIsland_KindleRoad_EmberSpa_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.WaterWarmsMeToCore)
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.EnjoyBowlOfChowder)
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.BrunoVisitsSpaOnOccasion)
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.SeeHowSmoothMySkinIs)
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_RockSmashMan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_HM06)) {
      ctx.say(OneIsland_KindleRoad_EmberSpa.ExplainHM06)
      return
    }
    ctx.say(OneIsland_KindleRoad_EmberSpa.UsedThisToMakeEmberSpa)
    ctx.giveItem(Items.HM06)
    ctx.setFlag(KantoFlags.FLAG_GOT_HM06)
    ctx.say(OneIsland_KindleRoad_EmberSpa.ExplainHM06)
  }
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.HotSpringIsTherapeutic)
}

internal val OneIsland_KindleRoad_EmberSpaScripts: Map<String, Script> =
    mapOf(
        "OneIsland_KindleRoad_EmberSpa_EventScript_OldMan" to
            OneIsland_KindleRoad_EmberSpa_EventScript_OldMan,
        "OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan1" to
            OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan1,
        "OneIsland_KindleRoad_EmberSpa_EventScript_BlackBelt" to
            OneIsland_KindleRoad_EmberSpa_EventScript_BlackBelt,
        "OneIsland_KindleRoad_EmberSpa_EventScript_OldWoman" to
            OneIsland_KindleRoad_EmberSpa_EventScript_OldWoman,
        "OneIsland_KindleRoad_EmberSpa_EventScript_RockSmashMan" to
            OneIsland_KindleRoad_EmberSpa_EventScript_RockSmashMan,
        "OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan2" to
            OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan2,
    )
