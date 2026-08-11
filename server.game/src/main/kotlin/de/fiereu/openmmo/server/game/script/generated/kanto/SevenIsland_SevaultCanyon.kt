package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_SevaultCanyon
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_COOL_COUPLE_LEX_NYA = 601

private const val TRAINER_COOLTRAINER_LEROY = 599
private const val TRAINER_COOLTRAINER_MICHELLE = 600
private const val TRAINER_CRUSH_GIRL_CYNDY = 591
private const val TRAINER_PKMN_RANGER_JACKSON = 596
private const val TRAINER_PKMN_RANGER_KATELYN = 598
private const val TRAINER_TAMER_EVAN = 593

internal object SevenIsland_SevaultCanyon_EventScript_Cyndy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_CRUSH_GIRL_CYNDY,
        SevenIsland_SevaultCanyon.CyndyIntro,
        SevenIsland_SevaultCanyon.CyndyDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.CyndyPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_EventScript_Evan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_TAMER_EVAN,
        SevenIsland_SevaultCanyon.EvanIntro,
        SevenIsland_SevaultCanyon.EvanDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.EvanPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_EventScript_Jackson : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_RANGER_JACKSON,
        SevenIsland_SevaultCanyon.JacksonIntro,
        SevenIsland_SevaultCanyon.JacksonDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.JacksonPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_EventScript_Katelyn : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_RANGER_KATELYN,
        SevenIsland_SevaultCanyon.KatelynIntro,
        SevenIsland_SevaultCanyon.KatelynDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.KatelynPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_EventScript_Leroy : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_LEROY,
        SevenIsland_SevaultCanyon.LeroyIntro,
        SevenIsland_SevaultCanyon.LeroyDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.LeroyPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_EventScript_Michelle : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOLTRAINER_MICHELLE,
        SevenIsland_SevaultCanyon.MichelleIntro,
        SevenIsland_SevaultCanyon.MichelleDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.MichellePostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_EventScript_Lex : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOL_COUPLE_LEX_NYA,
        SevenIsland_SevaultCanyon.LexIntro,
        SevenIsland_SevaultCanyon.LexDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.LexPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_EventScript_Nya : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_COOL_COUPLE_LEX_NYA,
        SevenIsland_SevaultCanyon.NyaIntro,
        SevenIsland_SevaultCanyon.NyaDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon.NyaPostBattle)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_KINGS_ROCK
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_ItemKingsRock : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_ItemKingsRock")
}

internal object SevenIsland_SevaultCanyon_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.MAX_ELIXIR)
}

internal object SevenIsland_SevaultCanyon_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.NUGGET)
}

internal object SevenIsland_SevaultCanyon_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SevenIsland_SevaultCanyon.BrunoTrainedWithBrawly)
}

internal object SevenIsland_SevaultCanyon_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SevenIsland_SevaultCanyon.RouteSign)
}

internal val SevenIsland_SevaultCanyonScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_SevaultCanyon_EventScript_Cyndy" to
            SevenIsland_SevaultCanyon_EventScript_Cyndy,
        "SevenIsland_SevaultCanyon_EventScript_Evan" to SevenIsland_SevaultCanyon_EventScript_Evan,
        "SevenIsland_SevaultCanyon_EventScript_Jackson" to
            SevenIsland_SevaultCanyon_EventScript_Jackson,
        "SevenIsland_SevaultCanyon_EventScript_Katelyn" to
            SevenIsland_SevaultCanyon_EventScript_Katelyn,
        "SevenIsland_SevaultCanyon_EventScript_Leroy" to
            SevenIsland_SevaultCanyon_EventScript_Leroy,
        "SevenIsland_SevaultCanyon_EventScript_Michelle" to
            SevenIsland_SevaultCanyon_EventScript_Michelle,
        "SevenIsland_SevaultCanyon_EventScript_Lex" to SevenIsland_SevaultCanyon_EventScript_Lex,
        "SevenIsland_SevaultCanyon_EventScript_Nya" to SevenIsland_SevaultCanyon_EventScript_Nya,
        "SevenIsland_SevaultCanyon_EventScript_ItemKingsRock" to
            SevenIsland_SevaultCanyon_EventScript_ItemKingsRock,
        "SevenIsland_SevaultCanyon_EventScript_ItemMaxElixir" to
            SevenIsland_SevaultCanyon_EventScript_ItemMaxElixir,
        "SevenIsland_SevaultCanyon_EventScript_ItemNugget" to
            SevenIsland_SevaultCanyon_EventScript_ItemNugget,
        "SevenIsland_SevaultCanyon_EventScript_BlackBelt" to
            SevenIsland_SevaultCanyon_EventScript_BlackBelt,
        "SevenIsland_SevaultCanyon_EventScript_RouteSign" to
            SevenIsland_SevaultCanyon_EventScript_RouteSign,
    )
