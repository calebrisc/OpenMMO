package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.server.game.script.generated.GeneratedScripts

/** Finds the [Script] for a decomp script label, or null when nothing is wired for it. */
class ScriptRegistry(private val byLabel: Map<String, Script>) {
  fun forLabel(scriptLabel: String): Script? = byLabel[scriptLabel]

  companion object {
    // Shared scripts win, so a per-map stub that was never ported does not shadow the real one.
    fun generated(): ScriptRegistry =
        ScriptRegistry(GeneratedScripts.byLabel + SharedScripts.byLabel)
  }
}
