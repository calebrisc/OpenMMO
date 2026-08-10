package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_LostCave_Room1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_RUIN_MANIAC_LAWSON = 607

internal object FiveIsland_LostCave_Room1_EventScript_Lawson : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_RUIN_MANIAC_LAWSON,
        FiveIsland_LostCave_Room1.LawsonIntro,
        FiveIsland_LostCave_Room1.LawsonDefeat))
        return
    ctx.say(FiveIsland_LostCave_Room1.LawsonPostBattle)
  }
}

internal val FiveIsland_LostCave_Room1Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room1_EventScript_Lawson" to
            FiveIsland_LostCave_Room1_EventScript_Lawson,
    )
