package gg.rsmod.plugins.content.items.packs

PackData.values().forEach {
    on_item_option(it.pack, "open") {
        if (player.inventory.remove(it.pack).hasSucceeded()) {
            player.inventory.add(it.content, it.amount)
        }
    }

    on_item_option(it.pack, "open-all") {
        val amount = player.inventory.getItemCount(it.pack)
        if (player.inventory.remove(it.pack, amount = amount).hasSucceeded()) {
            player.inventory.add(it.content, it.amount * amount)
        }
    }
}
