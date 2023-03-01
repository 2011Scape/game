package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop(
    "Beefy Bill's Supplies",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false
)
{
    items[0] = ShopItem(Items.BRONZE_ARROW, 30)
    items[1] = ShopItem(Items.BRONZE_SQ_SHIELD, 10)
    items[2] = ShopItem(Items.BRONZE_SWORD, 10)
    items[3] = ShopItem(Items.BUCKET_OF_WATER, 10)
    items[4] = ShopItem(Items.CAKE_TIN, 10)
    items[5] = ShopItem(Items.CHEFS_HAT, 10)
    items[6] = ShopItem(Items.EMPTY_POT, 30)
    items[7] = ShopItem(Items.JUG_OF_WATER, 10)
    items[8] = ShopItem(Items.PIE_DISH, 10)
    items[9] = ShopItem(Items.REDBERRIES, 10)
    items[10] = ShopItem(Items.SHEARS, 10)
    items[11] = ShopItem(Items.SHORTBOW, 10)
    items[12] = ShopItem(Items.SPINACH_ROLL, 10)
    items[13] = ShopItem(Items.TINDERBOX, 10)
}

on_npc_option(Npcs.BEEFY_BILL, "trade") {
	player.openShop("Beefy Bill's Supplies")
}

on_npc_option(Npcs.BEEFY_BILL, option = "talk-to") {
    player.queue {
        chatNpc("Beefy Bill at your service! I can bank your beef, your cowhides and your flour. I've also got other stuff for trade. What's it to be?.", facialExpression = FacialExpression.HAPPY_TALKING)
        when (options("Let's trade.", "I want you to bank things for me.", "Who are you?")) {
            1 -> {
				player.openShop("Beefy Bill's Supplies")
            }
            2 -> {
                chatPlayer("I want you to bank these things for me.", facialExpression = FacialExpression.HAPPY_TALKING)
                chatNpc("Excellent. Just hand me the items, and I'll work out a price for you. I charge a 10% commission.", facialExpression = FacialExpression.TALKING_ALOT)
                //TODO CHECK FOR ITEMS IN INVENTORY and bank them
                chatNpc("Pleasure doing business with ya, mate!")
            }
            3 -> {
                chatPlayer("Who are you?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("I'm Beefy Bill, specialist meat transporter and general merchant.", facialExpression = FacialExpression.TALKING_ALOT)
                chatNpc("People bring me their beef, cowhides and flour, and I transport it all to the bank, keeping a mere 10% for my services. I also have stuff for sale.")
            }
        }
    }
}
