package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_TrickHousePuzzle1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_EDDIE = 332
private const val TRAINER_ROBIN = 612
private const val TRAINER_SALLY = 611

internal object Route110_TrickHousePuzzle1_EventScript_Sally : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SALLY,
        Route110_TrickHousePuzzle1.SallyIntro,
        Route110_TrickHousePuzzle1.SallyDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle1.SallyPostBattle)
  }
}

internal object Route110_TrickHousePuzzle1_EventScript_Eddie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_EDDIE,
        Route110_TrickHousePuzzle1.EddieIntro,
        Route110_TrickHousePuzzle1.EddieDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle1.EddiePostBattle)
  }
}

internal object Route110_TrickHousePuzzle1_EventScript_Robin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ROBIN,
        Route110_TrickHousePuzzle1.RobinIntro,
        Route110_TrickHousePuzzle1.RobinDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle1.RobinPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ORANGE_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_1_STATE, 0, Route110_TrickHousePuzzle1_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle1_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle1_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle1Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle1_EventScript_Sally" to
            Route110_TrickHousePuzzle1_EventScript_Sally,
        "Route110_TrickHousePuzzle1_EventScript_Eddie" to
            Route110_TrickHousePuzzle1_EventScript_Eddie,
        "Route110_TrickHousePuzzle1_EventScript_Robin" to
            Route110_TrickHousePuzzle1_EventScript_Robin,
        "Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail" to
            Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail,
        "Route110_TrickHousePuzzle1_EventScript_Scroll" to
            Route110_TrickHousePuzzle1_EventScript_Scroll,
    )
