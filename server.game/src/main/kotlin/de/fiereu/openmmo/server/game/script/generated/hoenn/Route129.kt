package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route129
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALLISON = 387
private const val TRAINER_CHASE = 378
private const val TRAINER_CLARENCE = 580
private const val TRAINER_REED = 675
private const val TRAINER_TISHA = 676

internal object Route129_EventScript_Chase : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CHASE, Route129.ChaseIntro, Route129.ChaseDefeat)) return
    ctx.say(Route129.ChasePostBattle)
  }
}

internal object Route129_EventScript_Allison : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALLISON, Route129.AllisonIntro, Route129.AllisonDefeat))
        return
    ctx.say(Route129.AllisonPostBattle)
  }
}

internal object Route129_EventScript_Tisha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TISHA, Route129.TishaIntro, Route129.TishaDefeat)) return
    ctx.say(Route129.TishaPostBattle)
  }
}

internal object Route129_EventScript_Reed : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_REED, Route129.ReedIntro, Route129.ReedDefeat)) return
    ctx.say(Route129.ReedPostBattle)
  }
}

internal object Route129_EventScript_Clarence : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CLARENCE, Route129.ClarenceIntro, Route129.ClarenceDefeat))
        return
    ctx.say(Route129.ClarencePostBattle)
  }
}

internal val Route129Scripts: Map<String, Script> =
    mapOf(
        "Route129_EventScript_Chase" to Route129_EventScript_Chase,
        "Route129_EventScript_Allison" to Route129_EventScript_Allison,
        "Route129_EventScript_Tisha" to Route129_EventScript_Tisha,
        "Route129_EventScript_Reed" to Route129_EventScript_Reed,
        "Route129_EventScript_Clarence" to Route129_EventScript_Clarence,
    )
