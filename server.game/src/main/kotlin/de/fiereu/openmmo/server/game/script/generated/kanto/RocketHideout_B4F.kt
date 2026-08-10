package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.RocketHideout_B4F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_TEAM_ROCKET_GRUNT_16 = 366
private const val TRAINER_TEAM_ROCKET_GRUNT_17 = 367
private const val TRAINER_TEAM_ROCKET_GRUNT_18 = 368
private const val TRAINER_BOSS_GIOVANNI = 348

// The scope is a FireRed-only bag item the Emerald-sourced table lacks, so possession is a flag.
internal const val GOT_SILPH_SCOPE = "kanto/GOT_SILPH_SCOPE"

// The barrier to Giovanni is a metatile the protocol cannot change, so once both door guards
// are down the script walks the player through to the boss's room.
private suspend fun doorGruntsCleared(ctx: ScriptContext) {
  if (ctx.isTrainerDefeated(TRAINER_TEAM_ROCKET_GRUNT_16) &&
      ctx.isTrainerDefeated(TRAINER_TEAM_ROCKET_GRUNT_17)) {
    ctx.send(notice("The barrier to the boss's room released!"))
    ctx.repositionSelf(19, 6, Direction.UP)
  }
}

internal object RocketHideout_B4F_EventScript_Giovanni : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isTrainerDefeated(TRAINER_BOSS_GIOVANNI)) return
    ctx.say(RocketHideout_B4F.GiovanniIntro)
    if (!ctx.trainerBattleSingle(TRAINER_BOSS_GIOVANNI, null, RocketHideout_B4F.GiovanniDefeat))
        return
    ctx.say(RocketHideout_B4F.GiovanniPostBattle)
    ctx.despawnInteracted()
    ctx.setFlag(KantoFlags.FLAG_HIDE_HIDEOUT_GIOVANNI)
    ctx.clearFlag(KantoFlags.FLAG_HIDE_SILPH_SCOPE)
    ctx.setFlag(KantoFlags.FLAG_HIDE_CELADON_ROCKETS)
  }
}

internal object RocketHideout_B4F_EventScript_SilphScope : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(GOT_SILPH_SCOPE)
    ctx.despawnInteracted()
    ctx.send(notice("Found the SILPH SCOPE!"))
  }
}

internal object RocketHideout_B4F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_18,
        RocketHideout_B4F.Grunt1Intro,
        RocketHideout_B4F.Grunt1Defeat))
        return
    ctx.say(RocketHideout_B4F.Grunt1PostBattle)
    ctx.clearFlag(KantoFlags.FLAG_HIDE_LIFT_KEY)
  }
}

internal object RocketHideout_B4F_EventScript_LiftKey : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(KantoFlags.FLAG_CAN_USE_ROCKET_HIDEOUT_LIFT)

    ctx.despawnInteracted()
    ctx.send(notice("Found the LIFT KEY!"))
  }
}

internal object RocketHideout_B4F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_17,
        RocketHideout_B4F.Grunt3Intro,
        RocketHideout_B4F.Grunt3Defeat))
        return
    doorGruntsCleared(ctx)
  }
}

internal object RocketHideout_B4F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_16,
        RocketHideout_B4F.Grunt2Intro,
        RocketHideout_B4F.Grunt2Defeat))
        return
    doorGruntsCleared(ctx)
  }
}

internal object RocketHideout_B4F_EventScript_ItemTM49 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM49)
}

internal object RocketHideout_B4F_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ETHER)
}

internal object RocketHideout_B4F_EventScript_ItemCalcium : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CALCIUM)
}

internal val RocketHideout_B4FScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_B4F_EventScript_Giovanni" to RocketHideout_B4F_EventScript_Giovanni,
        "RocketHideout_B4F_EventScript_SilphScope" to RocketHideout_B4F_EventScript_SilphScope,
        "RocketHideout_B4F_EventScript_Grunt1" to RocketHideout_B4F_EventScript_Grunt1,
        "RocketHideout_B4F_EventScript_LiftKey" to RocketHideout_B4F_EventScript_LiftKey,
        "RocketHideout_B4F_EventScript_Grunt3" to RocketHideout_B4F_EventScript_Grunt3,
        "RocketHideout_B4F_EventScript_Grunt2" to RocketHideout_B4F_EventScript_Grunt2,
        "RocketHideout_B4F_EventScript_ItemTM49" to RocketHideout_B4F_EventScript_ItemTM49,
        "RocketHideout_B4F_EventScript_ItemMaxEther" to RocketHideout_B4F_EventScript_ItemMaxEther,
        "RocketHideout_B4F_EventScript_ItemCalcium" to RocketHideout_B4F_EventScript_ItemCalcium,
    )
