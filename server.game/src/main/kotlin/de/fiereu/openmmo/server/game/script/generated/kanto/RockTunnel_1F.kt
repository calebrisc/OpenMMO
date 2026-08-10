package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.RockTunnel_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_HIKER_LENNY = 192
private const val TRAINER_HIKER_LUCAS = 194
private const val TRAINER_HIKER_OLIVER = 193
private const val TRAINER_PICNICKER_ARIANA = 475
private const val TRAINER_PICNICKER_DANA = 474
private const val TRAINER_PICNICKER_LEAH = 476
private const val TRAINER_POKEMANIAC_ASHTON = 168

internal object RockTunnel_1F_EventScript_Dana : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_DANA, RockTunnel_1F.DanaIntro, RockTunnel_1F.DanaDefeat))
        return
    ctx.say(RockTunnel_1F.DanaPostBattle)
  }
}

internal object RockTunnel_1F_EventScript_Ariana : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_ARIANA, RockTunnel_1F.ArianaIntro, RockTunnel_1F.ArianaDefeat))
        return
    ctx.say(RockTunnel_1F.ArianaPostBattle)
  }
}

internal object RockTunnel_1F_EventScript_Leah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PICNICKER_LEAH, RockTunnel_1F.LeahIntro, RockTunnel_1F.LeahDefeat))
        return
    ctx.say(RockTunnel_1F.LeahPostBattle)
  }
}

internal object RockTunnel_1F_EventScript_Lucas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_LUCAS, RockTunnel_1F.LucasIntro, RockTunnel_1F.LucasDefeat))
        return
    ctx.say(RockTunnel_1F.LucasPostBattle)
  }
}

internal object RockTunnel_1F_EventScript_Oliver : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_OLIVER, RockTunnel_1F.OliverIntro, RockTunnel_1F.OliverDefeat))
        return
    ctx.say(RockTunnel_1F.OliverPostBattle)
  }
}

internal object RockTunnel_1F_EventScript_Lenny : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_LENNY, RockTunnel_1F.LennyIntro, RockTunnel_1F.LennyDefeat))
        return
    ctx.say(RockTunnel_1F.LennyPostBattle)
  }
}

internal object RockTunnel_1F_EventScript_Ashton : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_POKEMANIAC_ASHTON, RockTunnel_1F.AshtonIntro, RockTunnel_1F.AshtonDefeat))
        return
    ctx.say(RockTunnel_1F.AshtonPostBattle)
  }
}

internal object RockTunnel_1F_EventScript_ItemRepel : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.REPEL)
}

internal object RockTunnel_1F_EventScript_ItemPearl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PEARL)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object RockTunnel_1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RockTunnel_1F_EventScript_ItemEscapeRope")
}

internal object RockTunnel_1F_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RockTunnel_1F.RouteSign)
}

internal val RockTunnel_1FScripts: Map<String, Script> =
    mapOf(
        "RockTunnel_1F_EventScript_Dana" to RockTunnel_1F_EventScript_Dana,
        "RockTunnel_1F_EventScript_Ariana" to RockTunnel_1F_EventScript_Ariana,
        "RockTunnel_1F_EventScript_Leah" to RockTunnel_1F_EventScript_Leah,
        "RockTunnel_1F_EventScript_Lucas" to RockTunnel_1F_EventScript_Lucas,
        "RockTunnel_1F_EventScript_Oliver" to RockTunnel_1F_EventScript_Oliver,
        "RockTunnel_1F_EventScript_Lenny" to RockTunnel_1F_EventScript_Lenny,
        "RockTunnel_1F_EventScript_Ashton" to RockTunnel_1F_EventScript_Ashton,
        "RockTunnel_1F_EventScript_ItemRepel" to RockTunnel_1F_EventScript_ItemRepel,
        "RockTunnel_1F_EventScript_ItemPearl" to RockTunnel_1F_EventScript_ItemPearl,
        "RockTunnel_1F_EventScript_ItemEscapeRope" to RockTunnel_1F_EventScript_ItemEscapeRope,
        "RockTunnel_1F_EventScript_RouteSign" to RockTunnel_1F_EventScript_RouteSign,
    )
