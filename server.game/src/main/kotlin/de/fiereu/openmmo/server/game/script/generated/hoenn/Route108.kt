package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route108
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CAROLINA = 741
private const val TRAINER_JEROME = 156
private const val TRAINER_MATTHEW = 157
private const val TRAINER_MISSY = 447
private const val TRAINER_TARA = 446

internal object Route108_EventScript_Jerome : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JEROME, Route108.JeromeIntro, Route108.JeromeDefeated))
        return
    ctx.say(Route108.JeromePostBattle)
  }
}

internal object Route108_EventScript_Tara : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TARA, Route108.TaraIntro, Route108.TaraDefeated)) return
    ctx.say(Route108.TaraPostBattle)
  }
}

internal object Route108_EventScript_Matthew : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MATTHEW, Route108.MatthewIntro, Route108.MatthewDefeated))
        return
    ctx.say(Route108.MatthewPostBattle)
  }
}

internal object Route108_EventScript_Missy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MISSY, Route108.MissyIntro, Route108.MissyDefeated)) return
    ctx.say(Route108.MissyPostBattle)
  }
}

internal object Route108_EventScript_Carolina : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CAROLINA, Route108.CarolinaIntro, Route108.CarolinaDefeated))
        return
    ctx.say(Route108.CarolinaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CORY_1, Route108_Text_CoryIntro, Route108_Text_CoryDefeated, Route108_EventScript_CoryRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route108_EventScript_CoryRematch
 * msgbox Route108_Text_CoryPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route108_EventScript_Cory : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_Cory")
}

internal object Route108_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.STAR_PIECE)
}

internal val Route108Scripts: Map<String, Script> =
    mapOf(
        "Route108_EventScript_Jerome" to Route108_EventScript_Jerome,
        "Route108_EventScript_Tara" to Route108_EventScript_Tara,
        "Route108_EventScript_Matthew" to Route108_EventScript_Matthew,
        "Route108_EventScript_Missy" to Route108_EventScript_Missy,
        "Route108_EventScript_Carolina" to Route108_EventScript_Carolina,
        "Route108_EventScript_Cory" to Route108_EventScript_Cory,
        "Route108_EventScript_ItemStarPiece" to Route108_EventScript_ItemStarPiece,
    )
