package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route112
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BRICE = 626
private const val TRAINER_BRYANT = 746
private const val TRAINER_CAROL = 471
private const val TRAINER_LARRY = 213
private const val TRAINER_SHAYLA = 747

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_LeaderGoingToAwakenThing, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_WalkInPlaceFasterLeft
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_YeahWeNeedMeteorite, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_OhThatsWhyCrewWentToFallarbor, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_WalkInPlaceFasterLeft
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_CantLetAnyonePassUntilTheyreBack, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object Route112_EventScript_MagmaGrunts : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_MagmaGrunts")
}

internal object Route112_EventScript_Brice : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BRICE, Route112.BriceIntro, Route112.BriceDefeat)) return
    ctx.say(Route112.BricePostBattle)
  }
}

internal object Route112_EventScript_Larry : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LARRY, Route112.LarryIntro, Route112.LarryDefeat)) return
    ctx.say(Route112.LarryPostBattle)
  }
}

internal object Route112_EventScript_Carol : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAROL, Route112.CarolIntro, Route112.CarolDefeat)) return
    ctx.say(Route112.CarolPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TRENT_1, Route112_Text_TrentIntro, Route112_Text_TrentDefeat, Route112_EventScript_RegisterTrent
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route112_EventScript_RematchTrent
 * msgbox Route112_Text_TrentPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route112_EventScript_Trent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_Trent")
}

internal object Route112_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route112.NotEasyToGetBackToLavaridge)
}

internal object Route112_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NUGGET)
}

internal object Route112_EventScript_Bryant : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BRYANT, Route112.BryantIntro, Route112.BryantDefeat))
        return
    ctx.say(Route112.BryantPostBattle)
  }
}

internal object Route112_EventScript_Shayla : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SHAYLA, Route112.ShaylaIntro, Route112.ShaylaDefeat))
        return
    ctx.say(Route112.ShaylaPostBattle)
  }
}

internal object Route112_EventScript_MtChimneySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route112.MtChimneySign)
}

internal object Route112_EventScript_MtChimneyCableCarSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route112.MtChimneyCableCarSign)
}

internal object Route112_EventScript_RouteSignLavaridge : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route112.RouteSignLavaridge)
}

internal val Route112Scripts: Map<String, Script> =
    mapOf(
        "Route112_EventScript_MagmaGrunts" to Route112_EventScript_MagmaGrunts,
        "Route112_EventScript_Brice" to Route112_EventScript_Brice,
        "Route112_EventScript_Larry" to Route112_EventScript_Larry,
        "Route112_EventScript_Carol" to Route112_EventScript_Carol,
        "Route112_EventScript_Trent" to Route112_EventScript_Trent,
        "Route112_EventScript_Hiker" to Route112_EventScript_Hiker,
        "Route112_EventScript_ItemNugget" to Route112_EventScript_ItemNugget,
        "Route112_EventScript_Bryant" to Route112_EventScript_Bryant,
        "Route112_EventScript_Shayla" to Route112_EventScript_Shayla,
        "Route112_EventScript_MtChimneySign" to Route112_EventScript_MtChimneySign,
        "Route112_EventScript_MtChimneyCableCarSign" to Route112_EventScript_MtChimneyCableCarSign,
        "Route112_EventScript_RouteSignLavaridge" to Route112_EventScript_RouteSignLavaridge,
    )
