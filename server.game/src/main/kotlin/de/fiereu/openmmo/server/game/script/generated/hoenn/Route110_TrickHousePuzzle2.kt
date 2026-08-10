package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_TrickHousePuzzle2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GEORGIA = 281
private const val TRAINER_PAUL = 275
private const val TRAINER_TED = 274

internal object Route110_TrickHousePuzzle2_EventScript_Ted : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TED, Route110_TrickHousePuzzle2.TedIntro, Route110_TrickHousePuzzle2.TedDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle2.TedPostBattle)
  }
}

internal object Route110_TrickHousePuzzle2_EventScript_Paul : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PAUL, Route110_TrickHousePuzzle2.PaulIntro, Route110_TrickHousePuzzle2.PaulDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle2.PaulPostBattle)
  }
}

internal object Route110_TrickHousePuzzle2_EventScript_Georgia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GEORGIA,
        Route110_TrickHousePuzzle2.GeorgiaIntro,
        Route110_TrickHousePuzzle2.GeorgiaDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle2.GeorgiaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_WAVE_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_ItemWaveMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_ItemWaveMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HARBOR_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_ItemHarborMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_ItemHarborMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_2_STATE, 0, Route110_TrickHousePuzzle2_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle2Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle2_EventScript_Ted" to Route110_TrickHousePuzzle2_EventScript_Ted,
        "Route110_TrickHousePuzzle2_EventScript_Paul" to
            Route110_TrickHousePuzzle2_EventScript_Paul,
        "Route110_TrickHousePuzzle2_EventScript_Georgia" to
            Route110_TrickHousePuzzle2_EventScript_Georgia,
        "Route110_TrickHousePuzzle2_EventScript_ItemWaveMail" to
            Route110_TrickHousePuzzle2_EventScript_ItemWaveMail,
        "Route110_TrickHousePuzzle2_EventScript_ItemHarborMail" to
            Route110_TrickHousePuzzle2_EventScript_ItemHarborMail,
        "Route110_TrickHousePuzzle2_EventScript_Scroll" to
            Route110_TrickHousePuzzle2_EventScript_Scroll,
    )
