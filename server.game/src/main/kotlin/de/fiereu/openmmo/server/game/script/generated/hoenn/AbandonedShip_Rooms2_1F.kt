package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_Rooms2_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_GARRISON = 547
private const val TRAINER_JANI = 418

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_KIRA_AND_DAN_1, AbandonedShip_Rooms2_1F_Text_DanIntro, AbandonedShip_Rooms2_1F_Text_DanDefeat, AbandonedShip_Rooms2_1F_Text_DanNotEnoughMons, AbandonedShip_Rooms2_1F_EventScript_RegisterDan
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, AbandonedShip_Rooms2_1F_EventScript_DanRematch
 * msgbox AbandonedShip_Rooms2_1F_Text_DanPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object AbandonedShip_Rooms2_1F_EventScript_Dan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Rooms2_1F_EventScript_Dan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_KIRA_AND_DAN_1, AbandonedShip_Rooms2_1F_Text_KiraIntro, AbandonedShip_Rooms2_1F_Text_KiraDefeat, AbandonedShip_Rooms2_1F_Text_KiraNotEnoughMons, AbandonedShip_Rooms2_1F_EventScript_RegisterKira
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, AbandonedShip_Rooms2_1F_EventScript_KiraRematch
 * msgbox AbandonedShip_Rooms2_1F_Text_KiraPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object AbandonedShip_Rooms2_1F_EventScript_Kira : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Rooms2_1F_EventScript_Kira")
}

internal object AbandonedShip_Rooms2_1F_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.REVIVE)
}

internal object AbandonedShip_Rooms2_1F_EventScript_Garrison : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_GARRISON,
        AbandonedShip_Rooms2_1F.GarrisonIntro,
        AbandonedShip_Rooms2_1F.GarrisonDefeat))
        return
    ctx.say(AbandonedShip_Rooms2_1F.GarrisonPostBattle)
  }
}

internal object AbandonedShip_Rooms2_1F_EventScript_Jani : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JANI, AbandonedShip_Rooms2_1F.JaniIntro, AbandonedShip_Rooms2_1F.JaniDefeat))
        return
    ctx.say(AbandonedShip_Rooms2_1F.JaniPostBattle)
  }
}

internal val AbandonedShip_Rooms2_1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Rooms2_1F_EventScript_Dan" to AbandonedShip_Rooms2_1F_EventScript_Dan,
        "AbandonedShip_Rooms2_1F_EventScript_Kira" to AbandonedShip_Rooms2_1F_EventScript_Kira,
        "AbandonedShip_Rooms2_1F_EventScript_ItemRevive" to
            AbandonedShip_Rooms2_1F_EventScript_ItemRevive,
        "AbandonedShip_Rooms2_1F_EventScript_Garrison" to
            AbandonedShip_Rooms2_1F_EventScript_Garrison,
        "AbandonedShip_Rooms2_1F_EventScript_Jani" to AbandonedShip_Rooms2_1F_EventScript_Jani,
    )
