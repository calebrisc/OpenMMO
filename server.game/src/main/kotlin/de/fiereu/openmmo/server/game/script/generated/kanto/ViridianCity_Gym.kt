package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val TRAINER_LEADER_GIOVANNI = 350

private const val TRAINER_BLACK_BELT_ATSUSHI = 322
private const val TRAINER_BLACK_BELT_KIYO = 323
private const val TRAINER_BLACK_BELT_TAKASHI = 324
private const val TRAINER_COOLTRAINER_SAMUEL = 392
private const val TRAINER_COOLTRAINER_WARREN = 401
private const val TRAINER_COOLTRAINER_YUJI = 400
private const val TRAINER_TAMER_COLE = 297
private const val TRAINER_TAMER_JASON = 296

internal object ViridianCity_Gym_EventScript_Takashi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BLACK_BELT_TAKASHI, ViridianCity_Gym.TakashiIntro, ViridianCity_Gym.TakashiDefeat))
        return
    ctx.say(ViridianCity_Gym.TakashiPostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Yuji : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_YUJI, ViridianCity_Gym.YujiIntro, ViridianCity_Gym.YujiDefeat))
        return
    ctx.say(ViridianCity_Gym.YujiPostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Atsushi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BLACK_BELT_ATSUSHI, ViridianCity_Gym.AtsushiIntro, ViridianCity_Gym.AtsushiDefeat))
        return
    ctx.say(ViridianCity_Gym.AtsushiPostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Jason : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TAMER_JASON, ViridianCity_Gym.JasonIntro, ViridianCity_Gym.JasonDefeat))
        return
    ctx.say(ViridianCity_Gym.JasonPostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Cole : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TAMER_COLE, ViridianCity_Gym.ColeIntro, ViridianCity_Gym.ColeDefeat))
        return
    ctx.say(ViridianCity_Gym.ColePostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Kiyo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BLACK_BELT_KIYO, ViridianCity_Gym.KiyoIntro, ViridianCity_Gym.KiyoDefeat))
        return
    ctx.say(ViridianCity_Gym.KiyoPostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Samuel : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_SAMUEL, ViridianCity_Gym.SamuelIntro, ViridianCity_Gym.SamuelDefeat))
        return
    ctx.say(ViridianCity_Gym.SamuelPostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Giovanni : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_LEADER_GIOVANNI)
    if (!ctx.trainerBattleSingle(TRAINER_LEADER_GIOVANNI, ViridianCity_Gym.GiovanniIntro)) return
    if (firstWin) {
      ctx.setFlag(KantoFlags.FLAG_DEFEATED_LEADER_GIOVANNI)
      ctx.setFlag(KantoFlags.FLAG_BADGE08_GET)
      ctx.setFlag(KantoFlags.FLAG_HIDE_MISC_KANTO_ROCKETS)
      ctx.setVar(KantoVars.VAR_MAP_SCENE_ROUTE22, 3)
    }
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TM26_FROM_GIOVANNI)) {
      ctx.say(ViridianCity_Gym.ExplainEarthBadgeTakeThis)
      if (!ctx.giveItem(Items.TM26)) {
        ctx.say(ViridianCity_Gym.YouDoNotHaveSpace)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_TM26_FROM_GIOVANNI)
      ctx.say(ViridianCity_Gym.ReceivedTM26FromGiovanni)
      ctx.say(ViridianCity_Gym.ExplainTM26)
      return
    }
    ctx.say(ViridianCity_Gym.GiovanniPostBattle)
  }
}

internal object ViridianCity_Gym_EventScript_Warren : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_WARREN, ViridianCity_Gym.WarrenIntro, ViridianCity_Gym.WarrenDefeat))
        return
    ctx.say(ViridianCity_Gym.WarrenPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_LEADER_GIOVANNI, ViridianCity_Gym_EventScript_GymGuyPostVictory
 * msgbox ViridianCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE08_GET, ViridianCity_Gym_EventScript_GymStatuePostVictory
 * msgbox ViridianCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_GymStatue")
}

internal val ViridianCity_GymScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_Gym_EventScript_Takashi" to ViridianCity_Gym_EventScript_Takashi,
        "ViridianCity_Gym_EventScript_Yuji" to ViridianCity_Gym_EventScript_Yuji,
        "ViridianCity_Gym_EventScript_Atsushi" to ViridianCity_Gym_EventScript_Atsushi,
        "ViridianCity_Gym_EventScript_Jason" to ViridianCity_Gym_EventScript_Jason,
        "ViridianCity_Gym_EventScript_Cole" to ViridianCity_Gym_EventScript_Cole,
        "ViridianCity_Gym_EventScript_Kiyo" to ViridianCity_Gym_EventScript_Kiyo,
        "ViridianCity_Gym_EventScript_Samuel" to ViridianCity_Gym_EventScript_Samuel,
        "ViridianCity_Gym_EventScript_Giovanni" to ViridianCity_Gym_EventScript_Giovanni,
        "ViridianCity_Gym_EventScript_Warren" to ViridianCity_Gym_EventScript_Warren,
        "ViridianCity_Gym_EventScript_GymGuy" to ViridianCity_Gym_EventScript_GymGuy,
        "ViridianCity_Gym_EventScript_GymStatue" to ViridianCity_Gym_EventScript_GymStatue,
    )
