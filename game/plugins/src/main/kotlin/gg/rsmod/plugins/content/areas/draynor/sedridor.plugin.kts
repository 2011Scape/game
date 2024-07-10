package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.RuneMysteries
import gg.rsmod.util.Misc

on_npc_option(npc = Npcs.SEDRIDOR, option = "talk-to") {
    player.queue {
        when (player.getCurrentStage(RuneMysteries)) {
            1 -> questDialogue(this)
            2 -> packageDialogue(this)
            3 -> helpDialogue(this)
            5 -> finishDialogue(this)
            else -> mainDialogue(this)
        }
    }
}

on_npc_option(npc = Npcs.SEDRIDOR, option = "teleport") {
    if (!player.finishedQuest(RuneMysteries)) {
        player.message("You must've completed Rune Mysteries to teleport to the essence mines.")
        return@on_npc_option
    }
    essenceTeleport(player, targetTile = Tile(2920, 4821))
}

suspend fun mainDialogue(it: QueueTask) {
    it.chatNpc("Welcome adventurer, to the world-renowned", "Wizards' Tower. How may I help you?")
    val thirdOption = if (it.player.finishedQuest(RuneMysteries)) "Can you teleport me?" else ""
    when (it.options("Nothing, thanks. I'm just looking around.", "What are you doing down here?", thirdOption)) {
        1 -> {
            it.chatPlayer("Nothing, thanks. I'm just looking around.")
        }
        2 -> {
            downHereDialogue(it)
        }
        3 -> {
            essenceTeleport(it.player, targetTile = Tile(2920, 4821))
        }
    }
}

suspend fun finishDialogue(it: QueueTask) {
    it.chatNpc("Welcome adventurer, to the world-renowned", "Wizards' Tower. How may I help you?")
    it.chatNpc(
        "Ah, ${Misc.formatForDisplay(it.player.username)}. How goes your quest? Have you",
        "delivered the research notes to my friend yet?",
    )
    it.chatPlayer("Yes, I have. He gave me some research notes", "to pass on to you.")
    it.chatNpc("May I have them?")
    if (it.player.inventory.contains(Items.RESEARCH_NOTES)) {
        it.chatPlayer("Sure, I have them right here")
        it.chatNpc(
            "You have been nothing but helpful, adventurer. In",
            "return, I can let you in on the secret of our research.",
        )
        it.chatNpc(
            "Many centuries ago, the wizards of the Wizards' Tower",
            "learnt the secret of creating runes, which allowed them",
            "to cast magic very easily.",
        )
        it.chatNpc(
            "But, when this tower was burnt down, the secret of",
            "creating runes was lost with it...or so I thought. Some",
            "months ago, while searching these ruins for information,",
        )
        it.chatNpc(
            "I came upon a scroll that made reference to a magical",
            "rock, deep in the ice fields of the north.",
        )
        it.chatNpc(
            "This rock was called the `rune essence` by those",
            "magicians who studied its powers. Apparently, by simply",
            "breaking a chunk from it, a rune could be fashioned",
            "and taken to certain",
        )
        it.chatNpc(
            "magical altars that were scattered across the land. Now,",
            "this is an interesting little piece of history, but not much",
            "use to use since we do not have access to this rune",
            "essence",
        )
        it.chatNpc(
            "or these altars. This is where you and Aubury come in.",
            "A little while ago, Aubury discovered a parchment",
            "detailing a",
        )
        it.chatNpc(
            "teleportation spell that he had never come across before.",
            "When cast, it took him to a strange rock, yet it felt",
            "strangely familiar.",
        )
        it.chatNpc(
            "As I'm sure you have guessed, he had discovered a spell",
            "to the mythical rune essence. As soon as he told me of",
            "this, I saw the importance of the find.",
        )
        it.chatNpc(
            "For, if we could find the altars spoken of in the ancient",
            "texts, we would once more be able to create runes as",
            "our ancestors had done!",
        )
        it.chatPlayer("I'm still not sure how I fit into this little story of yours.")
        it.chatNpc(
            "You haven't guessed? This talisman you brought me is",
            "a key to the elemental altar of air! When",
            "you hold it, it directs you to",
        )
        it.chatNpc(
            "the entrance of the long-forgotten Air Altar. By",
            "bringing pieces of the rune essence to the Air Altar,",
            "you will be able to fashion your own air runes.",
        )
        it.chatNpc(
            "That's not all! By finding other talismans similar to this",
            "one, you will eventually be able to craft every rune that",
            "is available in this world, just",
        )
        it.chatNpc(
            "as our ancestors did. I cannot stress enough what a",
            "find this is! Now, due to the risks involved in letting this",
            "mighty power fall into the wrong hands,",
        )
        it.chatNpc(
            "I will try to keep the teleport spell to the rune essence",
            "a closely guarded secret. This means that, if any evil",
            "power should discover the talismans required to enter",
            "the elemental temples, we should be able to prevent their",
        )
        it.chatNpc(
            "access to the rune essence. I know not where the altars",
            "are located, nor do I know where the talismans have",
            "been scattered, but I now return your air talisman.",
        )
        it.chatNpc("Find the Air Altar and you will be able to craft your", "blank runes into air runes at will.")
        it.chatNpc(
            "Any time you wish to visit the rune essence, speak to",
            "me or Aubury and we will open a portal to that",
            "mystical place.",
        )
        it.chatPlayer("So, only you and Aubury know the teleport spell to the", "rune essence?")
        it.chatNpc(
            "No, there are others. When you speak to them, they",
            "will know you and grant you access to that place when",
            "asked.",
        )
        it.chatNpc(
            "Use the air talisman to locate the Air Altar and use any",
            "further talismans you find to locate the other altars.",
            "Now, my research notes, please?",
        )
        it.itemMessageBox(
            "You give the research notes to Sedridor. He<br><br>gives you an air talisman.",
            item = Items.AIR_TALISMAN,
        )
        RuneMysteries.finishQuest(it.player)
    } else {
        it.chatPlayer("I don't have them...")
    }
}

