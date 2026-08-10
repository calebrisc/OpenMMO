package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_4F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CHANNELER_JODY = 446
private const val TRAINER_CHANNELER_LAUREL = 445
private const val TRAINER_CHANNELER_PAULA = 444

internal object PokemonTower_4F_EventScript_Laurel : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_LAUREL, PokemonTower_4F.LaurelIntro, PokemonTower_4F.LaurelDefeat))
        return
    ctx.say(PokemonTower_4F.LaurelPostBattle)
  }
}

internal object PokemonTower_4F_EventScript_Jody : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_JODY, PokemonTower_4F.JodyIntro, PokemonTower_4F.JodyDefeat))
        return
    ctx.say(PokemonTower_4F.JodyPostBattle)
  }
}

internal object PokemonTower_4F_EventScript_Paula : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_PAULA, PokemonTower_4F.PaulaIntro, PokemonTower_4F.PaulaDefeat))
        return
    ctx.say(PokemonTower_4F.PaulaPostBattle)
  }
}

internal object PokemonTower_4F_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ELIXIR)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_AWAKENING
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_ItemAwakening : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_4F_EventScript_ItemAwakening")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GREAT_BALL
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_ItemGreatBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_4F_EventScript_ItemGreatBall")
}

internal val PokemonTower_4FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_4F_EventScript_Laurel" to PokemonTower_4F_EventScript_Laurel,
        "PokemonTower_4F_EventScript_Jody" to PokemonTower_4F_EventScript_Jody,
        "PokemonTower_4F_EventScript_Paula" to PokemonTower_4F_EventScript_Paula,
        "PokemonTower_4F_EventScript_ItemElixir" to PokemonTower_4F_EventScript_ItemElixir,
        "PokemonTower_4F_EventScript_ItemAwakening" to PokemonTower_4F_EventScript_ItemAwakening,
        "PokemonTower_4F_EventScript_ItemGreatBall" to PokemonTower_4F_EventScript_ItemGreatBall,
    )
