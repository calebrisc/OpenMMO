package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_PokemonCenter_1F
import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.PokemonJournal
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FiveIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.healParty()
    ctx.say(Misc.Text_MonsHealed)
  }
}

internal object FiveIsland_PokemonCenter_1F_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FiveIsland_PokemonCenter_1F.YoureOnQuestToCatchEveryMon)
}

internal object FiveIsland_PokemonCenter_1F_EventScript_Chef : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FiveIsland_PokemonCenter_1F.OnMyWayToPrepareMeal)
}

internal object FiveIsland_PokemonCenter_1F_EventScript_PokemonJournal : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonJournal.SpecialFeatureLorelei)
}

internal val FiveIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_PokemonCenter_1F_EventScript_Nurse" to
            FiveIsland_PokemonCenter_1F_EventScript_Nurse,
        "FiveIsland_PokemonCenter_1F_EventScript_CooltrainerF" to
            FiveIsland_PokemonCenter_1F_EventScript_CooltrainerF,
        "FiveIsland_PokemonCenter_1F_EventScript_Chef" to
            FiveIsland_PokemonCenter_1F_EventScript_Chef,
        "FiveIsland_PokemonCenter_1F_EventScript_PokemonJournal" to
            FiveIsland_PokemonCenter_1F_EventScript_PokemonJournal,
    )
