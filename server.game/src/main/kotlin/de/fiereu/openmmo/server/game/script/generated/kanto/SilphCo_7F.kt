package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_7F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SCIENTIST_JOSHUA = 342
private const val TRAINER_TEAM_ROCKET_GRUNT_33 = 383
private const val TRAINER_TEAM_ROCKET_GRUNT_34 = 384
private const val TRAINER_TEAM_ROCKET_GRUNT_35 = 385

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_LAPRAS_FROM_SILPH, SilphCo_7F_EventScript_AlreadyGotLapras
 * msgbox SilphCo_7F_Text_HaveMonForSavingUs
 * setvar VAR_TEMP_1, SPECIES_LAPRAS
 * givemon SPECIES_LAPRAS, 25
 * goto_if_eq VAR_RESULT, 0, SilphCo_7F_EventScript_ReceiveLaprasParty
 * goto_if_eq VAR_RESULT, 1, SilphCo_7F_EventScript_ReceiveLaprasPC
 * goto_if_eq VAR_RESULT, 2, EventScript_NoMoreRoomForPokemon
 * release
 * end
 * ```
 */
internal object SilphCo_7F_EventScript_LaprasGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_7F_EventScript_LaprasGuy")
}

internal object SilphCo_7F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_33, SilphCo_7F.Grunt1Intro, SilphCo_7F.Grunt1Defeat))
        return
    ctx.say(SilphCo_7F.Grunt1PostBattle)
  }
}

internal object SilphCo_7F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_34, SilphCo_7F.Grunt2Intro, SilphCo_7F.Grunt2Defeat))
        return
    ctx.say(SilphCo_7F.Grunt2PostBattle)
  }
}

internal object SilphCo_7F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_35, SilphCo_7F.Grunt3Intro, SilphCo_7F.Grunt3Defeat))
        return
    ctx.say(SilphCo_7F.Grunt3PostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_7F_EventScript_WorkerFRocketsGone
 * msgbox SilphCo_7F_Text_ReallyDangerousHere
 * release
 * end
 * ```
 */
internal object SilphCo_7F_EventScript_WorkerF : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_7F_EventScript_WorkerF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_7F_EventScript_WorkerM1RocketsGone
 * msgbox SilphCo_7F_Text_RocketsAfterMasterBall
 * release
 * end
 * ```
 */
internal object SilphCo_7F_EventScript_WorkerM1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_7F_EventScript_WorkerM1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_7F_EventScript_WorkerM2RocketsGone
 * msgbox SilphCo_7F_Text_BadIfTeamRocketTookOver
 * release
 * end
 * ```
 */
internal object SilphCo_7F_EventScript_WorkerM2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_7F_EventScript_WorkerM2")
}

internal object SilphCo_7F_EventScript_Joshua : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_JOSHUA, SilphCo_7F.JoshuaIntro, SilphCo_7F.JoshuaDefeat))
        return
    ctx.say(SilphCo_7F.JoshuaPostBattle)
  }
}

internal object SilphCo_7F_EventScript_ItemCalcium : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CALCIUM)
}

internal object SilphCo_7F_EventScript_ItemTM08 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM08)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 11
 * setvar VAR_0x8004, FLAG_SILPH_7F_DOOR_1
 * goto_if_set FLAG_SILPH_7F_DOOR_1, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_7F_EventScript_Door1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_7F_EventScript_Door1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 12
 * setvar VAR_0x8004, FLAG_SILPH_7F_DOOR_2
 * goto_if_set FLAG_SILPH_7F_DOOR_2, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_7F_EventScript_Door2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_7F_EventScript_Door2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 13
 * setvar VAR_0x8004, FLAG_SILPH_7F_DOOR_3
 * goto_if_set FLAG_SILPH_7F_DOOR_3, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_7F_EventScript_Door3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_7F_EventScript_Door3")
}

internal object SilphCo_7F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_7F.FloorSign)
}

internal val SilphCo_7FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_7F_EventScript_LaprasGuy" to SilphCo_7F_EventScript_LaprasGuy,
        "SilphCo_7F_EventScript_Grunt1" to SilphCo_7F_EventScript_Grunt1,
        "SilphCo_7F_EventScript_Grunt2" to SilphCo_7F_EventScript_Grunt2,
        "SilphCo_7F_EventScript_Grunt3" to SilphCo_7F_EventScript_Grunt3,
        "SilphCo_7F_EventScript_WorkerF" to SilphCo_7F_EventScript_WorkerF,
        "SilphCo_7F_EventScript_WorkerM1" to SilphCo_7F_EventScript_WorkerM1,
        "SilphCo_7F_EventScript_WorkerM2" to SilphCo_7F_EventScript_WorkerM2,
        "SilphCo_7F_EventScript_Joshua" to SilphCo_7F_EventScript_Joshua,
        "SilphCo_7F_EventScript_ItemCalcium" to SilphCo_7F_EventScript_ItemCalcium,
        "SilphCo_7F_EventScript_ItemTM08" to SilphCo_7F_EventScript_ItemTM08,
        "SilphCo_7F_EventScript_Door1" to SilphCo_7F_EventScript_Door1,
        "SilphCo_7F_EventScript_Door2" to SilphCo_7F_EventScript_Door2,
        "SilphCo_7F_EventScript_Door3" to SilphCo_7F_EventScript_Door3,
        "SilphCo_7F_EventScript_FloorSign" to SilphCo_7F_EventScript_FloorSign,
    )
