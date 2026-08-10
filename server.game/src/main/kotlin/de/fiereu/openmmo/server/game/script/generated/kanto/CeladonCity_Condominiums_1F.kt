package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Condominiums_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object CeladonCity_Condominiums_1F_EventScript_Meowth : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Condominiums_1F.Meowth)
}

internal object CeladonCity_Condominiums_1F_EventScript_Clefairy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Condominiums_1F.Clefairy)
}

internal object CeladonCity_Condominiums_1F_EventScript_Nidoran : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Condominiums_1F.Nidoran)
}

internal object CeladonCity_Condominiums_1F_EventScript_TeaWoman : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_TEA)) {
      return ctx.say(CeladonCity_Condominiums_1F.MyDearMonsKeepMeCompany)
    }
    ctx.say(CeladonCity_Condominiums_1F.TryThisDrinkInstead)
    ctx.setFlag(KantoFlags.FLAG_GOT_TEA)
    ctx.say(CeladonCity_Condominiums_1F.NothingBeatsThirstLikeTea)
  }
}

internal object CeladonCity_Condominiums_1F_EventScript_SuiteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_Condominiums_1F.ManagersSuite)
}

internal val CeladonCity_Condominiums_1FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Condominiums_1F_EventScript_Meowth" to
            CeladonCity_Condominiums_1F_EventScript_Meowth,
        "CeladonCity_Condominiums_1F_EventScript_Clefairy" to
            CeladonCity_Condominiums_1F_EventScript_Clefairy,
        "CeladonCity_Condominiums_1F_EventScript_Nidoran" to
            CeladonCity_Condominiums_1F_EventScript_Nidoran,
        "CeladonCity_Condominiums_1F_EventScript_TeaWoman" to
            CeladonCity_Condominiums_1F_EventScript_TeaWoman,
        "CeladonCity_Condominiums_1F_EventScript_SuiteSign" to
            CeladonCity_Condominiums_1F_EventScript_SuiteSign,
    )
