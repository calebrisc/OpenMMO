package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_LostCave_Room4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_PSYCHIC_LAURA = 608

internal object FiveIsland_LostCave_Room4_EventScript_Laura : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PSYCHIC_LAURA,
        FiveIsland_LostCave_Room4.LauraIntro,
        FiveIsland_LostCave_Room4.LauraDefeat))
        return
    ctx.say(FiveIsland_LostCave_Room4.LauraPostBattle)
  }
}

internal val FiveIsland_LostCave_Room4Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room4_EventScript_Laura" to
            FiveIsland_LostCave_Room4_EventScript_Laura,
    )
