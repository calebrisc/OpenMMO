package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCave_B1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val MEWTWO_DEX = 150

internal object CeruleanCave_B1F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

internal object CeruleanCave_B1F_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_REVIVE)
}

internal object CeruleanCave_B1F_EventScript_Mewtwo : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(CeruleanCave_B1F.Mew)
    val result = ctx.wildBattle(MEWTWO_DEX, 70)
    // Met either way: the decomp sets this on every outcome, so it is remembered even
    // by a player who ran.
    ctx.setFlag(KantoFlags.FLAG_FOUGHT_MEWTWO)
    if (result != BattleResult.VICTORY && result != BattleResult.CAUGHT) return
    ctx.setFlag(KantoFlags.FLAG_HIDE_MEWTWO)
    ctx.despawnInteracted()
  }
}

internal val CeruleanCave_B1FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCave_B1F_EventScript_ItemUltraBall" to CeruleanCave_B1F_EventScript_ItemUltraBall,
        "CeruleanCave_B1F_EventScript_ItemMaxRevive" to CeruleanCave_B1F_EventScript_ItemMaxRevive,
        "CeruleanCave_B1F_EventScript_Mewtwo" to CeruleanCave_B1F_EventScript_Mewtwo,
    )
