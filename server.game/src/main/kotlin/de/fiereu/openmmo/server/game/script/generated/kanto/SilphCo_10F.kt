package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_10F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val TRAINER_SCIENTIST_TRAVIS = 345
private const val TRAINER_TEAM_ROCKET_GRUNT_39 = 389

internal object SilphCo_10F_EventScript_Travis : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SCIENTIST_TRAVIS, SilphCo_10F.TravisIntro, SilphCo_10F.TravisDefeat))
        return
    ctx.say(SilphCo_10F.TravisPostBattle)
  }
}

internal object SilphCo_10F_EventScript_WorkerF : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_SILPH_CO_11F) >= 1) {
      ctx.say(SilphCo_10F.KeepMeCryingASecret)
      return
    }
    ctx.say(SilphCo_10F.WaaaImScared)
  }
}

internal object SilphCo_10F_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_39, SilphCo_10F.GruntIntro, SilphCo_10F.GruntDefeat))
        return
    ctx.say(SilphCo_10F.GruntPostBattle)
  }
}

internal object SilphCo_10F_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.CARBOS)
}

internal object SilphCo_10F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

internal object SilphCo_10F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 19
 * setvar VAR_0x8004, FLAG_SILPH_10F_DOOR
 * goto_if_set FLAG_SILPH_10F_DOOR, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_Door")
}

internal object SilphCo_10F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_10F.FloorSign)
}

internal val SilphCo_10FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_10F_EventScript_Travis" to SilphCo_10F_EventScript_Travis,
        "SilphCo_10F_EventScript_WorkerF" to SilphCo_10F_EventScript_WorkerF,
        "SilphCo_10F_EventScript_Grunt" to SilphCo_10F_EventScript_Grunt,
        "SilphCo_10F_EventScript_ItemCarbos" to SilphCo_10F_EventScript_ItemCarbos,
        "SilphCo_10F_EventScript_ItemUltraBall" to SilphCo_10F_EventScript_ItemUltraBall,
        "SilphCo_10F_EventScript_ItemRareCandy" to SilphCo_10F_EventScript_ItemRareCandy,
        "SilphCo_10F_EventScript_Door" to SilphCo_10F_EventScript_Door,
        "SilphCo_10F_EventScript_FloorSign" to SilphCo_10F_EventScript_FloorSign,
    )
