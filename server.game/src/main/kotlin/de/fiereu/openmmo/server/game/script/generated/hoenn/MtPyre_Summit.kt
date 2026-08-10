package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_Summit
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_MT_PYRE_1 = 23
private const val TRAINER_GRUNT_MT_PYRE_2 = 24
private const val TRAINER_GRUNT_MT_PYRE_3 = 25
private const val TRAINER_GRUNT_MT_PYRE_4 = 569

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SOOTOPOLIS_ARCHIE_MAXIE_LEAVE, MtPyre_Summit_EventScript_OldManAfterRayquaza
 * msgbox MtPyre_Summit_Text_WillYouHearOutMyTale, MSGBOX_YESNO
 * call_if_eq VAR_RESULT, YES, MtPyre_Summit_EventScript_OldManTale
 * call_if_eq VAR_RESULT, NO, MtPyre_Summit_EventScript_DeclineOldManTale
 * release
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_OldMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RETURNED_RED_OR_BLUE_ORB, MtPyre_Summit_EventScript_OldLadyAfterOrbsReturned
 * call_if_ge VAR_MT_PYRE_STATE, 3, MtPyre_Summit_EventScript_OldLadyOrbsReturned
 * goto_if_set FLAG_KYOGRE_ESCAPED_SEAFLOOR_CAVERN, MtPyre_Summit_EventScript_OldLadyLegendariesAwake
 * msgbox MtPyre_Summit_Text_OrbsHaveBeenTaken, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_OldLady : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_OldLady")
}

internal object MtPyre_Summit_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_1, MtPyre_Summit.Grunt1Intro, MtPyre_Summit.Grunt1Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt1PostBattle)
  }
}

internal object MtPyre_Summit_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_2, MtPyre_Summit.Grunt2Intro, MtPyre_Summit.Grunt2Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt2PostBattle)
  }
}

internal object MtPyre_Summit_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_3, MtPyre_Summit.Grunt3Intro, MtPyre_Summit.Grunt3Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt3PostBattle)
  }
}

internal object MtPyre_Summit_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_4, MtPyre_Summit.Grunt4Intro, MtPyre_Summit.Grunt4Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt4PostBattle)
  }
}

internal val MtPyre_SummitScripts: Map<String, Script> =
    mapOf(
        "MtPyre_Summit_EventScript_OldMan" to MtPyre_Summit_EventScript_OldMan,
        "MtPyre_Summit_EventScript_OldLady" to MtPyre_Summit_EventScript_OldLady,
        "MtPyre_Summit_EventScript_Grunt1" to MtPyre_Summit_EventScript_Grunt1,
        "MtPyre_Summit_EventScript_Grunt2" to MtPyre_Summit_EventScript_Grunt2,
        "MtPyre_Summit_EventScript_Grunt3" to MtPyre_Summit_EventScript_Grunt3,
        "MtPyre_Summit_EventScript_Grunt4" to MtPyre_Summit_EventScript_Grunt4,
    )
