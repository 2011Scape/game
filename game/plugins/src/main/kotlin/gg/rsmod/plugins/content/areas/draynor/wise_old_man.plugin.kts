package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.quests.QUEST_POINT_VARP
import gg.rsmod.plugins.content.quests.Quest
import gg.rsmod.util.Misc

/**
 * @author Alycia <https://github.com/alycii>
 */

on_npc_option(npc = Npcs.WISE_OLD_MAN, option = "talk-to") {
    player.queue {
        if (player.getVarp(QUEST_POINT_VARP) >= Quest.quests.sumOf { it.pointReward }) {
            questCapeDialogue(this)
        } else {
            mainDialogue(this)
        }
    }
}

suspend fun questCapeDialogue(it: QueueTask) {
    when (it.options("Quest Point Cape.", "Something else.")) {
        1 -> {
            it.chatPlayer("I believe you are the person to talk to if I want to buy", "a Quest Point Cape?")
            it.chatNpc(
                "Indeed you believe rightly, ${Misc.formatForDisplay(it.player.username)}, and if you",
                "know that then you'll also know that they cost 99,000",
                "coins.",
            )
            when (it.options("No, I hadn't heard that!", "Yes, so I was lead to believe")) {
                1 -> {
                    it.chatPlayer("No, I hadn't heard that!")
                    it.chatNpc("Well that's the cost, and it's not changing.")
                }
                2 -> {
                    it.chatPlayer("Yes, so I was lead to believe.")
                    purchaseCape(it)
                }
            }
        }
        2 -> mainDialogue(it)
    }
}

suspend fun purchaseCape(it: QueueTask) {
    if (it.player.inventory.capacity < 2) {
        it.chatPlayer("I don't seem to have enough inventory space.")
        return
    }
    if (it.player.inventory.getItemCount(Items.COINS_995) < 99000) {
        it.chatPlayer("I don't seem to have enough coins with", "me at this time")
        return
    }
    if (it.player.inventory
            .remove(Item(Items.COINS_995, 99000))
            .hasSucceeded()
    ) {
        it.player.inventory.add(Items.QUEST_POINT_CAPE)
        it.player.inventory.add(Items.QUEST_POINT_HOOD)
        it.chatNpc("Have fun with it.")
    }
}

suspend fun mainDialogue(it: QueueTask) {
    it.chatNpc("Greetings, ${Misc.formatForDisplay(it.player.username)}.")
    it.chatPlayer("I want to ask you about your hat.")
    it.chatNpc("Why, thank you! I rather like it myself.")
}
