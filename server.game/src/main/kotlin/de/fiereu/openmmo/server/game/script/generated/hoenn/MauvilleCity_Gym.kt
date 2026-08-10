package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MauvilleCity_Gym
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ANGELO = 802
private const val TRAINER_BEN = 323
private const val TRAINER_KIRK = 191
private const val TRAINER_SHAWN = 194
private const val TRAINER_VIVIAN = 649

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WATTSON_1, MauvilleCity_Gym_Text_WattsonIntro, MauvilleCity_Gym_Text_WattsonDefeat, MauvilleCity_Gym_EventScript_WattsonDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MauvilleCity_Gym_EventScript_WattsonRematch
 * goto_if_unset FLAG_RECEIVED_TM_SHOCK_WAVE, MauvilleCity_Gym_EventScript_GiveShockWave2
 * goto_if_eq VAR_NEW_MAUVILLE_STATE, 2, MauvilleCity_Gym_EventScript_CompletedNewMauville
 * msgbox MauvilleCity_Gym_Text_WattsonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_Wattson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_Wattson")
}

internal object MauvilleCity_Gym_EventScript_Shawn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SHAWN, MauvilleCity_Gym.ShawnIntro, MauvilleCity_Gym.ShawnDefeat))
        return
    ctx.say(MauvilleCity_Gym.ShawnPostBattle)
  }
}

internal object MauvilleCity_Gym_EventScript_Vivian : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_VIVIAN, MauvilleCity_Gym.VivianIntro, MauvilleCity_Gym.VivianDefeat))
        return
    ctx.say(MauvilleCity_Gym.VivianPostBattle)
  }
}

internal object MauvilleCity_Gym_EventScript_Ben : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BEN, MauvilleCity_Gym.BenIntro, MauvilleCity_Gym.BenDefeat))
        return
    ctx.say(MauvilleCity_Gym.BenPostBattle)
  }
}

internal object MauvilleCity_Gym_EventScript_Kirk : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_KIRK, MauvilleCity_Gym.KirkIntro, MauvilleCity_Gym.KirkDefeat))
        return
    ctx.say(MauvilleCity_Gym.KirkPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_MAUVILLE_GYM, MauvilleCity_Gym_EventScript_GymGuidePostVictory
 * msgbox MauvilleCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_GymGuide")
}

internal object MauvilleCity_Gym_EventScript_Angelo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ANGELO, MauvilleCity_Gym.AngeloIntro, MauvilleCity_Gym.AngeloDefeat))
        return
    ctx.say(MauvilleCity_Gym.AngeloPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE03_GET, MauvilleCity_Gym_EventScript_GymStatueCertified
 * goto MauvilleCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE03_GET, MauvilleCity_Gym_EventScript_GymStatueCertified
 * goto MauvilleCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_Gym_EventScript_RightGymStatue")
}

internal val MauvilleCity_GymScripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_Gym_EventScript_Wattson" to MauvilleCity_Gym_EventScript_Wattson,
        "MauvilleCity_Gym_EventScript_Shawn" to MauvilleCity_Gym_EventScript_Shawn,
        "MauvilleCity_Gym_EventScript_Vivian" to MauvilleCity_Gym_EventScript_Vivian,
        "MauvilleCity_Gym_EventScript_Ben" to MauvilleCity_Gym_EventScript_Ben,
        "MauvilleCity_Gym_EventScript_Kirk" to MauvilleCity_Gym_EventScript_Kirk,
        "MauvilleCity_Gym_EventScript_GymGuide" to MauvilleCity_Gym_EventScript_GymGuide,
        "MauvilleCity_Gym_EventScript_Angelo" to MauvilleCity_Gym_EventScript_Angelo,
        "MauvilleCity_Gym_EventScript_LeftGymStatue" to MauvilleCity_Gym_EventScript_LeftGymStatue,
        "MauvilleCity_Gym_EventScript_RightGymStatue" to
            MauvilleCity_Gym_EventScript_RightGymStatue,
    )
