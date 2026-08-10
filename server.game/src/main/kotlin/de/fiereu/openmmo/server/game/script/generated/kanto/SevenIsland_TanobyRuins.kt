package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_TanobyRuins
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GENTLEMAN_CLIFFORD = 605
private const val TRAINER_PAINTER_EDNA = 604
private const val TRAINER_RUIN_MANIAC_BENJAMIN = 603
private const val TRAINER_RUIN_MANIAC_BRANDON = 602

internal object SevenIsland_TanobyRuins_EventScript_Brandon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_RUIN_MANIAC_BRANDON,
        SevenIsland_TanobyRuins.BrandonIntro,
        SevenIsland_TanobyRuins.BrandonDefeat))
        return
    ctx.say(SevenIsland_TanobyRuins.BrandonPostBattle)
  }
}

internal object SevenIsland_TanobyRuins_EventScript_Benjamin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_RUIN_MANIAC_BENJAMIN,
        SevenIsland_TanobyRuins.BenjaminIntro,
        SevenIsland_TanobyRuins.BenjaminDefeat))
        return
    ctx.say(SevenIsland_TanobyRuins.BenjaminPostBattle)
  }
}

internal object SevenIsland_TanobyRuins_EventScript_Edna : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PAINTER_EDNA,
        SevenIsland_TanobyRuins.EdnaIntro,
        SevenIsland_TanobyRuins.EdnaDefeat))
        return
    ctx.say(SevenIsland_TanobyRuins.EdnaPostBattle)
  }
}

internal object SevenIsland_TanobyRuins_EventScript_Clifford : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GENTLEMAN_CLIFFORD,
        SevenIsland_TanobyRuins.CliffordIntro,
        SevenIsland_TanobyRuins.CliffordDefeat))
        return
    ctx.say(SevenIsland_TanobyRuins.CliffordPostBattle)
  }
}

internal val SevenIsland_TanobyRuinsScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_TanobyRuins_EventScript_Brandon" to
            SevenIsland_TanobyRuins_EventScript_Brandon,
        "SevenIsland_TanobyRuins_EventScript_Benjamin" to
            SevenIsland_TanobyRuins_EventScript_Benjamin,
        "SevenIsland_TanobyRuins_EventScript_Edna" to SevenIsland_TanobyRuins_EventScript_Edna,
        "SevenIsland_TanobyRuins_EventScript_Clifford" to
            SevenIsland_TanobyRuins_EventScript_Clifford,
    )
