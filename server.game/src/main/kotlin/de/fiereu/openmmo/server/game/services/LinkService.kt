package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.DuelInvitePacket
import de.fiereu.openmmo.net.game.packets.InGameChallengeResponsePacket
import de.fiereu.openmmo.net.game.packets.LinkKickMemberPacket
import de.fiereu.openmmo.net.game.packets.PartyInfoRequestPacket
import de.fiereu.openmmo.net.game.packets.PartyMember
import de.fiereu.openmmo.net.game.packets.PartyMemberLeavePacket
import de.fiereu.openmmo.net.game.packets.PartyRosterPacket
import de.fiereu.openmmo.net.game.packets.RequestConfirmationPromptPacket
import de.fiereu.openmmo.net.game.packets.SendChatCommandPacket
import de.fiereu.openmmo.net.game.packets.StringCommandPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.GuildStore
import de.fiereu.openmmo.server.game.storage.Link
import de.fiereu.openmmo.server.game.storage.LinkMember
import de.fiereu.openmmo.server.game.storage.LinkStore
import de.fiereu.openmmo.server.game.storage.MAX_LINK_SIZE
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

private val log = KotlinLogging.logger {}

private val INVITE_TIMEOUT = 2.minutes

/** What the prompt counts down from. The client has a line for an invite nobody answered. */
private const val NOT_IN_WORLD = "You are not in the world yet."

private const val INVITE_PROMPT_SECONDS = 60

/** The candidate request types, announced one at a time so a hit can be named. */
private val SWEEP_VALUES = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 16, 32, 64)

private val SWEEP_GAP = 2.seconds

/** Replaces the whole roster rather than merging into it. */
private const val ROSTER_REPLACE = 0

private data class PendingInvite(val fromCharId: Long, val linkId: Long, val sentAtMillis: Long)

/**
 * Links: the group of up to four players PokeMMO forms a raid from.
 *
 * The invite handshake the client uses carries bytes we have not decoded, so the reliable path is
 * the /link commands. The roster packets go out as well, so the client's own group UI fills in if
 * it reads them the way we think it does. Nothing depends on that working.
 */
