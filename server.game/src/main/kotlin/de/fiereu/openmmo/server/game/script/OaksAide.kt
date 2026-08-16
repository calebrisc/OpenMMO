package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.dialog.DialogLine
import de.fiereu.openmmo.items.ItemDef

/**
 * What one of Professor Oak's aides is holding, and how much of the dex they want to see first.
 *
 * There are five of them across Kanto, standing at 10, 20, 30, 40 and 50, and every one was a TODO
 * stub: they had nothing to say and nothing to give.
 */
internal data class AideGift(
    val item: ItemDef,
    val required: Int,
    /** The first of them counts what you have seen; the rest count what you have caught. */
    val countCaught: Boolean,
    val flag: String,
)

/**
 * An aide's own lines. The three shared ones -- turning them down, falling short, and having no
 * room -- are the same everywhere and live on [de.fiereu.openmmo.dialog.generated.kanto.Aide].
 *
 * The texts are written around buffers this server cannot fill: {STR_VAR_1} is the number wanted,
 * {STR_VAR_2} the item's name and {STR_VAR_3} how many the player has. Only a species name can be
 * put in one of those, so the numbers read as placeholders until buffered text is understood.
 */
internal data class AideLines(
    val offer: DialogLine,
    val greatHereYouGo: DialogLine,
    val received: DialogLine,
    val explain: DialogLine,
)
