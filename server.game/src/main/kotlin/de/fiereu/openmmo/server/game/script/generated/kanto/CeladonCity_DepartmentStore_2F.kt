package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_DepartmentStore_2F
import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_DepartmentStore_2F_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_2F.BuyReviveForLongOutings)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart CeladonCity_DepartmentStore_2F_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_2F_EventScript_ClerkItems : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_2F_EventScript_ClerkItems")
}

internal object CeladonCity_DepartmentStore_2F_EventScript_ClerkTMs : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.say(Misc.Text_MayIHelpYou)
    ctx.pokemart(Items.TM05, Items.TM15, Items.TM28, Items.TM31, Items.TM43, Items.TM45)
  }
}

internal object CeladonCity_DepartmentStore_2F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_2F.LanceComesToBuyCapes)
}

internal object CeladonCity_DepartmentStore_2F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_DepartmentStore_2F.FloorSign)
}

internal val CeladonCity_DepartmentStore_2FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_DepartmentStore_2F_EventScript_Lass" to
            CeladonCity_DepartmentStore_2F_EventScript_Lass,
        "CeladonCity_DepartmentStore_2F_EventScript_ClerkItems" to
            CeladonCity_DepartmentStore_2F_EventScript_ClerkItems,
        "CeladonCity_DepartmentStore_2F_EventScript_ClerkTMs" to
            CeladonCity_DepartmentStore_2F_EventScript_ClerkTMs,
        "CeladonCity_DepartmentStore_2F_EventScript_Woman" to
            CeladonCity_DepartmentStore_2F_EventScript_Woman,
        "CeladonCity_DepartmentStore_2F_EventScript_FloorSign" to
            CeladonCity_DepartmentStore_2F_EventScript_FloorSign,
    )
