package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.Route10_PokemonCenter_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.AideGift
import de.fiereu.openmmo.server.game.script.AideLines
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route10_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.healParty()
    ctx.say(Misc.Text_MonsHealed)
  }
}

internal object Route10_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route10_PokemonCenter_1F.EveryTypeStrongerThanOthers)
}

internal object Route10_PokemonCenter_1F_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route10_PokemonCenter_1F.NuggetUselessSoldFor5000)
}

internal object Route10_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route10_PokemonCenter_1F.HeardGhostsHauntLavender)
}

internal object Route10_PokemonCenter_1F_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.oaksAide(
          AideGift(
              item = Items.EVERSTONE,
              required = 20,
              countCaught = true,
              flag = KantoFlags.FLAG_GOT_EVERSTONE_FROM_OAKS_AIDE,
          ),
          AideLines(
              offer = Route10_PokemonCenter_1F.GiveEverstoneIfCaught20Mons,
              greatHereYouGo = Route10_PokemonCenter_1F.GreatHereYouGo,
              received = Route10_PokemonCenter_1F.ReceivedEverstoneFromAide,
              explain = Route10_PokemonCenter_1F.ExplainEverstone,
          ),
      )
}

internal val Route10_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "Route10_PokemonCenter_1F_EventScript_Nurse" to Route10_PokemonCenter_1F_EventScript_Nurse,
        "Route10_PokemonCenter_1F_EventScript_Gentleman" to
            Route10_PokemonCenter_1F_EventScript_Gentleman,
        "Route10_PokemonCenter_1F_EventScript_FatMan" to
            Route10_PokemonCenter_1F_EventScript_FatMan,
        "Route10_PokemonCenter_1F_EventScript_Youngster" to
            Route10_PokemonCenter_1F_EventScript_Youngster,
        "Route10_PokemonCenter_1F_EventScript_Aide" to Route10_PokemonCenter_1F_EventScript_Aide,
    )
