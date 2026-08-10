package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route9
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BUG_CATCHER_BRENT = 114
private const val TRAINER_BUG_CATCHER_CONNER = 115
private const val TRAINER_CAMPER_CHRIS = 148
private const val TRAINER_CAMPER_DREW = 149
private const val TRAINER_HIKER_ALAN = 185
private const val TRAINER_HIKER_BRICE = 186
private const val TRAINER_HIKER_JEREMY = 465
private const val TRAINER_PICNICKER_ALICIA = 154
private const val TRAINER_PICNICKER_CAITLIN = 155

internal object Route9_EventScript_Alicia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_ALICIA, Route9.AliciaIntro, Route9.AliciaDefeat))
        return
    ctx.say(Route9.AliciaPostBattle)
  }
}

internal object Route9_EventScript_Jeremy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIKER_JEREMY, Route9.JeremyIntro, Route9.JeremyDefeat))
        return
    ctx.say(Route9.JeremyPostBattle)
  }
}

internal object Route9_EventScript_Alan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIKER_ALAN, Route9.AlanIntro, Route9.AlanDefeat)) return
    ctx.say(Route9.AlanPostBattle)
  }
}

internal object Route9_EventScript_Chris : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_CHRIS, Route9.ChrisIntro, Route9.ChrisDefeat))
        return
    ctx.say(Route9.ChrisPostBattle)
  }
}

internal object Route9_EventScript_Brent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_BUG_CATCHER_BRENT, Route9.BrentIntro, Route9.BrentDefeat))
        return
    ctx.say(Route9.BrentPostBattle)
  }
}

internal object Route9_EventScript_Conner : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_CONNER, Route9.ConnerIntro, Route9.ConnerDefeat))
        return
    ctx.say(Route9.ConnerPostBattle)
  }
}

internal object Route9_EventScript_Brice : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_HIKER_BRICE, Route9.BriceIntro, Route9.BriceDefeat)) return
    ctx.say(Route9.BricePostBattle)
  }
}

internal object Route9_EventScript_Caitlin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_CAITLIN, Route9.CaitlinIntro, Route9.CaitlinDefeat))
        return
    ctx.say(Route9.CaitlinPostBattle)
  }
}

internal object Route9_EventScript_Drew : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_CAMPER_DREW, Route9.DrewIntro, Route9.DrewDefeat)) return
    ctx.say(Route9.DrewPostBattle)
  }
}

internal object Route9_EventScript_ItemTM40 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM40)
}

internal object Route9_EventScript_ItemBurnHeal : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.BURN_HEAL)
}

internal object Route9_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route9.RouteSign)
}

internal val Route9Scripts: Map<String, Script> =
    mapOf(
        "Route9_EventScript_Alicia" to Route9_EventScript_Alicia,
        "Route9_EventScript_Jeremy" to Route9_EventScript_Jeremy,
        "Route9_EventScript_Alan" to Route9_EventScript_Alan,
        "Route9_EventScript_Chris" to Route9_EventScript_Chris,
        "Route9_EventScript_Brent" to Route9_EventScript_Brent,
        "Route9_EventScript_Conner" to Route9_EventScript_Conner,
        "Route9_EventScript_Brice" to Route9_EventScript_Brice,
        "Route9_EventScript_Caitlin" to Route9_EventScript_Caitlin,
        "Route9_EventScript_Drew" to Route9_EventScript_Drew,
        "Route9_EventScript_ItemTM40" to Route9_EventScript_ItemTM40,
        "Route9_EventScript_ItemBurnHeal" to Route9_EventScript_ItemBurnHeal,
        "Route9_EventScript_RouteSign" to Route9_EventScript_RouteSign,
    )
