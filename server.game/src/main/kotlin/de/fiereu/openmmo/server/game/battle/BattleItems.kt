package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.PokemonStat
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.items.ItemDef
import de.fiereu.openmmo.items.generated.Items

/** What using an item in a battle does to the monster it is used on. */
data class BattleItemEffect(
    /** Hit points restored. [FULL_HEAL_AMOUNT] restores everything. */
    val heal: Int = 0,
    /** The conditions this clears. Empty clears nothing. */
    val cures: Set<StatusCondition> = emptySet(),
) {
  val healsAnything: Boolean
    get() = heal > 0

  val curesAnything: Boolean
    get() = cures.isNotEmpty()
}

/**
 * The in-battle items, with the Gen 3 amounts.
 *
 * Item data is generated with only a name and a price, so what an item does is not in it and has to
 * live here. Anything absent is simply not usable in a battle yet, which is reported rather than
 * silently doing nothing.
 */
object BattleItems {

  const val FULL_HEAL_AMOUNT = Int.MAX_VALUE

  private val ALL_STATUS =
      setOf(
          StatusCondition.SLEEP,
          StatusCondition.POISON,
          StatusCondition.BURN,
          StatusCondition.FREEZE,
          StatusCondition.PARALYSIS,
          StatusCondition.TOXIC,
      )

  private val EFFECTS: Map<ItemDef, BattleItemEffect> =
      mapOf(
          Items.POTION to BattleItemEffect(heal = 20),
          Items.SUPER_POTION to BattleItemEffect(heal = 50),
          Items.HYPER_POTION to BattleItemEffect(heal = 200),
          Items.MAX_POTION to BattleItemEffect(heal = FULL_HEAL_AMOUNT),
          Items.FULL_RESTORE to BattleItemEffect(heal = FULL_HEAL_AMOUNT, cures = ALL_STATUS),
          Items.FRESH_WATER to BattleItemEffect(heal = 50),
          Items.SODA_POP to BattleItemEffect(heal = 60),
          Items.LEMONADE to BattleItemEffect(heal = 80),
          Items.MOOMOO_MILK to BattleItemEffect(heal = 100),
          Items.ANTIDOTE to
              BattleItemEffect(cures = setOf(StatusCondition.POISON, StatusCondition.TOXIC)),
          Items.BURN_HEAL to BattleItemEffect(cures = setOf(StatusCondition.BURN)),
          Items.ICE_HEAL to BattleItemEffect(cures = setOf(StatusCondition.FREEZE)),
          Items.AWAKENING to BattleItemEffect(cures = setOf(StatusCondition.SLEEP)),
          Items.FULL_HEAL to BattleItemEffect(cures = ALL_STATUS),
          // Paralysis had no cure at all: the marts sell this and it did nothing.
          Items.PARLYZ_HEAL to BattleItemEffect(cures = setOf(StatusCondition.PARALYSIS)),
          Items.ENERGYPOWDER to BattleItemEffect(heal = 50),
          Items.ENERGY_ROOT to BattleItemEffect(heal = 200),
          Items.HEAL_POWDER to BattleItemEffect(cures = ALL_STATUS),
          Items.LAVA_COOKIE to BattleItemEffect(cures = ALL_STATUS),
          Items.OLD_GATEAU to BattleItemEffect(cures = ALL_STATUS),
          Items.BERRY_JUICE to BattleItemEffect(heal = 20),
      )

  /** What a revive brings a fainted monster back with, as a fraction of its maximum. */
  val REVIVES: Map<ItemDef, Int> =
      mapOf(
          Items.REVIVE to 2,
          Items.REVIVAL_HERB to 2,
          Items.MAX_REVIVE to 1,
          Items.SACRED_ASH to 1,
      )

  /** Ten effort points in one stat, which is what a vitamin is. */
  val VITAMINS: Map<ItemDef, PokemonStat> =
      mapOf(
          Items.HP_UP to PokemonStat.HP,
          Items.PROTEIN to PokemonStat.ATTACK,
          Items.IRON to PokemonStat.DEFENSE,
          Items.CARBOS to PokemonStat.SPEED,
          Items.CALCIUM to PokemonStat.SP_ATTACK,
          Items.ZINC to PokemonStat.SP_DEFENSE,
      )

  /** How much pp a restorative gives back, and whether it reaches every move. */
  val PP_RESTORES: Map<ItemDef, Pair<Int, Boolean>> =
      mapOf(
          Items.ETHER to (10 to false),
          Items.MAX_ETHER to (FULL_HEAL_AMOUNT to false),
          Items.ELIXIR to (10 to true),
          Items.MAX_ELIXIR to (FULL_HEAL_AMOUNT to true),
      )

  /** How many steps a repel keeps wild monsters away for. */
  val REPELS: Map<ItemDef, Int> =
      mapOf(
          Items.REPEL to 100,
          Items.SUPER_REPEL to 200,
          Items.MAX_REPEL to 250,
      )

  fun effectOf(item: ItemDef): BattleItemEffect? = EFFECTS[item]

  /** How much [effect] would actually restore, given where the monster is now. */
  fun healAmount(effect: BattleItemEffect, currentHp: Int, maxHp: Int): Int =
      if (effect.heal >= FULL_HEAL_AMOUNT) maxHp - currentHp
      else minOf(effect.heal, maxHp - currentHp)
}
