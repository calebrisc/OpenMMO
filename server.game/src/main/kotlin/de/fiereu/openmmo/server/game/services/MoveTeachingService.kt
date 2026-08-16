package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.PartyMemberSelectPacket
import de.fiereu.openmmo.net.game.packets.SelectSinglePokemonPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnPromptPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnReplyPacket
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/** A move waiting for its player to say which monster should learn it. */
private data class PendingTeach(
    val moveId: Int,
    val moveName: String,
    val from: String,
    /** Set once a monster actually learns it, so a tutor is spent by teaching, not by offering. */
    val spendFlag: String?,
)

/** A monster with a full moveset, waiting for its player to say what to drop. */
private data class PendingChoice(
    val charId: Long,
    val moveId: Int,
    val moveName: String,
    val spendFlag: String?,
)

/**
 * Teaching a move outside a battle: what a tutor does, and what a machine does.
 *
 * Neither worked. Both tutors on Route 4 were stubs and no machine had any effect at all, so every
 * TM in the game was a keepsake -- including the ones gym leaders hand over for beating them.
 *
 * The client owns the part that was missing. There is no decoded way to make it open its party for
 * a choice, so the offer is made, the player opens the party themselves and picks, and the pick
 * arrives on the packet the client already sends for a swap. A monster with a free slot simply
 * learns; a full one is asked what to forget through the same prompt a level up uses, which has
 * worked all along.
 */
