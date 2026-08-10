package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_Summit
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

internal const val GOT_MAGMA_EMBLEM = "hoenn/GOT_MAGMA_EMBLEM"

private const val TRAINER_GRUNT_MT_PYRE_1 = 23
private const val TRAINER_GRUNT_MT_PYRE_2 = 24
private const val TRAINER_GRUNT_MT_PYRE_3 = 25
private const val TRAINER_GRUNT_MT_PYRE_4 = 569

internal object MtPyre_Summit_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.askYesNo(MtPyre_Summit.WillYouHearOutMyTale)) {
      ctx.say(MtPyre_Summit.GroudonKyogreTale)
    }
  }
}

internal object MtPyre_Summit_EventScript_OldLady : Script {
  override suspend fun run(ctx: ScriptContext) {
    when {
      ctx.isFlagSet(HoennFlags.FLAG_SOOTOPOLIS_ARCHIE_MAXIE_LEAVE) ->
          ctx.say(MtPyre_Summit.ThoseTwoMenReturnedOrbs)
      ctx.isFlagSet(HoennFlags.FLAG_KYOGRE_ESCAPED_SEAFLOOR_CAVERN) ->
          ctx.say(MtPyre_Summit.GroudonKyogreAwakened)
      else -> ctx.say(MtPyre_Summit.OrbsHaveBeenTaken)
    }
  }
}

internal object MtPyre_Summit_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_1, MtPyre_Summit.Grunt1Intro, MtPyre_Summit.Grunt1Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt1PostBattle)
  }
}

internal object MtPyre_Summit_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_2, MtPyre_Summit.Grunt2Intro, MtPyre_Summit.Grunt2Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt2PostBattle)
  }
}

internal object MtPyre_Summit_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_3, MtPyre_Summit.Grunt3Intro, MtPyre_Summit.Grunt3Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt3PostBattle)
  }
}

internal object MtPyre_Summit_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_PYRE_4, MtPyre_Summit.Grunt4Intro, MtPyre_Summit.Grunt4Defeat))
        return
    ctx.say(MtPyre_Summit.Grunt4PostBattle)
  }
}

// The orb-theft scene is the summit's on-frame script, registered here like the champion's.
// The Magma Emblem is a story flag because late key items are absent from the item table.
internal object MtPyre_Summit_EventScript_TeamAquaExits : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_MT_PYRE_STATE) != 0) return
    if (!ctx.isFlagSet(HoennFlags.FLAG_TEAM_AQUA_ESCAPED_IN_SUBMARINE)) return
    ctx.sign(MtPyre_Summit.ArchieWeGotTheOrbLetsGo)
    ctx.setFlag(HoennFlags.FLAG_HIDE_MT_PYRE_SUMMIT_ARCHIE)
    ctx.setFlag(HoennFlags.FLAG_HIDE_MT_PYRE_SUMMIT_TEAM_AQUA)
    ctx.setVar(HoennVars.VAR_MT_PYRE_STATE, 1)
    ctx.sign(MtPyre_Summit.BothOrbsTakenMagmaLeftThis)
    ctx.setFlag(GOT_MAGMA_EMBLEM)
    ctx.setFlag(HoennFlags.FLAG_RECEIVED_RED_OR_BLUE_ORB)
    ctx.setFlag(HoennFlags.FLAG_HIDE_JAGGED_PASS_MAGMA_GUARD)
  }
}

internal val MtPyre_SummitScripts: Map<String, Script> =
    mapOf(
        "MtPyre_Summit_EventScript_TeamAquaExits" to MtPyre_Summit_EventScript_TeamAquaExits,
        "MtPyre_Summit_EventScript_OldMan" to MtPyre_Summit_EventScript_OldMan,
        "MtPyre_Summit_EventScript_OldLady" to MtPyre_Summit_EventScript_OldLady,
        "MtPyre_Summit_EventScript_Grunt1" to MtPyre_Summit_EventScript_Grunt1,
        "MtPyre_Summit_EventScript_Grunt2" to MtPyre_Summit_EventScript_Grunt2,
        "MtPyre_Summit_EventScript_Grunt3" to MtPyre_Summit_EventScript_Grunt3,
        "MtPyre_Summit_EventScript_Grunt4" to MtPyre_Summit_EventScript_Grunt4,
    )
