package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.MtMoon_B2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TEAM_ROCKET_GRUNT = 351
private const val TRAINER_TEAM_ROCKET_GRUNT_2 = 352
private const val TRAINER_TEAM_ROCKET_GRUNT_3 = 353
private const val TRAINER_TEAM_ROCKET_GRUNT_4 = 354

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MtMoon_B2F_Text_YouWantDomeFossil, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MtMoon_B2F_EventScript_DontTakeFossil
 * removeobject LOCALID_DOME_FOSSIL
 * giveitem_msg MtMoon_B2F_Text_ObtainedDomeFossil, ITEM_DOME_FOSSIL, 1, MUS_OBTAIN_KEY_ITEM
 * closemessage
 * special QuestLog_CutRecording
 * delay 10
 * applymovement LOCALID_MIGUEL, MtMoon_B2F_Movement_MiguelToHelixFossil
 * waitmovement 0
 * copyobjectxytoperm LOCALID_MIGUEL
 * textcolor NPC_TEXT_COLOR_MALE
 * playfanfare MUS_OBTAIN_KEY_ITEM
 * message MtMoon_B2F_Text_ThenThisFossilIsMine
 * waitmessage
 * waitfanfare
 * removeobject LOCALID_HELIX_FOSSIL
 * setflag FLAG_GOT_DOME_FOSSIL
 * setflag FLAG_GOT_FOSSIL_FROM_MT_MOON
 * release
 * end
 * ```
 */
internal object MtMoon_B2F_EventScript_DomeFossil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_B2F_EventScript_DomeFossil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MtMoon_B2F_Text_YouWantHelixFossil, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MtMoon_B2F_EventScript_DontTakeFossil
 * removeobject LOCALID_HELIX_FOSSIL
 * giveitem_msg MtMoon_B2F_Text_ObtainedHelixFossil, ITEM_HELIX_FOSSIL, 1, MUS_OBTAIN_KEY_ITEM
 * closemessage
 * special QuestLog_CutRecording
 * delay 10
 * applymovement LOCALID_MIGUEL, MtMoon_B2F_Movement_MiguelToDomeFossil
 * waitmovement 0
 * copyobjectxytoperm LOCALID_MIGUEL
 * textcolor NPC_TEXT_COLOR_MALE
 * playfanfare MUS_OBTAIN_KEY_ITEM
 * message MtMoon_B2F_Text_ThenThisFossilIsMine
 * waitmessage
 * waitfanfare
 * removeobject LOCALID_DOME_FOSSIL
 * setflag FLAG_GOT_HELIX_FOSSIL
 * setflag FLAG_GOT_FOSSIL_FROM_MT_MOON
 * release
 * end
 * ```
 */
internal object MtMoon_B2F_EventScript_HelixFossil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_B2F_EventScript_HelixFossil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_FOSSIL_FROM_MT_MOON, MtMoon_B2F_EventScript_MiguelFossilPicked
 * goto_if_defeated TRAINER_SUPER_NERD_MIGUEL, MtMoon_B2F_EventScript_MiguelGoPickFossil
 * call MtMoon_B2F_EventScript_BattleMiguel
 * release
 * end
 * ```
 */
internal object MtMoon_B2F_EventScript_Miguel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_B2F_EventScript_Miguel")
}

internal object MtMoon_B2F_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_4, MtMoon_B2F.Grunt4Intro, MtMoon_B2F.Grunt4Defeat))
        return
    ctx.say(MtMoon_B2F.Grunt4PostBattle)
  }
}

internal object MtMoon_B2F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT, MtMoon_B2F.Grunt1Intro, MtMoon_B2F.Grunt1Defeat))
        return
    ctx.say(MtMoon_B2F.Grunt1PostBattle)
  }
}

internal object MtMoon_B2F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_3, MtMoon_B2F.Grunt3Intro, MtMoon_B2F.Grunt3Defeat))
        return
    ctx.say(MtMoon_B2F.Grunt3PostBattle)
  }
}

internal object MtMoon_B2F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_2, MtMoon_B2F.Grunt2Intro, MtMoon_B2F.Grunt2Defeat))
        return
    ctx.say(MtMoon_B2F.Grunt2PostBattle)
  }
}

internal object MtMoon_B2F_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.STAR_PIECE)
}

internal object MtMoon_B2F_EventScript_ItemTM46 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM46)
}

internal object MtMoon_B2F_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.REVIVE)
}

internal object MtMoon_B2F_EventScript_ItemAntidote : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ANTIDOTE)
}

internal val MtMoon_B2FScripts: Map<String, Script> =
    mapOf(
        "MtMoon_B2F_EventScript_DomeFossil" to MtMoon_B2F_EventScript_DomeFossil,
        "MtMoon_B2F_EventScript_HelixFossil" to MtMoon_B2F_EventScript_HelixFossil,
        "MtMoon_B2F_EventScript_Miguel" to MtMoon_B2F_EventScript_Miguel,
        "MtMoon_B2F_EventScript_Grunt4" to MtMoon_B2F_EventScript_Grunt4,
        "MtMoon_B2F_EventScript_Grunt1" to MtMoon_B2F_EventScript_Grunt1,
        "MtMoon_B2F_EventScript_Grunt3" to MtMoon_B2F_EventScript_Grunt3,
        "MtMoon_B2F_EventScript_Grunt2" to MtMoon_B2F_EventScript_Grunt2,
        "MtMoon_B2F_EventScript_ItemStarPiece" to MtMoon_B2F_EventScript_ItemStarPiece,
        "MtMoon_B2F_EventScript_ItemTM46" to MtMoon_B2F_EventScript_ItemTM46,
        "MtMoon_B2F_EventScript_ItemRevive" to MtMoon_B2F_EventScript_ItemRevive,
        "MtMoon_B2F_EventScript_ItemAntidote" to MtMoon_B2F_EventScript_ItemAntidote,
    )
