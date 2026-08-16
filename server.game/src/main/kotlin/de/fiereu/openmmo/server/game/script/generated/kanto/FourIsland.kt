package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * special GetDaycareMonNicknames
 * specialvar VAR_RESULT, GetDaycareState
 * goto_if_eq VAR_RESULT, DAYCARE_EGG_WAITING, FourIsland_EventScript_DaycareEggWaiting
 * goto_if_eq VAR_RESULT, DAYCARE_ONE_MON, FourIsland_EventScript_CheckOnOneMon
 * goto_if_eq VAR_RESULT, DAYCARE_TWO_MONS, FourIsland_EventScript_CheckOnTwoMons
 * msgbox DayCare_Text_ImDaycareManSpeakToMyWife
 * release
 * end
 * ```
 */
internal object FourIsland_EventScript_DaycareMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_EventScript_DaycareMan")
}

internal object FourIsland_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_SYS_CAN_LINK_WITH_RS)) {
      ctx.say(FourIsland.LoreleiMetLaprasAsChild)
      return
    }
    ctx.say(FourIsland.LoreleiHasReturned)
  }
}

internal object FourIsland_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.STAR_PIECE)
}

internal object FourIsland_EventScript_ItemStardust : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.STARDUST)
}

internal object FourIsland_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FourIsland.MadeSlushieUsingIcefallCaveIce)
}

internal object FourIsland_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FourIsland.LoreleiHasLotsOfStuffedDolls)
}

internal object FourIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FourIsland.IslandSign)
}

internal object FourIsland_EventScript_LoreleisHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FourIsland.LoreleisHouse)
}

internal val FourIslandScripts: Map<String, Script> =
    mapOf(
        "FourIsland_EventScript_DaycareMan" to FourIsland_EventScript_DaycareMan,
        "FourIsland_EventScript_OldWoman" to FourIsland_EventScript_OldWoman,
        "FourIsland_EventScript_ItemStarPiece" to FourIsland_EventScript_ItemStarPiece,
        "FourIsland_EventScript_ItemStardust" to FourIsland_EventScript_ItemStardust,
        "FourIsland_EventScript_FatMan" to FourIsland_EventScript_FatMan,
        "FourIsland_EventScript_LittleGirl" to FourIsland_EventScript_LittleGirl,
        "FourIsland_EventScript_IslandSign" to FourIsland_EventScript_IslandSign,
        "FourIsland_EventScript_LoreleisHouseSign" to FourIsland_EventScript_LoreleisHouseSign,
    )
