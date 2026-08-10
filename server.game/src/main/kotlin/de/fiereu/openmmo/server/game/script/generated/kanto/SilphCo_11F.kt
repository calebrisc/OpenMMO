package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_11F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TEAM_ROCKET_GRUNT_40 = 390
private const val TRAINER_TEAM_ROCKET_GRUNT_41 = 391

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_MASTER_BALL_FROM_SILPH, SilphCo_11F_EventScript_AlreadyGotMasterBall
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, SilphCo_11F_EventScript_PresidentThanksMale
 * call_if_eq VAR_RESULT, FEMALE, SilphCo_11F_EventScript_PresidentThanksFemale
 * checkitemspace ITEM_MASTER_BALL
 * goto_if_eq VAR_RESULT, FALSE, SilphCo_11F_EventScript_NoRoomForMasterBall
 * giveitem_msg SilphCo_11F_Text_ObtainedMasterBallFromPresident, ITEM_MASTER_BALL, 1, MUS_OBTAIN_KEY_ITEM
 * msgbox SilphCo_11F_Text_ThatsOurSecretPrototype
 * setflag FLAG_GOT_MASTER_BALL_FROM_SILPH
 * release
 * end
 * ```
 */
internal object SilphCo_11F_EventScript_President : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_11F_EventScript_President")
}

internal object SilphCo_11F_EventScript_Secretary : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SilphCo_11F.ThanksForRescuingUs)
}

internal object SilphCo_11F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_40, SilphCo_11F.Grunt1Intro, SilphCo_11F.Grunt1Defeat))
        return
    ctx.say(SilphCo_11F.Grunt1PostBattle)
  }
}

internal object SilphCo_11F_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ZINC)
}

internal object SilphCo_11F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_41, SilphCo_11F.Grunt2Intro, SilphCo_11F.Grunt2Defeat))
        return
    ctx.say(SilphCo_11F.Grunt2PostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 20
 * setvar VAR_0x8004, FLAG_SILPH_11F_DOOR
 * goto_if_set FLAG_SILPH_11F_DOOR, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_11F_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_11F_EventScript_Door")
}

internal object SilphCo_11F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_11F.FloorSign)
}

internal val SilphCo_11FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_11F_EventScript_President" to SilphCo_11F_EventScript_President,
        "SilphCo_11F_EventScript_Secretary" to SilphCo_11F_EventScript_Secretary,
        "SilphCo_11F_EventScript_Grunt1" to SilphCo_11F_EventScript_Grunt1,
        "SilphCo_11F_EventScript_ItemZinc" to SilphCo_11F_EventScript_ItemZinc,
        "SilphCo_11F_EventScript_Grunt2" to SilphCo_11F_EventScript_Grunt2,
        "SilphCo_11F_EventScript_Door" to SilphCo_11F_EventScript_Door,
        "SilphCo_11F_EventScript_FloorSign" to SilphCo_11F_EventScript_FloorSign,
    )
