package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.plugins.content.quests.finishedQuest

val CHARTER_SELECTION_INTERFACE = 95
val CHARTER_INTERFACE = 299
val CHARTERVARP = 75

val FADE_IN_INTERFACE = 115
val FADE_OUT_INTERFACE = 170

fun setSail(
    player: Player,
    charter: CharterType,
    port: Ports,
    cost: Int,
) {
    player.closeInterface(CHARTER_SELECTION_INTERFACE)
    player.interruptQueues()
    player.lockingQueue(TaskPriority.WEAK) {
        val varpValue = charter.varpValue
        val delay = charter.delay
        val boatTile = port.tile
        if (charter.varpValue > 0) {
            player.openInterface(CHARTER_INTERFACE, InterfaceDestination.MAIN_SCREEN_FULL)
            player.setVarp(CHARTERVARP, varpValue)
            if (cost > 0) {
                player.message("You pay $cost coins and board the ship.")
            }
        } else {
            player.openInterface(FADE_IN_INTERFACE, InterfaceDestination.MAIN_SCREEN_FULL)
        }
        if (delay > 0) wait(delay)
        player.openInterface(FADE_OUT_INTERFACE, InterfaceDestination.MAIN_SCREEN_FULL)
        player.moveTo(boatTile)
        val coins = Item(Items.COINS_995, amount = cost)
        val remove = player.inventory.remove(coins)
        if (charter == CharterType.FADE_TO_BLACK) {
            player.message("You pay $cost coins and sail to ${port.portName}.".formatNumber())
        }
        wait(3)
        player.closeMainInterface()
        player.queue(TaskPriority.WEAK) {
            if (charter != CharterType.FADE_TO_BLACK) {
                messageBox("The ship arrives at ${port.portName}.")
            }
        }
    }
}

enumValues<Ports>().forEach { port ->
    if (port.component == -1) return@forEach
    on_button(interfaceId = CHARTER_SELECTION_INTERFACE, port.component) {
        val destination = port.destination.find { player.tile.isWithinRadius(it.tile, 50) } ?: return@on_button
        if (player.inventory.getItemCount(Items.COINS_995) < destination.cost) {
            player.closeInterface(InterfaceDestination.MAIN_SCREEN)
            player.queue {
                chooseAgainDialogue(this, port, destination)
            }
        } else {
            setSail(player, port.charter, port, destination.cost)
        }
    }
}

suspend fun chooseAgainDialogue(
    it: QueueTask,
    port: Ports,
    destination: Destinations,
) {
    when (
        it.options(
            "Choose again",
            "Cancel",
            title = "Sailing to ${port.portName}<br> costs ${destination.cost} coins.".formatNumber(),
        )
    ) {
        1 -> {
            openSailInterface(it.player)
        }
    }
}
arrayOf(Npcs.MONK_OF_ENTRANA_658, Npcs.MONK_OF_ENTRANA_2730, Npcs.MONK_OF_ENTRANA_2731).forEach { monk ->
    on_npc_option(monk, "Travel") {
        player.queue {
            setSail(player, CharterType.ENTRANA_TO_PORT_SARIM, Ports.PORT_SARIM, 0)
        }
    }
}

arrayOf(Npcs.MONK_OF_ENTRANA, Npcs.MONK_OF_ENTRANA_2729, Npcs.MONK_OF_ENTRANA_2728).forEach { monk ->
    on_npc_option(monk, "Travel") {
        player.queue {
            messageBox("The monk quickly searches you.")
            if (player.hasEntranaRestrictedEquipment()) {
                chatNpc(
                    "NO WEAPONS OR ARMOUR are permitted on holy Entrana",
                    "AT ALL. We will not allow you to travel there in",
                    "breach of mighty Saradomin's edict.",
                )
                chatNpc(
                    "Do not try to deceive us again. Come back when",
                    "you have laid down your Zamorakian instruments",
                    "of death.",
                )
            } else {
                chatNpc("All is satisfactory. You may board the boat now.")
                setSail(player, CharterType.PORT_SARIM_TO_ENTRANA, Ports.ENTRANA_MONKS, 0)
            }
        }
    }
}

