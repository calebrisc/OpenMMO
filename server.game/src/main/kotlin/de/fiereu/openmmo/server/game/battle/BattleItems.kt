package de.fiereu.openmmo.server.game.battle

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
      )

  fun effectOf(item: ItemDef): BattleItemEffect? = EFFECTS[item]

  /** How much [effect] would actually restore, given where the monster is now. */
  fun healAmount(effect: BattleItemEffect, currentHp: Int, maxHp: Int): Int =
      if (effect.heal >= FULL_HEAL_AMOUNT) maxHp - currentHp
      else minOf(effect.heal, maxHp - currentHp)
}
