package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland_LoreleisHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object FourIsland_LoreleisHouse_EventScript_Lorelei : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_TALKED_TO_LORELEI_AFTER_WAREHOUSE)) {
      ctx.say(FourIsland_LoreleisHouse.WillDoWhatICanHereAndNow)
      return
    }
    if (ctx.isFlagSet(KantoFlags.FLAG_DEFEATED_ROCKETS_IN_WAREHOUSE)) {
      ctx.setFlag(KantoFlags.FLAG_TALKED_TO_LORELEI_AFTER_WAREHOUSE)
      ctx.say(FourIsland_LoreleisHouse.IllReturnToLeagueInShortWhile)
      return
    }
    ctx.say(FourIsland_LoreleisHouse.IfAnythingWereToHappenToIsland)
  }
}

internal object FourIsland_LoreleisHouse_EventScript_Doll : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FourIsland_LoreleisHouse.StuffedMonDollsGalore)
}

internal val FourIsland_LoreleisHouseScripts: Map<String, Script> =
    mapOf(
        "FourIsland_LoreleisHouse_EventScript_Lorelei" to
            FourIsland_LoreleisHouse_EventScript_Lorelei,
        "FourIsland_LoreleisHouse_EventScript_Doll" to FourIsland_LoreleisHouse_EventScript_Doll,
    )
