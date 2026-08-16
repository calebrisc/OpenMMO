package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.RocketHideout_B3F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TEAM_ROCKET_GRUNT_14 = 364
private const val TRAINER_TEAM_ROCKET_GRUNT_15 = 365

internal object RocketHideout_B3F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_15,
        RocketHideout_B3F.Grunt2Intro,
        RocketHideout_B3F.Grunt2Defeat))
        return
    ctx.say(RocketHideout_B3F.Grunt2PostBattle)
  }
}

internal object RocketHideout_B3F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_14,
        RocketHideout_B3F.Grunt1Intro,
        RocketHideout_B3F.Grunt1Defeat))
        return
    ctx.say(RocketHideout_B3F.Grunt1PostBattle)
  }
}

internal object RocketHideout_B3F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object RocketHideout_B3F_EventScript_ItemTM21 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM21)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BLACK_GLASSES
 * end
 * ```
 */
internal object RocketHideout_B3F_EventScript_ItemBlackGlasses : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.BLACKGLASSES)
}

internal val RocketHideout_B3FScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_B3F_EventScript_Grunt2" to RocketHideout_B3F_EventScript_Grunt2,
        "RocketHideout_B3F_EventScript_Grunt1" to RocketHideout_B3F_EventScript_Grunt1,
        "RocketHideout_B3F_EventScript_ItemRareCandy" to
            RocketHideout_B3F_EventScript_ItemRareCandy,
        "RocketHideout_B3F_EventScript_ItemTM21" to RocketHideout_B3F_EventScript_ItemTM21,
        "RocketHideout_B3F_EventScript_ItemBlackGlasses" to
            RocketHideout_B3F_EventScript_ItemBlackGlasses,
    )
