package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_SevaultCanyon_Entrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_YOUNG_COUPLE_EVE_JON = 589

private const val TRAINER_AROMA_LADY_MIAH = 588
private const val TRAINER_JUGGLER_MASON = 590
private const val TRAINER_PKMN_RANGER_MADELINE = 522
private const val TRAINER_PKMN_RANGER_NICOLAS = 521

internal object SevenIsland_SevaultCanyon_Entrance_EventScript_Miah : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_AROMA_LADY_MIAH,
        SevenIsland_SevaultCanyon_Entrance.MiahIntro,
        SevenIsland_SevaultCanyon_Entrance.MiahDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon_Entrance.MiahPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_Entrance_EventScript_Eve : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNG_COUPLE_EVE_JON,
        SevenIsland_SevaultCanyon_Entrance.EveIntro,
        SevenIsland_SevaultCanyon_Entrance.EveDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon_Entrance.EvePostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_Entrance_EventScript_Jon : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_YOUNG_COUPLE_EVE_JON,
        SevenIsland_SevaultCanyon_Entrance.JonIntro,
        SevenIsland_SevaultCanyon_Entrance.JonDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon_Entrance.JonPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_Entrance_EventScript_Nicolas : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_RANGER_NICOLAS,
        SevenIsland_SevaultCanyon_Entrance.NicolasIntro,
        SevenIsland_SevaultCanyon_Entrance.NicolasDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon_Entrance.NicolasPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_Entrance_EventScript_Madeline : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_PKMN_RANGER_MADELINE,
        SevenIsland_SevaultCanyon_Entrance.MadelineIntro,
        SevenIsland_SevaultCanyon_Entrance.MadelineDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon_Entrance.MadelinePostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_Entrance_EventScript_Mason : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_JUGGLER_MASON,
        SevenIsland_SevaultCanyon_Entrance.MasonIntro,
        SevenIsland_SevaultCanyon_Entrance.MasonDefeat))
        return
    ctx.say(SevenIsland_SevaultCanyon_Entrance.MasonPostBattle)
  }
}

internal object SevenIsland_SevaultCanyon_Entrance_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SevenIsland_SevaultCanyon_Entrance.RouteSign)
}

internal val SevenIsland_SevaultCanyon_EntranceScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_SevaultCanyon_Entrance_EventScript_Miah" to
            SevenIsland_SevaultCanyon_Entrance_EventScript_Miah,
        "SevenIsland_SevaultCanyon_Entrance_EventScript_Eve" to
            SevenIsland_SevaultCanyon_Entrance_EventScript_Eve,
        "SevenIsland_SevaultCanyon_Entrance_EventScript_Jon" to
            SevenIsland_SevaultCanyon_Entrance_EventScript_Jon,
        "SevenIsland_SevaultCanyon_Entrance_EventScript_Nicolas" to
            SevenIsland_SevaultCanyon_Entrance_EventScript_Nicolas,
        "SevenIsland_SevaultCanyon_Entrance_EventScript_Madeline" to
            SevenIsland_SevaultCanyon_Entrance_EventScript_Madeline,
        "SevenIsland_SevaultCanyon_Entrance_EventScript_Mason" to
            SevenIsland_SevaultCanyon_Entrance_EventScript_Mason,
        "SevenIsland_SevaultCanyon_Entrance_EventScript_RouteSign" to
            SevenIsland_SevaultCanyon_Entrance_EventScript_RouteSign,
    )
