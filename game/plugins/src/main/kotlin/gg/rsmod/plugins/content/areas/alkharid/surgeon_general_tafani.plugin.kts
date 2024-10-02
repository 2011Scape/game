package gg.rsmod.plugins.content.areas.alkharid

import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.SURGEON_GENERAL_TAFANI, option = "talk-to") {
    player.queue {
        chatPlayer("Hi!")
        chatNpc("Hi. How can I help?")
        if (player.skills.getCurrentLevel(Skills.CONSTITUTION) >= 99) {
            mainChatWith99(this)
        } else {
            mainChat(this)
        }
    }
}

suspend fun mainChat(it: QueueTask) {
    when (
        it.options(
            "Can you heal me?",
            "Do you see a lot of injured fighters?",
            "Do you come here often?",
            "Bye.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Can you heal me?")
        }
        SECOND_OPTION -> {
            it.chatPlayer("Do you see a lot of injured fighters?")
            it.chatNpc(
                "Yes, I do. Thankfully, we can cope with almost",
                "anything. Jaraah really is a wonderful surgeon;",
                "his methods are a little unorthodox",
                "but he gets the job done.",
            )
            it.chatNpc(
                "I shouldn't tell you this, but his",
                "nickname is 'The Butcher'.",
            )
            it.chatPlayer("That's reassuring.")
        }
        THIRD_OPTION -> {
            it.chatPlayer("Do you come here often?")
            it.chatNpc("I work here, so yes. You're silly!")
        }
        FOURTH_OPTION -> {
            it.chatPlayer("Bye.")
        }
    }
}

suspend fun mainChatWith99(it: QueueTask) {
    when (
        it.options(
            "Can you heal me?",
            "Do you see a lot of injured fighters?",
            "Do you come here often?",
            "Can I buy a Skillcape of Constitution from you?",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Can you heal me?")
        }
        SECOND_OPTION -> {
            it.chatPlayer("Do you see a lot of injured fighters?")
            it.chatNpc(
                "Yes, I do. Thankfully, we can cope with almost",
                "anything. Jaraah really is a wonderful surgeon;",
                "his methods are a little unorthodox",
                "but he gets the job done.",
            )
            it.chatNpc(
                "I shouldn't tell you this, but his",
                "nickname is 'The Butcher'.",
            )
            it.chatPlayer("That's reassuring.")
        }
        THIRD_OPTION -> {
            it.chatPlayer("Do you come here often?")
            it.chatNpc("I work here, so yes. You're silly!")
        }
        FOURTH_OPTION -> {
            it.chatPlayer("Can I buy a Skillcape of Constitution from you?")
            it.chatNpc(
                "Why, certainly my friend. However, owning such an",
                "item makes you part of an elite group and that privilege",
                "will cost you 99000 coins.",
            )
            when (
                it.options(
                    "Sorry, that's much too pricey.",
                    "Sure, that's not too expensive for such a magnificent cape.",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("Sorry, that's much too pricey.")
                    it.chatNpc(
                        "Not at all; there are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.",
                    )
                }
                SECOND_OPTION -> {
                    it.chatPlayer("Sure, that's not too expensive for such a magnificent cape.")
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Constitution.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.CONSTITUTION)
                        it.chatNpc("Wear this cape in good health, my friend.")
                    } else {
                        it.chatPlayer("But, unfortunately, I don't have enough gold.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
            }
        }
    }
}
