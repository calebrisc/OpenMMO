package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route25_SeaCottage
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object Route25_SeaCottage_EventScript_Bill : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_SS_TICKET)) {
      return ctx.say(Route25_SeaCottage.SSAnnePartyYouGoInstead)
    }
    if (!ctx.isFlagSet(KantoFlags.FLAG_HELPED_BILL_IN_SEA_COTTAGE)) {
      // The machine scene is not portable yet, so helping him is one conversation.
      ctx.say(
          if (ctx.isFemale) Route25_SeaCottage.ImBillHelpMeOutLady
          else Route25_SeaCottage.ImBillHelpMeOutPal)
      ctx.say(Route25_SeaCottage.RunCellSeparationOnPC)
      ctx.setFlag(KantoFlags.FLAG_HELPED_BILL_IN_SEA_COTTAGE)
    }
    ctx.say(
        if (ctx.isFemale) Route25_SeaCottage.ThanksLadyTakeThis
        else Route25_SeaCottage.ThanksBudTakeThis)
    if (!ctx.giveItem(Items.S_S_TICKET)) {
      return ctx.say(Route25_SeaCottage.YouveGotTooMuchStuff)
    }
    ctx.setFlag(KantoFlags.FLAG_GOT_SS_TICKET)
    ctx.sign(Route25_SeaCottage.ReceivedSSTicketFromBill)
    ctx.say(Route25_SeaCottage.SSAnnePartyYouGoInstead)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set RETURN_AFTER_SS_TICKET, Route25_SeaCottage_EventScript_OpenBillsMonList
 * goto_if_set BILL_IN_TELEPORTER, Route25_SeaCottage_EventScript_RunCellSeparator
 * msgbox Route25_SeaCottage_Text_TeleporterIsDisplayed
 * releaseall
 * end
 * ```
 */
internal object Route25_SeaCottage_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) =
      // The decomp branches on whether Bill is still inside the teleporter and whether the ticket
      // has been collected, but helping him is one conversation here, so the machine only ever has
      // the two states either side of it: his monster list once he is out, the display before.
      if (ctx.isFlagSet(KantoFlags.FLAG_GOT_SS_TICKET)) {
        ctx.sign(Route25_SeaCottage.BillsFavoriteMonList)
      } else {
        ctx.sign(Route25_SeaCottage.TeleporterIsDisplayed)
      }
}

internal val Route25_SeaCottageScripts: Map<String, Script> =
    mapOf(
        "Route25_SeaCottage_EventScript_Bill" to Route25_SeaCottage_EventScript_Bill,
        "Route25_SeaCottage_EventScript_Computer" to Route25_SeaCottage_EventScript_Computer,
    )
