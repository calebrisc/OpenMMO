package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route24
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BUG_CATCHER_CALE = 110
private const val TRAINER_CAMPER_ETHAN = 144
private const val TRAINER_CAMPER_SHANE = 143
private const val TRAINER_LASS_ALI = 123
private const val TRAINER_LASS_RELI = 122
private const val TRAINER_YOUNGSTER_TIMMY = 92

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_ROUTE24, 1, Route24_EventScript_RocketPostBattle
 * msgbox Route24_Text_JustEarnedFabulousPrize
 * checkitemspace ITEM_NUGGET
 * goto_if_eq VAR_RESULT, FALSE, Route24_EventScript_NoRoomForNugget
 * call Route24_EventScript_BattleRocket
 * release
 * end
 * ```
 */
internal object Route24_EventScript_Rocket : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Rocket")
}

internal object Route24_EventScript_Ethan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_ETHAN, Route24.EthanIntro, Route24.EthanDefeat))
        return
    ctx.say(Route24.EthanPostBattle)
  }
}

internal object Route24_EventScript_Reli : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_RELI, Route24.ReliIntro, Route24.ReliDefeat)) return
    ctx.say(Route24.ReliPostBattle)
  }
}

internal object Route24_EventScript_Timmy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_TIMMY, Route24.TimmyIntro, Route24.TimmyDefeat))
        return
    ctx.say(Route24.TimmyPostBattle)
  }
}

internal object Route24_EventScript_Ali : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_ALI, Route24.AliIntro, Route24.AliDefeat)) return
    ctx.say(Route24.AliPostBattle)
  }
}

internal object Route24_EventScript_Cale : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BUG_CATCHER_CALE, Route24.CaleIntro, Route24.CaleDefeat))
        return
    ctx.say(Route24.CalePostBattle)
  }
}

internal object Route24_EventScript_Shane : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_SHANE, Route24.ShaneIntro, Route24.ShaneDefeat))
        return
    ctx.say(Route24.ShanePostBattle)
  }
}

internal object Route24_EventScript_ItemTM45 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM45)
}

internal val Route24Scripts: Map<String, Script> =
    mapOf(
        "Route24_EventScript_Rocket" to Route24_EventScript_Rocket,
        "Route24_EventScript_Ethan" to Route24_EventScript_Ethan,
        "Route24_EventScript_Reli" to Route24_EventScript_Reli,
        "Route24_EventScript_Timmy" to Route24_EventScript_Timmy,
        "Route24_EventScript_Ali" to Route24_EventScript_Ali,
        "Route24_EventScript_Cale" to Route24_EventScript_Cale,
        "Route24_EventScript_Shane" to Route24_EventScript_Shane,
        "Route24_EventScript_ItemTM45" to Route24_EventScript_ItemTM45,
    )
