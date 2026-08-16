package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.dialog.DialogLine
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * The two people every gym has, and neither of whom said anything.
 *
 * A guide stands inside the door of all eight and a plaque hangs by the leader, and both scripts
 * were stubs in every one of them, so the first thing a player meets on walking into a gym was
 * somebody who ignores them.
 *
 * Both read the same way: one line before the badge and another after. A few gyms have no second
 * line to give -- the guide in Pewter leaves once Brock is beaten, so the decomp never wrote him
 * one -- and those keep saying the first.
 */
internal suspend fun gymGuide(
    ctx: ScriptContext,
    defeatedFlag: String,
    advice: DialogLine,
    afterwards: DialogLine? = null,
) {
  val beaten = ctx.isFlagSet(defeatedFlag)
  ctx.say(if (beaten && afterwards != null) afterwards else advice)
}

/** The plaque by the leader, which reads like a sign and adds the winner's name once earned. */
internal suspend fun gymPlaque(
    ctx: ScriptContext,
    badgeFlag: String,
    plaque: DialogLine,
    won: DialogLine,
) {
  ctx.sign(if (ctx.isFlagSet(badgeFlag)) won else plaque)
}
