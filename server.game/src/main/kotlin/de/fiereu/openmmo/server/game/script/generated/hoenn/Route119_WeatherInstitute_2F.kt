package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route119_WeatherInstitute_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val TRAINER_SHELLY_WEATHER_INSTITUTE = 32
private const val CASTFORM = 351

private const val TRAINER_GRUNT_WEATHER_INST_2 = 18
private const val TRAINER_GRUNT_WEATHER_INST_3 = 19
private const val TRAINER_GRUNT_WEATHER_INST_5 = 596

internal object Route119_WeatherInstitute_2F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_WEATHER_INST_2,
        Route119_WeatherInstitute_2F.Grunt2Intro,
        Route119_WeatherInstitute_2F.Grunt2Defeat))
        return
    ctx.say(Route119_WeatherInstitute_2F.Grunt2PostBattle)
  }
}

internal object Route119_WeatherInstitute_2F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_WEATHER_INST_3,
        Route119_WeatherInstitute_2F.Grunt3Intro,
        Route119_WeatherInstitute_2F.Grunt3Defeat))
        return
    ctx.say(Route119_WeatherInstitute_2F.Grunt3PostBattle)
  }
}

internal object Route119_WeatherInstitute_2F_EventScript_Shelly : Script {
  override suspend fun run(ctx: ScriptContext) {
    val firstWin = !ctx.isTrainerDefeated(TRAINER_SHELLY_WEATHER_INSTITUTE)
    if (!ctx.trainerBattleSingle(
        TRAINER_SHELLY_WEATHER_INSTITUTE,
        Route119_WeatherInstitute_2F.ShellyIntro,
        Route119_WeatherInstitute_2F.ShellyDefeat))
        return
    ctx.say(Route119_WeatherInstitute_2F.ShellyPostBattle)
    if (firstWin) {
      ctx.sign(Route119_WeatherInstitute_2F.TeamMagmaJustPassedBy)
      ctx.sign(Route119_WeatherInstitute_2F.WeHaveToHurryToMtPyre)
      ctx.setVar(HoennVars.VAR_WEATHER_INSTITUTE_STATE, 1)
      ctx.clearFlag(HoennFlags.FLAG_HIDE_WEATHER_INSTITUTE_2F_WORKERS)
      ctx.setFlag(HoennFlags.FLAG_HIDE_ROUTE_119_TEAM_AQUA)
      ctx.setFlag(HoennFlags.FLAG_HIDE_WEATHER_INSTITUTE_2F_AQUA_GRUNT_M)
      ctx.despawnInteracted()
    }
  }
}

internal object Route119_WeatherInstitute_2F_EventScript_WeatherScientist : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_CASTFORM)) {
      return ctx.say(Route119_WeatherInstitute_2F.PokemonChangesWithWeather)
    }
    if (ctx.givePokemon(CASTFORM, 25) == null) return
    ctx.setFlag(HoennFlags.FLAG_RECEIVED_CASTFORM)
    ctx.sign(Route119_WeatherInstitute_2F.PlayerReceivedCastform)
    ctx.say(Route119_WeatherInstitute_2F.PokemonChangesWithWeather)
  }
}

internal object Route119_WeatherInstitute_2F_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_WEATHER_INST_5,
        Route119_WeatherInstitute_2F.Grunt5Intro,
        Route119_WeatherInstitute_2F.Grunt5Defeat))
        return
    ctx.say(Route119_WeatherInstitute_2F.Grunt5PostBattle)
  }
}

internal val Route119_WeatherInstitute_2FScripts: Map<String, Script> =
    mapOf(
        "Route119_WeatherInstitute_2F_EventScript_Grunt2" to
            Route119_WeatherInstitute_2F_EventScript_Grunt2,
        "Route119_WeatherInstitute_2F_EventScript_Grunt3" to
            Route119_WeatherInstitute_2F_EventScript_Grunt3,
        "Route119_WeatherInstitute_2F_EventScript_Shelly" to
            Route119_WeatherInstitute_2F_EventScript_Shelly,
        "Route119_WeatherInstitute_2F_EventScript_WeatherScientist" to
            Route119_WeatherInstitute_2F_EventScript_WeatherScientist,
        "Route119_WeatherInstitute_2F_EventScript_Grunt5" to
            Route119_WeatherInstitute_2F_EventScript_Grunt5,
    )
