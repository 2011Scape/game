package gg.rsmod.plugins.content.skills.crafting.pottery

val RUNES = listOf(Items.AIR_RUNE, Items.FIRE_RUNE, Items.WATER_RUNE, Items.EARTH_RUNE)

RUNES.forEach {
    PotteryData.getAllFiredUrns().forEach { urn ->
        on_item_on_item(item1 = it, item2 = urn) {
            val data = PotteryData.findPotteryItemByItem(urn) ?: return@on_item_on_item
            handleAddingRune(player, data)
        }
    }
}

fun handleAddingRune(
    player: Player,
    data: PotteryItem,
) {
    if (player.inventory.getItemCount(data.fired) <= 1) {
        player.queue { AddRuneAction.attachRune(this, data.fired + 2) }
    } else {
        val resultName =
            player.world.definitions
                .get(ItemDef::class.java, data.fired + 2)
                .name
                .replace(" (r)", "")
        val names = listOf(resultName).toTypedArray()
        player.queue {
            produceItemBox(data.fired + 2, names = names, logic = ::addRune)
        }
    }
}

fun addRune(
    player: Player,
    item: Int,
    amount: Int,
) {
    player.queue { AddRuneAction.attachRune(this, item, amount) }
}
