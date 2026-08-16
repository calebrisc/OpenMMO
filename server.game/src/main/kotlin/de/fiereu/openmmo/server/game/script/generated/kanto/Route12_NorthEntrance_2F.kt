package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route12_NorthEntrance_2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route12_NorthEntrance_2F_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_TM27)) {
      ctx.say(Route12_NorthEntrance_2F.ExplainTM27)
      return
    }
    val gender1 = if (ctx.isFemale) 1 else 0
    if (gender1 == 0) {
      ctx.say(Route12_NorthEntrance_2F.TakeTMDontNeedAnymoreMale)
    }
    if (gender1 == 1) {
      ctx.say(Route12_NorthEntrance_2F.TakeTMDontNeedAnymoreFemale)
    }
    if (!ctx.giveItem(Items.TM27)) {
      ctx.say(Route12_NorthEntrance_2F.DontHaveRoomForThis)
      return
    }
    ctx.say(Route12_NorthEntrance_2F.ReceivedTM27FromLittleGirl)
    ctx.say(Route12_NorthEntrance_2F.ExplainTM27)
    ctx.setFlag(KantoFlags.FLAG_GOT_TM27)
  }
}

internal object Route12_NorthEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route12_NorthEntrance_2F.TheresManFishing)
}

internal object Route12_NorthEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route12_NorthEntrance_2F.ItsPokemonTower)
}

internal val Route12_NorthEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route12_NorthEntrance_2F_EventScript_Lass" to Route12_NorthEntrance_2F_EventScript_Lass,
        "Route12_NorthEntrance_2F_EventScript_LeftBinoculars" to
            Route12_NorthEntrance_2F_EventScript_LeftBinoculars,
        "Route12_NorthEntrance_2F_EventScript_RightBinoculars" to
            Route12_NorthEntrance_2F_EventScript_RightBinoculars,
    )