@Singleton
class MoveTeachingService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val moves: MoveRegistry,
    private val moveLearner: MoveLearner,
) {

  private val pendingTeach = ConcurrentHashMap<Long, PendingTeach>()
  private val pendingChoice = ConcurrentHashMap<Long, PendingChoice>()

  /** True when this player is mid-offer, so the party screen means a choice rather than a swap. */
  fun isOffering(charId: Long): Boolean = pendingTeach.containsKey(charId)

  /**
   * Offers [moveId] to the player, who answers by picking a party member.
   *
   * Refuses when the move is not one this server knows, rather than promising something that cannot
   * be delivered once a monster has been chosen.
   */
  fun offer(
      session: SessionContext,
      charId: Long,
      moveId: Int,
      from: String,
      spendFlag: String? = null,
  ): Boolean {
    val def = moves.get(moveId)
    if (def == null) {
      log.warn { "char=$charId was offered move $moveId from $from, which is not in the registry" }
      return false
    }
    pendingTeach[charId] = PendingTeach(moveId, def.name, from, spendFlag)
    session.send(notice("Which POKéMON should learn ${def.name}? Open your party and choose one."))
    return true
  }

  /** Drops an offer that was never taken up, so a party screen means a swap again. */
  fun cancel(charId: Long) {
    pendingTeach.remove(charId)
  }

  /**
   * A party member was picked. True when it answered an offer, so the caller leaves it alone.
   *
   * Anything the player cannot be taught ends the offer rather than leaving it hanging over the
   * next thing they do with their party.
   */
  /**
   * Teaches [moveId] to a named monster, without asking which one first.
   *
   * A machine used from the bag names the monster in the very same packet, so there is nothing left
   * to ask: the client has already had the player pick one and is not going to send a party
   * selection afterwards. Waiting for one is why every TM and HM did nothing at all -- the offer
   * was made and then sat there for ever.
   *
   * False when [monsterId] is not one of theirs, which is the caller's cue to ask the long way
   * round instead.
   */
  suspend fun teachDirectly(
      session: SessionContext,
      charId: Long,
      monsterId: Long,
      moveId: Int,
      from: String,
      spendFlag: String? = null,
  ): Boolean {
    val def = moves.get(moveId) ?: return false
    val monster =
        characterStore.getCharacter(charId)?.pokemon?.firstOrNull { it.id == monsterId }
            ?: return false
    pendingTeach.remove(charId)
    return teach(session, charId, monster, PendingTeach(moveId, def.name, from, spendFlag))
  }

  suspend fun onPartySelect(event: PacketEvent<PartyMemberSelectPacket>): Boolean =
      onMonsterPicked(event.session, event.packet.entityId, "0x11 party member")

  /**
   * The other packet a party screen answers with, carrying one monster and nothing else.
   *
   * A tutor's offer was only ever listening for 0x11, and the player picking a monster did nothing
   * at all: the offer hung there exactly the way a machine's used to. Both are handled now, and
   * both say which arrived, so the log settles which one this client actually sends.
   */
  suspend fun onSingleSelect(event: PacketEvent<SelectSinglePokemonPacket>): Boolean =
      onMonsterPicked(event.session, event.packet.entityId, "0x9D single monster")

  private suspend fun onMonsterPicked(
      session: SessionContext,
      entityId: Long,
      via: String,
  ): Boolean {
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return false
    val offer = pendingTeach[charId] ?: return false
    val stored = characterStore.getCharacter(charId) ?: return false
    val monster = stored.pokemon.firstOrNull { it.id == entityId }
    if (monster == null) {
      log.info { "char=$charId picked $entityId on $via, which is not in their party" }
      return false
    }
    log.info { "char=$charId answered the ${offer.moveName} offer on $via" }
    pendingTeach.remove(charId)
    return teach(session, charId, monster, offer)
  }

  private suspend fun teach(
      session: SessionContext,
      charId: Long,
      monster: Pokemon,
      offer: PendingTeach,
  ): Boolean {
    val known = monster.moves.toMutableList()
    if (known.any { it.id.toInt() == offer.moveId }) {
      session.send(notice("It already knows ${offer.moveName}."))
      return true
    }
    val free = known.indexOfFirst { it.id.toInt() == 0 }
    if (free < 0 && known.size >= MAX_MOVE_SLOTS) {
      // The prompt a level up uses. The reply comes back through onMoveLearnReply below.
      pendingChoice[monster.id] =
          PendingChoice(charId, offer.moveId, offer.moveName, offer.spendFlag)
      session.send(MoveLearnPromptPacket(monster.id, listOf(offer.moveId.toShort())))
      return true
    }
    val pp = moves.get(offer.moveId)?.pp?.toByte() ?: 0
    if (free >= 0) known[free] = PokemonMove(offer.moveId.toShort(), pp)
    else known += PokemonMove(offer.moveId.toShort(), pp)
    characterStore.updatePokemon(charId, monster.copy(moves = known))
    characterStore.flushCharacterAsync(charId)
    offer.spendFlag?.let { characterStore.setStoryFlag(charId, it) }
    log.info { "char=$charId taught ${offer.moveName} from ${offer.from} to ${monster.id}" }
    session.send(notice("It learned ${offer.moveName}!"))
    return true
  }

  /**
   * The answer to "which move should it forget". True when it belonged to a teach rather than to a
   * battle, so the battle's own handler leaves it alone.
   */
  fun onMoveLearnReply(event: PacketEvent<MoveLearnReplyPacket>): Boolean {
    val session = event.session
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return false
    val reply = event.packet
    val choice = pendingChoice[reply.entityId] ?: return false
    if (choice.charId != charId) return false
    pendingChoice.remove(reply.entityId)

    val monster =
        characterStore.getCharacter(charId)?.pokemon?.firstOrNull { it.id == reply.entityId }
            ?: return true
    val known = monster.moves.toMutableList()
    if (!moveLearner.apply(known, reply.moveIds, listOf(choice.moveId.toShort()))) {
      log.warn {
        "char=$charId answered ${reply.entityId} with an impossible set: ${reply.moveIds}"
      }
      return true
    }
    if (known == monster.moves) {
      session.send(notice("It did not learn ${choice.moveName}."))
      return true
    }
    characterStore.updatePokemon(charId, monster.copy(moves = known))
    characterStore.flushCharacterAsync(charId)
    choice.spendFlag?.let { characterStore.setStoryFlag(charId, it) }
    log.info { "char=$charId taught ${choice.moveName} to ${reply.entityId} over another move" }
    session.send(notice("It learned ${choice.moveName}!"))
    return true
  }
}
