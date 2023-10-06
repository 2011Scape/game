package gg.rsmod.plugins.content.holiday

import gg.rsmod.game.model.attr.SCYTHE

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */

//Scythe Spawns
spawn_item(item = Items.SCYTHE, amount = 1, x = 2963, z = 3379, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 2967, z = 3384, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 2954, z = 3381, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 2999, z = 3364, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3093, z = 3260, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3101, z = 3250, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3105, z = 3265, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3120, z = 3419, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3088, z = 3420, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3078, z = 3418, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3078, z = 3433, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3093, z = 3477, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3086, z = 3488, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3079, z = 3502, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3092, z = 3503, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3197, z = 3243, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3186, z = 3430, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3168, z = 3428, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3148, z = 3416, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3219, z = 3224, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3224, z = 3223, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3220, z = 3212, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3233, z = 3216, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3236, z = 3226, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3228, z = 3232, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3223, z = 3242, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3213, z = 3246, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3259, z = 3230, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3218, z = 3429, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3209, z = 3424, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3276, z = 3163, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3317, z = 3234, respawnCycles = 6000)
spawn_item(item = Items.SCYTHE, amount = 1, x = 3296, z = 3296, respawnCycles = 6000)

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