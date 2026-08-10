package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_OceanicMuseum_2F
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val TRAINER_GRUNT_MUSEUM_1 = 20
private const val TRAINER_GRUNT_MUSEUM_2 = 21

internal object SlateportCity_OceanicMuseum_2F_EventScript_CaptStern : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_DELIVERED_DEVON_GOODS)) return
    if (!ctx.isFlagSet(HoennFlags.FLAG_RECOVERED_DEVON_GOODS)) {
      return ctx.say(SlateportCity_OceanicMuseum_2F.RemindsMeOfAbandonedShip)
    }
    ctx.say(SlateportCity_OceanicMuseum_2F.ThankYouForTheParts)
    ctx.sign(SlateportCity_OceanicMuseum_2F.WellTakeThoseParts)
    ctx.say(SlateportCity_OceanicMuseum_2F.SternWhoAreYou)
    ctx.sign(SlateportCity_OceanicMuseum_2F.WereTeamAqua)
    if (ctx.trainerBattle(TRAINER_GRUNT_MUSEUM_1) != BattleResult.VICTORY) return
    ctx.sign(SlateportCity_OceanicMuseum_2F.Grunt1Defeat)
    ctx.sign(SlateportCity_OceanicMuseum_2F.BossGoingToBeFurious)
    ctx.sign(SlateportCity_OceanicMuseum_2F.LetMeTakeCareOfThis)
    if (ctx.trainerBattle(TRAINER_GRUNT_MUSEUM_2) != BattleResult.VICTORY) return
    ctx.sign(SlateportCity_OceanicMuseum_2F.Grunt2Defeat)
    ctx.sign(SlateportCity_OceanicMuseum_2F.MeddlingKid)
    ctx.sign(SlateportCity_OceanicMuseum_2F.CameToSeeWhatsTakingSoLong)
    ctx.sign(SlateportCity_OceanicMuseum_2F.ArchieWarning)
    ctx.say(SlateportCity_OceanicMuseum_2F.SternThankYouForSavingUs)
    ctx.say(SlateportCity_OceanicMuseum_2F.SternIveGotToGo)
    ctx.healParty()
    ctx.despawnInteracted()
    ctx.setFlag(HoennFlags.FLAG_HIDE_ROUTE_110_TEAM_AQUA)
    ctx.setFlag(HoennFlags.FLAG_DELIVERED_DEVON_GOODS)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_ROUTE_116_DEVON_EMPLOYEE)
    ctx.setFlag(HoennFlags.FLAG_HIDE_RUSTBORO_CITY_DEVON_CORP_3F_EMPLOYEE)
    ctx.setFlag(HoennFlags.FLAG_HIDE_SLATEPORT_CITY_OCEANIC_MUSEUM_AQUA_GRUNTS)
    ctx.setVar(HoennVars.VAR_SLATEPORT_OUTSIDE_MUSEUM_STATE, 1)
  }
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_2F.RemindsMeOfAbandonedShip)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_2F.DontRunInMuseum)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_2F.RemindsMeOfAbandonedShip)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.WaterQualitySample1)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.WaterQualitySample2)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SubmersibleReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SumbersibleReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SubmarineReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SubmarineReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SSTidalReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SSTidalReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SSAnneReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SSAnneReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SurfaceSeawaterDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SurfaceSeawaterDisplay)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_DeepSeawaterDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.DeepSeawaterDisplay)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_HoennModel : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity_OceanicMuseum_2F.HoennModel)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_PressureExperiment : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.PressureExperiment)
}

internal val SlateportCity_OceanicMuseum_2FScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_OceanicMuseum_2F_EventScript_CaptStern" to
            SlateportCity_OceanicMuseum_2F_EventScript_CaptStern,
        "SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron1" to
            SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron1,
        "SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron2" to
            SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron2,
        "SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron3" to
            SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron3,
        "SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample1" to
            SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample1,
        "SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample2" to
            SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample2,
        "SlateportCity_OceanicMuseum_2F_EventScript_SubmersibleReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SubmersibleReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SubmarineReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SubmarineReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SSTidalReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SSTidalReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SSAnneReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SSAnneReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SurfaceSeawaterDisplay" to
            SlateportCity_OceanicMuseum_2F_EventScript_SurfaceSeawaterDisplay,
        "SlateportCity_OceanicMuseum_2F_EventScript_DeepSeawaterDisplay" to
            SlateportCity_OceanicMuseum_2F_EventScript_DeepSeawaterDisplay,
        "SlateportCity_OceanicMuseum_2F_EventScript_HoennModel" to
            SlateportCity_OceanicMuseum_2F_EventScript_HoennModel,
        "SlateportCity_OceanicMuseum_2F_EventScript_PressureExperiment" to
            SlateportCity_OceanicMuseum_2F_EventScript_PressureExperiment,
    )
