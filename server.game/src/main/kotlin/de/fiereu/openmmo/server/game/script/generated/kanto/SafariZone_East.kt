package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SafariZone_East
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_East_EventScript_ItemMaxPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_POTION)
}

internal object SafariZone_East_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_RESTORE)
}

internal object SafariZone_East_EventScript_ItemTM11 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM11)
}

internal object SafariZone_East_EventScript_ItemLeafStone : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.LEAF_STONE)
}

internal object SafariZone_East_EventScript_AreaSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_East.AreaSign)
}

internal object SafariZone_East_EventScript_RestHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_East.RestHouse)
}

internal object SafariZone_East_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SafariZone_East.TimeDeclinesOnlyWhileYouWalk)
}

internal val SafariZone_EastScripts: Map<String, Script> =
    mapOf(
        "SafariZone_East_EventScript_ItemMaxPotion" to SafariZone_East_EventScript_ItemMaxPotion,
        "SafariZone_East_EventScript_ItemFullRestore" to
            SafariZone_East_EventScript_ItemFullRestore,
        "SafariZone_East_EventScript_ItemTM11" to SafariZone_East_EventScript_ItemTM11,
        "SafariZone_East_EventScript_ItemLeafStone" to SafariZone_East_EventScript_ItemLeafStone,
        "SafariZone_East_EventScript_AreaSign" to SafariZone_East_EventScript_AreaSign,
        "SafariZone_East_EventScript_RestHouseSign" to SafariZone_East_EventScript_RestHouseSign,
        "SafariZone_East_EventScript_TrainerTips" to SafariZone_East_EventScript_TrainerTips,
    )
