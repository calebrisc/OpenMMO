package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_House5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object ThreeIsland_House5_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_RESCUED_LOSTELLE)) {
      ctx.say(ThreeIsland_House5.IllGoPlayWithLostelle)
      return
    }
    ctx.say(ThreeIsland_House5.ImNotLostelle)
  }
}

internal val ThreeIsland_House5Scripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_House5_EventScript_LittleGirl" to ThreeIsland_House5_EventScript_LittleGirl,
    )
