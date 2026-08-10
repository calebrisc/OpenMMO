package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route134
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_AARON = 397
private const val TRAINER_ALEX = 413
private const val TRAINER_HITOSHI = 180
private const val TRAINER_HUDSON = 510
private const val TRAINER_JACK = 172
private const val TRAINER_KELVIN = 507
private const val TRAINER_LAUREL = 463
private const val TRAINER_MARLEY = 508
private const val TRAINER_REYNA = 509

internal object Route134_EventScript_Jack : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JACK, Route134.JackIntro, Route134.JackDefeat)) return
    ctx.say(Route134.JackPostBattle)
  }
}

internal object Route134_EventScript_Laurel : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LAUREL, Route134.LaurelIntro, Route134.LaurelDefeat))
        return
    ctx.say(Route134.LaurelPostBattle)
  }
}

internal object Route134_EventScript_Aaron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_AARON, Route134.AaronIntro, Route134.AaronDefeat)) return
    ctx.say(Route134.AaronPostBattle)
  }
}

internal object Route134_EventScript_Alex : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALEX, Route134.AlexIntro, Route134.AlexDefeat)) return
    ctx.say(Route134.AlexPostBattle)
  }
}

internal object Route134_EventScript_Hitoshi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HITOSHI, Route134.HitoshiIntro, Route134.HitoshiDefeat))
        return
    ctx.say(Route134.HitoshiPostBattle)
  }
}

internal object Route134_EventScript_Marley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MARLEY, Route134.MarleyIntro, Route134.MarleyDefeat))
        return
    ctx.say(Route134.MarleyPostBattle)
  }
}

internal object Route134_EventScript_Kelvin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KELVIN, Route134.KelvinIntro, Route134.KelvinDefeat))
        return
    ctx.say(Route134.KelvinPostBattle)
  }
}

internal object Route134_EventScript_Reyna : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_REYNA, Route134.ReynaIntro, Route134.ReynaDefeat)) return
    ctx.say(Route134.ReynaPostBattle)
  }
}

internal object Route134_EventScript_Hudson : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HUDSON, Route134.HudsonIntro, Route134.HudsonDefeat))
        return
    ctx.say(Route134.HudsonPostBattle)
  }
}

internal object Route134_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CARBOS)
}

internal object Route134_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.STAR_PIECE)
}

internal val Route134Scripts: Map<String, Script> =
    mapOf(
        "Route134_EventScript_Jack" to Route134_EventScript_Jack,
        "Route134_EventScript_Laurel" to Route134_EventScript_Laurel,
        "Route134_EventScript_Aaron" to Route134_EventScript_Aaron,
        "Route134_EventScript_Alex" to Route134_EventScript_Alex,
        "Route134_EventScript_Hitoshi" to Route134_EventScript_Hitoshi,
        "Route134_EventScript_Marley" to Route134_EventScript_Marley,
        "Route134_EventScript_Kelvin" to Route134_EventScript_Kelvin,
        "Route134_EventScript_Reyna" to Route134_EventScript_Reyna,
        "Route134_EventScript_Hudson" to Route134_EventScript_Hudson,
        "Route134_EventScript_ItemCarbos" to Route134_EventScript_ItemCarbos,
        "Route134_EventScript_ItemStarPiece" to Route134_EventScript_ItemStarPiece,
    )
