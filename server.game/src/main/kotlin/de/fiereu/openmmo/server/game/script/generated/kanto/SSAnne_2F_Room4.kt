package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_2F_Room4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GENTLEMAN_LAMAR = 483
private const val TRAINER_LASS_DAWN = 127

internal object SSAnne_2F_Room4_EventScript_Lamar : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GENTLEMAN_LAMAR, SSAnne_2F_Room4.LamarIntro, SSAnne_2F_Room4.LamarDefeat))
        return
    ctx.say(SSAnne_2F_Room4.LamarPostBattle)
  }
}

internal object SSAnne_2F_Room4_EventScript_Dawn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LASS_DAWN, SSAnne_2F_Room4.DawnIntro, SSAnne_2F_Room4.DawnDefeat))
        return
    ctx.say(SSAnne_2F_Room4.DawnPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_ATTACK
 * end
 * ```
 */
internal object SSAnne_2F_Room4_EventScript_ItemXAttack : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SSAnne_2F_Room4_EventScript_ItemXAttack")
}

internal val SSAnne_2F_Room4Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Room4_EventScript_Lamar" to SSAnne_2F_Room4_EventScript_Lamar,
        "SSAnne_2F_Room4_EventScript_Dawn" to SSAnne_2F_Room4_EventScript_Dawn,
        "SSAnne_2F_Room4_EventScript_ItemXAttack" to SSAnne_2F_Room4_EventScript_ItemXAttack,
    )
