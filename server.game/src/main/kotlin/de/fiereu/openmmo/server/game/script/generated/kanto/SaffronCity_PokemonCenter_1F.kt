package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.PokemonJournal
import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

internal object SaffronCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.healParty()
    ctx.say(Misc.Text_MonsHealed)
  }
}

internal object SaffronCity_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SaffronCity_PokemonCenter_1F.SilphCoVictimOfFame)
}

internal object SaffronCity_PokemonCenter_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SaffronCity_PokemonCenter_1F.GrowthRatesDifferBySpecies)
}

internal object SaffronCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) == 1) {
      ctx.say(SaffronCity_PokemonCenter_1F.TeamRocketTookOff)
      return
    }
    ctx.say(SaffronCity_PokemonCenter_1F.GreatIfEliteFourCameBeatRockets)
  }
}

internal object SaffronCity_PokemonCenter_1F_EventScript_PokemonJournalSabrina : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonJournal.SpecialFeatureSabrina)
}

internal val SaffronCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_PokemonCenter_1F_EventScript_Nurse" to
            SaffronCity_PokemonCenter_1F_EventScript_Nurse,
        "SaffronCity_PokemonCenter_1F_EventScript_Gentleman" to
            SaffronCity_PokemonCenter_1F_EventScript_Gentleman,
        "SaffronCity_PokemonCenter_1F_EventScript_Woman" to
            SaffronCity_PokemonCenter_1F_EventScript_Woman,
        "SaffronCity_PokemonCenter_1F_EventScript_Youngster" to
            SaffronCity_PokemonCenter_1F_EventScript_Youngster,
        "SaffronCity_PokemonCenter_1F_EventScript_PokemonJournalSabrina" to
            SaffronCity_PokemonCenter_1F_EventScript_PokemonJournalSabrina,
    )
