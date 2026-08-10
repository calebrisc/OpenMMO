package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_BRICK_BREAK, SootopolisCity_House1_EventScript_ReceivedBrickBreak
 * msgbox SootopolisCity_House1_Text_DevelopedThisTM, MSGBOX_DEFAULT
 * giveitem ITEM_TM_BRICK_BREAK
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_BRICK_BREAK
 * msgbox SootopolisCity_House1_Text_ExplainBrickBreak, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_House1_EventScript_BrickBreakBlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_House1_EventScript_BrickBreakBlackBelt")
}

internal object SootopolisCity_House1_EventScript_Kecleon : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SootopolisCity_House1.Kecleon)
}

internal val SootopolisCity_House1Scripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_House1_EventScript_BrickBreakBlackBelt" to
            SootopolisCity_House1_EventScript_BrickBreakBlackBelt,
        "SootopolisCity_House1_EventScript_Kecleon" to SootopolisCity_House1_EventScript_Kecleon,
    )
