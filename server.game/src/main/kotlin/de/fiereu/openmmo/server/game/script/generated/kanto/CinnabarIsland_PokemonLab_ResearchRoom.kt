package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CinnabarIsland_PokemonLab_ResearchRoom
import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.script.TutorLines
import de.fiereu.openmmo.server.game.script.TutorMove
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_MetronomeTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.moveTutor(
          TutorMove(
              move = 118,
              flag = KantoFlags.FLAG_TUTOR_METRONOME,
              from = "the tutor in the Cinnabar lab"),
          TutorLines(
              teach = Misc.Text_MetronomeTeach,
              declined = Misc.Text_MetronomeDeclined,
              whichMon = Misc.Text_MetronomeWhichMon,
              taught = Misc.Text_MetronomeTaught,
          ),
      )
}

internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland_PokemonLab_ResearchRoom.EeveeCanEvolveIntroThreeMons)
}

internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_AmberPipe : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_ResearchRoom.AnAmberPipe)
}

internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_ResearchRoom.LegendaryBirdEmail)
}

internal val CinnabarIsland_PokemonLab_ResearchRoomScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_MetronomeTutor" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_MetronomeTutor,
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Scientist" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Scientist,
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_AmberPipe" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_AmberPipe,
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Computer" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Computer,
    )
