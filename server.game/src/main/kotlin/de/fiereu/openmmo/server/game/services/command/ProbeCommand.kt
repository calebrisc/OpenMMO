package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.hasPermission
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.DuelInviteOutcomePacket
import de.fiereu.openmmo.net.game.packets.DuelInvitePacket
import de.fiereu.openmmo.net.game.packets.EntityAppearanceInfo
import de.fiereu.openmmo.net.game.packets.EntityGroupMember
import de.fiereu.openmmo.net.game.packets.EntityGroupSnapshotPacket
import de.fiereu.openmmo.net.game.packets.GroupListFrameSet
import de.fiereu.openmmo.net.game.packets.GroupMemberRosterPacket
import de.fiereu.openmmo.net.game.packets.GroupRosterMember
import de.fiereu.openmmo.net.game.packets.PartyMemberJoinPacket
import de.fiereu.openmmo.server.game.services.LinkService
import de.fiereu.openmmo.server.game.services.MapLoadService
import de.fiereu.openmmo.server.game.services.MovementTuning
import de.fiereu.openmmo.server.game.services.PresenceService
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
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
private val log = KotlinLogging.logger {}

// Pallet Town, where a new character starts and where players have spawned cleanly all along.
private const val SAFE_REGION: Byte = 0
private const val SAFE_BANK: Byte = 3
private const val SAFE_MAP: Byte = 0
private const val SAFE_X: Short = 11
private const val SAFE_Y: Short = 10

