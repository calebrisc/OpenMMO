package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_LEADER_MISTY = 415

private const val TRAINER_PICNICKER_DIANA = 150
private const val TRAINER_SWIMMER_MALE_LUIS = 234

internal object CeruleanCity_Gym_EventScript_Luis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_LUIS, CeruleanCity_Gym.LuisIntro, CeruleanCity_Gym.LuisDefeat))
        return
    ctx.say(CeruleanCity_Gym.LuisPostBattle)
  }
}

internal object CeruleanCity_Gym_EventScript_Diana : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_DIANA, CeruleanCity_Gym.DianaIntro, CeruleanCity_Gym.DianaDefeat))
        return
    ctx.say(CeruleanCity_Gym.DianaPostBattle)
  }
}

internal object CeruleanCity_Gym_EventScript_Misty : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_LEADER_MISTY)
    if (!ctx.trainerBattleSingle(
        TRAINER_LEADER_MISTY, CeruleanCity_Gym.MistyIntro, CeruleanCity_Gym.MistyDefeat))
        return
    if (firstWin) {
      ctx.setFlag(KantoFlags.FLAG_DEFEATED_MISTY)
      ctx.setFlag(KantoFlags.FLAG_BADGE02_GET)
    }
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_TM03_FROM_MISTY)) {
      ctx.say(CeruleanCity_Gym.ExplainCascadeBadge)
      if (!ctx.giveItem(Items.TM03)) {
        ctx.say(CeruleanCity_Gym.BetterMakeRoomForThis)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_TM03_FROM_MISTY)
      ctx.say(CeruleanCity_Gym.ReceivedTM03FromMisty)
      ctx.say(CeruleanCity_Gym.ExplainTM03)
      return
    }
    ctx.say(CeruleanCity_Gym.ExplainTM03)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_MISTY, CeruleanCity_Gym_EventScript_GymGuyPostVictory
 * msgbox CeruleanCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object CeruleanCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE02_GET, CeruleanCity_Gym_EventScript_GymStatuePostVictory
 * msgbox CeruleanCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object CeruleanCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Gym_EventScript_GymStatue")
}

internal val CeruleanCity_GymScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_Gym_EventScript_Luis" to CeruleanCity_Gym_EventScript_Luis,
        "CeruleanCity_Gym_EventScript_Diana" to CeruleanCity_Gym_EventScript_Diana,
        "CeruleanCity_Gym_EventScript_Misty" to CeruleanCity_Gym_EventScript_Misty,
        "CeruleanCity_Gym_EventScript_GymGuy" to CeruleanCity_Gym_EventScript_GymGuy,
        "CeruleanCity_Gym_EventScript_GymStatue" to CeruleanCity_Gym_EventScript_GymStatue,
    )
