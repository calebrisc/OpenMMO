package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_MrPsychicsHouse
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object SaffronCity_MrPsychicsHouse_EventScript_MrPsychic : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_TM29_FROM_MR_PSYCHIC)) {
      ctx.say(SaffronCity_MrPsychicsHouse.ExplainTM29)
      return
    }
    ctx.say(SaffronCity_MrPsychicsHouse.YouWantedThis)
    if (!ctx.giveItem(Items.TM29)) {
      ctx.say(SaffronCity_MrPsychicsHouse.YouveNoRoom)
      return
    }
    ctx.say(SaffronCity_MrPsychicsHouse.ReceivedTM29FromMrPsychic)
    ctx.say(SaffronCity_MrPsychicsHouse.ExplainTM29)
    ctx.setFlag(KantoFlags.FLAG_GOT_TM29_FROM_MR_PSYCHIC)
  }
}

internal val SaffronCity_MrPsychicsHouseScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_MrPsychicsHouse_EventScript_MrPsychic" to
            SaffronCity_MrPsychicsHouse_EventScript_MrPsychic,
    )
