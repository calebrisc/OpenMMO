package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PewterCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val TRAINER_LEADER_BROCK = 414

private const val TRAINER_CAMPER_LIAM = 142

internal object PewterCity_Gym_EventScript_Brock : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_LEADER_BROCK)
    if (!ctx.trainerBattleSingle(TRAINER_LEADER_BROCK, PewterCity_Gym.BrockIntro)) return
    if (firstWin) {
      ctx.setFlag(KantoFlags.FLAG_DEFEATED_BROCK)
      ctx.setFlag(KantoFlags.FLAG_BADGE01_GET)
      ctx.setVar(KantoVars.VAR_MAP_SCENE_PEWTER_CITY, 1)
      ctx.setFlag(KantoFlags.FLAG_HIDE_PEWTER_CITY_GYM_GUIDE)
      ctx.clearFlag(KantoFlags.FLAG_HIDE_PEWTER_CITY_RUNNING_SHOES_GUY)
    }
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TM39_FROM_BROCK)) {
      ctx.say(PewterCity_Gym.TakeThisWithYou)
      if (!ctx.giveItem(Items.TM39)) {
        ctx.say(PewterCity_Gym.DontHaveRoomForThis)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_TM39_FROM_BROCK)
      ctx.say(PewterCity_Gym.ReceivedTM39FromBrock)
      ctx.say(PewterCity_Gym.ExplainTM39)
      return
    }
    ctx.say(PewterCity_Gym.BrockPostBattle)
  }
}

internal object PewterCity_Gym_EventScript_Liam : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CAMPER_LIAM, PewterCity_Gym.LiamIntro, PewterCity_Gym.LiamDefeat))
        return
    ctx.say(PewterCity_Gym.LiamPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_BROCK, PewterCity_Gym_EventScript_GymGuyPostVictory
 * msgbox PewterCity_Gym_Text_LetMeTakeYouToTheTop, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PewterCity_Gym_EventScript_GymGuyTakeMeToTop
 * goto_if_eq VAR_RESULT, NO, PewterCity_Gym_EventScript_GymGuyDontTakeMeToTop
 * end
 * ```
 */
internal object PewterCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE01_GET, PewterCity_Gym_EventScript_GymStatuePostVictory
 * msgbox PewterCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object PewterCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_Gym_EventScript_GymStatue")
}

internal val PewterCity_GymScripts: Map<String, Script> =
    mapOf(
        "PewterCity_Gym_EventScript_Brock" to PewterCity_Gym_EventScript_Brock,
        "PewterCity_Gym_EventScript_Liam" to PewterCity_Gym_EventScript_Liam,
        "PewterCity_Gym_EventScript_GymGuy" to PewterCity_Gym_EventScript_GymGuy,
        "PewterCity_Gym_EventScript_GymStatue" to PewterCity_Gym_EventScript_GymStatue,
    )
