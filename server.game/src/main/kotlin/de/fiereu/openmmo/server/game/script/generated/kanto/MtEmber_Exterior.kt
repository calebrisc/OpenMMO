package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.dialog.generated.kanto.MtEmber_Exterior
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.script.TutorLines
import de.fiereu.openmmo.server.game.script.TutorMove
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

private const val TRAINER_CRUSH_GIRL_JOCELYN = 592
private const val TRAINER_PKMN_RANGER_BETH = 597
private const val TRAINER_PKMN_RANGER_LOGAN = 595

internal object MtEmber_Exterior_EventScript_ExplosionTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.moveTutor(
          TutorMove(
              move = 153, flag = KantoFlags.FLAG_TUTOR_EXPLOSION, from = "the tutor on Mt Ember"),
          TutorLines(
              teach = Misc.Text_ExplosionTeach,
              declined = Misc.Text_ExplosionDeclined,
              whichMon = Misc.Text_ExplosionWhichMon,
              taught = Misc.Text_ExplosionTaught,
          ),
      )
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_defeated TRAINER_TEAM_ROCKET_GRUNT_43, MtEmber_Exterior_EventScript_Grunt1Defeated
 * goto_if_eq VAR_MAP_SCENE_ONE_ISLAND_POKEMON_CENTER_1F, 4, MtEmber_Exterior_EventScript_BattleGrunt1
 * msgbox MtEmber_Exterior_Text_WellTryDiggingHere
 * release
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Exterior_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_defeated TRAINER_TEAM_ROCKET_GRUNT_44, MtEmber_Exterior_EventScript_DefeatedGrunt2
 * goto_if_eq VAR_MAP_SCENE_ONE_ISLAND_POKEMON_CENTER_1F, 4, MtEmber_Exterior_EventScript_BattleGrunt2
 * msgbox MtEmber_Exterior_Text_YoureInTheWayGetLost
 * closemessage
 * applymovement LOCALID_MT_EMBER_GRUNT2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Exterior_EventScript_Grunt2")
}

internal object MtEmber_Exterior_EventScript_Jocelyn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CRUSH_GIRL_JOCELYN, MtEmber_Exterior.JocelynIntro, MtEmber_Exterior.JocelynDefeat))
        return
    ctx.say(MtEmber_Exterior.JocelynPostBattle)
  }
}

internal object MtEmber_Exterior_EventScript_Logan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_RANGER_LOGAN, MtEmber_Exterior.LoganIntro, MtEmber_Exterior.LoganDefeat))
        return
    ctx.say(MtEmber_Exterior.LoganPostBattle)
  }
}

internal object MtEmber_Exterior_EventScript_Beth : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_RANGER_BETH, MtEmber_Exterior.BethIntro, MtEmber_Exterior.BethDefeat))
        return
    ctx.say(MtEmber_Exterior.BethPostBattle)
  }
}

internal object MtEmber_Exterior_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

internal object MtEmber_Exterior_EventScript_ItemFireStone : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.FIRE_STONE)
}

internal object MtEmber_Exterior_EventScript_ItemDireHit : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.DIRE_HIT)
}

internal val MtEmber_ExteriorScripts: Map<String, Script> =
    mapOf(
        "MtEmber_Exterior_EventScript_ExplosionTutor" to
            MtEmber_Exterior_EventScript_ExplosionTutor,
        "MtEmber_Exterior_EventScript_Grunt1" to MtEmber_Exterior_EventScript_Grunt1,
        "MtEmber_Exterior_EventScript_Grunt2" to MtEmber_Exterior_EventScript_Grunt2,
        "MtEmber_Exterior_EventScript_Jocelyn" to MtEmber_Exterior_EventScript_Jocelyn,
        "MtEmber_Exterior_EventScript_Logan" to MtEmber_Exterior_EventScript_Logan,
        "MtEmber_Exterior_EventScript_Beth" to MtEmber_Exterior_EventScript_Beth,
        "MtEmber_Exterior_EventScript_ItemUltraBall" to MtEmber_Exterior_EventScript_ItemUltraBall,
        "MtEmber_Exterior_EventScript_ItemFireStone" to MtEmber_Exterior_EventScript_ItemFireStone,
        "MtEmber_Exterior_EventScript_ItemDireHit" to MtEmber_Exterior_EventScript_ItemDireHit,
    )
