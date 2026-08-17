package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland_House1
import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.script.TutorLines
import de.fiereu.openmmo.server.game.script.TutorMove
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object FourIsland_House1_EventScript_BodySlamTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.moveTutor(
          TutorMove(
              move = 34, flag = KantoFlags.FLAG_TUTOR_BODY_SLAM, from = "the tutor on Four Island"),
          TutorLines(
              teach = Misc.Text_BodySlamTeach,
              declined = Misc.Text_BodySlamDeclined,
              whichMon = Misc.Text_BodySlamWhichMon,
              taught = Misc.Text_BodySlamTaught,
          ),
      )
}

internal object FourIsland_House1_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FourIsland_House1.YoureAwfullyHeavy)
}

internal val FourIsland_House1Scripts: Map<String, Script> =
    mapOf(
        "FourIsland_House1_EventScript_BodySlamTutor" to
            FourIsland_House1_EventScript_BodySlamTutor,
        "FourIsland_House1_EventScript_FatMan" to FourIsland_House1_EventScript_FatMan,
    )
