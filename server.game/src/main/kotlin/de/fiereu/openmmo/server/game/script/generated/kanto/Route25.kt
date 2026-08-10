package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route25
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CAMPER_FLINT = 471
private const val TRAINER_HIKER_FRANKLIN = 182
private const val TRAINER_HIKER_NOB = 183
private const val TRAINER_HIKER_WAYNE = 184
private const val TRAINER_LASS_HALEY = 125
private const val TRAINER_PICNICKER_KELSEY = 153
private const val TRAINER_YOUNGSTER_CHAD = 95
private const val TRAINER_YOUNGSTER_DAN = 94
private const val TRAINER_YOUNGSTER_JOEY = 93

internal object Route25_EventScript_Franklin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_FRANKLIN, Route25.FranklinIntro, Route25.FranklinDefeat))
        return
    ctx.say(Route25.FranklinPostBattle)
  }
}

internal object Route25_EventScript_Joey : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_JOEY, Route25.JoeyIntro, Route25.JoeyDefeat))
        return
    ctx.say(Route25.JoeyPostBattle)
  }
}

internal object Route25_EventScript_Wayne : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIKER_WAYNE, Route25.WayneIntro, Route25.WayneDefeat))
        return
    ctx.say(Route25.WaynePostBattle)
  }
}

internal object Route25_EventScript_Dan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_DAN, Route25.DanIntro, Route25.DanDefeat)) return
    ctx.say(Route25.DanPostBattle)
  }
}

internal object Route25_EventScript_Kelsey : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_KELSEY, Route25.KelseyIntro, Route25.KelseyDefeat))
        return
    ctx.say(Route25.KelseyPostBattle)
  }
}

internal object Route25_EventScript_Nob : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIKER_NOB, Route25.NobIntro, Route25.NobDefeat)) return
    ctx.say(Route25.NobPostBattle)
  }
}

internal object Route25_EventScript_Flint : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_FLINT, Route25.FlintIntro, Route25.FlintDefeat))
        return
    ctx.say(Route25.FlintPostBattle)
  }
}

internal object Route25_EventScript_Chad : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_CHAD, Route25.ChadIntro, Route25.ChadDefeat))
        return
    ctx.say(Route25.ChadPostBattle)
  }
}

internal object Route25_EventScript_Haley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_HALEY, Route25.HaleyIntro, Route25.HaleyDefeat))
        return
    ctx.say(Route25.HaleyPostBattle)
  }
}

internal object Route25_EventScript_ItemTM43 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM43)
}

internal object Route25_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route25.MistyHighHopesAboutThisPlace)
}

internal object Route25_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route25.AreYouHereAlone)
}

internal object Route25_EventScript_SeaCottageSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route25.SeaCottageSign)
}

internal val Route25Scripts: Map<String, Script> =
    mapOf(
        "Route25_EventScript_Franklin" to Route25_EventScript_Franklin,
        "Route25_EventScript_Joey" to Route25_EventScript_Joey,
        "Route25_EventScript_Wayne" to Route25_EventScript_Wayne,
        "Route25_EventScript_Dan" to Route25_EventScript_Dan,
        "Route25_EventScript_Kelsey" to Route25_EventScript_Kelsey,
        "Route25_EventScript_Nob" to Route25_EventScript_Nob,
        "Route25_EventScript_Flint" to Route25_EventScript_Flint,
        "Route25_EventScript_Chad" to Route25_EventScript_Chad,
        "Route25_EventScript_Haley" to Route25_EventScript_Haley,
        "Route25_EventScript_ItemTM43" to Route25_EventScript_ItemTM43,
        "Route25_EventScript_Beauty" to Route25_EventScript_Beauty,
        "Route25_EventScript_Man" to Route25_EventScript_Man,
        "Route25_EventScript_SeaCottageSign" to Route25_EventScript_SeaCottageSign,
    )
