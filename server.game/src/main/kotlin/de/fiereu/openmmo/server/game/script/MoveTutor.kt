package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.dialog.DialogLine

/**
 * One of the townsfolk who will teach a move, once.
 *
 * There are fourteen of them across Kanto and the Sevii Islands and twelve were TODO stubs, so all
 * but the pair on Route 4 stood there saying nothing. They are one script with fourteen sets of
 * arguments, which is why this is a helper rather than fourteen copies of it -- the same reason the
 * gym leaders ended up sharing one.
 */
internal data class TutorMove(
    val move: Int,
    /** Set once something has actually learned it, so a change of mind does not spend the tutor. */
    val flag: String,
    /** Where the offer came from, for the log. */
    val from: String,
)

/** A tutor's four lines. All of them live on the shared `Misc` table rather than on a map. */
internal data class TutorLines(
    val teach: DialogLine,
    val declined: DialogLine,
    val whichMon: DialogLine,
    val taught: DialogLine,
)
