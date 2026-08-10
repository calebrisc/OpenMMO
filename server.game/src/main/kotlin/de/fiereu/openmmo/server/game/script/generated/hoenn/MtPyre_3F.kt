package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_3F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_KAYLA = 247
private const val TRAINER_WILLIAM = 236

internal object MtPyre_3F_EventScript_William : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_WILLIAM, MtPyre_3F.WilliamIntro, MtPyre_3F.WilliamDefeat))
        return
    ctx.say(MtPyre_3F.WilliamPostBattle)
  }
}

internal object MtPyre_3F_EventScript_Kayla : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KAYLA, MtPyre_3F.KaylaIntro, MtPyre_3F.KaylaDefeat)) return
    ctx.say(MtPyre_3F.KaylaPostBattle)
  }
}

internal object MtPyre_3F_EventScript_ItemSuperRepel : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SUPER_REPEL)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GABRIELLE_1, MtPyre_3F_Text_GabrielleIntro, MtPyre_3F_Text_GabrielleDefeat, MtPyre_3F_EventScript_RegisterGabrielle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MtPyre_3F_EventScript_RematchGabrielle
 * msgbox MtPyre_3F_Text_GabriellePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MtPyre_3F_EventScript_Gabrielle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_3F_EventScript_Gabrielle")
}

internal val MtPyre_3FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_3F_EventScript_William" to MtPyre_3F_EventScript_William,
        "MtPyre_3F_EventScript_Kayla" to MtPyre_3F_EventScript_Kayla,
        "MtPyre_3F_EventScript_ItemSuperRepel" to MtPyre_3F_EventScript_ItemSuperRepel,
        "MtPyre_3F_EventScript_Gabrielle" to MtPyre_3F_EventScript_Gabrielle,
    )
