package gg.rsmod.plugins.content.areas.varrock
import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.HEAD_CHEF, option = "talk-to") {
    player.queue {
        if (player.skills.getCurrentLevel(Skills.COOKING) >= 99) {
            mainChatWith99(this, player)
        } else {
            mainChat(this, player)
        }
    }
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "Hello, welcome to the Cooking Guild. It's always great to",
        "have such an accomplished chef visit us.",
    )
}

suspend fun mainChatWith99(
    it: QueueTask,
    player: Player,
) {
    it.chatNpc(
        "Hello, welcome to the Cooking Guild. It's always great to",
        "have such an accomplished chef visit us. Say, would you",
        "be interested in a Skillcape of Cooking? They're only",
        "available to master chefs.",
    )
    when (
        it.options(
            "No thanks.",
            "Yes please.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("No thanks.")
        }
        SECOND_OPTION -> {
            it.chatPlayer("Can I buy a Skillcape of Cooking from you?")
            it.chatNpc(
                "Most certainly, just as soon as you give me the 99000",
                "gold coins.",
            )
            when (
                it.options(
                    "That's much too expensive.",
                    "Sure.",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("That's much too expensive.")
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
                            "for me to sell you a Skillcape of Cooking.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.COOKING)
                        it.chatNpc("Now you can use the title Master Chef.")
                    } else {
                        it.chatPlayer("But, unfortunately, I don't have enough gold.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
            }
        }
    }
}
