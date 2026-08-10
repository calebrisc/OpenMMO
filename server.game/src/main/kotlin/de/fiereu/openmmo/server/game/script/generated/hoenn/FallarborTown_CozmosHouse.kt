package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FallarborTown_CozmosHouse
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags

internal object FallarborTown_CozmosHouse_EventScript_ProfCozmo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_TM_RETURN)) {
      return ctx.say(FallarborTown_CozmosHouse.ReallyGoingToHelpMyResearch)
    }
    if (!ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_METEORITE)) {
      return ctx.say(FallarborTown_CozmosHouse.MeteoriteWillNeverBeMineNow)
    }
    ctx.say(FallarborTown_CozmosHouse.IsThatMeteoriteMayIHaveIt)
    ctx.say(FallarborTown_CozmosHouse.PleaseUseThisTM)
    if (!ctx.giveItem(Items.TM27)) return
    ctx.setFlag(HoennFlags.FLAG_RECEIVED_TM_RETURN)
    ctx.clearFlag(HoennFlags.FLAG_RECEIVED_METEORITE)
    ctx.say(FallarborTown_CozmosHouse.ReallyGoingToHelpMyResearch)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_RETURN, FallarborTown_CozmosHouse_EventScript_CozmoIsHappy
 * goto_if_set FLAG_DEFEATED_EVIL_TEAM_MT_CHIMNEY, FallarborTown_CozmosHouse_EventScript_CozmoIsSad
 * msgbox FallarborTown_CozmosHouse_Text_CozmoWentToMeteorFalls, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FallarborTown_CozmosHouse_EventScript_CozmosWife : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FallarborTown_CozmosHouse_EventScript_CozmosWife")
}

internal val FallarborTown_CozmosHouseScripts: Map<String, Script> =
    mapOf(
        "FallarborTown_CozmosHouse_EventScript_ProfCozmo" to
            FallarborTown_CozmosHouse_EventScript_ProfCozmo,
        "FallarborTown_CozmosHouse_EventScript_CozmosWife" to
            FallarborTown_CozmosHouse_EventScript_CozmosWife,
    )
