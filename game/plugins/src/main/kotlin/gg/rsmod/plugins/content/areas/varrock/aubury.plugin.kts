package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.RuneMysteries

/**
 * @author Alycia <https://github.com/alycii>
 */

create_shop("Aubury's Rune Shop", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_STOCK) {
    sampleItems[0] = ShopItem(Items.AIR_RUNE, 30, resupplyCycles = 99)
    sampleItems[1] = ShopItem(Items.MIND_RUNE, 30, resupplyCycles = 99)
    items[0] = ShopItem(Items.FIRE_RUNE, 300)
    items[1] = ShopItem(Items.WATER_RUNE, 300)
    items[2] = ShopItem(Items.AIR_RUNE, 300)
    items[3] = ShopItem(Items.EARTH_RUNE, 300)
    items[4] = ShopItem(Items.MIND_RUNE, 100)
    items[5] = ShopItem(Items.BODY_RUNE, 100)
    items[6] = ShopItem(Items.CHAOS_RUNE, 30)
    items[7] = ShopItem(Items.DEATH_RUNE, 10)
}

on_npc_option(npc = Npcs.AUBURY, option = "Trade") {
    player.openShop("Aubury's Rune Shop")
}

on_npc_option(npc = Npcs.AUBURY, option = "teleport") {
    if (!player.finishedQuest(RuneMysteries)) {
        player.message("You must've completed Rune Mysteries to teleport to the essence mines.")
        return@on_npc_option
    }
    essenceTeleport(player, targetTile = Tile(2920, 4821))
}

on_npc_option(npc = Npcs.AUBURY, option = "talk-to") {
    player.queue {
        chatNpc("Do you want to buy some runes?")
        when (player.getCurrentStage(RuneMysteries)) {
            4 -> packageDelivered(this)
            else -> chat(this)
        }
    }
}

suspend fun chat(it: QueueTask) {
    val thirdOption =
        when (it.player.getCurrentStage(RuneMysteries)) {
            3 -> "I've been sent here with a package for you."
            5 -> "Ask about research notes."
            else -> ""
        }
    when (it.options("Yes, please.", "No, thank you.", thirdOption)) {
        1 -> {
            it.chatPlayer("Yes, please.")
            it.player.openShop("Aubury's Rune Shop")
        }
        2 -> {
            it.chatPlayer("No, thank you.")
        }
        3 -> {
            when (it.player.getCurrentStage(RuneMysteries)) {
                3 -> {
                    it.chatPlayer(
                        "I've been sent here with a package for you. It's from",
                        "Sedridor, the head wizard at the Wizards' Tower.",
                    )
                    it.chatNpc("Really? Surely he can't have...? Please, let me have it.")
                    if (it.player.inventory.contains(Items.RESEARCH_PACKAGE)) {
                        it.messageBox("You hand Aubury the research package.")
                        it.player.inventory.remove(Items.RESEARCH_PACKAGE)
                        it.player.advanceToNextStage(RuneMysteries)
                        packageDelivered(it)
                    } else {
                        it.chatPlayer("...except I don't have it with me...")
                    }
                }
                5 -> {
                    if (it.player.hasItem(Items.RESEARCH_NOTES)) {
                        it.chatPlayer("What should I do with these research notes?")
                        it.chatNpc(
                            "Take my research back to Sedridor in the basement of the",
                            "Wizards' Tower. He will know whether or not to let",
                            "you in on our little secret.",
                        )
                        teleportToWizardsTower(it)
                    } else {
                        it.chatPlayer("I seem to have lost the research notes...")
                        it.chatNpc("No matter, I have a copy here.")
                        receiveNotes(it)
                    }
                }
            }
        }
    }
}

suspend fun packageDelivered(it: QueueTask) {
    it.chatNpc("My gratitude, adventurer, for bringing me", "this research package.")
    it.chatNpc(
        "Combined with the information I have already collated",
        "regarding rune essence, I think we have finally",
        "unlocked the power to...",
    )
    it.chatNpc(
        "No, I'm getting ahead of myself. Take this summary of",
        "my research back to Sedridor in the basement of the",
        "Wizards' Tower. He will know whether or not to let",
        "you in on our little secret.",
    )
    if (receiveNotes(it)) {
        it.player.advanceToNextStage(RuneMysteries)
        teleportToWizardsTower(it)
    }
}

suspend fun receiveNotes(it: QueueTask): Boolean {
    return if (it.player.inventory.hasSpace) {
        it.messageBox("Aubury gives you his research notes.")
        it.player.inventory.add(Item(Items.RESEARCH_NOTES))
        true
    } else {
        it.chatNpc("Please clear up some inventory space", "so I can give you these notes.")
        false
    }
}

suspend fun teleportToWizardsTower(it: QueueTask) {
    it.chatNpc(
        "Now, I'm sure I can spare a couple of runes for such",
        "a worthy cause as these notes. Do you want me to",
        "teleport you back?",
    )
    when (it.options("Yes, please.", "No, thank you.")) {
        1 -> {
            it.chatPlayer("Yes, please.")
            essenceTeleport(it.player, dialogue = "Sparanti morduo calmentor!", Tile(3113, 3199, 0))
        }
        2 -> {
            it.chatPlayer("No, thank you.")
        }
    }
}
