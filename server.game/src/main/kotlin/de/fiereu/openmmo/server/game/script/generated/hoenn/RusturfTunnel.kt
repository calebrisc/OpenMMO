package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RusturfTunnel
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val TRAINER_GRUNT_RUSTURF_TUNNEL = 16
private const val SPOKE_TO_BOYFRIEND = "hoenn/SPOKE_TO_WANDAS_BOYFRIEND"

private const val TRAINER_MIKE_2 = 635

internal object RusturfTunnel_EventScript_WandasBoyfriend : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(SPOKE_TO_BOYFRIEND)) return ctx.say(RusturfTunnel.ToGetToVerdanturf)
    ctx.setFlag(SPOKE_TO_BOYFRIEND)
    ctx.say(RusturfTunnel.WhyCantTheyKeepDigging)
  }
}

internal object RusturfTunnel_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POKE_BALL)
}

internal object RusturfTunnel_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ETHER)
}

internal object RusturfTunnel_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_RECOVERED_DEVON_GOODS)) return
    ctx.say(RusturfTunnel.GruntIntro)
    if (!ctx.trainerBattleSingle(TRAINER_GRUNT_RUSTURF_TUNNEL, null, RusturfTunnel.GruntDefeat))
        return
    ctx.say(RusturfTunnel.GruntTakePackage)
    ctx.despawnInteracted()
    // The Briney and Peeko reunion walk is collapsed into its two lines.
    ctx.sign(RusturfTunnel.PeekoGladToSeeYouSafe)
    ctx.sign(RusturfTunnel.ThankYouLetsGoHomePeeko)
    ctx.clearFlag(HoennFlags.FLAG_DEVON_GOODS_STOLEN)
    ctx.setFlag(HoennFlags.FLAG_RECOVERED_DEVON_GOODS)
    ctx.setVar(HoennVars.VAR_RUSTBORO_CITY_STATE, 4)
    ctx.setVar(HoennVars.VAR_BRINEY_HOUSE_STATE, 1)
    ctx.setFlag(HoennFlags.FLAG_HIDE_ROUTE_116_MR_BRINEY)
    ctx.send(notice("Recovered the DEVON GOODS!"))
  }
}

internal object RusturfTunnel_EventScript_Peeko : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RusturfTunnel.Peeko)
}

internal object RusturfTunnel_EventScript_Mike : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MIKE_2, RusturfTunnel.MikeIntro, RusturfTunnel.MikeDefeat))
        return
    ctx.say(RusturfTunnel.MikePostBattle)
  }
}

internal object RusturfTunnel_EventScript_Wanda : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RusturfTunnel.BoyfriendOnOtherSideOfRock)
}

internal val RusturfTunnelScripts: Map<String, Script> =
    mapOf(
        "RusturfTunnel_EventScript_WandasBoyfriend" to RusturfTunnel_EventScript_WandasBoyfriend,
        "RusturfTunnel_EventScript_ItemPokeBall" to RusturfTunnel_EventScript_ItemPokeBall,
        "RusturfTunnel_EventScript_ItemMaxEther" to RusturfTunnel_EventScript_ItemMaxEther,
        "RusturfTunnel_EventScript_Grunt" to RusturfTunnel_EventScript_Grunt,
        "RusturfTunnel_EventScript_Peeko" to RusturfTunnel_EventScript_Peeko,
        "RusturfTunnel_EventScript_Mike" to RusturfTunnel_EventScript_Mike,
        "RusturfTunnel_EventScript_Wanda" to RusturfTunnel_EventScript_Wanda,
    )
