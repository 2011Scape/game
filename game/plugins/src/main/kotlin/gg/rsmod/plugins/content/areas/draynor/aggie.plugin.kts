package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer
import gg.rsmod.plugins.content.quests.startedQuest

on_npc_option(Npcs.AGGIE, "Talk-to") {
    player.queue {
        chatNpc("What can I help you with?")
        mainOptions(this)
    }
}

on_npc_option(Npcs.AGGIE, "Make-dyes") {
    player.queue {
        makeDyesOptions(this)
    }
}

suspend fun makeDyesOptions(it: QueueTask) {
    when (it.options(
        "Red dye (requires 5 coins and 3 lots of redberries).",
        "Blue dye (requires 5 coins and 2 woad leaves).",
        "Yellow dye (requires 5 coins and 2 onions)."
    )) {
        FIRST_OPTION -> makeRedDye(it)
        SECOND_OPTION -> makeBlueDye(it)
        THIRD_OPTION -> makeYellowDye(it)
    }
}

suspend fun mainOptions(it: QueueTask) {
    if (it.player.startedQuest(VampyreSlayer) || it.player.finishedQuest(VampyreSlayer)) {
        when (it.options(
            "Hey, you're a witch aren't you?",
            "So what is actually in that cauldron?",
            "Talk about Vampyre Slayer",
            "What's new in Draynor Village?",
            "More..."
        )) {
            FIRST_OPTION -> youreAWitchDialogue(it)
            SECOND_OPTION -> whatsInCauldronDialogue(it)
            THIRD_OPTION -> vampyreSlayerDialogue(it)
            FOURTH_OPTION -> whatsNewDialogue(it)
            FIFTH_OPTION -> moreOptions(it)
        }
    }
    else {
        when (it.options(
            "Hey, you're a witch aren't you?",
            "So what is actually in that cauldron?",
            "What's new in Draynor Village?",
            "More..."
        )) {
            FIRST_OPTION -> youreAWitchDialogue(it)
            SECOND_OPTION -> whatsInCauldronDialogue(it)
            THIRD_OPTION -> whatsNewDialogue(it)
            FOURTH_OPTION -> moreOptions(it)
        }
    }
}

