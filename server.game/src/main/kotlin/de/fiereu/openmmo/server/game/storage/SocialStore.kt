package de.fiereu.openmmo.server.game.storage

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Friend and block lists, per account. Both are read on nearly every chat message, so each account
 * is cached on first touch and the cache is kept in step with the writes.
 */
@Singleton
class SocialStore
@Inject
constructor(
    private val repository: SocialRepository,
) {
  private val friendsByUser = ConcurrentHashMap<Int, MutableSet<String>>()
  private val blockedByUser = ConcurrentHashMap<Int, MutableSet<String>>()

  suspend fun getFriends(userId: Int): Set<String> = cachedFriends(userId).toSet()

  suspend fun getBlocked(userId: Int): Set<String> = cachedBlocked(userId).toSet()

  /** The cached view only, for callers that must not suspend. Empty until [getBlocked] warms it. */
  fun blockedCached(userId: Int): Set<String> = blockedByUser[userId].orEmpty()

  suspend fun addFriend(userId: Int, name: String) {
    cachedFriends(userId).add(name)
    repository.addFriend(userId, name)
  }

  suspend fun removeFriend(userId: Int, name: String): Boolean {
    cachedFriends(userId).remove(name)
    return repository.removeFriend(userId, name)
  }

  suspend fun block(userId: Int, name: String, reason: String) {
    cachedBlocked(userId).add(name)
    repository.block(userId, name, reason)
  }

  suspend fun unblock(userId: Int, name: String): Boolean {
    cachedBlocked(userId).remove(name)
    return repository.unblock(userId, name)
  }

  private suspend fun cachedFriends(userId: Int): MutableSet<String> =
      friendsByUser[userId]
          ?: repository.friendsOf(userId).toMutableSet().also { friendsByUser[userId] = it }

  private suspend fun cachedBlocked(userId: Int): MutableSet<String> =
      blockedByUser[userId]
          ?: repository.blockedBy(userId).toMutableSet().also { blockedByUser[userId] = it }
}
