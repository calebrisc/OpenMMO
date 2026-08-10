package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_3F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SCIENTIST_JOSE = 338
private const val TRAINER_TEAM_ROCKET_GRUNT_25 = 375

internal object SilphCo_3F_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_25, SilphCo_3F.GruntIntro, SilphCo_3F.GruntDefeat))
        return
    ctx.say(SilphCo_3F.GruntPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_3F_EventScript_WorkerMRocketsGone
 * msgbox SilphCo_3F_Text_WhatAmIToDo
 * release
 * end
 * ```
 */
internal object SilphCo_3F_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_3F_EventScript_WorkerM")
}

internal object SilphCo_3F_EventScript_Jose : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_JOSE, SilphCo_3F.JoseIntro, SilphCo_3F.JoseDefeat))
        return
    ctx.say(SilphCo_3F.JosePostBattle)
  }
}

internal object SilphCo_3F_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HYPER_POTION)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 3
 * setvar VAR_0x8004, FLAG_SILPH_3F_DOOR_1
 * goto_if_set FLAG_SILPH_3F_DOOR_1, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_3F_EventScript_Door1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_3F_EventScript_Door1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 4
 * setvar VAR_0x8004, FLAG_SILPH_3F_DOOR_2
 * goto_if_set FLAG_SILPH_3F_DOOR_2, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_3F_EventScript_Door2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_3F_EventScript_Door2")
}

internal object SilphCo_3F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_3F.FloorSign)
}

internal val SilphCo_3FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_3F_EventScript_Grunt" to SilphCo_3F_EventScript_Grunt,
        "SilphCo_3F_EventScript_WorkerM" to SilphCo_3F_EventScript_WorkerM,
        "SilphCo_3F_EventScript_Jose" to SilphCo_3F_EventScript_Jose,
        "SilphCo_3F_EventScript_ItemHyperPotion" to SilphCo_3F_EventScript_ItemHyperPotion,
        "SilphCo_3F_EventScript_Door1" to SilphCo_3F_EventScript_Door1,
        "SilphCo_3F_EventScript_Door2" to SilphCo_3F_EventScript_Door2,
        "SilphCo_3F_EventScript_FloorSign" to SilphCo_3F_EventScript_FloorSign,
    )
