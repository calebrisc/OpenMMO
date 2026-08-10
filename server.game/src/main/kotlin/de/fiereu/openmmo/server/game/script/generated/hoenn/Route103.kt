package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route103
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val TRAINER_ANDREW = 336
private const val TRAINER_DAISY = 36
private const val TRAINER_ISABELLE = 736
private const val TRAINER_MARCOS = 702
private const val TRAINER_PETE = 735
private const val TRAINER_RHETT = 703

private const val LOCALID_RIVAL = 1

internal object Route103_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route103.ShortcutToOldale)
}

internal object Route103_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_DEFEATED_RIVAL_ROUTE103)) return
    val femalePlayer = ctx.isFemale
    if (femalePlayer) {
      ctx.say(Route103.BrendanRoute103Pokemon)
      ctx.say(Route103.BrendanLetsBattle)
    } else {
      ctx.say(Route103.MayRoute103Pokemon)
      ctx.say(Route103.MayLetsBattle)
    }

    val opponent =
        when (ctx.getVar(HoennVars.VAR_STARTER_MON)) {
          0 -> 255 // Treecko player -> Torchic rival
          1 -> 258 // Torchic player -> Mudkip rival
          else -> 252 // Mudkip player -> Treecko rival
        }
    val moves =
        when (opponent) {
          252 -> intArrayOf(1, 43)
          255 -> intArrayOf(10, 45)
          else -> intArrayOf(33, 45)
        }
    if (ctx.battle(opponent, 5, *moves) != BattleResult.VICTORY) return

    if (femalePlayer) {
      ctx.say(Route103.BrendanDefeated)
      ctx.say(Route103.BrendanTimeToHeadBack)
    } else {
      ctx.say(Route103.MayDefeated)
      ctx.say(Route103.MayTimeToHeadBack)
    }
    ctx.moveNpc(LOCALID_RIVAL, WALK_DOWN, WALK_DOWN, WALK_DOWN, WALK_DOWN, FACE_DOWN)
    ctx.setFlag(HoennFlags.FLAG_HIDE_ROUTE_103_RIVAL)
    ctx.setVar(HoennVars.VAR_BIRCH_LAB_STATE, 4)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BIRCHS_LAB_RIVAL)
    ctx.setFlag(HoennFlags.FLAG_DEFEATED_RIVAL_ROUTE103)
    ctx.setVar(HoennVars.VAR_OLDALE_RIVAL_STATE, 1)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_OLDALE_TOWN_RIVAL)
  }
}

internal object Route103_EventScript_Daisy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_DAISY, Route103.DaisyIntro, Route103.DaisyDefeated)) return
    ctx.say(Route103.DaisyPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_AMY_AND_LIV_1, Route103_Text_LivIntro, Route103_Text_LivDefeated, Route103_Text_LivNotEnoughPokemon, Route102_EventScript_LivRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route102_EventScript_LivRematch
 * msgbox Route103_Text_LivPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Liv : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Liv")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_AMY_AND_LIV_1, Route103_Text_AmyIntro, Route103_Text_AmyDefeated, Route103_Text_AmyNotEnoughPokemon, Route102_EventScript_AmyRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route102_EventScript_AmyRematch
 * msgbox Route103_Text_AmyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Amy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Amy")
}

internal object Route103_EventScript_Andrew : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ANDREW, Route103.AndrewIntro, Route103.AndrewDefeated))
        return
    ctx.say(Route103.AndrewPostBattle)
  }
}

internal object Route103_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route103.ShouldHaveBroughtPotion)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MIGUEL_1, Route103_Text_MiguelIntro, Route103_Text_MiguelDefeated, Route102_EventScript_MiguelRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route103_EventScript_MiguelRematch
 * msgbox Route103_Text_MiguelPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route103_EventScript_Miguel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Miguel")
}

internal object Route103_EventScript_ItemGuardSpec : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.GUARD_SPEC)
}

internal object Route103_EventScript_Rhett : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RHETT, Route103.RhettIntro, Route103.RhettDefeated)) return
    ctx.say(Route103.RhettPostBattle)
  }
}

internal object Route103_EventScript_Marcos : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MARCOS, Route103.MarcosIntro, Route103.MarcosDefeated))
        return
    ctx.say(Route103.MarcosPostBattle)
  }
}

internal object Route103_EventScript_Isabelle : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_ISABELLE, Route103.IsabelleIntro, Route103.IsabelleDefeated))
        return
    ctx.say(Route103.IsabellePostBattle)
  }
}

internal object Route103_EventScript_Pete : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_PETE, Route103.PeteIntro, Route103.PeteDefeated)) return
    ctx.say(Route103.PetePostBattle)
  }
}

internal object Route103_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.PP_UP)
}

internal object Route103_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route103.RouteSign)
}

internal val Route103Scripts: Map<String, Script> =
    mapOf(
        "Route103_EventScript_Man" to Route103_EventScript_Man,
        "Route103_EventScript_Rival" to Route103_EventScript_Rival,
        "Route103_EventScript_Daisy" to Route103_EventScript_Daisy,
        "Route103_EventScript_Liv" to Route103_EventScript_Liv,
        "Route103_EventScript_Amy" to Route103_EventScript_Amy,
        "Route103_EventScript_Andrew" to Route103_EventScript_Andrew,
        "Route103_EventScript_Boy" to Route103_EventScript_Boy,
        "Route103_EventScript_Miguel" to Route103_EventScript_Miguel,
        "Route103_EventScript_ItemGuardSpec" to Route103_EventScript_ItemGuardSpec,
        "Route103_EventScript_Rhett" to Route103_EventScript_Rhett,
        "Route103_EventScript_Marcos" to Route103_EventScript_Marcos,
        "Route103_EventScript_Isabelle" to Route103_EventScript_Isabelle,
        "Route103_EventScript_Pete" to Route103_EventScript_Pete,
        "Route103_EventScript_ItemPPUp" to Route103_EventScript_ItemPPUp,
        "Route103_EventScript_RouteSign" to Route103_EventScript_RouteSign,
    )
