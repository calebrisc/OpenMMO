package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_2F_Room2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_FISHERMAN_DALE = 223
private const val TRAINER_GENTLEMAN_BROOKS = 482

internal object SSAnne_2F_Room2_EventScript_Dale : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FISHERMAN_DALE, SSAnne_2F_Room2.DaleIntro, SSAnne_2F_Room2.DaleDefeat))
        return
    ctx.say(SSAnne_2F_Room2.DalePostBattle)
  }
}

internal object SSAnne_2F_Room2_EventScript_Brooks : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GENTLEMAN_BROOKS, SSAnne_2F_Room2.BrooksIntro, SSAnne_2F_Room2.BrooksDefeat))
        return
    ctx.say(SSAnne_2F_Room2.BrooksPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STARDUST
 * end
 * ```
 */
internal object SSAnne_2F_Room2_EventScript_ItemStardust : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SSAnne_2F_Room2_EventScript_ItemStardust")
}

internal val SSAnne_2F_Room2Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Room2_EventScript_Dale" to SSAnne_2F_Room2_EventScript_Dale,
        "SSAnne_2F_Room2_EventScript_Brooks" to SSAnne_2F_Room2_EventScript_Brooks,
        "SSAnne_2F_Room2_EventScript_ItemStardust" to SSAnne_2F_Room2_EventScript_ItemStardust,
    )
