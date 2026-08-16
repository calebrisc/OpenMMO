package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SafariZone_SecretHouse
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object SafariZone_SecretHouse_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_HM03)) {
      ctx.say(SafariZone_SecretHouse.ExplainSurf)
      return
    }
    ctx.say(SafariZone_SecretHouse.CongratsYouveWon)
    if (!ctx.giveItem(Items.HM03)) {
      ctx.say(SafariZone_SecretHouse.DontHaveRoomForPrize)
      return
    }
    ctx.say(SafariZone_SecretHouse.ReceivedHM03FromAttendant)
    ctx.say(SafariZone_SecretHouse.ExplainSurf)
    ctx.setFlag(KantoFlags.FLAG_GOT_HM03)
  }
}

internal val SafariZone_SecretHouseScripts: Map<String, Script> =
    mapOf(
        "SafariZone_SecretHouse_EventScript_Attendant" to
            SafariZone_SecretHouse_EventScript_Attendant,
    )
