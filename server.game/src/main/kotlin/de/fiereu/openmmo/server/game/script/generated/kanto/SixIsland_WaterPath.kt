package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SixIsland_WaterPath
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_TWINS_MIU_MIA = 580

private const val TRAINER_AROMA_LADY_ROSE = 577
private const val TRAINER_HIKER_EARL = 581
private const val TRAINER_JUGGLER_EDWARD = 291
private const val TRAINER_SWIMMER_FEMALE_DENISE = 579
private const val TRAINER_SWIMMER_MALE_SAMIR = 578

internal object SixIsland_WaterPath_EventScript_Rose : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_AROMA_LADY_ROSE, SixIsland_WaterPath.RoseIntro, SixIsland_WaterPath.RoseDefeat))
        return
    ctx.say(SixIsland_WaterPath.RosePostBattle)
  }
}

internal object SixIsland_WaterPath_EventScript_Edward : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_EDWARD, SixIsland_WaterPath.EdwardIntro, SixIsland_WaterPath.EdwardDefeat))
        return
    ctx.say(SixIsland_WaterPath.EdwardPostBattle)
  }
}

internal object SixIsland_WaterPath_EventScript_Samir : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_SAMIR,
        SixIsland_WaterPath.SamirIntro,
        SixIsland_WaterPath.SamirDefeat))
        return
    ctx.say(SixIsland_WaterPath.SamirPostBattle)
  }
}

internal object SixIsland_WaterPath_EventScript_Denise : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_DENISE,
        SixIsland_WaterPath.DeniseIntro,
        SixIsland_WaterPath.DeniseDefeat))
        return
    ctx.say(SixIsland_WaterPath.DenisePostBattle)
  }
}

internal object SixIsland_WaterPath_EventScript_Miu : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TWINS_MIU_MIA, SixIsland_WaterPath.MiuIntro, SixIsland_WaterPath.MiuDefeat))
        return
    ctx.say(SixIsland_WaterPath.MiuPostBattle)
  }
}

internal object SixIsland_WaterPath_EventScript_Mia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TWINS_MIU_MIA, SixIsland_WaterPath.MiaIntro, SixIsland_WaterPath.MiaDefeat))
        return
    ctx.say(SixIsland_WaterPath.MiaPostBattle)
  }
}

internal object SixIsland_WaterPath_EventScript_Earl : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_EARL, SixIsland_WaterPath.EarlIntro, SixIsland_WaterPath.EarlDefeat))
        return
    ctx.say(SixIsland_WaterPath.EarlPostBattle)
  }
}

internal object SixIsland_WaterPath_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ELIXIR)
}

internal object SixIsland_WaterPath_EventScript_ItemDragonScale : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.DRAGON_SCALE)
}

internal object SixIsland_WaterPath_EventScript_HornWantedSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SixIsland_WaterPath.WantedUltimateHorn)
}

internal object SixIsland_WaterPath_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SixIsland_WaterPath.RouteSign)
}

internal val SixIsland_WaterPathScripts: Map<String, Script> =
    mapOf(
        "SixIsland_WaterPath_EventScript_Rose" to SixIsland_WaterPath_EventScript_Rose,
        "SixIsland_WaterPath_EventScript_Edward" to SixIsland_WaterPath_EventScript_Edward,
        "SixIsland_WaterPath_EventScript_Samir" to SixIsland_WaterPath_EventScript_Samir,
        "SixIsland_WaterPath_EventScript_Denise" to SixIsland_WaterPath_EventScript_Denise,
        "SixIsland_WaterPath_EventScript_Miu" to SixIsland_WaterPath_EventScript_Miu,
        "SixIsland_WaterPath_EventScript_Mia" to SixIsland_WaterPath_EventScript_Mia,
        "SixIsland_WaterPath_EventScript_Earl" to SixIsland_WaterPath_EventScript_Earl,
        "SixIsland_WaterPath_EventScript_ItemElixir" to SixIsland_WaterPath_EventScript_ItemElixir,
        "SixIsland_WaterPath_EventScript_ItemDragonScale" to
            SixIsland_WaterPath_EventScript_ItemDragonScale,
        "SixIsland_WaterPath_EventScript_HornWantedSign" to
            SixIsland_WaterPath_EventScript_HornWantedSign,
        "SixIsland_WaterPath_EventScript_RouteSign" to SixIsland_WaterPath_EventScript_RouteSign,
    )
