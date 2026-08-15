package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.TradeActionPacket
import de.fiereu.openmmo.net.game.packets.TradeListEntryPacket
import de.fiereu.openmmo.net.game.packets.TradeSelectMonPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private val log = KotlinLogging.logger {}

/** One side of a trade in progress. */
private class TradeSeat(val charId: Long, val session: SessionContext, val name: String) {
  /** The monster this player has put up, by its id. */
  var offered: Long? = null
  var confirmed: Boolean = false
}

/** Two players exchanging one monster each. */
private class TradeSession(val a: TradeSeat, val b: TradeSeat) {
  val lock = Mutex()

  /**
   * Set once the exchange has begun. The exchange itself cannot run under the lock because it
   * suspends on the store, so without this a second confirm arriving in that window would start a
   * second exchange over the same two monsters.
   */
  var completing: Boolean = false

  fun other(charId: Long): TradeSeat = if (a.charId == charId) b else a

  fun seat(charId: Long): TradeSeat? =
      when (charId) {
        a.charId -> a
        b.charId -> b
        else -> null
      }

  /** Any change to what is on the table withdraws both confirmations. */
  fun unconfirm() {
    a.confirmed = false
    b.confirmed = false
  }
}

/**
 * Trading monsters between two players.
 *
 * Selecting is done in the client's own trade window, which is what its select packet carries, but
 * the exchange itself is committed from chat. The window's action byte arrives as a single value on
 * every press and nothing tells confirm from cancel, and a wrong reading of it would hand somebody's
 * monster away with no way back. A typed word cannot be misread.
 */
