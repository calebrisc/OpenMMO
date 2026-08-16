package de.fiereu.openmmo.pokemon

/** What a species becomes when a stone is used on it. [item] is the generated item's name. */
data class StoneEvolution(val from: Int, val item: String, val into: Int)

/** What a species becomes, and the level it needs. */
data class LevelEvolution(val level: Int, val into: Int)

/**
 * Level-up evolutions, taken from the decomp's evolution table and keyed by national dex id.
 *
 * Only the plain level trigger is here, which is 134 of the 184 the games define. The rest
 * wait on things this server has no notion of yet: beauty, friendship, friendship_day, friendship_night, item, level_atk_eq_def, level_atk_gt_def, level_atk_lt_def, level_cascoon, level_ninjask, level_shedinja, level_silcoon, trade, trade_item.
 * Leaving them out means a monster that should evolve by stone simply does not, rather than
 * evolving at the wrong moment.
 */
object EvolutionTable {
  private val byDexId: Map<Int, LevelEvolution> =
      mapOf(
        1 to LevelEvolution(16, 2),
        2 to LevelEvolution(32, 3),
        4 to LevelEvolution(16, 5),
        5 to LevelEvolution(36, 6),
        7 to LevelEvolution(16, 8),
        8 to LevelEvolution(36, 9),
        10 to LevelEvolution(7, 11),
        11 to LevelEvolution(10, 12),
        13 to LevelEvolution(7, 14),
        14 to LevelEvolution(10, 15),
        16 to LevelEvolution(18, 17),
        17 to LevelEvolution(36, 18),
        19 to LevelEvolution(20, 20),
        21 to LevelEvolution(20, 22),
        23 to LevelEvolution(22, 24),
        27 to LevelEvolution(22, 28),
        29 to LevelEvolution(16, 30),
        32 to LevelEvolution(16, 33),
        41 to LevelEvolution(22, 42),
        43 to LevelEvolution(21, 44),
        46 to LevelEvolution(24, 47),
        48 to LevelEvolution(31, 49),
        50 to LevelEvolution(26, 51),
        52 to LevelEvolution(28, 53),
        54 to LevelEvolution(33, 55),
        56 to LevelEvolution(28, 57),
        60 to LevelEvolution(25, 61),
        63 to LevelEvolution(16, 64),
        66 to LevelEvolution(28, 67),
        69 to LevelEvolution(21, 70),
        72 to LevelEvolution(30, 73),
        74 to LevelEvolution(25, 75),
        77 to LevelEvolution(40, 78),
        79 to LevelEvolution(37, 80),
        81 to LevelEvolution(30, 82),
        84 to LevelEvolution(31, 85),
        86 to LevelEvolution(34, 87),
        88 to LevelEvolution(38, 89),
        92 to LevelEvolution(25, 93),
        96 to LevelEvolution(26, 97),
        98 to LevelEvolution(28, 99),
        100 to LevelEvolution(30, 101),
        104 to LevelEvolution(28, 105),
        109 to LevelEvolution(35, 110),
        111 to LevelEvolution(42, 112),
        116 to LevelEvolution(32, 117),
        118 to LevelEvolution(33, 119),
        129 to LevelEvolution(20, 130),
        138 to LevelEvolution(40, 139),
        140 to LevelEvolution(40, 141),
        147 to LevelEvolution(30, 148),
        148 to LevelEvolution(55, 149),
        152 to LevelEvolution(16, 153),
        153 to LevelEvolution(32, 154),
        155 to LevelEvolution(14, 156),
        156 to LevelEvolution(36, 157),
        158 to LevelEvolution(18, 159),
        159 to LevelEvolution(30, 160),
        161 to LevelEvolution(15, 162),
        163 to LevelEvolution(20, 164),
        165 to LevelEvolution(18, 166),
        167 to LevelEvolution(22, 168),
        170 to LevelEvolution(27, 171),
        177 to LevelEvolution(25, 178),
        179 to LevelEvolution(15, 180),
        180 to LevelEvolution(30, 181),
        183 to LevelEvolution(18, 184),
        187 to LevelEvolution(18, 188),
        188 to LevelEvolution(27, 189),
        194 to LevelEvolution(20, 195),
        204 to LevelEvolution(31, 205),
        209 to LevelEvolution(23, 210),
        216 to LevelEvolution(30, 217),
        218 to LevelEvolution(38, 219),
        220 to LevelEvolution(33, 221),
        223 to LevelEvolution(25, 224),
        228 to LevelEvolution(24, 229),
        231 to LevelEvolution(25, 232),
        238 to LevelEvolution(30, 124),
        239 to LevelEvolution(30, 125),
        240 to LevelEvolution(30, 126),
        246 to LevelEvolution(30, 247),
        247 to LevelEvolution(55, 248),
        252 to LevelEvolution(16, 253),
        253 to LevelEvolution(36, 254),
        255 to LevelEvolution(16, 256),
        256 to LevelEvolution(36, 257),
        258 to LevelEvolution(16, 259),
        259 to LevelEvolution(36, 260),
        261 to LevelEvolution(18, 262),
        263 to LevelEvolution(20, 264),
        266 to LevelEvolution(10, 267),
        268 to LevelEvolution(10, 269),
        270 to LevelEvolution(14, 271),
        273 to LevelEvolution(14, 274),
        276 to LevelEvolution(22, 277),
        278 to LevelEvolution(25, 279),
        280 to LevelEvolution(20, 281),
        281 to LevelEvolution(30, 282),
        283 to LevelEvolution(22, 284),
        285 to LevelEvolution(23, 286),
        287 to LevelEvolution(18, 288),
        288 to LevelEvolution(36, 289),
        293 to LevelEvolution(20, 294),
        294 to LevelEvolution(40, 295),
        296 to LevelEvolution(24, 297),
        304 to LevelEvolution(32, 305),
        305 to LevelEvolution(42, 306),
        307 to LevelEvolution(37, 308),
        309 to LevelEvolution(26, 310),
        316 to LevelEvolution(26, 317),
        318 to LevelEvolution(30, 319),
        320 to LevelEvolution(40, 321),
        322 to LevelEvolution(33, 323),
        325 to LevelEvolution(32, 326),
        328 to LevelEvolution(35, 329),
        329 to LevelEvolution(45, 330),
        331 to LevelEvolution(32, 332),
        333 to LevelEvolution(35, 334),
        339 to LevelEvolution(30, 340),
        341 to LevelEvolution(30, 342),
        343 to LevelEvolution(36, 344),
        345 to LevelEvolution(40, 346),
        347 to LevelEvolution(40, 348),
        353 to LevelEvolution(37, 354),
        355 to LevelEvolution(37, 356),
        360 to LevelEvolution(15, 202),
        361 to LevelEvolution(42, 362),
        363 to LevelEvolution(32, 364),
        364 to LevelEvolution(44, 365),
        371 to LevelEvolution(30, 372),
        372 to LevelEvolution(50, 373),
        374 to LevelEvolution(20, 375),
        375 to LevelEvolution(45, 376),
      )

