package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MagmaHideout_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_MAGMA_HIDEOUT_1 = 716
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_2 = 717

internal object MagmaHideout_1F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_1, MagmaHideout_1F.Grunt1Intro, MagmaHideout_1F.Grunt1Defeat))
        return
    ctx.say(MagmaHideout_1F.Grunt1PostBattle)
  }
}

internal object MagmaHideout_1F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object MagmaHideout_1F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_2, MagmaHideout_1F.Grunt2Intro, MagmaHideout_1F.Grunt2Defeat))
        return
    ctx.say(MagmaHideout_1F.Grunt2PostBattle)
  }
}

internal val MagmaHideout_1FScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_1F_EventScript_Grunt1" to MagmaHideout_1F_EventScript_Grunt1,
        "MagmaHideout_1F_EventScript_ItemRareCandy" to MagmaHideout_1F_EventScript_ItemRareCandy,
        "MagmaHideout_1F_EventScript_Grunt2" to MagmaHideout_1F_EventScript_Grunt2,
    )