@Singleton
class TradeService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val sessionRegistry: SessionRegistry,
) {

  /** Pending offers, keyed by the player who owes an answer. */
  private val invites = ConcurrentHashMap<Long, Long>()

  /** Running trades, with both players indexed onto the one session. */
  private val trades = ConcurrentHashMap<Long, TradeSession>()

  fun describe(charId: Long): String {
    val trade = trades[charId]
    if (trade != null) {
      val me = trade.seat(charId)!!
      val them = trade.other(charId)
      return "Trading with ${them.name}. " +
          "You offer ${describeOffer(me)}, they offer ${describeOffer(them)}. " +
          "/trade offer <slot>, /trade confirm, /trade cancel."
    }
    val from = invites[charId] ?: return "Nobody is trading with you. /trade <name> to ask."
    val name = characterStore.getCharacter(from)?.info?.name ?: "Somebody"
    return "$name wants to trade. /trade accept or /trade decline."
  }

  private fun describeOffer(seat: TradeSeat): String {
    val id = seat.offered ?: return "nothing"
    val mon = characterStore.getCharacter(seat.charId)?.pokemon?.firstOrNull { it.id == id }
    val name = mon?.nickname?.takeIf { it.isNotBlank() } ?: mon?.let { "#${it.dexId}" } ?: "nothing"
    return name + if (seat.confirmed) " (confirmed)" else ""
  }

  fun invite(charId: Long, targetName: String): String {
    if (trades.containsKey(charId)) return "You are already trading. /trade cancel first."
    val target = characterStore.findCachedByName(targetName) ?: return "$targetName is not online."
    val targetId = target.info.id
    if (targetId == charId) return "You cannot trade with yourself."
    if (trades.containsKey(targetId)) return "${target.info.name} is already trading."
    sessionRegistry.getByCharacterId(targetId) ?: return "${target.info.name} is not online."
    val myName = characterStore.getCharacter(charId)?.info?.name ?: return "You are not in world."

    invites[targetId] = charId
    sessionRegistry
        .getByCharacterId(targetId)
        ?.send(notice("$myName wants to trade. Type /trade accept or /trade decline."))
    return "Trade request sent to ${target.info.name}."
  }

  fun accept(session: SessionContext, charId: Long): String {
    val fromId = invites.remove(charId) ?: return "Nobody has asked to trade."
    if (trades.containsKey(charId) || trades.containsKey(fromId)) return "That trade is no longer open."
    val fromSession = sessionRegistry.getByCharacterId(fromId) ?: return "They are no longer online."
    val from = characterStore.getCharacter(fromId) ?: return "They are no longer here."
    val me = characterStore.getCharacter(charId) ?: return "You are not in world."

    val trade =
        TradeSession(
            TradeSeat(fromId, fromSession, from.info.name),
            TradeSeat(charId, session, me.info.name),
        )
    trades[fromId] = trade
    trades[charId] = trade
    val opening =
        notice("Trade open. /trade offer <party slot 1-6>, then /trade confirm. /trade cancel ends it.")
    fromSession.send(notice("${me.info.name} accepted. $TRADE_HELP"))
    session.send(notice("Trading with ${from.info.name}. $TRADE_HELP"))
    fromSession.send(opening)
    session.send(opening)
    log.info { "Trade opened between ${from.info.name} and ${me.info.name}" }
    return "Trade started."
  }

  fun decline(charId: Long): String {
    val fromId = invites.remove(charId) ?: return "Nobody has asked to trade."
    val me = characterStore.getCharacter(charId)?.info?.name ?: "They"
    sessionRegistry.getByCharacterId(fromId)?.send(notice("$me declined the trade."))
    return "Trade declined."
  }

  /** Puts the monster in [partySlot], counted from one, on the table. */
  suspend fun offer(charId: Long, partySlot: Int): String {
    val trade = trades[charId] ?: return "You are not trading."
    val seat = trade.seat(charId) ?: return "You are not trading."
    val party = characterStore.getCharacter(charId)?.pokemon ?: return "You are not in world."
    val mon =
        party.sortedBy { it.containerSlot }.getOrNull(partySlot - 1)
            ?: return "You have nothing in slot $partySlot."
    if (party.size <= 1) return "You cannot trade away your last monster."

    trade.lock.withLock {
      seat.offered = mon.id
      // Anything new on the table means both sides have to agree to it again.
      trade.unconfirm()
    }
    val them = trade.other(charId)
    // The other player's window is told what is on offer, which is what it draws.
    them.session.send(TradeListEntryPacket(mon))
    them.session.send(notice("${seat.name} offers ${monLabel(mon)}. Confirmations reset."))
    return "You offer ${monLabel(mon)}. /trade confirm when ready."
  }

  suspend fun confirm(charId: Long): String {
    val trade = trades[charId] ?: return "You are not trading."
    val seat = trade.seat(charId) ?: return "You are not trading."
    val them = trade.other(charId)
    trade.lock.withLock {
      if (seat.offered == null || them.offered == null) {
        return "Both of you have to offer something first."
      }
      if (trade.completing) return "The trade is already going through."
      seat.confirmed = true
      if (!them.confirmed) {
        them.session.send(notice("${seat.name} confirmed. /trade confirm to complete it."))
        return "Confirmed. Waiting for ${them.name}."
      }
      trade.completing = true
    }
    return execute(trade)
  }

  fun cancel(charId: Long): String {
    val trade = trades.remove(charId) ?: return "You are not trading."
    val them = trade.other(charId)
    trades.remove(them.charId)
    val me = trade.seat(charId)
    them.session.send(notice("${me?.name ?: "They"} cancelled the trade."))
    return "Trade cancelled."
  }

  fun onDisconnect(charId: Long) {
    invites.remove(charId)
    invites.values.removeIf { it == charId }
    val trade = trades.remove(charId) ?: return
    val them = trade.other(charId)
    trades.remove(them.charId)
    them.session.send(notice("The other player disconnected. Trade cancelled."))
  }

  /**
   * The swap. Both monsters come off their owners before either is handed over, so a failure at any
   * point puts back exactly what was taken and neither side is left holding two copies or none.
   */
  private suspend fun execute(trade: TradeSession): String {
    val a = trade.a
    val b = trade.b
    val aId = a.offered ?: return "Nothing to trade."
    val bId = b.offered ?: return "Nothing to trade."

    val aMon = characterStore.removePokemon(a.charId, aId)
    if (aMon == null) {
      failed(trade, "${a.name}'s monster could not be taken")
      return "The trade failed. Nothing changed."
    }
    val bMon = characterStore.removePokemon(b.charId, bId)
    if (bMon == null) {
      characterStore.addPokemon(a.charId, aMon)
      failed(trade, "${b.name}'s monster could not be taken")
      return "The trade failed. Nothing changed."
    }

    val toB = aMon.copy(ownerId = b.charId, container = PokemonContainer.PARTY, containerSlot = freeSlot(b.charId))
    val toA = bMon.copy(ownerId = a.charId, container = PokemonContainer.PARTY, containerSlot = freeSlot(a.charId))
    val gaveB = characterStore.addPokemon(b.charId, toB)
    val gaveA = characterStore.addPokemon(a.charId, toA)
    if (!gaveB || !gaveA) {
      // Put both back where they came from rather than leaving one of them nowhere.
      if (!gaveB) characterStore.addPokemon(a.charId, aMon)
      if (!gaveA) characterStore.addPokemon(b.charId, bMon)
      failed(trade, "the exchange could not be written")
      return "The trade failed. Nothing changed."
    }

    characterStore.flushCharacterAsync(a.charId)
    characterStore.flushCharacterAsync(b.charId)
    log.info {
      "Trade completed: ${a.name} gave ${aMon.id} (dex ${aMon.dexId}), " +
          "${b.name} gave ${bMon.id} (dex ${bMon.dexId})"
    }

    trades.remove(a.charId)
    trades.remove(b.charId)
    sendParty(a)
    sendParty(b)
    a.session.send(notice("Trade complete. You received ${monLabel(toA)}."))
    b.session.send(notice("Trade complete. You received ${monLabel(toB)}."))
    return "Trade complete."
  }

  private fun failed(trade: TradeSession, why: String) {
    log.warn { "Trade between ${trade.a.name} and ${trade.b.name} failed: $why" }
    trades.remove(trade.a.charId)
    trades.remove(trade.b.charId)
    val message = notice("The trade failed and nothing was exchanged.")
    trade.a.session.send(message)
    trade.b.session.send(message)
  }

  private fun freeSlot(charId: Long): Short {
    val used = characterStore.getCharacter(charId)?.pokemon?.map { it.containerSlot } ?: emptyList()
    return ((0..5).firstOrNull { it.toShort() !in used } ?: used.size).toShort()
  }

  private fun sendParty(seat: TradeSeat) {
    val party =
        characterStore.getCharacter(seat.charId)?.pokemon?.sortedBy { it.containerSlot } ?: return
    seat.session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ))
  }

  private fun monLabel(mon: Pokemon): String =
      mon.nickname.takeIf { it.isNotBlank() } ?: "#${mon.dexId} (Lv ${mon.level})"

  /** The client's own trade window picking a monster, which offers it the same as the command. */
  suspend fun onSelectMon(event: PacketEvent<TradeSelectMonPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    if (!trades.containsKey(charId)) return
    // The window counts from zero and the command from one.
    val reply = offer(charId, event.packet.slotIndex + 1)
    event.session.send(notice(reply))
  }

  /**
   * The window's own buttons. Every press seen so far carries the same value, so nothing here can
   * tell a confirm from a cancel yet and the byte is recorded rather than acted on.
   */
  fun onTradeAction(event: PacketEvent<TradeActionPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    log.info { "char=$charId trade window action ${event.packet.action}" }
    if (trades.containsKey(charId)) {
      event.session.send(notice("Use /trade confirm to complete the trade."))
    }
  }

  private companion object {
    const val TRADE_HELP = "/trade offer <slot>, /trade confirm, /trade cancel."
  }
}
