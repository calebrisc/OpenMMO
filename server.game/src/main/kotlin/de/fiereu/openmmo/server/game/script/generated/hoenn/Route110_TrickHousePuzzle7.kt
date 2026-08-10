package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_TrickHousePuzzle7
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALEXIS = 248
private const val TRAINER_ALVARO = 849
private const val TRAINER_EVERETT = 850
private const val TRAINER_JOSHUA = 237
private const val TRAINER_MARIELA = 848
private const val TRAINER_PATRICIA = 105

internal object Route110_TrickHousePuzzle7_EventScript_Joshua : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JOSHUA,
        Route110_TrickHousePuzzle7.JoshuaIntro,
        Route110_TrickHousePuzzle7.JoshuaDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle7.JoshuaPostBattle)
  }
}

internal object Route110_TrickHousePuzzle7_EventScript_Alexis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ALEXIS,
        Route110_TrickHousePuzzle7.AlexisIntro,
        Route110_TrickHousePuzzle7.AlexisDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle7.AlexisPostBattle)
  }
}

internal object Route110_TrickHousePuzzle7_EventScript_Patricia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PATRICIA,
        Route110_TrickHousePuzzle7.PatriciaIntro,
        Route110_TrickHousePuzzle7.PatriciaDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle7.PatriciaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TROPIC_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_ItemTropicMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_ItemTropicMail")
}

internal object Route110_TrickHousePuzzle7_EventScript_Alvaro : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ALVARO,
        Route110_TrickHousePuzzle7.AlvaroIntro,
        Route110_TrickHousePuzzle7.AlvaroDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle7.AlvaroPostBattle)
  }
}

internal object Route110_TrickHousePuzzle7_EventScript_Mariela : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_MARIELA,
        Route110_TrickHousePuzzle7.MarielaIntro,
        Route110_TrickHousePuzzle7.MarielaDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle7.MarielaPostBattle)
  }
}

internal object Route110_TrickHousePuzzle7_EventScript_Everett : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_EVERETT,
        Route110_TrickHousePuzzle7.EverettIntro,
        Route110_TrickHousePuzzle7.EverettDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle7.EverettPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_7_STATE, 0, Route110_TrickHousePuzzle7_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle7Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle7_EventScript_Joshua" to
            Route110_TrickHousePuzzle7_EventScript_Joshua,
        "Route110_TrickHousePuzzle7_EventScript_Alexis" to
            Route110_TrickHousePuzzle7_EventScript_Alexis,
        "Route110_TrickHousePuzzle7_EventScript_Patricia" to
            Route110_TrickHousePuzzle7_EventScript_Patricia,
        "Route110_TrickHousePuzzle7_EventScript_ItemTropicMail" to
            Route110_TrickHousePuzzle7_EventScript_ItemTropicMail,
        "Route110_TrickHousePuzzle7_EventScript_Alvaro" to
            Route110_TrickHousePuzzle7_EventScript_Alvaro,
        "Route110_TrickHousePuzzle7_EventScript_Mariela" to
            Route110_TrickHousePuzzle7_EventScript_Mariela,
        "Route110_TrickHousePuzzle7_EventScript_Everett" to
            Route110_TrickHousePuzzle7_EventScript_Everett,
        "Route110_TrickHousePuzzle7_EventScript_Scroll" to
            Route110_TrickHousePuzzle7_EventScript_Scroll,
    )
