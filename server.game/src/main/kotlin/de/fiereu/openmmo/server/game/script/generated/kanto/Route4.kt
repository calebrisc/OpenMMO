package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.Route4
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.script.TutorLines
import de.fiereu.openmmo.server.game.script.TutorMove
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

internal object Route4_EventScript_MegaPunchTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.moveTutor(
          TutorMove(
              move = MOVE_MEGA_PUNCH,
              flag = KantoFlags.FLAG_TUTOR_MEGA_PUNCH,
              from = "the tutor on Route 4"),
          TutorLines(
              teach = Misc.Text_MegaPunchTeach,
              declined = Misc.Text_MegaPunchDeclined,
              whichMon = Misc.Text_MegaPunchWhichMon,
              taught = Misc.Text_MegaPunchTaught,
          ),
      )
}

internal object Route4_EventScript_MegaKickTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.moveTutor(
          TutorMove(
              move = MOVE_MEGA_KICK,
              flag = KantoFlags.FLAG_TUTOR_MEGA_KICK,
              from = "the tutor on Route 4"),
          TutorLines(
              teach = Misc.Text_MegaKickTeach,
              declined = Misc.Text_MegaKickDeclined,
              whichMon = Misc.Text_MegaKickWhichMon,
              taught = Misc.Text_MegaKickTaught,
          ),
      )
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
