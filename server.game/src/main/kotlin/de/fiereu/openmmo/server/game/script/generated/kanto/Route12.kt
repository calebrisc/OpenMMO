package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.Route12
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_YOUNG_COUPLE_GIA_JES = 486

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

private const val SNORLAX_DEX = 143

internal object Route12_EventScript_Snorlax : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFlagSet(KantoFlags.FLAG_GOT_POKE_FLUTE)) {
      return ctx.say(Route12.MonSprawledOutInSlumber)
    }
    if (!ctx.askYesNo(Misc.Text_WantToUsePokeFlute)) return
    val result = ctx.wildBattle(SNORLAX_DEX, 30)
    if (result != BattleResult.VICTORY && result != BattleResult.CAUGHT) return
    ctx.setFlag(KantoFlags.FLAG_HIDE_ROUTE_12_SNORLAX)
    ctx.setFlag(KantoFlags.FLAG_WOKE_UP_ROUTE_12_SNORLAX)
    ctx.despawnInteracted()
    ctx.sign(Misc.Text_SnorlaxReturnedToMountains)
  }
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

internal object Route12_EventScript_Gia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNG_COUPLE_GIA_JES, Route12.GiaIntro, Route12.GiaDefeat))
        return
    ctx.say(Route12.GiaPostBattle)
  }
}

internal object Route12_EventScript_Jes : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNG_COUPLE_GIA_JES, Route12.JesIntro, Route12.JesDefeat))
        return
    ctx.say(Route12.JesPostBattle)
  }
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
