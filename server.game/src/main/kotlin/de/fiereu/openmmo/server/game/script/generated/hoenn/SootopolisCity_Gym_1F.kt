package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity_Gym_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

private const val TRAINER_JUAN_1 = 272

internal object SootopolisCity_Gym_1F_EventScript_Juan : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_JUAN_1)
    if (!ctx.trainerBattleSingle(
        TRAINER_JUAN_1, SootopolisCity_Gym_1F.JuanIntro, SootopolisCity_Gym_1F.JuanDefeat))
        return
    if (firstWin) {
      ctx.setFlag(HoennFlags.FLAG_DEFEATED_SOOTOPOLIS_GYM)
      ctx.setFlag(HoennFlags.FLAG_BADGE08_GET)
      ctx.setFlag(HoennFlags.FLAG_HIDE_SOOTOPOLIS_CITY_RESIDENTS)
    }
    if (!ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_TM_WATER_PULSE)) {
      if (!ctx.giveItem(Items.TM03)) return
      ctx.setFlag(HoennFlags.FLAG_RECEIVED_TM_WATER_PULSE)
    }
    ctx.say(SootopolisCity_Gym_1F.JuanPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_SOOTOPOLIS_GYM, SootopolisCity_Gym_1F_EventScript_GymGuidePostVictory
 * msgbox SootopolisCity_Gym_1F_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_Gym_1F_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_Gym_1F_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE08_GET, SootopolisCity_Gym_1F_EventScript_GymStatueCertified
 * goto SootopolisCity_Gym_1F_EventScript_GymStatue
 * end
 * ```
 */
internal object SootopolisCity_Gym_1F_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_Gym_1F_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE08_GET, SootopolisCity_Gym_1F_EventScript_GymStatueCertified
 * goto SootopolisCity_Gym_1F_EventScript_GymStatue
 * end
 * ```
 */
internal object SootopolisCity_Gym_1F_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_Gym_1F_EventScript_RightGymStatue")
}

internal val SootopolisCity_Gym_1FScripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_Gym_1F_EventScript_Juan" to SootopolisCity_Gym_1F_EventScript_Juan,
        "SootopolisCity_Gym_1F_EventScript_GymGuide" to SootopolisCity_Gym_1F_EventScript_GymGuide,
        "SootopolisCity_Gym_1F_EventScript_LeftGymStatue" to
            SootopolisCity_Gym_1F_EventScript_LeftGymStatue,
        "SootopolisCity_Gym_1F_EventScript_RightGymStatue" to
            SootopolisCity_Gym_1F_EventScript_RightGymStatue,
    )