  /** The evolution this species reaches at [level], or null if it does not evolve then. */
  fun at(dexId: Int, level: Int): LevelEvolution? =
      byDexId[dexId]?.takeIf { level >= it.level }

  fun has(dexId: Int): Boolean = byDexId.containsKey(dexId)

  /**
   * Evolutions a stone brings on. Keyed by the item's generated name rather than its id, since the
   * ids come from a build step and the names are what the decomp and this table agree on.
   */
  private val byStone: List<StoneEvolution> =
      listOf(
        StoneEvolution(37, "FIRE_STONE", 38),
        StoneEvolution(58, "FIRE_STONE", 59),
        StoneEvolution(133, "FIRE_STONE", 136),
        StoneEvolution(44, "LEAF_STONE", 45),
        StoneEvolution(70, "LEAF_STONE", 71),
        StoneEvolution(102, "LEAF_STONE", 103),
        StoneEvolution(274, "LEAF_STONE", 275),
        StoneEvolution(30, "MOON_STONE", 31),
        StoneEvolution(33, "MOON_STONE", 34),
        StoneEvolution(35, "MOON_STONE", 36),
        StoneEvolution(39, "MOON_STONE", 40),
        StoneEvolution(300, "MOON_STONE", 301),
        StoneEvolution(44, "SUN_STONE", 182),
        StoneEvolution(191, "SUN_STONE", 192),
        StoneEvolution(25, "THUNDER_STONE", 26),
        StoneEvolution(133, "THUNDER_STONE", 135),
        StoneEvolution(61, "WATER_STONE", 62),
        StoneEvolution(90, "WATER_STONE", 91),
        StoneEvolution(120, "WATER_STONE", 121),
        StoneEvolution(133, "WATER_STONE", 134),
        StoneEvolution(271, "WATER_STONE", 272),
      )

  /** What [dexId] becomes if [itemName] is used on it, or null if that stone does nothing to it. */
  fun byStone(dexId: Int, itemName: String): Int? =
      byStone.firstOrNull { it.from == dexId && it.item == itemName }?.into

  val stoneCount: Int
    get() = byStone.size

  val size: Int
    get() = byDexId.size
}
