package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonMansion_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BURGLAR_SIMON = 218
private const val TRAINER_SCIENTIST_BRAYDON = 346

internal object PokemonMansion_3F_EventScript_Simon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BURGLAR_SIMON, PokemonMansion_1F.SimonIntro, PokemonMansion_1F.SimonDefeat))
        return
    ctx.say(PokemonMansion_1F.SimonPostBattle)
  }
}

internal object PokemonMansion_3F_EventScript_Braydon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_BRAYDON, PokemonMansion_1F.BraydonIntro, PokemonMansion_1F.BraydonDefeat))
        return
    ctx.say(PokemonMansion_1F.BraydonPostBattle)
  }
}

internal object PokemonMansion_3F_EventScript_ItemMaxPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_POTION)
}

internal object PokemonMansion_3F_EventScript_ItemIron : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.IRON)
}

internal object PokemonMansion_3F_EventScript_DiaryFeb6th : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PokemonMansion_1F.MewGaveBirthToMewtwo)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 2
 * call PokemonMansion_EventScript_SecretSwitch
 * playse SE_UNLOCK
 * special DrawWholeMapView
 * waitse
 * releaseall
 * end
 * ```
 */
internal object PokemonMansion_3F_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_3F_EventScript_Statue")
}

internal val PokemonMansion_3FScripts: Map<String, Script> =
    mapOf(
        "PokemonMansion_3F_EventScript_Simon" to PokemonMansion_3F_EventScript_Simon,
        "PokemonMansion_3F_EventScript_Braydon" to PokemonMansion_3F_EventScript_Braydon,
        "PokemonMansion_3F_EventScript_ItemMaxPotion" to
            PokemonMansion_3F_EventScript_ItemMaxPotion,
        "PokemonMansion_3F_EventScript_ItemIron" to PokemonMansion_3F_EventScript_ItemIron,
        "PokemonMansion_3F_EventScript_DiaryFeb6th" to PokemonMansion_3F_EventScript_DiaryFeb6th,
        "PokemonMansion_3F_EventScript_Statue" to PokemonMansion_3F_EventScript_Statue,
    )
