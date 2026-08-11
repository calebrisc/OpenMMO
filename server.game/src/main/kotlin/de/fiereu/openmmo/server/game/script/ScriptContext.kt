package de.fiereu.openmmo.server.game.script

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.common.dialog.DialogLine
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.items.ItemDef
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.dialog.TextPokemonSpeciesArg
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.services.BattleService
import de.fiereu.openmmo.server.game.services.DialogPresentation
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.MapEntryScripts
import de.fiereu.openmmo.server.game.services.ScriptMovementService
import de.fiereu.openmmo.server.game.services.ScriptWarpService
import de.fiereu.openmmo.server.game.services.ShopService
import de.fiereu.openmmo.server.game.services.StoryClientState
import de.fiereu.openmmo.server.game.services.StoryPlayerService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.services.notice
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore

/** What a [Script] uses to talk to the player it interacted with and read or write story state. */
class ScriptContext
internal constructor(
    internal val session: SessionContext,
    internal val state: PlayerState,
    /** The npc the player talked to, or -1 for a sign. */
    val entityId: Long,
    internal val dialog: DialogService,
    internal val story: StoryService,
    private val movement: ScriptMovementService,
    private val warp: ScriptWarpService? = null,
    internal val player: StoryPlayerService? = null,
    private val battles: BattleService? = null,
    internal val characters: CharacterStore? = null,
    private val maps: MapManager? = null,
    private val entryScripts: MapEntryScripts? = null,
    private val shops: ShopService? = null,
) {
  private val characterId: Long?
    get() = state.characterId

  val facingDirection: Direction
    get() = state.facingDirection

  val isFemale: Boolean
    get() = movement.playerGender(state) == FEMALE

  internal val playerEntityId: Long
    get() = checkNotNull(state.characterId) { "Scene has no selected character" }

  internal val playerName: String
    get() = state.characterId?.let { characters?.getCharacter(it)?.info?.name }.orEmpty()

  internal fun send(packet: Any) = session.send(packet)

  /** Show [line] as a sign and wait for the player to close it. */
  suspend fun sign(line: DialogLine) = dialog.showAndWait(session, state, line.textId, SIGN, -1)

  /** Show [line] from the interacted entity and wait for the player to go on. */
  suspend fun say(line: DialogLine) = dialog.showAndWait(session, state, line.textId, NPC, entityId)

  /** Show [line] from a cutscene npc addressed by its decomp local id. */
  suspend fun sayNpc(localId: Int, line: DialogLine) =
      dialog.showAndWait(
          session,
          state,
          line.textId,
          NPC,
          movement.npcEntityId(state, localId) ?: -1,
      )

  /** Shows dialog with a species-name variable. */
  suspend fun sayNpcWithSpeciesName(localId: Int, line: DialogLine, speciesId: Int) =
      dialog.showAndWait(
          session,
          state,
          line.textId,
          NPC,
          movement.npcEntityId(state, localId) ?: -1,
          DialogPresentation(
              messageArgs =
                  listOf(
                      TextPokemonSpeciesArg(
                          partySlot = 1,
                          stringVariable = 1,
                          speciesId = speciesId.toShort(),
                      ))),
      )

  /** Opens the Emerald starter picker. */
  suspend fun chooseHoennStarter(): Int = dialog.chooseHoennStarter(session, state)

  /** Ask a ROM-backed yes/no question from the interacted entity. */
  suspend fun askYesNo(line: DialogLine): Boolean =
      dialog.askYesNo(session, state, line.textId, entityId)

  /** Ask a ROM-backed yes/no question from a cutscene npc. */
  suspend fun askYesNoNpc(localId: Int, line: DialogLine): Boolean =
      dialog.askYesNo(
          session,
          state,
          line.textId,
          movement.npcEntityId(state, localId) ?: -1,
      )

  /** True if the story [flag] is set. Keys come from the content layer, for example HoennFlags. */
  fun isFlagSet(flag: String): Boolean = characterId?.let { story.isFlagSet(it, flag) } ?: false

  fun setFlag(flag: String) {
    characterId?.let {
      story.setFlag(it, flag)
      StoryClientState.flagUpdate(state.regionId.toByte(), flag, enabled = true)?.let(session::send)
    }
  }

  fun clearFlag(flag: String) {
    characterId?.let {
      story.clearFlag(it, flag)
      StoryClientState.flagUpdate(state.regionId.toByte(), flag, enabled = false)
          ?.let(session::send)
    }
  }

  /** The story var [key], or 0 if it was never set. */
  fun getVar(key: String): Int = characterId?.let { story.getVar(it, key) } ?: 0

  fun setVar(key: String, value: Int) {
    characterId?.let { story.setVar(it, key, value) }
  }

  suspend fun givePokemon(dexId: Int, level: Int, vararg moveIds: Int) =
      checkNotNull(player) { STORY_PLAYER_UNAVAILABLE }
          .givePokemon(session, state, dexId, level, moveIds.toList())

  fun healParty() = checkNotNull(player) { STORY_PLAYER_UNAVAILABLE }.healParty(session, state)

  suspend fun giveItem(item: ItemDef, quantity: Int = 1): Boolean =
      checkNotNull(player) { STORY_PLAYER_UNAVAILABLE }.giveItem(session, state, item, quantity)

  /** Take an item back out of the bag, the decomp removeitem. False when the bag lacks it. */
  suspend fun takeItem(item: ItemDef, quantity: Int = 1): Boolean = giveItem(item, -quantity)

  /**
   * Opens the mart window on [items], the decomp pokemart. It does not wait: the player shops while
   * the script ends, because nothing in the capture tells the server when the window closed.
   */
  fun pokemart(vararg items: ItemDef) =
      checkNotNull(shops) { "Shop service is unavailable" }.open(session, entityId, items.toList())

  /** Run a non-catchable, non-escapable story battle and wait for its result. */
  suspend fun battle(dexId: Int, level: Int, vararg moveIds: Int): BattleResult =
      checkNotNull(battles) { "Battle service is unavailable" }
          .startScriptedBattle(session, dexId, level, moveIds.toList())

  /** The decomp dowildbattle: a scripted encounter the player may catch or flee, awaited. */
  suspend fun wildBattle(dexId: Int, level: Int): BattleResult =
      checkNotNull(battles) { "Battle service is unavailable" }
          .startScriptedBattle(session, dexId, level, catchable = true, escapable = true)

  /** Fight the decomp trainer with this id, using the region the player is standing in. */
  suspend fun trainerBattle(trainerId: Int): BattleResult {
    return checkNotNull(battles) { "Battle service is unavailable" }
        .startTrainerBattle(session, currentRegion(), trainerId)
  }

  /**
   * The decomp trainerbattle_single: a one time fight against a map trainer. Once the trainer is
   * beaten the fight is skipped, so the script falls through to its post battle line the way the
   * engine falls through on a set trainer flag. Returns false when the player did not win, which
   * ends the script before its post battle text.
   */
  suspend fun trainerBattleSingle(
      trainerId: Int,
      intro: DialogLine? = null,
      defeat: DialogLine? = null,
  ): Boolean {
    val flag = defeatedTrainerFlag(trainerId)
    if (isFlagSet(flag)) return true
    intro?.let { say(it) }
    if (trainerBattle(trainerId) != BattleResult.VICTORY) return false
    setFlag(flag)
    defeat?.let { say(it) }
    return true
  }

  /** True once [trainerId] of the player's current region has been beaten. */
  fun isTrainerDefeated(trainerId: Int): Boolean = isFlagSet(defeatedTrainerFlag(trainerId))

  /**
   * The shared gym leader shape: a one time fight whose first victory runs [onFirstWin] immediately
   * after the durable defeated flag and before any dialog, so a disconnect between the win and the
   * post battle lines cannot lose the badge. Returns false until the player has won.
   */
  suspend fun leaderBattle(
      trainerId: Int,
      intro: DialogLine? = null,
      defeat: DialogLine? = null,
      onFirstWin: () -> Unit = {},
  ): Boolean {
    val flag = defeatedTrainerFlag(trainerId)
    if (isFlagSet(flag)) return true
    intro?.let { say(it) }
    if (trainerBattle(trainerId) != BattleResult.VICTORY) return false
    setFlag(flag)
    onFirstWin()
    defeat?.let { say(it) }
    return true
  }

  /**
   * The decomp finditem: bag the item, announce it, and set the ball's hide flag so it stays gone.
   * When the bag has no room the ball stays, like the engine's "too bad" path.
   */
  suspend fun findItem(item: ItemDef, quantity: Int = 1) {
    // Without the ball there is no hide flag to persist the pickup, so granting anyway would
    // let the script hand the item out again on every interaction.
    val npc = movement.interactedNpc(state, entityId) ?: return
    if (npc.hideFlag.isNotEmpty() && isFlagSet(npc.hideFlag)) return
    if (!giveItem(item, quantity)) {
      send(notice("Your bag is too full to take the ${item.name}."))
      return
    }
    val what = if (quantity == 1) "one ${item.name}" else "${item.name} x$quantity"
    send(notice("$playerName found $what!"))
    if (npc.hideFlag.isNotEmpty()) setFlag(npc.hideFlag)
    movement.removeNpc(session, state, npc.entityIdx)
  }

  /** True when a party monster knows [moveId], the decomp checkpartymove. */
  fun partyHasMove(moveId: Int): Boolean =
      characterId?.let { id ->
        characters?.getCharacter(id)?.pokemon?.any { mon ->
          mon.moves.any { it.id.toInt() == moveId }
        }
      } ?: false

  /**
   * The decomp EventScript_CutTree: with the region's cut badge and a party monster that knows Cut,
   * the tree falls for this session. Like the GBA, it grows back on the next map entry.
   */
  suspend fun cutTree() {
    val badge =
        if (currentRegion() == Region.KANTO) "kanto/FLAG_BADGE02_GET" else "hoenn/FLAG_BADGE01_GET"
    if (!isFlagSet(badge) || !partyHasMove(MOVE_CUT)) {
      send(notice("A monster that knows CUT could fell this tree."))
      return
    }
    val npc = movement.interactedNpc(state, entityId) ?: return
    movement.removeNpc(session, state, npc.entityIdx)
    send(notice("The tree was cut down!"))
  }

  /** Remove the npc the player is talking to, for this session only. */
  fun despawnInteracted() {
    movement.interactedNpc(state, entityId)?.let {
      movement.removeNpc(session, state, it.entityIdx)
    }
  }

  private fun currentRegion(): Region =
      checkNotNull(Region.byWireValue(state.regionId.toByte())) {
        "Scene ran in unknown region ${state.regionId}"
      }

  private fun defeatedTrainerFlag(trainerId: Int): String =
      "${currentRegion().name.lowercase()}/TRAINER_DEFEATED_$trainerId"

  /**
   * Walk the map npc with decomp local id [localId] (its entityIdx) through [steps] and wait for
   * the whole path to finish. This is applymovement plus waitmovement for an npc.
   */
  suspend fun moveNpc(localId: Int, vararg steps: MovementStep) =
      movement.moveNpc(session, state, localId, steps.toList())

  /** Starts concurrent NPC movement paths. */
  suspend fun moveNpcs(vararg paths: Pair<Int, List<MovementStep>>) =
      movement.moveNpcs(session, state, paths.toList())

  /** Starts player and NPC paths together. */
  suspend fun moveSelfAndNpcs(
      selfSteps: List<MovementStep>,
      vararg paths: Pair<Int, List<MovementStep>>,
  ) = movement.moveSelfAndNpcs(session, state, selfSteps, paths.toList())

  /** Walk the player's own avatar through [steps] and wait for it to finish. */
  suspend fun moveSelf(vararg steps: MovementStep) =
      movement.moveSelf(session, state, steps.toList())

  /** Show a normally hidden map npc (its decomp local id) to this player, the decomp addobject. */
  fun showNpc(localId: Int) = movement.showNpc(session, state, localId)

  /** Shows a hidden NPC at a new position. */
  fun showNpcAt(localId: Int, x: Int, y: Int) = movement.showNpcAt(session, state, localId, x, y)

  /** Repositions an existing NPC. */
  fun repositionNpc(localId: Int, x: Int, y: Int) =
      movement.repositionNpc(session, state, localId, x, y)

  /** Relocate the player's overworld entity as part of a cutscene. */
  fun repositionSelf(x: Int, y: Int, facing: Direction) =
      movement.repositionSelf(session, state, x, y, facing)

  /** Remove a cutscene npc and its collision (`removeobject`). */
  fun removeNpc(localId: Int) = movement.removeNpc(session, state, localId)

  /** Set where a MAP_DYNAMIC warp sends this player (the decomp setdynamicwarp). */
  fun setDynamicWarp(regionId: Int, bankId: Int, mapId: Int, x: Int, y: Int, facing: Direction) =
      movement.setDynamicWarp(
          state,
          DynamicWarp(
              regionId.toByte(), bankId.toByte(), mapId.toByte(), x.toShort(), y.toShort(), facing))

  /**
   * Warps the player without door movement, then runs the destination map's entry scripts on this
   * same coroutine, the way the decomp's warp continues into the new map's scripts.
   */
  suspend fun warp(regionId: Int, bankId: Int, mapId: Int, x: Int, y: Int, facing: Direction) {
    val warpService = checkNotNull(warp) { "Script warp service is unavailable" }
    state.scriptOwnsMapEntry = true
    try {
      warpService.warp(
          session,
          state,
          DynamicWarp(
              regionId.toByte(),
              bankId.toByte(),
              mapId.toByte(),
              x.toShort(),
              y.toShort(),
              facing,
          ),
      )
      val destination = maps?.getMap(regionId, bankId, mapId) ?: return
      val scripts = entryScripts ?: return
      scripts.onEntry(state, destination).forEach { it.run(this) }
      state.characterId?.let { charId ->
        scripts.atCoordinate(charId, destination, state.x.toInt(), state.y.toInt())?.run(this)
      }
    } finally {
      state.scriptOwnsMapEntry = false
    }
  }

  private companion object {
    // Sign boxes have no speaker, npc boxes point at the entity.
    const val SIGN = 3
    const val NPC = 4
    const val FEMALE: Byte = 1
    const val MOVE_CUT = 15
    const val STORY_PLAYER_UNAVAILABLE = "Story player service is unavailable"
  }
}
