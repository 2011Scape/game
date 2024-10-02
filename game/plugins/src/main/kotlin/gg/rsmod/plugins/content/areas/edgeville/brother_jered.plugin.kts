package gg.rsmod.plugins.content.areas.edgeville

import gg.rsmod.plugins.content.skills.Skillcapes

on_item_on_npc(1716, Npcs.BROTHER_JERED) {
    player.queue {
        player.inventory.remove(item = Item(Items.UNBLESSED_SYMBOL, amount = 1))
        messageBox("Jered closes his eyes and softly chants. The symbol is imbued with his blessing.")
        player.inventory.add(item = Item(Items.HOLY_SYMBOL, amount = 1))
    }
}

on_npc_option(npc = Npcs.BROTHER_JERED, option = "talk-to") {
    player.queue {
        if (player.skills.getCurrentLevel(Skills.PRAYER) >= 99) {
            mainChatWith99(this)
        } else {
            mainChat(this)
        }
    }
}

suspend fun mainChat(it: QueueTask) {
    when (
        it.options(
            "What can you do to help a bold adventurer like myself?",
            "Praise be to Saradomin.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("What can you do to help a bold adventurer like myself?")
            it.chatNpc(
                "I can tell you about holy symbols.",
            )
            it.chatPlayer("Tell me about holy symbols.")
            it.chatNpc(
                "If you have a silver star, which is the holy",
                "symbol of Saradomin, then I can bless it.",
                "Then if you are wearing it, it will help",
                "you when you are praying.",
            )
        }
        SECOND_OPTION -> {
            it.chatPlayer("Praise be to Saradomin.")
            it.chatNpc("Yes! Praise he who brings life to this world.")
        }
    }
}

suspend fun mainChatWith99(it: QueueTask) {
    when (
        it.options(
            "What can you do to help a bold adventurer like myself?",
            "Can you tell me about holy symbols?",
            "Praise be to Saradomin.",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("What can you do to help a bold adventurer like myself?")
            it.chatNpc(
                "Well, seeing as you are so devout in praising the gods",
                "I could sell you a Skillcape of Prayer.",
            )
            when (
                it.options(
                    "Yes please. So few people have Skillcapes of Prayer",
                    "No, thanks. I can't afford one of those.",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("Yes please. So few people have Skillcapes of Prayer")
                    it.chatNpc(
                        "One as pious as you has certainly earned the right to",
                        "wear one, but the monastery requires a donation of",
                        "99,000 coins for the privilege.",
                    )
                    when (
                        it.options(
                            "I'm afraid I can't afford that.",
                            "I am always happy to contribute towards the monastery's upkeep.",
                        )
                    ) {
                        FIRST_OPTION -> {
                            it.chatPlayer("I'm afraid I can't afford that.")
                            it.chatNpc(
                                "I am sorry to hear that. If you should find yourself",
                                "in wealthier times come back and see me.",
                            )
                        }
                        SECOND_OPTION -> {
                            it.chatPlayer(
                                "I am always happy to contribute",
                                "towards the monastery's upkeep.",
                            )
                            if (it.player.inventory.freeSlotCount < 2) {
                                it.chatNpc(
                                    "You don't have enough free space in your inventory ",
                                    "for me to sell you a Skillcape of Prayer.",
                                )
                                it.chatNpc("Come back to me when you've cleared up some space.")
                                return
                            }
                            if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                                Skills.purchaseSkillcape(it.player, data = Skillcapes.PRAYER)
                                it.chatNpc("Excellent! Wear that cape with pride my friend.")
                            } else {
                                it.chatPlayer("But, unfortunately, I don't have enough gold.")
                                it.chatNpc("Well, come back and see me when you do.")
                            }
                        }
                    }
                }
                SECOND_OPTION -> {
                    it.chatPlayer("No, thanks. I can't afford one of those..")
                    it.chatNpc(
                        "I am sorry to hear that. If you should find yourself",
                        "in wealthier times come back and see me.",
                    )
                }
            }
        }
        SECOND_OPTION -> {
            it.chatPlayer("Tell me about holy symbols.")
            it.chatNpc(
                "If you have a silver star, which is the holy",
                "symbol of Saradomin, then I can bless it.",
                "Then if you are wearing it, it will help",
                "you when you are praying.",
            )
        }
        THIRD_OPTION -> {
            it.chatPlayer("Praise be to Saradomin.")
            it.chatNpc("Yes! Praise he who brings life to this world.")
        }
    }
}
