package gg.rsmod.plugins.content.areas.ardougne

val guards = listOf(Npcs.LEGENDS_GUARD, Npcs.LEGENDS_GUARD_399)

guards.forEach {
    on_npc_option(it, "Talk-to") {
        player.queue {
            player.message("You approach a nearby guard.")
            chatNpc("Yes, ${sirMaam(player)}, how can I help you?")
            mainOptions(this)
        }
    }
}

suspend fun mainOptions(it: QueueTask) {
    when (it.options(
        "What is this place?",
        "How do I get in here?",
        "Can I speak to someone in charge?",
        "It's okay, thanks."
    )) {
        FIRST_OPTION -> whatIsThisPlaceDialogue(it)
        SECOND_OPTION -> howDoIGetInDialogue(it)
        THIRD_OPTION -> speakToSomeoneInChargeDialogue(it)
        FOURTH_OPTION -> {
            it.chatPlayer("It's okay, thanks.",
                facialExpression = FacialExpression.CALM_TALK,
                wrap = true)
            it.chatNpc("Very well, ${sirMaam(it.player)}",
                facialExpression = FacialExpression.HAPPY_TALKING,
                wrap = true)
        }
    }
}

suspend fun whatIsThisPlaceDialogue(it: QueueTask) {
    it.chatPlayer("What is this place?",
        facialExpression = FacialExpression.CONFUSED,
        wrap = true)
    it.chatNpc("This is the Legends' Guild, ${sirMaam(it.player)}",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
    it.chatNpc("Legendary citizens are invited on a quest in order to become members of the guild.",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
    when (it.options(
        "Can I go on the quest?",
        "What kind of quest is it?"
    )) {
        FIRST_OPTION -> {
            canIGoOnQuestDialogue(it)
        }
        SECOND_OPTION -> {
            it.chatPlayer("What kind of quest is it?",
                facialExpression = FacialExpression.CONFUSED,
                wrap = true)
            it.chatNpc("Well, to be honest, ${sirMaam(it.player)}, I'm not really sure. You'll need to talk to Grand " +
                "Vizier Erkle to figure that out.",
                facialExpression = FacialExpression.CONFUSED,
                wrap = true)
            when (it.options(
                "Can I go on the quest?",
                "Thanks for your help."
            )) {
                FIRST_OPTION -> canIGoOnQuestDialogue(it)
                SECOND_OPTION -> {
                    it.chatPlayer("Thanks for your help.",
                        facialExpression = FacialExpression.HAPPY_TALKING,
                        wrap = true)
                    it.chatNpc("You're welcome.",
                        facialExpression = FacialExpression.CALM_TALK,
                        wrap = true)
                    it.player.message("The guard marches off on patrol again.")
                }
            }
        }
    }
}

suspend fun howDoIGetInDialogue(it: QueueTask) {
    it.chatPlayer("How do I get in here?",
        facialExpression = FacialExpression.CONFUSED,
        wrap = true)
    it.chatNpc("Well, ${sirMaam(it.player)}, you'll need to be a legendary citizen of Gielinor.",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
    otherOptions(it)
}

suspend fun canIGoOnQuestDialogue(it: QueueTask) {
    it.chatPlayer("Can I go on the quest?",
        facialExpression = FacialExpression.CONFUSED,
        wrap = true)
    it.messageBox("The guard gets out a scroll of paper and starts looking through it.")
    it.chatNpc("I'm very sorry, but you need to complete more requirements before you can go on this " +
        "quest. They don't call it the Legends' Guild for nothing, you know!",
        facialExpression = FacialExpression.HAPPY_TALKING,
        wrap = true)
    // TODO: Change this once the Legend's Quest is released
    it.chatNpc("You can however, still temporarily enter the Legends Guild for now if you have " +
        "completed all available quests.",
        facialExpression = FacialExpression.HAPPY_TALKING,
        wrap = true)
}

suspend fun speakToSomeoneInChargeDialogue(it: QueueTask) {
    it.chatPlayer("Can I speak to someone in charge?",
        facialExpression = FacialExpression.CONFUSED,
        wrap = true)
    it.chatNpc("Well, ${sirMaam(it.player)}, Radimus Erkle is the Grand Vizier of the Legends' Guild. " +
        "He's a very busy man. And he'll only talk to those people eligible for the quest.",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
    mainOptions(it)
}

suspend fun otherOptions(it: QueueTask) {
    when (it.options(
        "What is this place?",
        "Can I speak to someone in charge?",
        "Can I go on the quest?"
    )) {
       FIRST_OPTION -> whatIsThisPlaceDialogue(it)
       SECOND_OPTION ->  speakToSomeoneInChargeDialogue(it)
        THIRD_OPTION -> canIGoOnQuestDialogue(it)
    }
}

fun sirMaam(player: Player): String {
    return if (player.appearance.gender == Gender.MALE) "sir" else "ma'am"
}
