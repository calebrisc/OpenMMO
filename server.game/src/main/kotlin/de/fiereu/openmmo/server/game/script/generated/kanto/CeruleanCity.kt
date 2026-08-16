package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity
import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_BikeShop
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars
import kotlin.random.Random

private const val TRAINER_TEAM_ROCKET_GRUNT_5 = 355

internal object CeruleanCity_EventScript_Policeman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.PeopleHereWereRobbed)
}

internal object CeruleanCity_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TEAM_ROCKET_GRUNT_5, CeruleanCity.GruntIntro, CeruleanCity.GruntDefeat))
        return
    ctx.setVar(KantoVars.VAR_MAP_SCENE_CERULEAN_CITY_ROCKET, 1)
    ctx.say(CeruleanCity.OkayIllReturnStolenTM)
    // He leaves whether or not the bag has room, as the decomp has it: the TM he stole is the
    // apology, but the apology is not what makes him go.
    if (ctx.giveItem(Items.TM28)) ctx.say(CeruleanCity.RecoveredTM28FromGrunt)
    ctx.say(CeruleanCity.BetterGetMovingBye)
    ctx.setFlag(KantoFlags.FLAG_HIDE_CERULEAN_ROCKET)
    ctx.despawnInteracted()
  }
}

internal object CeruleanCity_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_SS_TICKET)) {
      ctx.say(CeruleanCity.YouCanCutDownSmallTrees)
      return
    }
    ctx.say(CeruleanCity.IfSlowbroWasntThereCouldCutTree)
  }
}

internal object CeruleanCity_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.PokemonEncyclopediaAmusing)
}

internal object CeruleanCity_EventScript_Slowbro : Script {
  override suspend fun run(ctx: ScriptContext) {
    val roll1 = Random.nextInt(4)
    if (roll1 == 0) {
      ctx.say(CeruleanCity.SlowbroTookSnooze)
    }
    if (roll1 == 1) {
      ctx.say(CeruleanCity.SlowbroLoafingAround)
    }
    if (roll1 == 2) {
      ctx.say(CeruleanCity.SlowbroTurnedAway)
    }
    if (roll1 == 3) {
      ctx.say(CeruleanCity.SlowbroIgnoredOrders)
    }
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * random 3
 * copyvar VAR_0x8008, VAR_RESULT
 * call_if_eq VAR_0x8008, 0, CeruleanCity_EventScript_SlowbroCommand1
 * call_if_eq VAR_0x8008, 1, CeruleanCity_EventScript_SlowbroCommand2
 * call_if_eq VAR_0x8008, 2, CeruleanCity_EventScript_SlowbroCommand3
 * waitmessage
 * delay 40
 * playse SE_PIN
 * applymovement LOCALID_CERULEAN_SLOWBRO, Common_Movement_QuestionMark
 * waitmovement 0
 * delay 30
 * call_if_eq VAR_0x8008, 0, CeruleanCity_EventScript_SlowbroFailed1
 * call_if_eq VAR_0x8008, 1, CeruleanCity_EventScript_SlowbroFailed2
 * call_if_eq VAR_0x8008, 2, CeruleanCity_EventScript_SlowbroFailed3
 * release
 * end
 * ```
 */
internal object CeruleanCity_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_Lass")
}

internal object CeruleanCity_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.TrainerLifeIsToughIsntIt)
}

internal object CeruleanCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.WantBrightRedBicycle)
}

internal object CeruleanCity_EventScript_CeruleanCaveGuard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.ThisIsCeruleanCave)
}

internal object CeruleanCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity.CitySign)
}

internal object CeruleanCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.GymSign)
}

internal object CeruleanCity_EventScript_BikeShopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity.BikeShopSign)
}

internal object CeruleanCity_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity.TrainerTipsHeldItems)
}

internal object CeruleanCity_BikeShop_EventScript_Bicycle : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity_BikeShop.ShinyNewBicycle)
}

internal val CeruleanCityScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_EventScript_Policeman" to CeruleanCity_EventScript_Policeman,
        "CeruleanCity_EventScript_Grunt" to CeruleanCity_EventScript_Grunt,
        "CeruleanCity_EventScript_LittleBoy" to CeruleanCity_EventScript_LittleBoy,
        "CeruleanCity_EventScript_BaldingMan" to CeruleanCity_EventScript_BaldingMan,
        "CeruleanCity_EventScript_Slowbro" to CeruleanCity_EventScript_Slowbro,
        "CeruleanCity_EventScript_Lass" to CeruleanCity_EventScript_Lass,
        "CeruleanCity_EventScript_Youngster" to CeruleanCity_EventScript_Youngster,
        "CeruleanCity_EventScript_Woman" to CeruleanCity_EventScript_Woman,
        "CeruleanCity_EventScript_CeruleanCaveGuard" to CeruleanCity_EventScript_CeruleanCaveGuard,
        "CeruleanCity_EventScript_CitySign" to CeruleanCity_EventScript_CitySign,
        "CeruleanCity_EventScript_GymSign" to CeruleanCity_EventScript_GymSign,
        "CeruleanCity_EventScript_BikeShopSign" to CeruleanCity_EventScript_BikeShopSign,
        "CeruleanCity_EventScript_TrainerTips" to CeruleanCity_EventScript_TrainerTips,
        "CeruleanCity_BikeShop_EventScript_Bicycle" to CeruleanCity_BikeShop_EventScript_Bicycle,
    )
