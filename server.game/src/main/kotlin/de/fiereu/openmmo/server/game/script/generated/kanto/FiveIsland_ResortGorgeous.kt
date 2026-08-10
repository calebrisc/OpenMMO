package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_ResortGorgeous
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_LADY_GILLIAN = 564
private const val TRAINER_LADY_JACKI = 525
private const val TRAINER_PAINTER_CELINA = 562
private const val TRAINER_PAINTER_DAISY = 526
private const val TRAINER_PAINTER_RAYNA = 563
private const val TRAINER_SWIMMER_MALE_TOBY = 566
private const val TRAINER_YOUNGSTER_DESTIN = 565

internal object FiveIsland_ResortGorgeous_EventScript_Daisy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PAINTER_DAISY,
        FiveIsland_ResortGorgeous.DaisyIntro,
        FiveIsland_ResortGorgeous.DaisyDefeat))
        return
    ctx.say(FiveIsland_ResortGorgeous.DaisyPostBattle)
  }
}

internal object FiveIsland_ResortGorgeous_EventScript_Celina : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PAINTER_CELINA,
        FiveIsland_ResortGorgeous.CelinaIntro,
        FiveIsland_ResortGorgeous.CelinaDefeat))
        return
    ctx.say(FiveIsland_ResortGorgeous.CelinaPostBattle)
  }
}

internal object FiveIsland_ResortGorgeous_EventScript_Rayna : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PAINTER_RAYNA,
        FiveIsland_ResortGorgeous.RaynaIntro,
        FiveIsland_ResortGorgeous.RaynaDefeat))
        return
    ctx.say(FiveIsland_ResortGorgeous.RaynaPostBattle)
  }
}

internal object FiveIsland_ResortGorgeous_EventScript_Jacki : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LADY_JACKI,
        FiveIsland_ResortGorgeous.JackiIntro,
        FiveIsland_ResortGorgeous.JackiDefeat))
        return
    ctx.say(FiveIsland_ResortGorgeous.JackiPostBattle)
  }
}

internal object FiveIsland_ResortGorgeous_EventScript_Gillian : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LADY_GILLIAN,
        FiveIsland_ResortGorgeous.GillianIntro,
        FiveIsland_ResortGorgeous.GillianDefeat))
        return
    ctx.say(FiveIsland_ResortGorgeous.GillianPostBattle)
  }
}

internal object FiveIsland_ResortGorgeous_EventScript_Destin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNGSTER_DESTIN,
        FiveIsland_ResortGorgeous.DestinIntro,
        FiveIsland_ResortGorgeous.DestinDefeat))
        return
    ctx.say(FiveIsland_ResortGorgeous.DestinPostBattle)
  }
}

internal object FiveIsland_ResortGorgeous_EventScript_Toby : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_TOBY,
        FiveIsland_ResortGorgeous.TobyIntro,
        FiveIsland_ResortGorgeous.TobyDefeat))
        return
    ctx.say(FiveIsland_ResortGorgeous.TobyPostBattle)
  }
}

internal object FiveIsland_ResortGorgeous_EventScript_SelphysHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FiveIsland_ResortGorgeous.SelphysHouse)
}

internal val FiveIsland_ResortGorgeousScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_ResortGorgeous_EventScript_Daisy" to
            FiveIsland_ResortGorgeous_EventScript_Daisy,
        "FiveIsland_ResortGorgeous_EventScript_Celina" to
            FiveIsland_ResortGorgeous_EventScript_Celina,
        "FiveIsland_ResortGorgeous_EventScript_Rayna" to
            FiveIsland_ResortGorgeous_EventScript_Rayna,
        "FiveIsland_ResortGorgeous_EventScript_Jacki" to
            FiveIsland_ResortGorgeous_EventScript_Jacki,
        "FiveIsland_ResortGorgeous_EventScript_Gillian" to
            FiveIsland_ResortGorgeous_EventScript_Gillian,
        "FiveIsland_ResortGorgeous_EventScript_Destin" to
            FiveIsland_ResortGorgeous_EventScript_Destin,
        "FiveIsland_ResortGorgeous_EventScript_Toby" to FiveIsland_ResortGorgeous_EventScript_Toby,
        "FiveIsland_ResortGorgeous_EventScript_SelphysHouseSign" to
            FiveIsland_ResortGorgeous_EventScript_SelphysHouseSign,
    )
