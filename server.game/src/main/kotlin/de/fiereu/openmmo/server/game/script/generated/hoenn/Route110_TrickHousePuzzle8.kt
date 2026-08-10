package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_TrickHousePuzzle8
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_KEIRA = 93
private const val TRAINER_LEROY = 77
private const val TRAINER_VINCENT = 76

internal object Route110_TrickHousePuzzle8_EventScript_Vincent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_VINCENT,
        Route110_TrickHousePuzzle8.VincentIntro,
        Route110_TrickHousePuzzle8.VincentDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle8.VincentPostBattle)
  }
}

internal object Route110_TrickHousePuzzle8_EventScript_Leroy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LEROY,
        Route110_TrickHousePuzzle8.LeroyIntro,
        Route110_TrickHousePuzzle8.LeroyDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle8.LeroyPostBattle)
  }
}

internal object Route110_TrickHousePuzzle8_EventScript_Keira : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_KEIRA,
        Route110_TrickHousePuzzle8.KeiraIntro,
        Route110_TrickHousePuzzle8.KeiraDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle8.KeiraPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BEAD_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle8_EventScript_ItemBeadMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle8_EventScript_ItemBeadMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_8_STATE, 0, Route110_TrickHousePuzzle8_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle8_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle8_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle8Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle8_EventScript_Vincent" to
            Route110_TrickHousePuzzle8_EventScript_Vincent,
        "Route110_TrickHousePuzzle8_EventScript_Leroy" to
            Route110_TrickHousePuzzle8_EventScript_Leroy,
        "Route110_TrickHousePuzzle8_EventScript_Keira" to
            Route110_TrickHousePuzzle8_EventScript_Keira,
        "Route110_TrickHousePuzzle8_EventScript_ItemBeadMail" to
            Route110_TrickHousePuzzle8_EventScript_ItemBeadMail,
        "Route110_TrickHousePuzzle8_EventScript_Scroll" to
            Route110_TrickHousePuzzle8_EventScript_Scroll,
    )
