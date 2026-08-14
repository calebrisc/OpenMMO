package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

data class GuildMember(
    /** The member's character id, which is also the entity id the client addresses them by. */
    val id: Long,
    val name: String,
    val rank: GuildRank,
    val leader: Boolean,
    val joinedAt: Instant = Instant.EPOCH,
)

// Wire codes are provisional and unverified against the real client, so only the two the client
// has been seen to render live here. The name is what persists, so adding one stays a code change.
enum class GuildActivityType(val code: Int) {
  FOUNDED(0),
  JOINED(1),
}

data class GuildLogEntry(
    val seq: Int,
    val type: GuildActivityType,
    val actor: String,
    val target: String,
    val timestamp: Int,
)

data class Guild(
    val id: Long,
    val name: String,
    val tag: String,
    val members: MutableList<GuildMember>,
    val permissions: MutableMap<GuildRank, Set<GuildPermission>> = mutableMapOf(),
    val activityLog: MutableList<GuildLogEntry> = mutableListOf(),
    val rankLabels: MutableMap<GuildRank, String> = mutableMapOf(),
    @field:Volatile var motd: String = "",
    val foundedAt: Instant = Instant.EPOCH,
)

/**
 * Guilds in memory, written through to [GuildRepository] on every change. Guild edits are rare
 * enough that a write-through costs nothing, and it keeps the founding fee and the guild it paid
 * for landing together.
 */
@Singleton
class GuildStore
@Inject
constructor(
    private val repository: GuildRepository,
) {
  private val guilds = ConcurrentHashMap<Long, Guild>()
  private val guildByChar = ConcurrentHashMap<Long, Long>()
  private val nextId = AtomicLong(1)

  /** Called once at startup, before the server accepts connections. */
  suspend fun loadAll() {
    val loaded = repository.loadAll()
    for (guild in loaded) {
      guilds[guild.id] = guild
      guild.members.forEach { guildByChar[it.id] = guild.id }
    }
    nextId.set((loaded.maxOfOrNull { it.id } ?: 0L) + 1)
  }

  suspend fun createGuild(
      name: String,
      tag: String,
      leaderId: Long,
      leaderName: String,
  ): Guild {
    val now = Instant.now()
    val leader = GuildMember(leaderId, leaderName, GuildRank.BOSS, leader = true, joinedAt = now)
    val guild =
        Guild(
            id = nextId.getAndIncrement(),
            name = name,
            tag = tag,
            members = mutableListOf(leader),
            foundedAt = now,
        )
    guild.activityLog.add(
        GuildLogEntry(
            seq = 0,
            type = GuildActivityType.FOUNDED,
            actor = leaderName,
            target = "",
            timestamp = now.epochSecond.toInt()))
    guilds[guild.id] = guild
    guildByChar[leaderId] = guild.id
    repository.insertGuild(guild)
    return guild
  }

  fun getGuildForChar(charId: Long): Guild? = guildByChar[charId]?.let { guilds[it] }

  fun isMember(guild: Guild, charId: Long): Boolean = guild.members.any { it.id == charId }

  suspend fun addMember(guild: Guild, member: GuildMember) {
    guild.members.add(member)
    guildByChar[member.id] = guild.id
    repository.addMember(guild.id, member)
    appendLog(guild, GuildActivityType.JOINED, member.name)
  }

  suspend fun setMemberRank(guild: Guild, entityId: Long, rank: GuildRank) {
    val index = guild.members.indexOfFirst { it.id == entityId }
    if (index < 0) return
    val updated = guild.members[index].copy(rank = rank)
    guild.members[index] = updated
    repository.replaceMembers(guild.id, guild.members.toList())
  }

  suspend fun removeMember(guild: Guild, entityId: Long) {
    if (!guild.members.removeAll { it.id == entityId }) return
    guildByChar.remove(entityId)
    repository.removeMember(guild.id, entityId)
  }

  suspend fun transferLeadership(guild: Guild, newLeaderId: Long) {
    if (guild.members.none { it.id == newLeaderId }) return
    for (i in guild.members.indices) {
      val member = guild.members[i]
      guild.members[i] =
          when {
            member.id == newLeaderId -> member.copy(rank = GuildRank.BOSS, leader = true)
            member.leader || member.rank == GuildRank.BOSS ->
                member.copy(rank = GuildRank.EXECUTIVE, leader = false)
            else -> member
          }
    }
    repository.replaceMembers(guild.id, guild.members.toList())
  }

  suspend fun leaveGuild(charId: Long) {
    val guildId = guildByChar.remove(charId) ?: return
    guilds[guildId]?.members?.removeAll { it.id == charId }
    repository.removeMember(guildId, charId)
  }

  suspend fun disbandGuild(charId: Long) {
    val guildId = guildByChar[charId] ?: return
    guilds.remove(guildId)
    guildByChar.entries.removeIf { it.value == guildId }
    repository.deleteGuild(guildId)
  }

  suspend fun setPermissions(guild: Guild, permissions: Map<GuildRank, Set<GuildPermission>>) {
    guild.permissions.clear()
    guild.permissions.putAll(permissions)
    repository.replacePermissions(guild.id, permissions)
  }

  suspend fun setMotd(guild: Guild, motd: String) {
    guild.motd = motd
    repository.setMotd(guild.id, motd)
  }

  suspend fun setRankLabel(guild: Guild, rank: GuildRank, label: String) {
    guild.rankLabels[rank] = label
    repository.setRankLabel(guild.id, rank, label)
  }

  private suspend fun appendLog(guild: Guild, type: GuildActivityType, actor: String) {
    val entry =
        GuildLogEntry(
            seq = (guild.activityLog.maxOfOrNull { it.seq } ?: -1) + 1,
            type = type,
            actor = actor,
            target = "",
            timestamp = Instant.now().epochSecond.toInt(),
        )
    guild.activityLog.add(entry)
    repository.appendLog(guild.id, entry)
  }
}
