package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank

/**
 * Stands in for the database. Rows are copied on the way in and out so a test cannot pass by
 * holding the same object the store holds, which is exactly the mistake a restart would expose.
 */
class FakeGuildRepository : GuildRepository {
  private val rows = linkedMapOf<Long, Guild>()

  override suspend fun loadAll(): List<Guild> = rows.values.map { it.deepCopy() }

  override suspend fun insertGuild(guild: Guild) {
    rows[guild.id] = guild.deepCopy()
  }

  override suspend fun deleteGuild(guildId: Long) {
    rows.remove(guildId)
  }

  override suspend fun addMember(guildId: Long, member: GuildMember) {
    rows[guildId]?.members?.add(member)
  }

  override suspend fun removeMember(guildId: Long, characterId: Long) {
    rows[guildId]?.members?.removeAll { it.id == characterId }
  }

  override suspend fun replaceMembers(guildId: Long, members: List<GuildMember>) {
    rows[guildId]?.members?.apply {
      clear()
      addAll(members)
    }
  }

  override suspend fun appendLog(guildId: Long, entry: GuildLogEntry) {
    rows[guildId]?.activityLog?.add(entry)
  }

  override suspend fun replacePermissions(
      guildId: Long,
      permissions: Map<GuildRank, Set<GuildPermission>>,
  ) {
    rows[guildId]?.permissions?.apply {
      clear()
      putAll(permissions)
    }
  }

  override suspend fun setMotd(guildId: Long, motd: String) {
    rows[guildId]?.motd = motd
  }

  override suspend fun setRankLabel(guildId: Long, rank: GuildRank, label: String) {
    rows[guildId]?.rankLabels?.put(rank, label)
  }

  private fun Guild.deepCopy(): Guild =
      copy(
          members = members.toMutableList(),
          permissions = permissions.toMutableMap(),
          activityLog = activityLog.toMutableList(),
          rankLabels = rankLabels.toMutableMap(),
      )
}

class FakeSocialRepository : SocialRepository {
  private val friends = mutableMapOf<Int, MutableSet<String>>()
  private val blocked = mutableMapOf<Int, MutableSet<String>>()

  override suspend fun friendsOf(userId: Int): Set<String> = friends[userId].orEmpty().toSet()

  override suspend fun blockedBy(userId: Int): Set<String> = blocked[userId].orEmpty().toSet()

  override suspend fun addFriend(userId: Int, name: String) {
    friends.getOrPut(userId) { linkedSetOf() }.add(name)
  }

  override suspend fun removeFriend(userId: Int, name: String): Boolean =
      friends[userId]?.remove(name) == true

  override suspend fun block(userId: Int, name: String, reason: String) {
    blocked.getOrPut(userId) { linkedSetOf() }.add(name)
  }

  override suspend fun unblock(userId: Int, name: String): Boolean =
      blocked[userId]?.remove(name) == true
}
