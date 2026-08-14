package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.net.game.packets.DuelInviteOutcomePacket
import de.fiereu.openmmo.net.game.packets.DuelInvitePacket
import de.fiereu.openmmo.net.game.packets.PartyMemberJoinPacket
import de.fiereu.openmmo.server.game.services.LinkService
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sends a chosen packet at a chosen player so its undecoded bytes can be read off the screen rather
 * than guessed at.
 *
 * The client's invite, trade and challenge buttons all send us the target's name and nothing that
 * says which of the three it was, and they appear to stall because we never answer. This drives the
 * other half of that conversation: pick a byte, send it, and see what window opens.
 */
@Singleton
class ProbeCommand
@Inject
constructor(
    private val sessions: SessionRegistry,
    private val characterStore: CharacterStore,
    private val links: LinkService,
) : ChatCommand {
  override val name = "probe"
  override val usage =
      "/probe invite <name> <requestType> [flags] | /probe outcome <name> <packed> | " +
          "/probe requesttype <n> | /probe prompt on|off | /probe sweep <name>"
  override val description = "sends a raw packet at a player to see what the client does with it"
  override val permission = CharacterPermissions.DEVELOPER

  override suspend fun run(ctx: CommandContext) {
    val what = ctx.args.getOrNull(0)?.lowercase()
    if (what == "sweep") {
      val name = ctx.args.getOrNull(1)
      if (name == null) {
        ctx.reply("Usage: /probe sweep <name>")
        return
      }
      ctx.reply(links.sweepInvite(ctx.session, ctx.characterId, name))
      return
    }
    if (what == "prompt") {
      val on = ctx.args.getOrNull(1)?.lowercase()
      if (on != "on" && on != "off") {
        ctx.reply("Confirmation prompt is ${if (links.sendConfirmationPrompt) "on" else "off"}.")
        return
      }
      links.sendConfirmationPrompt = on == "on"
      ctx.reply("Confirmation prompt $on.")
      return
    }
    if (what == "requesttype") {
      val value = ctx.args.getOrNull(1)?.toByteOrNull()
      if (value == null) {
        ctx.reply("Link invites currently send requestType=${links.inviteRequestType}.")
        return
      }
      links.inviteRequestType = value
      ctx.reply("The client's Invite to Link button will now reply with requestType=$value.")
      return
    }
    val targetName = ctx.args.getOrNull(1)
    if (what == null || targetName == null) {
      ctx.reply("Usage: $usage")
      return
    }
    val target = characterStore.findCachedByName(targetName)
    val session = target?.let { sessions.getByCharacterId(it.info.id) }
    if (session == null) {
      ctx.reply("$targetName is not online.")
      return
    }
    when (what) {
      "invite" -> {
        val requestType = ctx.args.getOrNull(2)?.toByteOrNull()
        if (requestType == null) {
          ctx.reply("Usage: /probe invite <name> <requestType> [flags]")
          return
        }
        val flags = ctx.args.getOrNull(3)?.toByteOrNull() ?: 0
        session.send(
            DuelInvitePacket(
                flags = flags, requestType = requestType, name = ctx.character.info.name))
        ctx.reply("Sent invite requestType=$requestType flags=$flags to ${target.info.name}.")
      }
      "outcome" -> {
        val packed = ctx.args.getOrNull(2)?.toByteOrNull()
        if (packed == null) {
          ctx.reply("Usage: /probe outcome <name> <packed>")
          return
        }
        session.send(DuelInviteOutcomePacket(packed = packed))
        ctx.reply("Sent outcome packed=$packed to ${target.info.name}.")
      }
      "party" -> {
        session.send(
            PartyMemberJoinPacket(
                player = ctx.characterId,
                name = ctx.character.info.name,
                secondaryName = "",
                value = 0,
            ))
        ctx.reply("Sent a party join for yourself to ${target.info.name}.")
      }
      else -> ctx.reply("Usage: $usage")
    }
  }
}
