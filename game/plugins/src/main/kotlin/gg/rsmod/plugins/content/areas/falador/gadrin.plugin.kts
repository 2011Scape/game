package gg.rsmod.plugins.content.areas.falador
import gg.rsmod.plugins.content.skills.Skillcapes

on_npc_option(npc = Npcs.DWARF_3295, option = "talk-to") {
    player.queue {
        if (player.skills.getCurrentLevel(Skills.MINING) >= 99) {
            mainChatWith99(this)
        } else {
            mainChat(this)
        }
    }
}

suspend fun mainChat(it: QueueTask) {
    it.chatNpc(
        "Welcome to the Mining Guild.",
        "Can I help you with anything?",
    )
    when (
        it.options(
            "What have you got in the guild?",
            "What do you dwarves do with the ore you mine?",
            "Can you tell me about your skillcape?",
            "No thanks, I'm fine.",
            title = "Select an Option",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("What have you got in the guild?")
            it.chatNpc(
                "All sorts of things! There's plenty of coal rocks along with some iron, mithril and " +
                    "adamantite as well.",
                wrap = true,
            )
            it.chatNpc("There's no better mining site anywhere!")
        }
        SECOND_OPTION -> {
            it.chatPlayer("What do you dwarves do with the ore you mine?")
            it.chatNpc(
                "What do you think? We smelt it into bars, smith the metal to make armour and weapons, then we exchange " +
                    "them for goods and services.",
                wrap = true,
            )
            it.chatPlayer("I don't see many dwarves selling armour or weapons here.")
            it.chatNpc(
                "No, this is only a mining outpost. We dwarves don't much like to settle in human cities. Most of the " +
                    "ore is carted off to Keldagrim, the great dwarven city. They've got a special blast furnace up there - it makes",
                wrap = true,
            )
            it.chatNpc(
                "smelting the ore so much easier. There are plenty of dwarven traders working in Keldagrim. Anyway, can " +
                    "I help you with anything else?",
                wrap = true,
            )
            when (
                it.options(
                    "What have you got in the guild?",
                    "No thanks, I'm fine.",
                    title = "Select an Option",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("What have you got in the guild?")
                    it.chatNpc(
                        "All sorts of things! There's plenty of coal rocks along with some iron," +
                            " mithril and adamantite as well.",
                        wrap = true,
                    )
                    it.chatNpc("There's no better mining site anywhere!")
                }

                SECOND_OPTION -> {
                    it.chatPlayer("No thanks, I'm fine.")
                    return
                }
            }
        }

        THIRD_OPTION -> {
            it.chatPlayer("Can you tell me about your skillcape?")
            it.chatNpc(
                "Sure, this is a Skillcape of Mining. It shows my stature as a master miner! It has all" +
                    " sorts of uses including a skill boost to my Mining skill and a chance of mining extra ores.",
                wrap = true,
            )
            it.chatNpc("When you get to level 99 come and talk to me and I'll sell you one.", wrap = true)
            it.chatNpc("Is there anything else I can help you with?")
            when (
                it.options(
                    "What have you got in the guild?",
                    "What do you dwarves do with the ore you mine?",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("What have you got in the guild?")
                    it.chatNpc(
                        "All sorts of things! There's plenty of coal rocks along with some iron, mithril and " +
                            "adamantite as well.",
                        wrap = true,
                    )
                    it.chatNpc("There's no better mining site anywhere!")
                }
                SECOND_OPTION -> {
                    it.chatPlayer("What do you dwarves do with the ore you mine?")
                    it.chatNpc(
                        "What do you think? We smelt it into bars, smith the metal to make armour and weapons, then we exchange " +
                            "them for goods and services.",
                        wrap = true,
                    )
                    it.chatPlayer("I don't see many dwarves selling armour or weapons here.")
                    it.chatNpc(
                        "No, this is only a mining outpost. We dwarves don't much like to settle in human cities. Most of the " +
                            "ore is carted off to Keldagrim, the great dwarven city. They've got a special blast furnace up there - it makes",
                        wrap = true,
                    )
                    it.chatNpc(
                        "smelting the ore so much easier. There are plenty of dwarven traders working in Keldagrim. Anyway, can " +
                            "I help you with anything else?",
                        wrap = true,
                    )
                    when (
                        it.options(
                            "What have you got in the guild?",
                            "No thanks, I'm fine.",
                            title = "Select an Option",
                        )
                    ) {
                        FIRST_OPTION -> {
                            it.chatPlayer("What have you got in the guild?")
                            it.chatNpc(
                                "All sorts of things! There's plenty of coal rocks along with some iron," +
                                    " mithril and adamantite as well.",
                                wrap = true,
                            )
                            it.chatNpc("There's no better mining site anywhere!")
                        }

                        SECOND_OPTION -> {
                            it.chatPlayer("No thanks, I'm fine.")
                            return
                        }
                    }
                }
            }
        }
    }
}

