package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route131
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_RELI_AND_IAN = 686

private const val TRAINER_HERMAN = 167
private const val TRAINER_KARA = 457
private const val TRAINER_KEVIN = 171
private const val TRAINER_RICHARD = 166
private const val TRAINER_SUSIE = 456
private const val TRAINER_TALIA = 385

internal object Route131_EventScript_Richard : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RICHARD, Route131.RichardIntro, Route131.RichardDefeat))
        return
    ctx.say(Route131.RichardPostBattle)
  }
}

internal object Route131_EventScript_Herman : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HERMAN, Route131.HermanIntro, Route131.HermanDefeat))
        return
    ctx.say(Route131.HermanPostBattle)
  }
}

internal object Route131_EventScript_Susie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SUSIE, Route131.SusieIntro, Route131.SusieDefeat)) return
    ctx.say(Route131.SusiePostBattle)
  }
}

internal object Route131_EventScript_Kara : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KARA, Route131.KaraIntro, Route131.KaraDefeat)) return
    ctx.say(Route131.KaraPostBattle)
  }
}

internal object Route131_EventScript_Reli : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RELI_AND_IAN, Route131.ReliIntro, Route131.ReliDefeat))
        return
    ctx.say(Route131.ReliPostBattle)
  }
}

internal object Route131_EventScript_Ian : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RELI_AND_IAN, Route131.IanIntro, Route131.IanDefeat))
        return
    ctx.say(Route131.IanPostBattle)
  }
}

internal object Route131_EventScript_Kevin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KEVIN, Route131.KevinIntro, Route131.KevinDefeat)) return
    ctx.say(Route131.KevinPostBattle)
  }
}

internal object Route131_EventScript_Talia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TALIA, Route131.TaliaIntro, Route131.TaliaDefeat)) return
    ctx.say(Route131.TaliaPostBattle)
  }
}

internal val Route131Scripts: Map<String, Script> =
    mapOf(
        "Route131_EventScript_Richard" to Route131_EventScript_Richard,
        "Route131_EventScript_Herman" to Route131_EventScript_Herman,
        "Route131_EventScript_Susie" to Route131_EventScript_Susie,
        "Route131_EventScript_Kara" to Route131_EventScript_Kara,
        "Route131_EventScript_Reli" to Route131_EventScript_Reli,
        "Route131_EventScript_Ian" to Route131_EventScript_Ian,
        "Route131_EventScript_Kevin" to Route131_EventScript_Kevin,
        "Route131_EventScript_Talia" to Route131_EventScript_Talia,
    )
