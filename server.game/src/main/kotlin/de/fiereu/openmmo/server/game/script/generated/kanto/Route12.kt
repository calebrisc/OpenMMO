package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route12
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_CAMPER_JUSTIN = 477
private const val TRAINER_FISHERMAN_ANDREW = 233
private const val TRAINER_FISHERMAN_CHIP = 226
private const val TRAINER_FISHERMAN_ELLIOT = 228
private const val TRAINER_FISHERMAN_HANK = 227
private const val TRAINER_FISHERMAN_NED = 225
private const val TRAINER_ROCKER_LUCA = 285

internal object Route12_EventScript_Ned : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_FISHERMAN_NED, Route12.NedIntro, Route12.NedDefeat)) return
    ctx.say(Route12.NedPostBattle)
  }
}

internal object Route12_EventScript_Chip : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_FISHERMAN_CHIP, Route12.ChipIntro, Route12.ChipDefeat))
        return
    ctx.say(Route12.ChipPostBattle)
  }
}

internal object Route12_EventScript_Hank : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_FISHERMAN_HANK, Route12.HankIntro, Route12.HankDefeat))
        return
    ctx.say(Route12.HankPostBattle)
  }
}

internal object Route12_EventScript_Elliot : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FISHERMAN_ELLIOT, Route12.ElliotIntro, Route12.ElliotDefeat))
        return
    ctx.say(Route12.ElliotPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_GOT_POKE_FLUTE, Route12_EventScript_SnorlaxNoPokeFlute
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * msgbox Text_WantToUsePokeFlute, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Route12_EventScript_DontUsePokeFlute
 * call EventScript_AwakenSnorlax
 * setwildbattle SPECIES_SNORLAX, 30
 * waitse
 * playmoncry SPECIES_SNORLAX, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_HIDE_ROUTE_12_SNORLAX
 * setflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * setflag FLAG_WOKE_UP_ROUTE_12_SNORLAX
 * dowildbattle
 * clearflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, Route12_EventScript_FoughtSnorlax
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, Route12_EventScript_FoughtSnorlax
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, Route12_EventScript_FoughtSnorlax
 * release
 * end
 * ```
 */
internal object Route12_EventScript_Snorlax : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route12_EventScript_Snorlax")
}

internal object Route12_EventScript_Luca : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ROCKER_LUCA, Route12.LucaIntro, Route12.LucaDefeat)) return
    ctx.say(Route12.LucaPostBattle)
  }
}

internal object Route12_EventScript_Justin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_JUSTIN, Route12.JustinIntro, Route12.JustinDefeat))
        return
    ctx.say(Route12.JustinPostBattle)
  }
}

internal object Route12_EventScript_Andrew : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FISHERMAN_ANDREW, Route12.AndrewIntro, Route12.AndrewDefeat))
        return
    ctx.say(Route12.AndrewPostBattle)
  }
}

internal object Route12_EventScript_ItemTM48 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM48)
}

internal object Route12_EventScript_ItemIron : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.IRON)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_YOUNG_COUPLE_GIA_JES, Route12_Text_GiaIntro, Route12_Text_GiaDefeat, Route12_Text_GiaNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route12_EventScript_GiaRematch
 * msgbox Route12_Text_GiaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route12_EventScript_Gia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route12_EventScript_Gia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_YOUNG_COUPLE_GIA_JES, Route12_Text_JesIntro, Route12_Text_JesDefeat, Route12_Text_JesNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route12_EventScript_JesRematch
 * msgbox Route12_Text_JesPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route12_EventScript_Jes : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route12_EventScript_Jes")
}

internal object Route12_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route12.RouteSign)
}

internal object Route12_EventScript_FishingSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route12.SportfishingArea)
}

internal val Route12Scripts: Map<String, Script> =
    mapOf(
        "Route12_EventScript_Ned" to Route12_EventScript_Ned,
        "Route12_EventScript_Chip" to Route12_EventScript_Chip,
        "Route12_EventScript_Hank" to Route12_EventScript_Hank,
        "Route12_EventScript_Elliot" to Route12_EventScript_Elliot,
        "Route12_EventScript_Snorlax" to Route12_EventScript_Snorlax,
        "Route12_EventScript_Luca" to Route12_EventScript_Luca,
        "Route12_EventScript_Justin" to Route12_EventScript_Justin,
        "Route12_EventScript_Andrew" to Route12_EventScript_Andrew,
        "Route12_EventScript_ItemTM48" to Route12_EventScript_ItemTM48,
        "Route12_EventScript_ItemIron" to Route12_EventScript_ItemIron,
        "Route12_EventScript_Gia" to Route12_EventScript_Gia,
        "Route12_EventScript_Jes" to Route12_EventScript_Jes,
        "Route12_EventScript_RouteSign" to Route12_EventScript_RouteSign,
        "Route12_EventScript_FishingSign" to Route12_EventScript_FishingSign,
    )
