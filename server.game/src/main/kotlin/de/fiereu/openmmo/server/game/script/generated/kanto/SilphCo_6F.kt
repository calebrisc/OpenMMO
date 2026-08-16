package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_6F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val TRAINER_SCIENTIST_TAYLOR = 341
private const val TRAINER_TEAM_ROCKET_GRUNT_30 = 380
private const val TRAINER_TEAM_ROCKET_GRUNT_31 = 381

internal object SilphCo_6F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_30, SilphCo_6F.Grunt1Intro, SilphCo_6F.Grunt1Defeat))
        return
    ctx.say(SilphCo_6F.Grunt1PostBattle)
  }
}

internal object SilphCo_6F_EventScript_WorkerM1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_6F.WeGotEngaged)
      return
    }
    ctx.say(SilphCo_6F.HelpMePlease)
  }
}

internal object SilphCo_6F_EventScript_WorkerF1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_6F.NeedsMeToLookAfterHim)
      return
    }
    ctx.say(SilphCo_6F.ThatManIsSuchACoward)
  }
}

internal object SilphCo_6F_EventScript_WorkerM2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_6F.ComeWorkForSilphWhenYoureOlder)
      return
    }
    ctx.say(SilphCo_6F.TargetedSilphForOurMonProducts)
  }
}

internal object SilphCo_6F_EventScript_WorkerM3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_6F.BetterGetBackToWork)
      return
    }
    ctx.say(SilphCo_6F.RocketsTookOverBuilding)
  }
}

internal object SilphCo_6F_EventScript_WorkerF2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_6F.RocketsRanAwayBecauseOfYou)
      return
    }
    ctx.say(SilphCo_6F.RocketsTryingToConquerWorld)
  }
}

internal object SilphCo_6F_EventScript_Taylor : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_TAYLOR, SilphCo_6F.TaylorIntro, SilphCo_6F.TaylorDefeat))
        return
    ctx.say(SilphCo_6F.TaylorPostBattle)
  }
}

internal object SilphCo_6F_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HP_UP)
}

internal object SilphCo_6F_EventScript_ItemXSpecial : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.X_SPECIAL)
}

internal object SilphCo_6F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_31, SilphCo_6F.Grunt2Intro, SilphCo_6F.Grunt2Defeat))
        return
    ctx.say(SilphCo_6F.Grunt2PostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 10
 * setvar VAR_0x8004, FLAG_SILPH_6F_DOOR
 * goto_if_set FLAG_SILPH_6F_DOOR, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_Door")
}

internal object SilphCo_6F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_6F.FloorSign)
}

internal val SilphCo_6FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_6F_EventScript_Grunt1" to SilphCo_6F_EventScript_Grunt1,
        "SilphCo_6F_EventScript_WorkerM1" to SilphCo_6F_EventScript_WorkerM1,
        "SilphCo_6F_EventScript_WorkerF1" to SilphCo_6F_EventScript_WorkerF1,
        "SilphCo_6F_EventScript_WorkerM2" to SilphCo_6F_EventScript_WorkerM2,
        "SilphCo_6F_EventScript_WorkerM3" to SilphCo_6F_EventScript_WorkerM3,
        "SilphCo_6F_EventScript_WorkerF2" to SilphCo_6F_EventScript_WorkerF2,
        "SilphCo_6F_EventScript_Taylor" to SilphCo_6F_EventScript_Taylor,
        "SilphCo_6F_EventScript_ItemHPUp" to SilphCo_6F_EventScript_ItemHPUp,
        "SilphCo_6F_EventScript_ItemXSpecial" to SilphCo_6F_EventScript_ItemXSpecial,
        "SilphCo_6F_EventScript_Grunt2" to SilphCo_6F_EventScript_Grunt2,
        "SilphCo_6F_EventScript_Door" to SilphCo_6F_EventScript_Door,
        "SilphCo_6F_EventScript_FloorSign" to SilphCo_6F_EventScript_FloorSign,
    )
