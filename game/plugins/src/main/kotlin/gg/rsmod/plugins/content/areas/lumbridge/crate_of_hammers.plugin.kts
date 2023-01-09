package gg.rsmod.plugins.content.areas.lumbridge

on_obj_option(obj = Objs.CRATE_OF_HAMMERS, option = "take-hammer") {
    player.queue {
        if(player.hasItem(Items.HAMMER)) {
            messageBox(message = "You already have a hammer.")
        } else {
            itemMessageBox(message = "You take a hammer from the crate.", item = Items.HAMMER)
            player.inventory.add(Items.HAMMER)
        }
    }
}
