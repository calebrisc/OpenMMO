package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_7F
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

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

// 6F's ghost trigger has no generated home of its own, so the label is registered here. Without
// the Silph Scope the ghost turns the player back; with it, the trigger becomes the one time
// Marowak fight, and winning or catching flips the scene var that keys the trigger off.
private const val MAROWAK_DEX = 105
private const val MAROWAK_BEATEN = "kanto/BEAT_MAROWAK_GHOST"

internal object PokemonTower_6F_EventScript_MarowakGhost : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(MAROWAK_BEATEN)) return
    if (!ctx.isFlagSet(de.fiereu.openmmo.server.game.script.generated.kanto.GOT_SILPH_SCOPE)) {
      ctx.send(notice("An unidentifiable ghost blocks the way! The SILPH SCOPE might reveal it."))
      ctx.moveSelf(
          when (ctx.facingDirection) {
            Direction.UP -> MovementStep.WALK_DOWN
            Direction.DOWN -> MovementStep.WALK_UP
            Direction.LEFT -> MovementStep.WALK_RIGHT
            else -> MovementStep.WALK_LEFT
          })
      return
    }
    ctx.send(notice("The SILPH SCOPE unmasked the ghost: it's a MAROWAK!"))
    val result = ctx.wildBattle(MAROWAK_DEX, 30)
    if (result != BattleResult.VICTORY && result != BattleResult.CAUGHT) return
    ctx.setFlag(MAROWAK_BEATEN)
    ctx.setVar(KantoVars.VAR_MAP_SCENE_POKEMON_TOWER_6F, 1)
  }
}

internal val PokemonTower_7FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_6F_EventScript_MarowakGhost" to PokemonTower_6F_EventScript_MarowakGhost,
        "PokemonTower_7F_EventScript_MrFuji" to PokemonTower_7F_EventScript_MrFuji,
        "PokemonTower_7F_EventScript_Grunt1" to PokemonTower_7F_EventScript_Grunt1,
        "PokemonTower_7F_EventScript_Grunt2" to PokemonTower_7F_EventScript_Grunt2,
        "PokemonTower_7F_EventScript_Grunt3" to PokemonTower_7F_EventScript_Grunt3,
    )
