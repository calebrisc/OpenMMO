package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.enums.IVs

/**
 * One of the townsfolk who will swap a monster with you, straight out of the decomp's trade table.
 *
 * [offered] and [requested] are national dex numbers, which is what the rest of this server counts
 * in; the decomp stores its own internal species ids, and for the Hoenn trades the two disagree
 * (Seedot is 298 there and 273 here). Everything else — the nickname, the original trainer and the
 * IVs — is fixed data the games hand out identically to every player, so a traded monster is the
 * same monster for everyone.
 *
 * The level is not in the table on purpose: `CreateInGameTradePokemonInternal` reads it off the
 * monster the player hands over, so the trade is level for level.
 */
internal data class InGameTrade(
    val nickname: String,
    val otName: String,
    val otId: Int,
    val personality: Int,
    val offered: Int,
    val requested: Int,
    private val hp: Int,
    private val attack: Int,
    private val defense: Int,
    private val speed: Int,
    private val spAttack: Int,
    private val spDefense: Int,
) {
  /**
   * A fresh set of IVs each time. [IVs] is a mutable map, so a single shared instance would be the
   * same object on every monster this trade ever handed out.
   */
  fun ivs(): IVs =
      IVs().apply {
        hp = this@InGameTrade.hp
        atk = attack
        def = defense
        spd = speed
        spAtk = spAttack
        spDef = spDefense
      }
}

/**
 * The nine Kanto trades and the four Hoenn ones.
 *
 * IVs are listed in the decomp's own order — HP, attack, defence, speed, special attack, special
 * defence — which is not the order [IVs] takes, so they are named at the call site rather than
 * passed positionally.
 */
internal object InGameTrades {

  // --- Kanto (FireRed) ---

  /** Reyley, in the Route 2 house: MIMIEN the Mr. Mime, for an Abra. */
  val MR_MIME =
      InGameTrade(
          nickname = "MIMIEN",
          otName = "REYLEY",
          otId = 1985,
          personality = 0x00009cae,
          offered = 122,
          requested = 63,
          hp = 20,
          attack = 15,
          defense = 17,
          speed = 24,
          spAttack = 23,
          spDefense = 22,
      )

  /** Dontae, in the Cerulean house: ZYNX the Jynx, for a Poliwhirl. */
  val JYNX =
      InGameTrade(
          nickname = "ZYNX",
          otName = "DONTAE",
          otId = 36728,
          personality = 0x498a2e1d,
          offered = 124,
          requested = 61,
          hp = 18,
          attack = 17,
          defense = 18,
          speed = 22,
          spAttack = 25,
          spDefense = 21,
      )

  /** Saige, at the north Underground Path entrance: MS. NIDO, for a male Nidoran. */
  val NIDORAN =
      InGameTrade(
          nickname = "MS. NIDO",
          otName = "SAIGE",
          otId = 63184,
          personality = 0x4c970b89,
          offered = 29,
          requested = 32,
          hp = 22,
          attack = 18,
          defense = 25,
          speed = 19,
          spAttack = 15,
          spDefense = 22,
      )

  /** Elyssa, in the Vermilion house: CH'DING the Farfetch'd, for a Spearow. */
  val FARFETCHD =
      InGameTrade(
          nickname = "CH'DING",
          otName = "ELYSSA",
          otId = 8810,
          personality = 0x151943d7,
          offered = 83,
          requested = 21,
          hp = 20,
          attack = 25,
          defense = 21,
          speed = 24,
          spAttack = 15,
          spDefense = 20,
      )

  /** Turner, above the Route 11 gate: NINA the Nidorina, for a Nidorino. */
  val NIDORINOA =
      InGameTrade(
          nickname = "NINA",
          otName = "TURNER",
          otId = 13637,
          personality = 0x00eeca15,
          offered = 30,
          requested = 33,
          hp = 22,
          attack = 25,
          defense = 18,
          speed = 19,
          spAttack = 22,
          spDefense = 15,
      )

  /** Haden, above the Route 18 gate: MARC the Lickitung, for a Golduck. */
  val LICKITUNG =
      InGameTrade(
          nickname = "MARC",
          otName = "HADEN",
          otId = 1239,
          personality = 0x451308ab,
          offered = 108,
          requested = 55,
          hp = 24,
          attack = 19,
          defense = 21,
          speed = 15,
          spAttack = 23,
          spDefense = 21,
      )

  /** Clifton, in the Cinnabar lab lounge: ESPHERE the Electrode, for a Raichu. */
  val ELECTRODE =
      InGameTrade(
          nickname = "ESPHERE",
          otName = "CLIFTON",
          otId = 50298,
          personality = 0x06341016,
          offered = 101,
          requested = 26,
          hp = 19,
          attack = 16,
          defense = 18,
          speed = 25,
          spAttack = 25,
          spDefense = 19,
      )

  /** Norma, in the Cinnabar lab lounge: TANGENY the Tangela, for a Venonat. */
  val TANGELA =
      InGameTrade(
          nickname = "TANGENY",
          otName = "NORMA",
          otId = 60042,
          personality = 0x5c77ecfa,
          offered = 114,
          requested = 48,
          hp = 22,
          attack = 17,
          defense = 25,
          speed = 16,
          spAttack = 23,
          spDefense = 20,
      )

  /** Garett, in the Cinnabar lab experiment room: SEELOR the Seel, for a Ponyta. */
  val SEEL =
      InGameTrade(
          nickname = "SEELOR",
          otName = "GARETT",
          otId = 9853,
          personality = 0x482cac89,
          offered = 86,
          requested = 77,
          hp = 24,
          attack = 15,
          defense = 22,
          speed = 16,
          spAttack = 23,
          spDefense = 22,
      )

  // --- Hoenn (Emerald) ---

  /** Kobe, in the Rustboro house: DOTS the Seedot, for a Ralts. */
  val SEEDOT =
      InGameTrade(
          nickname = "DOTS",
          otName = "KOBE",
          otId = 38726,
          personality = 0x84,
          offered = 273,
          requested = 280,
          hp = 5,
          attack = 4,
          defense = 5,
          speed = 4,
          spAttack = 4,
          spDefense = 4,
      )

  /** Roman, in the Fortree house: PLUSES the Plusle, for a Volbeat. */
  val PLUSLE =
      InGameTrade(
          nickname = "PLUSES",
          otName = "ROMAN",
          otId = 73996,
          personality = 0x6f,
          offered = 311,
          requested = 313,
          hp = 4,
          attack = 4,
          defense = 4,
          speed = 5,
          spAttack = 5,
          spDefense = 4,
      )

  /** Skylar, in the Pacifidlog house: SEASOR the Horsea, for a Bagon. */
  val HORSEA =
      InGameTrade(
          nickname = "SEASOR",
          otName = "SKYLAR",
          otId = 46285,
          personality = 0x7f,
          offered = 116,
          requested = 371,
          hp = 5,
          attack = 4,
          defense = 4,
          speed = 4,
          spAttack = 5,
          spDefense = 4,
      )

  /** Isis, in the Battle Frontier lounge: MEOWOW the Meowth, for a Skitty. */
  val MEOWTH =
      InGameTrade(
          nickname = "MEOWOW",
          otName = "ISIS",
          otId = 91481,
          personality = 0x8b,
          offered = 52,
          requested = 315,
          hp = 4,
          attack = 5,
          defense = 4,
          speed = 5,
          spAttack = 4,
          spDefense = 4,
      )
}
