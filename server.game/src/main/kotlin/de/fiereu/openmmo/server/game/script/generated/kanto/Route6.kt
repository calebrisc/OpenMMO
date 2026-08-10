package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route6
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BUG_CATCHER_ELIJAH = 112
private const val TRAINER_BUG_CATCHER_KEIGO = 111
private const val TRAINER_CAMPER_JEFF = 146
private const val TRAINER_CAMPER_RICKY = 145
private const val TRAINER_PICNICKER_ISABELLE = 152
private const val TRAINER_PICNICKER_NANCY = 151

internal object Route6_EventScript_Keigo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BUG_CATCHER_KEIGO, Route6.KeigoIntro, Route6.KeigoDefeat))
        return
    ctx.say(Route6.KeigoPostBattle)
  }
}

internal object Route6_EventScript_Ricky : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_RICKY, Route6.RickyIntro, Route6.RickyDefeat))
        return
    ctx.say(Route6.RickyPostBattle)
  }
}

internal object Route6_EventScript_Nancy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_NANCY, Route6.NancyIntro, Route6.NancyDefeat))
        return
    ctx.say(Route6.NancyPostBattle)
  }
}

internal object Route6_EventScript_Elijah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_ELIJAH, Route6.ElijahIntro, Route6.ElijahDefeat))
        return
    ctx.say(Route6.ElijahPostBattle)
  }
}

internal object Route6_EventScript_Isabelle : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_ISABELLE, Route6.IsabelleIntro, Route6.IsabelleDefeat))
        return
    ctx.say(Route6.IsabellePostBattle)
  }
}

internal object Route6_EventScript_Jeff : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_JEFF, Route6.JeffIntro, Route6.JeffDefeat)) return
    ctx.say(Route6.JeffPostBattle)
  }
}

internal object Route6_EventScript_UndergroundPathSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route6.UndergroundPathSign)
}

internal val Route6Scripts: Map<String, Script> =
    mapOf(
        "Route6_EventScript_Keigo" to Route6_EventScript_Keigo,
        "Route6_EventScript_Ricky" to Route6_EventScript_Ricky,
        "Route6_EventScript_Nancy" to Route6_EventScript_Nancy,
        "Route6_EventScript_Elijah" to Route6_EventScript_Elijah,
        "Route6_EventScript_Isabelle" to Route6_EventScript_Isabelle,
        "Route6_EventScript_Jeff" to Route6_EventScript_Jeff,
        "Route6_EventScript_UndergroundPathSign" to Route6_EventScript_UndergroundPathSign,
    )
