package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.DialogChoicePacket
import de.fiereu.openmmo.net.game.packets.DialogStatePacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionPacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogActionResponsePacket
import de.fiereu.openmmo.net.game.packets.dialog.DialogMessageArg
import de.fiereu.openmmo.server.game.session.PENDING_DIALOG
import de.fiereu.openmmo.server.game.session.PENDING_DIALOG_RESPONSE
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CompletableDeferred

private val log = KotlinLogging.logger {}

internal val CLOSE_DIALOG_ACTION =
    DialogActionPacket(
        flags = 0,
        actionType = 0x64,
        textId = 0,
        entityId = -1,
        contextValue = 0,
        messageArgs = emptyList(),
        detail = ByteArray(0),
    )

/**
 * The optional tail of a dialog box.
 *
 * [detail] carries a zero byte that a live server does not: every one of the 369 plain boxes in the
 * archive is exactly 19 bytes and ours are 20. Dropping it to match was tried, blamed for breaking
 * dialogs, and put back -- and the blame was wrong. The client kept refusing boxes for a full
 * minute after the shorter one was reverted, so this byte was never the problem. What was refused
 * was a box carrying two message arguments; one is fine.
 *
 * It is left at a zero byte because that is what this server has always sent and what every working
 * dialog on it carries, not because it is known to be needed. Whether the client wants it is
 * untested. If it is ever taken out again, take it out on its own and watch the client log, rather
 * than alongside anything else.
 */
data class DialogPresentation(
    val messageArgs: List<DialogMessageArg> = emptyList(),
    val contextValue: Int = 0,
    val detail: ByteArray = byteArrayOf(0),
)

@Singleton
class DialogService @Inject constructor() {

  /** Emerald starter picker ROM ids. */
  suspend fun chooseHoennStarter(session: SessionContext, state: PlayerState): Int {
    while (true) {
      val choice =
          showChoiceAndWait(
                  session = session,
                  state = state,
                  textId = HOENN_STARTER_PICK_TEXT,
                  actionType = STARTER_PICK,
                  entityId = NO_ENTITY,
                  presentation =
                      DialogPresentation(
                          contextValue = STARTER_CONTEXT,
                          detail =
                              byteArrayOf(
                                  3,
                                  (TREECKO and 0xFF).toByte(),
                                  (TREECKO shr 8).toByte(),
                                  (TORCHIC and 0xFF).toByte(),
                                  (TORCHIC shr 8).toByte(),
                                  (MUDKIP and 0xFF).toByte(),
                                  (MUDKIP shr 8).toByte(),
                              )),
              )
              .unk
      if (choice !in 1..3) continue

      val accepted =
          showChoiceAndWait(
                  session = session,
                  state = state,
                  textId = HOENN_STARTER_CONFIRM_TEXT,
                  actionType = YES_NO,
                  entityId = NO_ENTITY,
                  presentation = DialogPresentation(contextValue = STARTER_CONTEXT),
              )
              .unk != 0
      if (accepted) return listOf(TREECKO, TORCHIC, MUDKIP)[choice - 1]
    }
  }

  /**
   * Show a ROM-backed yes/no box and return true for YES.
   *
   * [messageArgs] fills the string variables the question is written around. The trade questions
   * name two species — "Do you have a {STR_VAR_1}? Want to trade it for my {STR_VAR_2}?" — and
   * without them the box asks about nothing at all.
   */
  suspend fun askYesNo(
      session: SessionContext,
      state: PlayerState,
      textId: Int,
      entityId: Long,
      messageArgs: List<DialogMessageArg> = emptyList(),
  ): Boolean =
      showChoiceAndWait(
              session,
              state,
              textId,
              YES_NO,
              entityId,
              DialogPresentation(messageArgs = messageArgs),
          )
          .unk != 0

  /**
   * Shows a dialog box and waits for the player to advance or close it. [actionType] is 3 for a
   * sign and 4 for an npc box, [entityId] is the speaking npc or -1. The flags byte counts up per
   * box.
   */
  suspend fun showAndWait(
      session: SessionContext,
      state: PlayerState,
      textId: Int,
      actionType: Int,
      entityId: Long,
      presentation: DialogPresentation = DialogPresentation(),
  ) {
    val advance = CompletableDeferred<Unit>()
    session.attributes[PENDING_DIALOG] = advance
    val seq = state.dialogSeqId
    state.dialogSeqId = seq + 1
    state.inDialog = true
    state.dialogNpcEntityId = entityId
    log.debug {
      "Send dialog box seq=$seq actionType=$actionType textId=0x${textId.toString(16)} entity=$entityId"
    }
    session.send(
        DialogActionPacket(
            flags = seq.toByte(),
            actionType = actionType.toByte(),
            textId = textId,
            entityId = entityId,
            contextValue = presentation.contextValue,
            messageArgs = presentation.messageArgs,
            detail = presentation.detail,
        ))
    advance.await()
  }

