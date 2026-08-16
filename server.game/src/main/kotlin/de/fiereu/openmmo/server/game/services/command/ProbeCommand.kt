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
import de.fiereu.openmmo.net.game.packets.StoryFlagUpdatePacket
import de.fiereu.openmmo.net.game.packets.WorldFlagSetPacket
import de.fiereu.openmmo.net.game.packets.WorldSessionStatePacket
import de.fiereu.openmmo.server.game.battle.BattleFieldTuning
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.BoxSyncTuning
import de.fiereu.openmmo.server.game.services.GtlTuning
import de.fiereu.openmmo.server.game.services.LinkService
import de.fiereu.openmmo.server.game.services.MapLoadService
import de.fiereu.openmmo.server.game.services.MovementTuning
import de.fiereu.openmmo.server.game.services.PlayerStateTuning
import de.fiereu.openmmo.server.game.services.PresenceService
import de.fiereu.openmmo.server.game.services.WorldStateService
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
/** One sweep at a time, so a mistyped range cannot bury the client in packets. */
private const val MAX_FLAG_SWEEP = 256

private const val SAFE_X: Short = 11
private const val SAFE_Y: Short = 10

@Singleton
class ProbeCommand
@Inject
constructor(
    private val battles: BattleService,
    private val maps: MapManager,
    private val mapLoad: MapLoadService,
    private val presence: PresenceService,
    private val sessions: SessionRegistry,
    private val characterStore: CharacterStore,
    private val links: LinkService,
    private val worldState: WorldStateService,
) : ChatCommand {
  override val name = "probe"
  override val usage =
      "/probe invite <name> <requestType> [flags] | /probe outcome <name> <packed> | " +
          "/probe requesttype <n> | /probe prompt on|off | /probe sweep <name> | " +
          "/probe box <0|1|2> | /probe gtl on|off | /probe flags <from> <to> [region]"
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
    if (what == "double") {
      val on = ctx.args.getOrNull(1)?.lowercase()
      if (on == "on" || on == "off") BattleFieldTuning.announceDouble = on == "on"
      BattleFieldTuning.battleType =
          ctx.args.getOrNull(2)?.toIntOrNull() ?: BattleFieldTuning.battleType
      BattleFieldTuning.perspective =
          ctx.args.getOrNull(3)?.toIntOrNull() ?: BattleFieldTuning.perspective
      log.info {
        "Double announce=${BattleFieldTuning.announceDouble} " +
            "type=${BattleFieldTuning.battleType} perspective=${BattleFieldTuning.perspective}"
      }
      ctx.reply(
          "Doubles announce is ${if (BattleFieldTuning.announceDouble) "on" else "off"}, " +
              "type=${BattleFieldTuning.battleType} perspective=${BattleFieldTuning.perspective}. " +
              "Start a battle.")
      return
    }
    if (what == "slots") {
      val value = ctx.args.getOrNull(1)?.toIntOrNull()
      if (value == null) {
        ctx.reply(
            "Player side opens with ${BattleFieldTuning.playerSideOpener}. Set with /probe slots <n>.")
        return
      }
      BattleFieldTuning.playerSideOpener = value
      log.info { "Player side opener set to $value" }
      ctx.reply("Your side will open with $value. Start a battle to see it.")
      return
    }
    if (what == "side") {
      val slot = ctx.args.getOrNull(1)?.toIntOrNull() ?: 1
      ctx.reply(battles.probeAddToOwnSide(ctx.characterId, slot))
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
    if (what == "state") {
      val value = ctx.args.getOrNull(1)?.toIntOrNull()
      if (value == null || value !in 0..255) {
        ctx.reply(
            "/probe state <n> sends the world session state the server has never sent. The client " +
                "names its own state every time it refuses a flag, so one flag goes out after it " +
                "and the client's log says whether the state moved.")
        return
      }
      // State 11 is the only value whose body carries anything, and what it carries is undecoded,
      // so it is sent with no blocks rather than guessed at.
      ctx.session.send(WorldSessionStatePacket(value, if (value == 11) emptyList() else null))
      // The client refuses every story flag and logs the state it was in when it did. That refusal
      // is the read-out: send one and the log names the state we just put it in.
      ctx.session.send(StoryFlagUpdatePacket(ctx.character.info.positionRegionId, 1, false))
      log.info { "Sent world session state $value to ${ctx.character.info.name}" }
      ctx.reply("Sent world state $value. Check the client log for the state name it now reports.")
      return
    }

    if (what == "evo") {
      // The two captured payloads read as group 2/1, index 32, value -1 under the flag codec, but
      // 32 is Nidoran, a species, so the flag reading is probably wrong and this is the prompt the
      // client answers with EvolutionPromptResponsePacket. Sending exactly what was captured is the
      // safe version of the question: it is a byte sequence the real server already sent a client.
      val a = ctx.args.getOrNull(1)?.toIntOrNull()?.toByte() ?: 2
      val b = ctx.args.getOrNull(2)?.toIntOrNull()?.toShort() ?: 32
      val c = ctx.args.getOrNull(3)?.toIntOrNull()?.toByte() ?: -1
      ctx.session.send(WorldFlagSetPacket(a, b, c))
      log.info { "Sent 0x0B $a/$b/$c to ${ctx.character.info.name}" }
      ctx.reply("Sent 0x0B as $a, $b, $c. Say what the screen did.")
      return
    }

    if (what == "flags") {
      val value = ctx.args.getOrNull(1)?.toIntOrNull()
      if (value == null) {
        ctx.reply(
            "Player state flags: ${PlayerStateTuning.flags}. Set with /probe flags <n>, " +
                "then try the bike. 0 is what the server has always sent.")
        return
      }
      PlayerStateTuning.flags = value.toByte()
      val stored = characterStore.getCharacter(ctx.characterId)
      if (stored == null) {
        ctx.reply("You are not in world.")
        return
      }
      // A resync rather than a reconnect, so the value takes effect without logging out.
      worldState.send(ctx.session, stored, fullVars = true)
      log.info { "Player state flags set to $value for char=${ctx.characterId}" }
      ctx.reply("Flags set to $value and your state resent. Try the bike now.")
      return
    }
    if (what == "flags") {
      val from = ctx.args.getOrNull(1)?.toIntOrNull()
      val to = ctx.args.getOrNull(2)?.toIntOrNull()
      if (from == null || to == null || to < from) {
        ctx.reply(
            "/probe flags <from> <to> [region] sends one flag update per id so the client's log " +
                "says which it will take. At most $MAX_FLAG_SWEEP at a time.")
        return
      }
      if (to - from >= MAX_FLAG_SWEEP) {
        ctx.reply("That is more than $MAX_FLAG_SWEEP ids. Sweep it in pieces.")
        return
      }
      val region =
          ctx.args.getOrNull(3)?.toIntOrNull()?.toByte() ?: ctx.character.info.positionRegionId
      // Cleared rather than set: most story flags hide something, so clearing them shows things
      // that are already there instead of making scenery vanish. Either way it is the client's own
      // copy and a relog restores it.
      for (id in from..to) {
        ctx.session.send(StoryFlagUpdatePacket(region, id, enabled = false))
      }
      log.info { "Swept flags $from..$to on region $region at ${ctx.character.info.name}" }
      ctx.reply(
          "Sent ${to - from + 1} flag updates for region $region. Every one the client refuses is " +
              "in its log as \"0x2A\"; the ids missing from that list are the ones it accepts. " +
              "Relog when the sweep is done.")
      return
    }

    if (what == "gtl") {
      val on = ctx.args.getOrNull(1)?.lowercase()
      if (on != "on" && on != "off") {
        ctx.reply(
            "The trade board is ${if (GtlTuning.answerBoards) "answered" else "silent"}. " +
                "Turn it on with /probe gtl on, then open the Global Trade Link.")
        return
      }
      GtlTuning.answerBoards = on == "on"
      log.info { "Trade board answering set to ${GtlTuning.answerBoards}" }
      ctx.reply(
          if (GtlTuning.answerBoards) "The board will answer now. Open it and say what it draws."
          else "The board is silent again.")
      return
    }

    if (what == "box") {
      val mode = ctx.args.getOrNull(1)?.toIntOrNull()
      if (mode == null || mode !in 0..2) {
        ctx.reply(
            "Box resend mode is ${BoxSyncTuning.mode}. 0 sends each container once, " +
                "1 empties it first, 2 sends every container the login sends. " +
                "Set with /probe box <0|1|2>, then move a monster and look at the box.")
        return
      }
      BoxSyncTuning.mode = mode
      log.info { "Box resend mode set to $mode" }
      ctx.reply("Box resend mode $mode. Move a monster now and say what the box does.")
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
