package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VermilionCity_PokemonFanClub
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

/**
 * The chairman's story, which is what the bike voucher is really for.
 *
 * There is no voucher item in this game's table -- it is a Gen 5 table and the voucher is not in it
 * -- so the flag alone carries it, and the bike shop reads that flag. Both ends of this were stubs,
 * so the bicycle could not be got at all.
 */
internal object VermilionCity_PokemonFanClub_EventScript_Chairman : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_GOT_BIKE_VOUCHER)) {
      ctx.say(VermilionCity_PokemonFanClub.DidntComeToSeeAboutMonsAgain)
      return
    }
    if (!ctx.askYesNo(VermilionCity_PokemonFanClub.DidYouComeToHearAboutMyMons)) {
      ctx.say(VermilionCity_PokemonFanClub.ComeBackToHearStory)
      return
    }
    ctx.say(VermilionCity_PokemonFanClub.ChairmansStory)
    // The flag is the voucher here, so it lands before the two lines that follow it.
    ctx.setFlag(KantoFlags.FLAG_GOT_BIKE_VOUCHER)
    ctx.say(VermilionCity_PokemonFanClub.ReceivedBikeVoucherFromChairman)
    ctx.say(VermilionCity_PokemonFanClub.ExplainBikeVoucher)
  }
}

internal object VermilionCity_PokemonFanClub_EventScript_WorkerF : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_SYS_GAME_CLEAR)) {
      ctx.say(VermilionCity_PokemonFanClub.ChairmanReallyAdoresHisMons)
      return
    }
    ctx.say(VermilionCity_PokemonFanClub.ChairmanVeryVocalAboutPokemon)
  }
}

internal object VermilionCity_PokemonFanClub_EventScript_Pikachu : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(VermilionCity_PokemonFanClub.Pikachu)
}

internal object VermilionCity_PokemonFanClub_EventScript_Seel : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(VermilionCity_PokemonFanClub.Seel)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set SPOKE_TO_FAT_MAN_LAST, VermilionCity_PokemonFanClub_EventScript_WomanSpokeToFatMan
 * msgbox VermilionCity_PokemonFanClub_Text_AdoreMySeel
 * closemessage
 * applymovement LOCALID_POKEMON_FAN_CLUB_WOMAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag SPOKE_TO_WOMAN_LAST
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set SPOKE_TO_WOMAN_LAST, VermilionCity_PokemonFanClub_EventScript_FatManSpokeToWoman
 * msgbox VermilionCity_PokemonFanClub_Text_AdmirePikachusTail
 * closemessage
 * applymovement LOCALID_POKEMON_FAN_CLUB_FAT_MAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag SPOKE_TO_FAT_MAN_LAST
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_FatMan")
}

internal object VermilionCity_PokemonFanClub_EventScript_RulesSign1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(VermilionCity_PokemonFanClub.ListenPolitelyToOtherTrainers)
}

internal object VermilionCity_PokemonFanClub_EventScript_RulesSign2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(VermilionCity_PokemonFanClub.SomeoneBragsBragBack)
}

internal val VermilionCity_PokemonFanClubScripts: Map<String, Script> =
    mapOf(
        "VermilionCity_PokemonFanClub_EventScript_Chairman" to
            VermilionCity_PokemonFanClub_EventScript_Chairman,
        "VermilionCity_PokemonFanClub_EventScript_WorkerF" to
            VermilionCity_PokemonFanClub_EventScript_WorkerF,
        "VermilionCity_PokemonFanClub_EventScript_Pikachu" to
            VermilionCity_PokemonFanClub_EventScript_Pikachu,
        "VermilionCity_PokemonFanClub_EventScript_Seel" to
            VermilionCity_PokemonFanClub_EventScript_Seel,
        "VermilionCity_PokemonFanClub_EventScript_Woman" to
            VermilionCity_PokemonFanClub_EventScript_Woman,
        "VermilionCity_PokemonFanClub_EventScript_FatMan" to
            VermilionCity_PokemonFanClub_EventScript_FatMan,
        "VermilionCity_PokemonFanClub_EventScript_RulesSign1" to
            VermilionCity_PokemonFanClub_EventScript_RulesSign1,
        "VermilionCity_PokemonFanClub_EventScript_RulesSign2" to
            VermilionCity_PokemonFanClub_EventScript_RulesSign2,
    )
