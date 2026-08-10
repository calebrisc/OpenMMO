package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.items.ItemRegistry
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject

/** Hands an item to a connected player through the store, so the cache stays authoritative. */
class GiveCommand
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val items: ItemRegistry,
) : ChatCommand {
  override val name = "give"
  override val usage = "/give <character> <item name> [quantity]"
  override val description = "gives a connected player an item"
  override val permission = CharacterPermissions.DEVELOPER

  override suspend fun run(ctx: CommandContext) {
    if (ctx.args.size < 2) return ctx.reply(usage)
    val target = characterStore.findCachedByName(ctx.args.first())
    if (target == null) {
      return ctx.reply("No connected character named ${ctx.args.first()}.")
    }
    val quantity = ctx.args.last().toIntOrNull()
    val itemWords = if (quantity != null) ctx.args.drop(1).dropLast(1) else ctx.args.drop(1)
    val itemName = itemWords.joinToString(" ")
    val item = items.all().firstOrNull { it.name.equals(itemName, ignoreCase = true) }
    if (item == null) return ctx.reply("No item named $itemName.")
    val amount = quantity ?: 1
    if (!characterStore.addItem(target.info.id, items.idOf(item), amount)) {
      return ctx.reply("Could not add the item.")
    }
    ctx.reply("Gave ${target.info.name} ${item.name} x$amount. It shows on their next bag sync.")
  }
}
