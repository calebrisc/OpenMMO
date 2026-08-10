package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route10
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_HIKER_CLARK = 187
private const val TRAINER_HIKER_TRENT = 188
private const val TRAINER_PICNICKER_CAROL = 157
private const val TRAINER_PICNICKER_HEIDI = 156
private const val TRAINER_POKEMANIAC_HERMAN = 163
private const val TRAINER_POKEMANIAC_MARK = 162

internal object Route10_EventScript_Carol : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_CAROL, Route10.CarolIntro, Route10.CarolDefeat))
        return
    ctx.say(Route10.CarolPostBattle)
  }
}

internal object Route10_EventScript_Clark : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIKER_CLARK, Route10.ClarkIntro, Route10.ClarkDefeat))
        return
    ctx.say(Route10.ClarkPostBattle)
  }
}

internal object Route10_EventScript_Herman : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_POKEMANIAC_HERMAN, Route10.HermanIntro, Route10.HermanDefeat))
        return
    ctx.say(Route10.HermanPostBattle)
  }
}

internal object Route10_EventScript_Trent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIKER_TRENT, Route10.TrentIntro, Route10.TrentDefeat))
        return
    ctx.say(Route10.TrentPostBattle)
  }
}

internal object Route10_EventScript_Mark : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_POKEMANIAC_MARK, Route10.MarkIntro, Route10.MarkDefeat))
        return
    ctx.say(Route10.MarkPostBattle)
  }
}

internal object Route10_EventScript_Heidi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_HEIDI, Route10.HeidiIntro, Route10.HeidiDefeat))
        return
    ctx.say(Route10.HeidiPostBattle)
  }
}

internal object Route10_EventScript_SouthRockTunnelSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route10.RockTunnel)
}

internal object Route10_EventScript_PowerPlantSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route10.PowerPlant)
}

internal object Route10_EventScript_NorthRockTunnelSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route10.RockTunnelDetourToLavender)
}

internal val Route10Scripts: Map<String, Script> =
    mapOf(
        "Route10_EventScript_Carol" to Route10_EventScript_Carol,
        "Route10_EventScript_Clark" to Route10_EventScript_Clark,
        "Route10_EventScript_Herman" to Route10_EventScript_Herman,
        "Route10_EventScript_Trent" to Route10_EventScript_Trent,
        "Route10_EventScript_Mark" to Route10_EventScript_Mark,
        "Route10_EventScript_Heidi" to Route10_EventScript_Heidi,
        "Route10_EventScript_SouthRockTunnelSign" to Route10_EventScript_SouthRockTunnelSign,
        "Route10_EventScript_PowerPlantSign" to Route10_EventScript_PowerPlantSign,
        "Route10_EventScript_NorthRockTunnelSign" to Route10_EventScript_NorthRockTunnelSign,
    )
