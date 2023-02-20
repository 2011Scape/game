package gg.rsmod.plugins.content.skills.firemaking

val firemakingDefinitions = FiremakingData.firemakingDefinitions
val logIds = FiremakingData.values.map { data -> data.raw }.toIntArray()

logIds.forEach {
    val item = it
    on_item_on_item(item1 = Items.TINDERBOX_590, item2 = item) {
        firemakingAction(player, item)
    }

    on_item_on_ground_item(item = Items.TINDERBOX_590, groundItem = item) {
        firemakingAction(player, item)
    }
}

fun firemakingAction(player: Player, item: Int) {
    player.interruptQueues()
    player.resetInteractions()
    player.queue {
        val def = firemakingDefinitions[item] ?: return@queue
        FiremakingAction.burnLog(this, def)
    }
}
