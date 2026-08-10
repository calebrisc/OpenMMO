package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.GraniteCave_StevensRoom
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

internal object GraniteCave_StevensRoom_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_DELIVERED_STEVEN_LETTER)) {
      return ctx.say(GraniteCave_StevensRoom.IveGotToHurryAlong)
    }
    if (!ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_POKENAV)) {
      // Without the letter he only introduces himself.
      return ctx.say(GraniteCave_StevensRoom.ImStevenLetterForMe)
    }
    ctx.say(GraniteCave_StevensRoom.ImStevenLetterForMe)
    ctx.say(GraniteCave_StevensRoom.ThankYouTakeThis)
    if (!ctx.giveItem(Items.TM47)) return
    ctx.setFlag(HoennFlags.FLAG_DELIVERED_STEVEN_LETTER)
    ctx.say(GraniteCave_StevensRoom.CouldBecomeChampionLetsRegister)
    ctx.sign(GraniteCave_StevensRoom.RegisteredSteven)
    ctx.setFlag(HoennFlags.FLAG_REGISTERED_STEVEN_POKENAV)
    ctx.say(GraniteCave_StevensRoom.IveGotToHurryAlong)
    ctx.despawnInteracted()
    ctx.setFlag(HoennFlags.FLAG_HIDE_GRANITE_CAVE_STEVEN)
  }
}

internal val GraniteCave_StevensRoomScripts: Map<String, Script> =
    mapOf(
        "GraniteCave_StevensRoom_EventScript_Steven" to GraniteCave_StevensRoom_EventScript_Steven,
    )
