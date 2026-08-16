package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.OneIsland
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object OneIsland_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_SYS_CAN_LINK_WITH_RS)) {
      ctx.say(OneIsland.HavePCLinkageWithHoenn)
      return
    }
    if (ctx.isFlagSet(KantoFlags.FLAG_SEVII_DETOUR_FINISHED)) {
      ctx.say(OneIsland.HavePCLinkageWithKanto)
      return
    }
    ctx.say(OneIsland.LuckyToHaveCelioHere)
  }
}

internal object OneIsland_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(OneIsland.IsntWarmClimateHereGreat)
}

internal object OneIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(OneIsland.IslandSign)
}

internal object OneIsland_EventScript_PokemonNetCenterSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(OneIsland.PokemonNetCenterSign)
}

internal val OneIslandScripts: Map<String, Script> =
    mapOf(
        "OneIsland_EventScript_OldMan" to OneIsland_EventScript_OldMan,
        "OneIsland_EventScript_BaldingMan" to OneIsland_EventScript_BaldingMan,
        "OneIsland_EventScript_IslandSign" to OneIsland_EventScript_IslandSign,
        "OneIsland_EventScript_PokemonNetCenterSign" to OneIsland_EventScript_PokemonNetCenterSign,
    )