suspend fun mainChatWith99(it: QueueTask) {
    it.chatNpc(
        "Welcome to the Mining Guild.",
        "Can I help you with anything?",
    )
    when (
        it.options(
            "What have you got in the guild?",
            "What do you dwarves do with the ore you mine?",
            "Can I buy a Skillcape of Mining from you?",
            "No thanks, I'm fine.",
            title = "Select an Option",
        )
    ) {
        FIRST_OPTION -> {
            it.chatPlayer("What have you got in the guild?")
            it.chatNpc(
                "All sorts of things! There's plenty of coal rocks along with some iron, mithril and " +
                    "adamantite as well.",
                wrap = true,
            )
            it.chatNpc("There's no better mining site anywhere!")
        }
        SECOND_OPTION -> {
            it.chatPlayer("What do you dwarves do with the ore you mine?")
            it.chatNpc(
                "What do you think? We smelt it into bars, smith the metal to make armour and weapons, then we exchange " +
                    "them for goods and services.",
                wrap = true,
            )
            it.chatPlayer("I don't see many dwarves selling armour or weapons here.")
            it.chatNpc(
                "No, this is only a mining outpost. We dwarves don't much like to settle in human cities. Most of the " +
                    "ore is carted off to Keldagrim, the great dwarven city. They've got a special blast furnace up there - it makes",
                wrap = true,
            )
            it.chatNpc(
                "smelting the ore so much easier. There are plenty of dwarven traders working in Keldagrim. Anyway, can " +
                    "I help you with anything else?",
                wrap = true,
            )
            when (
                it.options(
                    "What have you got in the guild?",
                    "No thanks, I'm fine.",
                    title = "Select an Option",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("What have you got in the guild?")
                    it.chatNpc(
                        "All sorts of things! There's plenty of coal rocks along with some iron, mithril " +
                            "and adamantite as well.",
                        wrap = true,
                    )
                    it.chatNpc("There's no better mining site anywhere!")
                }

                SECOND_OPTION -> {
                    it.chatPlayer("No thanks, I'm fine.")
                    return
                }
            }
        }

        THIRD_OPTION -> {
            it.chatPlayer(
                "I believe I can buy a Skillcape of Mining from you?",
                facialExpression = FacialExpression.UNCERTAIN,
            )
            it.chatNpc(
                "You believe right, miner. You have earned the right to",
                "wear one. but I'll need 99000 coins from you first.",
            )
            when (
                it.options(
                    "Sorry, that's overpriced.",
                    "Okay then.",
                )
            ) {
                FIRST_OPTION -> {
                    it.chatPlayer("Sorry, that's overpriced.")
                    it.chatNpc(
                        "There are many other adventurers who",
                        "would love the opportunity to purchase such a",
                        "prestigious item! You can find me here if you change",
                        "your mind.",
                    )
                }
                SECOND_OPTION -> {
                    it.chatPlayer("Okay then.")
                    if (it.player.inventory.freeSlotCount < 2) {
                        it.chatNpc(
                            "You don't have enough free space in your inventory ",
                            "for me to sell you a Skillcape of Mining.",
                        )
                        it.chatNpc("Come back to me when you've cleared up some space.")
                        return
                    }
                    if (it.player.inventory.getItemCount(Items.COINS_995) >= 99000) {
                        Skills.purchaseSkillcape(it.player, data = Skillcapes.MINING)
                        it.chatNpc("Thanks very much, master miner.")
                    } else {
                        it.chatPlayer("But, unfortunately, I don't have enough gold.")
                        it.chatNpc("Well, come back and see me when you do.")
                    }
                }
            }
        }
    }
}
