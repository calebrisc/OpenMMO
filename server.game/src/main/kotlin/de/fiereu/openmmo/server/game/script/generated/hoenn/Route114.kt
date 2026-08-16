package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route114
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TYRA_AND_IVY = 679

private const val TRAINER_ANGELINA = 712
private const val TRAINER_CHARLOTTE = 714
private const val TRAINER_CLAUDE = 338
private const val TRAINER_KAI = 713
private const val TRAINER_LENNY = 628
private const val TRAINER_LUCAS_1 = 629
private const val TRAINER_NANCY = 472
private const val TRAINER_NOLAN = 342
private const val TRAINER_SHANE = 214

internal object Route114_EventScript_Lenny : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LENNY, Route114.LennyIntro, Route114.LennyDefeat)) return
    ctx.say(Route114.LennyPostBattle)
  }
}

internal object Route114_EventScript_Lucas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LUCAS_1, Route114.LucasIntro, Route114.LucasDefeat)) return
    ctx.say(Route114.LucasPostBattle)
  }
}

internal object Route114_EventScript_Shane : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SHANE, Route114.ShaneIntro, Route114.ShaneDefeat)) return
    ctx.say(Route114.ShanePostBattle)
  }
}

internal object Route114_EventScript_Nancy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_NANCY, Route114.NancyIntro, Route114.NancyDefeat)) return
    ctx.say(Route114.NancyPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_STEVE_1, Route114_Text_SteveIntro, Route114_Text_SteveDefeat, Route114_EventScript_RegisterSteve
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route114_EventScript_RematchSteve
 * msgbox Route114_Text_StevePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_Steve : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Steve")
}

internal object Route114_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object Route114_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PROTEIN)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_ROAR, Route114_EventScript_ReceivedRoar
 * msgbox Route114_Text_AllMyMonDoesIsRoarTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_ROAR
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_ROAR
 * msgbox Route114_Text_ExplainRoar, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_RoarGentleman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_RoarGentleman")
}

internal object Route114_EventScript_Poochyena : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route114.Poochyena)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_ROUTE_114_RECEIVED_BERRY, Route114_EventScript_ReceivedBerry
 * msgbox Route114_Text_LoveUsingBerryCrushShareBerry, MSGBOX_DEFAULT
 * random NUM_ROUTE_114_MAN_BERRIES
 * addvar VAR_RESULT, NUM_ROUTE_114_MAN_BERRIES_SKIPPED
 * addvar VAR_RESULT, FIRST_BERRY_INDEX
 * giveitem VAR_RESULT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_DAILY_ROUTE_114_RECEIVED_BERRY
 * msgbox Route114_Text_TryBerryCrushWithFriends, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Man")
}

internal object Route114_EventScript_Nolan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_NOLAN, Route114.NolanIntro, Route114.NolanDefeat)) return
    ctx.say(Route114.NolanPostBattle)
  }
}

internal object Route114_EventScript_Claude : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CLAUDE, Route114.ClaudeIntro, Route114.ClaudeDefeat))
        return
    ctx.say(Route114.ClaudePostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BERNIE_1, Route114_Text_BernieIntro, Route114_Text_BernieDefeat, Route114_EventScript_RegisterBernie
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route114_EventScript_RematchBernie
 * msgbox Route114_Text_BerniePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_Bernie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Bernie")
}

internal object Route114_EventScript_Ivy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TYRA_AND_IVY, Route114.IvyIntro, Route114.IvyDefeat))
        return
    ctx.say(Route114.IvyPostBattle)
  }
}

internal object Route114_EventScript_Tyra : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TYRA_AND_IVY, Route114.TyraIntro, Route114.TyraDefeat))
        return
    ctx.say(Route114.TyraPostBattle)
  }
}

internal object Route114_EventScript_Charlotte : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CHARLOTTE, Route114.CharlotteIntro, Route114.CharlotteDefeat))
        return
    ctx.say(Route114.CharlottePostBattle)
  }
}

internal object Route114_EventScript_Angelina : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ANGELINA, Route114.AngelinaIntro, Route114.AngelinaDefeat))
        return
    ctx.say(Route114.AngelinaPostBattle)
  }
}

internal object Route114_EventScript_ItemEnergyPowder : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ENERGYPOWDER)
}

internal object Route114_EventScript_Kai : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KAI, Route114.KaiIntro, Route114.KaiDefeat)) return
    ctx.say(Route114.KaiPostBattle)
  }
}

internal object Route114_EventScript_MeteorFallsSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route114.MeteorFallsSign)
}

internal object Route114_EventScript_FossilManiacsHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route114.FossilManiacsHouseSign)
}

internal object Route114_EventScript_LanettesHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route114.LanettesHouse)
}

internal val Route114Scripts: Map<String, Script> =
    mapOf(
        "Route114_EventScript_Lenny" to Route114_EventScript_Lenny,
        "Route114_EventScript_Lucas" to Route114_EventScript_Lucas,
        "Route114_EventScript_Shane" to Route114_EventScript_Shane,
        "Route114_EventScript_Nancy" to Route114_EventScript_Nancy,
        "Route114_EventScript_Steve" to Route114_EventScript_Steve,
        "Route114_EventScript_ItemRareCandy" to Route114_EventScript_ItemRareCandy,
        "Route114_EventScript_ItemProtein" to Route114_EventScript_ItemProtein,
        "Route114_EventScript_RoarGentleman" to Route114_EventScript_RoarGentleman,
        "Route114_EventScript_Poochyena" to Route114_EventScript_Poochyena,
        "Route114_EventScript_Man" to Route114_EventScript_Man,
        "Route114_EventScript_Nolan" to Route114_EventScript_Nolan,
        "Route114_EventScript_Claude" to Route114_EventScript_Claude,
        "Route114_EventScript_Bernie" to Route114_EventScript_Bernie,
        "Route114_EventScript_Ivy" to Route114_EventScript_Ivy,
        "Route114_EventScript_Tyra" to Route114_EventScript_Tyra,
        "Route114_EventScript_Charlotte" to Route114_EventScript_Charlotte,
        "Route114_EventScript_Angelina" to Route114_EventScript_Angelina,
        "Route114_EventScript_ItemEnergyPowder" to Route114_EventScript_ItemEnergyPowder,
        "Route114_EventScript_Kai" to Route114_EventScript_Kai,
        "Route114_EventScript_MeteorFallsSign" to Route114_EventScript_MeteorFallsSign,
        "Route114_EventScript_FossilManiacsHouseSign" to
            Route114_EventScript_FossilManiacsHouseSign,
        "Route114_EventScript_LanettesHouseSign" to Route114_EventScript_LanettesHouseSign,
    )
