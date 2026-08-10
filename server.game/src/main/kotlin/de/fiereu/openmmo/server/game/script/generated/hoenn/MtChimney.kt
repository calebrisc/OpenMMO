package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtChimney
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

private const val TRAINER_MAXIE_MT_CHIMNEY = 602
private const val SHELBY_ID = 313
private const val SAWYER_ID = 1

private const val TRAINER_GRUNT_MT_CHIMNEY_1 = 146
private const val TRAINER_GRUNT_MT_CHIMNEY_2 = 579
private const val TRAINER_MELISSA = 124
private const val TRAINER_SHEILA = 125
private const val TRAINER_SHIRLEY = 126
private const val TRAINER_TABITHA_MT_CHIMNEY = 597

internal object MtChimney_EventScript_Archie : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtChimney.ArchieIHaveMyHandsFull)
}

internal object MtChimney_EventScript_Maxie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_DEFEATED_EVIL_TEAM_MT_CHIMNEY)) return
    ctx.sign(MtChimney.MeteoriteWillActivateVolcano)
    ctx.say(MtChimney.MaxieIntro)
    if (!ctx.trainerBattleSingle(TRAINER_MAXIE_MT_CHIMNEY, null, MtChimney.MaxieDefeat)) return
    ctx.say(MtChimney.MaxieYouHaventSeenLastOfMagma)
    ctx.despawnInteracted()
    ctx.setFlag(HoennFlags.FLAG_HIDE_MT_CHIMNEY_TEAM_MAGMA)
    ctx.sign(MtChimney.ArchieThankYou)
    ctx.setFlag(HoennFlags.FLAG_HIDE_MT_CHIMNEY_TEAM_AQUA)
    ctx.setFlag(HoennFlags.FLAG_DEFEATED_EVIL_TEAM_MT_CHIMNEY)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_FALLARBOR_HOUSE_PROF_COZMO)
    ctx.setFlag(HoennFlags.FLAG_HIDE_METEOR_FALLS_1F_1R_COZMO)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_MT_CHIMNEY_LAVA_COOKIE_LADY)
  }
}

internal object MtChimney_EventScript_Tabitha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TABITHA_MT_CHIMNEY, MtChimney.TabithaIntro, MtChimney.TabithaDefeat))
        return
    ctx.say(MtChimney.TabithaPostBattle)
  }
}

internal object MtChimney_EventScript_BusyMagmaGrunt5 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.DouseThemInFire)
}

internal object MtChimney_EventScript_BusyMagmaGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.AquasNameSimilar)
}

internal object MtChimney_EventScript_BusyAquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.LessHabitatForWaterPokemon)
}

internal object MtChimney_EventScript_BusyAquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.MagmaOutnumbersUs)
}

internal object MtChimney_EventScript_BusyAquaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.MagmasNameSimilar)
}

internal object MtChimney_EventScript_LavaCookieLady : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(MtChimney.LavaCookiesJust200)
    ctx.pokemart(Items.LAVA_COOKIE)
  }
}

internal object MtChimney_EventScript_BusyMagmaGrunt6 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.KeepMakingMoreLand)
}

internal object MtChimney_EventScript_AquaPoochyena : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.Bushaa)
}

internal object MtChimney_EventScript_MagmaPoochyena : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.Bufoh)
}

internal object MtChimney_EventScript_BusyMagmaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.MeteoritesPackAmazingPower)
}

internal object MtChimney_EventScript_BusyMagmaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.YouBetterNotMessWithUs)
}

internal object MtChimney_EventScript_BusyMagmaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.TeamAquaAlwaysMessingWithPlans)
}

internal object MtChimney_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_CHIMNEY_2, MtChimney.Grunt2Intro, MtChimney.Grunt2Defeat))
        return
    ctx.say(MtChimney.Grunt2PostBattle)
  }
}

internal object MtChimney_EventScript_Shelby : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(SHELBY_ID, MtChimney.ShelbyIntro, MtChimney.ShelbyDefeat)) return
    ctx.say(MtChimney.ShelbyPostBattle)
  }
}

internal object MtChimney_EventScript_Melissa : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MELISSA, MtChimney.MelissaIntro, MtChimney.MelissaDefeat))
        return
    ctx.say(MtChimney.MelissaPostBattle)
  }
}

