package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route133
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BECK = 414
private const val TRAINER_CONOR = 511
private const val TRAINER_DEBRA = 460
private const val TRAINER_FRANKLIN = 170
private const val TRAINER_LINDA = 461
private const val TRAINER_MOLLIE = 137
private const val TRAINER_WARREN = 88

internal object Route133_EventScript_Franklin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_FRANKLIN, Route133.FranklinIntro, Route133.FranklinDefeat))
        return
    ctx.say(Route133.FranklinPostBattle)
  }
}

internal object Route133_EventScript_Linda : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LINDA, Route133.LindaIntro, Route133.LindaDefeat)) return
    ctx.say(Route133.LindaPostBattle)
  }
}

internal object Route133_EventScript_Debra : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DEBRA, Route133.DebraIntro, Route133.DebraDefeat)) return
    ctx.say(Route133.DebraPostBattle)
  }
}

internal object Route133_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.BIG_PEARL)
}

internal object Route133_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.STAR_PIECE)
}

internal object Route133_EventScript_Beck : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BECK, Route133.BeckIntro, Route133.BeckDefeat)) return
    ctx.say(Route133.BeckPostBattle)
  }
}

internal object Route133_EventScript_Warren : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_WARREN, Route133.WarrenIntro, Route133.WarrenDefeat))
        return
    ctx.say(Route133.WarrenPostBattle)
  }
}

internal object Route133_EventScript_Mollie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MOLLIE, Route133.MollieIntro, Route133.MollieDefeat))
        return
    ctx.say(Route133.MolliePostBattle)
  }
}

internal object Route133_EventScript_Conor : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CONOR, Route133.ConorIntro, Route133.ConorDefeat)) return
    ctx.say(Route133.ConorPostBattle)
  }
}

internal object Route133_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_REVIVE)
}

internal val Route133Scripts: Map<String, Script> =
    mapOf(
        "Route133_EventScript_Franklin" to Route133_EventScript_Franklin,
        "Route133_EventScript_Linda" to Route133_EventScript_Linda,
        "Route133_EventScript_Debra" to Route133_EventScript_Debra,
        "Route133_EventScript_ItemBigPearl" to Route133_EventScript_ItemBigPearl,
        "Route133_EventScript_ItemStarPiece" to Route133_EventScript_ItemStarPiece,
        "Route133_EventScript_Beck" to Route133_EventScript_Beck,
        "Route133_EventScript_Warren" to Route133_EventScript_Warren,
        "Route133_EventScript_Mollie" to Route133_EventScript_Mollie,
        "Route133_EventScript_Conor" to Route133_EventScript_Conor,
        "Route133_EventScript_ItemMaxRevive" to Route133_EventScript_ItemMaxRevive,
    )
