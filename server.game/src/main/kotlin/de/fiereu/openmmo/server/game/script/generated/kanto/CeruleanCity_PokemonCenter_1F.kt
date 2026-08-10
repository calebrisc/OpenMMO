package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_PokemonCenter_1F
import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.PokemonJournal
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeruleanCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.healParty()
    ctx.say(Misc.Text_MonsHealed)
  }
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_PokemonCenter_1F.EveryoneCallsBillPokemaniac)
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_PokemonCenter_1F.BillDoesWhateverForRareMons)
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_PokemonCenter_1F.BillCollectsRareMons)
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_PokemonCenter_1F.TryTradingUpstairs)
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_PokemonJournalMisty : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonJournal.SpecialFeatureMisty)
}

internal val CeruleanCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_PokemonCenter_1F_EventScript_Nurse" to
            CeruleanCity_PokemonCenter_1F_EventScript_Nurse,
        "CeruleanCity_PokemonCenter_1F_EventScript_Gentleman" to
            CeruleanCity_PokemonCenter_1F_EventScript_Gentleman,
        "CeruleanCity_PokemonCenter_1F_EventScript_Rocker" to
            CeruleanCity_PokemonCenter_1F_EventScript_Rocker,
        "CeruleanCity_PokemonCenter_1F_EventScript_Youngster" to
            CeruleanCity_PokemonCenter_1F_EventScript_Youngster,
        "CeruleanCity_PokemonCenter_1F_EventScript_Lass" to
            CeruleanCity_PokemonCenter_1F_EventScript_Lass,
        "CeruleanCity_PokemonCenter_1F_EventScript_PokemonJournalMisty" to
            CeruleanCity_PokemonCenter_1F_EventScript_PokemonJournalMisty,
    )
