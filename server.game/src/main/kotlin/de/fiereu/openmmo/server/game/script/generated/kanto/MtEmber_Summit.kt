package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VictoryRoad_2F
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val MOLTRES_DEX = 146

internal object MtEmber_Summit_EventScript_Moltres : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(VictoryRoad_2F.Gyaoo)
    val result = ctx.wildBattle(MOLTRES_DEX, 50)
    // Met either way: the decomp sets this on every outcome, so it is remembered even
    // by a player who ran.
    ctx.setFlag(KantoFlags.FLAG_FOUGHT_MOLTRES)
    if (result != BattleResult.VICTORY && result != BattleResult.CAUGHT) return
    ctx.setFlag(KantoFlags.FLAG_HIDE_MOLTRES)
    ctx.despawnInteracted()
  }
}

internal val MtEmber_SummitScripts: Map<String, Script> =
    mapOf(
        "MtEmber_Summit_EventScript_Moltres" to MtEmber_Summit_EventScript_Moltres,
    )
