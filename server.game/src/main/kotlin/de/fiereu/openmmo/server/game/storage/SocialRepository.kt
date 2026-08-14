package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.db.game.tables.references.ACCOUNT_BLOCKS
import de.fiereu.openmmo.db.game.tables.references.ACCOUNT_FRIENDS
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

interface SocialRepository {
  suspend fun friendsOf(userId: Int): Set<String>

  suspend fun blockedBy(userId: Int): Set<String>

  suspend fun addFriend(userId: Int, name: String)

  suspend fun removeFriend(userId: Int, name: String): Boolean

  suspend fun block(userId: Int, name: String, reason: String)

  suspend fun unblock(userId: Int, name: String): Boolean
}

@Singleton
class JooqSocialRepository
@Inject
constructor(
    private val dsl: DSLContext,
    @param:Named("db") private val dispatcher: CoroutineDispatcher,
) : SocialRepository {

  override suspend fun friendsOf(userId: Int): Set<String> =
      withContext(dispatcher) {
        dsl.select(ACCOUNT_FRIENDS.NAME)
            .from(ACCOUNT_FRIENDS)
            .where(ACCOUNT_FRIENDS.USER_ID.eq(userId))
            .orderBy(ACCOUNT_FRIENDS.ADDED_AT.asc())
            .fetch { it.value1() }
            .toCollection(LinkedHashSet())
      }

  override suspend fun blockedBy(userId: Int): Set<String> =
      withContext(dispatcher) {
        dsl.select(ACCOUNT_BLOCKS.NAME)
            .from(ACCOUNT_BLOCKS)
            .where(ACCOUNT_BLOCKS.USER_ID.eq(userId))
            .fetch { it.value1() }
            .toCollection(LinkedHashSet())
      }

  override suspend fun addFriend(userId: Int, name: String) =
      withContext(dispatcher) {
        dsl.insertInto(ACCOUNT_FRIENDS)
            .set(ACCOUNT_FRIENDS.USER_ID, userId)
            .set(ACCOUNT_FRIENDS.NAME, name)
            .set(ACCOUNT_FRIENDS.ADDED_AT, LocalDateTime.now(ZoneOffset.UTC))
            .onConflictDoNothing()
            .execute()
        Unit
      }

  override suspend fun removeFriend(userId: Int, name: String): Boolean =
      withContext(dispatcher) {
        dsl.deleteFrom(ACCOUNT_FRIENDS)
            .where(ACCOUNT_FRIENDS.USER_ID.eq(userId))
            .and(ACCOUNT_FRIENDS.NAME.eq(name))
            .execute() > 0
      }

  override suspend fun block(userId: Int, name: String, reason: String) =
      withContext(dispatcher) {
        dsl.insertInto(ACCOUNT_BLOCKS)
            .set(ACCOUNT_BLOCKS.USER_ID, userId)
            .set(ACCOUNT_BLOCKS.NAME, name)
            .set(ACCOUNT_BLOCKS.REASON, reason)
            .set(ACCOUNT_BLOCKS.BLOCKED_AT, LocalDateTime.now(ZoneOffset.UTC))
            .onConflict(ACCOUNT_BLOCKS.USER_ID, ACCOUNT_BLOCKS.NAME)
            .doUpdate()
            .set(ACCOUNT_BLOCKS.REASON, reason)
            .execute()
        Unit
      }

  override suspend fun unblock(userId: Int, name: String): Boolean =
      withContext(dispatcher) {
        dsl.deleteFrom(ACCOUNT_BLOCKS)
            .where(ACCOUNT_BLOCKS.USER_ID.eq(userId))
            .and(ACCOUNT_BLOCKS.NAME.eq(name))
            .execute() > 0
      }
}
