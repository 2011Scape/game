package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import kotlin.math.floor

create_shop(
    "Beefy Bill's Supplies",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.EMPTY_POT, 30)
    items[1] = ShopItem(Items.BUCKET_OF_WATER, 10)
    items[2] = ShopItem(Items.JUG_OF_WATER, 10)
    items[3] = ShopItem(Items.PIE_DISH, 30)
    items[4] = ShopItem(Items.CAKE_TIN, 10)
    items[5] = ShopItem(Items.TINDERBOX_590, 10)
    items[6] = ShopItem(Items.SHEARS, 10)
    items[7] = ShopItem(Items.REDBERRIES, 10)
    items[8] = ShopItem(Items.SPINACH_ROLL, 10)
    items[9] = ShopItem(Items.CHEFS_HAT, 10)
    items[10] = ShopItem(Items.TRAINING_SWORD, 10)
    items[11] = ShopItem(Items.TRAINING_SHIELD, 10)
    items[12] = ShopItem(Items.TRAINING_BOW, 10)
    items[13] = ShopItem(Items.TRAINING_ARROWS, 30)
}

on_npc_option(Npcs.BEEFY_BILL, "trade") {
    player.openShop("Beefy Bill's Supplies")
}

on_npc_option(npc = Npcs.BEEFY_BILL, option = "talk-to") {
    player.queue {
        mainChat(this)
    }
}

fun mainChat(mainchat: QueueTask) {
    mainchat.player.queue {
        chatNpc(
            "Beefy Bill at your service!",
            "I can bank your beef, your cowhides and your flour.",
            "I've also got other stuff for trade.",
        )
        chatNpc("What's it to be?")
        when (
            options(
                "Let's trade.",
                "I want you to bank things for me.",
                "Who are you?",
                "I'll have a think about it.",
            )
        ) {
            FIRST_OPTION -> {
                player.openShop("Beefy Bill's Supplies")
            }
            SECOND_OPTION -> {
                chatPlayer("I want you to bank things for me.")
                chatNpc(
                    "Excellent.",
                    "Just hand me the items, and I'll work out a price for you.",
                    "I charge a 10% commission.",
                )
            }
            THIRD_OPTION -> {
                chatPlayer("Who are you?")
                chatNpc(
                    "I'm Beefy Bill, specialist meat transporter",
                    "and general merchant.",
                )
                chatNpc(
                    "People bring me their beef, cowhides and flour.",
                    "I transport it all to the bank, keeping a mere 10% for my",
                    "services. I also have stuff for sale.",
                )
                chatPlayer("How do you pull your wagon?")
                chatNpc(
                    "Oh, I don't pull it myself!",
                    "I use cattle to pull it for me.",
                )
                chatPlayer("Isn't that disgusting?")
                chatNpc(
                    "Oh, stop being naive! I'm not letting your petty",
                    "personal ethics stand in the way of my right",
                    "to run a successful business.",
                )
                chatNpc("Now, do you want my services or not?")
                mainChat(this) // returns to "main menu"
            }
            FOURTH_OPTION -> {
                chatPlayer("I'll have a think about it.")
                chatNpc("Don't waste too much time thinking; time is money.")
            }
        }
    }
}

fun beefExchange(beefexchange: QueueTask) {
    beefexchange.player.queue {
        val inventory = player.inventory
        val playerbeefcount = inventory.getItemCount(Items.RAW_BEEF) // amount player inputs (will always be whole number)
        val bankbeefcount = floor(playerbeefcount * 0.9).toInt() // amount sent to bank rounded down to the nearest int
        val billbeefcount = floor((playerbeefcount - bankbeefcount).toDouble()).toInt() // amount sent to bill rounded down to the nearest int
        when (
            options(
                "Bank $bankbeefcount, Bill keeps $billbeefcount",
                "Forget it.",
                title = "Bill keeps 1 item out of every 10:",
            )
        ) {
            FIRST_OPTION -> { // Handles the removal and addition of items from inventory to bank
                player.inventory.remove(item = Item(Items.RAW_BEEF, amount = playerbeefcount), assureFullRemoval = true)
                player.bank.add(item = Items.RAW_BEEF, amount = bankbeefcount, assureFullInsertion = true)
                chatNpc("Pleasure doing business with ya, mate!")
            }
            SECOND_OPTION -> {
                // terminate
            }
        }
    }
}

fun hideExchange(hideexchange: QueueTask) {
    hideexchange.player.queue {
        val inventory = player.inventory
        val playerhidecount = inventory.getItemCount(Items.COWHIDE) // amount player inputs (will always be whole number)
        val bankhidecount = floor(playerhidecount * 0.9).toInt() // amount sent to bank rounded down to the nearest int
        val billhidecount = floor((playerhidecount - bankhidecount).toDouble()).toInt() // amount sent to bill rounded down to the nearest int
        when (
            options(
                "Bank $bankhidecount, Bill keeps $billhidecount",
                "Forget it.",
                title = "Bill keeps 1 item out of every 10:",
            )
        ) {
            FIRST_OPTION -> { // Handles the removal and addition of items from inventory to bank
                player.inventory.remove(item = Item(Items.COWHIDE, amount = playerhidecount), assureFullRemoval = true)
                player.bank.add(item = Items.COWHIDE, amount = bankhidecount, assureFullInsertion = true)
                chatNpc("Pleasure doing business with ya, mate!")
            }
            SECOND_OPTION -> {
                // terminate
            }
        }
    }
}

fun flourExchange(flourexchange: QueueTask) {
    flourexchange.player.queue {
        val inventory = player.inventory
        val playerflourcount = inventory.getItemCount(Items.POT_OF_FLOUR) // amount player inputs (will always be whole number)
        val bankflourcount = floor(playerflourcount * 0.9).toInt() // amount sent to bank rounded down to the nearest int
        val billflourcount = floor((playerflourcount - bankflourcount).toDouble()).toInt() // amount sent to bill rounded down to the nearest int
        when (
            options(
                "Bank $bankflourcount, Bill keeps $billflourcount",
                "Forget it.",
                title = "Bill keeps 1 item out of every 10:",
            )
        ) {
            FIRST_OPTION -> { // Handles the removal and addition of items from inventory to bank
                player.inventory.remove(
                    item = Item(Items.POT_OF_FLOUR, amount = playerflourcount),
                    assureFullRemoval = true,
                )
                player.bank.add(item = Items.POT_OF_FLOUR, amount = bankflourcount, assureFullInsertion = true)
                chatNpc("Pleasure doing business with ya, mate!")
            }
            SECOND_OPTION -> {
                // terminate
            }
        }
    }
}

on_item_on_npc(Items.COINS_995, Npcs.BEEFY_BILL) {
    player.queue {
        player.inventory.remove(item = Item(Items.COINS_995, amount = 1))
        chatNpc("Thanks!")
    }
}
on_item_on_npc(Items.COWHIDE, Npcs.BEEFY_BILL) {
    player.queue {
        hideExchange(this)
    }
}
on_item_on_npc(Items.RAW_BEEF, Npcs.BEEFY_BILL) {
    player.queue {
        beefExchange(this)
    }
}

on_item_on_npc(Items.POT_OF_FLOUR, Npcs.BEEFY_BILL) {
    player.queue {
        flourExchange(this)
    }
}
