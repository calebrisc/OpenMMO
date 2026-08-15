package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.MAX_PARTY_SIZE
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.PcBoxRenamePacket
import de.fiereu.openmmo.net.game.packets.PcBoxStorePacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.StorageBoxClosePacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}


/**
 * Moving monsters between the party and the PC.
 *
 * The client was already sent both containers at login and drew them, but nothing listened for the
 * packet that moves one, so every deposit and withdrawal snapped back. A monster lives in one of two
 * lists rather than carrying a label, so a move is a removal and an addition rather than an edit.
 */
@Singleton
class PcBoxService @Inject constructor(private val characterStore: CharacterStore) {

  /** The containers a player is allowed to move a monster into by hand. */
  private val movable = setOf(PokemonContainer.PARTY, PokemonContainer.PC)

  suspend fun onStore(event: PacketEvent<PcBoxStorePacket>) {
    val ctx = event.session
    val charId = ctx.attributes[PLAYER_STATE]?.characterId ?: return
    val packet = event.packet
    val destination = PokemonContainer.entries.getOrNull(packet.boxId.toInt())
    if (destination == null || destination !in movable) {
      log.info { "char=$charId tried to store into container ${packet.boxId}, which is not movable" }
      return
    }

    val stored = characterStore.getCharacter(charId) ?: return
    val inParty = stored.pokemon.firstOrNull { it.id == packet.pokemonId }
    val inPc = stored.pcStorage.firstOrNull { it.id == packet.pokemonId }
    val monster = inParty ?: inPc
    if (monster == null) {
      log.info { "char=$charId tried to store ${packet.pokemonId}, which they do not own" }
      return
    }

    val leavingParty = inParty != null && destination == PokemonContainer.PC
    if (leavingParty && stored.pokemon.size <= 1) {
      ctx.send(notice("You cannot put your last monster away."))
      resend(ctx, charId)
      return
    }
    if (destination == PokemonContainer.PARTY &&
        inPc != null &&
        stored.pokemon.size >= MAX_PARTY_SIZE) {
      ctx.send(notice("Your party is full."))
      resend(ctx, charId)
      return
    }

    // Within the same container this is only a reorder, which the slot alone carries.
    if (inParty != null && destination == PokemonContainer.PARTY) {
      characterStore.updatePokemon(charId, monster.copy(containerSlot = packet.slotIndex.toShort()))
      characterStore.flushCharacterAsync(charId)
      resend(ctx, charId)
      return
    }
    if (inPc != null && destination == PokemonContainer.PC) {
      characterStore.updatePokemon(charId, monster.copy(containerSlot = packet.slotIndex.toShort()))
      characterStore.flushCharacterAsync(charId)
      resend(ctx, charId)
      return
    }

    val taken = characterStore.removePokemon(charId, monster.id)
    if (taken == null) {
      ctx.send(notice("That could not be moved."))
      resend(ctx, charId)
      return
    }
    val slot = packet.slotIndex.toShort().takeIf { it >= 0 } ?: freeSlot(charId, destination)
    val moved = taken.copy(container = destination, containerSlot = slot)
    if (!characterStore.addPokemon(charId, moved)) {
      // Put it back exactly where it was rather than leaving it in neither list.
      characterStore.addPokemon(charId, taken)
      ctx.send(notice("That could not be moved."))
      resend(ctx, charId)
      return
    }
    characterStore.flushCharacterAsync(charId)
    log.info { "char=$charId moved ${moved.id} to $destination slot $slot" }
    resend(ctx, charId)
  }

  /** Closing the box needs no state change, but it is answered so the client is not left waiting. */
  fun onClose(event: PacketEvent<StorageBoxClosePacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    resend(event.session, charId)
  }

  /**
   * Box names are accepted and logged. Nothing sends a name back to the client yet, so storing one
   * would be state no screen could ever show.
   */
  fun onRename(event: PacketEvent<PcBoxRenamePacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    log.info { "char=$charId renamed box ${event.packet.boxIndex} to ${event.packet.newName}" }
  }

  private fun freeSlot(charId: Long, container: PokemonContainer): Short {
    val stored = characterStore.getCharacter(charId) ?: return 0
    val used =
        if (container == PokemonContainer.PC) stored.pcStorage.map { it.containerSlot }
        else stored.pokemon.map { it.containerSlot }
    return (generateSequence(0) { it + 1 }.first { it.toShort() !in used }).toShort()
  }

  /** Both containers, so the client's two panes agree with the server after any move. */
  private fun resend(ctx: SessionContext, charId: Long) {
    val stored = characterStore.getCharacter(charId) ?: return
    ctx.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = stored.pokemon.sortedBy { it.containerSlot },
        ))
    ctx.send(
        PokemonContainerPacket(
            container = PokemonContainer.PC,
            hasChange = true,
            delete = false,
            pokemon = stored.pcStorage.sortedBy { it.containerSlot },
        ))
  }
}
