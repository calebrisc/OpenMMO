package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route105
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BEVERLY = 441
private const val TRAINER_DOMINIK = 152
private const val TRAINER_FOSTER = 46
private const val TRAINER_IMANI = 442
private const val TRAINER_JOSUE = 738
private const val TRAINER_LUIS = 151

internal object Route105_EventScript_Luis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LUIS, Route105.LuisIntro, Route105.LuisDefeated)) return
    ctx.say(Route105.LuisPostBattle)
  }
}

internal object Route105_EventScript_Dominik : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DOMINIK, Route105.DominikIntro, Route105.DominikDefeated))
        return
    ctx.say(Route105.DominikPostBattle)
  }
}

internal object Route105_EventScript_Beverly : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BEVERLY, Route105.BeverlyIntro, Route105.BeverlyDefeated))
        return
    ctx.say(Route105.PostBattle)
  }
}

internal object Route105_EventScript_Imani : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_IMANI, Route105.ImaniIntro, Route105.ImaniDefeated)) return
    ctx.say(Route105.ImaniPostBattle)
  }
}

internal object Route105_EventScript_ItemIron : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.IRON)
}

internal object Route105_EventScript_Foster : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_FOSTER, Route105.FosterIntro, Route105.FosterDefeated))
        return
    ctx.say(Route105.FosterPostBattle)
  }
}

internal object Route105_EventScript_Josue : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JOSUE, Route105.JosueIntro, Route105.JosueDefeated)) return
    ctx.say(Route105.JosuePostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ANDRES_1, Route105_Text_AndresIntro, Route105_Text_AndresDefeated, Route105_EventScript_AndresRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route105_EventScript_AndresRematch
 * msgbox Route105_Text_AndresPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route105_EventScript_Andres : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route105_EventScript_Andres")
}

internal val Route105Scripts: Map<String, Script> =
    mapOf(
        "Route105_EventScript_Luis" to Route105_EventScript_Luis,
        "Route105_EventScript_Dominik" to Route105_EventScript_Dominik,
        "Route105_EventScript_Beverly" to Route105_EventScript_Beverly,
        "Route105_EventScript_Imani" to Route105_EventScript_Imani,
        "Route105_EventScript_ItemIron" to Route105_EventScript_ItemIron,
        "Route105_EventScript_Foster" to Route105_EventScript_Foster,
        "Route105_EventScript_Josue" to Route105_EventScript_Josue,
        "Route105_EventScript_Andres" to Route105_EventScript_Andres,
    )
