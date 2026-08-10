package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity_Gym_B1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ANDREA = 613
private const val TRAINER_ANNIKA = 502
private const val TRAINER_BETHANY = 301
private const val TRAINER_BRIANNA = 118
private const val TRAINER_BRIDGET = 129
private const val TRAINER_CONNIE = 128
private const val TRAINER_CRISSY = 614
private const val TRAINER_DAPHNE = 115
private const val TRAINER_OLIVIA = 130
private const val TRAINER_TIFFANY = 131

internal object SootopolisCity_Gym_B1F_EventScript_Andrea : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ANDREA, SootopolisCity_Gym_B1F.AndreaIntro, SootopolisCity_Gym_B1F.AndreaDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.AndreaPostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Connie : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CONNIE, SootopolisCity_Gym_B1F.ConnieIntro, SootopolisCity_Gym_B1F.ConnieDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.ConniePostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Brianna : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BRIANNA, SootopolisCity_Gym_B1F.BriannaIntro, SootopolisCity_Gym_B1F.BriannaDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.BriannaPostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Bridget : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BRIDGET, SootopolisCity_Gym_B1F.BridgetIntro, SootopolisCity_Gym_B1F.BridgetDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.BridgetPostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Tiffany : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TIFFANY, SootopolisCity_Gym_B1F.TiffanyIntro, SootopolisCity_Gym_B1F.TiffanyDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.TiffanyPostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Bethany : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BETHANY, SootopolisCity_Gym_B1F.BethanyIntro, SootopolisCity_Gym_B1F.BethanyDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.BethanyPostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Crissy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CRISSY, SootopolisCity_Gym_B1F.CrissyIntro, SootopolisCity_Gym_B1F.CrissyDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.CrissyPostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Olivia : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_OLIVIA, SootopolisCity_Gym_B1F.OliviaIntro, SootopolisCity_Gym_B1F.OliviaDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.OliviaPostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Daphne : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_DAPHNE, SootopolisCity_Gym_B1F.DaphneIntro, SootopolisCity_Gym_B1F.DaphneDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.DaphnePostBattle)
  }
}

internal object SootopolisCity_Gym_B1F_EventScript_Annika : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ANNIKA, SootopolisCity_Gym_B1F.AnnikaIntro, SootopolisCity_Gym_B1F.AnnikaDefeat))
        return
    ctx.say(SootopolisCity_Gym_B1F.AnnikaPostBattle)
  }
}

internal val SootopolisCity_Gym_B1FScripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_Gym_B1F_EventScript_Andrea" to SootopolisCity_Gym_B1F_EventScript_Andrea,
        "SootopolisCity_Gym_B1F_EventScript_Connie" to SootopolisCity_Gym_B1F_EventScript_Connie,
        "SootopolisCity_Gym_B1F_EventScript_Brianna" to SootopolisCity_Gym_B1F_EventScript_Brianna,
        "SootopolisCity_Gym_B1F_EventScript_Bridget" to SootopolisCity_Gym_B1F_EventScript_Bridget,
        "SootopolisCity_Gym_B1F_EventScript_Tiffany" to SootopolisCity_Gym_B1F_EventScript_Tiffany,
        "SootopolisCity_Gym_B1F_EventScript_Bethany" to SootopolisCity_Gym_B1F_EventScript_Bethany,
        "SootopolisCity_Gym_B1F_EventScript_Crissy" to SootopolisCity_Gym_B1F_EventScript_Crissy,
        "SootopolisCity_Gym_B1F_EventScript_Olivia" to SootopolisCity_Gym_B1F_EventScript_Olivia,
        "SootopolisCity_Gym_B1F_EventScript_Daphne" to SootopolisCity_Gym_B1F_EventScript_Daphne,
        "SootopolisCity_Gym_B1F_EventScript_Annika" to SootopolisCity_Gym_B1F_EventScript_Annika,
    )
