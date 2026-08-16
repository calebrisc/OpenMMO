package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_4F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val TRAINER_SCIENTIST_RODNEY = 339
private const val TRAINER_TEAM_ROCKET_GRUNT_26 = 376
private const val TRAINER_TEAM_ROCKET_GRUNT_27 = 377

internal object SilphCo_4F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_27, SilphCo_4F.Grunt2Intro, SilphCo_4F.Grunt2Defeat))
        return
    ctx.say(SilphCo_4F.Grunt2PostBattle)
  }
}

internal object SilphCo_4F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_26, SilphCo_4F.Grunt1Intro, SilphCo_4F.Grunt1Defeat))
        return
    ctx.say(SilphCo_4F.Grunt1PostBattle)
  }
}

internal object SilphCo_4F_EventScript_Rodney : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_RODNEY, SilphCo_4F.RodneyIntro, SilphCo_4F.RodneyDefeat))
        return
    ctx.say(SilphCo_4F.RodneyPostBattle)
  }
}

internal object SilphCo_4F_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_4F.TeamRocketIsGone)
      return
    }
    ctx.say(SilphCo_4F.CantYouSeeImHiding)
  }
}

internal object SilphCo_4F_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_REVIVE)
}

internal object SilphCo_4F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ESCAPE_ROPE)
}

internal object SilphCo_4F_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_HEAL)
}

internal object SilphCo_4F_EventScript_ItemTM41 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM41)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 5
 * setvar VAR_0x8004, FLAG_SILPH_4F_DOOR_1
 * goto_if_set FLAG_SILPH_4F_DOOR_1, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_Door1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_Door1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 6
 * setvar VAR_0x8004, FLAG_SILPH_4F_DOOR_2
 * goto_if_set FLAG_SILPH_4F_DOOR_2, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_Door2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_Door2")
}

internal object SilphCo_4F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_4F.FloorSign)
}

internal val SilphCo_4FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_4F_EventScript_Grunt2" to SilphCo_4F_EventScript_Grunt2,
        "SilphCo_4F_EventScript_Grunt1" to SilphCo_4F_EventScript_Grunt1,
        "SilphCo_4F_EventScript_Rodney" to SilphCo_4F_EventScript_Rodney,
        "SilphCo_4F_EventScript_WorkerM" to SilphCo_4F_EventScript_WorkerM,
        "SilphCo_4F_EventScript_ItemMaxRevive" to SilphCo_4F_EventScript_ItemMaxRevive,
        "SilphCo_4F_EventScript_ItemEscapeRope" to SilphCo_4F_EventScript_ItemEscapeRope,
        "SilphCo_4F_EventScript_ItemFullHeal" to SilphCo_4F_EventScript_ItemFullHeal,
        "SilphCo_4F_EventScript_ItemTM41" to SilphCo_4F_EventScript_ItemTM41,
        "SilphCo_4F_EventScript_Door1" to SilphCo_4F_EventScript_Door1,
        "SilphCo_4F_EventScript_Door2" to SilphCo_4F_EventScript_Door2,
        "SilphCo_4F_EventScript_FloorSign" to SilphCo_4F_EventScript_FloorSign,
    )
