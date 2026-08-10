package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_DevonCorp_3F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

internal object RustboroCity_DevonCorp_3F_EventScript_MrStone : Script {
  override suspend fun run(ctx: ScriptContext) {
    when {
      ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_EXP_SHARE) ->
          ctx.say(RustboroCity_DevonCorp_3F.VisitCaptSternShipyard)
      ctx.isFlagSet(HoennFlags.FLAG_DELIVERED_STEVEN_LETTER) -> {
        ctx.say(RustboroCity_DevonCorp_3F.ThankYouForDeliveringLetter)
        if (!ctx.giveItem(Items.EXP_SHARE)) return
        ctx.setFlag(HoennFlags.FLAG_RECEIVED_EXP_SHARE)
        ctx.say(RustboroCity_DevonCorp_3F.ExplainExpShare)
      }
      else -> ctx.say(RustboroCity_DevonCorp_3F.CountingOnYou)
    }
  }
}

internal object RustboroCity_DevonCorp_3F_EventScript_Employee : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_DevonCorp_3F.RepeatAndTimerHugelyPopular)
}

internal object RustboroCity_DevonCorp_3F_EventScript_RareRocksDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(RustboroCity_DevonCorp_3F.RareRocksDisplay)
}

// The president scene is this floor's on-frame script, registered here like the champion's.
internal object RustboroCity_DevonCorp_3F_EventScript_MeetPresident : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_DEVON_CORP_3F_STATE) != 0) return
    if (!ctx.isFlagSet(HoennFlags.FLAG_RECOVERED_DEVON_GOODS)) return
    ctx.sign(RustboroCity_DevonCorp_3F.ThisIs3rdFloorWaitHere)
    ctx.sign(RustboroCity_DevonCorp_3F.WordWithPresidentComeWithMe)
    ctx.sign(RustboroCity_DevonCorp_3F.PleaseGoAhead)
    ctx.say(RustboroCity_DevonCorp_3F.MrStoneIHaveFavor)
    // The letter is carried as story state; Steven's script completes the delivery.
    ctx.say(RustboroCity_DevonCorp_3F.MrStoneWantYouToHaveThis)
    ctx.sign(RustboroCity_DevonCorp_3F.ReceivedPokenav)
    ctx.setFlag(HoennFlags.FLAG_SYS_POKENAV_GET)
    ctx.setFlag(HoennFlags.FLAG_RECEIVED_POKENAV)
    ctx.say(RustboroCity_DevonCorp_3F.MrStoneExplainPokenavRestUp)
    ctx.healParty()
    ctx.say(RustboroCity_DevonCorp_3F.MrStoneGoWithCautionAndCare)
    ctx.setFlag(HoennFlags.FLAG_HIDE_ROUTE_116_WANDAS_BOYFRIEND)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_RUSTURF_TUNNEL_WANDAS_BOYFRIEND)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_RUSTURF_TUNNEL_WANDA)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_BRINEYS_HOUSE_MR_BRINEY)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_BRINEYS_HOUSE_PEEKO)
    ctx.setVar(HoennVars.VAR_BRINEY_LOCATION, 1)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_RUSTBORO_CITY_RIVAL)
    ctx.setVar(HoennVars.VAR_DEVON_CORP_3F_STATE, 1)
    ctx.setVar(HoennVars.VAR_RUSTBORO_CITY_STATE, 6)
  }
}

internal val RustboroCity_DevonCorp_3FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_DevonCorp_3F_EventScript_MeetPresident" to
            RustboroCity_DevonCorp_3F_EventScript_MeetPresident,
        "RustboroCity_DevonCorp_3F_EventScript_MrStone" to
            RustboroCity_DevonCorp_3F_EventScript_MrStone,
        "RustboroCity_DevonCorp_3F_EventScript_Employee" to
            RustboroCity_DevonCorp_3F_EventScript_Employee,
        "RustboroCity_DevonCorp_3F_EventScript_RareRocksDisplay" to
            RustboroCity_DevonCorp_3F_EventScript_RareRocksDisplay,
    )
