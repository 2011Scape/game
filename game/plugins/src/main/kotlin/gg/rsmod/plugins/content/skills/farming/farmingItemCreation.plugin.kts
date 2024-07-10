package gg.rsmod.plugins.content.skills.farming

import gg.rsmod.plugins.content.skills.farming.data.itemCreation.BasketItemCreation
import gg.rsmod.plugins.content.skills.farming.data.itemCreation.SackItemCreation

BasketItemCreation.values().forEach { basketCreation ->
    on_item_on_item(basketCreation.basket, basketCreation.produce) {
        if (player.inventory.getItemCount(basketCreation.produce) < basketCreation.amount) {
            player.message(basketCreation.message)
        } else {
            player.queue {
                wait(1)
                if (player.inventory.remove(basketCreation.basket).hasSucceeded() &&
                    player.inventory.remove(basketCreation.produce, amount = basketCreation.amount).hasSucceeded()
                ) {
                    player.inventory.add(basketCreation.product)
                }
            }
        }
    }
}

on_item_option(Items.BASKET, "fill") {
    val item = BasketItemCreation.values().firstOrNull { player.inventory.getItemCount(it.produce) >= it.amount }
    if (item == null) {
        player.message("You cannot fill this basket with any fruit.")
    } else {
        player.queue {
            wait(1)
            if (player.inventory.remove(item.basket).hasSucceeded() &&
                player.inventory.remove(item.produce, amount = item.amount).hasSucceeded()
            ) {
                player.inventory.add(item.product)
            }
        }
    }
}

SackItemCreation.values().forEach { sackCreation ->
    on_item_on_item(sackCreation.sack, sackCreation.produce) {
        if (player.inventory.getItemCount(sackCreation.produce) < sackCreation.amount) {
            player.message(sackCreation.message)
        } else {
            player.queue {
                wait(1)
                if (player.inventory.remove(sackCreation.sack).hasSucceeded() &&
                    player.inventory.remove(sackCreation.produce, amount = sackCreation.amount).hasSucceeded()
                ) {
                    player.inventory.add(sackCreation.product)
                }
            }
        }
    }
}

on_item_option(Items.EMPTY_SACK, "fill") {
    val item = SackItemCreation.values().firstOrNull { player.inventory.getItemCount(it.produce) >= it.amount }
    if (item == null) {
        player.message("You cannot fill this sack with any crops.")
    } else {
        player.queue {
            wait(1)
            if (player.inventory.remove(item.sack).hasSucceeded() &&
                player.inventory.remove(item.produce, amount = item.amount).hasSucceeded()
            ) {
                player.inventory.add(item.product)
            }
        }
    }
}

on_item_on_item(Items.HAY_SACK, intArrayOf(Items.BRONZE_SPEAR, Items.WATERMELON)) {
    val items = listOf(Items.HAY_SACK, Items.BRONZE_SPEAR, Items.WATERMELON)
    if (items.any { player.inventory.getItemCount(it) == 0 }) {
        player.message("You need a hay sack, a bronze spear and a watermelon to do that.")
    } else if (player.skills.getCurrentLevel(Skills.FARMING) < 23) {
        player.message("You need to be a level 23 Farmer to do that.")
    } else {
        player.queue {
            wait(1)
            if (items.all { player.inventory.remove(it).hasSucceeded() }) {
                player.inventory.add(Items.SCARECROW)
                player.addXp(Skills.FARMING, 25.0)
            }
        }
    }
}

on_item_on_obj(
    arrayOf(
        Objs.HAYSTACK,
        Objs.HAYSTACK_8714,
        Objs.HAYSTACK_8716,
        Objs.HAYSTACK_43590,
        Objs.HAYSTACK_52841,
        Objs.HAY_BALE,
        Objs.HAY_BALE_8713,
        Objs.HAY_BALE_8715,
        Objs.HAY_BALE_36892,
        Objs.HAY_BALE_36893,
        Objs.HAY_BALES_299,
        Objs.HAY_BALES_10084,
        Objs.HAY_BALES_10085,
        Objs.HAY_BALES_10362,
        Objs.HAY_BALES_34593,
        Objs.HAY_BALES_34594,
        Objs.HAY_BALES_36894,
        Objs.HAY_BALES_36895,
        Objs.HAY_BALES_36896,
        Objs.HAY_BALES_36897,
        Objs.HAY_BALES_36898,
    ),
    Items.EMPTY_SACK,
) {
    if (player.inventory.remove(Items.EMPTY_SACK).hasSucceeded()) {
        player.message("You fill the sack with straw.")
        player.inventory.add(Items.HAY_SACK)
    }
}
