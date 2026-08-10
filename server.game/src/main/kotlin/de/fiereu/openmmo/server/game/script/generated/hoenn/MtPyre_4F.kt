package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_4F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TASHA = 109

internal object MtPyre_4F_EventScript_Tasha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TASHA, MtPyre_4F.TashaIntro, MtPyre_4F.TashaDefeat)) return
    ctx.say(MtPyre_4F.TashaPostBattle)
  }
}

internal object MtPyre_4F_EventScript_ItemSeaIncense : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SEA_INCENSE)
}

internal val MtPyre_4FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_4F_EventScript_Tasha" to MtPyre_4F_EventScript_Tasha,
        "MtPyre_4F_EventScript_ItemSeaIncense" to MtPyre_4F_EventScript_ItemSeaIncense,
    )
