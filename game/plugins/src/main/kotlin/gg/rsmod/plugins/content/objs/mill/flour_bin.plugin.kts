package gg.rsmod.plugins.content.objs.mill

import gg.rsmod.game.model.attr.EXRTA_FINE_FLOUR


val FULL_BIN_VARP = 695

on_obj_option(obj = 36878, option = "Take-flour") {
    if (!player.inventory.contains(Items.EMPTY_POT)) {
        player.queue {
            this.chatPlayer("I need an empty pot to hold the flour.")
        }
        return@on_obj_option
    }

    player.queue {
        player.animate(1650)
        wait(world.getAnimationDelay(1650))

        player.inventory.remove(Items.EMPTY_POT)
        if (player.attr[EXRTA_FINE_FLOUR] == true) {
            player.inventory.add(Items.EXTRA_FINE_FLOUR)
            player.attr.remove(EXRTA_FINE_FLOUR)
        } else {
            player.inventory.add(Items.POT_OF_FLOUR)
        }

        player.setVarp(FULL_BIN_VARP, player.getVarp(FULL_BIN_VARP) - 1)
        if (player.getVarp(FULL_BIN_VARP) == 0) {
            player.message("You fill a pot with the last of the flour in the bin.")
        } else {
            player.message("You fill a pot with flour from the bin.")
        }
    }
}
