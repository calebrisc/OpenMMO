package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.ChatMessageSendPacket
import de.fiereu.openmmo.server.game.services.command.ChatCommandService
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.GuildStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/** The mode the client uses when the text carries a separate target, which is how it whispers. */
private const val MODE_DIRECTED = 4

/**
 * Routes chat by its type. Everything used to go to everyone, so whispers were public and team chat
 * never reached the team.
 */
@Singleton
class ChatService
@Inject
constructor(
    private val sessionRegistry: SessionRegistry,
    private val characterStore: CharacterStore,
    private val guildStore: GuildStore,
    private val linkService: LinkService,
    private val socialService: SocialService,
    private val chatCommandService: ChatCommandService,
) {

  /** The channel-tagged form the client sends once it knows which channel it is talking on. */
  suspend fun onChatMessage(event: PacketEvent<ChatMessagePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE]
    if (state == null) {
      log.warn { "Chat message from session without PlayerState" }
      return
    }
    val charId = state.characterId ?: return
    val msg = event.packet
    val sender = characterStore.getCharacter(charId)?.info?.name ?: "Unknown"
    log.info { "Chat [${msg.type}] $sender: ${msg.message}" }

    if (chatCommandService.tryHandle(ctx, msg.message)) return

    when (msg.type) {
      ChatType.TEAM -> sendToGuild(charId, sender, msg.message)
      ChatType.LINK -> sendToLink(charId, sender, msg.message)
      ChatType.WHISPER -> ctx.send(notice("Whisper needs a name: /pm <name> <message>"))
      else ->
          broadcast(
              sender,
              ChatMessagePacket(
                  type = msg.type,
                  language = msg.language ?: Language.EN,
                  message = msg.message,
                  sender = sender,
              ),
          )
    }
  }

  /** The raw form the client sends as the player types, before it has picked a channel. */
  suspend fun onChatSend(event: PacketEvent<ChatMessageSendPacket>) {
    val ctx = event.session
    val packet = event.packet
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val sender = characterStore.getCharacter(charId)?.info?.name ?: return

    if (packet.mode.toInt() == MODE_DIRECTED && packet.message != null) {
      whisper(ctx, sender, packet.target, packet.message!!)
      return
    }

    // The text rides in target unless the mode carries a message of its own.
    val text = packet.message ?: packet.target
    if (text.isBlank()) return
    if (chatCommandService.tryHandle(ctx, text)) return
    // Anything that is not a command used to be dropped here.
    log.info { "Chat [send] $sender: $text" }
    broadcast(
        sender,
        ChatMessagePacket(
            type = ChatType.NORMAL,
            language = Language.EN,
            message = text,
            sender = sender,
        ),
    )
  }

  fun whisper(ctx: SessionContext, sender: String, targetName: String, message: String) {
    val target = onlineSessionNamed(targetName)
    if (target == null) {
      ctx.send(notice("$targetName is not online."))
      return
    }
    val targetState = target.attributes[PLAYER_STATE]
    if (targetState != null && socialService.hasBlocked(targetState.userId, sender)) {
      // Say nothing about the block itself, which would tell the sender they were blocked.
      ctx.send(notice("Your message to $targetName could not be delivered."))
      return
    }
    val packet =
        ChatMessagePacket(
            type = ChatType.WHISPER,
            language = Language.EN,
            message = message,
            sender = sender,
        )
    target.send(packet)
    // Echo it back so the sender sees their own whisper in the log.
    ctx.send(packet)
    log.info { "Whisper $sender -> $targetName" }
  }

  private fun sendToLink(charId: Long, sender: String, message: String) {
    val link = linkService.linkFor(charId)
    if (link == null) {
      sessionRegistry.getByCharacterId(charId)?.send(notice("You are not in a link."))
      return
    }
    val packet =
        ChatMessagePacket(
            type = ChatType.LINK,
            language = Language.EN,
            message = message,
            sender = sender,
        )
    linkService.sessionsIn(link).forEach { it.send(packet) }
  }

  private fun sendToGuild(charId: Long, sender: String, message: String) {
    val guild = guildStore.getGuildForChar(charId)
    if (guild == null) {
      sessionRegistry.getByCharacterId(charId)?.send(notice("You are not in a team."))
      return
    }
    // The team form of the packet carries no sender field, so the name rides in the text.
    val packet =
        ChatMessagePacket(
            type = ChatType.TEAM,
            language = null,
            message = "$sender: $message",
            sender = null,
        )
    for (member in guild.members) {
      val session = sessionRegistry.getByCharacterId(member.id) ?: continue
      val state = session.attributes[PLAYER_STATE] ?: continue
      if (socialService.hasBlocked(state.userId, sender)) continue
      session.send(packet)
    }
  }

  private fun broadcast(sender: String, packet: ChatMessagePacket) {
    for (characterId in sessionRegistry.onlineCharacterIds()) {
      val ctx = sessionRegistry.getByCharacterId(characterId) ?: continue
      if (!ctx.channel.isActive) continue
      val state = ctx.attributes[PLAYER_STATE] ?: continue
      if (socialService.hasBlocked(state.userId, sender)) continue
      ctx.send(packet)
    }
  }

  private fun onlineSessionNamed(name: String): SessionContext? =
      sessionRegistry.onlineCharacterIds().firstNotNullOfOrNull { id ->
        if (characterStore.getCharacter(id)?.info?.name.equals(name, ignoreCase = true)) {
          sessionRegistry.getByCharacterId(id)
        } else {
          null
        }
      }
}
