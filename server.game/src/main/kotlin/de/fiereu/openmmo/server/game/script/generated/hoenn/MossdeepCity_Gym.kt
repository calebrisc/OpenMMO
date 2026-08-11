package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MossdeepCity_Gym
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

private const val TRAINER_TATE_AND_LIZA_1 = 271

private const val TRAINER_BLAKE = 235
private const val TRAINER_CLIFFORD = 584
private const val TRAINER_HANNAH = 244
private const val TRAINER_KATHLEEN = 583
private const val TRAINER_MACEY = 591
private const val TRAINER_MAURA = 246
private const val TRAINER_NATE = 582
private const val TRAINER_NICHOLAS = 585
private const val TRAINER_PRESTON = 233
private const val TRAINER_SAMANTHA = 245
private const val TRAINER_SYLVIA = 575
private const val TRAINER_VIRGIL = 234

internal object MossdeepCity_Gym_EventScript_TateAndLiza : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.leaderBattle(
        TRAINER_TATE_AND_LIZA_1,
        MossdeepCity_Gym.TateAndLizaIntro,
        MossdeepCity_Gym.TateAndLizaDefeat) {
          ctx.setFlag(HoennFlags.FLAG_DEFEATED_MOSSDEEP_GYM)
          ctx.setFlag(HoennFlags.FLAG_BADGE07_GET)
          ctx.setFlag(HoennFlags.FLAG_HIDE_AQUA_HIDEOUT_GRUNTS)
        })
        return
    if (!ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_TM_CALM_MIND)) {
      if (!ctx.giveItem(Items.TM04)) return
      ctx.setFlag(HoennFlags.FLAG_RECEIVED_TM_CALM_MIND)
    }
    ctx.say(MossdeepCity_Gym.TateAndLizaPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Preston : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PRESTON, MossdeepCity_Gym.PrestonIntro, MossdeepCity_Gym.PrestonDefeat))
        return
    ctx.say(MossdeepCity_Gym.PrestonPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Blake : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BLAKE, MossdeepCity_Gym.BlakeIntro, MossdeepCity_Gym.BlakeDefeat))
        return
    ctx.say(MossdeepCity_Gym.BlakePostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Maura : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_MAURA, MossdeepCity_Gym.MauraIntro, MossdeepCity_Gym.MauraDefeat))
        return
    ctx.say(MossdeepCity_Gym.MauraPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Samantha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SAMANTHA, MossdeepCity_Gym.SamanthaIntro, MossdeepCity_Gym.SamanthaDefeat))
        return
    ctx.say(MossdeepCity_Gym.SamanthaPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Virgil : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_VIRGIL, MossdeepCity_Gym.VirgilIntro, MossdeepCity_Gym.VirgilDefeat))
        return
    ctx.say(MossdeepCity_Gym.VirgilPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Hannah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HANNAH, MossdeepCity_Gym.HannahIntro, MossdeepCity_Gym.HannahDefeat))
        return
    ctx.say(MossdeepCity_Gym.HannahPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_MOSSDEEP_GYM, MossdeepCity_Gym_EventScript_GymGuidePostVictory
 * msgbox MossdeepCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_GymGuide")
}

internal object MossdeepCity_Gym_EventScript_Nate : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_NATE, MossdeepCity_Gym.NateIntro, MossdeepCity_Gym.NateDefeat))
        return
    ctx.say(MossdeepCity_Gym.NatePostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Sylvia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SYLVIA, MossdeepCity_Gym.SylviaIntro, MossdeepCity_Gym.SylviaDefeat))
        return
    ctx.say(MossdeepCity_Gym.SylviaPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Clifford : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CLIFFORD, MossdeepCity_Gym.CliffordIntro, MossdeepCity_Gym.CliffordDefeat))
        return
    ctx.say(MossdeepCity_Gym.CliffordPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Macey : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_MACEY, MossdeepCity_Gym.MaceyIntro, MossdeepCity_Gym.MaceyDefeat))
        return
    ctx.say(MossdeepCity_Gym.MaceyPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Kathleen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_KATHLEEN, MossdeepCity_Gym.KathleenIntro, MossdeepCity_Gym.KathleenDefeat))
        return
    ctx.say(MossdeepCity_Gym.KathleenPostBattle)
  }
}

internal object MossdeepCity_Gym_EventScript_Nicholas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_NICHOLAS, MossdeepCity_Gym.NicholasIntro, MossdeepCity_Gym.NicholasDefeat))
        return
    ctx.say(MossdeepCity_Gym.NicholasPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE07_GET, MossdeepCity_Gym_EventScript_GymStatueCertified
 * goto MossdeepCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE07_GET, MossdeepCity_Gym_EventScript_GymStatueCertified
 * goto MossdeepCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_Gym_EventScript_RightGymStatue")
}

internal val MossdeepCity_GymScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_Gym_EventScript_TateAndLiza" to MossdeepCity_Gym_EventScript_TateAndLiza,
        "MossdeepCity_Gym_EventScript_Preston" to MossdeepCity_Gym_EventScript_Preston,
        "MossdeepCity_Gym_EventScript_Blake" to MossdeepCity_Gym_EventScript_Blake,
        "MossdeepCity_Gym_EventScript_Maura" to MossdeepCity_Gym_EventScript_Maura,
        "MossdeepCity_Gym_EventScript_Samantha" to MossdeepCity_Gym_EventScript_Samantha,
        "MossdeepCity_Gym_EventScript_Virgil" to MossdeepCity_Gym_EventScript_Virgil,
        "MossdeepCity_Gym_EventScript_Hannah" to MossdeepCity_Gym_EventScript_Hannah,
        "MossdeepCity_Gym_EventScript_GymGuide" to MossdeepCity_Gym_EventScript_GymGuide,
        "MossdeepCity_Gym_EventScript_Nate" to MossdeepCity_Gym_EventScript_Nate,
        "MossdeepCity_Gym_EventScript_Sylvia" to MossdeepCity_Gym_EventScript_Sylvia,
        "MossdeepCity_Gym_EventScript_Clifford" to MossdeepCity_Gym_EventScript_Clifford,
        "MossdeepCity_Gym_EventScript_Macey" to MossdeepCity_Gym_EventScript_Macey,
        "MossdeepCity_Gym_EventScript_Kathleen" to MossdeepCity_Gym_EventScript_Kathleen,
        "MossdeepCity_Gym_EventScript_Nicholas" to MossdeepCity_Gym_EventScript_Nicholas,
        "MossdeepCity_Gym_EventScript_LeftGymStatue" to MossdeepCity_Gym_EventScript_LeftGymStatue,
        "MossdeepCity_Gym_EventScript_RightGymStatue" to
            MossdeepCity_Gym_EventScript_RightGymStatue,
    )
