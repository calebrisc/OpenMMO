package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_1F_Room2
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_LASS_ANN = 126
private const val TRAINER_YOUNGSTER_TYLER = 96

internal object SSAnne_1F_Room2_EventScript_Ann : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LASS_ANN, SSAnne_1F_Room2.AnnIntro, SSAnne_1F_Room2.AnnDefeat))
        return
    ctx.say(SSAnne_1F_Room2.AnnPostBattle)
  }
}

internal object SSAnne_1F_Room2_EventScript_Tyler : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNGSTER_TYLER, SSAnne_1F_Room2.TylerIntro, SSAnne_1F_Room2.TylerDefeat))
        return
    ctx.say(SSAnne_1F_Room2.TylerPostBattle)
  }
}

internal object SSAnne_1F_Room2_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_1F_Room2.CruisingAroundWorld)
}

internal object SSAnne_1F_Room2_EventScript_ItemTM31 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM31)
}

internal val SSAnne_1F_Room2Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room2_EventScript_Ann" to SSAnne_1F_Room2_EventScript_Ann,
        "SSAnne_1F_Room2_EventScript_Tyler" to SSAnne_1F_Room2_EventScript_Tyler,
        "SSAnne_1F_Room2_EventScript_Woman" to SSAnne_1F_Room2_EventScript_Woman,
        "SSAnne_1F_Room2_EventScript_ItemTM31" to SSAnne_1F_Room2_EventScript_ItemTM31,
    )
