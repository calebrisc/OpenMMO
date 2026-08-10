package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SixIsland_PatternBush
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BUG_CATCHER_GARRET = 611
private const val TRAINER_BUG_CATCHER_JONAH = 612
private const val TRAINER_BUG_CATCHER_VANCE = 613
private const val TRAINER_CAMPER_RILEY = 618
private const val TRAINER_LASS_DALIA = 616
private const val TRAINER_LASS_JOANA = 617
private const val TRAINER_PICNICKER_MARCY = 619
private const val TRAINER_PKMN_BREEDER_ALLISON = 610
private const val TRAINER_PKMN_BREEDER_BETHANY = 609
private const val TRAINER_RUIN_MANIAC_LAYTON = 620
private const val TRAINER_YOUNGSTER_CORDELL = 615

private const val TRAINER_YOUNGSTER_NASH = 614

internal object SixIsland_PatternBush_EventScript_Bethany : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_BREEDER_BETHANY,
        SixIsland_PatternBush.BethanyIntro,
        SixIsland_PatternBush.BethanyDefeat))
        return
    ctx.say(SixIsland_PatternBush.BethanyPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Allison : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_BREEDER_ALLISON,
        SixIsland_PatternBush.AllisonIntro,
        SixIsland_PatternBush.AllisonDefeat))
        return
    ctx.say(SixIsland_PatternBush.AllisonPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Garret : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_GARRET,
        SixIsland_PatternBush.GarretIntro,
        SixIsland_PatternBush.GarretDefeat))
        return
    ctx.say(SixIsland_PatternBush.GarretPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Jonah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_JONAH,
        SixIsland_PatternBush.JonahIntro,
        SixIsland_PatternBush.JonahDefeat))
        return
    ctx.say(SixIsland_PatternBush.JonahPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Vance : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_VANCE,
        SixIsland_PatternBush.VanceIntro,
        SixIsland_PatternBush.VanceDefeat))
        return
    ctx.say(SixIsland_PatternBush.VancePostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Nash : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNGSTER_NASH, SixIsland_PatternBush.NashIntro, SixIsland_PatternBush.NashDefeat))
        return
    ctx.say(SixIsland_PatternBush.NashPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Cordell : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNGSTER_CORDELL,
        SixIsland_PatternBush.CordellIntro,
        SixIsland_PatternBush.CordellDefeat))
        return
    ctx.say(SixIsland_PatternBush.CordellPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Dalia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LASS_DALIA, SixIsland_PatternBush.DaliaIntro, SixIsland_PatternBush.DaliaDefeat))
        return
    ctx.say(SixIsland_PatternBush.DaliaPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Joana : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LASS_JOANA, SixIsland_PatternBush.JoanaIntro, SixIsland_PatternBush.JoanaDefeat))
        return
    ctx.say(SixIsland_PatternBush.JoanaPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Riley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CAMPER_RILEY, SixIsland_PatternBush.RileyIntro, SixIsland_PatternBush.RileyDefeat))
        return
    ctx.say(SixIsland_PatternBush.RileyPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Marcy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_MARCY,
        SixIsland_PatternBush.MarcyIntro,
        SixIsland_PatternBush.MarcyDefeat))
        return
    ctx.say(SixIsland_PatternBush.MarcyPostBattle)
  }
}

internal object SixIsland_PatternBush_EventScript_Layton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_RUIN_MANIAC_LAYTON,
        SixIsland_PatternBush.LaytonIntro,
        SixIsland_PatternBush.LaytonDefeat))
        return
    ctx.say(SixIsland_PatternBush.LaytonPostBattle)
  }
}

internal val SixIsland_PatternBushScripts: Map<String, Script> =
    mapOf(
        "SixIsland_PatternBush_EventScript_Bethany" to SixIsland_PatternBush_EventScript_Bethany,
        "SixIsland_PatternBush_EventScript_Allison" to SixIsland_PatternBush_EventScript_Allison,
        "SixIsland_PatternBush_EventScript_Garret" to SixIsland_PatternBush_EventScript_Garret,
        "SixIsland_PatternBush_EventScript_Jonah" to SixIsland_PatternBush_EventScript_Jonah,
        "SixIsland_PatternBush_EventScript_Vance" to SixIsland_PatternBush_EventScript_Vance,
        "SixIsland_PatternBush_EventScript_Nash" to SixIsland_PatternBush_EventScript_Nash,
        "SixIsland_PatternBush_EventScript_Cordell" to SixIsland_PatternBush_EventScript_Cordell,
        "SixIsland_PatternBush_EventScript_Dalia" to SixIsland_PatternBush_EventScript_Dalia,
        "SixIsland_PatternBush_EventScript_Joana" to SixIsland_PatternBush_EventScript_Joana,
        "SixIsland_PatternBush_EventScript_Riley" to SixIsland_PatternBush_EventScript_Riley,
        "SixIsland_PatternBush_EventScript_Marcy" to SixIsland_PatternBush_EventScript_Marcy,
        "SixIsland_PatternBush_EventScript_Layton" to SixIsland_PatternBush_EventScript_Layton,
    )
