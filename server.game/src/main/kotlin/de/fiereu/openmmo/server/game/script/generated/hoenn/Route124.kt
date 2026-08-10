package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route124
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CHAD = 174
private const val TRAINER_DECLAN = 15
private const val TRAINER_GRACE = 450
private const val TRAINER_ISABELLA = 595
private const val TRAINER_ROLAND = 160
private const val TRAINER_SPENCER = 159

internal object Route124_EventScript_Spencer : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SPENCER, Route124.SpencerIntro, Route124.SpencerDefeat))
        return
    ctx.say(Route124.SpencerPostBattle)
  }
}

internal object Route124_EventScript_Roland : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ROLAND, Route124.RolandIntro, Route124.RolandDefeat))
        return
    ctx.say(Route124.RolandPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JENNY_1, Route124_Text_JennyIntro, Route124_Text_JennyDefeat, Route124_EventScript_RegisterJenny
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route124_EventScript_RematchJenny
 * msgbox Route124_Text_JennyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route124_EventScript_Jenny : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Jenny")
}

internal object Route124_EventScript_Grace : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GRACE, Route124.GraceIntro, Route124.GraceDefeat)) return
    ctx.say(Route124.GracePostBattle)
  }
}

internal object Route124_EventScript_Chad : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CHAD, Route124.ChadIntro, Route124.ChadDefeat)) return
    ctx.say(Route124.ChadPostBattle)
  }
}

internal object Route124_EventScript_ItemRedShard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RED_SHARD)
}

internal object Route124_EventScript_ItemBlueShard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.BLUE_SHARD)
}

internal object Route124_EventScript_ItemYellowShard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.YELLOW_SHARD)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LILA_AND_ROY_1, Route124_Text_LilaIntro, Route124_Text_LilaDefeat, Route124_Text_LilaNotEnoughMons, Route124_EventScript_RegisterLila
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route124_EventScript_RematchLila
 * msgbox Route124_Text_LilaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route124_EventScript_Lila : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Lila")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LILA_AND_ROY_1, Route124_Text_RoyIntro, Route124_Text_RoyDefeat, Route124_Text_RoyNotEnoughMons, Route124_EventScript_RegisterRoy
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route124_EventScript_RematchRoy
 * msgbox Route124_Text_RoyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route124_EventScript_Roy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Roy")
}

internal object Route124_EventScript_Declan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DECLAN, Route124.DeclanIntro, Route124.DeclanDefeat))
        return
    ctx.say(Route124.DeclanPostBattle)
  }
}

internal object Route124_EventScript_Isabella : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ISABELLA, Route124.IsabellaIntro, Route124.IsabellaDefeat))
        return
    ctx.say(Route124.IsabellaPostBattle)
  }
}

internal object Route124_EventScript_HuntersHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route124.HuntersHouse)
}

internal val Route124Scripts: Map<String, Script> =
    mapOf(
        "Route124_EventScript_Spencer" to Route124_EventScript_Spencer,
        "Route124_EventScript_Roland" to Route124_EventScript_Roland,
        "Route124_EventScript_Jenny" to Route124_EventScript_Jenny,
        "Route124_EventScript_Grace" to Route124_EventScript_Grace,
        "Route124_EventScript_Chad" to Route124_EventScript_Chad,
        "Route124_EventScript_ItemRedShard" to Route124_EventScript_ItemRedShard,
        "Route124_EventScript_ItemBlueShard" to Route124_EventScript_ItemBlueShard,
        "Route124_EventScript_ItemYellowShard" to Route124_EventScript_ItemYellowShard,
        "Route124_EventScript_Lila" to Route124_EventScript_Lila,
        "Route124_EventScript_Roy" to Route124_EventScript_Roy,
        "Route124_EventScript_Declan" to Route124_EventScript_Declan,
        "Route124_EventScript_Isabella" to Route124_EventScript_Isabella,
        "Route124_EventScript_HuntersHouseSign" to Route124_EventScript_HuntersHouseSign,
    )
