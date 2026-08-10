package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route126
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BARRY = 163
private const val TRAINER_BRENDA = 454
private const val TRAINER_DEAN = 164
private const val TRAINER_ISOBEL = 383
private const val TRAINER_LEONARDO = 576
private const val TRAINER_NIKKI = 453
private const val TRAINER_SIENNA = 459

internal object Route126_EventScript_Barry : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BARRY, Route126.BarryIntro, Route126.BarryDefeat)) return
    ctx.say(Route126.BarryPostBattle)
  }
}

internal object Route126_EventScript_Dean : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DEAN, Route126.DeanIntro, Route126.DeanDefeat)) return
    ctx.say(Route126.DeanPostBattle)
  }
}

internal object Route126_EventScript_Nikki : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_NIKKI, Route126.NikkiIntro, Route126.NikkiDefeat)) return
    ctx.say(Route126.NikkiPostBattle)
  }
}

internal object Route126_EventScript_Brenda : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BRENDA, Route126.BrendaIntro, Route126.BrendaDefeat))
        return
    ctx.say(Route126.BrendaPostBattle)
  }
}

internal object Route126_EventScript_ItemGreenShard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.GREEN_SHARD)
}

internal object Route126_EventScript_Sienna : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SIENNA, Route126.SiennaIntro, Route126.SiennaDefeat))
        return
    ctx.say(Route126.SiennaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PABLO_1, Route126_Text_PabloIntro, Route126_Text_PabloDefeat, Route126_EventScript_RegisterPablo
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route126_EventScript_RematchPablo
 * msgbox Route126_Text_PabloPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route126_EventScript_Pablo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Pablo")
}

internal object Route126_EventScript_Isobel : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ISOBEL, Route126.IsobelIntro, Route126.IsobelDefeat))
        return
    ctx.say(Route126.IsobelPostBattle)
  }
}

internal object Route126_EventScript_Leonardo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LEONARDO, Route126.LeonardoIntro, Route126.LeonardoDefeat))
        return
    ctx.say(Route126.LeonardoPostBattle)
  }
}

internal val Route126Scripts: Map<String, Script> =
    mapOf(
        "Route126_EventScript_Barry" to Route126_EventScript_Barry,
        "Route126_EventScript_Dean" to Route126_EventScript_Dean,
        "Route126_EventScript_Nikki" to Route126_EventScript_Nikki,
        "Route126_EventScript_Brenda" to Route126_EventScript_Brenda,
        "Route126_EventScript_ItemGreenShard" to Route126_EventScript_ItemGreenShard,
        "Route126_EventScript_Sienna" to Route126_EventScript_Sienna,
        "Route126_EventScript_Pablo" to Route126_EventScript_Pablo,
        "Route126_EventScript_Isobel" to Route126_EventScript_Isobel,
        "Route126_EventScript_Leonardo" to Route126_EventScript_Leonardo,
    )
