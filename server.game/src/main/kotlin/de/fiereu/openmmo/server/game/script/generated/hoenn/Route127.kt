package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route127
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_AIDAN = 674
private const val TRAINER_ATHENA = 577
private const val TRAINER_CAMDEN = 374
private const val TRAINER_DONNY = 384
private const val TRAINER_HENRY = 668
private const val TRAINER_JONAH = 667
private const val TRAINER_ROGER = 669

internal object Route127_EventScript_Camden : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMDEN, Route127.CamdenIntro, Route127.CamdenDefeat))
        return
    ctx.say(Route127.CamdenPostBattle)
  }
}

internal object Route127_EventScript_Donny : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DONNY, Route127.DonnyIntro, Route127.DonnyDefeat)) return
    ctx.say(Route127.DonnyPostBattle)
  }
}

internal object Route127_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ZINC)
}

internal object Route127_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CARBOS)
}

internal object Route127_EventScript_Jonah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JONAH, Route127.JonahIntro, Route127.JonahDefeat)) return
    ctx.say(Route127.JonahPostBattle)
  }
}

internal object Route127_EventScript_Roger : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ROGER, Route127.RogerIntro, Route127.RogerDefeat)) return
    ctx.say(Route127.RogerPostBattle)
  }
}

internal object Route127_EventScript_Henry : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HENRY, Route127.HenryIntro, Route127.HenryDefeat)) return
    ctx.say(Route127.HenryPostBattle)
  }
}

internal object Route127_EventScript_Aidan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_AIDAN, Route127.AidanIntro, Route127.AidanDefeat)) return
    ctx.say(Route127.AidanPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KOJI_1, Route127_Text_KojiIntro, Route127_Text_KojiDefeat, Route127_EventScript_RegisterKoji
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route127_EventScript_RematchKoji
 * msgbox Route127_Text_KojiPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route127_EventScript_Koji : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Koji")
}

internal object Route127_EventScript_Athena : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ATHENA, Route127.AthenaIntro, Route127.AthenaDefeat))
        return
    ctx.say(Route127.AthenaPostBattle)
  }
}

internal object Route127_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal val Route127Scripts: Map<String, Script> =
    mapOf(
        "Route127_EventScript_Camden" to Route127_EventScript_Camden,
        "Route127_EventScript_Donny" to Route127_EventScript_Donny,
        "Route127_EventScript_ItemZinc" to Route127_EventScript_ItemZinc,
        "Route127_EventScript_ItemCarbos" to Route127_EventScript_ItemCarbos,
        "Route127_EventScript_Jonah" to Route127_EventScript_Jonah,
        "Route127_EventScript_Roger" to Route127_EventScript_Roger,
        "Route127_EventScript_Henry" to Route127_EventScript_Henry,
        "Route127_EventScript_Aidan" to Route127_EventScript_Aidan,
        "Route127_EventScript_Koji" to Route127_EventScript_Koji,
        "Route127_EventScript_Athena" to Route127_EventScript_Athena,
        "Route127_EventScript_ItemRareCandy" to Route127_EventScript_ItemRareCandy,
    )
