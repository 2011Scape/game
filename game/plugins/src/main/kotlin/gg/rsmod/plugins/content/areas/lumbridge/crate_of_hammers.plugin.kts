package gg.rsmod.plugins.content.areas.lumbridge

import kotlin.random.Random

on_obj_option(obj = Objs.CRATE_OF_HAMMERS, option = "take-hammer") {
    player.queue {
        if(player.inventory.contains(Items.HAMMER)) {
            messageBox(message = "You already have a hammer.")
        } else {
            itemMessageBox(message = "You take a hammer from the crate.", item = Items.HAMMER)
            player.inventory.add(Items.HAMMER)
            itemMessageBox(message = "You notice some coins scattered across the bottom of the crate and decide to swipe them.", item = Items.COINS)
            val randomChance = Random.nextInt(2, 30)
            player.inventory.add(Item(Items.COINS_995, randomChance))
            player.message("You take $randomChance coins from the pile.")
            player.addXp(Skills.THIEVING, 5.1)
        }
    }
}
