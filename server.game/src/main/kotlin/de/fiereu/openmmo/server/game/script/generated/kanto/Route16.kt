package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.Route16
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_BIKER_HIDEO = 201
private const val TRAINER_BIKER_LAO = 199
private const val TRAINER_BIKER_RUBEN = 202
private const val TRAINER_CUE_BALL_CAMRON = 251
private const val TRAINER_CUE_BALL_KOJI = 249
private const val TRAINER_CUE_BALL_LUKE = 250

internal object Route16_EventScript_Lao : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_LAO, Route16.LaoIntro, Route16.LaoDefeat)) return
    ctx.say(Route16.LaoPostBattle)
  }
}

internal object Route16_EventScript_Koji : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CUE_BALL_KOJI, Route16.KojiIntro, Route16.KojiDefeat))
        return
    ctx.say(Route16.KojiPostBattle)
  }
}

internal object Route16_EventScript_Luke : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CUE_BALL_LUKE, Route16.LukeIntro, Route16.LukeDefeat))
        return
    ctx.say(Route16.LukePostBattle)
  }
}

internal object Route16_EventScript_Ruben : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_RUBEN, Route16.RubenIntro, Route16.RubenDefeat))
        return
    ctx.say(Route16.RubenPostBattle)
  }
}

internal object Route16_EventScript_Hideo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BIKER_HIDEO, Route16.HideoIntro, Route16.HideoDefeat))
        return
    ctx.say(Route16.HideoPostBattle)
  }
}

internal object Route16_EventScript_Camron : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CUE_BALL_CAMRON, Route16.CamronIntro, Route16.CamronDefeat))
        return
    ctx.say(Route16.CamronPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_YOUNG_COUPLE_LEA_JED, Route16_Text_LeaIntro, Route16_Text_LeaDefeat, Route16_Text_LeaNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_LeaRematch
 * msgbox Route16_Text_LeaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Lea : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Lea")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_YOUNG_COUPLE_LEA_JED, Route16_Text_JedIntro, Route16_Text_JedDefeat, Route16_Text_JedNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_JedRematch
 * msgbox Route16_Text_JedPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Jed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Jed")
}

private const val SNORLAX_DEX = 143

internal object Route16_EventScript_Snorlax : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_POKE_FLUTE)) {
      return ctx.say(Route16.MonSprawledOutInSlumber)
    }
    if (!ctx.askYesNo(Misc.Text_WantToUsePokeFlute)) return
    val result = ctx.wildBattle(SNORLAX_DEX, 30)
    if (result != BattleResult.VICTORY && result != BattleResult.CAUGHT) return
    ctx.setFlag(KantoFlags.FLAG_HIDE_ROUTE_16_SNORLAX)
    ctx.despawnInteracted()
    ctx.sign(Misc.Text_SnorlaxReturnedToMountains)
  }
}

internal object Route16_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route16.RouteSign)
}

internal object Route16_EventScript_CyclingRoadSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route16.CyclingRoadSign)
}

internal val Route16Scripts: Map<String, Script> =
    mapOf(
        "Route16_EventScript_Lao" to Route16_EventScript_Lao,
        "Route16_EventScript_Koji" to Route16_EventScript_Koji,
        "Route16_EventScript_Luke" to Route16_EventScript_Luke,
        "Route16_EventScript_Ruben" to Route16_EventScript_Ruben,
        "Route16_EventScript_Hideo" to Route16_EventScript_Hideo,
        "Route16_EventScript_Camron" to Route16_EventScript_Camron,
        "Route16_EventScript_Lea" to Route16_EventScript_Lea,
        "Route16_EventScript_Jed" to Route16_EventScript_Jed,
        "Route16_EventScript_Snorlax" to Route16_EventScript_Snorlax,
        "Route16_EventScript_RouteSign" to Route16_EventScript_RouteSign,
        "Route16_EventScript_CyclingRoadSign" to Route16_EventScript_CyclingRoadSign,
    )
