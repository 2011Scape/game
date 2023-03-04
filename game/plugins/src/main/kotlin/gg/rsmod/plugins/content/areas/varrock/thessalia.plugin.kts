package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Thessalia's Fine Clothes", CoinCurrency(), containsSamples = false) {
    items[0] = ShopItem(Items.WHITE_APRON, 10)
    items[1] = ShopItem(Items.LEATHER_BODY, 10)
    items[2] = ShopItem(Items.LEATHER_GLOVES, 10)
    items[3] = ShopItem(Items.LEATHER_BOOTS, 10)
    items[4] = ShopItem(Items.BROWN_APRON, 10)
    items[5] = ShopItem(Items.PINK_SKIRT, 10)
    items[6] = ShopItem(Items.BLACK_SKIRT, 10)
    items[7] = ShopItem(Items.BLUE_SKIRT, 10)
    items[8] = ShopItem(Items.CAPE, 10)
    items[9] = ShopItem(Items.SILK, 10)
    items[10] = ShopItem(Items.PRIEST_GOWN_428, 10)
    items[11] = ShopItem(Items.PRIEST_GOWN, 10)
    items[12] = ShopItem(Items.NEEDLE, 10)
    items[13] = ShopItem(Items.THREAD, 1000)
}

on_npc_option(npc = Npcs.THESSALIA, option = "Talk-to") {
    player.queue {
        chatNpc("Would you like to buy any fine clothes?")
        chatNpc("Or if you're more after fancy dress costumes or", "commemorative capes, talk to granny Iffie.")
        when(options("What do you have?", "No, thank you.")) {
            1 -> {
                chatPlayer("What do you have?")
                chatNpc("Well, I have a number of fine pieces of clothing on sale or,", "if you prefer, I can offer you an exclusive, total clothing", "makeover?")
                when(options("Tell me more about this makeover.", "I'd just like to buy some clothes.")) {
                    1 -> {
                        chatPlayer("Tell me more about this makeover.")
                        chatNpc("Certainly!")
                        chatNpc("Here at Thessalia's Fine Clothing Boutique we offer a", "unique service, where we will totally revamp your outfit to", " your choosing. Tired of always wearing the same old", "outfit, day-in, day-out? Then this is the service for you!")
                        chatNpc("So, what do you say? Interested?")
                        when(options("I'd like to change my outfit, please.", "I'd just like to buy some clothes.", "No, thank you.")) {
                            1 -> {
                                chatPlayer("I'd like to change my outfit, please.")
                                chatNpc("Wonderful. Feel free to try on some items and see if", "there's anything you would like.")
                                chatPlayer("Okay, thanks.")
                                openMakeover(player)
                            }
                            2 -> {
                                chatPlayer("I'd just like to buy some clothes.")
                                player.openShop("Thessalia's Fine Clothes")
                            }
                        }
                    }
                    2 -> {
                        chatPlayer("I'd just like to buy some clothes.")
                        player.openShop("Thessalia's Fine Clothes")
                    }
                }
            }
            2 -> chatPlayer("No, thank you.")
        }
    }
}

on_npc_option(npc = Npcs.THESSALIA, option = "Trade") {
    player.openShop("Thessalia's Fine Clothes")
}

on_npc_option(npc = Npcs.THESSALIA, option = "Change-clothes") {
   openMakeover(player)
}

fun openMakeover(player: Player) {
    if(!player.equipment.isEmpty) {
        player.queue {
            chatNpc("You're not able to try on my clothes with all that armour.", "Take it off and speak to me again.")
        }
        return
    }
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 729)
}