@Singleton
class ProbeCommand
@Inject
constructor(
    private val maps: MapManager,
    private val mapLoad: MapLoadService,
    private val presence: PresenceService,
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
    if (what == "unstuck") {
      val name = ctx.args.getOrNull(1) ?: ctx.character.info.name
      val target = characterStore.findCachedByName(name)
      val targetSession = target?.let { sessions.getByCharacterId(it.info.id) }
      val targetState = targetSession?.attributes?.get(PLAYER_STATE)
      if (target == null || targetSession == null || targetState == null) {
        ctx.reply("$name is not online.")
        return
      }
      // Placed the way a login places somebody rather than warped: the warp blacks the screen
      // and waits for the client to confirm the map, and when that confirmation never came the
      // player sat through the timeout and arrived frozen. Nothing here waits on the client.
      val map = maps.getMap(SAFE_REGION, SAFE_BANK, SAFE_MAP)
      if (map == null) {
        ctx.reply("The safe map is missing from this build.")
        return
      }
      characterStore.updatePosition(
          target.info.id, SAFE_X, SAFE_Y, SAFE_BANK, SAFE_MAP, Direction.DOWN)
      characterStore.flushCharacterAsync(target.info.id)
      targetState.regionId = SAFE_REGION.toInt()
      targetState.bankId = SAFE_BANK.toInt()
      targetState.mapId = SAFE_MAP.toInt()
      targetState.x = SAFE_X
      targetState.y = SAFE_Y
      targetState.facingDirection = Direction.DOWN
      targetState.inDialog = false
      targetState.consecutiveDesyncs = 0
      mapLoad.resetClientCache(targetSession, map)
      targetSession.send(maps.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true))
      presence.refresh(targetSession)
      log.info { "Unstuck ${target.info.name} to $SAFE_REGION:$SAFE_BANK:$SAFE_MAP" }
      targetSession.send(notice("You have been moved somewhere safe."))
      ctx.reply("Moved ${target.info.name} to safety.")
      return
    }
    if (what == "group") {
      val name = ctx.args.getOrNull(1)
      val target = name?.let { characterStore.findCachedByName(it) }
      val targetSession = target?.let { sessions.getByCharacterId(it.info.id) }
      if (target == null || targetSession == null) {
        ctx.reply("Usage: /probe group <name>")
        return
      }
      // The one packet that names a leader, which is the shape of "you are in a group" rather than
      // the bare member list the roster turned out to be. The roster did nothing across every
      // state, so this is the next candidate.
      val members =
          listOf(ctx.character, target).map { who ->
            EntityGroupMember(
                entityId = who.info.id,
                appearance =
                    EntityAppearanceInfo(
                        name = who.info.name,
                        gender = 0,
                        formId = 0,
                        kind = 0,
                        palettePack = 0,
                        slots = List(4) { 0 },
                    ),
                frames = GroupListFrameSet(listType = null, frames = emptyList()),
            )
          }
      targetSession.send(
          EntityGroupSnapshotPacket(
              present = true,
              leaderId = ctx.characterId,
              members = members,
          ))
      ctx.reply("Sent a group snapshot to ${target.info.name}, led by you.")
      return
    }
    if (what == "roster") {
      val name = ctx.args.getOrNull(1)
      val state = ctx.args.getOrNull(2)?.toByteOrNull() ?: 0
      val target = name?.let { characterStore.findCachedByName(it) }
      val targetSession = target?.let { sessions.getByCharacterId(it.info.id) }
      if (targetSession == null) {
        ctx.reply("Usage: /probe roster <name> [state]")
        return
      }
      // The client has a whole link interface with a member list, kick and leave, and the server
      // has never sent it a roster. Both players go in it, so if the panel fills in we have found
      // where a link actually lives.
      val members =
          listOf(ctx.character, target).map { who ->
            GroupRosterMember(
                entityId = who.info.id,
                name = who.info.name,
                guildName = "",
                trackedEntities = emptyList(),
            )
          }
      targetSession.send(
          GroupMemberRosterPacket(
              reset = true,
              updateState = true,
              state = state,
              members = members,
          ))
      ctx.reply("Sent a two person roster to ${target.info.name} with state=$state.")
      return
    }
    if (what == "grant") {
      val name = ctx.args.getOrNull(1)
      if (name == null) {
        ctx.reply("Usage: /probe grant <name>")
        return
      }
      val target = characterStore.findCachedByName(name)
      if (target == null) {
        ctx.reply("$name is not online.")
        return
      }
      if (target.info.hasPermission(CharacterPermissions.DEVELOPER)) {
        ctx.reply("${target.info.name} already has developer access.")
        return
      }
      // Through the store rather than the database: it is the authority while a player is online
      // and would otherwise write its cached copy straight back over the change.
      val granted = target.info.permissions or CharacterPermissions.DEVELOPER
      characterStore.updateCharacter(target.info.copy(permissions = granted))
      characterStore.flushCharacterAsync(target.info.id)
      log.info { "Granted developer to ${target.info.name} (permissions=$granted)" }
      sessions
          .getByCharacterId(target.info.id)
          ?.send(notice("You have been given developer access."))
      ctx.reply("${target.info.name} now has developer access.")
      return
    }
    if (what == "mm") {
      val walk = ctx.args.getOrNull(1)?.toIntOrNull()
      val run = ctx.args.getOrNull(2)?.toIntOrNull()
      if (walk == null) {
        ctx.reply(
            "Relayed movement modes: walk=${MovementTuning.walk} run=${MovementTuning.run}. " +
                "Set with /probe mm <walk> [run].")
        return
      }
      MovementTuning.walk = walk
      if (run != null) MovementTuning.run = run
      log.info { "Relayed movement modes set to walk=$walk run=${MovementTuning.run}" }
      ctx.reply("Relaying walk=${MovementTuning.walk} run=${MovementTuning.run}. Walk around now.")
      return
    }
    if (what == "sweep") {
      val name = ctx.args.getOrNull(1)
      if (name == null) {
        ctx.reply("Usage: /probe sweep <name> [from] [to]")
        return
      }
      val from = ctx.args.getOrNull(2)?.toIntOrNull() ?: 0
      val to = ctx.args.getOrNull(3)?.toIntOrNull() ?: (from + 7)
      ctx.reply(links.sweepInvite(ctx.session, ctx.characterId, name, from, to))
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
