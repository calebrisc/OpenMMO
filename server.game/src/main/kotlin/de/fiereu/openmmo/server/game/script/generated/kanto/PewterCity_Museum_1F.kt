package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.PewterCity_Museum_1F
import de.fiereu.openmmo.dialog.generated.kanto.PokemonJournal
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.script.TutorLines
import de.fiereu.openmmo.server.game.script.TutorMove
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_FACING, DIR_WEST, PewterCity_Museum_1F_EventScript_Scientist1BehindCounter
 * goto_if_eq VAR_FACING, DIR_SOUTH, PewterCity_Museum_1F_EventScript_Scientist1BehindCounter
 * goto_if_eq VAR_FACING, DIR_NORTH, PewterCity_Museum_1F_EventScript_Scientist1BehindCounter
 * msgbox PewterCity_Museum_1F_Text_PleaseEnjoyYourself
 * release
 * end
 * ```
 */
internal object PewterCity_Museum_1F_EventScript_Scientist1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PewterCity_Museum_1F_EventScript_Scientist1")
}

internal object PewterCity_Museum_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Museum_1F.ShouldBeGratefulForLongLife)
}

internal object PewterCity_Museum_1F_EventScript_OldAmberScientist : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_OLD_AMBER)) {
      ctx.say(PewterCity_Museum_1F.GetOldAmberChecked)
      return
    }
    ctx.say(PewterCity_Museum_1F.WantYouToGetAmberExamined)
    if (!ctx.giveItem(Items.OLD_AMBER)) {
      ctx.say(PewterCity_Museum_1F.DontHaveSpaceForThis)
      return
    }
    ctx.setFlag(KantoFlags.FLAG_GOT_OLD_AMBER)
    ctx.despawnInteracted()
    ctx.say(PewterCity_Museum_1F.ReceivedOldAmberFromMan)
  }
}

internal object PewterCity_Museum_1F_EventScript_OldAmber : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PewterCity_Museum_1F.BeautifulPieceOfAmber)
}

internal object PewterCity_Museum_1F_EventScript_Scientist2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Museum_1F.WeHaveTwoFossilsOnExhibit)
}

internal object PewterCity_Museum_1F_EventScript_SeismicTossTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.moveTutor(
          TutorMove(
              move = 69,
              flag = KantoFlags.FLAG_TUTOR_SEISMIC_TOSS,
              from = "the tutor in the Pewter museum"),
          TutorLines(
              teach = Misc.Text_SeismicTossTeach,
              declined = Misc.Text_SeismicTossDeclined,
              whichMon = Misc.Text_SeismicTossWhichMon,
              taught = Misc.Text_SeismicTossTaught,
          ),
      )
}

internal object PewterCity_Museum_1F_EventScript_AerodactylFossil : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setVar(KantoVars.VAR_0x8004, 142)
    ctx.setVar(KantoVars.VAR_0x8005, 10)
    ctx.setVar(KantoVars.VAR_0x8006, 3)
    ctx.say(PewterCity_Museum_1F.AerodactylFossil)
  }
}

internal object PewterCity_Museum_1F_EventScript_KabutopsFossil : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setVar(KantoVars.VAR_0x8004, 141)
    ctx.setVar(KantoVars.VAR_0x8005, 10)
    ctx.setVar(KantoVars.VAR_0x8006, 3)
    ctx.say(PewterCity_Museum_1F.KabutopsFossil)
  }
}

internal object PewterCity_Museum_1F_EventScript_PokemonJournalBrock : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonJournal.SpecialFeatureBrock)
}

internal val PewterCity_Museum_1FScripts: Map<String, Script> =
    mapOf(
        "PewterCity_Museum_1F_EventScript_Scientist1" to
            PewterCity_Museum_1F_EventScript_Scientist1,
        "PewterCity_Museum_1F_EventScript_OldMan" to PewterCity_Museum_1F_EventScript_OldMan,
        "PewterCity_Museum_1F_EventScript_OldAmberScientist" to
            PewterCity_Museum_1F_EventScript_OldAmberScientist,
        "PewterCity_Museum_1F_EventScript_OldAmber" to PewterCity_Museum_1F_EventScript_OldAmber,
        "PewterCity_Museum_1F_EventScript_Scientist2" to
            PewterCity_Museum_1F_EventScript_Scientist2,
        "PewterCity_Museum_1F_EventScript_SeismicTossTutor" to
            PewterCity_Museum_1F_EventScript_SeismicTossTutor,
        "PewterCity_Museum_1F_EventScript_AerodactylFossil" to
            PewterCity_Museum_1F_EventScript_AerodactylFossil,
        "PewterCity_Museum_1F_EventScript_KabutopsFossil" to
            PewterCity_Museum_1F_EventScript_KabutopsFossil,
        "PewterCity_Museum_1F_EventScript_PokemonJournalBrock" to
            PewterCity_Museum_1F_EventScript_PokemonJournalBrock,
    )
