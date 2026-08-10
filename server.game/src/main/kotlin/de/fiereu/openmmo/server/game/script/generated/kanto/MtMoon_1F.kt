package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.MtMoon_1F
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BUG_CATCHER_KENT = 108
private const val TRAINER_BUG_CATCHER_ROBBY = 109
private const val TRAINER_HIKER_MARCOS = 181
private const val TRAINER_LASS_IRIS = 121
private const val TRAINER_LASS_MIRIAM = 120
private const val TRAINER_SUPER_NERD_JOVAN = 169
private const val TRAINER_YOUNGSTER_JOSH = 91

internal object MtMoon_1F_EventScript_Iris : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_LASS_IRIS, MtMoon_1F.IrisIntro, MtMoon_1F.IrisDefeat))
        return
    ctx.say(MtMoon_1F.IrisPostBattle)
  }
}

internal object MtMoon_1F_EventScript_Robby : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_ROBBY, MtMoon_1F.RobbyIntro, MtMoon_1F.RobbyDefeat))
        return
    ctx.say(MtMoon_1F.RobbyPostBattle)
  }
}

internal object MtMoon_1F_EventScript_Jovan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_SUPER_NERD_JOVAN, MtMoon_1F.JovanIntro, MtMoon_1F.JovanDefeat))
        return
    ctx.say(MtMoon_1F.JovanPostBattle)
  }
}

internal object MtMoon_1F_EventScript_Miriam : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_LASS_MIRIAM, MtMoon_1F.MiriamIntro, MtMoon_1F.MiriamDefeat))
        return
    ctx.say(MtMoon_1F.MiriamPostBattle)
  }
}

internal object MtMoon_1F_EventScript_Kent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BUG_CATCHER_KENT, MtMoon_1F.KentIntro, MtMoon_1F.KentDefeat))
        return
    ctx.say(MtMoon_1F.KentPostBattle)
  }
}

internal object MtMoon_1F_EventScript_Josh : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(TRAINER_YOUNGSTER_JOSH, MtMoon_1F.JoshIntro, MtMoon_1F.JoshDefeat))
        return
    ctx.say(MtMoon_1F.JoshPostBattle)
  }
}

internal object MtMoon_1F_EventScript_Marcos : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_HIKER_MARCOS, MtMoon_1F.MarcosIntro, MtMoon_1F.MarcosDefeat))
        return
    ctx.say(MtMoon_1F.MarcosPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PARALYZE_HEAL
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_ItemParalyzeHeal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_ItemParalyzeHeal")
}

internal object MtMoon_1F_EventScript_ItemTM09 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.TM09)
}

internal object MtMoon_1F_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.POTION)
}

internal object MtMoon_1F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.RARE_CANDY)
}

internal object MtMoon_1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.ESCAPE_ROPE)
}

internal object MtMoon_1F_EventScript_ItemMoonStone : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MOON_STONE)
}

internal object MtMoon_1F_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtMoon_1F.BrockHelpsExcavateFossils)
}

internal object MtMoon_1F_EventScript_ZubatSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtMoon_1F.ZubatIsABloodsucker)
}

internal val MtMoon_1FScripts: Map<String, Script> =
    mapOf(
        "MtMoon_1F_EventScript_Iris" to MtMoon_1F_EventScript_Iris,
        "MtMoon_1F_EventScript_Robby" to MtMoon_1F_EventScript_Robby,
        "MtMoon_1F_EventScript_Jovan" to MtMoon_1F_EventScript_Jovan,
        "MtMoon_1F_EventScript_Miriam" to MtMoon_1F_EventScript_Miriam,
        "MtMoon_1F_EventScript_Kent" to MtMoon_1F_EventScript_Kent,
        "MtMoon_1F_EventScript_Josh" to MtMoon_1F_EventScript_Josh,
        "MtMoon_1F_EventScript_Marcos" to MtMoon_1F_EventScript_Marcos,
        "MtMoon_1F_EventScript_ItemParalyzeHeal" to MtMoon_1F_EventScript_ItemParalyzeHeal,
        "MtMoon_1F_EventScript_ItemTM09" to MtMoon_1F_EventScript_ItemTM09,
        "MtMoon_1F_EventScript_ItemPotion" to MtMoon_1F_EventScript_ItemPotion,
        "MtMoon_1F_EventScript_ItemRareCandy" to MtMoon_1F_EventScript_ItemRareCandy,
        "MtMoon_1F_EventScript_ItemEscapeRope" to MtMoon_1F_EventScript_ItemEscapeRope,
        "MtMoon_1F_EventScript_ItemMoonStone" to MtMoon_1F_EventScript_ItemMoonStone,
        "MtMoon_1F_EventScript_BaldingMan" to MtMoon_1F_EventScript_BaldingMan,
        "MtMoon_1F_EventScript_ZubatSign" to MtMoon_1F_EventScript_ZubatSign,
    )
