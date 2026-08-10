package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_B1F_Room2
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SAILOR_HUEY = 138

internal object SSAnne_B1F_Room2_EventScript_Huey : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_HUEY, SSAnne_B1F_Room2.HueyIntro, SSAnne_B1F_Room2.HueyDefeat))
        return
    ctx.say(SSAnne_B1F_Room2.HueyPostBattle)
  }
}

internal object SSAnne_B1F_Room2_EventScript_ItemTM44 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM44)
}

internal val SSAnne_B1F_Room2Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room2_EventScript_Huey" to SSAnne_B1F_Room2_EventScript_Huey,
        "SSAnne_B1F_Room2_EventScript_ItemTM44" to SSAnne_B1F_Room2_EventScript_ItemTM44,
    )
