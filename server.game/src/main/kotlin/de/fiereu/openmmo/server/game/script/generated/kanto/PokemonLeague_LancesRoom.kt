package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonLeague_ChampionsRoom
import de.fiereu.openmmo.dialog.generated.kanto.PokemonLeague_LancesRoom
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val LANCE_ID = 413

internal object PokemonLeague_LancesRoom_EventScript_Lance : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isTrainerDefeated(LANCE_ID)) return ctx.say(PokemonLeague_LancesRoom.PostBattle)
    if (!ctx.trainerBattleSingle(
        LANCE_ID, PokemonLeague_LancesRoom.Intro, PokemonLeague_LancesRoom.Defeat))
        return
    ctx.say(PokemonLeague_LancesRoom.PostBattle)
  }
}

// The champion scene is this map's on-frame script; the room has no npc script of its own, so
// the label is registered here. It self-gates on the champion being beaten instead of the
// decomp's VAR_TEMP_1, which this engine never resets between maps.
private const val CHAMPION_WITH_SQUIRTLE = 438
private const val CHAMPION_WITH_BULBASAUR = 439
private const val CHAMPION_WITH_CHARMANDER = 440
private const val LOCALID_CHAMPION = 1

internal object PokemonLeague_ChampionsRoom_EventScript_EnterRoom : Script {
  override suspend fun run(ctx: ScriptContext) {
    val champion =
        when (ctx.getVar(KantoVars.VAR_STARTER_MON)) {
          0 -> CHAMPION_WITH_CHARMANDER
          1 -> CHAMPION_WITH_BULBASAUR
          else -> CHAMPION_WITH_SQUIRTLE
        }
    if (ctx.isTrainerDefeated(champion)) return
    ctx.sayNpc(LOCALID_CHAMPION, PokemonLeague_ChampionsRoom.Intro)
    if (ctx.trainerBattle(champion) != BattleResult.VICTORY) return
    ctx.sayNpc(LOCALID_CHAMPION, PokemonLeague_ChampionsRoom.Defeat)
    ctx.sayNpc(LOCALID_CHAMPION, PokemonLeague_ChampionsRoom.Victory)
    ctx.setFlag(KantoFlags.FLAG_SYS_GAME_CLEAR)
    ctx.healParty()
    ctx.send(notice("Congratulations! You are the new Pokemon League Champion!"))
  }
}

internal val PokemonLeague_LancesRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_ChampionsRoom_EventScript_EnterRoom" to
            PokemonLeague_ChampionsRoom_EventScript_EnterRoom,
        "PokemonLeague_LancesRoom_EventScript_Lance" to PokemonLeague_LancesRoom_EventScript_Lance,
    )
