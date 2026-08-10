package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonMansion_B1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BURGLAR_LEWIS = 219

private const val TRAINER_SCIENTIST_IVAN = 347

internal object PokemonMansion_B1F_EventScript_ItemTM22 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM22)
}

internal object PokemonMansion_B1F_EventScript_Lewis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BURGLAR_LEWIS, PokemonMansion_B1F.LewisIntro, PokemonMansion_B1F.LewisDefeat))
        return
    ctx.say(PokemonMansion_B1F.LewisPostBattle)
  }
}

internal object PokemonMansion_B1F_EventScript_Ivan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_IVAN, PokemonMansion_B1F.IvanIntro, PokemonMansion_B1F.IvanDefeat))
        return
    ctx.say(PokemonMansion_B1F.IvanPostBattle)
  }
}

internal object PokemonMansion_B1F_EventScript_ItemTM14 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM14)
}

internal object PokemonMansion_B1F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FULL_RESTORE)
}

internal object PokemonMansion_B1F_EventScript_ItemSecretKey : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.SECRET_KEY)
}

internal object PokemonMansion_B1F_EventScript_DiarySep1st : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PokemonMansion_B1F.MewtwoIsFarTooPowerful)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 3
 * call PokemonMansion_EventScript_SecretSwitch
 * playse SE_UNLOCK
 * special DrawWholeMapView
 * waitse
 * releaseall
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_B1F_EventScript_Statue")
}

internal val PokemonMansion_B1FScripts: Map<String, Script> =
    mapOf(
        "PokemonMansion_B1F_EventScript_ItemTM22" to PokemonMansion_B1F_EventScript_ItemTM22,
        "PokemonMansion_B1F_EventScript_Lewis" to PokemonMansion_B1F_EventScript_Lewis,
        "PokemonMansion_B1F_EventScript_Ivan" to PokemonMansion_B1F_EventScript_Ivan,
        "PokemonMansion_B1F_EventScript_ItemTM14" to PokemonMansion_B1F_EventScript_ItemTM14,
        "PokemonMansion_B1F_EventScript_ItemFullRestore" to
            PokemonMansion_B1F_EventScript_ItemFullRestore,
        "PokemonMansion_B1F_EventScript_ItemSecretKey" to
            PokemonMansion_B1F_EventScript_ItemSecretKey,
        "PokemonMansion_B1F_EventScript_DiarySep1st" to PokemonMansion_B1F_EventScript_DiarySep1st,
        "PokemonMansion_B1F_EventScript_Statue" to PokemonMansion_B1F_EventScript_Statue,
    )
