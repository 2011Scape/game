package gg.rsmod.plugins.content.areas.kandarin
import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.MASTER_FISHER, option = "talk-to") {
    player.queue {
        if (player.skills.getCurrentLevel(Skills.FISHING) >= 99) {
            mainChatWith99(this)
        } else {
            mainChat(this)
        }
    }
}

on_npc_option(npc = Npcs.MASTER_FISHER, option = "pickpocket") {
    player.queue {
        player.message("You have no reason to pickpocket him.")
    }
}

suspend fun mainChat(it: QueueTask) {
    it.chatNpc(
        "Hello, welcome to the Fishing Guild. Please feel free",
        "to make use of any of our facilities.",
    )
}

suspend fun mainChatWith99(it: QueueTask) {
    it.chatNpc(
        "Hello, welcome to the Fishing Guild. Please feel free",
        "to make use of any of our facilities.",
    )
    when (
        it.options(
            "Yes.",
            "No.",
            title = "Ask about his cape?",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Can you tell me about that skillcape you're wearing?")
            it.chatNpc(
                "I'm happy to say that being such a fine fisher yourself,",
                "I'm happy to offer you the opportunity to purchase a",
                "fine skillcape just like this one. Are you interested? It",
                "would cost 99000 coins.",
            )
            when (
                it.options(
                    "Yes.",
                    "No.",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("Yes, please.")
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Fishing.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.FISHING)
                        it.chatNpc("Excellent! Wear that cape with pride my friend.")
                    } else {
                        it.chatPlayer("But, unfortunately, I don't have enough gold.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
                SECOND_OPTION -> {
                    it.chatPlayer("No, thanks.")
                    it.chatNpc(
                        "There are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.",
                    )
                }
            }
        }
        SECOND_OPTION -> {
        }
    }
}
