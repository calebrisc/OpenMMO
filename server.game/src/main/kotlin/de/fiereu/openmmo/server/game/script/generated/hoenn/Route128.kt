package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route128
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALEXA = 670
private const val TRAINER_CARLEE = 464
private const val TRAINER_HARRISON = 578
private const val TRAINER_RUBEN = 671
private const val TRAINER_WAYNE = 673

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ISAIAH_1, Route128_Text_IsaiahIntro, Route128_Text_IsaiahDefeat, Route128_EventScript_RegisterIsaiah
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route128_EventScript_RematchIsaiah
 * msgbox Route128_Text_IsaiahPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route128_EventScript_Isaiah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route128_EventScript_Isaiah")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KATELYN_1, Route128_Text_KatelynIntro, Route128_Text_KatelynDefeat, Route128_EventScript_RegisterKatelyn
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route128_EventScript_RematchKatelyn
 * msgbox Route128_Text_KatelynPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route128_EventScript_Katelyn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route128_EventScript_Katelyn")
}

internal object Route128_EventScript_Wayne : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_WAYNE, Route128.WayneIntro, Route128.WayneDefeat)) return
    ctx.say(Route128.WaynePostBattle)
  }
}

internal object Route128_EventScript_Ruben : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RUBEN, Route128.RubenIntro, Route128.RubenDefeat)) return
    ctx.say(Route128.RubenPostBattle)
  }
}

internal object Route128_EventScript_Alexa : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALEXA, Route128.AlexaIntro, Route128.AlexaDefeat)) return
    ctx.say(Route128.AlexaPostBattle)
  }
}

internal object Route128_EventScript_Carlee : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CARLEE, Route128.CarleeIntro, Route128.CarleeDefeat))
        return
    ctx.say(Route128.CarleePostBattle)
  }
}

internal object Route128_EventScript_Harrison : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HARRISON, Route128.HarrisonIntro, Route128.HarrisonDefeat))
        return
    ctx.say(Route128.HarrisonPostBattle)
  }
}

internal val Route128Scripts: Map<String, Script> =
    mapOf(
        "Route128_EventScript_Isaiah" to Route128_EventScript_Isaiah,
        "Route128_EventScript_Katelyn" to Route128_EventScript_Katelyn,
        "Route128_EventScript_Wayne" to Route128_EventScript_Wayne,
        "Route128_EventScript_Ruben" to Route128_EventScript_Ruben,
        "Route128_EventScript_Alexa" to Route128_EventScript_Alexa,
        "Route128_EventScript_Carlee" to Route128_EventScript_Carlee,
        "Route128_EventScript_Harrison" to Route128_EventScript_Harrison,
    )
