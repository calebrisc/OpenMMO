package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route13
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BEAUTY_LOLA = 268
private const val TRAINER_BEAUTY_SHEILA = 269
private const val TRAINER_BIKER_JARED = 195
private const val TRAINER_BIRD_KEEPER_PERRY = 301
private const val TRAINER_BIRD_KEEPER_ROBERT = 302
private const val TRAINER_BIRD_KEEPER_SEBASTIAN = 300
private const val TRAINER_PICNICKER_ALMA = 466
private const val TRAINER_PICNICKER_GWEN = 469
private const val TRAINER_PICNICKER_SUSIE = 467
private const val TRAINER_PICNICKER_VALERIE = 468

internal object Route13_EventScript_Alma : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_ALMA, Route13.AlmaIntro, Route13.AlmaDefeat))
        return
    ctx.say(Route13.AlmaPostBattle)
  }
}

internal object Route13_EventScript_Sebastian : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_SEBASTIAN, Route13.SebastianIntro, Route13.SebastianDefeat))
        return
    ctx.say(Route13.SebastianPostBattle)
  }
}

internal object Route13_EventScript_Susie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_SUSIE, Route13.SusieIntro, Route13.SusieDefeat))
        return
    ctx.say(Route13.SusiePostBattle)
  }
}

internal object Route13_EventScript_Sheila : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BEAUTY_SHEILA, Route13.SheilaIntro, Route13.SheilaDefeat))
        return
    ctx.say(Route13.SheilaPostBattle)
  }
}

internal object Route13_EventScript_Lola : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BEAUTY_LOLA, Route13.LolaIntro, Route13.LolaDefeat)) return
    ctx.say(Route13.LolaPostBattle)
  }
}

internal object Route13_EventScript_Valerie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_VALERIE, Route13.ValerieIntro, Route13.ValerieDefeat))
        return
    ctx.say(Route13.ValeriePostBattle)
  }
}

internal object Route13_EventScript_Gwen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_GWEN, Route13.GwenIntro, Route13.GwenDefeat))
        return
    ctx.say(Route13.GwenPostBattle)
  }
}

internal object Route13_EventScript_Robert : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_ROBERT, Route13.RobertIntro, Route13.RobertDefeat))
        return
    ctx.say(Route13.RobertPostBattle)
  }
}

internal object Route13_EventScript_Perry : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_PERRY, Route13.PerryIntro, Route13.PerryDefeat))
        return
    ctx.say(Route13.PerryPostBattle)
  }
}

internal object Route13_EventScript_Jared : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_JARED, Route13.JaredIntro, Route13.JaredDefeat))
        return
    ctx.say(Route13.JaredPostBattle)
  }
}

internal object Route13_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route13.RouteSign)
}

internal object Route13_EventScript_TrainerTips2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route13.SelectToSwitchItems)
}

internal object Route13_EventScript_TrainerTips1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route13.LookToLeftOfThatPost)
}

internal val Route13Scripts: Map<String, Script> =
    mapOf(
        "Route13_EventScript_Alma" to Route13_EventScript_Alma,
        "Route13_EventScript_Sebastian" to Route13_EventScript_Sebastian,
        "Route13_EventScript_Susie" to Route13_EventScript_Susie,
        "Route13_EventScript_Sheila" to Route13_EventScript_Sheila,
        "Route13_EventScript_Lola" to Route13_EventScript_Lola,
        "Route13_EventScript_Valerie" to Route13_EventScript_Valerie,
        "Route13_EventScript_Gwen" to Route13_EventScript_Gwen,
        "Route13_EventScript_Robert" to Route13_EventScript_Robert,
        "Route13_EventScript_Perry" to Route13_EventScript_Perry,
        "Route13_EventScript_Jared" to Route13_EventScript_Jared,
        "Route13_EventScript_RouteSign" to Route13_EventScript_RouteSign,
        "Route13_EventScript_TrainerTips2" to Route13_EventScript_TrainerTips2,
        "Route13_EventScript_TrainerTips1" to Route13_EventScript_TrainerTips1,
    )
