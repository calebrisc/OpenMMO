package de.fiereu.openmmo.server.game.script

/**
 * Scripts the decomp keeps in one place and every map points at, rather than copying per map.
 *
 * The storage box is the first of them. Every pokemon centre references the same label, so porting
 * it once opens every box in the world.
 */
internal object SharedScripts {
  private val EventScript_PC = Script { ctx -> ctx.openPc() }

  val byLabel: Map<String, Script> =
      mapOf(
          "EventScript_PC" to EventScript_PC,
          // The decomp gives the player's own bedroom box its own label pointing at the same thing.
          "LittlerootTown_MaysHouse_2F_EventScript_PC" to EventScript_PC,
          "LittlerootTown_BrendansHouse_2F_EventScript_PC" to EventScript_PC,
      )
}
