package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank
import de.fiereu.openmmo.net.game.packets.guild.GuildActivityLogEntry
import de.fiereu.openmmo.net.game.packets.guild.GuildActivityLogPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildActivityLogPageRequestPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildCreatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildDisbandPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildInvitePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildLeavePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberEntry
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberKickPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMemberRankAssignPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMembershipPacket
import de.fiereu.openmmo.net.game.packets.guild.GuildMotdUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildProfileData
import de.fiereu.openmmo.net.game.packets.guild.GuildRankLabelUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.GuildRankPermissionUpdatePacket
import de.fiereu.openmmo.net.game.packets.guild.SyncGuildMembersPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.Guild
import de.fiereu.openmmo.server.game.storage.GuildMember
import de.fiereu.openmmo.server.game.storage.GuildStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val GUILD_FOUND_COST = 15000

// The entries list is length-prefixed with a single byte, so a page holds at most 255 entries.
private const val MAX_ACTIVITY_LOG_ENTRIES = 255

@Singleton
class GuildService
@Inject
constructor(
    private val guildStore: GuildStore,
    private val characterStore: CharacterStore,
    private val sessionRegistry: SessionRegistry,
) {

  suspend fun onCreateGuild(event: PacketEvent<GuildCreatePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val stored = characterStore.getCharacter(charId) ?: return
    val packet = event.packet
    log.info {
      "CreateGuild name='${packet.guildName}' tag='${packet.guildTag}' char=$charId money=${stored.info.money}"
    }
    if (guildStore.getGuildForChar(charId) != null) {
      ctx.send(notice("You are already in a team."))
      return
    }
    if (stored.info.money < GUILD_FOUND_COST) {
      log.info { "Insufficient funds to found a guild (need $GUILD_FOUND_COST)" }
      ctx.send(notice("Founding a team costs $GUILD_FOUND_COST."))
      return
    }
    // The guild write has to land before the fee, so a failure here cannot bill for nothing.
    val guild = guildStore.createGuild(packet.guildName, packet.guildTag, charId, stored.info.name)
    characterStore.addMoney(charId, -GUILD_FOUND_COST)
    ctx.send(buildMembership(guild))
    ctx.send(buildMemberSync(guild))
  }

  suspend fun onActivityLogPageRequest(event: PacketEvent<GuildActivityLogPageRequestPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val page = event.packet.pageIndex.toInt()
    log.info { "Guild activity log page=$page requested." }
    val packet = buildActivityLog(guild)
    log.info {
      "Sending guild activity log guild=${guild.id} sent=${packet.entries.size} total=${packet.totalCount}"
    }
    ctx.send(packet)
  }

  suspend fun onGuildInvite(event: PacketEvent<GuildInvitePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val target = event.packet.targetName
    log.info { "GuildInvite char=$charId target='$target'" }
    // Only a loaded character has an id the client can address, so the target has to be online.
    val invited = characterStore.findCachedByName(target)
    if (invited == null) {
      ctx.send(notice("$target is not online."))
      return
    }
    if (invited.info.id == charId) return
    if (guildStore.getGuildForChar(invited.info.id) != null) {
      ctx.send(notice("$target is already in a team."))
      return
    }
    guildStore.addMember(
        guild,
        GuildMember(
            id = invited.info.id,
            name = invited.info.name,
            rank = GuildRank.GRUNT,
            leader = false,
            joinedAt = Instant.now(),
        ),
    )
    broadcastMemberSync(guild)
    sessionRegistry.getByCharacterId(invited.info.id)?.let { joined ->
      joined.send(buildMembership(guild))
      joined.send(notice("You joined ${guild.name}."))
    }
  }

  suspend fun onRankAssign(event: PacketEvent<GuildMemberRankAssignPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val rank = GuildRank.entries.getOrNull(event.packet.rankOrdinal) ?: return
    if (rank == GuildRank.BOSS) {
      guildStore.transferLeadership(guild, event.packet.memberEntityId)
      log.info { "Leadership transferred char=$charId newLeader=${event.packet.memberEntityId}" }
    } else {
      guildStore.setMemberRank(guild, event.packet.memberEntityId, rank)
      log.info { "RankAssign char=$charId member=${event.packet.memberEntityId} rank=$rank" }
    }
    broadcastMemberSync(guild)
  }

  suspend fun onKick(event: PacketEvent<GuildMemberKickPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val targetId = event.packet.targetEntityId
    guildStore.removeMember(guild, targetId)
    log.info { "Kick char=$charId member=$targetId" }
    broadcastMemberSync(guild)
    sessionRegistry.getByCharacterId(targetId)?.let { kicked ->
      kicked.send(GuildMembershipPacket(inGuild = false, profile = null))
      kicked.send(notice("You were removed from ${guild.name}."))
    }
  }

  suspend fun onLeave(event: PacketEvent<GuildLeavePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId)
    guildStore.leaveGuild(charId)
    log.info { "GuildLeave char=$charId" }
    ctx.send(GuildMembershipPacket(inGuild = false, profile = null))
    guild?.let { broadcastMemberSync(it) }
  }

  suspend fun onDisband(event: PacketEvent<GuildDisbandPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val initiate = event.packet.initiate
    val guild = guildStore.getGuildForChar(charId)
    log.info { "GuildDisband char=$charId guild=${guild?.id} initiate=$initiate" }
    if (guild == null) return
    // We disband immediately on initiate, so a follow-up cancel has no pending state to undo.
    if (!initiate) return
    val members = guild.members.map { it.id }
    guildStore.disbandGuild(charId)
    log.info { "Guild ${guild.id} disbanded by char=$charId" }
    // TODO: The guild window does not close after disbanding. Sending
    // GuildMembershipPacket(inGuild = false) updates the state (reopening the
    // window shows the create-guild screen) but does not dismiss the currently
    // open window. Check against the real game to see what packet closes it.
    for (memberId in members) {
      val session = sessionRegistry.getByCharacterId(memberId) ?: continue
      session.send(GuildMembershipPacket(inGuild = false, profile = null))
      if (memberId != charId) {
        session.send(notice("${guild.name} was disbanded."))
      }
    }
  }

  suspend fun onMotdUpdate(event: PacketEvent<GuildMotdUpdatePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val motd = event.packet.motdText
    guildStore.setMotd(guild, motd)
    log.info { "GuildMotdUpdate char=$charId guild=${guild.id} motd='$motd'" }
    broadcast(guild) { buildMembership(guild) }
  }

  suspend fun onRankLabelUpdate(event: PacketEvent<GuildRankLabelUpdatePacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val rank = GuildRank.entries.getOrNull(event.packet.rankOrdinal) ?: return
    guildStore.setRankLabel(guild, rank, event.packet.rankLabel)
    log.info {
      "GuildRankLabelUpdate char=$charId guild=${guild.id} rank=$rank label='${event.packet.rankLabel}'"
    }
  }

  suspend fun onRankPermissionUpdate(event: PacketEvent<GuildRankPermissionUpdatePacket>) {
    val state = event.session.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val guild = guildStore.getGuildForChar(charId) ?: return
    val sanitized =
        event.packet.permissions.mapValues { (rank, perms) ->
          if (rank == GuildRank.GRUNT && GuildPermission.KICK in perms) {
            log.warn { "Rejecting KICK permission for GRUNT" }
            perms - GuildPermission.KICK
          } else {
            perms
          }
        }
    guildStore.setPermissions(guild, sanitized)
    log.info { "RankPermUpdate char=$charId perms=$sanitized" }
  }

  /** Every online member of [guild], for the caller to send to. */
  fun onlineMembers(guild: Guild) =
      guild.members.mapNotNull { sessionRegistry.getByCharacterId(it.id) }

  private fun broadcastMemberSync(guild: Guild) {
    val packet = buildMemberSync(guild)
    onlineMembers(guild).forEach { it.send(packet) }
  }

  private fun broadcast(guild: Guild, packet: () -> Any) {
    val built = packet()
    onlineMembers(guild).forEach { it.send(built) }
  }

  private fun buildMembership(guild: Guild): GuildMembershipPacket =
      GuildMembershipPacket(
          inGuild = true,
          profile =
              GuildProfileData(
                  guildId = guild.id,
                  name = guild.name,
                  tag = guild.tag,
                  foundedAt = guild.foundedAt.epochSecond.toInt(),
                  message = guild.motd.ifEmpty { "Your Team has been successfully created!" },
                  updatedAt = 0,
                  value1 = 5,
                  value2 = 5,
                  value3 = 5,
                  value4 = 0,
                  value5 = 0,
                  unk1 = 0,
                  rankCount = GuildRank.entries.size,
                  unk2 = 0,
                  unk3 = 0,
                  flag = 0,
              ),
      )

  private fun buildMemberSync(guild: Guild): SyncGuildMembersPacket =
      SyncGuildMembersPacket(
          replace = true,
          members =
              guild.members.map { member ->
                GuildMemberEntry(
                    entityId = member.id,
                    rank = member.rank.ordinal.toByte(),
                    joinedAt = member.joinedAt.epochSecond.toInt(),
                    name = member.name,
                    online = sessionRegistry.getByCharacterId(member.id) != null,
                    lastSeen = 0,
                    appearance = List(5) { 0 },
                    leader = member.leader,
                )
              },
      )

  private fun buildActivityLog(guild: Guild): GuildActivityLogPacket =
      GuildActivityLogPacket(
          totalCount = guild.activityLog.size.toShort(),
          entries =
              guild.activityLog.takeLast(MAX_ACTIVITY_LOG_ENTRIES).map { entry ->
                GuildActivityLogEntry(
                    type = entry.type.code,
                    actor = entry.actor,
                    target = entry.target,
                    timestamp = entry.timestamp,
                )
              },
      )
}
