package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SixIsland_GreenPath
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_PSYCHIC_JACLYN = 517

internal object SixIsland_GreenPath_EventScript_Jaclyn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PSYCHIC_JACLYN, SixIsland_GreenPath.JaclynIntro, SixIsland_GreenPath.JaclynDefeat))
        return
    ctx.say(SixIsland_GreenPath.JaclynPostBattle)
  }
}

internal object SixIsland_GreenPath_EventScript_LeftRouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SixIsland_GreenPath.RightRouteSign)
}

internal object SixIsland_GreenPath_EventScript_RightRouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SixIsland_GreenPath.LeftRouteSign)
}

internal val SixIsland_GreenPathScripts: Map<String, Script> =
    mapOf(
        "SixIsland_GreenPath_EventScript_Jaclyn" to SixIsland_GreenPath_EventScript_Jaclyn,
        "SixIsland_GreenPath_EventScript_LeftRouteSign" to
            SixIsland_GreenPath_EventScript_LeftRouteSign,
        "SixIsland_GreenPath_EventScript_RightRouteSign" to
            SixIsland_GreenPath_EventScript_RightRouteSign,
    )
