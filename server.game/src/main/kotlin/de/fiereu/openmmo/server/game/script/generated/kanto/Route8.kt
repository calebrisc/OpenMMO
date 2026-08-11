package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route8
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TWINS_ELI_ANNE = 484

private const val TRAINER_BIKER_JAREN = 536
private const val TRAINER_BIKER_RICARDO = 535
private const val TRAINER_GAMER_RICH = 264
private const val TRAINER_GAMER_STAN = 262
private const val TRAINER_LASS_ANDREA = 129
private const val TRAINER_LASS_JULIA = 131
private const val TRAINER_LASS_MEGAN = 130
private const val TRAINER_LASS_PAIGE = 128
private const val TRAINER_SUPER_NERD_AIDAN = 171
private const val TRAINER_SUPER_NERD_GLENN = 172
private const val TRAINER_SUPER_NERD_LESLIE = 173

internal object Route8_EventScript_Julia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_JULIA, Route8.JuliaIntro, Route8.JuliaDefeat)) return
    ctx.say(Route8.JuliaPostBattle)
  }
}

internal object Route8_EventScript_Rich : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GAMER_RICH, Route8.RichIntro, Route8.RichDefeat)) return
    ctx.say(Route8.RichPostBattle)
  }
}

internal object Route8_EventScript_Glenn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SUPER_NERD_GLENN, Route8.GlennIntro, Route8.GlennDefeat))
        return
    ctx.say(Route8.GlennPostBattle)
  }
}

internal object Route8_EventScript_Paige : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_PAIGE, Route8.PaigeIntro, Route8.PaigeDefeat)) return
    ctx.say(Route8.PaigePostBattle)
  }
}

internal object Route8_EventScript_Leslie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SUPER_NERD_LESLIE, Route8.LeslieIntro, Route8.LeslieDefeat))
        return
    ctx.say(Route8.LesliePostBattle)
  }
}

internal object Route8_EventScript_Andrea : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_ANDREA, Route8.AndreaIntro, Route8.AndreaDefeat))
        return
    ctx.say(Route8.AndreaPostBattle)
  }
}

internal object Route8_EventScript_Megan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_MEGAN, Route8.MeganIntro, Route8.MeganDefeat)) return
    ctx.say(Route8.MeganPostBattle)
  }
}

internal object Route8_EventScript_Stan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GAMER_STAN, Route8.StanIntro, Route8.StanDefeat)) return
    ctx.say(Route8.StanPostBattle)
  }
}

internal object Route8_EventScript_Aidan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SUPER_NERD_AIDAN, Route8.AidanIntro, Route8.AidanDefeat))
        return
    ctx.say(Route8.AidanPostBattle)
  }
}

internal object Route8_EventScript_Eli : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TWINS_ELI_ANNE, Route8.EliIntro, Route8.EliDefeat)) return
    ctx.say(Route8.EliPostBattle)
  }
}

internal object Route8_EventScript_Anne : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TWINS_ELI_ANNE, Route8.AnneIntro, Route8.AnneDefeat))
        return
    ctx.say(Route8.AnnePostBattle)
  }
}

internal object Route8_EventScript_Ricardo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_RICARDO, Route8.RicardoIntro, Route8.RicardoDefeat))
        return
    ctx.say(Route8.RicardoPostBattle)
  }
}

internal object Route8_EventScript_Jaren : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_JAREN, Route8.JarenIntro, Route8.JarenDefeat)) return
    ctx.say(Route8.JarenPostBattle)
  }
}

internal object Route8_EventScript_UndergroundPathSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route8.UndergroundPathSign)
}

internal val Route8Scripts: Map<String, Script> =
    mapOf(
        "Route8_EventScript_Julia" to Route8_EventScript_Julia,
        "Route8_EventScript_Rich" to Route8_EventScript_Rich,
        "Route8_EventScript_Glenn" to Route8_EventScript_Glenn,
        "Route8_EventScript_Paige" to Route8_EventScript_Paige,
        "Route8_EventScript_Leslie" to Route8_EventScript_Leslie,
        "Route8_EventScript_Andrea" to Route8_EventScript_Andrea,
        "Route8_EventScript_Megan" to Route8_EventScript_Megan,
        "Route8_EventScript_Stan" to Route8_EventScript_Stan,
        "Route8_EventScript_Aidan" to Route8_EventScript_Aidan,
        "Route8_EventScript_Eli" to Route8_EventScript_Eli,
        "Route8_EventScript_Anne" to Route8_EventScript_Anne,
        "Route8_EventScript_Ricardo" to Route8_EventScript_Ricardo,
        "Route8_EventScript_Jaren" to Route8_EventScript_Jaren,
        "Route8_EventScript_UndergroundPathSign" to Route8_EventScript_UndergroundPathSign,
    )