arrayOf(Npcs.MONK_OF_ENTRANA, Npcs.MONK_OF_ENTRANA_2729, Npcs.MONK_OF_ENTRANA_2728).forEach { monk ->
    on_npc_option(monk, "talk-to") {
        player.queue {
            chatNpc(
                "Do you seek passage to holy Entrana? If so, you must",
                "leave your weaponry and armour behind. This is",
                "Saradomin's will.",
            )
            when (options("No, not right now.", "Yes, okay, I'm ready to go.")) {
                1 -> {
                    chatPlayer("No, not right now.")
                }
                2 -> {
                    chatPlayer("Yes, okay, I'm ready to go.")
                    chatNpc("Very well. One moment please.")
                    messageBox("The monk quickly searches you.")
                    if (player.hasEntranaRestrictedEquipment()) {
                        chatNpc(
                            "NO WEAPONS OR ARMOUR are permitted on holy Entrana",
                            "AT ALL. We will not allow you to travel there in",
                            "breach of mighty Saradomin's edict.",
                        )
                        chatNpc(
                            "Do not try to deceive us again. Come back when",
                            "you have laid down your Zamorakian instruments",
                            "of death.",
                        )
                    } else {
                        chatNpc("All is satisfactory. You may board the boat now.")
                        setSail(player, CharterType.PORT_SARIM_TO_ENTRANA, Ports.ENTRANA_MONKS, 0)
                    }
                }
            }
        }
    }
}

arrayOf(Npcs.CAPTAIN_TOBIAS, Npcs.SEAMAN_LORRIS, Npcs.SEAMAN_THRESNOR).forEach { npc ->
    on_npc_option(npc, "Talk-to") {
        player.queue {
            karamjaDialogue(this, leaving = false)
        }
    }
    on_npc_option(npc, "Pay-fare") {
        if (player.inventory.getItemCount(Items.COINS_995) < 30) {
            player.message("You do not have enough money for that.")
            return@on_npc_option
        }
        setSail(player, CharterType.PORT_SARIM_TO_KARAMJA, Ports.MUSA_POINT, 30)
    }
}

on_npc_option(Npcs.CUSTOMS_OFFICER, "Talk-to") {
    player.queue {
        karamjaDialogue(this, leaving = true)
    }
}

on_npc_option(Npcs.CUSTOMS_OFFICER, "Pay-fare") {
    if (player.inventory.getItemCount(Items.COINS_995) < 30) {
        player.message("You do not have enough coins to pay passage, you need 30.")
        return@on_npc_option
    }
    setSail(player, CharterType.KARAMJA_TO_PORT_SARIM, Ports.CAPTAIN_TOBIAS_BOAT, 30)
}

suspend fun karamjaDialogue(
    it: QueueTask,
    leaving: Boolean,
) {
    if (leaving) {
        it.chatNpc("Can I help you?")
        when (it.options("Can I journey on this ship?", "Does Karamja have unusual customs then?")) {
            1 -> {
                it.chatPlayer("Can I journey on this ship?")
                it.chatNpc("Hey, I know you, you work at the plantation.")
                it.chatNpc(
                    *"I don't think you'll try smuggling anything, you just need to pay a boarding charge of 30 coins."
                        .splitForDialogue(),
                )
                when (it.options("Ok.", "Oh, I'll not bother then.")) {
                    1 -> {
                        it.chatPlayer("Ok.")
                        if (it.player.inventory.getItemCount(Items.COINS_995) < 30) {
                            it.chatPlayer("Oh dear, I don't seem to have enough money.")
                            return
                        }
                        setSail(it.player, CharterType.KARAMJA_TO_PORT_SARIM, Ports.CAPTAIN_TOBIAS_BOAT, 30)
                    }

                    2 -> {
                        it.chatPlayer("Oh, I'll not bother then.")
                    }
                }
                if (it.player.inventory.getItemCount(Items.COINS_995) < 30) {
                    it.chatPlayer("Oh dear, I don't seem to have enough money.")
                    return
                }
                setSail(it.player, CharterType.PORT_SARIM_TO_KARAMJA, Ports.MUSA_POINT, 30)
            }

            2 -> {
                it.chatPlayer("Does Karamja have any unusual customs then?")
                it.chatNpc("I'm not that sort of customs officer.")
            }
        }
    } else {
        it.chatNpc("Do you want to go on a trip to Karamja?")
        it.chatNpc("The trip will cost you 30 coins.")
        when (it.options("Yes please.", "No, thank you.")) {
            1 -> {
                it.chatPlayer("Yes please.")
                if (it.player.inventory.getItemCount(Items.COINS_995) < 30) {
                    it.chatPlayer("Oh dear, I don't seem to have enough money.")
                    return
                }
                setSail(it.player, CharterType.PORT_SARIM_TO_KARAMJA, Ports.MUSA_POINT, 30)
            }

            2 -> {
                it.chatPlayer("No, thank you.")
            }
        }
    }
}

