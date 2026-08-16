package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_BikeShop
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

/**
 * The bike shop, which sells one thing nobody can afford and exchanges one thing anybody can.
 *
 * The decomp opens a multichoice menu here and this server cannot draw one, so the counter offer is
 * not asked -- but the only outcome behind it is being told the price, since a bicycle costs a
 * million and the shop refuses every player in the games too. The voucher path is the whole usable
 * feature and it is here in full. The voucher itself is a flag rather than an item: this game's
 * item table is the Gen 5 one and has no voucher in it.
 */
internal object CeruleanCity_BikeShop_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_BICYCLE)) {
      ctx.say(CeruleanCity_BikeShop.HowDoYouLikeNewBicycle)
      return
    }
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_BIKE_VOUCHER)) {
      ctx.say(CeruleanCity_BikeShop.OhBikeVoucherHereYouGo)
      if (!ctx.giveItem(Items.BICYCLE)) {
        ctx.say(CeruleanCity_BikeShop.MakeRoomForBicycle)
        return
      }
      ctx.setFlag(KantoFlags.FLAG_GOT_BICYCLE)
      ctx.say(CeruleanCity_BikeShop.ExchangedVoucherForBicycle)
      ctx.say(CeruleanCity_BikeShop.ThankYouComeAgain)
      return
    }
    ctx.say(CeruleanCity_BikeShop.WelcomeToBikeShop)
    ctx.say(CeruleanCity_BikeShop.SorryYouCantAffordIt)
    ctx.say(CeruleanCity_BikeShop.ThankYouComeAgain)
  }
}

internal object CeruleanCity_BikeShop_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_BICYCLE)) {
      ctx.say(CeruleanCity_BikeShop.WowYourBikeIsCool)
      return
    }
    ctx.say(CeruleanCity_BikeShop.BikesCoolButExpensive)
  }
}

internal object CeruleanCity_BikeShop_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_BikeShop.CityBikeGoodEnoughForMe)
}

internal val CeruleanCity_BikeShopScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_BikeShop_EventScript_Clerk" to CeruleanCity_BikeShop_EventScript_Clerk,
        "CeruleanCity_BikeShop_EventScript_Youngster" to
            CeruleanCity_BikeShop_EventScript_Youngster,
        "CeruleanCity_BikeShop_EventScript_Woman" to CeruleanCity_BikeShop_EventScript_Woman,
    )
