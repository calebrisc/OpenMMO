package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route125
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_KIM_AND_IRIS = 678

private const val TRAINER_AURON = 506
private const val TRAINER_NOLEN = 161
private const val TRAINER_PRESLEY = 403
private const val TRAINER_SHARON = 452
private const val TRAINER_STAN = 162
private const val TRAINER_TANYA = 451

internal object Route125_EventScript_Nolen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_NOLEN, Route125.NolenIntro, Route125.NolenDefeat)) return
    ctx.say(Route125.NolenPostBattle)
  }
}

internal object Route125_EventScript_Stan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_STAN, Route125.StanIntro, Route125.StanDefeat)) return
    ctx.say(Route125.StanPostBattle)
  }
}

internal object Route125_EventScript_Tanya : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TANYA, Route125.TanyaIntro, Route125.TanyaDefeat)) return
    ctx.say(Route125.TanyaPostBattle)
  }
}

internal object Route125_EventScript_Sharon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SHARON, Route125.SharonIntro, Route125.SharonDefeat))
        return
    ctx.say(Route125.SharonPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ERNEST_1, Route125_Text_ErnestIntro, Route125_Text_ErnestDefeat, Route125_EventScript_RegisterErnest
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route125_EventScript_RematchErnest
 * msgbox Route125_Text_ErnestPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route125_EventScript_Ernest : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Ernest")
}

internal object Route125_EventScript_Kim : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KIM_AND_IRIS, Route125.KimIntro, Route125.KimDefeat))
        return
    ctx.say(Route125.KimPostBattle)
  }
}

internal object Route125_EventScript_Iris : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KIM_AND_IRIS, Route125.IrisIntro, Route125.IrisDefeat))
        return
    ctx.say(Route125.IrisPostBattle)
  }
}

internal object Route125_EventScript_Presley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PRESLEY, Route125.PresleyIntro, Route125.PresleyDefeat))
        return
    ctx.say(Route125.PresleyPostBattle)
  }
}

internal object Route125_EventScript_Auron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_AURON, Route125.AuronIntro, Route125.AuronDefeat)) return
    ctx.say(Route125.AuronPostBattle)
  }
}

internal object Route125_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.BIG_PEARL)
}

internal val Route125Scripts: Map<String, Script> =
    mapOf(
        "Route125_EventScript_Nolen" to Route125_EventScript_Nolen,
        "Route125_EventScript_Stan" to Route125_EventScript_Stan,
        "Route125_EventScript_Tanya" to Route125_EventScript_Tanya,
        "Route125_EventScript_Sharon" to Route125_EventScript_Sharon,
        "Route125_EventScript_Ernest" to Route125_EventScript_Ernest,
        "Route125_EventScript_Kim" to Route125_EventScript_Kim,
        "Route125_EventScript_Iris" to Route125_EventScript_Iris,
        "Route125_EventScript_Presley" to Route125_EventScript_Presley,
        "Route125_EventScript_Auron" to Route125_EventScript_Auron,
        "Route125_EventScript_ItemBigPearl" to Route125_EventScript_ItemBigPearl,
    )