@Singleton
class LinkService
@Inject
constructor(
    private val linkStore: LinkStore,
    private val sessionRegistry: SessionRegistry,
    private val characterStore: CharacterStore,
    private val guildStore: GuildStore,
) {
  // Keyed by the invited character.
  private val pending = ConcurrentHashMap<Long, PendingInvite>()

  fun linkFor(charId: Long): Link? = linkStore.forChar(charId)

  /**
   * Which value the invite carries. The client distinguishes a link request from a duel or a trade
   * with a byte we have not decoded, and it shows the invited player nothing at all until the
   * server answers, so the way to find it is to send one and see whether the prompt appears.
   * Settable live with /probe requesttype so a sweep does not need a redeploy.
   */
  @Volatile var inviteRequestType: Byte = 0

  /**
   * DO NOT TURN THIS ON. RequestConfirmationPromptPacket is the anti-bot CAPTCHA, not an invite:
   * sending it opens "solve the captcha in your web browser" on the target and points them at
   * pokemmo.com/captcha/<their id>/<the id we sent>, which no private server can answer, so it
   * simply blocks them. The two timeouts are the captcha's own countdown, not an invite's.
   *
   * Kept only so the shape can be re-tested deliberately.
   */
  @Volatile var sendConfirmationPrompt: Boolean = false

  /**
   * The client's own Invite to Link button. It sends us the target's name and nothing that says
   * which of invite, trade or challenge was pressed, then waits. Answering is what makes its prompt
   * appear, which is why the button has never done anything.
   */
  fun onNamedInvite(session: SessionContext, targetName: String) {
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return
    if (targetName.isBlank()) return
    val reply = invite(session, charId, targetName)
    session.send(notice(reply))
    val target = characterStore.findCachedByName(targetName) ?: return
    val inviter = characterStore.getCharacter(charId) ?: return
    val targetSession = sessionRegistry.getByCharacterId(target.info.id) ?: return
    targetSession.send(
        DuelInvitePacket(
            flags = 0,
            requestType = inviteRequestType,
            name = inviter.info.name,
        ))
    if (sendConfirmationPrompt) {
      targetSession.send(
          RequestConfirmationPromptPacket(
              visible = true,
              entityId = charId,
              requestTimeoutSeconds = INVITE_PROMPT_SECONDS,
              responseTimeoutSeconds = INVITE_PROMPT_SECONDS,
          ))
    }
    log.info {
      "Sent link invite to ${target.info.name} requestType=$inviteRequestType " +
          "prompt=$sendConfirmationPrompt"
    }
  }

  /**
   * Sends the invite once per candidate value, announcing each in chat first so whichever one
   * finally draws a prompt can be named. Sixteen rounds by hand is how an experiment gets abandoned
   * half way.
   */
  suspend fun sweepInvite(ctx: SessionContext, inviterId: Long, targetName: String): String {
    val target = characterStore.findCachedByName(targetName) ?: return "$targetName is not online."
    val inviter = characterStore.getCharacter(inviterId) ?: return NOT_IN_WORLD
    val targetSession =
        sessionRegistry.getByCharacterId(target.info.id) ?: return "$targetName is not online."
    for (value in SWEEP_VALUES) {
      val label = notice("Trying invite value $value.")
      ctx.send(label)
      targetSession.send(label)
      targetSession.send(
          DuelInvitePacket(flags = 0, requestType = value.toByte(), name = inviter.info.name))
      log.info { "Sweep sent invite value=$value to ${target.info.name}" }
      delay(SWEEP_GAP)
    }
    return "Swept ${SWEEP_VALUES.size} values at ${target.info.name}. Which one drew a prompt?"
  }

  fun onSendChatCommand(event: PacketEvent<SendChatCommandPacket>) {
    onNamedInvite(event.session, event.packet.message)
  }

  fun onStringCommand(event: PacketEvent<StringCommandPacket>) {
    onNamedInvite(event.session, event.packet.command)
  }

  /** Invites [targetName] to the caller's link, starting one if they are not in a link yet. */
  fun invite(ctx: SessionContext, inviterId: Long, targetName: String): String {
    val inviter = characterStore.getCharacter(inviterId) ?: return NOT_IN_WORLD
    val target = characterStore.findCachedByName(targetName) ?: return "$targetName is not online."
    if (target.info.id == inviterId) return "You cannot link with yourself."
    if (linkStore.forChar(target.info.id) != null)
        return "${target.info.name} is already in a link."

    val link = linkStore.create(LinkMember(inviterId, inviter.info.name))
    if (link.full) return "Your link is full ($MAX_LINK_SIZE players)."
    if (link.leader.charId != inviterId) return "Only the link leader can invite."

    pending[target.info.id] = PendingInvite(inviterId, link.id, System.currentTimeMillis())
    sessionRegistry
        .getByCharacterId(target.info.id)
        ?.send(
            notice(
                "${inviter.info.name} invited you to their link. " +
                    "Type /link accept to join, or /link decline."))
    log.info { "Link invite char=$inviterId -> ${target.info.id}" }
    return "Invited ${target.info.name} to your link."
  }

  /** Accepts the pending invite, if it has not gone stale. */
  fun accept(charId: Long): String {
    val invite = pending.remove(charId) ?: return "You have no link invite."
    if (System.currentTimeMillis() - invite.sentAtMillis > INVITE_TIMEOUT.inWholeMilliseconds) {
      dropEmptyLink(invite.fromCharId)
      return "That link invite expired."
    }
    val character = characterStore.getCharacter(charId) ?: return NOT_IN_WORLD
    val link = linkStore.forChar(invite.fromCharId)
    if (link == null || link.id != invite.linkId) return "That link no longer exists."
    if (!linkStore.add(link, LinkMember(charId, character.info.name))) {
      return "That link is full."
    }
    log.info { "char=$charId joined link ${link.id}" }
    announce(link, "${character.info.name} joined the link.")
    broadcastRoster(link)
    return "You joined the link."
  }

  fun decline(charId: Long): String {
    val invite = pending.remove(charId) ?: return "You have no link invite."
    val name = characterStore.getCharacter(charId)?.info?.name ?: "Someone"
    sessionRegistry.getByCharacterId(invite.fromCharId)?.send(notice("$name declined your invite."))
    dropEmptyLink(invite.fromCharId)
    return "Invite declined."
  }

  /**
   * An invite starts a link before anyone has accepted, so a decline or an expiry can leave the
   * inviter alone in one. That would read as a group they never formed and would stop anyone else
   * inviting them.
   */
  private fun dropEmptyLink(charId: Long) {
    val link = linkStore.forChar(charId) ?: return
    if (link.members.size > 1) return
    if (pending.values.any { it.linkId == link.id }) return
    linkStore.remove(charId)
    sessionRegistry.getByCharacterId(charId)?.send(PartyRosterPacket(ROSTER_REPLACE, emptyList()))
  }

  fun leave(charId: Long): String {
    val name = characterStore.getCharacter(charId)?.info?.name ?: "Someone"
    val remaining = linkStore.remove(charId) ?: return "You are not in a link."
    sessionRegistry.getByCharacterId(charId)?.send(PartyRosterPacket(ROSTER_REPLACE, emptyList()))
    if (!linkStore.contains(remaining)) {
      // Dropping below two ends it, so tell whoever was left and clear their roster too.
      announce(remaining, "The link broke up.")
      remaining.members.forEach { member ->
        sessionRegistry
            .getByCharacterId(member.charId)
            ?.send(PartyRosterPacket(ROSTER_REPLACE, emptyList()))
      }
      return "You left the link."
    }
    announce(remaining, "$name left the link.")
    broadcastRoster(remaining)
    broadcastLeave(remaining, charId)
    return "You left the link."
  }

  fun kick(charId: Long, targetName: String): String {
    val link = linkStore.forChar(charId) ?: return "You are not in a link."
    if (link.leader.charId != charId) return "Only the link leader can remove somebody."
    val target =
        link.members.firstOrNull { it.name.equals(targetName, ignoreCase = true) }
            ?: return "$targetName is not in your link."
    if (target.charId == charId) return "Use /link leave to leave your own link."
    sessionRegistry.getByCharacterId(target.charId)?.send(notice("You were removed from the link."))
    val remaining = linkStore.remove(target.charId)
    if (remaining != null && remaining.members.isNotEmpty()) {
      announce(remaining, "${target.name} was removed from the link.")
      broadcastRoster(remaining)
      broadcastLeave(remaining, target.charId)
    }
    return "Removed ${target.name} from the link."
  }

  fun describe(charId: Long): String {
    val link = linkStore.forChar(charId) ?: return "You are not in a link. Try /link invite <name>."
    val names =
        link.members.joinToString(", ") { member ->
          if (member.charId == link.leader.charId) "${member.name} (leader)" else member.name
        }
    return "Link (${link.members.size}/$MAX_LINK_SIZE): $names"
  }

  /** Every online member's session, for chat and later for starting a battle together. */
  fun sessionsIn(link: Link): List<SessionContext> =
      link.members.mapNotNull { sessionRegistry.getByCharacterId(it.charId) }

  fun onPartyInfoRequest(event: PacketEvent<PartyInfoRequestPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val link = linkStore.forChar(charId)
    event.session.send(
        if (link == null) PartyRosterPacket(ROSTER_REPLACE, emptyList()) else rosterOf(link))
  }

  fun onKickMember(event: PacketEvent<LinkKickMemberPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val link = linkStore.forChar(charId) ?: return
    val target = link.members.firstOrNull { it.charId == event.packet.targetEntityId } ?: return
    event.session.send(notice(kick(charId, target.name)))
  }

  /** The client's own accept or decline button, if it routes through this packet. */
  fun onChallengeResponse(event: PacketEvent<InGameChallengeResponsePacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    if (!pending.containsKey(charId)) return
    val reply = if (event.packet.accepted) accept(charId) else decline(charId)
    event.session.send(notice(reply))
  }

  /** Drops a disconnecting player out of their link. */
  fun onDisconnect(charId: Long) {
    pending.remove(charId)
    pending.entries.removeIf { it.value.fromCharId == charId }
    if (linkStore.forChar(charId) == null) return
    leave(charId)
  }

  private fun announce(link: Link, message: String) {
    sessionsIn(link).forEach { it.send(notice(message)) }
  }

  private fun broadcastRoster(link: Link) {
    val roster = rosterOf(link)
    sessionsIn(link).forEach { it.send(roster) }
  }

  private fun broadcastLeave(link: Link, charId: Long) {
    sessionsIn(link).forEach { it.send(PartyMemberLeavePacket(memberId = charId)) }
  }

  private fun rosterOf(link: Link): PartyRosterPacket =
      PartyRosterPacket(
          mergeMode = ROSTER_REPLACE,
          members =
              link.members.map { member ->
                PartyMember(
                    player = member.charId,
                    name = member.name,
                    // The client shows a guild name beside the player name.
                    secondaryName = guildStore.getGuildForChar(member.charId)?.name ?: "",
                    value = 0,
                )
              },
      )
}
