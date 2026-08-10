package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route20
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BIRD_KEEPER_ROGER = 310
private const val TRAINER_PICNICKER_IRENE = 473
private const val TRAINER_PICNICKER_MISSY = 472
private const val TRAINER_SWIMMER_FEMALE_MELISSA = 272
private const val TRAINER_SWIMMER_FEMALE_NORA = 271
private const val TRAINER_SWIMMER_FEMALE_SHIRLEY = 279
private const val TRAINER_SWIMMER_FEMALE_TIFFANY = 270
private const val TRAINER_SWIMMER_MALE_BARRY = 242
private const val TRAINER_SWIMMER_MALE_DARRIN = 244
private const val TRAINER_SWIMMER_MALE_DEAN = 243

internal object Route20_EventScript_Melissa : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_MELISSA, Route20.MelissaIntro, Route20.MelissaDefeat))
        return
    ctx.say(Route20.MelissaPostBattle)
  }
}

internal object Route20_EventScript_Missy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_MISSY, Route20.MissyIntro, Route20.MissyDefeat))
        return
    ctx.say(Route20.MissyPostBattle)
  }
}

internal object Route20_EventScript_Nora : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_NORA, Route20.NoraIntro, Route20.NoraDefeat))
        return
    ctx.say(Route20.NoraPostBattle)
  }
}

internal object Route20_EventScript_Roger : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_ROGER, Route20.RogerIntro, Route20.RogerDefeat))
        return
    ctx.say(Route20.RogerPostBattle)
  }
}

internal object Route20_EventScript_Dean : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_SWIMMER_MALE_DEAN, Route20.DeanIntro, Route20.DeanDefeat))
        return
    ctx.say(Route20.DeanPostBattle)
  }
}

internal object Route20_EventScript_Irene : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PICNICKER_IRENE, Route20.IreneIntro, Route20.IreneDefeat))
        return
    ctx.say(Route20.IrenePostBattle)
  }
}

internal object Route20_EventScript_Barry : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_BARRY, Route20.BarryIntro, Route20.BarryDefeat))
        return
    ctx.say(Route20.BarryPostBattle)
  }
}

internal object Route20_EventScript_Darrin : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_MALE_DARRIN, Route20.DarrinIntro, Route20.DarrinDefeat))
        return
    ctx.say(Route20.DarrinPostBattle)
  }
}

internal object Route20_EventScript_Shirley : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_SHIRLEY, Route20.ShirleyIntro, Route20.ShirleyDefeat))
        return
    ctx.say(Route20.ShirleyPostBattle)
  }
}

internal object Route20_EventScript_Tiffany : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SWIMMER_FEMALE_TIFFANY, Route20.TiffanyIntro, Route20.TiffanyDefeat))
        return
    ctx.say(Route20.TiffanyPostBattle)
  }
}

internal object Route20_EventScript_Camper : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route20.MistyTrainsHere)
}

internal object Route20_EventScript_SeafoamIslandsSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route20.SeafoamIslands)
}

internal val Route20Scripts: Map<String, Script> =
    mapOf(
        "Route20_EventScript_Melissa" to Route20_EventScript_Melissa,
        "Route20_EventScript_Missy" to Route20_EventScript_Missy,
        "Route20_EventScript_Nora" to Route20_EventScript_Nora,
        "Route20_EventScript_Roger" to Route20_EventScript_Roger,
        "Route20_EventScript_Dean" to Route20_EventScript_Dean,
        "Route20_EventScript_Irene" to Route20_EventScript_Irene,
        "Route20_EventScript_Barry" to Route20_EventScript_Barry,
        "Route20_EventScript_Darrin" to Route20_EventScript_Darrin,
        "Route20_EventScript_Shirley" to Route20_EventScript_Shirley,
        "Route20_EventScript_Tiffany" to Route20_EventScript_Tiffany,
        "Route20_EventScript_Camper" to Route20_EventScript_Camper,
        "Route20_EventScript_SeafoamIslandsSign" to Route20_EventScript_SeafoamIslandsSign,
    )
