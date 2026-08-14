package de.fiereu.openmmo.server.game.storage

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

/** PokeMMO calls a player group a link, and a raid wants four of them. */
const val MAX_LINK_SIZE = 4

data class LinkMember(val charId: Long, val name: String)

class Link(val id: Long, val members: MutableList<LinkMember>) {
  /** The first member is the leader. Leaving hands it to whoever is next. */
  val leader: LinkMember
    get() = members.first()

  val full: Boolean
    get() = members.size >= MAX_LINK_SIZE

  fun has(charId: Long): Boolean = members.any { it.charId == charId }
}

/**
 * Links live only as long as the players are online, so unlike a guild there is nothing to persist.
 * A link that empties out is dropped rather than kept for its members to come back to.
 */
@Singleton
class LinkStore @Inject constructor() {
  private val links = ConcurrentHashMap<Long, Link>()
  private val linkByChar = ConcurrentHashMap<Long, Long>()
  private val nextId = AtomicLong(1)

  fun forChar(charId: Long): Link? = linkByChar[charId]?.let { links[it] }

  /** Whether the link is still live, as opposed to one [remove] has just disbanded. */
  fun contains(link: Link): Boolean = links.containsKey(link.id)

  /** Starts a link around [leader], or returns the one they are already in. */
  fun create(leader: LinkMember): Link {
    forChar(leader.charId)?.let {
      return it
    }
    val link = Link(nextId.getAndIncrement(), mutableListOf(leader))
    links[link.id] = link
    linkByChar[leader.charId] = link.id
    return link
  }

  /** False when the link filled up or the player is already in one. */
  fun add(link: Link, member: LinkMember): Boolean {
    if (link.full || linkByChar.containsKey(member.charId)) return false
    link.members.add(member)
    linkByChar[member.charId] = link.id
    return true
  }

  /**
   * Removes [charId] and disbands the link once fewer than two players are left. The returned link
   * lists whoever was still in it, which is who has to be told; use [contains] to ask whether it
   * survived.
   */
  fun remove(charId: Long): Link? {
    val link = forChar(charId) ?: return null
    link.members.removeAll { it.charId == charId }
    linkByChar.remove(charId)
    if (link.members.size < 2) {
      link.members.forEach { linkByChar.remove(it.charId) }
      val emptied = Link(link.id, link.members.toMutableList())
      link.members.clear()
      links.remove(link.id)
      return emptied
    }
    return link
  }
}
