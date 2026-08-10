package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route21_South
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_FISHERMAN_CLAUDE = 230
private const val TRAINER_FISHERMAN_NOLAN = 232
private const val TRAINER_SWIMMER_MALE_JACK = 246
private const val TRAINER_SWIMMER_MALE_JEROME = 247
private const val TRAINER_SWIMMER_MALE_ROLAND = 248

internal object Route21_South_EventScript_Claude : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FISHERMAN_CLAUDE, Route21_South.ClaudeIntro, Route21_South.ClaudeDefeat))
        return
    ctx.say(Route21_South.ClaudePostBattle)
  }
}

internal object Route21_South_EventScript_Nolan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FISHERMAN_NOLAN, Route21_South.NolanIntro, Route21_South.NolanDefeat))
        return
    ctx.say(Route21_South.NolanPostBattle)
  }
}

internal object Route21_South_EventScript_Jack : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_JACK, Route21_South.JackIntro, Route21_South.JackDefeat))
        return
    ctx.say(Route21_South.JackPostBattle)
  }
}

internal object Route21_South_EventScript_Jerome : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_JEROME, Route21_South.JeromeIntro, Route21_South.JeromeDefeat))
        return
    ctx.say(Route21_South.JeromePostBattle)
  }
}

internal object Route21_South_EventScript_Roland : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_ROLAND, Route21_South.RolandIntro, Route21_South.RolandDefeat))
        return
    ctx.say(Route21_South.RolandPostBattle)
  }
}

internal val Route21_SouthScripts: Map<String, Script> =
    mapOf(
        "Route21_South_EventScript_Claude" to Route21_South_EventScript_Claude,
        "Route21_South_EventScript_Nolan" to Route21_South_EventScript_Nolan,
        "Route21_South_EventScript_Jack" to Route21_South_EventScript_Jack,
        "Route21_South_EventScript_Jerome" to Route21_South_EventScript_Jerome,
        "Route21_South_EventScript_Roland" to Route21_South_EventScript_Roland,
    )
