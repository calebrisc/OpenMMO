package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_7F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val GRUNT_1_ID = 369
private const val GRUNT_2_ID = 370
private const val GRUNT_3_ID = 371

internal object PokemonTower_7F_EventScript_MrFuji : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(KantoFlags.FLAG_HIDE_TOWER_FUJI)
    ctx.clearFlag(KantoFlags.FLAG_HIDE_POKEHOUSE_FUJI)
    ctx.setFlag(KantoFlags.FLAG_RESCUED_MR_FUJI)
    ctx.say(PokemonTower_7F.MrFujiThankYouFollowMe)
    ctx.warp(0, 8, 2, 4, 7, Direction.UP)
  }
}

internal object PokemonTower_7F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        GRUNT_1_ID, PokemonTower_7F.Grunt1Intro, PokemonTower_7F.Grunt1Defeat))
        return
    ctx.say(PokemonTower_7F.Grunt1PostBattle)
  }
}

internal object PokemonTower_7F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        GRUNT_2_ID, PokemonTower_7F.Grunt2Intro, PokemonTower_7F.Grunt2Defeat))
        return
    ctx.say(PokemonTower_7F.Grunt2PostBattle)
  }
}

internal object PokemonTower_7F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        GRUNT_3_ID, PokemonTower_7F.Grunt3Intro, PokemonTower_7F.Grunt3Defeat))
        return
    ctx.say(PokemonTower_7F.Grunt3PostBattle)
  }
}

internal val PokemonTower_7FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_7F_EventScript_MrFuji" to PokemonTower_7F_EventScript_MrFuji,
        "PokemonTower_7F_EventScript_Grunt1" to PokemonTower_7F_EventScript_Grunt1,
        "PokemonTower_7F_EventScript_Grunt2" to PokemonTower_7F_EventScript_Grunt2,
        "PokemonTower_7F_EventScript_Grunt3" to PokemonTower_7F_EventScript_Grunt3,
    )
