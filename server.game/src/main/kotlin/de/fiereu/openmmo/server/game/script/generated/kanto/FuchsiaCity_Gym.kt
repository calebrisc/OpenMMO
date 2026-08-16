package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_LEADER_KOGA = 418

private const val TRAINER_JUGGLER_KAYDEN = 292
private const val TRAINER_JUGGLER_KIRK = 288
private const val TRAINER_JUGGLER_NATE = 293
private const val TRAINER_JUGGLER_SHAWN = 289
private const val TRAINER_TAMER_EDGAR = 295
private const val TRAINER_TAMER_PHIL = 294

internal object FuchsiaCity_Gym_EventScript_Kayden : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_KAYDEN, FuchsiaCity_Gym.KaydenIntro, FuchsiaCity_Gym.KaydenDefeat))
        return
    ctx.say(FuchsiaCity_Gym.KaydenPostBattle)
  }
}

internal object FuchsiaCity_Gym_EventScript_Shawn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_SHAWN, FuchsiaCity_Gym.ShawnIntro, FuchsiaCity_Gym.ShawnDefeat))
        return
    ctx.say(FuchsiaCity_Gym.ShawnPostBattle)
  }
}

internal object FuchsiaCity_Gym_EventScript_Kirk : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_KIRK, FuchsiaCity_Gym.KirkIntro, FuchsiaCity_Gym.KirkDefeat))
        return
    ctx.say(FuchsiaCity_Gym.KirkPostBattle)
  }
}

internal object FuchsiaCity_Gym_EventScript_Edgar : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TAMER_EDGAR, FuchsiaCity_Gym.EdgarIntro, FuchsiaCity_Gym.EdgarDefeat))
        return
    ctx.say(FuchsiaCity_Gym.EdgarPostBattle)
  }
}

internal object FuchsiaCity_Gym_EventScript_Phil : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TAMER_PHIL, FuchsiaCity_Gym.PhilIntro, FuchsiaCity_Gym.PhilDefeat))
        return
    ctx.say(FuchsiaCity_Gym.PhilPostBattle)
  }
}

internal object FuchsiaCity_Gym_EventScript_Nate : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_NATE, FuchsiaCity_Gym.NateIntro, FuchsiaCity_Gym.NateDefeat))
        return
    ctx.say(FuchsiaCity_Gym.NatePostBattle)
  }
}

internal object FuchsiaCity_Gym_EventScript_Koga : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.leaderBattle(
        TRAINER_LEADER_KOGA, FuchsiaCity_Gym.KogaIntro, FuchsiaCity_Gym.KogaDefeat) {
          ctx.setFlag(KantoFlags.FLAG_DEFEATED_KOGA)
          ctx.setFlag(KantoFlags.FLAG_BADGE05_GET)
          ctx.clearFlag(KantoFlags.FLAG_HIDE_FAME_CHECKER_KOGA_JOURNAL)
        })
        return
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TM06_FROM_KOGA)) {
      ctx.say(FuchsiaCity_Gym.KogaExplainSoulBadge)
      if (!ctx.giveItem(Items.TM06)) {
        ctx.say(FuchsiaCity_Gym.MakeSpaceForThis)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_TM06_FROM_KOGA)
      ctx.say(FuchsiaCity_Gym.ReceivedTM06FromKoga)
      ctx.say(FuchsiaCity_Gym.KogaExplainTM06)
      return
    }
    ctx.say(FuchsiaCity_Gym.KogaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_KOGA, FuchsiaCity_Gym_EventScript_GymGuyPostVictory
 * msgbox FuchsiaCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) =
      gymGuide(
          ctx,
          KantoFlags.FLAG_DEFEATED_KOGA,
          FuchsiaCity_Gym.GymGuyAdvice,
          FuchsiaCity_Gym.GymGuyPostVictory)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE05_GET, FuchsiaCity_Gym_EventScript_GymStatuePostVictory
 * msgbox FuchsiaCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      gymPlaque(
          ctx,
          KantoFlags.FLAG_BADGE05_GET,
          FuchsiaCity_Gym.GymStatue,
          FuchsiaCity_Gym.GymStatuePlayerWon)
}

internal val FuchsiaCity_GymScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_Gym_EventScript_Kayden" to FuchsiaCity_Gym_EventScript_Kayden,
        "FuchsiaCity_Gym_EventScript_Shawn" to FuchsiaCity_Gym_EventScript_Shawn,
        "FuchsiaCity_Gym_EventScript_Kirk" to FuchsiaCity_Gym_EventScript_Kirk,
        "FuchsiaCity_Gym_EventScript_Edgar" to FuchsiaCity_Gym_EventScript_Edgar,
        "FuchsiaCity_Gym_EventScript_Phil" to FuchsiaCity_Gym_EventScript_Phil,
        "FuchsiaCity_Gym_EventScript_Nate" to FuchsiaCity_Gym_EventScript_Nate,
        "FuchsiaCity_Gym_EventScript_Koga" to FuchsiaCity_Gym_EventScript_Koga,
        "FuchsiaCity_Gym_EventScript_GymGuy" to FuchsiaCity_Gym_EventScript_GymGuy,
        "FuchsiaCity_Gym_EventScript_GymStatue" to FuchsiaCity_Gym_EventScript_GymStatue,
    )
