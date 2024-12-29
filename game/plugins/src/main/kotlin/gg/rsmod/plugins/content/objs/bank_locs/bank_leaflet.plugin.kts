package gg.rsmod.plugins.content.objs.bank_locs

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.inter.bank.Bank

private val leaflets = listOf(Objs.LEAFLETS, Objs.LEAFLETS_40179, Objs.LEAFLETS_45504, Objs.LEAFLETS_45562)

leaflets.forEach {
    on_obj_option(obj = it, option = "take") {
        if (player.inventory.contains(Items.LEAFLET)) {
            player.message("You already have a copy of the leaflet.")
        } else {
            player.message("You take a copy of the leaflet.")
            player.inventory.add(Items.LEAFLET)
        }
    }
}

on_item_option(item = Items.LEAFLET, option = "read") {
    player.message("You read the leaflet.")
    player.openInterface(Bank.BANK_HELP_INTERFACE_ID, InterfaceDestination.MAIN_SCREEN)
}
