package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_B1F_Room3
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SAILOR_DYLAN = 139

internal object SSAnne_B1F_Room3_EventScript_Dylan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_DYLAN, SSAnne_B1F_Room3.DylanIntro, SSAnne_B1F_Room3.DylanDefeat))
        return
    ctx.say(SSAnne_B1F_Room3.DylanPostBattle)
  }
}

internal object SSAnne_B1F_Room3_EventScript_ItemEther : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ETHER)
}

internal val SSAnne_B1F_Room3Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room3_EventScript_Dylan" to SSAnne_B1F_Room3_EventScript_Dylan,
        "SSAnne_B1F_Room3_EventScript_ItemEther" to SSAnne_B1F_Room3_EventScript_ItemEther,
    )
