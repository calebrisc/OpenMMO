package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_LEADER_SABRINA = 420

private const val TRAINER_CHANNELER_AMANDA = 462
private const val TRAINER_CHANNELER_STACY = 463
private const val TRAINER_CHANNELER_TASHA = 464
private const val TRAINER_PSYCHIC_CAMERON = 282
private const val TRAINER_PSYCHIC_JOHAN = 280
private const val TRAINER_PSYCHIC_PRESTON = 283
private const val TRAINER_PSYCHIC_TYRON = 281

internal object SaffronCity_Gym_EventScript_Cameron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PSYCHIC_CAMERON, SaffronCity_Gym.CameronIntro, SaffronCity_Gym.CameronDefeat))
        return
    ctx.say(SaffronCity_Gym.CameronPostBattle)
  }
}

internal object SaffronCity_Gym_EventScript_Johan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PSYCHIC_JOHAN, SaffronCity_Gym.JohanIntro, SaffronCity_Gym.JohanDefeat))
        return
    ctx.say(SaffronCity_Gym.JohanPostBattle)
  }
}

internal object SaffronCity_Gym_EventScript_Preston : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PSYCHIC_PRESTON, SaffronCity_Gym.PrestonIntro, SaffronCity_Gym.PrestonDefeat))
        return
    ctx.say(SaffronCity_Gym.PrestonPostBattle)
  }
}

internal object SaffronCity_Gym_EventScript_Amanda : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_AMANDA, SaffronCity_Gym.AmandaIntro, SaffronCity_Gym.AmandaDefeat))
        return
    ctx.say(SaffronCity_Gym.AmandaPostBattle)
  }
}

internal object SaffronCity_Gym_EventScript_Stacy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_STACY, SaffronCity_Gym.StacyIntro, SaffronCity_Gym.StacyDefeat))
        return
    ctx.say(SaffronCity_Gym.StacyPostBattle)
  }
}

internal object SaffronCity_Gym_EventScript_Tasha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_TASHA, SaffronCity_Gym.TashaIntro, SaffronCity_Gym.TashaDefeat))
        return
    ctx.say(SaffronCity_Gym.TashaPostBattle)
  }
}

internal object SaffronCity_Gym_EventScript_Sabrina : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_LEADER_SABRINA)
    if (!ctx.trainerBattleSingle(
        TRAINER_LEADER_SABRINA, SaffronCity_Gym.SabrinaIntro, SaffronCity_Gym.SabrinaDefeat))
        return
    if (firstWin) {
      ctx.setFlag(KantoFlags.FLAG_DEFEATED_SABRINA)
      ctx.setFlag(KantoFlags.FLAG_BADGE06_GET)
      ctx.clearFlag(KantoFlags.FLAG_HIDE_SAFFRON_CITY_POKECENTER_SABRINA_JOURNALS)
    }
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TM04_FROM_SABRINA)) {
      ctx.say(SaffronCity_Gym.ExplainMarshBadgeTakeThis)
      if (!ctx.giveItem(Items.TM04)) {
        ctx.say(SaffronCity_Gym.BagFullOfOtherItems)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_TM04_FROM_SABRINA)
      ctx.say(SaffronCity_Gym.ReceivedTM04FromSabrina)
      ctx.say(SaffronCity_Gym.SabrinaPostBattle)
      return
    }
    ctx.say(SaffronCity_Gym.ExplainTM04)
  }
}

internal object SaffronCity_Gym_EventScript_Tyron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PSYCHIC_TYRON, SaffronCity_Gym.TyronIntro, SaffronCity_Gym.TyronDefeat))
        return
    ctx.say(SaffronCity_Gym.TyronPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_SABRINA, SaffronCity_Gym_EventScript_GymGuyPostVictory
 * msgbox SaffronCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE06_GET, SaffronCity_Gym_EventScript_GymStatuePostVictory
 * msgbox SaffronCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_GymStatue")
}

internal val SaffronCity_GymScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_Gym_EventScript_Cameron" to SaffronCity_Gym_EventScript_Cameron,
        "SaffronCity_Gym_EventScript_Johan" to SaffronCity_Gym_EventScript_Johan,
        "SaffronCity_Gym_EventScript_Preston" to SaffronCity_Gym_EventScript_Preston,
        "SaffronCity_Gym_EventScript_Amanda" to SaffronCity_Gym_EventScript_Amanda,
        "SaffronCity_Gym_EventScript_Stacy" to SaffronCity_Gym_EventScript_Stacy,
        "SaffronCity_Gym_EventScript_Tasha" to SaffronCity_Gym_EventScript_Tasha,
        "SaffronCity_Gym_EventScript_Sabrina" to SaffronCity_Gym_EventScript_Sabrina,
        "SaffronCity_Gym_EventScript_Tyron" to SaffronCity_Gym_EventScript_Tyron,
        "SaffronCity_Gym_EventScript_GymGuy" to SaffronCity_Gym_EventScript_GymGuy,
        "SaffronCity_Gym_EventScript_GymStatue" to SaffronCity_Gym_EventScript_GymStatue,
    )
