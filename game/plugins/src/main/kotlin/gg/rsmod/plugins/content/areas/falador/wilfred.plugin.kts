package gg.rsmod.plugins.content.areas.falador

import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.WILFRED, option = "talk-to") {
    player.queue {
        if (player.skills.getCurrentLevel(Skills.WOODCUTTING) >= 99) {
            mainChatWith99(this)
        } else {
            mainChat(player)
        }
    }
}

fun mainChat(player: Player) {
    player.message("Nothing interesting happens.")
}

suspend fun mainChatWith99(it: QueueTask) {
    it.chatNpc(
        "Wow! It's not often I meet somebody as accomplished",
        "as you in Woodcutting! Seeing as you're so skilled,",
        "maybe you are interested in buying a Skillcape of",
        "Woodcutting?",
    )
    when (
        it.options(
            "No, thanks",
            "Yes, please.",
        )
    ) {
        FIRST_OPTION -> {
            // nothing, close chat.
        }
        SECOND_OPTION -> {
            it.chatNpc(
                "Anybody who as spent as much time cutting trees as",
                "you deserves the right to own one. That'll be 99000",
                "coins, please.",
            )
            when (
                it.options(
                    "99000! That's too rich for me.",
                    "No problem.",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatNpc(
                        "Not at all; there are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.",
                    )
                }
                SECOND_OPTION -> {
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Woodcutting.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.WOODCUTTING)
                        it.chatNpc("Excellent! Wear that cape with pride my friend.")
                    } else {
                        it.chatPlayer("Unfortunately, I don't have enough gold.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
            }
        }
    }
}
