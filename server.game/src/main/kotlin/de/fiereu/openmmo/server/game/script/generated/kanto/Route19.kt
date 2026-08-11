package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route19
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SIS_AND_BRO_LIA_LUC = 490

private const val TRAINER_SWIMMER_FEMALE_ALICE = 277
private const val TRAINER_SWIMMER_FEMALE_ANYA = 276
private const val TRAINER_SWIMMER_FEMALE_CONNIE = 278
private const val TRAINER_SWIMMER_MALE_AXLE = 241
private const val TRAINER_SWIMMER_MALE_DAVID = 239
private const val TRAINER_SWIMMER_MALE_DOUGLAS = 238
private const val TRAINER_SWIMMER_MALE_MATTHEW = 237
private const val TRAINER_SWIMMER_MALE_REECE = 236
private const val TRAINER_SWIMMER_MALE_RICHARD = 235
private const val TRAINER_SWIMMER_MALE_TONY = 240

internal object Route19_EventScript_Reece : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_REECE, Route19.ReeceIntro, Route19.ReeceDefeat))
        return
    ctx.say(Route19.ReecePostBattle)
  }
}

internal object Route19_EventScript_Richard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_RICHARD, Route19.RichardIntro, Route19.RichardDefeat))
        return
    ctx.say(Route19.RichardPostBattle)
  }
}

internal object Route19_EventScript_Tony : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SWIMMER_MALE_TONY, Route19.TonyIntro, Route19.TonyDefeat))
        return
    ctx.say(Route19.TonyPostBattle)
  }
}

internal object Route19_EventScript_Matthew : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_MATTHEW, Route19.MatthewIntro, Route19.MatthewDefeat))
        return
    ctx.say(Route19.MatthewPostBattle)
  }
}

internal object Route19_EventScript_Douglas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_DOUGLAS, Route19.DouglasIntro, Route19.DouglasDefeat))
        return
    ctx.say(Route19.DouglasPostBattle)
  }
}

internal object Route19_EventScript_David : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_DAVID, Route19.DavidIntro, Route19.DavidDefeat))
        return
    ctx.say(Route19.DavidPostBattle)
  }
}

internal object Route19_EventScript_Axle : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SWIMMER_MALE_AXLE, Route19.AxleIntro, Route19.AxleDefeat))
        return
    ctx.say(Route19.AxlePostBattle)
  }
}

internal object Route19_EventScript_Alice : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_ALICE, Route19.AliceIntro, Route19.AliceDefeat))
        return
    ctx.say(Route19.AlicePostBattle)
  }
}

internal object Route19_EventScript_Connie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_CONNIE, Route19.ConnieIntro, Route19.ConnieDefeat))
        return
    ctx.say(Route19.ConniePostBattle)
  }
}

internal object Route19_EventScript_Anya : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_ANYA, Route19.AnyaIntro, Route19.AnyaDefeat))
        return
    ctx.say(Route19.AnyaPostBattle)
  }
}

internal object Route19_EventScript_Lia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SIS_AND_BRO_LIA_LUC, Route19.LiaIntro, Route19.LiaDefeat))
        return
    ctx.say(Route19.LiaPostBattle)
  }
}

internal object Route19_EventScript_Luc : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SIS_AND_BRO_LIA_LUC, Route19.LucIntro, Route19.LucDefeat))
        return
    ctx.say(Route19.LucPostBattle)
  }
}

internal object Route19_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route19.RouteSign)
}

internal val Route19Scripts: Map<String, Script> =
    mapOf(
        "Route19_EventScript_Reece" to Route19_EventScript_Reece,
        "Route19_EventScript_Richard" to Route19_EventScript_Richard,
        "Route19_EventScript_Tony" to Route19_EventScript_Tony,
        "Route19_EventScript_Matthew" to Route19_EventScript_Matthew,
        "Route19_EventScript_Douglas" to Route19_EventScript_Douglas,
        "Route19_EventScript_David" to Route19_EventScript_David,
        "Route19_EventScript_Axle" to Route19_EventScript_Axle,
        "Route19_EventScript_Alice" to Route19_EventScript_Alice,
        "Route19_EventScript_Connie" to Route19_EventScript_Connie,
        "Route19_EventScript_Anya" to Route19_EventScript_Anya,
        "Route19_EventScript_Lia" to Route19_EventScript_Lia,
        "Route19_EventScript_Luc" to Route19_EventScript_Luc,
        "Route19_EventScript_RouteSign" to Route19_EventScript_RouteSign,
    )
