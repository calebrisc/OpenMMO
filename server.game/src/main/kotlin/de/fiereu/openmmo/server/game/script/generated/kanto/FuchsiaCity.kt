package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object FuchsiaCity_EventScript_Erik : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FuchsiaCity.WheresSara)
}

internal object FuchsiaCity_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FuchsiaCity.ItemBallInThere)
}

internal object FuchsiaCity_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FuchsiaCity.DidYouTrySafariGame)
}

internal object FuchsiaCity_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FuchsiaCity.SafariZoneZooInFront)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_SUBSTITUTE, EventScript_SubstituteTaught
 * msgbox Text_SubstituteTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_SubstituteDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_SubstituteDeclined
 * msgbox Text_SubstituteWhichMon
 * setvar VAR_0x8005, MOVETUTOR_SUBSTITUTE
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_SubstituteDeclined
 * setflag FLAG_TUTOR_SUBSTITUTE
 * goto EventScript_SubstituteTaught
 * end
 * ```
 */
internal object FuchsiaCity_EventScript_SubstituteTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_EventScript_SubstituteTutor")
}

internal object FuchsiaCity_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FuchsiaCity.MyFatherIsGymLeader)
}

internal object FuchsiaCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FuchsiaCity.CitySign)
}

internal object FuchsiaCity_EventScript_SafariZoneSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FuchsiaCity.SafariZoneSign)
}

internal object FuchsiaCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FuchsiaCity.GymSign)
}

internal object FuchsiaCity_EventScript_WardensHomeSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FuchsiaCity.WardensHomeSign)
}

internal object FuchsiaCity_EventScript_VoltorbSign : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setVar(KantoVars.VAR_0x8004, 100)
    ctx.say(FuchsiaCity.VoltorbSign)
  }
}

internal object FuchsiaCity_EventScript_SlowpokeSign : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setVar(KantoVars.VAR_0x8004, 79)
    ctx.say(FuchsiaCity.SlowpokeSign)
  }
}

internal object FuchsiaCity_EventScript_ChanseySign : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setVar(KantoVars.VAR_0x8004, 113)
    ctx.say(FuchsiaCity.ChanseySign)
  }
}

internal object FuchsiaCity_EventScript_KangaskhanSign : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setVar(KantoVars.VAR_0x8004, 115)
    ctx.say(FuchsiaCity.KangaskhanSign)
  }
}

internal object FuchsiaCity_EventScript_FossilMonSign : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_DOME_FOSSIL)) {
      ctx.setVar(KantoVars.VAR_0x8004, 138)
      ctx.say(FuchsiaCity.OmanyteSign)
      return
    }
    ctx.setVar(KantoVars.VAR_0x8004, 140)
    ctx.say(FuchsiaCity.KabutoSign)
  }
}

internal object FuchsiaCity_EventScript_LaprasSign : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setVar(KantoVars.VAR_0x8004, 131)
    ctx.say(FuchsiaCity.LaprasSign)
  }
}

internal object FuchsiaCity_EventScript_SafariGameSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FuchsiaCity.SafariGameSign)
}

internal val FuchsiaCityScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_EventScript_Erik" to FuchsiaCity_EventScript_Erik,
        "FuchsiaCity_EventScript_Youngster" to FuchsiaCity_EventScript_Youngster,
        "FuchsiaCity_EventScript_LittleBoy" to FuchsiaCity_EventScript_LittleBoy,
        "FuchsiaCity_EventScript_OldMan" to FuchsiaCity_EventScript_OldMan,
        "FuchsiaCity_EventScript_SubstituteTutor" to FuchsiaCity_EventScript_SubstituteTutor,
        "FuchsiaCity_EventScript_Lass" to FuchsiaCity_EventScript_Lass,
        "FuchsiaCity_EventScript_CitySign" to FuchsiaCity_EventScript_CitySign,
        "FuchsiaCity_EventScript_SafariZoneSign" to FuchsiaCity_EventScript_SafariZoneSign,
        "FuchsiaCity_EventScript_GymSign" to FuchsiaCity_EventScript_GymSign,
        "FuchsiaCity_EventScript_WardensHomeSign" to FuchsiaCity_EventScript_WardensHomeSign,
        "FuchsiaCity_EventScript_VoltorbSign" to FuchsiaCity_EventScript_VoltorbSign,
        "FuchsiaCity_EventScript_SlowpokeSign" to FuchsiaCity_EventScript_SlowpokeSign,
        "FuchsiaCity_EventScript_ChanseySign" to FuchsiaCity_EventScript_ChanseySign,
        "FuchsiaCity_EventScript_KangaskhanSign" to FuchsiaCity_EventScript_KangaskhanSign,
        "FuchsiaCity_EventScript_FossilMonSign" to FuchsiaCity_EventScript_FossilMonSign,
        "FuchsiaCity_EventScript_LaprasSign" to FuchsiaCity_EventScript_LaprasSign,
        "FuchsiaCity_EventScript_SafariGameSign" to FuchsiaCity_EventScript_SafariGameSign,
    )