suspend fun questDialogue(it: QueueTask) {
    it.chatNpc("Welcome adventurer, to the world-renowned", "Wizards' Tower. How may I help you?")
    when (
        it.options(
            "Nothing, thanks. I'm just looking around.",
            "What are you doing down here?",
            "I'm looking for the head wizard.",
        )
    ) {
        1 -> {
            it.chatPlayer("Nothing, thanks. I'm just looking around.")
        }
        2 -> {
            downHereDialogue(it)
        }
        3 -> {
            it.chatPlayer("I'm looking for the head wizard.")
            it.chatNpc("That's me, but why would you be doing that?")
            it.chatPlayer(
                "The Duke of Lumbridge sent me to find him...er, you.",
                "I have a weird talisman that the Duke found. He said",
                "the head wizard would be interested in it.",
            )
            it.chatNpc(
                "Did he now? Well that IS interesting. Hand it over,",
                "then, adventurer - let me see what all the hubbub is",
                "about. Just some crude amulet, I'll wager.",
            )
            if (it.player.inventory.contains(Items.AIR_TALISMAN)) {
                it.chatPlayer("Okay, here you are.")
                it.itemMessageBox("You give the talisman to the wizard.", item = Items.AIR_TALISMAN)
                it.player.inventory.remove(Item(Items.AIR_TALISMAN))
                it.player.advanceToNextStage(RuneMysteries)
                packageDialogue(it)
            } else {
                it.chatPlayer("...except I don't have it with me...")
            }
        }
    }
}

