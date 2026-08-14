package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank
import de.fiereu.openmmo.db.game.tables.references.GUILDS
import de.fiereu.openmmo.db.game.tables.references.GUILD_LOG
import de.fiereu.openmmo.db.game.tables.references.GUILD_MEMBERS
import de.fiereu.openmmo.db.game.tables.references.GUILD_PERMISSIONS
import de.fiereu.openmmo.db.game.tables.references.GUILD_RANK_LABELS
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

interface GuildRepository {
  suspend fun loadAll(): List<Guild>

  suspend fun insertGuild(guild: Guild)

  suspend fun deleteGuild(guildId: Long)

  suspend fun addMember(guildId: Long, member: GuildMember)

  suspend fun removeMember(guildId: Long, characterId: Long)

  /** Rewrites every member row, which is what a leadership transfer needs. */
  suspend fun replaceMembers(guildId: Long, members: List<GuildMember>)

  suspend fun appendLog(guildId: Long, entry: GuildLogEntry)

  suspend fun replacePermissions(guildId: Long, permissions: Map<GuildRank, Set<GuildPermission>>)

  suspend fun setMotd(guildId: Long, motd: String)

  suspend fun setRankLabel(guildId: Long, rank: GuildRank, label: String)
}

@Singleton
class JooqGuildRepository
@Inject
constructor(
    private val dsl: DSLContext,
    @param:Named("db") private val dispatcher: CoroutineDispatcher,
) : GuildRepository {

  override suspend fun loadAll(): List<Guild> =
      withContext(dispatcher) {
        val members =
            dsl.selectFrom(GUILD_MEMBERS).fetch().groupBy({ it.guildId }) {
              GuildMember(
                  id = it.characterId,
                  name = it.name,
                  rank = GuildRank.valueOf(it.rank),
                  leader = it.leader,
                  joinedAt = it.joinedAt.toInstant(ZoneOffset.UTC),
              )
            }
        val permissions =
            dsl.selectFrom(GUILD_PERMISSIONS)
                .fetch()
                .groupBy { it.guildId }
                .mapValues { (_, rows) ->
                  rows
                      .groupBy { GuildRank.valueOf(it.rank) }
                      .mapValues { (_, perms) ->
                        perms.map { GuildPermission.valueOf(it.permission) }.toSet()
                      }
                }
        val labels =
            dsl.selectFrom(GUILD_RANK_LABELS)
                .fetch()
                .groupBy { it.guildId }
                .mapValues { (_, rows) ->
                  rows.associate { GuildRank.valueOf(it.rank) to it.label }
                }
        val log =
            dsl.selectFrom(GUILD_LOG).orderBy(GUILD_LOG.SEQ.asc()).fetch().groupBy({ it.guildId }) {
              GuildLogEntry(
                  seq = it.seq,
                  type = GuildActivityType.valueOf(it.type),
                  actor = it.actor,
                  target = it.target,
                  timestamp = it.createdAt,
              )
            }
        dsl.selectFrom(GUILDS).fetch().map { row ->
          Guild(
              id = row.id,
              name = row.name,
              tag = row.tag,
              members = members[row.id].orEmpty().toMutableList(),
              permissions = permissions[row.id].orEmpty().toMutableMap(),
              activityLog = log[row.id].orEmpty().toMutableList(),
              rankLabels = labels[row.id].orEmpty().toMutableMap(),
              motd = row.motd,
              foundedAt = row.foundedAt.toInstant(ZoneOffset.UTC),
          )
        }
      }

  override suspend fun insertGuild(guild: Guild) =
      withContext(dispatcher) {
        dsl.transaction { cfg ->
          val tx = cfg.dsl()
          val now = LocalDateTime.ofInstant(guild.foundedAt, ZoneOffset.UTC)
          tx.insertInto(GUILDS)
              .set(GUILDS.ID, guild.id)
              .set(GUILDS.NAME, guild.name)
              .set(GUILDS.TAG, guild.tag)
              .set(GUILDS.MOTD, guild.motd)
              .set(GUILDS.FOUNDED_AT, now)
              .set(GUILDS.UPDATED_AT, now)
              .execute()
          guild.members.forEach { tx.insertMember(guild.id, it) }
          guild.activityLog.forEach { tx.insertLog(guild.id, it) }
        }
      }

  override suspend fun deleteGuild(guildId: Long) =
      withContext(dispatcher) {
        // Every child table cascades from guilds.
        dsl.deleteFrom(GUILDS).where(GUILDS.ID.eq(guildId)).execute()
        Unit
      }

  override suspend fun addMember(guildId: Long, member: GuildMember) =
      withContext(dispatcher) { dsl.insertMember(guildId, member) }

  override suspend fun removeMember(guildId: Long, characterId: Long) =
      withContext(dispatcher) {
        dsl.deleteFrom(GUILD_MEMBERS)
            .where(GUILD_MEMBERS.GUILD_ID.eq(guildId))
            .and(GUILD_MEMBERS.CHARACTER_ID.eq(characterId))
            .execute()
        Unit
      }

  override suspend fun replaceMembers(guildId: Long, members: List<GuildMember>) =
      withContext(dispatcher) {
        dsl.transaction { cfg ->
          val tx = cfg.dsl()
          tx.deleteFrom(GUILD_MEMBERS).where(GUILD_MEMBERS.GUILD_ID.eq(guildId)).execute()
          members.forEach { tx.insertMember(guildId, it) }
        }
      }

  override suspend fun appendLog(guildId: Long, entry: GuildLogEntry) =
      withContext(dispatcher) { dsl.insertLog(guildId, entry) }

  override suspend fun replacePermissions(
      guildId: Long,
      permissions: Map<GuildRank, Set<GuildPermission>>,
  ) =
      withContext(dispatcher) {
        dsl.transaction { cfg ->
          val tx = cfg.dsl()
          tx.deleteFrom(GUILD_PERMISSIONS).where(GUILD_PERMISSIONS.GUILD_ID.eq(guildId)).execute()
          permissions.forEach { (rank, perms) ->
            perms.forEach { permission ->
              tx.insertInto(GUILD_PERMISSIONS)
                  .set(GUILD_PERMISSIONS.GUILD_ID, guildId)
                  .set(GUILD_PERMISSIONS.RANK, rank.name)
                  .set(GUILD_PERMISSIONS.PERMISSION, permission.name)
                  .execute()
            }
          }
        }
      }

  override suspend fun setMotd(guildId: Long, motd: String) =
      withContext(dispatcher) {
        dsl.update(GUILDS)
            .set(GUILDS.MOTD, motd)
            .set(GUILDS.UPDATED_AT, LocalDateTime.now(ZoneOffset.UTC))
            .where(GUILDS.ID.eq(guildId))
            .execute()
        Unit
      }

  override suspend fun setRankLabel(guildId: Long, rank: GuildRank, label: String) =
      withContext(dispatcher) {
        dsl.insertInto(GUILD_RANK_LABELS)
            .set(GUILD_RANK_LABELS.GUILD_ID, guildId)
            .set(GUILD_RANK_LABELS.RANK, rank.name)
            .set(GUILD_RANK_LABELS.LABEL, label)
            .onConflict(GUILD_RANK_LABELS.GUILD_ID, GUILD_RANK_LABELS.RANK)
            .doUpdate()
            .set(GUILD_RANK_LABELS.LABEL, label)
            .execute()
        Unit
      }

  private fun DSLContext.insertMember(guildId: Long, member: GuildMember) {
    insertInto(GUILD_MEMBERS)
        .set(GUILD_MEMBERS.GUILD_ID, guildId)
        .set(GUILD_MEMBERS.CHARACTER_ID, member.id)
        .set(GUILD_MEMBERS.NAME, member.name)
        .set(GUILD_MEMBERS.RANK, member.rank.name)
        .set(GUILD_MEMBERS.LEADER, member.leader)
        .set(GUILD_MEMBERS.JOINED_AT, LocalDateTime.ofInstant(member.joinedAt, ZoneOffset.UTC))
        .execute()
  }

  private fun DSLContext.insertLog(guildId: Long, entry: GuildLogEntry) {
    insertInto(GUILD_LOG)
        .set(GUILD_LOG.GUILD_ID, guildId)
        .set(GUILD_LOG.SEQ, entry.seq)
        .set(GUILD_LOG.TYPE, entry.type.name)
        .set(GUILD_LOG.ACTOR, entry.actor)
        .set(GUILD_LOG.TARGET, entry.target)
        .set(GUILD_LOG.CREATED_AT, entry.timestamp)
        .execute()
  }
}
