package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_TrickHousePuzzle4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CORA = 428
private const val TRAINER_PAULA = 429
private const val TRAINER_YUJI = 188

internal object Route110_TrickHousePuzzle4_EventScript_Cora : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CORA, Route110_TrickHousePuzzle4.CoraIntro, Route110_TrickHousePuzzle4.CoraDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle4.CoraPostBattle)
  }
}

internal object Route110_TrickHousePuzzle4_EventScript_Paula : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PAULA,
        Route110_TrickHousePuzzle4.PaulaIntro,
        Route110_TrickHousePuzzle4.PaulaDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle4.PaulaPostBattle)
  }
}

internal object Route110_TrickHousePuzzle4_EventScript_Yuji : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YUJI, Route110_TrickHousePuzzle4.YujiIntro, Route110_TrickHousePuzzle4.YujiDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle4.YujiPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MECH_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle4_EventScript_ItemMechMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle4_EventScript_ItemMechMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_4_STATE, 0, Route110_TrickHousePuzzle4_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle4_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle4_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle4Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle4_EventScript_Cora" to
            Route110_TrickHousePuzzle4_EventScript_Cora,
        "Route110_TrickHousePuzzle4_EventScript_Paula" to
            Route110_TrickHousePuzzle4_EventScript_Paula,
        "Route110_TrickHousePuzzle4_EventScript_Yuji" to
            Route110_TrickHousePuzzle4_EventScript_Yuji,
        "Route110_TrickHousePuzzle4_EventScript_ItemMechMail" to
            Route110_TrickHousePuzzle4_EventScript_ItemMechMail,
        "Route110_TrickHousePuzzle4_EventScript_Scroll" to
            Route110_TrickHousePuzzle4_EventScript_Scroll,
    )
