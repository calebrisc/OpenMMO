package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_2F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_LEAH = 35
private const val TRAINER_MARK = 145
private const val TRAINER_ZANDER = 31

internal object MtPyre_2F_EventScript_Mark : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_MARK, MtPyre_2F.MarkIntro, MtPyre_2F.MarkDefeat)) return
    ctx.say(MtPyre_2F.MarkPostBattle)
  }
}

internal object MtPyre_2F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ULTRA_BALL)
}

internal object MtPyre_2F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtPyre_2F.MemoriesOfSkitty)
}

internal object MtPyre_2F_EventScript_PokefanM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtPyre_2F.TumbledFromFloorAbove)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_DEZ_AND_LUKE, MtPyre_2F_Text_DezIntro, MtPyre_2F_Text_DezDefeat, MtPyre_2F_Text_DezNotEnoughMons
 * msgbox MtPyre_2F_Text_DezPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_Dez : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_Dez")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_DEZ_AND_LUKE, MtPyre_2F_Text_LukeIntro, MtPyre_2F_Text_LukeDefeat, MtPyre_2F_Text_LukeNotEnoughMons
 * msgbox MtPyre_2F_Text_LukePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_Luke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_Luke")
}

internal object MtPyre_2F_EventScript_Zander : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_ZANDER, MtPyre_2F.ZanderIntro, MtPyre_2F.ZanderDefeat))
        return
    ctx.say(MtPyre_2F.ZanderPostBattle)
  }
}

internal object MtPyre_2F_EventScript_Leah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LEAH, MtPyre_2F.LeahIntro, MtPyre_2F.LeahDefeat)) return
    ctx.say(MtPyre_2F.LeahPostBattle)
  }
}

internal val MtPyre_2FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_2F_EventScript_Mark" to MtPyre_2F_EventScript_Mark,
        "MtPyre_2F_EventScript_ItemUltraBall" to MtPyre_2F_EventScript_ItemUltraBall,
        "MtPyre_2F_EventScript_Woman" to MtPyre_2F_EventScript_Woman,
        "MtPyre_2F_EventScript_PokefanM" to MtPyre_2F_EventScript_PokefanM,
        "MtPyre_2F_EventScript_Dez" to MtPyre_2F_EventScript_Dez,
        "MtPyre_2F_EventScript_Luke" to MtPyre_2F_EventScript_Luke,
        "MtPyre_2F_EventScript_Zander" to MtPyre_2F_EventScript_Zander,
        "MtPyre_2F_EventScript_Leah" to MtPyre_2F_EventScript_Leah,
    )
