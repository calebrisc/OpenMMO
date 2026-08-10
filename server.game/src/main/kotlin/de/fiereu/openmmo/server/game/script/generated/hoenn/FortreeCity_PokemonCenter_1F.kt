package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_PokemonCenter_1F
import de.fiereu.openmmo.dialog.generated.hoenn.Misc
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FortreeCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.healParty()
    ctx.say(Misc.gText_ThankYouForWaiting)
  }
}

internal object FortreeCity_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_PokemonCenter_1F.GoToSafariZone)
}

internal object FortreeCity_PokemonCenter_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_PokemonCenter_1F.RecordCornerIsNeat)
}

internal object FortreeCity_PokemonCenter_1F_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_PokemonCenter_1F.DoYouKnowAboutPokenav)
}

internal val FortreeCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "FortreeCity_PokemonCenter_1F_EventScript_Nurse" to
            FortreeCity_PokemonCenter_1F_EventScript_Nurse,
        "FortreeCity_PokemonCenter_1F_EventScript_Gentleman" to
            FortreeCity_PokemonCenter_1F_EventScript_Gentleman,
        "FortreeCity_PokemonCenter_1F_EventScript_Man" to
            FortreeCity_PokemonCenter_1F_EventScript_Man,
        "FortreeCity_PokemonCenter_1F_EventScript_Boy" to
            FortreeCity_PokemonCenter_1F_EventScript_Boy,
    )
