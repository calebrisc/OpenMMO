package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SeafloorCavern_Room1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_SEAFLOOR_CAVERN_1 = 6
private const val TRAINER_GRUNT_SEAFLOOR_CAVERN_2 = 7

internal object SeafloorCavern_Room1_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_SEAFLOOR_CAVERN_1,
        SeafloorCavern_Room1.Grunt1Intro,
        SeafloorCavern_Room1.Grunt1Defeat))
        return
    ctx.say(SeafloorCavern_Room1.Grunt1PostBattle)
  }
}

internal object SeafloorCavern_Room1_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_SEAFLOOR_CAVERN_2,
        SeafloorCavern_Room1.Grunt2Intro,
        SeafloorCavern_Room1.Grunt2Defeat))
        return
    ctx.say(SeafloorCavern_Room1.Grunt2PostBattle)
  }
}

internal val SeafloorCavern_Room1Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room1_EventScript_Grunt1" to SeafloorCavern_Room1_EventScript_Grunt1,
        "SeafloorCavern_Room1_EventScript_Grunt2" to SeafloorCavern_Room1_EventScript_Grunt2,
    )
