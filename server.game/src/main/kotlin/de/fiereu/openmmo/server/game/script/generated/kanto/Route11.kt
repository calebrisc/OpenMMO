package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route11
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ENGINEER_BERNIE = 222
private const val TRAINER_ENGINEER_BRAXTON = 221
private const val TRAINER_GAMER_DARIAN = 261
private const val TRAINER_GAMER_DIRK = 260
private const val TRAINER_GAMER_HUGO = 258
private const val TRAINER_GAMER_JASPER = 259
private const val TRAINER_YOUNGSTER_DAVE = 100
private const val TRAINER_YOUNGSTER_DILLON = 98
private const val TRAINER_YOUNGSTER_EDDIE = 97
private const val TRAINER_YOUNGSTER_YASU = 99

internal object Route11_EventScript_Eddie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_EDDIE, Route11.EddieIntro, Route11.EddieDefeat))
        return
    ctx.say(Route11.EddiePostBattle)
  }
}

internal object Route11_EventScript_Hugo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GAMER_HUGO, Route11.HugoIntro, Route11.HugoDefeat)) return
    ctx.say(Route11.HugoPostBattle)
  }
}

internal object Route11_EventScript_Dillon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNGSTER_DILLON, Route11.DillonIntro, Route11.DillonDefeat))
        return
    ctx.say(Route11.DillonPostBattle)
  }
}

internal object Route11_EventScript_Dave : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_DAVE, Route11.DaveIntro, Route11.DaveDefeat))
        return
    ctx.say(Route11.DavePostBattle)
  }
}

internal object Route11_EventScript_Jasper : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GAMER_JASPER, Route11.JasperIntro, Route11.JasperDefeat))
        return
    ctx.say(Route11.JasperPostBattle)
  }
}

internal object Route11_EventScript_Darian : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GAMER_DARIAN, Route11.DarianIntro, Route11.DarianDefeat))
        return
    ctx.say(Route11.DarianPostBattle)
  }
}

internal object Route11_EventScript_Braxton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ENGINEER_BRAXTON, Route11.BraxtonIntro, Route11.BraxtonDefeat))
        return
    ctx.say(Route11.BraxtonPostBattle)
  }
}

internal object Route11_EventScript_Yasu : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_YASU, Route11.YasuIntro, Route11.YasuDefeat))
        return
    ctx.say(Route11.YasuPostBattle)
  }
}

internal object Route11_EventScript_Dirk : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GAMER_DIRK, Route11.DirkIntro, Route11.DirkDefeat)) return
    ctx.say(Route11.DirkPostBattle)
  }
}

internal object Route11_EventScript_Bernie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ENGINEER_BERNIE, Route11.BernieIntro, Route11.BernieDefeat))
        return
    ctx.say(Route11.BerniePostBattle)
  }
}

internal object Route11_EventScript_ItemXDefend : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.X_DEFEND)
}

internal object Route11_EventScript_ItemGreatBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.GREAT_BALL)
}

internal object Route11_EventScript_ItemAwakening : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.AWAKENING)
}

internal object Route11_EventScript_DiglettsCaveSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route11.DiglettsCave)
}

internal val Route11Scripts: Map<String, Script> =
    mapOf(
        "Route11_EventScript_Eddie" to Route11_EventScript_Eddie,
        "Route11_EventScript_Hugo" to Route11_EventScript_Hugo,
        "Route11_EventScript_Dillon" to Route11_EventScript_Dillon,
        "Route11_EventScript_Dave" to Route11_EventScript_Dave,
        "Route11_EventScript_Jasper" to Route11_EventScript_Jasper,
        "Route11_EventScript_Darian" to Route11_EventScript_Darian,
        "Route11_EventScript_Braxton" to Route11_EventScript_Braxton,
        "Route11_EventScript_Yasu" to Route11_EventScript_Yasu,
        "Route11_EventScript_Dirk" to Route11_EventScript_Dirk,
        "Route11_EventScript_Bernie" to Route11_EventScript_Bernie,
        "Route11_EventScript_ItemXDefend" to Route11_EventScript_ItemXDefend,
        "Route11_EventScript_ItemGreatBall" to Route11_EventScript_ItemGreatBall,
        "Route11_EventScript_ItemAwakening" to Route11_EventScript_ItemAwakening,
        "Route11_EventScript_DiglettsCaveSign" to Route11_EventScript_DiglettsCaveSign,
    )
