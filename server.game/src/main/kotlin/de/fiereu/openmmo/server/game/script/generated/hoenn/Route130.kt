package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route130
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_KATIE = 455
private const val TRAINER_RODNEY = 165
private const val TRAINER_SANTIAGO = 168

internal object Route130_EventScript_Rodney : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RODNEY, Route130.RodneyIntro, Route130.RodneyDefeat))
        return
    ctx.say(Route130.RodneyPostBattle)
  }
}

internal object Route130_EventScript_Katie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KATIE, Route130.KatieIntro, Route130.KatieDefeat)) return
    ctx.say(Route130.KatiePostBattle)
  }
}

internal object Route130_EventScript_Santiago : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SANTIAGO, Route130.SantiagoIntro, Route130.SantiagoDefeat))
        return
    ctx.say(Route130.SantiagoPostBattle)
  }
}

internal val Route130Scripts: Map<String, Script> =
    mapOf(
        "Route130_EventScript_Rodney" to Route130_EventScript_Rodney,
        "Route130_EventScript_Katie" to Route130_EventScript_Katie,
        "Route130_EventScript_Santiago" to Route130_EventScript_Santiago,
    )
