package gg.rsmod.plugins.content.areas.edgeville

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

create_shop("Oziach's Armour", CoinCurrency(), containsSamples = false, purchasePolicy = PurchasePolicy.BUY_STOCK) {
    items[0] = ShopItem(Items.RUNE_PLATEBODY, 10)
    items[1] = ShopItem(Items.GREEN_DHIDE_BODY, 10)
}

on_npc_option(npc = Npcs.OZIACH, option = "talk-to") {
    player.queue {
        if (player.inventory.contains(Items.DRACONIC_VISAGE)) {
            carryingDraconicVisageDialogue(this)
        } else {
            afterDragonSlayerDialogue(this)
        }
    }
}

on_npc_option(npc = Npcs.OZIACH, option = "trade") {
    player.openShop("Oziach's Armour")
}

/**
suspend fun beforeDragonSlayerDialogue(it: QueueTask) {
 it.chatNpc("Aye, 'tis a fair day my friend.")
 when (it.options("I'm not your friend.", "Yes, it's a very nice day.")) {
 FIRST_OPTION -> {
 it.chatPlayer("I'm not your friend.")
 it.chatNpc("I'm surprised if you're anyone's friend with those kind", "of manners.")
 }
 SECOND_OPTION -> {
 it.chatPlayer("Yes, it's a very nice day.")
 it.chatNpc("Aye, may the gods walk by yer side. Now leave me", "alone.")
 }
 }
}**/

suspend fun afterDragonSlayerDialogue(it: QueueTask) {
    it.chatPlayer("Good day to you.")
    it.chatNpc("Aye, 'tis a fair day, mighty dragon-slaying friend.")
    when (
        it.options(
            "Can I buy a rune platebody now, please?",
            "I'm not your friend.",
            "Yes, it's a very nice day.",
            "Can I have another key to Melzar's Maze?",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Can I buy a rune platebody now please?")
            it.player.openShop("Oziach's Armour")
        }
        SECOND_OPTION -> {
            it.chatPlayer("I'm not your friend.")
            it.chatNpc("I'm surprised if you're anyone's friend with those kind", "of manners.")
        }
        THIRD_OPTION -> {
            it.chatPlayer("Yes, it's a very nice day.")
            it.chatNpc("Aye, may the gods walk by yer side. Now leave me", "alone.")
        }
        FOURTH_OPTION -> {
            it.chatPlayer("Can I have another key to Melzar's Maze?")
            it.chatNpc(
                "It's the Guildmaster in the Champion's Guild who hands those keys out now.",
                "Go talk to him. No need to bother me if you don't need armour.",
            )
        }
    }
}

suspend fun carryingDraconicVisageDialogue(it: QueueTask) {
    it.chatPlayer("Good day to you.")
    it.chatNpc(
        "Aye, 'tis a fair day, mighty dragon-slaying friend.",
        "Ye've got... ye've found a draconic visage! Could I look at it?",
    )
    when (
        it.options(
            "Can I buy a rune platebody now, please?",
            "I'm not your friend.",
            "Yes, it's a very nice day.",
            "Can I have another key to Melzar's Maze?",
            "Here you go.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Can I buy a rune platebody now please?")
            it.player.openShop("Oziach's Armour")
        }
        SECOND_OPTION -> {
            it.chatPlayer("I'm not your friend.")
            it.chatNpc("I'm surprised if you're anyone's friend with those kind", "of manners.")
        }
        THIRD_OPTION -> {
            it.chatPlayer("Yes, it's a very nice day.")
            it.chatNpc("Aye, may the gods walk by yer side. Now leave me", "alone.")
        }
        FOURTH_OPTION -> {
            it.chatPlayer("Can I have another key to Melzar's Maze?")
            it.chatNpc(
                "It's the Guildmaster in the Champion's Guild who hands those keys out now.",
                "Go talk to him. No need to bother me if you don't need armour.",
            )
        }
        FIFTH_OPTION -> {
            it.chatPlayer("Here you go.")
            it.chatNpc("Amazin'! Ye can almost feel it pulsing with draconic power!")
            if (it.player.inventory.contains(Items.ANTIDRAGON_SHIELD)) {
                it.chatNpc(
                    "Now, if ye want me to, I could attach this to yer",
                    "anti-dragonbreath shield and make something pretty special.",
                )
                it.chatNpc(
                    "The shield won't be easy to wield though, ye'll need level 70 Defence. I'll charge 1,250,000 coins to construct it.",
                    wrap = true,
                )
                it.chatNpc("What d'ye say?")
                when (it.options("Yes, please!", "No, thanks.", "That's a bit expensive!")) {
                    FIRST_OPTION -> {
                        it.chatPlayer("Yes, please!")
                        it.player.queue {
                            purchaseDragonfireShield(this)
                        }
                    }
                    SECOND_OPTION -> {
                        it.chatPlayer("No, thanks.")
                        it.chatNpc("Suit yerself.")
                    }
                    THIRD_OPTION -> {
                        it.chatPlayer("That's a bit expensive!")
                        it.chatNpc(
                            "Yes it is. But I'm a master smith so I reckon I can charge what I like.",
                            "Unless ye've got level 90 Smithing too, which I doubt!",
                        )
                        if (it.player.skills.getMaxLevel(Skills.SMITHING) < 90) {
                            it.chatPlayer("No, I don't have level 90 Smithing.")
                            it.chatNpc(
                                "I thought not. I'm the master smith of Misthalin! So, shall I construct the shield for you?",
                            )
                        } else {
                            it.chatPlayer("Actually, I have got level 90 Smithing.")
                            it.chatNpc("Oh! Well, I suppose ye don't need me for this then.")
                        }
                    }
                }
            } else {
                it.chatNpc(
                    "Now, if ye bring me this and the anti-dragonbreath shield,",
                    "I reckon I could combine them into something pretty special!",
                )
            }
        }
    }
}

suspend fun purchaseDragonfireShield(it: QueueTask) {
    if (it.player.inventory.capacity < 1) {
        it.chatPlayer("I don't seem to have enough inventory space.")
        return
    }
    if (it.player.inventory.getItemCount(Items.COINS_995) < 1250000) {
        it.chatPlayer("I don't seem to have enough coins with", "me at this time")
        return
    }
    if (it.player.inventory
            .remove(Item(Items.COINS_995, 1250000))
            .hasSucceeded() &&
        it.player.inventory
            .remove(Item(Items.ANTIDRAGON_SHIELD))
            .hasSucceeded() &&
        it.player.inventory
            .remove(Item(Items.DRACONIC_VISAGE))
            .hasSucceeded()
    ) {
        it.player.inventory.add(Items.DRAGONFIRE_SHIELD)
        it.messageBox("Oziach skillfully forges the shield and visage into a new shield.")
    }
}
