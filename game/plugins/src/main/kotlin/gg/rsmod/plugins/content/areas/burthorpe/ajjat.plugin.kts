package gg.rsmod.plugins.content.areas.burthorpe

import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.AJJAT, option = "talk-to") {
    player.queue {
        this.chatNpc(
            "Greetings, fellow warrior. I am Ajjat, former Black",
            "Knight and now training officer here in the Warriors'",
            "Guild.",
        )
        if (player.skills.getCurrentLevel(Skills.ATTACK) >= 99) {
            chatNpc(
                "Ah, but I can see you are already a master in the fine art",
                "of attacking, perhaps you have come to me to purchase a",
                "Skillcape of Attack, and thus join the elite few who have",
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
                "Ah, this is a skillcape of Attack. I have mastered the",
                "skill of attack and wear it proudly to show others.",
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
            it.chatPlayer("May I buy a Skillcape of Attack, please?")
            it.chatNpc(
                "Most certainly; unfortunately being such a prestigious",
                "item, they are appropriately expensive. I'm afraid I must",
                "ask you for the princely sum of 99000 gold!",
            )
            when (it.options("99000 gold! Are you mad?", "I would gladly pay such a paltry sum for a splendid cape.")) {
                FIRST_OPTION -> {
                    it.chatPlayer("99000 gold! Are you mad?")
                    it.chatNpc(
                        "Not at all; there are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.",
                    )
                }
                SECOND_OPTION -> {
                    it.chatPlayer("I would gladly pay such a paltry sum for a splendid cape.")
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Attack.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.ATTACK)
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
