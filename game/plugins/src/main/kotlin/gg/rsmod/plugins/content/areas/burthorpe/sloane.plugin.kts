package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.SLOANE, option = "talk-to") {
    val playername = player.username
    player.queue {
        this.chatNpc(
            "Ahhh, hello there, $playername.",
        )
        if (player.skills.getCurrentLevel(Skills.STRENGTH) >= 99) {
            chatNpc(
                "Ah, but I can see you are already as strong as is",
                "possible! Perhaps you have come to me to purchase a",
                "Skillcape of Strength, and thus join the elite few who have",
                "mastered this exacting skill?",
            )
            mainChatWith99(this)
        } else {
            mainChat(this)
        }
    }
}

suspend fun mainChat(it: QueueTask) {
    when (
        it.options(
            "What is that cape you're wearing?",
            "Bye",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("What is that cape you're wearing?")
            it.chatNpc(
                "Ah, this is a skillcape of Strength. I have mastered the",
                "art of Strength and wear it proudly to show others.",
            )
            it.chatPlayer("Hmm, interesting.")
        }
        SECOND_OPTION -> {
            // nothing, so close
        }
    }
}

suspend fun mainChatWith99(it: QueueTask) {
    when (
        it.options(
            "May I buy a skillcape, please?",
            "Bye",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("May I buy a Skillcape of Strength, please?")
            it.chatNpc(
                "Hmm, yes, you certainly look strong enough. That'll be",
                "99000 gold coins, please.",
            )
            when (it.options("Sorry, that's just too much to pay.", "Sure.")) {
                FIRST_OPTION -> {
                    it.chatPlayer("Sorry, that's just too much to pay.")
                    it.chatNpc(
                        "Not at all; there are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.",
                    )
                }
                SECOND_OPTION -> {
                    it.chatPlayer("Sure.")
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Strength.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.STRENGTH)
                        it.chatNpc("Excellent! Wear that cape with pride my friend.")
                    } else {
                        it.chatPlayer("But, unfortunately, I don't have enough gold.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
            }
        }
        SECOND_OPTION -> {
            // nothing, so closes
        }
    }
}
