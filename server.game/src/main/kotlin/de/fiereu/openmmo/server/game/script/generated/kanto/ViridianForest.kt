package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianForest
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BUG_CATCHER_ANTHONY = 531
private const val TRAINER_BUG_CATCHER_CHARLIE = 532
private const val TRAINER_BUG_CATCHER_DOUG = 103
private const val TRAINER_BUG_CATCHER_RICK = 102
private const val TRAINER_BUG_CATCHER_SAMMY = 104

internal object ViridianForest_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianForest.FriendsItchingToBattle)
}

internal object ViridianForest_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianForest.RanOutOfPokeBalls)
}

internal object ViridianForest_EventScript_Rick : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_RICK, ViridianForest.RickIntro, ViridianForest.RickDefeat))
        return
    ctx.say(ViridianForest.RickPostBattle)
  }
}

internal object ViridianForest_EventScript_Doug : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_DOUG, ViridianForest.DougIntro, ViridianForest.DougDefeat))
        return
    ctx.say(ViridianForest.DougPostBattle)
  }
}

internal object ViridianForest_EventScript_Sammy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_SAMMY, ViridianForest.SammyIntro, ViridianForest.SammyDefeat))
        return
    ctx.say(ViridianForest.SammyPostBattle)
  }
}

internal object ViridianForest_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POKE_BALL)
}

internal object ViridianForest_EventScript_ItemAntidote : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ANTIDOTE)
}

internal object ViridianForest_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POTION)
}

internal object ViridianForest_EventScript_Anthony : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_ANTHONY, ViridianForest.AnthonyIntro, ViridianForest.AnthonyDefeat))
        return
    ctx.say(ViridianForest.AnthonyPostBattle)
  }
}

internal object ViridianForest_EventScript_Charlie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_CHARLIE, ViridianForest.CharlieIntro, ViridianForest.CharlieDefeat))
        return
    ctx.say(ViridianForest.CharliePostBattle)
  }
}

internal object ViridianForest_EventScript_ItemPotion2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POTION)
}

internal object ViridianForest_EventScript_TrainerTips1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.AvoidGrassyAreasWhenWeak)
}

internal object ViridianForest_EventScript_TrainerTips3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(ViridianForest.ContactOakViaPCToRatePokedex)
}

internal object ViridianForest_EventScript_TrainerTips4 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.CantCatchOwnedMons)
}

internal object ViridianForest_EventScript_ExitSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.LeavingViridianForest)
}

internal object ViridianForest_EventScript_TrainerTips2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.UseAntidoteForPoison)
}

internal object ViridianForest_EventScript_TrainerTips5 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.WeakenMonsBeforeCapture)
}

internal val ViridianForestScripts: Map<String, Script> =
    mapOf(
        "ViridianForest_EventScript_Youngster" to ViridianForest_EventScript_Youngster,
        "ViridianForest_EventScript_Boy" to ViridianForest_EventScript_Boy,
        "ViridianForest_EventScript_Rick" to ViridianForest_EventScript_Rick,
        "ViridianForest_EventScript_Doug" to ViridianForest_EventScript_Doug,
        "ViridianForest_EventScript_Sammy" to ViridianForest_EventScript_Sammy,
        "ViridianForest_EventScript_ItemPokeBall" to ViridianForest_EventScript_ItemPokeBall,
        "ViridianForest_EventScript_ItemAntidote" to ViridianForest_EventScript_ItemAntidote,
        "ViridianForest_EventScript_ItemPotion" to ViridianForest_EventScript_ItemPotion,
        "ViridianForest_EventScript_Anthony" to ViridianForest_EventScript_Anthony,
        "ViridianForest_EventScript_Charlie" to ViridianForest_EventScript_Charlie,
        "ViridianForest_EventScript_ItemPotion2" to ViridianForest_EventScript_ItemPotion2,
        "ViridianForest_EventScript_TrainerTips1" to ViridianForest_EventScript_TrainerTips1,
        "ViridianForest_EventScript_TrainerTips3" to ViridianForest_EventScript_TrainerTips3,
        "ViridianForest_EventScript_TrainerTips4" to ViridianForest_EventScript_TrainerTips4,
        "ViridianForest_EventScript_ExitSign" to ViridianForest_EventScript_ExitSign,
        "ViridianForest_EventScript_TrainerTips2" to ViridianForest_EventScript_TrainerTips2,
        "ViridianForest_EventScript_TrainerTips5" to ViridianForest_EventScript_TrainerTips5,
    )
