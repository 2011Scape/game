package gg.rsmod.plugins.content.areas.asgarnia
import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.MASTER_CRAFTER, option = "talk-to") {
    player.queue {
        if (player.skills.getCurrentLevel(Skills.CRAFTING) >= 99) {
            mainChatWith99(this)
        } else {
            mainChat(this)
        }
    }
}

suspend fun mainChat(it: QueueTask) {
    it.chatNpc(
        "Hello, and welcome to the Crafting Guild. Accomplished crafters from all over the land come here to use our top" +
            " notch workshops.",
        wrap = true,
    )
    it.chatNpc("Would you like to ask about a Skillcape of Crafting?", wrap = true)
    when (
        it.options(
            "Yes.",
            "No.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("Hey, what is that cape you're wearing? I don't recognise it.", wrap = true)
            it.chatNpc(
                "This? This is a Skillcape of Crafting. It is a symbol of my ability as master of the" +
                    " Crafting Guild.",
                wrap = true,
            )
            it.chatNpc(
                "If you should ever achieve level 99 Crafting come and talk to me and we'll see if we can" +
                    " sort you out with one.",
                wrap = true,
            )
        }
        SECOND_OPTION -> {
            return
        }
    }
}

suspend fun mainChatWith99(it: QueueTask) {
    it.chatNpc(
        "Hello, and welcome to the Crafting Guild. Accomplished crafters from all over the land come here to use our top" +
            " notch workshops.",
        wrap = true,
    )
    it.chatPlayer(
        "Are you the person I should be talking to about buying a",
        "Skillcape of Crafting?",
    )
    it.chatNpc("I certainly am, and I can see that you are definitely talented enough to own one!", wrap = true)
    it.chatNpc(
        "Unfortunately, being such a prestigious item, they are appropriately expensive. I'm afraid I must" +
            " ask you for 99000 gold.",
        wrap = true,
    )
    when (
        it.options(
            "99000 gold! Are you mad?",
            "That's fine.",
            title = "Select an Option",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("99000 gold! Are you mad?", facialExpression = FacialExpression.DISBELIEF)
            it.chatNpc(
                " Not at all; there are many other adventurers who would love the opportunity to purchase" +
                    " such a prestigious item! You can find me here if you change your mind.",
                wrap = true,
            )
            return
        }
        SECOND_OPTION -> {
            it.chatPlayer("That's fine.")
            if (it.player.inventory.freeSlotCount < 2) {
                it.chatNpc(
                    "Unfortunately all Skillcapes are only available with a free hood, it's part of a skill" +
                        " promotion deal; buy one get one free, you know. So you'll need to free up some inventory " +
                        "space before I can sell you one.",
                    wrap = true,
                )
                it.chatNpc("Come back to me when you've cleared up some space.")
                return
            }
            if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                Skills.purchaseSkillcape(it.player, data = Skillcapes.CRAFTING)
                it.chatNpc("Excellent! Wear that cape with pride my friend.")
            } else {
                it.chatPlayer("But, unfortunately, I don't have enough gold.")
                it.chatNpc("Well, come back and see me when you do.")
            }
        }
    }
}