suspend fun downHereDialogue(it: QueueTask) {
    it.chatPlayer("What are you doing down here?")
    it.chatNpc(
        "That is indeed a good question. Here in the cellar",
        "of the Wizards' Tower you find the remains of",
        "the old Wizards' Tower, destroyed by fire,",
    )
    it.chatNpc(
        "many years past by the treachery of the Zamorakians.",
        "Many mysteries were lost, which we try to find once",
        "more. By building this tower on the remains of the old",
    )
    it.chatNpc(
        "we sought to show the world of our dedication to",
        "learning the mysteries of Magic. I am here searching",
        "through these fragements for knowledge from",
        "the artefacts of our past.",
    )
    it.chatPlayer("And have you found anything useful?")
    it.chatNpc(
        "Aaah... that would be telling adventurer. Anything I",
        "have found I cannot speak frealy of, for fear the",
        "treachery of the past might be repeated.",
    )
    it.chatPlayer("Ok, well I'll leave you to it.")
    it.chatNpc("Perhaps I will see you later ${Misc.formatForDisplay(it.player.username)}.")
}

suspend fun packageDialogue(it: QueueTask) {
    it.chatNpc("Wow! This is incredible!")
    it.chatNpc(
        "Th-this talisman you brought me...it is the last piece of",
        "the puzzle. Finally! The legacy of our ancestors will",
        "return to us once more!",
    )
    it.chatNpc(
        "I need time to study this, ${Misc.formatForDisplay(it.player.username)}. Can you please",
        "perform one task while I study this talisman? In the",
        "mighty city of Varrock, located north-east of here, there",
        "is a certain shop that sells magical runes. I have, in this",
    )
    it.chatNpc("package, all of the research I have done relation to", "runes, but I")
    it.chatNpc(
        "require somebody to take them to a shopkeeper who can",
        "offer me his insights. Do this thing for me, bring back",
        "what he gives you.",
    )
    it.chatNpc(
        "and, if my suspicions are correct, I will let you in on",
        "one of the greatest secrets this world has ever known.",
    )
    it.chatNpc("It is a secret so powerful that it destroyed the original", "Wizards' Tower many centuries ago!")
    it.chatNpc("Do this thing for me, ${Misc.formatForDisplay(it.player.username)}, and you will be", "rewarded.")
    when (it.options("Yes, certainly", "No, I'm busy.")) {
        1 -> {
            it.chatPlayer("Yes, certainly.")
            it.chatNpc(
                "Take this package to Varrock, the large city north of",
                "Lumbridge. Aubury's rune store is in the south-east",
                "quarter. He will give you a special item - bring it back",
                "to me and I shall show you the mystery of the runes.",
            )
            if (receivePackage(it)) {
                it.chatNpc("Best of luck with your quest, ${Misc.formatForDisplay(it.player.username)}.")
                it.player.advanceToNextStage(RuneMysteries)
            }
        }
        2 -> {
            it.chatPlayer("No, I'm busy right now.")
            it.chatNpc(
                "As you wish adventurer. I will continue to study this",
                "talisman you have brought me. Return here if you find",
                "yourself with some spare time to help me.",
            )
        }
    }
}

suspend fun helpDialogue(it: QueueTask) {
    it.chatNpc("Hello again, adventurer. Did you take that package to", "Aubury?")
    if (!it.player.hasItem(Items.RESEARCH_PACKAGE)) {
        it.chatPlayer("I lost it. Could I have another?")
        it.chatNpc("Well, it's a good job I have copies of everything.")
        receivePackage(it)
        it.chatNpc("Best of luck with your quest, ${Misc.formatForDisplay(it.player.username)}.")
    } else {
        it.chatPlayer("Not yet.")
        it.chatNpc("He runs a rune shop in the south east of Varrock.", "Please deliver it to him soon.")
    }
}

suspend fun receivePackage(it: QueueTask): Boolean {
    return if (it.player.inventory.hasSpace) {
        it.itemMessageBox("The head wizard gives you a research package.", item = Items.RESEARCH_PACKAGE)
        it.player.inventory.add(Item(Items.RESEARCH_PACKAGE))
        true
    } else {
        it.chatNpc("Please clear up some inventory space", "so I can give you this package.")
        false
    }
}
