package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.script.TutorLines
import de.fiereu.openmmo.server.game.script.TutorMove
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object SevenIsland_EventScript_SwordsDanceTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.moveTutor(
          TutorMove(
              move = 14,
              flag = KantoFlags.FLAG_TUTOR_SWORDS_DANCE,
              from = "the tutor on Seven Island"),
          TutorLines(
              teach = Misc.Text_SwordsDanceTeach,
              declined = Misc.Text_SwordsDanceDeclined,
              whichMon = Misc.Text_SwordsDanceWhichMon,
              taught = Misc.Text_SwordsDanceTaught,
          ),
      )
}

internal object SevenIsland_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SevenIsland.IslandsMadeInSevenDays)
}

internal object SevenIsland_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SevenIsland.IslandVirtuallyUntouched)
}

internal object SevenIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SevenIsland.IslandSign)
}

internal val SevenIslandScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_EventScript_SwordsDanceTutor" to SevenIsland_EventScript_SwordsDanceTutor,
        "SevenIsland_EventScript_OldWoman" to SevenIsland_EventScript_OldWoman,
        "SevenIsland_EventScript_Scientist" to SevenIsland_EventScript_Scientist,
        "SevenIsland_EventScript_IslandSign" to SevenIsland_EventScript_IslandSign,
    )
