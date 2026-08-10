package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AquaHideout_B2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_AQUA_HIDEOUT_6 = 28
private const val TRAINER_GRUNT_AQUA_HIDEOUT_8 = 193

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MATT, AquaHideout_B2F_Text_MattIntro, AquaHideout_B2F_Text_MattDefeat, AquaHideout_B2F_EventScript_SubmarineEscape
 * msgbox AquaHideout_B2F_Text_MattPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object AquaHideout_B2F_EventScript_Matt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B2F_EventScript_Matt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_AQUA_HIDEOUT_4, AquaHideout_B2F_Text_Grunt4Intro, AquaHideout_B2F_Text_Grunt4Defeat, AquaHideout_B2F_EventScript_Grunt4Defeated
 * msgbox AquaHideout_B2F_Text_Grunt4PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AquaHideout_B2F_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B2F_EventScript_Grunt4")
}

internal object AquaHideout_B2F_EventScript_ItemNestBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NEST_BALL)
}

internal object AquaHideout_B2F_EventScript_Grunt6 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_AQUA_HIDEOUT_6, AquaHideout_B2F.Grunt6Intro, AquaHideout_B2F.Grunt6Defeat))
        return
    ctx.say(AquaHideout_B2F.Grunt6PostBattle)
  }
}

internal object AquaHideout_B2F_EventScript_Grunt8 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_AQUA_HIDEOUT_8, AquaHideout_B2F.Grunt8Intro, AquaHideout_B2F.Grunt8Defeat))
        return
    ctx.say(AquaHideout_B2F.Grunt8PostBattle)
  }
}

internal val AquaHideout_B2FScripts: Map<String, Script> =
    mapOf(
        "AquaHideout_B2F_EventScript_Matt" to AquaHideout_B2F_EventScript_Matt,
        "AquaHideout_B2F_EventScript_Grunt4" to AquaHideout_B2F_EventScript_Grunt4,
        "AquaHideout_B2F_EventScript_ItemNestBall" to AquaHideout_B2F_EventScript_ItemNestBall,
        "AquaHideout_B2F_EventScript_Grunt6" to AquaHideout_B2F_EventScript_Grunt6,
        "AquaHideout_B2F_EventScript_Grunt8" to AquaHideout_B2F_EventScript_Grunt8,
    )
