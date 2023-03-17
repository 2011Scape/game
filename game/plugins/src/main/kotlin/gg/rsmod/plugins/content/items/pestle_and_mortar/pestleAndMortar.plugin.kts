package gg.rsmod.plugins.content.items.pestle_and_mortar

PestleAndMortarData.values().forEach {
    on_item_on_item(Items.PESTLE_AND_MORTAR, it.source) {
        if (player.inventory.remove(it.source).hasSucceeded()) {
            player.inventory.add(it.result)
        }
    }
}
