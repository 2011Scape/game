package gg.rsmod.plugins.content.areas.falador

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.DoricsQuest
import gg.rsmod.plugins.content.quests.startQuest

/**
 * @author Alycia <https://github.com/alycii>
 */

val doricsQuest = DoricsQuest

on_npc_option(npc = Npcs.DORIC, option = "talk-to") {
    player.queue {
        when (player.getCurrentStage(doricsQuest)) {
            0 -> preQuestDialogue(this)
            1 -> questDialogue(this)
            else -> mainChat(this)
        }
    }
}

suspend fun mainChat(it: QueueTask) {
    it.chatNpc("Hello traveller, how is your metalworking coming along?")
    it.chatPlayer("Not too bad, Doric.")
    it.chatNpc("Good, the love of metal is a thing close to my heart.")
}

suspend fun questDialogue(it: QueueTask) {
    it.chatNpc("Have you got my materials yet, traveller?")
    if (it.player.inventory.getItemCount(Items.CLAY) >= 6 &&
        it.player.inventory.getItemCount(Items.COPPER_ORE) >= 4 &&
        it.player.inventory.getItemCount(Items.IRON_ORE) >= 2
    ) {
        it.chatPlayer("I have everything you need.")
        it.chatNpc(
            "Many thanks. Pass them here, please. I can spare you",
            "some coins for your trouble, and please use my anvils",
            "any time you want.",
        )
        it.itemMessageBox("You hand the clay, copper, and iron to Doric.", item = Items.COPPER_ORE)
        DoricsQuest.finishQuest(player = it.player)
    } else {
        it.chatPlayer("Sorry, I don't have them all yet.")
        it.chatNpc("Not to worry, stick at it. Remember, I need 6 clay, 4", "copper ore, and 2 iron ore.")
        helpDialogue(it)
    }
}

suspend fun preQuestDialogue(it: QueueTask) {
    it.chatNpc("Hello traveller, what brings you to my humble smithy?")
    when (
        it.options(
            "I wanted to use your anvils.",
            "I want to use your whetstone.",
            "Mind your own business, shortstuff!",
            "I was just checking out the landscape.",
            "What do you make here?",
        )
    ) {
        1 -> {
            it.chatPlayer("I wanted to use your anvils.")
            it.chatNpc(
                "My anvils get enough work with my own use. I make",
                "pickaxes, and it takes a lot of hard work. If you could",
                "get me some more materials, then I could let you use",
                "them.",
            )
            startQuest(it)
        }
        2 -> {
            it.chatPlayer("I wanted to use your whetstone.")
            it.chatNpc(
                "The whetstone is for more advanced smithing, but I",
                "could let you use it as well as my anvils if you could",
                "get me some more materials.",
            )
            startQuest(it)
        }
        3 -> {
            it.chatPlayer("Mind your own business, shortstuff!")
            it.chatNpc(
                "How nice to meet someone with such pleasent manners.",
                "Do come again when you need to shout at someone",
                "smaller than you!",
            )
        }
        4 -> {
            it.chatPlayer("I was just checking out the landscape.")
            it.chatNpc(
                "Hope you like it. I do enjoy the solitude of my little",
                "home. If you get the time, please say hi to my friends in",
                "the Dwarven Mine.",
            )
            when (it.options("Dwarven Mine?", "Will do!")) {
                1 -> {
                    it.chatPlayer("Dwarven Mine?")
                    it.chatNpc(
                        "Yep, the entrance is in the side of Ice Mountain just to",
                        "the east of here. They're a friendly bunch. Stop in at",
                        "Nurmof's store and buy one of my pickaxes!",
                    )
                }
                2 -> {
                    it.chatPlayer("Will do!")
                }
            }
        }
        5 -> {
            it.chatPlayer("What do you make here?")
            it.chatNpc("I make pickaxes. I am the best maker of pickaxes in the", "whole of RuneScape.")
            it.chatPlayer("Do you have any to sell?")
            it.chatNpc("Sorry, but I've got a running order with Nurmof.")
            when (it.options("Who's Nurmof?", "Ah, fair enough.")) {
                1 -> {
                    it.chatPlayer("Who's Nurmof?")
                    it.chatNpc(
                        "Nurmof has a store over in the Dwarven Mine. You",
                        "can find the entrance on the side of Ice Mountain to",
                        "the east of here.",
                    )
                }
                2 -> {
                    it.chatPlayer("Ah, fair enough.")
                }
            }
        }
    }
}

suspend fun helpDialogue(it: QueueTask) {
    when (it.options("Where can I find those?", "Certainly, I'll be right back!")) {
        1 -> {
            it.chatPlayer("Where can I find those?")
            it.chatNpc(
                "You'll be able to find all those ores in the rocks just",
                "inside the Dwarven Mine. Head east from here and",
                "you'll find the entrance in the side of Ice Mountain.",
            )
            if (it.player.skills.getMaxLevel(Skills.MINING) < 15) {
                it.chatPlayer("But I'm not a good enough miner to get iron ore.")
                it.chatNpc(
                    "Oh well, you could practice mining until you can. Can't",
                    "beat a bit of mining - it's a useful skill. Failing that, you",
                    "might be able to find a more experienced adventurer to",
                    "buy the iron ore off.",
                )
            } else {
                it.chatPlayer("Okay, thanks! I'll be right back.")
            }
        }
        2 -> {
            it.chatPlayer("Certainly, I'll be right back!")
        }
    }
}

suspend fun startQuest(it: QueueTask) {
    if (it.player.skills.getMaxLevel(Skills.MINING) < 15) {
        it.messageBox(
            "Before starting this quest, be aware that one or more of your skill<br><br>levels are lower than recommended.",
        )
    }
    when (it.options("Yes.", "No.", title = "Start Doric's Quest?")) {
        1 -> {
            it.chatPlayer("Yes, I will get you the materials.")
            if (it.player.inventory.hasSpace) {
                it.player.inventory.add(Items.BRONZE_PICKAXE)
                it.player.startQuest(doricsQuest)
                it.chatNpc(
                    "Clay is what I use more than anything, to make casts.",
                    "Could you get me 6 clay, 4 copper ore, and 2 iron ore,",
                    "please? I could pay a little, and let you use my anvils.",
                    "Take this pickaxe with you just in case you need it.",
                )
                helpDialogue(it)
            } else {
                it.chatNpc("You'll need some inventory space before we can continue.")
            }
        }
        2 -> {
            it.chatPlayer("No, hitting rocks is for the boring people, sorry.")
            it.chatNpc("That is your choice. Nice to meet you anyway.")
        }
    }
}
