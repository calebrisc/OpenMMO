package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleMonState
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleRng
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.session.SessionRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.LinkStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/** How much tougher a boss is than the monster it is built from. */
private const val BOSS_HP_MULTIPLIER = 8
private const val DEFAULT_BOSS_LEVEL = 30
private const val MAX_RAIDERS = 4

/** How many waiting players it takes to form a raid without anybody arranging it. */
private const val QUEUE_TARGET = 2

/** A boss and everybody currently fighting it. */
private class Raid(val boss: BattleMonState, val bossName: String) {
  val raiders = mutableSetOf<Long>()
  var finished = false
}

/**
 * Raids: several players against one boss.
 *
 * Every raider gets their own battle holding the very same boss, rather than one battle holding
 * every raider. The client draws one monster per side and nothing found so far persuades it to draw
 * two, so a shared battle would have been a shared screen it cannot render. Sharing the monster
 * instead of the screen gives the part that matters, which is that all of the damage lands on one
 * pool of health and the boss falls to the group rather than to any one of them.
 *
 * What it costs is the sight of each other: a raider sees their own fight, not their teammates'
 * monsters. Everything else, the shared boss, the shared health and the shared outcome, is real.
 */
@Singleton
class RaidService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val battles: BattleService,
    private val sessionRegistry: SessionRegistry,
    private val speciesRegistry: SpeciesRegistry,
    private val wildMons: WildMonFactory,
    private val linkStore: LinkStore,
    private val battleRegistry: BattleRegistry,
) {

  private val raids = ConcurrentHashMap<Long, Raid>()

  /** Players waiting for a raid to form, in the order they asked. */
  private val queue = LinkedHashSet<Long>()

  init {
    // The boss takes damage in whichever raider's battle landed it, so without this the others
    // would have no way of seeing the pool they are all working on go down.
    battles.onTurnResolved { charId -> reportProgress(charId) }
  }

  /** Tells the other raiders what the boss has left after one of them hit it. */
  private fun reportProgress(charId: Long) {
    val raid = raids[charId] ?: return
    val boss = raid.boss
    if (boss.currentHp <= 0) return
    val hitter = characterStore.getCharacter(charId)?.info?.name ?: "A raider"
    val percent = (boss.currentHp * 100) / boss.stats.hp.coerceAtLeast(1)
    val update = notice("$hitter attacked ${raid.bossName}: $percent% left.")
    raid.raiders.filter { it != charId }.forEach {
      sessionRegistry.getByCharacterId(it)?.send(update)
    }
  }

  fun describe(charId: Long): String {
    purge(charId)
    val raid =
        raids[charId]
            ?: run {
              val waiting = synchronized(queue) { queue.size }
              return if (charId in synchronized(queue) { queue.toList() })
                  "Waiting for a raid ($waiting in the queue). /raid queue leave to stop waiting."
              else "You are not in a raid. /raid queue to wait for one, or /raid start to begin one."
            }
    val names = raid.raiders.mapNotNull { characterStore.getCharacter(it)?.info?.name }
    return "Raid against ${raid.bossName}: ${raid.boss.currentHp}/${raid.boss.stats.hp} hp left. " +
        "Raiders: ${names.joinToString()}."
  }

  /**
   * Starts a raid for the caller and every squad member who is online. Without a squad it is a solo
   * boss fight, which is a fair fight to lose and the only way to try one alone.
   */
  fun start(charId: Long, dexId: Int?, level: Int?): String {
    purge(charId)
    if (raids.containsKey(charId)) return "You are already in a raid."

    val squad =
        linkStore.forChar(charId)?.members?.map { it.charId }?.toSet() ?: setOf(charId)
    val party = (squad + charId).take(MAX_RAIDERS)
    party.forEach { purge(it) }
    val busy = party.filter { raids.containsKey(it) }
    if (busy.isNotEmpty()) return "Somebody in your squad is already raiding."
    return launch(party, dexId, level)
  }

  /** Builds the boss and drops every one of [party] into their own battle against it. */
  private fun launch(party: List<Long>, dexId: Int?, level: Int?): String {
    val bossLevel = (level ?: DEFAULT_BOSS_LEVEL).coerceIn(2, 100)
    val bossDex = dexId ?: DEFAULT_BOSSES.random()
    val species = speciesRegistry.get(bossDex) ?: return "There is no species $bossDex."
    val rolled =
        wildMons.create(bossDex, bossLevel, BattleRng())
            ?: return "That species cannot be built yet."
    // Perfect stats and a deep pool of health, so it takes a group rather than one lucky turn.
    val base = StatCalculator.computeAll(species, rolled)
    // A deeper pool of health is what makes it a raid rather than a wild encounter: one player
    // cannot chew through it alone in the turns they have.
    val bossStats = base.copy(hp = base.hp * BOSS_HP_MULTIPLIER)
    val source = rolled.copy(hp = bossStats.hp.toShort())
    val boss = BattleMonState(source.id, species, null, source, bossStats)

    val raid = Raid(boss, species.name)
    val started = mutableListOf<String>()
    for (raiderId in party) {
      val session = sessionRegistry.getByCharacterId(raiderId) ?: continue
      if (battles.startSharedBossBattle(session, boss) == null) continue
      raid.raiders += raiderId
      raids[raiderId] = raid
      started += characterStore.getCharacter(raiderId)?.info?.name ?: "?"
    }
    if (raid.raiders.isEmpty()) {
      return "The raid could not be started."
    }
    log.info { "Raid against ${species.name} level $bossLevel started with $started" }
    val announcement =
        notice(
            "A raid against ${species.name} has begun. " +
                "You are all fighting the same one, so every hit counts.")
    raid.raiders.forEach { sessionRegistry.getByCharacterId(it)?.send(announcement) }
    return "Raid started against ${species.name} with ${started.size} raider(s)."
  }

  /**
   * Joins the raid queue and carries on playing.
   *
   * Nobody has to be in a squad, or in a voice call, or even know each other. Players wait in the
   * overworld and a raid forms itself the moment enough of them are waiting, which is the only way
   * a group comes together on a server where not everyone is talking to each other.
   */
  fun joinQueue(charId: Long): String {
    purge(charId)
    if (raids.containsKey(charId)) return "You are already in a raid."
    if (battleRegistry.byChar(charId) != null) return "Finish your battle first."
    val waiting: Int
    synchronized(queue) {
      if (!queue.add(charId)) return "You are already in the queue."
      waiting = queue.size
    }
    val name = characterStore.getCharacter(charId)?.info?.name ?: "Somebody"
    announceToQueue("$name is waiting for a raid ($waiting in the queue).", except = charId)
    val formed = tryForm()
    if (formed != null) return formed
    return "In the raid queue ($waiting waiting). Carry on playing, you will be pulled in " +
        "when $QUEUE_TARGET are ready."
  }

  fun leaveQueue(charId: Long): String =
      synchronized(queue) {
        if (queue.remove(charId)) "You left the raid queue." else "You are not in the queue."
      }

  /**
   * Forms a raid as soon as enough free players are waiting.
   *
   * Anyone who has gone offline or wandered into a battle since queueing is passed over rather than
   * dragged out of it, and stays in the queue for the next attempt.
   */
  private fun tryForm(): String? {
    val group: List<Long>
    synchronized(queue) {
      val free =
          queue.filter {
            sessionRegistry.getByCharacterId(it) != null &&
                battleRegistry.byChar(it) == null &&
                !raids.containsKey(it)
          }
      if (free.size < QUEUE_TARGET) return null
      group = free.take(MAX_RAIDERS)
      queue.removeAll(group.toSet())
    }
    val names = group.mapNotNull { characterStore.getCharacter(it)?.info?.name }
    log.info { "Raid queue formed a group of ${group.size}: $names" }
    group.forEach {
      sessionRegistry
          .getByCharacterId(it)
          ?.send(notice("The queue found you a raid with ${names.joinToString()}."))
    }
    return launch(group, null, null)
  }

  private fun announceToQueue(message: String, except: Long) {
    val packet = notice(message)
    val waiting = synchronized(queue) { queue.toList() }
    waiting.filter { it != except }.forEach { sessionRegistry.getByCharacterId(it)?.send(packet) }
  }

  /**
   * Puts a player into a raid somebody else is already in, for a raider who joined after it began.
   */
  fun enrol(charId: Long, existingRaiderId: Long): Boolean {
    val raid = raids[existingRaiderId] ?: return false
    raid.raiders += charId
    raids[charId] = raid
    return true
  }

  /** Leaves the raid's bookkeeping behind. The battle itself ends the ordinary way. */
  fun leave(charId: Long) {
    val raid = raids.remove(charId) ?: return
    raid.raiders.remove(charId)
    if (raid.raiders.isEmpty()) return
    val name = characterStore.getCharacter(charId)?.info?.name ?: "A raider"
    raid.raiders.forEach {
      sessionRegistry.getByCharacterId(it)?.send(notice("$name left the raid."))
    }
  }

  fun onDisconnect(charId: Long) {
    synchronized(queue) { queue.remove(charId) }
    leave(charId)
  }

  /**
   * Forgets a raid whose battle is over.
   *
   * A raid's battles end through the ordinary path and tell nobody, so without this the entry would
   * outlive the fight and every later raid would be refused as one already in progress. The battle
   * having gone is what says the raid is over, which is true whether the boss fell or the raider
   * did.
   */
  private fun purge(charId: Long) {
    val raid = raids[charId] ?: return
    if (battleRegistry.byChar(charId) != null) return
    raids.remove(charId)
    raid.raiders.remove(charId)
    if (raid.finished || raid.boss.currentHp > 0) return
    // The first raider to be noticed leaving a felled boss announces it, once.
    raid.finished = true
    val won = notice("${raid.bossName} has been defeated. The raid is over.")
    raid.raiders.forEach { sessionRegistry.getByCharacterId(it)?.send(won) }
  }

  private companion object {
    /** Kanto's heavier hitters, so a raid feels like one whatever the group brings. */
    val DEFAULT_BOSSES = listOf(59, 68, 76, 94, 112, 130, 143, 149)
  }
}
