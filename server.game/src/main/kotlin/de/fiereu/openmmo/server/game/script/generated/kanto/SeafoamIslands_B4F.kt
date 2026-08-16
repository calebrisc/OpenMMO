package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SeafoamIslands_B4F
import de.fiereu.openmmo.dialog.generated.kanto.VictoryRoad_2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val ARTICUNO_DEX = 144

internal object SeafoamIslands_B4F_EventScript_Articuno : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(VictoryRoad_2F.Gyaoo)
    val result = ctx.wildBattle(ARTICUNO_DEX, 50)
    // Met either way: the decomp sets this on every outcome, so it is remembered even
    // by a player who ran.
    ctx.setFlag(KantoFlags.FLAG_FOUGHT_ARTICUNO)
    if (result != BattleResult.VICTORY && result != BattleResult.CAUGHT) return
    ctx.setFlag(KantoFlags.FLAG_HIDE_ARTICUNO)
    ctx.despawnInteracted()
  }
}

internal object SeafoamIslands_B4F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

internal object SeafoamIslands_B4F_EventScript_FastCurrentSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SeafoamIslands_B4F.DangerFastCurrent)
}

internal object SeafoamIslands_B4F_EventScript_BoulderHintSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SeafoamIslands_B4F.BouldersMightChangeWaterFlow)
}

internal val SeafoamIslands_B4FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_B4F_EventScript_Articuno" to SeafoamIslands_B4F_EventScript_Articuno,
        "SeafoamIslands_B4F_EventScript_ItemUltraBall" to
            SeafoamIslands_B4F_EventScript_ItemUltraBall,
        "SeafoamIslands_B4F_EventScript_FastCurrentSign" to
            SeafoamIslands_B4F_EventScript_FastCurrentSign,
        "SeafoamIslands_B4F_EventScript_BoulderHintSign" to
            SeafoamIslands_B4F_EventScript_BoulderHintSign,
    )
