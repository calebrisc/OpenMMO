package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_BondBridge
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TWINS_JOY_MEG = 560

private const val TRAINER_AROMA_LADY_NIKKI = 523
private const val TRAINER_AROMA_LADY_VIOLET = 558
private const val TRAINER_SWIMMER_FEMALE_TISHA = 561
private const val TRAINER_TUBER_ALEXIS = 559
private const val TRAINER_TUBER_AMIRA = 519

internal object ThreeIsland_BondBridge_EventScript_Nikki : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_AROMA_LADY_NIKKI,
        ThreeIsland_BondBridge.NikkiIntro,
        ThreeIsland_BondBridge.NikkiDefeat))
        return
    ctx.say(ThreeIsland_BondBridge.NikkiPostBattle)
  }
}

internal object ThreeIsland_BondBridge_EventScript_Violet : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_AROMA_LADY_VIOLET,
        ThreeIsland_BondBridge.VioletIntro,
        ThreeIsland_BondBridge.VioletDefeat))
        return
    ctx.say(ThreeIsland_BondBridge.VioletPostBattle)
  }
}

internal object ThreeIsland_BondBridge_EventScript_Amira : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TUBER_AMIRA, ThreeIsland_BondBridge.AmiraIntro, ThreeIsland_BondBridge.AmiraDefeat))
        return
    ctx.say(ThreeIsland_BondBridge.AmiraPostBattle)
  }
}

internal object ThreeIsland_BondBridge_EventScript_Alexis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TUBER_ALEXIS,
        ThreeIsland_BondBridge.AlexisIntro,
        ThreeIsland_BondBridge.AlexisDefeat))
        return
    ctx.say(ThreeIsland_BondBridge.AlexisPostBattle)
  }
}

internal object ThreeIsland_BondBridge_EventScript_Tisha : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_TISHA,
        ThreeIsland_BondBridge.TishaIntro,
        ThreeIsland_BondBridge.TishaDefeat))
        return
    ctx.say(ThreeIsland_BondBridge.TishaPostBattle)
  }
}

internal object ThreeIsland_BondBridge_EventScript_Joy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TWINS_JOY_MEG, ThreeIsland_BondBridge.JoyIntro, ThreeIsland_BondBridge.JoyDefeat))
        return
    ctx.say(ThreeIsland_BondBridge.JoyPostBattle)
  }
}

internal object ThreeIsland_BondBridge_EventScript_Meg : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TWINS_JOY_MEG, ThreeIsland_BondBridge.MegIntro, ThreeIsland_BondBridge.MegDefeat))
        return
    ctx.say(ThreeIsland_BondBridge.MegPostBattle)
  }
}

internal object ThreeIsland_BondBridge_EventScript_BerryForestSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ThreeIsland_BondBridge.BerryForestAhead)
}

internal object ThreeIsland_BondBridge_EventScript_BondBridgeSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ThreeIsland_BondBridge.BondBridgeSign)
}

internal val ThreeIsland_BondBridgeScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_BondBridge_EventScript_Nikki" to ThreeIsland_BondBridge_EventScript_Nikki,
        "ThreeIsland_BondBridge_EventScript_Violet" to ThreeIsland_BondBridge_EventScript_Violet,
        "ThreeIsland_BondBridge_EventScript_Amira" to ThreeIsland_BondBridge_EventScript_Amira,
        "ThreeIsland_BondBridge_EventScript_Alexis" to ThreeIsland_BondBridge_EventScript_Alexis,
        "ThreeIsland_BondBridge_EventScript_Tisha" to ThreeIsland_BondBridge_EventScript_Tisha,
        "ThreeIsland_BondBridge_EventScript_Joy" to ThreeIsland_BondBridge_EventScript_Joy,
        "ThreeIsland_BondBridge_EventScript_Meg" to ThreeIsland_BondBridge_EventScript_Meg,
        "ThreeIsland_BondBridge_EventScript_BerryForestSign" to
            ThreeIsland_BondBridge_EventScript_BerryForestSign,
        "ThreeIsland_BondBridge_EventScript_BondBridgeSign" to
            ThreeIsland_BondBridge_EventScript_BondBridgeSign,
    )
