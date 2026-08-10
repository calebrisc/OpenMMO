package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.RocketHideout_B2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TEAM_ROCKET_GRUNT_13 = 363

internal object RocketHideout_B2F_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_13, RocketHideout_B2F.GruntIntro, RocketHideout_B2F.GruntDefeat))
        return
    ctx.say(RocketHideout_B2F.GruntPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_SPEED
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_ItemXSpeed : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B2F_EventScript_ItemXSpeed")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MOON_STONE
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_ItemMoonStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B2F_EventScript_ItemMoonStone")
}

internal object RocketHideout_B2F_EventScript_ItemTM12 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM12)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SUPER_POTION
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_ItemSuperPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B2F_EventScript_ItemSuperPotion")
}

internal val RocketHideout_B2FScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_B2F_EventScript_Grunt" to RocketHideout_B2F_EventScript_Grunt,
        "RocketHideout_B2F_EventScript_ItemXSpeed" to RocketHideout_B2F_EventScript_ItemXSpeed,
        "RocketHideout_B2F_EventScript_ItemMoonStone" to
            RocketHideout_B2F_EventScript_ItemMoonStone,
        "RocketHideout_B2F_EventScript_ItemTM12" to RocketHideout_B2F_EventScript_ItemTM12,
        "RocketHideout_B2F_EventScript_ItemSuperPotion" to
            RocketHideout_B2F_EventScript_ItemSuperPotion,
    )
