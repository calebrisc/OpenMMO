package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.PartyMemberSelectPacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/** The selection kind the client sends when a party member is picked to be moved. */
private const val SELECT_FOR_SWAP: Byte = 1

/**
 * Reordering the party.
 *
 * The client sends a selection for the monster picked and another for where it should go, and
 * nothing on the server listened for either, so every attempt to move a party member did nothing at
 * all. The pair is completed here and the whole container is sent back, which is how the client
 * learns the new order.
 */
@Singleton
class PartyService @Inject constructor(private val characterStore: CharacterStore) {

  /** The first half of a swap, per character, waiting for its partner. */
  private val pendingSwap = ConcurrentHashMap<Long, Long>()

  fun onMemberSelect(event: PacketEvent<PartyMemberSelectPacket>) {
    val ctx = event.session
    val charId = ctx.attributes[PLAYER_STATE]?.characterId ?: return
    val packet = event.packet

    if (packet.selectionType != SELECT_FOR_SWAP) {
      // Logged rather than dropped silently: the other kinds are still unknown, and the value is
      // what will name them.
      log.info { "char=$charId party selection kind ${packet.selectionType} on ${packet.entityId}" }
      return
    }

    val stored = characterStore.getCharacter(charId) ?: return
    val picked = stored.pokemon.firstOrNull { it.id == packet.entityId }
    if (picked == null) {
      log.info { "char=$charId selected ${packet.entityId}, which is not in their party" }
      pendingSwap.remove(charId)
      return
    }

    val first = pendingSwap.remove(charId)
    if (first == null || first == picked.id) {
      // Nothing to pair it with yet, so hold it until the destination arrives.
      pendingSwap[charId] = picked.id
      return
    }

    val other = stored.pokemon.firstOrNull { it.id == first }
    if (other == null) {
      pendingSwap[charId] = picked.id
      return
    }

    characterStore.updatePokemon(charId, picked.copy(containerSlot = other.containerSlot))
    characterStore.updatePokemon(charId, other.copy(containerSlot = picked.containerSlot))
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId swapped party slots ${picked.containerSlot} and ${other.containerSlot}" }
    sendParty(event, charId)
  }

  /** Forgets a half finished swap, so a fresh session never completes one against a stale pick. */
  fun onDisconnect(charId: Long) {
    pendingSwap.remove(charId)
  }

  private fun sendParty(event: PacketEvent<PartyMemberSelectPacket>, charId: Long) {
    val party =
        characterStore.getCharacter(charId)?.pokemon?.sortedBy { it.containerSlot } ?: return
    event.session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ))
  }
}
