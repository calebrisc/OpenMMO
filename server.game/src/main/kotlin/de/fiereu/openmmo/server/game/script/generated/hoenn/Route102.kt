package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route102
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_ALLEN = 333
private const val TRAINER_RICK = 615
private const val TRAINER_TIANA = 603

internal object Route102_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route102.ImNotVeryTall)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CALVIN_1, Route102_Text_CalvinIntro, Route102_Text_CalvinDefeated, Route102_EventScript_CalvinRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route102_EventScript_CalvinRematch
 * setvar VAR_0x8004, TRAINER_CALVIN_1
 * specialvar VAR_RESULT, IsTrainerRegistered
 * goto_if_eq VAR_RESULT, FALSE, Route102_EventScript_CalvinTryRegister
 * msgbox Route102_Text_CalvinPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route102_EventScript_Calvin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route102_EventScript_Calvin")
}

internal object Route102_EventScript_Rick : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_RICK, Route102.RickIntro, Route102.RickDefeated)) return
    ctx.say(Route102.RickPostBattle)
  }
}

internal object Route102_EventScript_Tiana : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_TIANA, Route102.TianaIntro, Route102.TianaDefeated)) return
    ctx.say(Route102.TianaPostBattle)
  }
}

internal object Route102_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route102.CatchWholeBunchOfPokemon)
}

internal object Route102_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POTION)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * special ObjectEventInteractionGetBerryTreeData
 * switch VAR_0x8004
 * case BERRY_STAGE_SPARKLING, BerryTree_EventScript_Sparkling
 * case BERRY_STAGE_NO_BERRY, BerryTree_EventScript_CheckSoil
 * case BERRY_STAGE_PLANTED, BerryTree_EventScript_CheckBerryStage1
 * case BERRY_STAGE_SPROUTED, BerryTree_EventScript_CheckBerryStage2
 * case BERRY_STAGE_TALLER, BerryTree_EventScript_CheckBerryStage3
 * case BERRY_STAGE_FLOWERING, BerryTree_EventScript_CheckBerryStage4
 * case BERRY_STAGE_BERRIES, BerryTree_EventScript_CheckBerryFullyGrown
 * end
 * ```
 */
internal object BerryTreeScript : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BerryTreeScript")
}

internal object Route102_EventScript_Allen : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ALLEN, Route102.AllenIntro, Route102.AllenDefeated)) return
    ctx.say(Route102.AllenPostBattle)
  }
}

internal object Route102_EventScript_RouteSignPetalburg : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route102.RouteSignPetalburg)
}

internal object Route102_EventScript_RouteSignOldale : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route102.RouteSignOldale)
}

internal val Route102Scripts: Map<String, Script> =
    mapOf(
        "Route102_EventScript_LittleBoy" to Route102_EventScript_LittleBoy,
        "Route102_EventScript_Calvin" to Route102_EventScript_Calvin,
        "Route102_EventScript_Rick" to Route102_EventScript_Rick,
        "Route102_EventScript_Tiana" to Route102_EventScript_Tiana,
        "Route102_EventScript_Boy" to Route102_EventScript_Boy,
        "Route102_EventScript_ItemPotion" to Route102_EventScript_ItemPotion,
        "BerryTreeScript" to BerryTreeScript,
        "Route102_EventScript_Allen" to Route102_EventScript_Allen,
        "Route102_EventScript_RouteSignPetalburg" to Route102_EventScript_RouteSignPetalburg,
        "Route102_EventScript_RouteSignOldale" to Route102_EventScript_RouteSignOldale,
    )
