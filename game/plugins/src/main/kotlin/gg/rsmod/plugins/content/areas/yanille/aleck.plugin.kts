package gg.rsmod.plugins.content.areas.yanille

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Eikenb00m <https://github.com/Eikenb00m>
 */

create_shop(
    "Aleck's Hunter Emporium",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_STOCK,
) {
    items[0] = ShopItem(Items.BUTTERFLY_NET, 5)
    items[1] = ShopItem(Items.BUTTERFLY_JAR, 100)
    items[2] = ShopItem(Items.MAGIC_BOX, 30)
    items[3] = ShopItem(Items.NOOSE_WAND, 50)
    items[4] = ShopItem(Items.BIRD_SNARE, 50)
    items[5] = ShopItem(Items.BOX_TRAP, 25)
    items[6] = ShopItem(Items.TEASING_STICK, 5)
    items[7] = ShopItem(Items.UNLIT_TORCH, 20)
    items[8] = ShopItem(Items.RABBIT_SNARE, 10)
    items[9] = ShopItem(Items.GRENWALL_SPIKES, 1)
}

on_npc_option(npc = Npcs.ALECK, option = "Trade") {
    player.openShop("Aleck's Hunter Emporium")
}

on_npc_option(npc = Npcs.ALECK, "talk-to") {
    player.queue {
        chatPlayer("Hello.")
        chatNpc(
            "Hello, Hello,",
            " and a most warm welcome to my Hunter Emporium.",
            "We have everything the discerning Hunter could need.",
        )
        chatNpc(
            "Would you like me to show you our range of equipment?",
            " aOr was there something specific you were after?",
        )
        when (options("Ok, let's see what you've got!", "I'm not interested, thanks.", title = "Select an Option")) {
            1 -> {
                chatPlayer("Ok, let's see what you've got!")
                player.openShop("Aleck's Hunter Emporium")
            }
            2 -> {
                chatPlayer("I'm not interested, thanks.")
                chatNpc(
                    "Well, 'if you do ever find yourself in need of ",
                    "the finest Hunter equipment available, ",
                    "then you know where to come.",
                )
            }
        }
    }
}
