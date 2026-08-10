package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SootopolisCity_EventScript_CaveOfOriginExpert : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.CaveOfOriginSleepsToo)
}

internal object SootopolisCity_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.CityRegainedCalm)
}

internal object SootopolisCity_EventScript_Kiri : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.DoYouKnowMonNames)
}

internal object SootopolisCity_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.ThisIsWicked)
}

internal object SootopolisCity_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.PhysicallyFitLivingHere)
}

internal object SootopolisCity_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.SootopolisDidntGetWrecked)
}

internal object SootopolisCity_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.NoOrdinaryTourist)
}

internal object SootopolisCity_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.SootopolisSkyBeautiful)
}

internal object SootopolisCity_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.SeeingLegendWithOwnEyes)
}

internal object SootopolisCity_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.PrettyMonCameFromSky)
}

internal object SootopolisCity_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.WonderWhatWorldIsLike)
}

internal object SootopolisCity_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.GoRedAndBlueMon)
}

internal object SootopolisCity_EventScript_Maxie : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.WhereDidLegendariesGo)
}

internal object SootopolisCity_EventScript_Archie : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.TwoPokemonArentAngry)
}

internal object SootopolisCity_EventScript_Wallace : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity.LeadSuperiorTrainerToCave)
}

internal object SootopolisCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SootopolisCity.GymSign)
}

internal object SootopolisCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SootopolisCity.CitySign)
}

internal val SootopolisCityScripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_EventScript_CaveOfOriginExpert" to
            SootopolisCity_EventScript_CaveOfOriginExpert,
        "SootopolisCity_EventScript_Woman2" to SootopolisCity_EventScript_Woman2,
        "SootopolisCity_EventScript_Kiri" to SootopolisCity_EventScript_Kiri,
        "SootopolisCity_EventScript_NinjaBoy" to SootopolisCity_EventScript_NinjaBoy,
        "SootopolisCity_EventScript_Boy1" to SootopolisCity_EventScript_Boy1,
        "SootopolisCity_EventScript_Man" to SootopolisCity_EventScript_Man,
        "SootopolisCity_EventScript_Steven" to SootopolisCity_EventScript_Steven,
        "SootopolisCity_EventScript_Woman1" to SootopolisCity_EventScript_Woman1,
        "SootopolisCity_EventScript_Maniac" to SootopolisCity_EventScript_Maniac,
        "SootopolisCity_EventScript_Girl" to SootopolisCity_EventScript_Girl,
        "SootopolisCity_EventScript_BlackBelt" to SootopolisCity_EventScript_BlackBelt,
        "SootopolisCity_EventScript_Boy2" to SootopolisCity_EventScript_Boy2,
        "SootopolisCity_EventScript_Maxie" to SootopolisCity_EventScript_Maxie,
        "SootopolisCity_EventScript_Archie" to SootopolisCity_EventScript_Archie,
        "SootopolisCity_EventScript_Wallace" to SootopolisCity_EventScript_Wallace,
        "SootopolisCity_EventScript_GymSign" to SootopolisCity_EventScript_GymSign,
        "SootopolisCity_EventScript_CitySign" to SootopolisCity_EventScript_CitySign,
    )
