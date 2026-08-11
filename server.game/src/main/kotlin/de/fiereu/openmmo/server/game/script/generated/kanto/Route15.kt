package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route15
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CRUSH_KIN_RON_MYA = 488

private const val TRAINER_BEAUTY_GRACE = 273
private const val TRAINER_BEAUTY_OLIVIA = 274
private const val TRAINER_BIKER_ALEX = 198
private const val TRAINER_BIKER_ERNEST = 197
private const val TRAINER_BIRD_KEEPER_CHESTER = 306
private const val TRAINER_BIRD_KEEPER_EDWIN = 305
private const val TRAINER_PICNICKER_BECKY = 480
private const val TRAINER_PICNICKER_CELIA = 481
private const val TRAINER_PICNICKER_KINDRA = 479
private const val TRAINER_PICNICKER_YAZMIN = 478

internal object Route15_EventScript_Yazmin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_YAZMIN, Route15.YazminIntro, Route15.YazminDefeat))
        return
    ctx.say(Route15.YazminPostBattle)
  }
}

internal object Route15_EventScript_Edwin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_EDWIN, Route15.EdwinIntro, Route15.EdwinDefeat))
        return
    ctx.say(Route15.EdwinPostBattle)
  }
}

internal object Route15_EventScript_Chester : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_CHESTER, Route15.ChesterIntro, Route15.ChesterDefeat))
        return
    ctx.say(Route15.ChesterPostBattle)
  }
}

internal object Route15_EventScript_Kindra : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_KINDRA, Route15.KindraIntro, Route15.KindraDefeat))
        return
    ctx.say(Route15.KindraPostBattle)
  }
}

internal object Route15_EventScript_Olivia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BEAUTY_OLIVIA, Route15.OliviaIntro, Route15.OliviaDefeat))
        return
    ctx.say(Route15.OliviaPostBattle)
  }
}

internal object Route15_EventScript_Alex : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_ALEX, Route15.AlexIntro, Route15.AlexDefeat)) return
    ctx.say(Route15.AlexPostBattle)
  }
}

internal object Route15_EventScript_Ernest : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_ERNEST, Route15.ErnestIntro, Route15.ErnestDefeat))
        return
    ctx.say(Route15.ErnestPostBattle)
  }
}

internal object Route15_EventScript_Becky : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_BECKY, Route15.BeckyIntro, Route15.BeckyDefeat))
        return
    ctx.say(Route15.BeckyPostBattle)
  }
}

internal object Route15_EventScript_Grace : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BEAUTY_GRACE, Route15.GraceIntro, Route15.GraceDefeat))
        return
    ctx.say(Route15.GracePostBattle)
  }
}

internal object Route15_EventScript_Celia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_CELIA, Route15.CeliaIntro, Route15.CeliaDefeat))
        return
    ctx.say(Route15.CeliaPostBattle)
  }
}

internal object Route15_EventScript_ItemTM18 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM18)
}

internal object Route15_EventScript_Ron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CRUSH_KIN_RON_MYA, Route15.RonIntro, Route15.RonDefeat))
        return
    ctx.say(Route15.RonPostBattle)
  }
}

internal object Route15_EventScript_Mya : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CRUSH_KIN_RON_MYA, Route15.MyaIntro, Route15.MyaDefeat))
        return
    ctx.say(Route15.MyaPostBattle)
  }
}

internal object Route15_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route15.RouteSign)
}

internal val Route15Scripts: Map<String, Script> =
    mapOf(
        "Route15_EventScript_Yazmin" to Route15_EventScript_Yazmin,
        "Route15_EventScript_Edwin" to Route15_EventScript_Edwin,
        "Route15_EventScript_Chester" to Route15_EventScript_Chester,
        "Route15_EventScript_Kindra" to Route15_EventScript_Kindra,
        "Route15_EventScript_Olivia" to Route15_EventScript_Olivia,
        "Route15_EventScript_Alex" to Route15_EventScript_Alex,
        "Route15_EventScript_Ernest" to Route15_EventScript_Ernest,
        "Route15_EventScript_Becky" to Route15_EventScript_Becky,
        "Route15_EventScript_Grace" to Route15_EventScript_Grace,
        "Route15_EventScript_Celia" to Route15_EventScript_Celia,
        "Route15_EventScript_ItemTM18" to Route15_EventScript_ItemTM18,
        "Route15_EventScript_Ron" to Route15_EventScript_Ron,
        "Route15_EventScript_Mya" to Route15_EventScript_Mya,
        "Route15_EventScript_RouteSign" to Route15_EventScript_RouteSign,
    )
