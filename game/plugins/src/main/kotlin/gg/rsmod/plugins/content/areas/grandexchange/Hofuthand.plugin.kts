package gg.rsmod.plugins.content.areas.grandexchange

import gg.rsmod.game.model.shop.Shop
import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

val FORCE_CHAT_TIMER = TimerKey()
val DELAY = 35..80

on_npc_spawn(npc = Npcs.HOFUTHAND_ARMOUR_AND_WEAPONS) {
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

on_timer(FORCE_CHAT_TIMER) {
    when(world.random(5)) {
        0 -> npc.forceChat("Wow, that's cheap.")
        1 -> npc.forceChat("Oh. That didn't sell so well.")
        2 -> npc.forceChat("Hmmm. If I spend twenty thousand on that, then...")
        3 -> npc.forceChat("Jackpot! I'm in the money now!")
        4 -> npc.forceChat("Hahaha! Trading the likes of which I have never seen.")
    }
    npc.timers[FORCE_CHAT_TIMER] = world.random(DELAY)
}

create_shop("Hofuthand's Armour", CoinCurrency(), stockType = StockType.INFINITE, purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    val itemsList = listOf(
        ShopItem(Items.ADAMANT_ARMOUR_SET_LG, amount = 20000, sellPrice = 20000),
        ShopItem(Items.RUNE_ARMOUR_SET_LG, amount = 200000, sellPrice = 200000),
        ShopItem(Items.DRAGON_CHAIN_ARMOUR_SET_SK, amount = 1200000, sellPrice = 1200000),
        ShopItem(Items.DRAGON_PLATE_ARMOUR_SET_LG, amount = 35500000, sellPrice = 35500000),
        ShopItem(Items.BARROWS__AHRIMS_SET, amount = 2500000, sellPrice = 2500000),
        ShopItem(Items.BARROWS__DHAROKS_SET, amount = 4000000, sellPrice = 4000000),
        ShopItem(Items.BARROWS__GUTHANS_SET, amount = 3500000, sellPrice = 3500000),
        ShopItem(Items.BARROWS__KARILS_SET, amount = 2000000, sellPrice = 2000000),
        ShopItem(Items.BARROWS__TORAGS_SET, amount = 1800000, sellPrice = 1800000),
        ShopItem(Items.BARROWS__VERACS_SET, amount = 2200000, sellPrice = 2200000),
        ShopItem(Items.THIRDAGE_MELEE_SET, amount = 140000000, sellPrice = 140000000),
        ShopItem(Items.THIRDAGE_RANGER_SET, amount = 100000000, sellPrice = 100000000),
        ShopItem(Items.THIRDAGE_MAGE_SET, amount = 110000000, sellPrice = 110000000),
        ShopItem(Items.GREEN_DRAGONHIDE_SET, amount = 12000, sellPrice = 12000),
        ShopItem(Items.BLUE_DRAGONHIDE_SET, amount = 25000, sellPrice = 25000),
        ShopItem(Items.RED_DRAGONHIDE_SET, amount = 55000, sellPrice = 55000),
        ShopItem(Items.BLACK_DRAGONHIDE_SET, amount = 120000, sellPrice = 120000),
        ShopItem(Items.MYSTIC_ROBES_SET, amount = 250000, sellPrice = 250000),
        ShopItem(Items.LIGHT_MYSTIC_ROBES_SET, amount = 250000, sellPrice = 250000),
        ShopItem(Items.DARK_MYSTIC_ROBES_SET, amount = 250000, sellPrice = 250000),
        ShopItem(Items.INFINITY_ROBES_SET, amount = 7000000, sellPrice = 7000000),
        ShopItem(Items.BANDOS_CHESTPLATE, amount = 20000000, sellPrice = 20000000),
        ShopItem(Items.BANDOS_TASSETS, amount = 20000000, sellPrice = 20000000),
        ShopItem(Items.BANDOS_BOOTS, amount = 1500000, sellPrice = 1500000),
        ShopItem(Items.DRAGON_BOOTS, amount = 400000, sellPrice = 400000),
        ShopItem(Items.STEADFAST_BOOTS, amount = 35000000, sellPrice = 35000000),
        ShopItem(Items.GLAIVEN_BOOTS, amount = 20000000, sellPrice = 20000000),
        ShopItem(Items.RAGEFIRE_BOOTS, amount = 15000000, sellPrice = 15000000),
        ShopItem(Items.ARMADYL_HELMET, amount = 20000000, sellPrice = 20000000),
        ShopItem(Items.ARMADYL_CHESTPLATE, amount = 40000000, sellPrice = 40000000),
        ShopItem(Items.ARMADYL_CHAINSKIRT, amount = 30000000, sellPrice = 30000000),
        ShopItem(Items.DRAGONFIRE_SHIELD, amount = 25000000, sellPrice = 25000000),
        ShopItem(Items.ARCANE_SPIRIT_SHIELD, amount = 75000000, sellPrice = 75000000),
        ShopItem(Items.SPECTRAL_SPIRIT_SHIELD, amount = 60000000, sellPrice = 60000000),
        ShopItem(Items.ELYSIAN_SPIRIT_SHIELD, amount = 400000000, sellPrice = 400000000),
        ShopItem(Items.DIVINE_SPIRIT_SHIELD, amount = 600000000, sellPrice = 600000000),
        )

    for ((slotId, item) in itemsList.withIndex()) {
        items[slotId] = item
    }
}

create_shop("Hofuthand's Weapons", CoinCurrency(), stockType = StockType.INFINITE, purchasePolicy = PurchasePolicy.BUY_STOCK, containsSamples = false) {
    val itemsList = listOf(
        ShopItem(Items.ABYSSAL_WHIP, amount = 2500000, sellPrice = 2500000),
        ShopItem(Items.DRAGON_SCIMITAR, amount = 100000, sellPrice = 100000),
        ShopItem(Items.DRAGON_HATCHET, amount = 1500000, sellPrice = 1500000),
        ShopItem(Items.DRAGON_CLAWS, amount = 25000000, sellPrice = 25000000),
        ShopItem(Items.GRANITE_MAUL, amount = 50000, sellPrice = 50000),
        ShopItem(Items.SARADOMIN_SWORD, amount = 6000000, sellPrice = 6000000),
        ShopItem(Items.ARMADYL_GODSWORD, amount = 60000000, sellPrice = 60000000),
        ShopItem(Items.SARADOMIN_GODSWORD, amount = 40000000, sellPrice = 40000000),
        ShopItem(Items.BANDOS_GODSWORD, amount = 30000000, sellPrice = 30000000),
        ShopItem(Items.ZAMORAK_GODSWORD, amount = 20000000, sellPrice = 20000000),
        ShopItem(Items.DARK_BOW, amount = 1000000, sellPrice = 1000000),
        ShopItem(Items.NEW_CRYSTAL_BOW, amount = 900000, sellPrice = 900000),
        ShopItem(Items.RUNE_CROSSBOW, amount = 25000, sellPrice = 25000),
        ShopItem(Items.DRAGON_DART, amount = 3000, sellPrice = 3000),
        ShopItem(Items.RED_CHINCHOMPA_10034, amount = 1000, sellPrice = 1000),
        ShopItem(Items.MASTER_WAND, amount = 1500000, sellPrice = 1500000),
        ShopItem(Items.MAGES_BOOK, amount = 6000000, sellPrice = 6000000),
        ShopItem(Items.ANCIENT_STAFF, amount = 80000, sellPrice = 80000),
        ShopItem(Items.STAFF_OF_LIGHT, amount = 8000000, sellPrice = 8000000),
    )

    for ((slotId, item) in itemsList.withIndex()) {
        items[slotId] = item
    }
}

on_npc_option(npc = Npcs.HOFUTHAND_ARMOUR_AND_WEAPONS, option = "info-combat") {
    player.queue {
        when (options("Armour", "Weapons", title = "Select a shop")) {
            1 -> {
                player.openShop("Hofuthand's Armour", customPrices = true)
            }

            2 -> {
                player.openShop("Hofuthand's Weapons", customPrices = true)
            }
        }
    }
}


on_npc_option(npc = Npcs.HOFUTHAND_ARMOUR_AND_WEAPONS, "talk-to") {
    player.queue {
        chatPlayer("Hello!")
        chatNpc("What? Oh, hello. I was deep in thought. Did you want me to show you the prices of weapons and armour?", wrap = true)
        when(options("You seem a bit flustered.", "Yes, show me the prices of weapons and armour.", "I'll leave you alone.", title = "Select an Option")) {
            1 -> {
                chatPlayer("You seem a bit flustered.", wrap = true)
                chatNpc("Sorry, I'm just deep in thought. I'm waiting for many deals to complete today.", wrap = true)
                chatPlayer("What sort of things are you selling?")
                chatNpc(" Good old weapons and armour! My people - dwarves, you understand - are hoping I can trade with humans.", wrap = true)
                chatPlayer("It looks like you've come to the right place for that.", wrap = true)
                chatNpc("I have indeed, my friend. Now, can I help you?", wrap = true)
                when(options("Yes, show me the prices of weapons and armour.", "I'll leave you alone.", title = "Select an Option")) {
                    1 -> {
                        chatPlayer("Yes, show me the prices of weapons and armour.", wrap = true)
                        when(options("Armour", "Weapons", title = "Select a shop")) {
                            1 -> {
                                player.openShop("Hofuthand's Armour", customPrices = true)
                            }
                            2 -> {
                                player.openShop("Hofuthand's Weapons", customPrices = true)
                            }
                        }
                    }
                    2 -> {
                        chatPlayer("I'll leave you alone.")
                        chatNpc("Thank you, I have much on my mind.")
                    }
                }
            }
            2 -> {
                chatPlayer("Yes, show me the prices of weapons and armour.", wrap = true)
                when(options("Armour", "Weapons", title = "Select a shop")) {
                    1 -> {
                        player.openShop("Hofuthand's Armour", customPrices = true)
                    }
                    2 -> {
                        player.openShop("Hofuthand's Weapons", customPrices = true)
                    }
                }
            }
            3 -> {
                chatPlayer("I'll leave you alone.")
                chatNpc("Thank you, I have much on my mind.")
            }
        }
    }
}
