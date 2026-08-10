package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_Meadow
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TEAM_ROCKET_GRUNT_49 = 567
private const val TRAINER_TEAM_ROCKET_GRUNT_50 = 568
private const val TRAINER_TEAM_ROCKET_GRUNT_51 = 569

internal object FiveIsland_Meadow_EventScript_Rocket3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_51,
        FiveIsland_Meadow.Rocket3Intro,
        FiveIsland_Meadow.Rocket3Defeat))
        return
    ctx.say(FiveIsland_Meadow.Rocket3PostBattle)
  }
}

internal object FiveIsland_Meadow_EventScript_Rocket1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_49,
        FiveIsland_Meadow.Rocket1Intro,
        FiveIsland_Meadow.Rocket1Defeat))
        return
    ctx.say(FiveIsland_Meadow.Rocket1PostBattle)
  }
}

internal object FiveIsland_Meadow_EventScript_Rocket2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_50,
        FiveIsland_Meadow.Rocket2Intro,
        FiveIsland_Meadow.Rocket2Defeat))
        return
    ctx.say(FiveIsland_Meadow.Rocket2PostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_POTION
 * end
 * ```
 */
internal object FiveIsland_Meadow_EventScript_ItemMaxPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_Meadow_EventScript_ItemMaxPotion")
}

internal object FiveIsland_Meadow_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_UNLOCKED_ROCKET_WAREHOUSE, FiveIsland_Meadow_EventScript_WarehouseDoorAlreadyOpen
 * goto_if_set FLAG_LEARNED_YES_NAH_CHANSEY, FiveIsland_Meadow_EventScript_OpenWarehouseDoor
 * msgbox FiveIsland_Meadow_Text_EnteredPasswordAnotherNeeded
 * releaseall
 * end
 * ```
 */
internal object FiveIsland_Meadow_EventScript_WarehouseDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_Meadow_EventScript_WarehouseDoor")
}

internal val FiveIsland_MeadowScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_Meadow_EventScript_Rocket3" to FiveIsland_Meadow_EventScript_Rocket3,
        "FiveIsland_Meadow_EventScript_Rocket1" to FiveIsland_Meadow_EventScript_Rocket1,
        "FiveIsland_Meadow_EventScript_Rocket2" to FiveIsland_Meadow_EventScript_Rocket2,
        "FiveIsland_Meadow_EventScript_ItemMaxPotion" to
            FiveIsland_Meadow_EventScript_ItemMaxPotion,
        "FiveIsland_Meadow_EventScript_ItemPPUp" to FiveIsland_Meadow_EventScript_ItemPPUp,
        "FiveIsland_Meadow_EventScript_WarehouseDoor" to
            FiveIsland_Meadow_EventScript_WarehouseDoor,
    )
