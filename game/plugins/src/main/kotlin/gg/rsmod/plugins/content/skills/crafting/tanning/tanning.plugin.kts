package gg.rsmod.plugins.content.skills.crafting.tanning

var TANNING_INTERFACE = 324
val buttons = arrayOf(1, 2, 3, 4, 5, 6, 7, 8)

on_button(interfaceId = TANNING_INTERFACE, component = buttons) {
    val product = TanningData.tanningDefinitions[player.getInteractingButton()]!!
    when (player.getInteractingOpcode()) {
        61 -> startTanning(player, tanningData = product, amount = 1)
        64 -> startTanning(player, tanningData = product, amount = 5)
        4 -> startTanning(player, tanningData = product, amount = 10)
        52 -> {
            player.queue(TaskPriority.WEAK) {
                val count = inputInt("How many would you like to tan?")
                startTanning(player, tanningData = product, amount = count)
            }
        }
        81 -> startTanning(player, tanningData = product, amount = player.inventory.getItemCount(product.rawItemId))
    }
}

fun startTanning(
    player: Player,
    tanningData: TanningData,
    amount: Int,
) {
    val invCount = player.inventory.getItemCount(tanningData.rawItemId)
    val count = if (amount >= invCount) invCount else amount
    val name =
        world.definitions
            .get(ItemDef::class.java, tanningData.rawItemId)
            .name
            .lowercase()
    val totalPrice = count * tanningData.price
    if (!player.inventory.containsAny(tanningData.rawItemId)) {
        player.message("You don't have any ${name}s to tan.")
        return
    }
    if (player.inventory.remove(Items.COINS_995, amount = totalPrice, assureFullRemoval = true).hasSucceeded()) {
        player.inventory.remove(item = Item(tanningData.rawItemId, amount = count), assureFullRemoval = true)
        player.inventory.add(item = Item(tanningData.tannedId, amount = count))
        player.message("The tanner tans your ${name}${if (count > 1) "s" else ""}.")
    } else {
        player.message("You don't have enough coins to tan your hides.")
        return
    }
}
