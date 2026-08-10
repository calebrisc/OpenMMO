package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route132
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_DANA = 458
private const val TRAINER_DARCY = 733
private const val TRAINER_GILBERT = 169
private const val TRAINER_JONATHAN = 598
private const val TRAINER_KIYO = 181
private const val TRAINER_MAKAYLA = 758
private const val TRAINER_PAXTON = 594
private const val TRAINER_RONALD = 350

internal object Route132_EventScript_Gilbert : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_GILBERT, Route132.GilbertIntro, Route132.GilbertDefeat))
        return
    ctx.say(Route132.GilbertPostBattle)
  }
}

internal object Route132_EventScript_Dana : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DANA, Route132.DanaIntro, Route132.DanaDefeat)) return
    ctx.say(Route132.DanaPostBattle)
  }
}

internal object Route132_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object Route132_EventScript_Kiyo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_KIYO, Route132.KiyoIntro, Route132.KiyoDefeat)) return
    ctx.say(Route132.KiyoPostBattle)
  }
}

internal object Route132_EventScript_Ronald : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RONALD, Route132.RonaldIntro, Route132.RonaldDefeat))
        return
    ctx.say(Route132.RonaldPostBattle)
  }
}

internal object Route132_EventScript_Paxton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PAXTON, Route132.PaxtonIntro, Route132.PaxtonDefeat))
        return
    ctx.say(Route132.PaxtonPostBattle)
  }
}

internal object Route132_EventScript_Darcy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DARCY, Route132.DarcyIntro, Route132.DarcyDefeat)) return
    ctx.say(Route132.DarcyPostBattle)
  }
}

internal object Route132_EventScript_Makayla : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MAKAYLA, Route132.MakaylaIntro, Route132.MakaylaDefeat))
        return
    ctx.say(Route132.MakaylaPostBattle)
  }
}

internal object Route132_EventScript_Jonathan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_JONATHAN, Route132.JonathanIntro, Route132.JonathanDefeat))
        return
    ctx.say(Route132.JonathanPostBattle)
  }
}

internal object Route132_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PROTEIN)
}

internal val Route132Scripts: Map<String, Script> =
    mapOf(
        "Route132_EventScript_Gilbert" to Route132_EventScript_Gilbert,
        "Route132_EventScript_Dana" to Route132_EventScript_Dana,
        "Route132_EventScript_ItemRareCandy" to Route132_EventScript_ItemRareCandy,
        "Route132_EventScript_Kiyo" to Route132_EventScript_Kiyo,
        "Route132_EventScript_Ronald" to Route132_EventScript_Ronald,
        "Route132_EventScript_Paxton" to Route132_EventScript_Paxton,
        "Route132_EventScript_Darcy" to Route132_EventScript_Darcy,
        "Route132_EventScript_Makayla" to Route132_EventScript_Makayla,
        "Route132_EventScript_Jonathan" to Route132_EventScript_Jonathan,
        "Route132_EventScript_ItemProtein" to Route132_EventScript_ItemProtein,
    )
