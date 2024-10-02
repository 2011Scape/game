package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.CooksAssistant

/**
 * @author Alycia <https://github.com/alycii>
 */

on_obj_option(obj = Objs.PRIZED_DAIRY_COW, option = "Milk") {
    if (!player.inventory.contains(Items.BUCKET)) {
        player.message("You'll need an empty bucket to collect the milk.")
        return@on_obj_option
    }
    if (player.getCurrentStage(quest = CooksAssistant) == 1) {
        player.queue {
            if (player.hasItem(Items.TOPQUALITY_MILK)) {
                messageBox("You already have some top quality milk.")
                return@queue
            }
            player.animate(2305)
            wait(world.getAnimationDelay(2305))
            player.inventory.remove(Items.BUCKET)
            player.inventory.add(Items.TOPQUALITY_MILK)
            player.message("You milk the cow for top-quality milk.")
        }
    } else {
        player.queue {
            messageBox("If you're after ordinary milk, you should use an ordinary dairy cow.")
        }
    }
}