suspend fun youreAWitchDialogue(it: QueueTask) {
    it.chatPlayer("Hey, you're a witch aren't you?", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc("My, you're observant!", facialExpression = FacialExpression.LAUGHING)
    it.chatPlayer("Cool, do you turn people into frogs?", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc("Oh, not for years. But if you meet a talking chicken, you have probably met the professor in the " +
        "manor north of here. A few years ago it was a flying fish. That machine is a menace.", wrap = true)
}

suspend fun whatsInCauldronDialogue(it: QueueTask) {
    it.chatPlayer("So what is actually in that cauldron?", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc("You don't really expect me to give away trade secrets, do you?", facialExpression = FacialExpression
        .CONFUSED, wrap = true)
}

suspend fun vampyreSlayerDialogue(it: QueueTask) {
    if (it.player.finishedQuest(VampyreSlayer)) {
        it.chatNpc("I must congratulate you on killing Count Draynor! You've done a great thing for our village.",
            wrap = true)
        it.chatPlayer("I'm happy to help")
    }
    else {
        it.chatPlayer("Can you tell me anything about vampyres? I told Morgan I would slay the one in Draynor Manor",
            facialExpression = FacialExpression.CONFUSED, wrap = true)
        it.chatNpc("I know they can only be killed with special equipment, but", "I don't know what or where you " +
            "could get it. I'm glad you", "are attempting to end Count Draynor's reign of terror,", "though.")
        it.chatPlayer("I'll try my best!")
    }
}

suspend fun whatsNewDialogue(it: QueueTask) {
    if (it.player.startedQuest(VampyreSlayer)) {
        it.chatNpc("Nothing, now that you've gotten rid of that blood sucker. You've done this village a favor.",
            wrap = true)
        it.chatPlayer("I do my best!")
    }
    else {
        it.chatNpc("The blood sucker has returned to Draynor Manor. I hate his kind.", facialExpression =
            FacialExpression.DISBELIEF, wrap = true)
        it.chatPlayer("The blood sucker?", facialExpression = FacialExpression.CONFUSED)
        it.chatNpc("Yes, Count Draynor, the vampyre. He's preying on the innocent people of this village. I do my " +
            "best to protect them with what magic I have, but he is powerful.", facialExpression = FacialExpression
                .MEAN_FACE, wrap = true)
        it.chatPlayer("Wow, that's sad.")
    }
}

suspend fun moreOptions(it: QueueTask) {
    when (it.options(
        "What could you make for me?",
        "Can you make dyes for me please?",
        "More..."
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("What could you make for me?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("I mostly just make what I find pretty. I sometimes make dye for the women's clothes to " +
                "brighten the place up. I can make red, yellow and blue dyes. If you'd like some, just bring me the " +
                "appropriate ingredients.", wrap = true)
            when (it.options(
                "What do you need to make red dye?",
                "What do you need to make yellow dye?",
                "What do you need to make blue dye?",
                "No thanks, I am happy the colour I am."
            )) {
                FIRST_OPTION -> askAboutRed(it)
                SECOND_OPTION -> askAboutYellow(it)
                THIRD_OPTION -> askAboutBlue(it)
                FOURTH_OPTION -> {
                    it.chatPlayer("No thanks, I am happy the colour I am.")
                    it.chatNpc("You are easily pleased with yourself then. When you need dyes, come to me.", wrap =
                        true)
                }
            }
        }
        SECOND_OPTION -> {
            it.chatPlayer("Can you make dyes for me please?", facialExpression = FacialExpression.CONFUSED)
            otherColors(it, true)
        }
        THIRD_OPTION -> mainOptions(it)
    }
}

suspend fun otherColors(it: QueueTask, altOption: Boolean = false) {
    if (altOption) {
        it.chatNpc("What sort of dye would you like? Red, yellow or blue?")
    }
    else {
        it.chatPlayer("What other colours can you make?", facialExpression = FacialExpression.CONFUSED,
            wrap = true)
        it.chatNpc("Red, yellow or blue. Which one would you like?")
    }
    when (it.options(
        "What do you need to make red dye?",
        "What do you need to make yellow dye?",
        "What do you need to make blue dye?"
    )) {
        FIRST_OPTION -> askAboutRed(it)
        SECOND_OPTION -> askAboutYellow(it)
        THIRD_OPTION -> askAboutBlue(it)
    }
}

suspend fun otherAndThanks(it: QueueTask) {
    when (it.options(
        "What other colours can you make?",
        "Thanks."
    )) {
        FIRST_OPTION -> otherColors(it)
        SECOND_OPTION -> {
            it.chatPlayer("Thanks.")
            it.chatNpc("You're welcome!")
        }
    }
}

suspend fun askAboutRed(it: QueueTask) {
    it.chatPlayer("What do you need to make red dye?", facialExpression = FacialExpression.CONFUSED, wrap = true)
    it.chatNpc("3 lots of redberries and 5 coins to you.")
    when (it.options(
        "Could you make me some red dye, please?",
        "I don't think I have all the ingredients yet.",
        "I can do without dye at that price.",
        "Where can I get redberries?",
        "What other colours can you make?"
    )) {
        FIRST_OPTION -> makeRedDye(it)
        SECOND_OPTION -> notAllIngredients(it)
        THIRD_OPTION -> doWithout(it)
        FOURTH_OPTION -> {
            it.chatPlayer("Where can I get redberries?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("I pick mine from the woods south of Varrock. The food shop in Port Sarim sometimes has some " +
                "as well.", wrap = true)
            otherAndThanks(it)
        }
        FIFTH_OPTION -> otherColors(it)
    }
}

suspend fun askAboutYellow(it: QueueTask) {
    it.chatPlayer("What do you need to make yellow dye?", facialExpression = FacialExpression.CONFUSED, wrap = true)
    it.chatNpc("Yellow is a strange colour to get, comes from onion skins. I need 2 onions and 5 coins to make yellow" +
        " dye.", wrap = true)
    when (it.options(
        "Could you make me some yellow dye, please?",
        "I don't think I have all the ingredients yet.",
        "I can do without dye at that price.",
        "Where can I get onions?",
        "What other colours can you make?"
    )) {
        FIRST_OPTION -> makeYellowDye(it)
        SECOND_OPTION -> notAllIngredients(it)
        THIRD_OPTION -> doWithout(it)
        FOURTH_OPTION -> {
            it.chatPlayer("Where can I get onions?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("There are some onions growing on a farm to the East of here, next to the sheep field.", wrap = true)
            otherAndThanks(it)
        }
        FIFTH_OPTION -> otherColors(it)
    }
}

suspend fun askAboutBlue(it: QueueTask) {
    it.chatPlayer("What do you need to make blue dye?", facialExpression = FacialExpression.CONFUSED, wrap = true)
    it.chatNpc("2 Woad leaves and 5 coins to you.")
    when (it.options(
        "Could you make me some blue dye, please?",
        "I don't think I have all the ingredients yet.",
        "I can do without dye at that price.",
        "Where can I get woad leaves?",
        "What other colours can you make?"
    )) {
        FIRST_OPTION -> makeBlueDye(it)
        SECOND_OPTION -> notAllIngredients(it)
        THIRD_OPTION -> doWithout(it)
        FOURTH_OPTION -> {
            it.chatPlayer("Where can I get woad leaves?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("Woad leaves are fairly hard to find. My other customers tell me the chief gardener in Falador" +
                " grows them.", wrap = true)
            otherAndThanks(it)
        }
        FIFTH_OPTION -> otherColors(it)
    }
}

suspend fun makeRedDye(it: QueueTask) {
    if (it.player.inventory.getItemCount(Items.REDBERRIES) < 3) {
        it.messageBox("You don't have enough berries to make the red dye.")
    }
    else if (it.player.inventory.getItemCount(Items.COINS_995) < 5) {
        it.messageBox("You don't have enough coins to pay for the dye.")
    }
    else {
        it.chatPlayer("Could you make me some red dye, please?")
        it.player.inventory.remove(Items.REDBERRIES, 3)
        it.player.inventory.remove(Items.COINS_995, 5)
        it.itemMessageBox("You hand the berries and payment to Aggie. Aggie produces a red bottle and hands it to you" +
            ".", Items.RED_DYE)
    }
}

suspend fun makeYellowDye(it: QueueTask) {
    if (it.player.inventory.getItemCount(Items.ONION) < 2) {
        it.messageBox("You don't have enough onions to make the yellow dye.")
    }
    else if (it.player.inventory.getItemCount(Items.COINS_995) < 5) {
        it.messageBox("You don't have enough coins to pay for the dye.")
    }
    else {
        it.chatPlayer("Could you make me some blue dye, please?")
        it.player.inventory.remove(Items.ONION, 2)
        it.player.inventory.remove(Items.COINS_995, 5)
        it.itemMessageBox("You hand the onions and payment to Aggie. Aggie produces a yellow bottle and hands" +
            " it to you.", Items.YELLOW_DYE)
    }
}

suspend fun makeBlueDye(it: QueueTask) {
    if (it.player.inventory.getItemCount(Items.WOAD_LEAF) < 2) {
        it.messageBox("You don't have enough woad leaves to make the blue dye.")
    }
    else if (it.player.inventory.getItemCount(Items.COINS_995) < 5) {
        it.messageBox("You don't have enough coins to pay for the dye.")
    }
    else {
        it.chatPlayer("Could you make me some blue dye, please?")
        it.player.inventory.remove(Items.WOAD_LEAF, 2)
        it.player.inventory.remove(Items.COINS_995, 5)
        it.itemMessageBox("You hand the woad leaves and payment to Aggie. Aggie produces a blue bottle and hands" +
            " it to you.", Items.BLUE_DYE)
    }
}

suspend fun notAllIngredients(it: QueueTask) {
    it.chatPlayer("I don't think I have all the ingredients yet.", facialExpression = FacialExpression.CONFUSED, wrap
    = true)
    it.chatNpc("You know what you need to get, now come back when you have them. Goodbye for now.", wrap = true)
}

suspend fun doWithout(it: QueueTask) {
    it.chatPlayer(" I can do without dye at that price.", wrap = true)
    it.chatNpc("That's your choice, but I would think you have killed for less. I can see it in your eyes.",
        facialExpression = FacialExpression.SUSPICIOUS, wrap = true)
}
