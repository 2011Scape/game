package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import kotlin.math.floor

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
    items[13] = ShopItem(Items.TINDERBOX_590, 10)
}

on_npc_option(Npcs.BEEFY_BILL, "trade") {
	player.openShop("Beefy Bill's Supplies")
}

on_npc_option(Npcs.BEEFY_BILL, option = "talk-to") {
    val inventory = player.inventory

    val playerbeefcount = inventory.getItemCount(Items.RAW_BEEF)
    val playerhidecount = inventory.getItemCount(Items.COWHIDE)
    val playerflourcount = inventory.getItemCount(Items.POT_OF_FLOUR)

    val bankbeefcount = playerbeefcount * 0.9
    val bankhidecount = playerhidecount * 0.9
    val bankflourcount = playerflourcount * 0.9

    val roundedbeefcount = floor(bankbeefcount).toInt()
    val roundedhidecount = floor(bankhidecount).toInt()
    val roundedflourcount = floor(bankflourcount).toInt()

    var tradedbill = false

    player.queue {
        chatNpc("Beefy Bill at your service!", "I can bank your beef, your cowhides and your flour.", "I've also got other stuff for trade.", facialExpression = FacialExpression.HAPPY_TALKING)
        chatNpc("What's it to be?", facialExpression = FacialExpression.HAPPY_TALKING)
        when (options("Let's trade.", "I want you to bank things for me.", "Who are you?")) {
            1 -> {
				player.openShop("Beefy Bill's Supplies")
            }
            2 -> {
                chatPlayer("I want you to bank these things for me.", facialExpression = FacialExpression.HAPPY_TALKING)
                chatNpc("Excellent.", "Just hand me the items, and I'll work out a price for you.", "I charge a 10% commission.", facialExpression = FacialExpression.TALKING_ALOT)
                if (player.inventory.contains(Items.RAW_BEEF) && playerbeefcount >= 10) {
                    player.inventory.remove(item = Item(Items.RAW_BEEF, amount = playerbeefcount))
                    messageBox("Bill takes 10% of your raw beef and sends $roundedbeefcount to the bank.")
                    player.bank.add(item = Items.RAW_BEEF, amount = roundedbeefcount, assureFullInsertion = true)
                    tradedbill = true
                } else
                    messageBox("You must have at least 10 raw beef.")

                if (player.inventory.contains(Items.COWHIDE) && playerhidecount >= 10) {
                    player.inventory.remove(item = Item(Items.COWHIDE, amount = playerhidecount))
                    messageBox("Bill takes 10% of your cowhide and sends $roundedhidecount to the bank.")
                    player.bank.add(item = 1739, amount = roundedhidecount, assureFullInsertion = true)
                    tradedbill = true
                } else
                    messageBox("You must have at least 10 cowhide.")

                if (player.inventory.contains(Items.POT_OF_FLOUR) && playerflourcount >= 10) {
                    player.inventory.remove(item = Item(Items.POT_OF_FLOUR, amount = playerflourcount))
                    messageBox("Bill takes 10% of your pots of flour and sends $roundedflourcount to the bank.")
                    player.bank.add(item = Items.POT_OF_FLOUR, amount = roundedflourcount, assureFullInsertion = true)
                    tradedbill = true
                } else
                    messageBox("You must have at least 10 pots of flour.")

                if (tradedbill){
                    chatNpc("Pleasure doing business with ya, mate!", facialExpression = FacialExpression.HAPPY)
                    tradedbill = false
                } else
                    chatNpc("Forget it then.", facialExpression = FacialExpression.ANGRY)
            }
            3 -> {
                chatPlayer("Who are you?", facialExpression = FacialExpression.CONFUSED)
                chatNpc("I'm Beefy Bill, specialist meat transporter", "and general merchant.", facialExpression = FacialExpression.TALKING_ALOT)
                chatNpc("People bring me their beef, cowhides and flour.", "I transport it all to the bank, keeping a mere 10% for my", "services. I also have stuff for sale.", facialExpression = FacialExpression.TALKING_ALOT)
                chatPlayer("I'll have a think about it.", facialExpression = FacialExpression.THINK)
            }
        }
    }
}
