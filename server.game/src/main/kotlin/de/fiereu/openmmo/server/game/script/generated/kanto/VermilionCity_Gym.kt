package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VermilionCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_LEADER_LT_SURGE = 416

private const val TRAINER_ENGINEER_BAILY = 220
private const val TRAINER_SAILOR_DWAYNE = 141

internal object VermilionCity_Gym_EventScript_LtSurge : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_LEADER_LT_SURGE)
    if (!ctx.trainerBattleSingle(
        TRAINER_LEADER_LT_SURGE, VermilionCity_Gym.LtSurgeIntro, VermilionCity_Gym.LtSurgeDefeat))
        return
    if (firstWin) {
      ctx.setFlag(KantoFlags.FLAG_DEFEATED_LT_SURGE)
      ctx.setFlag(KantoFlags.FLAG_BADGE03_GET)
    }
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TM34_FROM_SURGE)) {
      ctx.say(VermilionCity_Gym.ExplainThunderBadgeTakeThis)
      if (!ctx.giveItem(Items.TM34)) {
        ctx.say(VermilionCity_Gym.MakeRoomInYourBag)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_TM34_FROM_SURGE)
      ctx.say(VermilionCity_Gym.ReceivedTM34FromLtSurge)
      ctx.say(VermilionCity_Gym.ExplainTM34)
      return
    }
    ctx.say(VermilionCity_Gym.LtSurgePostBattle)
  }
}

internal object VermilionCity_Gym_EventScript_Baily : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ENGINEER_BAILY, VermilionCity_Gym.BailyIntro, VermilionCity_Gym.BailyDefeat))
        return
    ctx.say(VermilionCity_Gym.BailyPostBattle)
  }
}

internal object VermilionCity_Gym_EventScript_Dwayne : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAILOR_DWAYNE, VermilionCity_Gym.DwayneIntro, VermilionCity_Gym.DwayneDefeat))
        return
    ctx.say(VermilionCity_Gym.DwaynePostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_LT_SURGE, VermilionCity_Gym_EventScript_GymGuyPostVictory
 * msgbox VermilionCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VermilionCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GENTLEMAN_TUCKER, VermilionCity_Gym_Text_TuckerIntro, VermilionCity_Gym_Text_TuckerDefeat, VermilionCity_Gym_EventScript_DefeatedTucker
 * famechecker FAMECHECKER_LTSURGE, 3
 * msgbox VermilionCity_Gym_Text_TuckerPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_Tucker : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VermilionCity_Gym_EventScript_Tucker")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE03_GET, VermilionCity_Gym_EventScript_GymStatuePostVictory
 * msgbox VermilionCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_GymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 1
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 2
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 3
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 4
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 5
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 6
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan6 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 7
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan7 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan7")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 8
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan8 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan8")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 9
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan9 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan9")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 10
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan10 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan10")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 11
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan11 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan11")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 12
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan12 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan12")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 13
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan13 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan13")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 14
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan14 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan14")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar TRASH_CAN_ID, 15
 * goto VermilionCity_Gym_EventScript_TrashCan
 * end
 * ```
 */
internal object VermilionCity_Gym_EventScript_TrashCan15 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_Gym_EventScript_TrashCan15")
}

internal val VermilionCity_GymScripts: Map<String, Script> =
    mapOf(
        "VermilionCity_Gym_EventScript_LtSurge" to VermilionCity_Gym_EventScript_LtSurge,
        "VermilionCity_Gym_EventScript_Baily" to VermilionCity_Gym_EventScript_Baily,
        "VermilionCity_Gym_EventScript_Dwayne" to VermilionCity_Gym_EventScript_Dwayne,
        "VermilionCity_Gym_EventScript_GymGuy" to VermilionCity_Gym_EventScript_GymGuy,
        "VermilionCity_Gym_EventScript_Tucker" to VermilionCity_Gym_EventScript_Tucker,
        "VermilionCity_Gym_EventScript_GymStatue" to VermilionCity_Gym_EventScript_GymStatue,
        "VermilionCity_Gym_EventScript_TrashCan1" to VermilionCity_Gym_EventScript_TrashCan1,
        "VermilionCity_Gym_EventScript_TrashCan2" to VermilionCity_Gym_EventScript_TrashCan2,
        "VermilionCity_Gym_EventScript_TrashCan3" to VermilionCity_Gym_EventScript_TrashCan3,
        "VermilionCity_Gym_EventScript_TrashCan4" to VermilionCity_Gym_EventScript_TrashCan4,
        "VermilionCity_Gym_EventScript_TrashCan5" to VermilionCity_Gym_EventScript_TrashCan5,
        "VermilionCity_Gym_EventScript_TrashCan6" to VermilionCity_Gym_EventScript_TrashCan6,
        "VermilionCity_Gym_EventScript_TrashCan7" to VermilionCity_Gym_EventScript_TrashCan7,
        "VermilionCity_Gym_EventScript_TrashCan8" to VermilionCity_Gym_EventScript_TrashCan8,
        "VermilionCity_Gym_EventScript_TrashCan9" to VermilionCity_Gym_EventScript_TrashCan9,
        "VermilionCity_Gym_EventScript_TrashCan10" to VermilionCity_Gym_EventScript_TrashCan10,
        "VermilionCity_Gym_EventScript_TrashCan11" to VermilionCity_Gym_EventScript_TrashCan11,
        "VermilionCity_Gym_EventScript_TrashCan12" to VermilionCity_Gym_EventScript_TrashCan12,
        "VermilionCity_Gym_EventScript_TrashCan13" to VermilionCity_Gym_EventScript_TrashCan13,
        "VermilionCity_Gym_EventScript_TrashCan14" to VermilionCity_Gym_EventScript_TrashCan14,
        "VermilionCity_Gym_EventScript_TrashCan15" to VermilionCity_Gym_EventScript_TrashCan15,
    )
