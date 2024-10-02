package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.MELEE_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc("Greetings adventurer, I am the Melee combat tutor. Is", "there anything I can do for you?")
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (
        it.options(
            "Who are you?",
            "What is that cape you're wearing?",
            title = "What would you like to talk about?",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Who are you?")
            it.chatNpc("My name is Harlan, a master of defence!")
        }
        SECOND_OPTION -> {
            it.chatPlayer("What is that cape you're wearing?")
            it.chatNpc(
                "Ah, this is a skillcape of Defence. I have mastered the",
                "art of defence and wear it proudly to show others.",
            )
            it.chatPlayer("Hmm, interesting.")
            if (it.player.skills.getMaxLevel(Skills.DEFENCE) >= 99)
                {
                    it.chatNpc(
                        "Ah, I can see you're already a master in the fine ",
                        "art of Defence. Perhaps you have come to me to ",
                        "purchase a Skillcape of Defence, and thus join the ",
                        "elite few who have mastered this exacting skill?",
                    )
                    when (it.options("Yes, please sell me a Skillcape of Defence", "No, thanks.")) {
                        FIRST_OPTION -> {
                            it.terminateAction
                            it.player.queue { buySkillcape(this) }
                        }
                        SECOND_OPTION -> {
                            it.chatPlayer("No, thanks.")
                        }
                    }
                } else {
                it.chatNpc(
                    "Of course. Skillcapes are a symbol of achievement. ",
                    "Only people who have mastered a skill and reached ",
                    "level 99 can get their hands on them ",
                    "and gain the benefits they carry.",
                )
                it.chatNpc("Is there anything else you would like to know?")
                it.terminateAction
                it.player.queue { mainChat(this) }
            }
        }
    }
}

suspend fun buySkillcape(it: QueueTask) {
    it.chatPlayer("May I buy a Skillcape of Defence, please?")
    it.chatNpc(
        "You wish to join the elite defenders of this world?",
        "I'm afraid such things do not come cheaply -",
        "in fact they cost 99000 coins, to be precise!",
    )
    when (it.options("99000 coins? That's much too expensive.", "I think I have the money right here, actually.")) {
        FIRST_OPTION -> {
            it.chatPlayer("99000 coins? That's much too expensive.")
            it.chatNpc(
                "Not at all; there are many other adventurers who",
                "would love the opportunity to purchase such a",
                "prestigious item! You can find me here if you change",
                "your mind.",
            )
        }
        SECOND_OPTION -> {
            it.chatPlayer("I think I have the money right here, actually.")
            if (it.player.inventory.freeSlotCount < 2) {
                it.chatNpc(
                    "You don't have enough free space in your inventory ",
                    "for me to sell you a Skillcape of Defence.",
                )
                it.chatNpc("Come back to me when you've cleared up some space.")
                return
            }
            if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                Skills.purchaseSkillcape(it.player, data = Skillcapes.DEFENCE)
                it.chatNpc("Excellent! Wear that cape with pride my friend.")
            } else {
                it.chatPlayer("But, unfortunately, I was mistaken.")
                it.chatNpc("Well, come back and see me when you do.")
            }
        }
    }
}
