package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_8F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val TRAINER_SCIENTIST_PARKER = 343
private const val TRAINER_TEAM_ROCKET_GRUNT_32 = 382
private const val TRAINER_TEAM_ROCKET_GRUNT_36 = 386

internal object SilphCo_8F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_32, SilphCo_8F.Grunt1Intro, SilphCo_8F.Grunt1Defeat))
        return
    ctx.say(SilphCo_8F.Grunt1PostBattle)
  }
}

internal object SilphCo_8F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_36, SilphCo_8F.Grunt2Intro, SilphCo_8F.Grunt2Defeat))
        return
    ctx.say(SilphCo_8F.Grunt2PostBattle)
  }
}

internal object SilphCo_8F_EventScript_Parker : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_PARKER, SilphCo_8F.ParkerIntro, SilphCo_8F.ParkerDefeat))
        return
    ctx.say(SilphCo_8F.ParkerPostBattle)
  }
}

internal object SilphCo_8F_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_8F.ThanksForSavingUs)
      return
    }
    ctx.say(SilphCo_8F.WonderIfSilphIsFinished)
  }
}

internal object SilphCo_8F_EventScript_ItemIron : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.IRON)
}

internal object SilphCo_8F_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SilphCo_8F.ToRocketBossMonsAreTools)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 14
 * setvar VAR_0x8004, FLAG_SILPH_8F_DOOR
 * goto_if_set FLAG_SILPH_8F_DOOR, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_Door")
}

internal object SilphCo_8F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_8F.FloorSign)
}

internal val SilphCo_8FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_8F_EventScript_Grunt1" to SilphCo_8F_EventScript_Grunt1,
        "SilphCo_8F_EventScript_Grunt2" to SilphCo_8F_EventScript_Grunt2,
        "SilphCo_8F_EventScript_Parker" to SilphCo_8F_EventScript_Parker,
        "SilphCo_8F_EventScript_WorkerM" to SilphCo_8F_EventScript_WorkerM,
        "SilphCo_8F_EventScript_ItemIron" to SilphCo_8F_EventScript_ItemIron,
        "SilphCo_8F_EventScript_Scientist" to SilphCo_8F_EventScript_Scientist,
        "SilphCo_8F_EventScript_Door" to SilphCo_8F_EventScript_Door,
        "SilphCo_8F_EventScript_FloorSign" to SilphCo_8F_EventScript_FloorSign,
    )
