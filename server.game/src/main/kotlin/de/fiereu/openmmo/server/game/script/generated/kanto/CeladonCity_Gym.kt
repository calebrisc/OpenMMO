package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_LEADER_ERIKA = 417

private const val TRAINER_BEAUTY_BRIDGET = 265
private const val TRAINER_BEAUTY_LORI = 267
private const val TRAINER_BEAUTY_TAMIA = 266
private const val TRAINER_COOLTRAINER_MARY = 402
private const val TRAINER_LASS_KAY = 132
private const val TRAINER_LASS_LISA = 133
private const val TRAINER_PICNICKER_TINA = 160

internal object CeladonCity_Gym_EventScript_Kay : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LASS_KAY, CeladonCity_Gym.KayIntro, CeladonCity_Gym.KayDefeat))
        return
    ctx.say(CeladonCity_Gym.KayPostBattle)
  }
}

internal object CeladonCity_Gym_EventScript_Bridget : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BEAUTY_BRIDGET, CeladonCity_Gym.BridgetIntro, CeladonCity_Gym.BridgetDefeat))
        return
    ctx.say(CeladonCity_Gym.BridgetPostBattle)
  }
}

internal object CeladonCity_Gym_EventScript_Tina : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_TINA, CeladonCity_Gym.TinaIntro, CeladonCity_Gym.TinaDefeat))
        return
    ctx.say(CeladonCity_Gym.TinaPostBattle)
  }
}

internal object CeladonCity_Gym_EventScript_Tamia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BEAUTY_TAMIA, CeladonCity_Gym.TamiaIntro, CeladonCity_Gym.TamiaDefeat))
        return
    ctx.say(CeladonCity_Gym.TamiaPostBattle)
  }
}

internal object CeladonCity_Gym_EventScript_Lori : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BEAUTY_LORI, CeladonCity_Gym.LoriIntro, CeladonCity_Gym.LoriDefeat))
        return
    ctx.say(CeladonCity_Gym.LoriPostBattle)
  }
}

internal object CeladonCity_Gym_EventScript_Lisa : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LASS_LISA, CeladonCity_Gym.LisaIntro, CeladonCity_Gym.LisaDefeat))
        return
    ctx.say(CeladonCity_Gym.LisaPostBattle)
  }
}

internal object CeladonCity_Gym_EventScript_Erika : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.leaderBattle(TRAINER_LEADER_ERIKA, null, CeladonCity_Gym.ErikaDefeat) {
      ctx.setFlag(KantoFlags.FLAG_DEFEATED_ERIKA)
      ctx.setFlag(KantoFlags.FLAG_BADGE04_GET)
      ctx.clearFlag(KantoFlags.FLAG_HIDE_FAME_CHECKER_ERIKA_JOURNALS)
    })
        return
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TM19_FROM_ERIKA)) {
      ctx.say(CeladonCity_Gym.ExplainRainbowBadgeTakeThis)
      if (!ctx.giveItem(Items.TM19)) {
        ctx.say(CeladonCity_Gym.ShouldMakeRoomForThis)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_TM19_FROM_ERIKA)
      ctx.say(CeladonCity_Gym.ReceivedTM19FromErika)
      ctx.say(CeladonCity_Gym.ExplainTM19)
      return
    }
    ctx.say(CeladonCity_Gym.ErikaPostBattle)
  }
}

internal object CeladonCity_Gym_EventScript_Mary : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_MARY, CeladonCity_Gym.MaryIntro, CeladonCity_Gym.MaryDefeat))
        return
    ctx.say(CeladonCity_Gym.MaryPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE04_GET, CeladonCity_Gym_EventScript_GymStatuePostVictory
 * msgbox CeladonCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object CeladonCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeladonCity_Gym_EventScript_GymStatue")
}

internal val CeladonCity_GymScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Gym_EventScript_Kay" to CeladonCity_Gym_EventScript_Kay,
        "CeladonCity_Gym_EventScript_Bridget" to CeladonCity_Gym_EventScript_Bridget,
        "CeladonCity_Gym_EventScript_Tina" to CeladonCity_Gym_EventScript_Tina,
        "CeladonCity_Gym_EventScript_Tamia" to CeladonCity_Gym_EventScript_Tamia,
        "CeladonCity_Gym_EventScript_Lori" to CeladonCity_Gym_EventScript_Lori,
        "CeladonCity_Gym_EventScript_Lisa" to CeladonCity_Gym_EventScript_Lisa,
        "CeladonCity_Gym_EventScript_Erika" to CeladonCity_Gym_EventScript_Erika,
        "CeladonCity_Gym_EventScript_Mary" to CeladonCity_Gym_EventScript_Mary,
        "CeladonCity_Gym_EventScript_GymStatue" to CeladonCity_Gym_EventScript_GymStatue,
    )
