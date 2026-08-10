package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_MemorialPillar
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

private const val TRAINER_BIRD_KEEPER_CHAZ = 571
private const val TRAINER_BIRD_KEEPER_HAROLD = 572
private const val TRAINER_BIRD_KEEPER_MILO = 570

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_set FLAG_GOT_TM42_AT_MEMORIAL_PILLAR, FiveIsland_MemorialPillar_EventScript_AlreadyGotTM42
 * goto_if_set FLAG_NO_ROOM_FOR_TM42_AT_MEMORIAL_PILLAR, FiveIsland_MemorialPillar_EventScript_ReturnedForTM42
 * msgbox FiveIsland_MemorialPillar_Text_ScrubScrub
 * closemessage
 * applymovement LOCALID_MEMORIAL_MAN, Common_Movement_FacePlayer
 * waitmovement 0
 * delay 45
 * msgbox FiveIsland_MemorialPillar_Text_YourMonsLookHealthy
 * applymovement LOCALID_MEMORIAL_MAN, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * msgbox FiveIsland_MemorialPillar_Text_ThisIsWhereIBuriedMyOnix
 * release
 * end
 * ```
 */
internal object FiveIsland_MemorialPillar_EventScript_MemorialMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_MemorialPillar_EventScript_MemorialMan")
}

internal object FiveIsland_MemorialPillar_EventScript_Milo : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_MILO,
        FiveIsland_MemorialPillar.MiloIntro,
        FiveIsland_MemorialPillar.MiloDefeat))
        return
    ctx.say(FiveIsland_MemorialPillar.MiloPostBattle)
  }
}

internal object FiveIsland_MemorialPillar_EventScript_Chaz : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_CHAZ,
        FiveIsland_MemorialPillar.ChazIntro,
        FiveIsland_MemorialPillar.ChazDefeat))
        return
    ctx.say(FiveIsland_MemorialPillar.ChazPostBattle)
  }
}

internal object FiveIsland_MemorialPillar_EventScript_Harold : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.trainerBattleSingle(
        TRAINER_BIRD_KEEPER_HAROLD,
        FiveIsland_MemorialPillar.HaroldIntro,
        FiveIsland_MemorialPillar.HaroldDefeat))
        return
    ctx.say(FiveIsland_MemorialPillar.HaroldPostBattle)
  }
}

internal object FiveIsland_MemorialPillar_EventScript_ItemMetalCoat : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.findItem(Items.METAL_COAT)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lockall
 * goto_if_set FLAG_GOT_TM42_AT_MEMORIAL_PILLAR, FiveIsland_MemorialPillar_EventScript_MemorialLemonadeAlreadyPlaced
 * goto_if_set FLAG_NO_ROOM_FOR_TM42_AT_MEMORIAL_PILLAR, FiveIsland_MemorialPillar_EventScript_MemorialLemonadeAlreadyPlaced
 * msgbox FiveIsland_MemorialPillar_Text_HereLiesTectonixLemonadeOffering
 * checkitem ITEM_LEMONADE
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_MemorialPillar_EventScript_AskPlaceLemonade
 * releaseall
 * end
 * ```
 */
internal object FiveIsland_MemorialPillar_EventScript_Memorial : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_MemorialPillar_EventScript_Memorial")
}

internal val FiveIsland_MemorialPillarScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_MemorialPillar_EventScript_MemorialMan" to
            FiveIsland_MemorialPillar_EventScript_MemorialMan,
        "FiveIsland_MemorialPillar_EventScript_Milo" to FiveIsland_MemorialPillar_EventScript_Milo,
        "FiveIsland_MemorialPillar_EventScript_Chaz" to FiveIsland_MemorialPillar_EventScript_Chaz,
        "FiveIsland_MemorialPillar_EventScript_Harold" to
            FiveIsland_MemorialPillar_EventScript_Harold,
        "FiveIsland_MemorialPillar_EventScript_ItemMetalCoat" to
            FiveIsland_MemorialPillar_EventScript_ItemMetalCoat,
        "FiveIsland_MemorialPillar_EventScript_Memorial" to
            FiveIsland_MemorialPillar_EventScript_Memorial,
    )
