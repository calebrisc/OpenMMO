package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_5F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ATSUSHI = 190

internal object MtPyre_5F_EventScript_Atsushi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ATSUSHI, MtPyre_5F.AtsushiIntro, MtPyre_5F.AtsushiDefeat))
        return
    ctx.say(MtPyre_5F.AtsushiPostBattle)
  }
}

internal object MtPyre_5F_EventScript_ItemLaxIncense : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.LAX_INCENSE)
}

internal val MtPyre_5FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_5F_EventScript_Atsushi" to MtPyre_5F_EventScript_Atsushi,
        "MtPyre_5F_EventScript_ItemLaxIncense" to MtPyre_5F_EventScript_ItemLaxIncense,
    )