suspend fun traderDialogue(it: QueueTask) {
    it.chatNpc("Greetings, adventurer. How can I help you?")
    when (it.options("Yes, who are you?", "Yes, I would like to charter a ship.", "No thanks.")) {
        1 -> {
            optionOne(it)
        }

        2 -> {
            charterShip(it)
        }

        3 -> {
            it.chatPlayer("No, thanks.")
        }
    }
}

suspend fun optionOne(it: QueueTask) {
    it.chatPlayer("Yes, who are you?")
    if (it.player.getInteractingNpc().id == Npcs.TRADER_STAN) {
        it.chatNpc(
            *
                "Why, I'm Trader Stan, owner and operator of the largest fleet of trading ships and chartered vessels to ever sail the seas!"
                    .splitForDialogue(),
        )
    } else {
        it.chatNpc(
            *"I'm one of the Trader Stan's crew; we are all part of the largest fleet of trading and sailing vessels to ever sail the seven seas."
                .splitForDialogue(),
        )
    }
    it.chatNpc(
        *"If you want to get to a port in a hurry then you can charter one of our ships to take you there - if the price is right..."
            .splitForDialogue(),
    )
    it.chatPlayer("So, where exactly can I go with your ships?")
    it.chatNpc(
        *"We run ships from Port Phasmatys over to Port Tyras, stopping at Port Sarim, Catherby, Brimhaven, Musa Point, the Shipyard and Port Khazard."
            .splitForDialogue(),
    )
    it.chatNpc(
        *"We might dock at Mos Le'Harmless and oo'glog once in a while, as well, if you catch my meaning..."
            .splitForDialogue(),
    )
    it.chatPlayer(*"Wow, that's a lot of ports. I take it you have some exotic stuff to trade?".splitForDialogue())
    it.chatNpc(
        *"We certainly do! We have access to items bought and sold from around the world. Would you like to take a look? Or would you like to charter a ship?"
            .splitForDialogue(),
    )
    if (it.player.getInteractingNpc().id == Npcs.TRADER_STAN) {
        traderQuestions(it)
    } else {
        when (
            it.options(
                "Yes, let's see what you're trading.",
                "Yes, I would like to charter a ship.",
                "Isn't it tricky to sail about in those clothes?",
                "No thanks.",
            )
        ) {
            1 -> {
                openShop(it)
            }

            2 -> {
                charterShip(it)
            }

            3 -> {
                it.chatPlayer("Isn't it tricky to sail about in those clothes?")
                it.chatNpc("Tricky? Tricky!")
                it.chatNpc(
                    *"With all due credit, Trader Stan is a great employer, but he insists we wear the latest in high fashion even when sailing."
                        .splitForDialogue(),
                )
                it.chatNpc(
                    *"Do you have even the slightest idea how tricky it is to sail in this stuff?".splitForDialogue(),
                )
                it.chatNpc(
                    *"Some of us tried tearing it and arguing that it was too fragile to wear when on a boat, but he just had it enchanted to re-stitch itself."
                        .splitForDialogue(),
                )
                it.chatNpc(
                    *"It's hard to hate him when we know how much he shells out on this gear, but if I fall overboard because of this getup one more time, I'm going to quit."
                        .splitForDialogue(),
                )
                it.chatPlayer("Wow, that's kind of harsh.")
                it.chatNpc(
                    *"Anyway, would you like to take a look at our exotic wares from around the world? Or would you like to charter a ship?"
                        .splitForDialogue(),
                )
                traderQuestions(it)
            }

            4 -> {
                it.chatPlayer("No thanks.")
            }
        }
    }
}

