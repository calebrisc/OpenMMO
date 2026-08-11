package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route14
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TWINS_KIRI_JAN = 487

private const val TRAINER_BIKER_GERALD = 209
private const val TRAINER_BIKER_ISAAC = 208
private const val TRAINER_BIKER_LUKAS = 207
private const val TRAINER_BIKER_MALIK = 196
private const val TRAINER_BIRD_KEEPER_BECK = 315
private const val TRAINER_BIRD_KEEPER_BENNY = 304
private const val TRAINER_BIRD_KEEPER_CARTER = 313
private const val TRAINER_BIRD_KEEPER_DONALD = 303
private const val TRAINER_BIRD_KEEPER_MARLON = 316
private const val TRAINER_BIRD_KEEPER_MITCH = 314

internal object Route14_EventScript_Gerald : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_GERALD, Route14.GeraldIntro, Route14.GeraldDefeat))
        return
    ctx.say(Route14.GeraldPostBattle)
  }
}

internal object Route14_EventScript_Donald : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_DONALD, Route14.DonaldIntro, Route14.DonaldDefeat))
        return
    ctx.say(Route14.DonaldPostBattle)
  }
}

internal object Route14_EventScript_Beck : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIRD_KEEPER_BECK, Route14.BeckIntro, Route14.BeckDefeat))
        return
    ctx.say(Route14.BeckPostBattle)
  }
}

internal object Route14_EventScript_Marlon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_MARLON, Route14.MarlonIntro, Route14.MarlonDefeat))
        return
    ctx.say(Route14.MarlonPostBattle)
  }
}

internal object Route14_EventScript_Isaac : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_ISAAC, Route14.IsaacIntro, Route14.IsaacDefeat))
        return
    ctx.say(Route14.IsaacPostBattle)
  }
}

internal object Route14_EventScript_Malik : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_MALIK, Route14.MalikIntro, Route14.MalikDefeat))
        return
    ctx.say(Route14.MalikPostBattle)
  }
}

internal object Route14_EventScript_Mitch : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_MITCH, Route14.MitchIntro, Route14.MitchDefeat))
        return
    ctx.say(Route14.MitchPostBattle)
  }
}

internal object Route14_EventScript_Carter : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_CARTER, Route14.CarterIntro, Route14.CarterDefeat))
        return
    ctx.say(Route14.CarterPostBattle)
  }
}

internal object Route14_EventScript_Lukas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_LUKAS, Route14.LukasIntro, Route14.LukasDefeat))
        return
    ctx.say(Route14.LukasPostBattle)
  }
}

internal object Route14_EventScript_Benny : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_BENNY, Route14.BennyIntro, Route14.BennyDefeat))
        return
    ctx.say(Route14.BennyPostBattle)
  }
}

internal object Route14_EventScript_Jan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TWINS_KIRI_JAN, Route14.JanIntro, Route14.JanDefeat))
        return
    ctx.say(Route14.JanPostBattle)
  }
}

internal object Route14_EventScript_Kiri : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TWINS_KIRI_JAN, Route14.KiriIntro, Route14.KiriDefeat))
        return
    ctx.say(Route14.KiriPostBattle)
  }
}

internal object Route14_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route14.RouteSign)
}

internal val Route14Scripts: Map<String, Script> =
    mapOf(
        "Route14_EventScript_Gerald" to Route14_EventScript_Gerald,
        "Route14_EventScript_Donald" to Route14_EventScript_Donald,
        "Route14_EventScript_Beck" to Route14_EventScript_Beck,
        "Route14_EventScript_Marlon" to Route14_EventScript_Marlon,
        "Route14_EventScript_Isaac" to Route14_EventScript_Isaac,
        "Route14_EventScript_Malik" to Route14_EventScript_Malik,
        "Route14_EventScript_Mitch" to Route14_EventScript_Mitch,
        "Route14_EventScript_Carter" to Route14_EventScript_Carter,
        "Route14_EventScript_Lukas" to Route14_EventScript_Lukas,
        "Route14_EventScript_Benny" to Route14_EventScript_Benny,
        "Route14_EventScript_Jan" to Route14_EventScript_Jan,
        "Route14_EventScript_Kiri" to Route14_EventScript_Kiri,
        "Route14_EventScript_RouteSign" to Route14_EventScript_RouteSign,
    )
