package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

internal object RustboroCity_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(HoennFlags.FLAG_VISITED_RUSTBORO_CITY)
  }
}

internal object RustboroCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity.GymLeaderIsntEasyWithFire)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEVON_GOODS_STOLEN, RustboroCity_EventScript_FatManSawGrunt
 * msgbox RustboroCity_Text_WeShortenItToDevon, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_EventScript_FatMan")
}

internal object RustboroCity_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(RustboroCity.CatchRarePokemonIfIGoToSchool)
}

internal object RustboroCity_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity.WowYouHavePokemon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_POKENAV, RustboroCity_EventScript_Boy2BrineyLeftTunnel
 * msgbox RustboroCity_Text_MrBrineyWalksInTheTunnel, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_EventScript_Boy2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BADGE01_GET, RustboroCity_EventScript_Man1HaveBadge
 * msgbox RustboroCity_Text_HaveYouChallengedGym, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_EventScript_Man1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_EventScript_Man1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox RustboroCity_Text_PokemonCanChangeLookFromExp, MSGBOX_DEFAULT
 * applymovement LOCALID_RUSTBORO_LITTLE_BOY, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object RustboroCity_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_EventScript_LittleBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox RustboroCity_Text_PokemonChangeShape, MSGBOX_DEFAULT
 * applymovement LOCALID_RUSTBORO_LITTLE_GIRL, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object RustboroCity_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_EventScript_LittleGirl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECOVERED_DEVON_GOODS, RustboroCity_EventScript_ReturnGoodsSpokeToEmployee
 * msgbox RustboroCity_Text_ShadyCharacterTookOffTowardsTunnel, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_EventScript_DevonEmployee1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_EventScript_DevonEmployee1")
}

internal object RustboroCity_EventScript_DevonEmployee2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity.YoureNewAroundHere)
}

internal object RustboroCity_EventScript_ItemXDefend : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.X_DEFEND)
}

