package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_6F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CHANNELER_ANGELICA = 451
private const val TRAINER_CHANNELER_EMILIA = 452
private const val TRAINER_CHANNELER_JENNIFER = 453

internal object PokemonTower_6F_EventScript_Angelica : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_ANGELICA, PokemonTower_6F.AngelicaIntro, PokemonTower_6F.AngelicaDefeat))
        return
    ctx.say(PokemonTower_6F.AngelicaPostBattle)
  }
}

internal object PokemonTower_6F_EventScript_Jennifer : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_JENNIFER, PokemonTower_6F.JenniferIntro, PokemonTower_6F.JenniferDefeat))
        return
    ctx.say(PokemonTower_6F.JenniferPostBattle)
  }
}

internal object PokemonTower_6F_EventScript_Emilia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_EMILIA, PokemonTower_6F.EmiliaIntro, PokemonTower_6F.EmiliaDefeat))
        return
    ctx.say(PokemonTower_6F.EmiliaPostBattle)
  }
}

internal object PokemonTower_6F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object PokemonTower_6F_EventScript_ItemXAccuracy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.X_ACCURACY)
}

internal val PokemonTower_6FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_6F_EventScript_Angelica" to PokemonTower_6F_EventScript_Angelica,
        "PokemonTower_6F_EventScript_Jennifer" to PokemonTower_6F_EventScript_Jennifer,
        "PokemonTower_6F_EventScript_Emilia" to PokemonTower_6F_EventScript_Emilia,
        "PokemonTower_6F_EventScript_ItemRareCandy" to PokemonTower_6F_EventScript_ItemRareCandy,
        "PokemonTower_6F_EventScript_ItemXAccuracy" to PokemonTower_6F_EventScript_ItemXAccuracy,
    )
