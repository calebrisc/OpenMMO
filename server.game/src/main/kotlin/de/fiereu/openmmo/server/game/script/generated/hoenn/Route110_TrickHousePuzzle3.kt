package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_TrickHousePuzzle3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALAN = 630
private const val TRAINER_JUSTIN = 215
private const val TRAINER_MARTHA = 473

internal object Route110_TrickHousePuzzle3_EventScript_Justin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUSTIN,
        Route110_TrickHousePuzzle3.JustinIntro,
        Route110_TrickHousePuzzle3.JustinDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle3.JustinPostBattle)
  }
}

internal object Route110_TrickHousePuzzle3_EventScript_Martha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_MARTHA,
        Route110_TrickHousePuzzle3.MarthaIntro,
        Route110_TrickHousePuzzle3.MarthaDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle3.MarthaPostBattle)
  }
}

internal object Route110_TrickHousePuzzle3_EventScript_Alan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ALAN, Route110_TrickHousePuzzle3.AlanIntro, Route110_TrickHousePuzzle3.AlanDefeat))
        return
    ctx.say(Route110_TrickHousePuzzle3.AlanPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_WOOD_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_ItemWoodMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_ItemWoodMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SHADOW_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_ItemShadowMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_ItemShadowMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_3_STATE, 0, Route110_TrickHousePuzzle3_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle3Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle3_EventScript_Justin" to
            Route110_TrickHousePuzzle3_EventScript_Justin,
        "Route110_TrickHousePuzzle3_EventScript_Martha" to
            Route110_TrickHousePuzzle3_EventScript_Martha,
        "Route110_TrickHousePuzzle3_EventScript_Alan" to
            Route110_TrickHousePuzzle3_EventScript_Alan,
        "Route110_TrickHousePuzzle3_EventScript_ItemWoodMail" to
            Route110_TrickHousePuzzle3_EventScript_ItemWoodMail,
        "Route110_TrickHousePuzzle3_EventScript_ItemShadowMail" to
            Route110_TrickHousePuzzle3_EventScript_ItemShadowMail,
        "Route110_TrickHousePuzzle3_EventScript_Scroll" to
            Route110_TrickHousePuzzle3_EventScript_Scroll,
    )