suspend fun traderQuestions(it: QueueTask) {
    when (it.options("Yes, let's see what you're trading.", "Yes, I would like to charter a ship.", "No thanks.")) {
        1 -> {
            openShop(it)
        }

        2 -> {
            charterShip(it)
        }

        3 -> {
            it.chatPlayer("No thanks.")
        }
    }
}

suspend fun openShop(it: QueueTask) {
    it.chatPlayer("Yes, let's see what you're trading.")
    it.player.openShop("Trader Stan's Trading Post")
}

suspend fun charterShip(it: QueueTask) {
    it.chatPlayer("Yes, I would like to charter a ship.")
    it.chatNpc("Certainly sir. Where would you like to go?")
    openSailInterface(it.player)
}

fun openSailInterface(player: Player) {
    player.openInterface(CHARTER_SELECTION_INTERFACE, InterfaceDestination.MAIN_SCREEN)
    for (i in Ports.PORT_TYRAS.component until Ports.OO_GLOG.component + 1) {
        player.setComponentHidden(interfaceId = CHARTER_SELECTION_INTERFACE, component = i, hidden = true)
    }
    player.setComponentHidden(CHARTER_SELECTION_INTERFACE, component = 32, hidden = true)
    enumValues<Ports>().forEach { port ->
        val destination = port.destination.find { player.tile.isWithinRadius(it.tile, 50) }
        val quest = if (port.questReq != null) port.questReq?.let { player.finishedQuest(it) } else true
        if (destination != null && quest == true) {
            player.setComponentHidden(
                interfaceId = CHARTER_SELECTION_INTERFACE,
                component = port.component,
                hidden = false,
            )
        }
    }
}

crewMembers.forEach { trader ->
    on_npc_option(trader, "Talk-to") {
        player.queue {
            traderDialogue(this)
        }
    }
    on_npc_option(trader, "Trade") {
        player.openShop("Trader Stan's Trading Post")
    }
    on_npc_option(trader, "Charter") {
        openSailInterface(player)
    }
}

create_shop(
    "Trader Stan's Trading Post",
    CoinCurrency(),
    containsSamples = false,
    purchasePolicy = PurchasePolicy.BUY_TRADEABLES,
) {
    items[0] = ShopItem(Items.EMPTY_POT, 5)
    items[1] = ShopItem(Items.JUG, 2)
    items[2] = ShopItem(Items.SHEARS, 2)
    items[3] = ShopItem(Items.BUCKET, 3)
    items[4] = ShopItem(Items.BOWL, 2)
    items[5] = ShopItem(Items.CAKE_TIN, 2)
    items[6] = ShopItem(Items.TINDERBOX_590, 2)
    items[7] = ShopItem(Items.CHISEL, 2)
    items[8] = ShopItem(Items.HAMMER, 5)
    items[9] = ShopItem(Items.NEWCOMER_MAP, 5)
    items[10] = ShopItem(Items.SECURITY_BOOK, 5)
    items[11] = ShopItem(Items.ROPE, 2)
    items[12] = ShopItem(Items.KNIFE, 2)
    items[13] = ShopItem(Items.PINEAPPLE, 15)
    items[14] = ShopItem(Items.BANANA, 15)
    items[15] = ShopItem(Items.ORANGE, 10)
    items[16] = ShopItem(Items.BUCKET_OF_SLIME, 10)
    items[17] = ShopItem(Items.GLASSBLOWING_PIPE, 15)
    items[18] = ShopItem(Items.BUCKET_OF_SAND, 10)
    items[19] = ShopItem(Items.SEAWEED, 20)
    items[20] = ShopItem(Items.SODA_ASH, 10)
    items[21] = ShopItem(Items.LOBSTER_POT, 20)
    items[22] = ShopItem(Items.FISHING_ROD, 20)
    items[23] = ShopItem(Items.SWAMP_PASTE, 30)
    items[24] = ShopItem(Items.TYRAS_HELM, 25)
    items[25] = ShopItem(Items.RAW_RABBIT, 20)
    items[26] = ShopItem(Items.EYE_PATCH, 5)
}
