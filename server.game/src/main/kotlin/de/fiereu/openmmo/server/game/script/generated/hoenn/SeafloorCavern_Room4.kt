package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SeafloorCavern_Room4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_SEAFLOOR_CAVERN_3 = 8
private const val TRAINER_GRUNT_SEAFLOOR_CAVERN_4 = 14

internal object SeafloorCavern_Room4_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_SEAFLOOR_CAVERN_3,
        SeafloorCavern_Room4.Grunt3Intro,
        SeafloorCavern_Room4.Grunt3Defeat))
        return
    ctx.say(SeafloorCavern_Room4.Grunt3PostBattle)
  }
}

internal object SeafloorCavern_Room4_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_SEAFLOOR_CAVERN_4,
        SeafloorCavern_Room4.Grunt4Intro,
        SeafloorCavern_Room4.Grunt4Defeat))
        return
    ctx.say(SeafloorCavern_Room4.Grunt4PostBattle)
  }
}

internal val SeafloorCavern_Room4Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room4_EventScript_Grunt3" to SeafloorCavern_Room4_EventScript_Grunt3,
        "SeafloorCavern_Room4_EventScript_Grunt4" to SeafloorCavern_Room4_EventScript_Grunt4,
    )
