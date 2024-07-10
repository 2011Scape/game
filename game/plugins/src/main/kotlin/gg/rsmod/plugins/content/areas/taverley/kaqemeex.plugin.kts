package gg.rsmod.plugins.content.areas.taverley

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.DruidicRitual
import gg.rsmod.plugins.content.quests.startQuest

val druidicRitual = DruidicRitual

on_npc_option(Npcs.KAQEMEEX, option = "talk-to") { // handles the talk to option when clicking on npc
    player.queue {
        chatPlayer("Hello there.")
        when (player.getCurrentStage(druidicRitual)) { // checks current quest stage
            0 -> preQuest(this)
            1 -> duringDruidicRitual(this)
            2 -> duringDruidicRitual(this) // returns the quest stage and sends to a certain
            3 -> finishingDruidicRitual(this) // set of dialogue depending on quest stage
            else -> postQuest(this) // otherwise, sends "postQuest" dialogue
        }
    }
}

suspend fun duringDruidicRitual(it: QueueTask) { // dialogue during druidic ritual.
    it.chatNpc("What brings you to our holy monument?")
    when (it.options("Who are you?", "I'm in search of a quest", "Did you build this?")) {
        1 -> {
            it.chatPlayer("Who are you?")
            whoIsKaqemeex(it)
        }
        2 -> {
            it.chatPlayer("I'm in search of a quest.")
            it.chatNpc("You are already on my quest!")
        }
        3 -> {
            it.chatPlayer("Did you build this?")
            circlesDialogue(it)
        }
    }
}

suspend fun finishingDruidicRitual(it: QueueTask) { // dialogue when about to finish druidic ritual
    it.chatNpc(
        "I have word from Sanfew that you have been very",
        "helpful in assisting him with his preparations for the",
        "purification ritual. As promised I will teach you",
        "the ancient arts of Herblore.",
    )
    herbloreTutorial(it)
    druidicRitual.finishQuest(it.player)
}

suspend fun whoIsKaqemeex(it: QueueTask) { // kaqemeex backstory
    it.chatNpc(
        "We are the druids of Guthix. We worship our god at our",
        "famous stone circles. You will find them located ",
        "throughout these lands.",
    )
}

suspend fun circlesDialogue(it: QueueTask) { // did you build these circles?
    it.chatNpc(
        "What, personally? No, of course I didn't. However, our",
        "forefathers did. The first Druids of Guthix built many",
        "stone circles across these lands over eight hundred",
        "years ago. Unfortunately we only know of two remaining,",
    )
    it.chatNpc("and of those only one is usable by us anymore.")
}

suspend fun questDialogue(it: QueueTask) { // if quest is not started, present this dialogue
    it.chatNpc(
        "Hmm. I think I may have a worthwhile quest for you",
        "actually. I don't know if you are familiar with the stone",
        "circle south of Varrock or not, but...",
    )
    it.chatNpc(
        "That used to be OUR stone circle. Unfortunately, many",
        "many years ago, dark wizards cast a wicked spell upon it",
        "so that they could corrupt its power for their own evil",
        "ends.",
    )
    it.chatNpc(
        "When they cursed the rocks for their rituals they made",
        "them useless to us and our magics. We require a brave",
        "adventurer to go on a quest for us to help purify the",
        "circle of Varrock.",
    )
    when (
        it.options(
            "Ok, I will try and help.",
            "No, that doesn't sound very interesting.",
            "So... is there anything in this for me?",
        )
    ) {
        1 -> {
            it.chatPlayer("Ok, I will try and help.")
            it.chatNpc(
                "Excellent. Go to the village south of this place and speak",
                "to my fellow Sanfew who is working on the purification",
                "ritual. He knows better than I what is required to",
                "complete it.",
            )
            it.chatPlayer("Will do.")
            it.player.startQuest(druidicRitual) // starts the "druidic ritual" quest
        }
        2 -> {
            it.chatPlayer("No, that doesn't sound very interesting.")
            it.chatNpc(
                "I will not try and change your mind adventurer. Some",
                "day when you have matured you may reconsider your",
                "position. We will wait until then.",
            )
        }
        3 -> {
            it.chatPlayer("So... is there anything in this for me?")
            it.chatNpc(
                "We druids value wisdom over wealth, so if you expect",
                "material gain, you will be disappointed. We are,",
                "however, very skilled in the art of Herblore, which we",
                "will share with you if you can assist us with this",
            )
            it.chatNpc(
                "task. You may find such wisdom a greater reward than",
                "mere money.",
            )
        }
    }
}

suspend fun postQuest(it: QueueTask) { // dialogue available AFTER druidic ritual is done.
    when (
        it.options(
            "Who are you?",
            "Can you teach me about Herblore again?",
            "Did you build this?",
            "Can you sell me a skillcape?",
        )
    ) {
        1 -> {
            it.chatPlayer("Who are you?")
            whoIsKaqemeex(it)
        }
        2 -> {
            it.chatPlayer("Can you teach me about Herblore again?")
            herbloreTutorial(it)
        }
        3 -> {
            it.chatPlayer("Did you build this?")
            circlesDialogue(it)
        }
        4 -> {
            // TODO: Add skillcape dialogue.
        }
    }
}

suspend fun preQuest(it: QueueTask) { // dialogue before starting the quest.
    it.chatNpc("What brings you to our holy monument?")
    when (it.options("Who are you?", "I'm in search of a quest", "Did you build this?")) {
        1 -> {
            it.chatPlayer("Who are you?")
            whoIsKaqemeex(it)
        }
        2 -> {
            it.chatPlayer("I'm in search of a quest.")
            questDialogue(it)
        }
        3 -> {
            it.chatPlayer("Did you build this?")
            circlesDialogue(it)
        }
    }
}

suspend fun herbloreTutorial(it: QueueTask) { // the herblore tut he gives you after finishing the quest or asking about it again
    it.chatNpc(
        "I will now explain the fundamentals of Herblore:",
        "Herblore is the skill of working with herbs and other",
        "ingredients to make useful potions and poison.",
    )
    it.chatNpc(
        "First you will need a vial, which can be found or made",
        "with the crafting skill. Then you must gather the herbs",
        "needed to make the potion you want. Refer to the",
        "Council's instructions in the Skills section of the",
    )
    it.chatNpc(
        "website for the items needed to make a particular kind",
        "of potion. You must fill the vial with water and add",
        "the ingredients you need. There are normally 2",
        "ingredients to each type of potion. Bear in mind, you",
    )
    it.chatNpc(
        "must first identify each herb, to see what it is.",
        "You may also have to grind some herbs before you can",
        "use them. You will need a pestle and mortar in order",
        "to do this. Herbs can be found on the ground, and are",
    )
    it.chatNpc(
        "also dropped by some monsters when you kill them.",
        "Let's try an example Attack potion: The first ingredient",
        "is Guam leaf; the next is Eye of Newt. Mix these in",
        "your water-filled vial and you will produce an Attack",
    )
    it.chatNpc(
        "potion. Drink this potion to increase your Attack",
        "level. Different potions also require different",
        "Herblore levels before you can make them. Good luck",
        "with your Herblore practices, Good day Adventurer.",
    )
}
