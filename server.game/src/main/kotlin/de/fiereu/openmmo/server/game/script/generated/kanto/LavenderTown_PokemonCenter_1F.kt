package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.LavenderTown_PokemonCenter_1F
import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object LavenderTown_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.healParty()
    ctx.say(Misc.Text_MonsHealed)
  }
}

internal object LavenderTown_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavenderTown_PokemonCenter_1F.RocketsDoAnythingForMoney)
}

internal object LavenderTown_PokemonCenter_1F_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavenderTown_PokemonCenter_1F.CubonesMotherKilledByRockets)
}

internal object LavenderTown_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavenderTown_PokemonCenter_1F.PeoplePayForCuboneSkulls)
}

internal object LavenderTown_PokemonCenter_1F_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavenderTown_PokemonCenter_1F.HearMrFujiNotFromAroundHere)
}

internal val LavenderTown_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "LavenderTown_PokemonCenter_1F_EventScript_Nurse" to
            LavenderTown_PokemonCenter_1F_EventScript_Nurse,
        "LavenderTown_PokemonCenter_1F_EventScript_Gentleman" to
            LavenderTown_PokemonCenter_1F_EventScript_Gentleman,
        "LavenderTown_PokemonCenter_1F_EventScript_Lass" to
            LavenderTown_PokemonCenter_1F_EventScript_Lass,
        "LavenderTown_PokemonCenter_1F_EventScript_Youngster" to
            LavenderTown_PokemonCenter_1F_EventScript_Youngster,
        "LavenderTown_PokemonCenter_1F_EventScript_BaldingMan" to
            LavenderTown_PokemonCenter_1F_EventScript_BaldingMan,
    )