internal object RustboroCity_EventScript_Man2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity.TradePokemonGrowFast)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * call_if_unset FLAG_MET_RIVAL_RUSTBORO, RustboroCity_EventScript_PlayRivalMusic
 * applymovement LOCALID_RUSTBORO_RIVAL, Common_Movement_FacePlayer
 * waitmovement 0
 * goto RustboroCity_EventScript_RivalEncounter
 * ```
 */
internal object RustboroCity_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_EventScript_Rival")
}

internal object RustboroCity_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity.YouCanHave2On2Battle)
}

internal object RustboroCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity.GymSign)
}

internal object RustboroCity_EventScript_TrainersSchoolSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity.TrainersSchoolSign)
}

internal object RustboroCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity.CitySign)
}

internal object RustboroCity_EventScript_DevonCorpSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity.DevonCorpSign)
}

internal object RustboroCity_EventScript_TunnelSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity.TunnelNearingCompletion)
}

internal object RustboroCity_EventScript_CuttersHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity.CuttersHouse)
}

// The stolen-goods scene is five coordinate triggers outside the gym, registered here since the
// bootstrap never stubbed coord scripts. It reveals the tunnel grunt the rescue scene needs.
private suspend fun stolenGoodsScene(ctx: ScriptContext) {
  ctx.sign(RustboroCity.OutOfTheWay)
  ctx.sign(RustboroCity.WaitDontTakeMyGoods)
  ctx.setFlag(HoennFlags.FLAG_DEVON_GOODS_STOLEN)
  ctx.setVar(HoennVars.VAR_RUSTBORO_CITY_STATE, 2)
  ctx.setVar(HoennVars.VAR_RUSTURF_TUNNEL_STATE, 2)
  ctx.setVar(HoennVars.VAR_ROUTE116_STATE, 1)
  ctx.clearFlag(HoennFlags.FLAG_HIDE_ROUTE_116_MR_BRINEY)
  ctx.clearFlag(HoennFlags.FLAG_HIDE_RUSTURF_TUNNEL_PEEKO)
  ctx.clearFlag(HoennFlags.FLAG_HIDE_RUSTURF_TUNNEL_AQUA_GRUNT)
  ctx.setFlag(HoennFlags.FLAG_HIDE_BRINEYS_HOUSE_MR_BRINEY)
  ctx.setFlag(HoennFlags.FLAG_HIDE_BRINEYS_HOUSE_PEEKO)
  ctx.clearFlag(HoennFlags.FLAG_HIDE_RUSTBORO_CITY_DEVON_EMPLOYEE_1)
}

internal object RustboroCity_EventScript_StolenGoodsTrigger0 : Script {
  override suspend fun run(ctx: ScriptContext) = stolenGoodsScene(ctx)
}

internal object RustboroCity_EventScript_StolenGoodsTrigger1 : Script {
  override suspend fun run(ctx: ScriptContext) = stolenGoodsScene(ctx)
}

internal object RustboroCity_EventScript_StolenGoodsTrigger2 : Script {
  override suspend fun run(ctx: ScriptContext) = stolenGoodsScene(ctx)
}

internal object RustboroCity_EventScript_StolenGoodsTrigger3 : Script {
  override suspend fun run(ctx: ScriptContext) = stolenGoodsScene(ctx)
}

internal object RustboroCity_EventScript_StolenGoodsTrigger4 : Script {
  override suspend fun run(ctx: ScriptContext) = stolenGoodsScene(ctx)
}

internal val RustboroCityScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_EventScript_StolenGoodsTrigger4" to
            RustboroCity_EventScript_StolenGoodsTrigger4,
        "RustboroCity_EventScript_StolenGoodsTrigger3" to
            RustboroCity_EventScript_StolenGoodsTrigger3,
        "RustboroCity_EventScript_StolenGoodsTrigger2" to
            RustboroCity_EventScript_StolenGoodsTrigger2,
        "RustboroCity_EventScript_StolenGoodsTrigger1" to
            RustboroCity_EventScript_StolenGoodsTrigger1,
        "RustboroCity_EventScript_StolenGoodsTrigger0" to
            RustboroCity_EventScript_StolenGoodsTrigger0,
        "RustboroCity_OnTransition" to RustboroCity_OnTransition,
        "RustboroCity_EventScript_Woman" to RustboroCity_EventScript_Woman,
        "RustboroCity_EventScript_FatMan" to RustboroCity_EventScript_FatMan,
        "RustboroCity_EventScript_NinjaBoy" to RustboroCity_EventScript_NinjaBoy,
        "RustboroCity_EventScript_Twin" to RustboroCity_EventScript_Twin,
        "RustboroCity_EventScript_Boy2" to RustboroCity_EventScript_Boy2,
        "RustboroCity_EventScript_Man1" to RustboroCity_EventScript_Man1,
        "RustboroCity_EventScript_LittleBoy" to RustboroCity_EventScript_LittleBoy,
        "RustboroCity_EventScript_LittleGirl" to RustboroCity_EventScript_LittleGirl,
        "RustboroCity_EventScript_DevonEmployee1" to RustboroCity_EventScript_DevonEmployee1,
        "RustboroCity_EventScript_DevonEmployee2" to RustboroCity_EventScript_DevonEmployee2,
        "RustboroCity_EventScript_ItemXDefend" to RustboroCity_EventScript_ItemXDefend,
        "RustboroCity_EventScript_Man2" to RustboroCity_EventScript_Man2,
        "RustboroCity_EventScript_Rival" to RustboroCity_EventScript_Rival,
        "RustboroCity_EventScript_Boy1" to RustboroCity_EventScript_Boy1,
        "RustboroCity_EventScript_GymSign" to RustboroCity_EventScript_GymSign,
        "RustboroCity_EventScript_TrainersSchoolSign" to
            RustboroCity_EventScript_TrainersSchoolSign,
        "RustboroCity_EventScript_CitySign" to RustboroCity_EventScript_CitySign,
        "RustboroCity_EventScript_DevonCorpSign" to RustboroCity_EventScript_DevonCorpSign,
        "RustboroCity_EventScript_TunnelSign" to RustboroCity_EventScript_TunnelSign,
        "RustboroCity_EventScript_CuttersHouseSign" to RustboroCity_EventScript_CuttersHouseSign,
    )
