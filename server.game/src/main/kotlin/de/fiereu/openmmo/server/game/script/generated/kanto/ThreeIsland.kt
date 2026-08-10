package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_set FLAG_GOT_FULL_RESTORE_FROM_THREE_ISLAND_DEFENDER, ThreeIsland_EventScript_AntiBiker1GotFullRestore
 * goto_if_eq VAR_MAP_SCENE_THREE_ISLAND, 4, ThreeIsland_EventScript_GiveFullRestore
 * setvar VAR_TEMP_1, 0
 * call ThreeIsland_EventScript_BikerArgumentScene
 * release
 * end
 * ```
 */
internal object ThreeIsland_EventScript_AntiBiker1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ThreeIsland_EventScript_AntiBiker1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_eq VAR_MAP_SCENE_THREE_ISLAND, 4, ThreeIsland_EventScript_AntiBiker2BikersGone
 * setvar VAR_TEMP_1, 1
 * call ThreeIsland_EventScript_BikerArgumentScene
 * release
 * end
 * ```
 */
internal object ThreeIsland_EventScript_AntiBiker2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ThreeIsland_EventScript_AntiBiker2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * setvar VAR_TEMP_1, 2
 * call ThreeIsland_EventScript_BikerArgumentScene
 * release
 * end
 * ```
 */
internal object ThreeIsland_EventScript_Biker : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ThreeIsland_EventScript_Biker")
}

internal object ThreeIsland_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ZINC)
}

internal object ThreeIsland_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland.WouldntWantToSeeBikersHereAgain)
}

internal object ThreeIsland_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland.WhenDodouEvolvesGoingToPlayGame)
}

internal object ThreeIsland_EventScript_Doduo : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ThreeIsland.Doduo)
}

internal object ThreeIsland_EventScript_Biker6 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ThreeIsland.IslandBelongsToUs)
}

internal object ThreeIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ThreeIsland.IslandSign)
}

internal val ThreeIslandScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_EventScript_AntiBiker1" to ThreeIsland_EventScript_AntiBiker1,
        "ThreeIsland_EventScript_AntiBiker2" to ThreeIsland_EventScript_AntiBiker2,
        "ThreeIsland_EventScript_Biker" to ThreeIsland_EventScript_Biker,
        "ThreeIsland_EventScript_ItemZinc" to ThreeIsland_EventScript_ItemZinc,
        "ThreeIsland_EventScript_Woman" to ThreeIsland_EventScript_Woman,
        "ThreeIsland_EventScript_LittleBoy" to ThreeIsland_EventScript_LittleBoy,
        "ThreeIsland_EventScript_Doduo" to ThreeIsland_EventScript_Doduo,
        "ThreeIsland_EventScript_Biker6" to ThreeIsland_EventScript_Biker6,
        "ThreeIsland_EventScript_IslandSign" to ThreeIsland_EventScript_IslandSign,
    )
