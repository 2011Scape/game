package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.RuneMysteries

on_npc_option(npc = Npcs.MAGIC_INSTRUCTOR, option = "talk-to") {
    player.queue {
        this.chatNpc(
            "Hello there adventurer, I am the Magic combat tutor.",
            "Would you like to learn about magic combat, or perhaps",
            "how to make runes?",
        )
        mainChat(this)
    }
}

suspend fun mainChat(it: QueueTask) {
    when (it.options("Tell me about magic combat please.", "How do I make runes?", "Goodbye.")) {
        FIRST_OPTION -> {
            it.chatPlayer("Tell me about magic combat please.")
            it.chatNpc(
                "Of course ${it.player.username}! As a rule of thumb, if you cast the",
                "highest spell of which you're capable, you'll get the best ",
                "experience possible.",
            )
            it.chatNpc(
                "Wearing metal armour and ranged armour can",
                "seriously impair your magical abilities. Make sure you",
                "wear some robes to maximise your capabilities.",
            )
            it.chatNpc(
                "Superheat Item and the Alchemy spells are good ways",
                "to level magic if you are not interested in the combat",
                "aspect of magic.",
            )
            it.chatNpc(
                "There's always the Magic Training Arena. You can",
                "find it north of the Duel Arena in Al Kharid. You will",
                "be able to earn some special rewards there by practicing",
                "your magic there.",
            )
//            if (it.player.hasAncientMagicks) {
//                it.chatNpc("I see you already have access to the ancient magicks.", "Well done, these will aid you greatly.")
//            }
            it.chatNpc("Is there anything else you would like to know?")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        SECOND_OPTION -> {
            it.chatPlayer("How do I make runes?")
            it.chatNpc(
                "There are a couple of things you will need to make",
                "runes, rune essence and a talisman to enter the temple",
                "ruins.",
            )
            if (it.player.skills.getCurrentXp(Skills.RUNECRAFTING) > 0 && it.player.getCurrentStage(RuneMysteries) > 1)
                {
                    it.chatNpc(
                        "To get rune essence you will need to gather them in",
                        "the essence mine. You can get to the mine by talking",
                        "to Aubury who owns the runes shop in south east",
                        "Varrock.",
                    )
                    it.chatNpc(
                        "I see you have some experience already in",
                        "Runecrafting. Perhaps you should try crafting some",
                        "runes which you can then use in magic.",
                    )
                    it.player.focusTab(Tabs.SKILLS)
                    it.chatNpc("Check the skill guide to see which runes you can craft.")
                } else {
                it.chatNpc(
                    "To get rune essence you will need to gather them",
                    "somehow. You should talk to the Duke of Lumbridge, he",
                    "may be able to help you with that. Alternatively, other",
                    "players may sell you the essence.",
                )
                it.chatNpc("As you're fairly new to runecrafting you should start", "with air runes and mind runes.")
            }
            it.chatNpc(
                "You will need a talisman for the rune you would like to",
                "create. You can right-click on it and select the Locate",
                "option. This will tell you the rough location of the altar.",
            )
            it.chatNpc(
                "When you find the ruined altar, use the talisman on it",
                "to be transported to a temple where you can craft your",
                "runes.",
            )
            it.chatNpc(
                "Clicking on the temple's altar will imbue your rune",
                "essence with the altar's magical property.",
            )
            it.chatNpc(
                "If you want to save yourself an inventory space, you",
                "could always try binding the talisman to a tiara.",
            )
            it.chatNpc(
                "To make one, take a tiara and talisman to the ruins",
                "and use the tiara on the temple altar. This will bind the",
                "talisman to your tiara.",
            )
            it.chatNpc("Is there anything else you would like to know?")
            it.terminateAction
            it.player.queue { mainChat(this) }
        }
        THIRD_OPTION -> {
            it.chatPlayer("Goodbye.")
        }
    }
}
