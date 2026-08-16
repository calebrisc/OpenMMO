package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.Route4
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val MOVE_MEGA_PUNCH = 5
private const val MOVE_MEGA_KICK = 25

private const val TRAINER_LASS_CRISSY = 119

internal object Route4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route4.TrippedOverGeodude)
}

internal object Route4_EventScript_Crissy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_CRISSY, Route4.CrissyIntro, Route4.CrissyDefeat))
        return
    ctx.say(Route4.CrissyPostBattle)
  }
}

internal object Route4_EventScript_ItemTM05 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM05)
}

internal object Route4_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route4.PeopleLikeAndRespectBrock)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_MEGA_PUNCH, EventScript_MegaPunchTaught
 * msgbox Text_MegaPunchTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaPunchDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaPunchDeclined
 * msgbox Text_MegaPunchWhichMon
 * setvar VAR_0x8005, MOVETUTOR_MEGA_PUNCH
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_MegaPunchDeclined
 * setflag FLAG_TUTOR_MEGA_PUNCH
 * goto EventScript_MegaPunchTaught
 * end
 * ```
 */
internal object Route4_EventScript_MegaPunchTutor : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_TUTOR_MEGA_PUNCH)) {
      ctx.say(Misc.Text_MegaPunchTaught)
      return
    }
    if (!ctx.askYesNo(Misc.Text_MegaPunchTeach)) {
      ctx.say(Misc.Text_MegaPunchDeclined)
      return
    }
    ctx.say(Misc.Text_MegaPunchWhichMon)
    // The pick happens afterwards, at the player's own party screen, and the flag is spent by
    // something actually learning the move rather than by the offer being made.
    ctx.offerMove(MOVE_MEGA_PUNCH, "the tutor on Route 4", KantoFlags.FLAG_TUTOR_MEGA_PUNCH)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_MEGA_KICK, EventScript_MegaKickTaught
 * msgbox Text_MegaKickTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaKickDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaKickDeclined
 * msgbox Text_MegaKickWhichMon
 * setvar VAR_0x8005, MOVETUTOR_MEGA_KICK
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_MegaKickDeclined
 * setflag FLAG_TUTOR_MEGA_KICK
 * goto EventScript_MegaKickTaught
 * end
 * ```
 */
internal object Route4_EventScript_MegaKickTutor : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_TUTOR_MEGA_KICK)) {
      ctx.say(Misc.Text_MegaKickTaught)
      return
    }
    if (!ctx.askYesNo(Misc.Text_MegaKickTeach)) {
      ctx.say(Misc.Text_MegaKickDeclined)
      return
    }
    ctx.say(Misc.Text_MegaKickWhichMon)
    // The pick happens afterwards, at the player's own party screen, and the flag is spent by
    // something actually learning the move rather than by the offer being made.
    ctx.offerMove(MOVE_MEGA_KICK, "the tutor on Route 4", KantoFlags.FLAG_TUTOR_MEGA_KICK)
  }
}

internal object Route4_EventScript_MtMoonSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route4.MtMoonEntrance)
}

internal object Route4_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route4.RouteSign)
}

internal val Route4Scripts: Map<String, Script> =
    mapOf(
        "Route4_EventScript_Woman" to Route4_EventScript_Woman,
        "Route4_EventScript_Crissy" to Route4_EventScript_Crissy,
        "Route4_EventScript_ItemTM05" to Route4_EventScript_ItemTM05,
        "Route4_EventScript_Boy" to Route4_EventScript_Boy,
        "Route4_EventScript_MegaPunchTutor" to Route4_EventScript_MegaPunchTutor,
        "Route4_EventScript_MegaKickTutor" to Route4_EventScript_MegaKickTutor,
        "Route4_EventScript_MtMoonSign" to Route4_EventScript_MtMoonSign,
        "Route4_EventScript_RouteSign" to Route4_EventScript_RouteSign,
    )