internal object MtChimney_EventScript_Sheila : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SHEILA, MtChimney.SheilaIntro, MtChimney.SheilaDefeat))
        return
    ctx.say(MtChimney.SheilaPostBattle)
  }
}

internal object MtChimney_EventScript_Shirley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SHIRLEY, MtChimney.ShirleyIntro, MtChimney.ShirleyDefeat))
        return
    ctx.say(MtChimney.ShirleyPostBattle)
  }
}

internal object MtChimney_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MT_CHIMNEY_1, MtChimney.Grunt1Intro, MtChimney.Grunt1Defeat))
        return
    ctx.say(MtChimney.Grunt1PostBattle)
  }
}

internal object MtChimney_EventScript_Sawyer : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(SAWYER_ID, MtChimney.SawyerIntro, MtChimney.SawyerDefeat)) return
    ctx.say(MtChimney.SawyerPostBattle)
  }
}

internal object MtChimney_EventScript_MeteoriteMachine : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(HoennFlags.FLAG_DEFEATED_EVIL_TEAM_MT_CHIMNEY)) {
      return ctx.sign(MtChimney.MeteoriteFittedOnMachine)
    }
    if (ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_METEORITE)) {
      return ctx.sign(MtChimney.MachineMakesNoResponse)
    }
    if (!ctx.askYesNo(MtChimney.RemoveTheMeteorite)) return ctx.sign(MtChimney.PlayerLeftMeteorite)
    // The meteorite is story state; Cozmo's script checks the flag.
    ctx.setFlag(HoennFlags.FLAG_RECEIVED_METEORITE)
    ctx.sign(MtChimney.PlayerRemovedMeteorite)
  }
}

internal object MtChimney_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.RouteSign)
}

internal val MtChimneyScripts: Map<String, Script> =
    mapOf(
        "MtChimney_EventScript_Archie" to MtChimney_EventScript_Archie,
        "MtChimney_EventScript_Maxie" to MtChimney_EventScript_Maxie,
        "MtChimney_EventScript_Tabitha" to MtChimney_EventScript_Tabitha,
        "MtChimney_EventScript_BusyMagmaGrunt5" to MtChimney_EventScript_BusyMagmaGrunt5,
        "MtChimney_EventScript_BusyMagmaGrunt4" to MtChimney_EventScript_BusyMagmaGrunt4,
        "MtChimney_EventScript_BusyAquaGrunt2" to MtChimney_EventScript_BusyAquaGrunt2,
        "MtChimney_EventScript_BusyAquaGrunt1" to MtChimney_EventScript_BusyAquaGrunt1,
        "MtChimney_EventScript_BusyAquaGrunt3" to MtChimney_EventScript_BusyAquaGrunt3,
        "MtChimney_EventScript_LavaCookieLady" to MtChimney_EventScript_LavaCookieLady,
        "MtChimney_EventScript_BusyMagmaGrunt6" to MtChimney_EventScript_BusyMagmaGrunt6,
        "MtChimney_EventScript_AquaPoochyena" to MtChimney_EventScript_AquaPoochyena,
        "MtChimney_EventScript_MagmaPoochyena" to MtChimney_EventScript_MagmaPoochyena,
        "MtChimney_EventScript_BusyMagmaGrunt2" to MtChimney_EventScript_BusyMagmaGrunt2,
        "MtChimney_EventScript_BusyMagmaGrunt3" to MtChimney_EventScript_BusyMagmaGrunt3,
        "MtChimney_EventScript_BusyMagmaGrunt1" to MtChimney_EventScript_BusyMagmaGrunt1,
        "MtChimney_EventScript_Grunt2" to MtChimney_EventScript_Grunt2,
        "MtChimney_EventScript_Shelby" to MtChimney_EventScript_Shelby,
        "MtChimney_EventScript_Melissa" to MtChimney_EventScript_Melissa,
        "MtChimney_EventScript_Sheila" to MtChimney_EventScript_Sheila,
        "MtChimney_EventScript_Shirley" to MtChimney_EventScript_Shirley,
        "MtChimney_EventScript_Grunt1" to MtChimney_EventScript_Grunt1,
        "MtChimney_EventScript_Sawyer" to MtChimney_EventScript_Sawyer,
        "MtChimney_EventScript_MeteoriteMachine" to MtChimney_EventScript_MeteoriteMachine,
        "MtChimney_EventScript_RouteSign" to MtChimney_EventScript_RouteSign,
    )
