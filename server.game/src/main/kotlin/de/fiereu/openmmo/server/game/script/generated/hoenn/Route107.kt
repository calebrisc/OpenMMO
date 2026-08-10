package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route107
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BETH = 445
private const val TRAINER_CAMRON = 739
private const val TRAINER_DARRIN = 154
private const val TRAINER_DENISE = 444

internal object Route107_EventScript_Darrin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DARRIN, Route107.DarrinIntro, Route107.DarrinDefeated))
        return
    ctx.say(Route107.DarrinPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TONY_1, Route107_Text_TonyIntro, Route107_Text_TonyDefeated, Route107_EventScript_TonyRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route107_EventScript_TonyRematch
 * msgbox Route107_Text_TonyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route107_EventScript_Tony : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Tony")
}

internal object Route107_EventScript_Denise : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DENISE, Route107.DeniseIntro, Route107.DeniseDefeated))
        return
    ctx.say(Route107.DenisePostBattle)
  }
}

internal object Route107_EventScript_Beth : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BETH, Route107.BethIntro, Route107.BethDefeated)) return
    ctx.say(Route107.BethPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LISA_AND_RAY, Route107_Text_LisaIntro, Route107_Text_LisaDefeated, Route107_Text_LisaNotEnoughPokemon
 * msgbox Route107_Text_LisaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Lisa : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Lisa")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LISA_AND_RAY, Route107_Text_RayIntro, Route107_Text_RayDefeated, Route107_Text_RayNotEnoughPokemon
 * msgbox Route107_Text_RayPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Ray : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Ray")
}

internal object Route107_EventScript_Camron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMRON, Route107.CamronIntro, Route107.CamronDefeated))
        return
    ctx.say(Route107.CamronPostBattle)
  }
}

internal val Route107Scripts: Map<String, Script> =
    mapOf(
        "Route107_EventScript_Darrin" to Route107_EventScript_Darrin,
        "Route107_EventScript_Tony" to Route107_EventScript_Tony,
        "Route107_EventScript_Denise" to Route107_EventScript_Denise,
        "Route107_EventScript_Beth" to Route107_EventScript_Beth,
        "Route107_EventScript_Lisa" to Route107_EventScript_Lisa,
        "Route107_EventScript_Ray" to Route107_EventScript_Ray,
        "Route107_EventScript_Camron" to Route107_EventScript_Camron,
    )
