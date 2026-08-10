package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.RockTunnel_B1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_HIKER_ALLEN = 190
private const val TRAINER_HIKER_DUDLEY = 189
private const val TRAINER_HIKER_ERIC = 191
private const val TRAINER_PICNICKER_MARTHA = 159
private const val TRAINER_PICNICKER_SOFIA = 158
private const val TRAINER_POKEMANIAC_COOPER = 164
private const val TRAINER_POKEMANIAC_STEVE = 165
private const val TRAINER_POKEMANIAC_WINSTON = 166

internal object RockTunnel_B1F_EventScript_Cooper : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_POKEMANIAC_COOPER, RockTunnel_B1F.CooperIntro, RockTunnel_B1F.CooperDefeat))
        return
    ctx.say(RockTunnel_B1F.CooperPostBattle)
  }
}

internal object RockTunnel_B1F_EventScript_Dudley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_DUDLEY, RockTunnel_B1F.DudleyIntro, RockTunnel_B1F.DudleyDefeat))
        return
    ctx.say(RockTunnel_B1F.DudleyPostBattle)
  }
}

internal object RockTunnel_B1F_EventScript_Sofia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_SOFIA, RockTunnel_B1F.SofiaIntro, RockTunnel_B1F.SofiaDefeat))
        return
    ctx.say(RockTunnel_B1F.SofiaPostBattle)
  }
}

internal object RockTunnel_B1F_EventScript_Allen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_ALLEN, RockTunnel_B1F.AllenIntro, RockTunnel_B1F.AllenDefeat))
        return
    ctx.say(RockTunnel_B1F.AllenPostBattle)
  }
}

internal object RockTunnel_B1F_EventScript_Eric : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_ERIC, RockTunnel_B1F.EricIntro, RockTunnel_B1F.EricDefeat))
        return
    ctx.say(RockTunnel_B1F.EricPostBattle)
  }
}

internal object RockTunnel_B1F_EventScript_Steve : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_POKEMANIAC_STEVE, RockTunnel_B1F.SteveIntro, RockTunnel_B1F.SteveDefeat))
        return
    ctx.say(RockTunnel_B1F.StevePostBattle)
  }
}

internal object RockTunnel_B1F_EventScript_Martha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_MARTHA, RockTunnel_B1F.MarthaIntro, RockTunnel_B1F.MarthaDefeat))
        return
    ctx.say(RockTunnel_B1F.MarthaPostBattle)
  }
}

internal object RockTunnel_B1F_EventScript_Winston : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_POKEMANIAC_WINSTON, RockTunnel_B1F.WinstonIntro, RockTunnel_B1F.WinstonDefeat))
        return
    ctx.say(RockTunnel_B1F.WinstonPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_ROCK_SLIDE, EventScript_RockSlideTaught
 * msgbox Text_RockSlideTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_RockSlideDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_RockSlideDeclined
 * msgbox Text_RockSlideWhichMon
 * setvar VAR_0x8005, MOVETUTOR_ROCK_SLIDE
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_RockSlideDeclined
 * setflag FLAG_TUTOR_ROCK_SLIDE
 * goto EventScript_RockSlideTaught
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_RockSlideTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RockTunnel_B1F_EventScript_RockSlideTutor")
}

internal object RockTunnel_B1F_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.REVIVE)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ETHER
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RockTunnel_B1F_EventScript_ItemMaxEther")
}

internal val RockTunnel_B1FScripts: Map<String, Script> =
    mapOf(
        "RockTunnel_B1F_EventScript_Cooper" to RockTunnel_B1F_EventScript_Cooper,
        "RockTunnel_B1F_EventScript_Dudley" to RockTunnel_B1F_EventScript_Dudley,
        "RockTunnel_B1F_EventScript_Sofia" to RockTunnel_B1F_EventScript_Sofia,
        "RockTunnel_B1F_EventScript_Allen" to RockTunnel_B1F_EventScript_Allen,
        "RockTunnel_B1F_EventScript_Eric" to RockTunnel_B1F_EventScript_Eric,
        "RockTunnel_B1F_EventScript_Steve" to RockTunnel_B1F_EventScript_Steve,
        "RockTunnel_B1F_EventScript_Martha" to RockTunnel_B1F_EventScript_Martha,
        "RockTunnel_B1F_EventScript_Winston" to RockTunnel_B1F_EventScript_Winston,
        "RockTunnel_B1F_EventScript_RockSlideTutor" to RockTunnel_B1F_EventScript_RockSlideTutor,
        "RockTunnel_B1F_EventScript_ItemRevive" to RockTunnel_B1F_EventScript_ItemRevive,
        "RockTunnel_B1F_EventScript_ItemMaxEther" to RockTunnel_B1F_EventScript_ItemMaxEther,
    )
