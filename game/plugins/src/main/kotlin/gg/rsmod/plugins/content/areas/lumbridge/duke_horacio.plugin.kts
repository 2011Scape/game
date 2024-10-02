package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.RuneMysteries
import gg.rsmod.plugins.content.quests.startQuest

val runeMysteries = RuneMysteries

on_npc_option(Npcs.DUKE_HORACIO, option = "talk-to") {
    player.queue {
        chatNpc("Greetings. Welcome to my castle.")
        if (player.completedAllQuests()) {
            dragonSlayerQuest(this)
        } else {
            when (player.getCurrentStage(runeMysteries)) {
                0 -> preQuest(this)
                1 -> duringRuneMysteries(this)
                else -> postQuest(this)
            }
        }
    }
}

suspend fun duringRuneMysteries(it: QueueTask) {
    when (it.options("What did you want me to do again?", "Where can I find money?")) {
        1 -> {
            it.chatPlayer("What did you want me to do again?")
            if (!it.player.hasItem(Items.AIR_TALISMAN)) {
                it.chatNpc("Did you take that talisman to Sedridor?")
                it.chatPlayer("No, I lost it.")
                it.chatNpc(
                    "Ah, well that explains things. One of my servants found",
                    "it outside, and it seemed too much of a coincidence that",
                    "another would suddenly show up.",
                )
                it.chatNpc(
                    "Here, take it to the Wizards' Tower, south west of here.",
                    "Please try not to lose it this time.",
                )
                receiveTalisman(it)
            } else {
                it.chatNpc(
                    "Take that talisman I gave you to Sedridor at the",
                    "Wizards' Tower. You'll find it south west of here,",
                    "across the bridge from Draynor Village.",
                )
                it.chatPlayer("Okay, will do.")
            }
        }
        2 -> {
            moneyChat(it)
        }
    }
}

suspend fun dragonSlayerQuest(it: QueueTask) {
    when (
        it.options(
            "I seek a shield that will protect me from dragonbreath.",
            "Have you any quests for me?",
            "Where can I find money?",
        )
    ) {
        1 -> {
            it.chatPlayer("I seek a shield that will protect me from dragonbreath.")
            it.chatNpc("A knight going on a dragon quest, hmm? What dragon", "do you intend to slay?")
            it.chatPlayer("Oh, no dragon in particular. I just feel like killing a", "dragon.")
            it.chatNpc(
                "Of course. As someone who has completed all of the",
                "available quests, you've earned the right",
                "to call the shield your own!",
            )
            if (it.player.hasItem(Items.ANTIDRAGON_SHIELD)) {
                it.chatNpc("You've already got a shield.")
            } else {
                it.player.inventory.add(Items.ANTIDRAGON_SHIELD)
                it.itemMessageBox("The Duke hands you the shield.", item = Items.ANTIDRAGON_SHIELD)
            }
        }
        2 -> {
            it.chatPlayer("Have you any quests for me?")
            it.chatNpc("The only job I had was the delivery of that talisman, so", "I'm afraid not.")
        }
        3 -> {
            moneyChat(it)
        }
    }
}

suspend fun postQuest(it: QueueTask) {
    val firstOption =
        when {
            it.player.completedAllQuests() -> "I seek a shield that will protect me from dragonbreath."
            else -> ""
        }

    when (it.options("Have you any quests for me?", "Where can I find money?")) {
        1 -> {
            it.chatPlayer("Have you any quests for me?")
            it.chatNpc("The only job I had was the delivery of that talisman, so", "I'm afraid not.")
        }
        2 -> {
            moneyChat(it)
        }
    }
}

suspend fun preQuest(it: QueueTask) {
    when (it.options("Have you any quests for me?", "Where can I find money?")) {
        1 -> {
            it.chatPlayer("Have you any quests for me?")
            it.chatNpc("Well, I wouldn't describe it as a quest, but there is", "something I could use some help with.")
            it.chatPlayer("What is it?")
            it.chatNpc(
                "We were recently sorting through some of the things",
                "stored down in the cellar, and we found this old",
                "talisman.",
            )
            it.itemMessageBox("The Duke shows you a talisman.", item = Items.AIR_TALISMAN)
            it.chatNpc(
                "The Order of Wizards over at the Wizard's Tower",
                "have been on the hunt for magical artefacts recently. I",
                "wonder if this might be just the kind of thing they're",
                "after.",
            )
            it.chatNpc("Would you be willing to take it to them for me?")
            when (it.options("Yes", "No")) {
                1 -> {
                    it.chatPlayer("Sure, no problem.")
                    it.chatNpc(
                        "Thank you very much. You'll find the Wizards' Tower",
                        "south west of here, across the bridge from Draynor",
                        "Village. When you arrive, look for Sedridor. He is the",
                        "Archmage of the wizards there.",
                    )
                    if (receiveTalisman(it)) {
                        it.player.startQuest(runeMysteries)
                    }
                }
                2 -> {
                    it.chatPlayer("Not right now.")
                    it.chatNpc("As you wish. Hopefully I can find someone else to help.")
                }
            }
        }
        2 -> {
            moneyChat(it)
        }
    }
}

suspend fun receiveTalisman(it: QueueTask): Boolean {
    return if (it.player.inventory.hasSpace) {
        it.itemMessageBox("The Duke hands you the talisman.", item = Items.AIR_TALISMAN)
        it.player.inventory.add(Item(Items.AIR_TALISMAN))
        true
    } else {
        it.chatNpc("Please clear up some inventory space", "so I can give you this talisman.")
        false
    }
}

suspend fun moneyChat(it: QueueTask) {
    it.chatPlayer("Where can I find money?")
    it.chatNpc(
        "I've heard that the blacksmiths are prosperous amongst",
        "the peasantry. Maybe you could try your hand at",
        "that?",
    )
}
