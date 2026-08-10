package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_5F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CHANNELER_JANAE = 450
private const val TRAINER_CHANNELER_KARINA = 449
private const val TRAINER_CHANNELER_RUTH = 448
private const val TRAINER_CHANNELER_TAMMY = 447

internal object PokemonTower_5F_EventScript_Ruth : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_RUTH, PokemonTower_5F.RuthIntro, PokemonTower_5F.RuthDefeat))
        return
    ctx.say(PokemonTower_5F.RuthPostBattle)
  }
}

internal object PokemonTower_5F_EventScript_Tammy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_TAMMY, PokemonTower_5F.TammyIntro, PokemonTower_5F.TammyDefeat))
        return
    ctx.say(PokemonTower_5F.TammyPostBattle)
  }
}

internal object PokemonTower_5F_EventScript_Karina : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_KARINA, PokemonTower_5F.KarinaIntro, PokemonTower_5F.KarinaDefeat))
        return
    ctx.say(PokemonTower_5F.KarinaPostBattle)
  }
}

internal object PokemonTower_5F_EventScript_Janae : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_JANAE, PokemonTower_5F.JanaeIntro, PokemonTower_5F.JanaeDefeat))
        return
    ctx.say(PokemonTower_5F.JanaePostBattle)
  }
}

internal object PokemonTower_5F_EventScript_Channeler : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonTower_5F.RestHereInPurifiedSpace)
}

internal object PokemonTower_5F_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NUGGET)
}

internal object PokemonTower_5F_EventScript_ItemCleanseTag : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CLEANSE_TAG)
}

internal val PokemonTower_5FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_5F_EventScript_Ruth" to PokemonTower_5F_EventScript_Ruth,
        "PokemonTower_5F_EventScript_Tammy" to PokemonTower_5F_EventScript_Tammy,
        "PokemonTower_5F_EventScript_Karina" to PokemonTower_5F_EventScript_Karina,
        "PokemonTower_5F_EventScript_Janae" to PokemonTower_5F_EventScript_Janae,
        "PokemonTower_5F_EventScript_Channeler" to PokemonTower_5F_EventScript_Channeler,
        "PokemonTower_5F_EventScript_ItemNugget" to PokemonTower_5F_EventScript_ItemNugget,
        "PokemonTower_5F_EventScript_ItemCleanseTag" to PokemonTower_5F_EventScript_ItemCleanseTag,
    )
