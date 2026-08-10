package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RusturfTunnel
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_MIKE_2 = 635

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TEMP_1, RusturfTunnel_EventScript_AlreadySpokenTo
 * setflag FLAG_TEMP_1
 * msgbox RusturfTunnel_Text_WhyCantTheyKeepDigging, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_WandasBoyfriend : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RusturfTunnel_EventScript_WandasBoyfriend")
}

internal object RusturfTunnel_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POKE_BALL)
}

internal object RusturfTunnel_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ETHER)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * playbgm MUS_ENCOUNTER_AQUA, FALSE
 * msgbox RusturfTunnel_Text_GruntIntro, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_GRUNT_RUSTURF_TUNNEL, RusturfTunnel_Text_GruntDefeat
 * msgbox RusturfTunnel_Text_GruntTakePackage, MSGBOX_DEFAULT
 * giveitem ITEM_DEVON_GOODS
 * closemessage
 * applymovement LOCALID_PLAYER, RusturfTunnel_Movement_PushPlayerAsideForGrunt
 * applymovement LOCALID_RUSTURF_TUNNEL_GRUNT, RusturfTunnel_Movement_GruntEscape
 * waitmovement 0
 * removeobject LOCALID_RUSTURF_TUNNEL_GRUNT
 * delay 50
 * addobject LOCALID_RUSTURF_TUNNEL_BRINEY
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, RusturfTunnel_Movement_BrineyApproachPeeko1
 * waitmovement 0
 * applymovement LOCALID_PLAYER, RusturfTunnel_Movement_PlayerMoveAsideForBriney
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, RusturfTunnel_Movement_BrineyApproachPeeko2
 * waitmovement 0
 * msgbox RusturfTunnel_Text_PeekoGladToSeeYouSafe, MSGBOX_DEFAULT
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, Common_Movement_FacePlayer
 * waitmovement 0
 * message RusturfTunnel_Text_ThankYouLetsGoHomePeeko
 * waitmessage
 * waitse
 * playmoncry SPECIES_WINGULL, CRY_MODE_NORMAL
 * waitbuttonpress
 * waitmoncry
 * closemessage
 * applymovement LOCALID_PLAYER, RusturfTunnel_Movement_PlayerWatchBrineyExit
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, RusturfTunnel_Movement_BrineyExit
 * applymovement LOCALID_RUSTURF_TUNNEL_PEEKO, RusturfTunnel_Movement_PeekoExit
 * waitmovement 0
 * removeobject LOCALID_RUSTURF_TUNNEL_BRINEY
 * removeobject LOCALID_RUSTURF_TUNNEL_PEEKO
 * clearflag FLAG_DEVON_GOODS_STOLEN
 * setflag FLAG_RECOVERED_DEVON_GOODS
 * setvar VAR_RUSTBORO_CITY_STATE, 4
 * setvar VAR_BRINEY_HOUSE_STATE, 1
 * setflag FLAG_HIDE_ROUTE_116_MR_BRINEY
 * release
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_Grunt")
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

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox RusturfTunnel_Text_BoyfriendOnOtherSideOfRock, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_Wanda : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_Wanda")
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
