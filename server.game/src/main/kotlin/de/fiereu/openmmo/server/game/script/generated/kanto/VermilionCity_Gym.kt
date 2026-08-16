package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.VermilionCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_GENTLEMAN_TUCKER = 423

// The doors are metatile changes the protocol cannot make yet, so solving the puzzle walks the
// player through the doorway instead. First switch is random per character, second is a fresh
// random can, and a wrong second guess relocks both, like the cartridge.
private const val CAN1 = "kanto/VAR_VERMILION_GYM_CAN1"
private const val CAN2 = "kanto/VAR_VERMILION_GYM_CAN2"
private const val LOCKS = "kanto/VAR_VERMILION_GYM_LOCKS"

private suspend fun trashCan(ctx: ScriptContext, can: Int) {
  if (ctx.getVar(LOCKS) == 2) {
    // The door metatile never opens, so a solved puzzle keeps walking the player through.
    ctx.sign(VermilionCity_Gym.NopeOnlyTrashHere)
    ctx.repositionSelf(5, 4, Direction.UP)
    return
  }
  if (ctx.getVar(CAN1) == 0) ctx.setVar(CAN1, (1..15).random())
  when {
    ctx.getVar(LOCKS) == 0 && can == ctx.getVar(CAN1) -> {
      ctx.setVar(LOCKS, 1)
      ctx.setVar(CAN2, ((1..15).toList() - can).random())
      ctx.sign(VermilionCity_Gym.SwitchUnderTrashFirstLockOpened)
    }
    ctx.getVar(LOCKS) == 1 && can == ctx.getVar(CAN2) -> {
      ctx.setVar(LOCKS, 2)
      ctx.sign(VermilionCity_Gym.SecondLockOpened)
      ctx.repositionSelf(5, 4, Direction.UP)
    }
    ctx.getVar(LOCKS) == 1 -> {
      // The wrong can slams both locks shut and the first switch moves.
      ctx.setVar(LOCKS, 0)
      ctx.setVar(CAN1, 0)
      ctx.sign(VermilionCity_Gym.AnotherSwitchInTrash)
    }
    else -> ctx.sign(VermilionCity_Gym.NopeOnlyTrashHere)
  }
}

private const val TRAINER_LEADER_LT_SURGE = 416

private const val TRAINER_ENGINEER_BAILY = 220
private const val TRAINER_SAILOR_DWAYNE = 141

internal object VermilionCity_Gym_EventScript_LtSurge : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.leaderBattle(
        TRAINER_LEADER_LT_SURGE, VermilionCity_Gym.LtSurgeIntro, VermilionCity_Gym.LtSurgeDefeat) {
          ctx.setFlag(KantoFlags.FLAG_DEFEATED_LT_SURGE)
          ctx.setFlag(KantoFlags.FLAG_BADGE03_GET)
        })
        return
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
  override suspend fun run(ctx: ScriptContext) =
      gymGuide(
          ctx,
          KantoFlags.FLAG_DEFEATED_LT_SURGE,
          VermilionCity_Gym.GymGuyAdvice,
          VermilionCity_Gym.GymGuyPostVictory)
}

internal object VermilionCity_Gym_EventScript_Tucker : Script {
  override suspend fun run(ctx: ScriptContext) {
    // The decomp also ticks a fame checker entry, which is a trainer-card scrapbook this server
    // has no notion of, so the fight and the line are the whole of it here.
    if (!ctx.trainerBattleSingle(
        TRAINER_GENTLEMAN_TUCKER, VermilionCity_Gym.TuckerIntro, VermilionCity_Gym.TuckerDefeat))
        return
    ctx.say(VermilionCity_Gym.TuckerPostBattle)
  }
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
      gymPlaque(
          ctx,
          KantoFlags.FLAG_BADGE03_GET,
          VermilionCity_Gym.GymStatue,
          VermilionCity_Gym.GymStatuePlayerWon)
}

internal object VermilionCity_Gym_EventScript_TrashCan1 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 1)
}

internal object VermilionCity_Gym_EventScript_TrashCan2 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 2)
}

internal object VermilionCity_Gym_EventScript_TrashCan3 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 3)
}

internal object VermilionCity_Gym_EventScript_TrashCan4 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 4)
}

internal object VermilionCity_Gym_EventScript_TrashCan5 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 5)
}

internal object VermilionCity_Gym_EventScript_TrashCan6 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 6)
}

internal object VermilionCity_Gym_EventScript_TrashCan7 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 7)
}

internal object VermilionCity_Gym_EventScript_TrashCan8 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 8)
}

internal object VermilionCity_Gym_EventScript_TrashCan9 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 9)
}

internal object VermilionCity_Gym_EventScript_TrashCan10 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 10)
}

internal object VermilionCity_Gym_EventScript_TrashCan11 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 11)
}

internal object VermilionCity_Gym_EventScript_TrashCan12 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 12)
}

internal object VermilionCity_Gym_EventScript_TrashCan13 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 13)
}

internal object VermilionCity_Gym_EventScript_TrashCan14 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 14)
}

internal object VermilionCity_Gym_EventScript_TrashCan15 : Script {
  override suspend fun run(ctx: ScriptContext) = trashCan(ctx, 15)
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
