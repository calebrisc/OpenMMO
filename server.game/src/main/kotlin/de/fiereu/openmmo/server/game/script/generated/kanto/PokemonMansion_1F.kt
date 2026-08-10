package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonMansion_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_YOUNGSTER_JOHNSON = 534

private const val TRAINER_SCIENTIST_TED = 335

internal object PokemonMansion_1F_EventScript_Ted : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_TED, PokemonMansion_1F.TedIntro, PokemonMansion_1F.TedDefeat))
        return
    ctx.say(PokemonMansion_1F.TedPostBattle)
  }
}

internal object PokemonMansion_1F_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CARBOS)
}

internal object PokemonMansion_1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ESCAPE_ROPE)
}

internal object PokemonMansion_1F_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PROTEIN)
}

internal object PokemonMansion_1F_EventScript_Johnson : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNGSTER_JOHNSON, PokemonMansion_1F.JohnsonIntro, PokemonMansion_1F.JohnsonDefeat))
        return
    ctx.say(PokemonMansion_1F.JohnsonPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 0
 * call PokemonMansion_EventScript_SecretSwitch
 * playse SE_UNLOCK
 * special DrawWholeMapView
 * waitse
 * releaseall
 * end
 * ```
 */
internal object PokemonMansion_1F_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_1F_EventScript_Statue")
}

internal val PokemonMansion_1FScripts: Map<String, Script> =
    mapOf(
        "PokemonMansion_1F_EventScript_Ted" to PokemonMansion_1F_EventScript_Ted,
        "PokemonMansion_1F_EventScript_ItemCarbos" to PokemonMansion_1F_EventScript_ItemCarbos,
        "PokemonMansion_1F_EventScript_ItemEscapeRope" to
            PokemonMansion_1F_EventScript_ItemEscapeRope,
        "PokemonMansion_1F_EventScript_ItemProtein" to PokemonMansion_1F_EventScript_ItemProtein,
        "PokemonMansion_1F_EventScript_Johnson" to PokemonMansion_1F_EventScript_Johnson,
        "PokemonMansion_1F_EventScript_Statue" to PokemonMansion_1F_EventScript_Statue,
    )
