package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_Gym
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ASHLEY = 655
private const val TRAINER_DARIUS = 803
private const val TRAINER_EDWARDO = 404
private const val TRAINER_FLINT = 654
private const val TRAINER_HUMBERTO = 402
private const val TRAINER_JARED = 401

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WINONA_1, FortreeCity_Gym_Text_WinonaIntro, FortreeCity_Gym_Text_WinonaDefeat, FortreeCity_Gym_EventScript_WinonaDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FortreeCity_Gym_EventScript_WinonaRematch
 * goto_if_unset FLAG_RECEIVED_TM_AERIAL_ACE, FortreeCity_Gym_EventScript_GiveAerialAce2
 * msgbox FortreeCity_Gym_Text_WinonaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Winona : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Winona")
}

internal object FortreeCity_Gym_EventScript_Jared : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JARED, FortreeCity_Gym.JaredIntro, FortreeCity_Gym.JaredDefeat))
        return
    ctx.say(FortreeCity_Gym.JaredPostBattle)
  }
}

internal object FortreeCity_Gym_EventScript_Flint : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FLINT, FortreeCity_Gym.FlintIntro, FortreeCity_Gym.FlintDefeat))
        return
    ctx.say(FortreeCity_Gym.FlintPostBattle)
  }
}

internal object FortreeCity_Gym_EventScript_Ashley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ASHLEY, FortreeCity_Gym.AshleyIntro, FortreeCity_Gym.AshleyDefeat))
        return
    ctx.say(FortreeCity_Gym.AshleyPostBattle)
  }
}

internal object FortreeCity_Gym_EventScript_Edwardo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_EDWARDO, FortreeCity_Gym.EdwardoIntro, FortreeCity_Gym.EdwardoDefeat))
        return
    ctx.say(FortreeCity_Gym.EdwardoPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_FORTREE_GYM, FortreeCity_Gym_EventScript_GymGuidePostVictory
 * msgbox FortreeCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_GymGuide")
}

internal object FortreeCity_Gym_EventScript_Humberto : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HUMBERTO, FortreeCity_Gym.HumbertoIntro, FortreeCity_Gym.HumbertoDefeat))
        return
    ctx.say(FortreeCity_Gym.HumbertoPostBattle)
  }
}

internal object FortreeCity_Gym_EventScript_Darius : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_DARIUS, FortreeCity_Gym.DariusIntro, FortreeCity_Gym.DariusDefeat))
        return
    ctx.say(FortreeCity_Gym.DariusPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE06_GET, FortreeCity_Gym_EventScript_GymStatueCertified
 * goto FortreeCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FortreeCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE06_GET, FortreeCity_Gym_EventScript_GymStatueCertified
 * goto FortreeCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FortreeCity_Gym_EventScript_RightGymStatue")
}

internal val FortreeCity_GymScripts: Map<String, Script> =
    mapOf(
        "FortreeCity_Gym_EventScript_Winona" to FortreeCity_Gym_EventScript_Winona,
        "FortreeCity_Gym_EventScript_Jared" to FortreeCity_Gym_EventScript_Jared,
        "FortreeCity_Gym_EventScript_Flint" to FortreeCity_Gym_EventScript_Flint,
        "FortreeCity_Gym_EventScript_Ashley" to FortreeCity_Gym_EventScript_Ashley,
        "FortreeCity_Gym_EventScript_Edwardo" to FortreeCity_Gym_EventScript_Edwardo,
        "FortreeCity_Gym_EventScript_GymGuide" to FortreeCity_Gym_EventScript_GymGuide,
        "FortreeCity_Gym_EventScript_Humberto" to FortreeCity_Gym_EventScript_Humberto,
        "FortreeCity_Gym_EventScript_Darius" to FortreeCity_Gym_EventScript_Darius,
        "FortreeCity_Gym_EventScript_LeftGymStatue" to FortreeCity_Gym_EventScript_LeftGymStatue,
        "FortreeCity_Gym_EventScript_RightGymStatue" to FortreeCity_Gym_EventScript_RightGymStatue,
    )
