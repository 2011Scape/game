package gg.rsmod.plugins.content.holiday

import gg.rsmod.game.model.attr.SCYTHE

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

set_ground_item_condition(item = Items.SCYTHE) {
    val hasItem = player.hasItem(Items.SCYTHE)

    if (hasItem) {
        player.message("You already have a scythe, you don't need another one.")
        return@set_ground_item_condition false
    } else {
        player.attr.put(SCYTHE, true)
        !hasItem
    }
}