  /** Show a scene page and wait for the client's 0x21 acknowledgement. */
  suspend fun showScenePageAndWait(
      session: SessionContext,
      state: PlayerState,
      textId: Int,
      actionType: Int,
      contextValue: Int,
      messageArgs: List<DialogMessageArg>,
  ) =
      showAndWait(
          session,
          state,
          textId,
          actionType,
          NO_ENTITY,
          DialogPresentation(messageArgs, contextValue, ByteArray(0)),
      )

  /** Show a scene menu and return its 1-based choice. */
  suspend fun showSceneMenuAndWait(
      session: SessionContext,
      state: PlayerState,
      detail: ByteArray,
  ): Int =
      showChoiceAndWait(
              session,
              state,
              textId = 0,
              actionType = STARTER_PICK,
              entityId = NO_ENTITY,
              presentation = DialogPresentation(detail = detail),
          )
          .unk

  /** Closes the dialog once a script has shown its last box. */
  fun close(session: SessionContext, state: PlayerState) {
    session.attributes.remove(PENDING_DIALOG)
    session.attributes.remove(PENDING_DIALOG_RESPONSE)
    if (state.inDialog) {
      session.send(DialogStatePacket(false))
      state.inDialog = false
      state.dialogNpcEntityId = 0
    }
  }

  fun onInteractive(event: PacketEvent<DialogActionResponsePacket>) {
    val session = event.session
    val response = session.attributes.remove(PENDING_DIALOG_RESPONSE)
    if (response != null) {
      response.complete(event.packet)
      return
    }
    val advance = session.attributes.remove(PENDING_DIALOG)
    log.debug { "Dialog response id=${event.packet.id} advancing=${advance != null}" }
    if (advance != null) {
      // A script is waiting on this box, let it move on to its next line.
      advance.complete(Unit)
      return
    }
    // No script is driving this dialog, just close whatever is open.
    val state = session.attributes[PLAYER_STATE] ?: return
    if (state.inDialog) {
      session.send(DialogStatePacket(false))
      state.inDialog = false
      state.dialogNpcEntityId = 0
    }
  }

  fun onDialogChoice(event: PacketEvent<DialogChoicePacket>) {
    val session = event.session
    log.info { "Dialog choice received: unk1=${event.packet.unk1}, unk2=${event.packet.unk2}" }
    val advance = session.attributes.remove(PENDING_DIALOG)
    if (advance != null) {
      advance.complete(Unit)
      return
    }
    val state = session.attributes[PLAYER_STATE] ?: return
    if (state.inDialog) {
      session.send(DialogStatePacket(false))
      state.inDialog = false
      state.dialogNpcEntityId = 0
    }
  }

  private suspend fun showChoiceAndWait(
      session: SessionContext,
      state: PlayerState,
      textId: Int,
      actionType: Int,
      entityId: Long,
      presentation: DialogPresentation = DialogPresentation(),
  ): DialogActionResponsePacket {
    val response = CompletableDeferred<DialogActionResponsePacket>()
    session.attributes[PENDING_DIALOG_RESPONSE] = response
    val seq = state.dialogSeqId
    state.dialogSeqId = seq + 1
    state.inDialog = true
    state.dialogNpcEntityId = entityId
    session.send(
        DialogActionPacket(
            flags = seq.toByte(),
            actionType = actionType.toByte(),
            textId = textId,
            entityId = entityId,
            contextValue = presentation.contextValue,
            messageArgs = presentation.messageArgs,
            detail = presentation.detail,
        ))
    return response.await()
  }

  private companion object {
    const val NO_ENTITY = -1L
    const val YES_NO = 0x05
    const val STARTER_PICK = 0x23
    const val STARTER_CONTEXT = 700

    const val TREECKO = 252
    const val TORCHIC = 255
    const val MUDKIP = 258

    // Verified against the captured Emerald dialog database.
    const val HOENN_STARTER_PICK_TEXT = 0x105E8C53
    const val HOENN_STARTER_CONFIRM_TEXT = 0x105E8C90
  }
}
