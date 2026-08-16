package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_11F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_TEAM_ROCKET_GRUNT_40 = 390
private const val TRAINER_TEAM_ROCKET_GRUNT_41 = 391

internal object SilphCo_11F_EventScript_President : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_MASTER_BALL_FROM_SILPH)) {
      ctx.say(SilphCo_11F.ThatsOurSecretPrototype)
      return
    }
    val gender1 = if (ctx.isFemale) 1 else 0
    if (gender1 == 0) {
      ctx.say(SilphCo_11F.ThanksForSavingMeDearBoy)
    }
    if (gender1 == 1) {
      ctx.say(SilphCo_11F.ThanksForSavingMeDearGirl)
    }
    if (!ctx.giveItem(Items.MASTER_BALL)) {
      ctx.say(SilphCo_11F.YouHaveNoRoomForThis)
      return
    }
    ctx.say(SilphCo_11F.ObtainedMasterBallFromPresident)
    ctx.say(SilphCo_11F.ThatsOurSecretPrototype)
    ctx.setFlag(KantoFlags.FLAG_GOT_MASTER_BALL_FROM_SILPH)
  }
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
