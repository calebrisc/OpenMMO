package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonMansion_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BURGLAR_ARNIE = 216

internal object PokemonMansion_2F_EventScript_Arnie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BURGLAR_ARNIE, PokemonMansion_1F.ArnieIntro, PokemonMansion_1F.ArnieDefeat))
        return
    ctx.say(PokemonMansion_1F.ArniePostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CALCIUM
 * end
 * ```
 */
internal object PokemonMansion_2F_EventScript_ItemCalcium : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_2F_EventScript_ItemCalcium")
}

internal object PokemonMansion_2F_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ZINC)
}

internal object PokemonMansion_2F_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.HP_UP)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 1
 * call PokemonMansion_EventScript_SecretSwitch
 * playse SE_UNLOCK
 * special DrawWholeMapView
 * waitse
 * releaseall
 * end
 * ```
 */
internal object PokemonMansion_2F_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_2F_EventScript_Statue")
}

internal object PokemonMansion_2F_EventScript_DiaryJuly10th : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PokemonMansion_1F.ChristenedDiscoveredMonMew)
}

internal object PokemonMansion_2F_EventScript_DiaryJuly5th : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PokemonMansion_1F.NewMonDiscoveredInGuyanaJungle)
}

internal val PokemonMansion_2FScripts: Map<String, Script> =
    mapOf(
        "PokemonMansion_2F_EventScript_Arnie" to PokemonMansion_2F_EventScript_Arnie,
        "PokemonMansion_2F_EventScript_ItemCalcium" to PokemonMansion_2F_EventScript_ItemCalcium,
        "PokemonMansion_2F_EventScript_ItemZinc" to PokemonMansion_2F_EventScript_ItemZinc,
        "PokemonMansion_2F_EventScript_ItemHPUp" to PokemonMansion_2F_EventScript_ItemHPUp,
        "PokemonMansion_2F_EventScript_Statue" to PokemonMansion_2F_EventScript_Statue,
        "PokemonMansion_2F_EventScript_DiaryJuly10th" to
            PokemonMansion_2F_EventScript_DiaryJuly10th,
        "PokemonMansion_2F_EventScript_DiaryJuly5th" to PokemonMansion_2F_EventScript_DiaryJuly5th,
    )
