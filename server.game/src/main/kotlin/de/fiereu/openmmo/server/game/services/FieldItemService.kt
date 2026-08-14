package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.net.game.packets.DialogOptionPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleItems
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/**
 * Using an item from the bag outside a battle.
 *
 * The client asks with what was read as a dialog option: the id is the item and the entity, when
 * there is one, is the monster it was used on. Nothing handled it, so every potion taken in the
 * overworld did nothing and was not consumed, and so did every press of a bike.
 */
@Singleton
class FieldItemService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val items: ItemRegistry,
    private val species: SpeciesRegistry,
    private val battles: BattleService,
) {

  suspend fun onUseItem(event: PacketEvent<DialogOptionPacket>) {
    val ctx = event.session
    val charId = ctx.attributes[PLAYER_STATE]?.characterId ?: return
    val packet = event.packet
    val item = items.get(packet.optionId)
    val stored = characterStore.getCharacter(charId) ?: return

    // A battle has its own item handling, including throwing balls.
    if (battles.inBattle(charId)) return

    if (item == null) {
      log.info { "char=$charId used unknown item id ${packet.optionId}" }
      return
    }
    val held = stored.items[packet.optionId] ?: 0
    if (held <= 0) {
      log.info { "char=$charId used ${item.name} without holding it" }
      return
    }

    val effect = BattleItems.effectOf(item)
    if (effect == null) {
      // The bike lands here: it is a real item with a real effect in the games and no model on this
      // server yet, so say so rather than leaving the player pressing a key that does nothing.
      log.info { "char=$charId used ${item.name} (id ${packet.optionId}), which is not modelled" }
      ctx.send(notice("${item.name} does not do anything yet."))
      return
    }

    val target =
        stored.pokemon.firstOrNull { it.id == packet.entityId } ?: stored.pokemon.firstOrNull()
    if (target == null) {
      ctx.send(notice("You have nothing to use that on."))
      return
    }
    val definition = species.get(target.dexId)
    if (definition == null) {
      ctx.send(notice("That monster is not covered by the battle data yet."))
      return
    }
    val maxHp = StatCalculator.computeAll(definition, target).hp
    val healed = BattleItems.healAmount(effect, target.hp.toInt(), maxHp)
    val cured = effect.cures.contains(target.status) && target.status.isSet
    if (healed <= 0 && !cured) {
      ctx.send(notice("It would have no effect."))
      return
    }

    val updated =
        target.copy(
            hp = (target.hp + healed).toShort(),
            status = if (cured) StatusCondition.NONE else target.status,
        )
    characterStore.updatePokemon(charId, updated)
    characterStore.addItem(charId, packet.optionId, -1)
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId used ${item.name} on ${target.id}: healed=$healed cured=$cured" }

    val party = characterStore.getCharacter(charId)?.pokemon?.toList() ?: return
    ctx.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ))
  }
}
