package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.OneIsland_TreasureBeach
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SWIMMER_FEMALE_AMARA = 546

internal object OneIsland_TreasureBeach_EventScript_Amara : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_AMARA,
        OneIsland_TreasureBeach.AmaraIntro,
        OneIsland_TreasureBeach.AmaraDefeat))
        return
    ctx.say(OneIsland_TreasureBeach.AmaraPostBattle)
  }
}

internal object OneIsland_TreasureBeach_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_TreasureBeach.GoodThingsWashUpOnBeach)
}

internal val OneIsland_TreasureBeachScripts: Map<String, Script> =
    mapOf(
        "OneIsland_TreasureBeach_EventScript_Amara" to OneIsland_TreasureBeach_EventScript_Amara,
        "OneIsland_TreasureBeach_EventScript_Boy" to OneIsland_TreasureBeach_EventScript_Boy,
    )
