package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MagmaHideout_4F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GRUNT_MAGMA_HIDEOUT_11 = 726
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_12 = 727
private const val TRAINER_GRUNT_MAGMA_HIDEOUT_13 = 728
private const val TRAINER_TABITHA_MAGMA_HIDEOUT = 732

internal object MagmaHideout_4F_EventScript_Grunt11 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_11,
        MagmaHideout_4F.Grunt11Intro,
        MagmaHideout_4F.Grunt11Defeat))
        return
    ctx.say(MagmaHideout_4F.Grunt11PostBattle)
  }
}

internal object MagmaHideout_4F_EventScript_Grunt12 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_12,
        MagmaHideout_4F.Grunt12Intro,
        MagmaHideout_4F.Grunt12Defeat))
        return
    ctx.say(MagmaHideout_4F.Grunt12PostBattle)
  }
}

internal object MagmaHideout_4F_EventScript_Grunt13 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GRUNT_MAGMA_HIDEOUT_13,
        MagmaHideout_4F.Grunt13Intro,
        MagmaHideout_4F.Grunt13Defeat))
        return
    ctx.say(MagmaHideout_4F.Grunt13PostBattle)
  }
}

internal object MagmaHideout_4F_EventScript_Tabitha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TABITHA_MAGMA_HIDEOUT, MagmaHideout_4F.TabithaIntro, MagmaHideout_4F.TabithaDefeat))
        return
    ctx.say(MagmaHideout_4F.TabithaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * playbgm MUS_ENCOUNTER_MAGMA, FALSE
 * msgbox MagmaHideout_4F_Text_MaxieAwakenGroudon, MSGBOX_DEFAULT
 * closemessage
 * delay 20
 * setvar VAR_RESULT, 1
 * playse SE_M_DETECT
 * dofieldeffectsparkle 18, 42, 0
 * waitfieldeffect FLDEFF_SPARKLE
 * setvar VAR_RESULT, 1
 * playfanfare MUS_AWAKEN_LEGEND
 * playse SE_ORB
 * special DoOrbEffect
 * applymovement LOCALID_PLAYER, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * delay 150
 * removeobject LOCALID_MAGMA_HIDEOUT_4F_GROUDON_SLEEPING
 * addobject LOCALID_MAGMA_HIDEOUT_4F_GROUDON
 * waitstate
 * delay 60
 * applymovement LOCALID_MAGMA_HIDEOUT_4F_GROUDON, MagmaHideout_4F_Movement_GroudonApproach
 * waitmovement 0
 * special FadeOutOrbEffect
 * setvar VAR_0x8004, 1  @ vertical pan
 * setvar VAR_0x8005, 1  @ horizontal pan
 * setvar VAR_0x8006, 8  @ num shakes
 * setvar VAR_0x8007, 5  @ shake delay
 * special ShakeCamera
 * waitstate
 * applymovement LOCALID_MAGMA_HIDEOUT_4F_GROUDON, MagmaHideout_4F_Movement_GroudonExit
 * waitmovement 0
 * removeobject LOCALID_MAGMA_HIDEOUT_4F_GROUDON
 * delay 4
 * setvar VAR_0x8004, 2  @ vertical pan
 * setvar VAR_0x8005, 2  @ horizontal pan
 * setvar VAR_0x8006, 8  @ num shakes
 * setvar VAR_0x8007, 5  @ shake delay
 * special ShakeCamera
 * waitstate
 * delay 30
 * applymovement LOCALID_MAGMA_HIDEOUT_4F_MAXIE, MagmaHideout_4F_Movement_MaxieLookAround
 * waitmovement 0
 * msgbox MagmaHideout_4F_Text_MaxieGroudonWhatsWrong, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_PLAYER, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * delay 30
 * applymovement LOCALID_MAGMA_HIDEOUT_4F_MAXIE, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox MagmaHideout_4F_Text_MaxieOhItWasYou, MSGBOX_DEFAULT
 * closemessage
 * trainerbattle_no_intro TRAINER_MAXIE_MAGMA_HIDEOUT, MagmaHideout_4F_Text_MaxieDefeat
 * msgbox MagmaHideout_4F_Text_MaxieImGoingAfterGroudon, MSGBOX_DEFAULT
 * closemessage
 * clearflag FLAG_HIDE_SLATEPORT_CITY_CAPTAIN_STERN
 * clearflag FLAG_HIDE_SLATEPORT_CITY_GABBY_AND_TY
 * setvar VAR_SLATEPORT_CITY_STATE, 1
 * setflag FLAG_GROUDON_AWAKENED_MAGMA_HIDEOUT
 * setvar VAR_SLATEPORT_HARBOR_STATE, 1
 * fadescreen FADE_TO_BLACK
 * removeobject LOCALID_MAGMA_HIDEOUT_4F_MAXIE
 * removeobject LOCALID_MAGMA_HIDEOUT_4F_GRUNT_1
 * removeobject LOCALID_MAGMA_HIDEOUT_4F_GRUNT_2
 * removeobject LOCALID_MAGMA_HIDEOUT_4F_GRUNT_3
 * removeobject LOCALID_MAGMA_HIDEOUT_4F_TABITHA
 * setflag FLAG_HIDE_MAGMA_HIDEOUT_GRUNTS
 * fadescreen FADE_FROM_BLACK
 * releaseall
 * end
 * ```
 */
internal object MagmaHideout_4F_EventScript_Maxie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_4F_EventScript_Maxie")
}

internal object MagmaHideout_4F_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_REVIVE)
}

internal val MagmaHideout_4FScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_4F_EventScript_Grunt11" to MagmaHideout_4F_EventScript_Grunt11,
        "MagmaHideout_4F_EventScript_Grunt12" to MagmaHideout_4F_EventScript_Grunt12,
        "MagmaHideout_4F_EventScript_Grunt13" to MagmaHideout_4F_EventScript_Grunt13,
        "MagmaHideout_4F_EventScript_Tabitha" to MagmaHideout_4F_EventScript_Tabitha,
        "MagmaHideout_4F_EventScript_Maxie" to MagmaHideout_4F_EventScript_Maxie,
        "MagmaHideout_4F_EventScript_ItemMaxRevive" to MagmaHideout_4F_EventScript_ItemMaxRevive,
    )
