package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_3F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CHANNELER_CARLY = 442
private const val TRAINER_CHANNELER_HOPE = 443
private const val TRAINER_CHANNELER_PATRICIA = 441

internal object PokemonTower_3F_EventScript_Patricia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_PATRICIA, PokemonTower_3F.PatriciaIntro, PokemonTower_3F.PatriciaDefeat))
        return
    ctx.say(PokemonTower_3F.PatriciaPostBattle)
  }
}

internal object PokemonTower_3F_EventScript_Carly : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_CARLY, PokemonTower_3F.CarlyIntro, PokemonTower_3F.CarlyDefeat))
        return
    ctx.say(PokemonTower_3F.CarlyPostBattle)
  }
}

internal object PokemonTower_3F_EventScript_Hope : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHANNELER_HOPE, PokemonTower_3F.HopeIntro, PokemonTower_3F.HopeDefeat))
        return
    ctx.say(PokemonTower_3F.HopePostBattle)
  }
}

internal object PokemonTower_3F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ESCAPE_ROPE)
}

internal val PokemonTower_3FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_3F_EventScript_Patricia" to PokemonTower_3F_EventScript_Patricia,
        "PokemonTower_3F_EventScript_Carly" to PokemonTower_3F_EventScript_Carly,
        "PokemonTower_3F_EventScript_Hope" to PokemonTower_3F_EventScript_Hope,
        "PokemonTower_3F_EventScript_ItemEscapeRope" to PokemonTower_3F_EventScript_ItemEscapeRope,
    )
