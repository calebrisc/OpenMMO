package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SeafloorCavern_Room3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_SEAFLOOR_CAVERN_5 = 567
private const val TRAINER_SHELLY_SEAFLOOR_CAVERN = 33

internal object SeafloorCavern_Room3_EventScript_Shelly : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SHELLY_SEAFLOOR_CAVERN,
        SeafloorCavern_Room3.ShellyIntro,
        SeafloorCavern_Room3.ShellyDefeat))
        return
    ctx.say(SeafloorCavern_Room3.ShellyPostBattle)
  }
}

internal object SeafloorCavern_Room3_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_SEAFLOOR_CAVERN_5,
        SeafloorCavern_Room3.Grunt5Intro,
        SeafloorCavern_Room3.Grunt5Defeat))
        return
    ctx.say(SeafloorCavern_Room3.Grunt5PostBattle)
  }
}

internal val SeafloorCavern_Room3Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room3_EventScript_Shelly" to SeafloorCavern_Room3_EventScript_Shelly,
        "SeafloorCavern_Room3_EventScript_Grunt5" to SeafloorCavern_Room3_EventScript_Grunt5,
    )
