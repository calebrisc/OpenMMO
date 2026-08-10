package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.PokemonJournal
import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SevenIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.healParty()
    ctx.say(Misc.Text_MonsHealed)
  }
}

internal object SevenIsland_PokemonCenter_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SevenIsland_PokemonCenter_1F.EventsDependingOnConnections)
}

internal object SevenIsland_PokemonCenter_1F_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SevenIsland_PokemonCenter_1F.DidYouRideSeagallopHere)
}

internal object SevenIsland_PokemonCenter_1F_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SevenIsland_PokemonCenter_1F.CheckOutTrainerTower)
}

internal object SevenIsland_PokemonCenter_1F_EventScript_PokemonJournal : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonJournal.SpecialFeatureAgatha)
}

internal val SevenIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_PokemonCenter_1F_EventScript_Nurse" to
            SevenIsland_PokemonCenter_1F_EventScript_Nurse,
        "SevenIsland_PokemonCenter_1F_EventScript_OldMan" to
            SevenIsland_PokemonCenter_1F_EventScript_OldMan,
        "SevenIsland_PokemonCenter_1F_EventScript_Sailor" to
            SevenIsland_PokemonCenter_1F_EventScript_Sailor,
        "SevenIsland_PokemonCenter_1F_EventScript_Beauty" to
            SevenIsland_PokemonCenter_1F_EventScript_Beauty,
        "SevenIsland_PokemonCenter_1F_EventScript_PokemonJournal" to
            SevenIsland_PokemonCenter_1F_EventScript_PokemonJournal,
    )
