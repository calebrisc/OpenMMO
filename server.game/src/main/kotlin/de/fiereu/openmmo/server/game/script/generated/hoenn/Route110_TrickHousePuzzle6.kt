package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_TrickHousePuzzle6
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BENNY = 407
private const val TRAINER_SEBASTIAN = 554
private const val TRAINER_SOPHIA = 561

internal object Route110_TrickHousePuzzle6_EventScript_Sophia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SOPHIA,
        Route110_TrickHousePuzzle6.SophiaIntro,
        Route110_TrickHousePuzzle6.SophiaDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle6.SophiaPostBattle)
  }
}

internal object Route110_TrickHousePuzzle6_EventScript_Benny : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BENNY,
        Route110_TrickHousePuzzle6.BennyIntro,
        Route110_TrickHousePuzzle6.BennyDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle6.BennyPostBattle)
  }
}

internal object Route110_TrickHousePuzzle6_EventScript_Sebastian : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SEBASTIAN,
        Route110_TrickHousePuzzle6.SebastianIntro,
        Route110_TrickHousePuzzle6.SebastianDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle6.SebastianPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GLITTER_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_6_STATE, 0, Route110_TrickHousePuzzle6_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle6_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle6_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle6Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle6_EventScript_Sophia" to
            Route110_TrickHousePuzzle6_EventScript_Sophia,
        "Route110_TrickHousePuzzle6_EventScript_Benny" to
            Route110_TrickHousePuzzle6_EventScript_Benny,
        "Route110_TrickHousePuzzle6_EventScript_Sebastian" to
            Route110_TrickHousePuzzle6_EventScript_Sebastian,
        "Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail" to
            Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail,
        "Route110_TrickHousePuzzle6_EventScript_Scroll" to
            Route110_TrickHousePuzzle6_EventScript_Scroll,
    )
