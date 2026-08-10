package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VictoryRoad_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALBERT = 80
private const val TRAINER_EDGAR = 79
private const val TRAINER_HOPE = 96
private const val TRAINER_KATELYNN = 325
private const val TRAINER_QUINCY = 324
private const val TRAINER_WALLY_VR_2 = 657

internal object VictoryRoad_1F_EventScript_Edgar : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_EDGAR, VictoryRoad_1F.EdgarIntro, VictoryRoad_1F.EdgarDefeat))
        return
    ctx.say(VictoryRoad_1F.EdgarPostBattle)
  }
}

internal object VictoryRoad_1F_EventScript_Hope : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HOPE, VictoryRoad_1F.HopeIntro, VictoryRoad_1F.HopeDefeat))
        return
    ctx.say(VictoryRoad_1F.HopePostBattle)
  }
}

internal object VictoryRoad_1F_EventScript_Albert : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ALBERT, VictoryRoad_1F.AlbertIntro, VictoryRoad_1F.AlbertDefeat))
        return
    ctx.say(VictoryRoad_1F.AlbertPostBattle)
  }
}

internal object VictoryRoad_1F_EventScript_EntranceWally : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(VictoryRoad_1F.WallyPostEntranceBattle)
}

internal object VictoryRoad_1F_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ELIXIR)
}

internal object VictoryRoad_1F_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

internal object VictoryRoad_1F_EventScript_ExitWally : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_WALLY_VR_2, VictoryRoad_1F.WallyIntro, VictoryRoad_1F.WallyDefeat))
        return
    ctx.say(VictoryRoad_1F.WallyPostBattle)
  }
}

internal object VictoryRoad_1F_EventScript_Katelynn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_KATELYNN, VictoryRoad_1F.KatelynnIntro, VictoryRoad_1F.KatelynnDefeat))
        return
    ctx.say(VictoryRoad_1F.KatelynnPostBattle)
  }
}

internal object VictoryRoad_1F_EventScript_Quincy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_QUINCY, VictoryRoad_1F.QuincyIntro, VictoryRoad_1F.QuincyDefeat))
        return
    ctx.say(VictoryRoad_1F.QuincyPostBattle)
  }
}

internal val VictoryRoad_1FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_1F_EventScript_Edgar" to VictoryRoad_1F_EventScript_Edgar,
        "VictoryRoad_1F_EventScript_Hope" to VictoryRoad_1F_EventScript_Hope,
        "VictoryRoad_1F_EventScript_Albert" to VictoryRoad_1F_EventScript_Albert,
        "VictoryRoad_1F_EventScript_EntranceWally" to VictoryRoad_1F_EventScript_EntranceWally,
        "VictoryRoad_1F_EventScript_ItemMaxElixir" to VictoryRoad_1F_EventScript_ItemMaxElixir,
        "VictoryRoad_1F_EventScript_ItemPPUp" to VictoryRoad_1F_EventScript_ItemPPUp,
        "VictoryRoad_1F_EventScript_ExitWally" to VictoryRoad_1F_EventScript_ExitWally,
        "VictoryRoad_1F_EventScript_Katelynn" to VictoryRoad_1F_EventScript_Katelynn,
        "VictoryRoad_1F_EventScript_Quincy" to VictoryRoad_1F_EventScript_Quincy,
    )
