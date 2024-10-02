package gg.rsmod.plugins.content.skills.firemaking


val firemakingDefinitions = FiremakingData.firemakingDefinitions
val logIds = FiremakingData.values.map { data -> data.raw }.toIntArray()

logIds.forEach {
    val item = it
    on_item_on_item(item1 = Items.TINDERBOX_590, item2 = item) {
        player.queue {
            firemakingAction(player, item, null)
        }
    }

    on_ground_item_option(it, 2) {
        player.queue {
            firemakingAction(player, item, player.getInteractingGroundItem())
        }
    }

    on_item_on_ground_item(item = Items.TINDERBOX_590, groundItem = item) {
        player.queue {
            firemakingAction(player, item, player.getInteractingGroundItem())
        }
    }
}

fun firemakingAction(
    player: Player,
    item: Int,
    ground: GroundItem? = null,
) {
    player.queue {
        val def = firemakingDefinitions[item] ?: return@queue
        FiremakingAction.burnLog(this, def, ground)
    }
}
