package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route23
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route23_EventScript_CascadeBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_BADGE02_GET)) {
      ctx.send(notice("The guard blocks the way: only trainers with the CASCADE BADGE may pass."))
      return
    }
    ctx.send(notice("The guard verified your CASCADE BADGE and stands aside."))
    ctx.despawnInteracted()
  }
}

internal object Route23_EventScript_ThunderBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_BADGE03_GET)) {
      ctx.send(notice("The guard blocks the way: only trainers with the THUNDER BADGE may pass."))
      return
    }
    ctx.send(notice("The guard verified your THUNDER BADGE and stands aside."))
    ctx.despawnInteracted()
  }
}

internal object Route23_EventScript_RainbowBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_BADGE04_GET)) {
      ctx.send(notice("The guard blocks the way: only trainers with the RAINBOW BADGE may pass."))
      return
    }
    ctx.send(notice("The guard verified your RAINBOW BADGE and stands aside."))
    ctx.despawnInteracted()
  }
}

internal object Route23_EventScript_SoulBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_BADGE05_GET)) {
      ctx.send(notice("The guard blocks the way: only trainers with the SOUL BADGE may pass."))
      return
    }
    ctx.send(notice("The guard verified your SOUL BADGE and stands aside."))
    ctx.despawnInteracted()
  }
}

internal object Route23_EventScript_MarshBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_BADGE06_GET)) {
      ctx.send(notice("The guard blocks the way: only trainers with the MARSH BADGE may pass."))
      return
    }
    ctx.send(notice("The guard verified your MARSH BADGE and stands aside."))
    ctx.despawnInteracted()
  }
}

internal object Route23_EventScript_VolcanoBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_BADGE07_GET)) {
      ctx.send(notice("The guard blocks the way: only trainers with the VOLCANO BADGE may pass."))
      return
    }
    ctx.send(notice("The guard verified your VOLCANO BADGE and stands aside."))
    ctx.despawnInteracted()
  }
}

internal object Route23_EventScript_EarthBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_BADGE08_GET)) {
      ctx.send(notice("The guard blocks the way: only trainers with the EARTH BADGE may pass."))
      return
    }
    ctx.send(notice("The guard verified your EARTH BADGE and stands aside."))
    ctx.despawnInteracted()
  }
}

internal object Route23_EventScript_VictoryRoadGateSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route23.VictoryRoadGateSign)
}

internal val Route23Scripts: Map<String, Script> =
    mapOf(
        "Route23_EventScript_CascadeBadgeGuard" to Route23_EventScript_CascadeBadgeGuard,
        "Route23_EventScript_ThunderBadgeGuard" to Route23_EventScript_ThunderBadgeGuard,
        "Route23_EventScript_RainbowBadgeGuard" to Route23_EventScript_RainbowBadgeGuard,
        "Route23_EventScript_SoulBadgeGuard" to Route23_EventScript_SoulBadgeGuard,
        "Route23_EventScript_MarshBadgeGuard" to Route23_EventScript_MarshBadgeGuard,
        "Route23_EventScript_VolcanoBadgeGuard" to Route23_EventScript_VolcanoBadgeGuard,
        "Route23_EventScript_EarthBadgeGuard" to Route23_EventScript_EarthBadgeGuard,
        "Route23_EventScript_VictoryRoadGateSign" to Route23_EventScript_VictoryRoadGateSign,
    )
