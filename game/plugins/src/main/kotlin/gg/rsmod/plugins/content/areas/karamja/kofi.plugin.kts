package gg.rsmod.plugins.content.areas.karamja

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author MrSlayerGod <https://github.com/MrSlayerGod>
 */

val shopkeeper = arrayOf(Npcs.SHOPKEEPER_KOFI, Npcs.SHOP_ASSISTANT_11678)

create_shop(
    name = "Karamja General Store",
    currency = CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.EMPTY_POT, 30)
    items[1] = ShopItem(Items.JUG, 10)
    items[2] = ShopItem(Items.SHEARS, 10)
    items[2] = ShopItem(Items.BUCKET, 30)
    items[2] = ShopItem(Items.BOWL, 10)
    items[2] = ShopItem(Items.CAKE_TIN, 10)
    items[2] = ShopItem(Items.TINDERBOX_590, 10)
    items[2] = ShopItem(Items.CHISEL, 10)
    items[2] = ShopItem(Items.HAMMER, 10)
}

shopkeeper.forEach {
    on_npc_option(it, "trade") {
        player.openShop("Karamja General Store")
    }

    on_npc_option(it, "talk-to") {
        player.queue {
            chatNpc(
                "Thanks for helping me out,",
                "friend. What can I do for you?",
            )
            when (
                options(
                    "What are you selling? ",
                    "How should I use your shop?",
                    "Nothing, thanks.",
                    title = "Select an Option",
                )
            ) {
                1 -> {
                    player.openShop("Karamja Wines, Spirits, and Beers")
                }
                2 -> {
                    chatNpc(
                        "I'm glad you ask! You can buy as many",
                        "of the items stocked as you wish.",
                        "You can also sell most items to the shop.",
                    )
                }
                3 -> {
                    chatNpc("Nothing, thanks.")
                }
            }
        }
    }
}
