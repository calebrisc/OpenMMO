package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SixIsland_OutcastIsland
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_SIS_AND_BRO_AVA_GEB = 576

private const val TRAINER_FISHERMAN_TYLOR = 573
private const val TRAINER_SWIMMER_FEMALE_NICOLE = 575
private const val TRAINER_SWIMMER_MALE_MYMO = 574
private const val TRAINER_TEAM_ROCKET_GRUNT_46 = 540

internal object SixIsland_OutcastIsland_EventScript_Rocket : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_46,
        SixIsland_OutcastIsland.RocketIntro,
        SixIsland_OutcastIsland.RocketDefeat))
        return
    ctx.say(SixIsland_OutcastIsland.RocketPostBattle)
  }
}

internal object SixIsland_OutcastIsland_EventScript_Tylor : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_FISHERMAN_TYLOR,
        SixIsland_OutcastIsland.TylorIntro,
        SixIsland_OutcastIsland.TylorDefeat))
        return
    ctx.say(SixIsland_OutcastIsland.TylorPostBattle)
  }
}

internal object SixIsland_OutcastIsland_EventScript_Mymo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_MYMO,
        SixIsland_OutcastIsland.MymoIntro,
        SixIsland_OutcastIsland.MymoDefeat))
        return
    ctx.say(SixIsland_OutcastIsland.MymoPostBattle)
  }
}

internal object SixIsland_OutcastIsland_EventScript_Nicole : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_NICOLE,
        SixIsland_OutcastIsland.NicoleIntro,
        SixIsland_OutcastIsland.NicoleDefeat))
        return
    ctx.say(SixIsland_OutcastIsland.NicolePostBattle)
  }
}

internal object SixIsland_OutcastIsland_EventScript_Ava : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SIS_AND_BRO_AVA_GEB,
        SixIsland_OutcastIsland.AvaIntro,
        SixIsland_OutcastIsland.AvaDefeat))
        return
    ctx.say(SixIsland_OutcastIsland.AvaPostBattle)
  }
}

internal object SixIsland_OutcastIsland_EventScript_Geb : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SIS_AND_BRO_AVA_GEB,
        SixIsland_OutcastIsland.GebIntro,
        SixIsland_OutcastIsland.GebDefeat))
        return
    ctx.say(SixIsland_OutcastIsland.GebPostBattle)
  }
}

internal object SixIsland_OutcastIsland_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

internal val SixIsland_OutcastIslandScripts: Map<String, Script> =
    mapOf(
        "SixIsland_OutcastIsland_EventScript_Rocket" to SixIsland_OutcastIsland_EventScript_Rocket,
        "SixIsland_OutcastIsland_EventScript_Tylor" to SixIsland_OutcastIsland_EventScript_Tylor,
        "SixIsland_OutcastIsland_EventScript_Mymo" to SixIsland_OutcastIsland_EventScript_Mymo,
        "SixIsland_OutcastIsland_EventScript_Nicole" to SixIsland_OutcastIsland_EventScript_Nicole,
        "SixIsland_OutcastIsland_EventScript_Ava" to SixIsland_OutcastIsland_EventScript_Ava,
        "SixIsland_OutcastIsland_EventScript_Geb" to SixIsland_OutcastIsland_EventScript_Geb,
        "SixIsland_OutcastIsland_EventScript_ItemPPUp" to
            SixIsland_OutcastIsland_EventScript_ItemPPUp,
    )
