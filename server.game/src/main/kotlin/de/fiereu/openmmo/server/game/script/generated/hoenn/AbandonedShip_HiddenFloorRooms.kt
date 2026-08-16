package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_HiddenFloorRooms
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemLuxuryBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.LUXURY_BALL)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SCANNER
 * end
 * ```
 */
internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner")
}

internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemTMRainDance : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM18)
}

internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemWaterStone : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.WATER_STONE)
}

internal object AbandonedShip_HiddenFloorRooms_EventScript_Trash : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(AbandonedShip_HiddenFloorRooms.BrightShinyTrash)
}

internal val AbandonedShip_HiddenFloorRoomsScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemLuxuryBall" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemLuxuryBall,
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner,
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemTMRainDance" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemTMRainDance,
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemWaterStone" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemWaterStone,
        "AbandonedShip_HiddenFloorRooms_EventScript_Trash" to
            AbandonedShip_HiddenFloorRooms_EventScript_Trash,
    )
