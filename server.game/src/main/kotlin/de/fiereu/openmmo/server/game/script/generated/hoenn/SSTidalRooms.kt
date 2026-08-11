package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SSTidalRooms
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_LEA_AND_JED = 641

private const val TRAINER_COLTON = 294
private const val TRAINER_GARRET = 138
private const val TRAINER_MICAH = 255
private const val TRAINER_NAOMI = 119
private const val TRAINER_THOMAS = 256

internal object SSTidalRooms_EventScript_Colton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COLTON, SSTidalRooms.ColtonIntro, SSTidalRooms.ColtonDefeat))
        return
    ctx.say(SSTidalRooms.ColtonPostBattle)
  }
}

internal object SSTidalRooms_EventScript_Micah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MICAH, SSTidalRooms.MicahIntro, SSTidalRooms.MicahDefeat))
        return
    ctx.say(SSTidalRooms.MicahPostBattle)
  }
}

internal object SSTidalRooms_EventScript_Thomas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_THOMAS, SSTidalRooms.ThomasIntro, SSTidalRooms.ThomasDefeat))
        return
    ctx.say(SSTidalRooms.ThomasPostBattle)
  }
}

internal object SSTidalRooms_EventScript_Jed : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LEA_AND_JED, SSTidalRooms.JedIntro, SSTidalRooms.JedDefeat))
        return
    ctx.say(SSTidalRooms.JedPostBattle)
  }
}

internal object SSTidalRooms_EventScript_Lea : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LEA_AND_JED, SSTidalRooms.LeaIntro, SSTidalRooms.LeaDefeat))
        return
    ctx.say(SSTidalRooms.LeaPostBattle)
  }
}

internal object SSTidalRooms_EventScript_Garret : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GARRET, SSTidalRooms.GarretIntro, SSTidalRooms.GarretDefeat))
        return
    ctx.say(SSTidalRooms.GarretPostBattle)
  }
}

internal object SSTidalRooms_EventScript_Naomi : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_NAOMI, SSTidalRooms.NaomiIntro, SSTidalRooms.NaomiDefeat))
        return
    ctx.say(SSTidalRooms.NaomiPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_SNATCH, SSTidalRooms_EventScript_ExplainSnatch
 * msgbox SSTidalRooms_Text_NotSuspiciousTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_SNATCH
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_SNATCH
 * msgbox SSTidalRooms_Text_ExplainSnatch, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_SnatchGiver : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_SnatchGiver")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox SSTidalRooms_Text_TakeRestOnBed, MSGBOX_DEFAULT
 * closemessage
 * call Common_EventScript_OutOfCenterPartyHeal
 * call SSTidalRooms_EventScript_ProgessCruiseAfterBed
 * releaseall
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Bed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Bed")
}

internal val SSTidalRoomsScripts: Map<String, Script> =
    mapOf(
        "SSTidalRooms_EventScript_Colton" to SSTidalRooms_EventScript_Colton,
        "SSTidalRooms_EventScript_Micah" to SSTidalRooms_EventScript_Micah,
        "SSTidalRooms_EventScript_Thomas" to SSTidalRooms_EventScript_Thomas,
        "SSTidalRooms_EventScript_Jed" to SSTidalRooms_EventScript_Jed,
        "SSTidalRooms_EventScript_Lea" to SSTidalRooms_EventScript_Lea,
        "SSTidalRooms_EventScript_Garret" to SSTidalRooms_EventScript_Garret,
        "SSTidalRooms_EventScript_Naomi" to SSTidalRooms_EventScript_Naomi,
        "SSTidalRooms_EventScript_SnatchGiver" to SSTidalRooms_EventScript_SnatchGiver,
        "SSTidalRooms_EventScript_Bed" to SSTidalRooms_EventScript_Bed,
    )
