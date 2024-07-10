package gg.rsmod.plugins.content.areas.wilderness

val darkMage = Npcs.DARK_MAGE_2262

on_npc_option(darkMage, option = "talk-to") {
    player.queue {
        darkMageDialogue(this)
    }
}

suspend fun darkMageDialogue(it: QueueTask) {
    it.chatPlayer("Hello there.")
    it.chatNpc("Quiet! You must not break my concentration!")
    val option1 = it.options("Why not?", "What are you doing here?", "Ok, Sorry", "I need your help with something...")
    when (option1) {
        1 -> whyNot(it)
        2 -> whatAreYouDoingHere(it)
        3 -> okSorry(it)
        4 -> needHelp(it)
    }
}

suspend fun whyNot(it: QueueTask) {
    it.chatPlayer("Why not?")
    it.chatNpc(
        "Well, if my concentration is broken while keeping this",
        "gate open, then, if we are lucky, everyone within a one mile",
        "radius will either have their heads explode, or will be",
        "consumed internally by the creatures of the Abyss.",
    )
    it.chatPlayer("Erm... And if we are unlucky?")
    it.chatNpc(
        "If we are unlucky, then the entire universe will begin to fold",
        "in upon itself, and all reality as we know it will be ",
        "annihilated in a single stroke. So leave me alone!",
    )
    val option2 = it.options("What are you doing here?", "Ok, Sorry")
    when (option2) {
        1 -> whatAreYouDoingHere(it)
        2 -> okSorry(it)
    }
}

suspend fun whatAreYouDoingHere(it: QueueTask) {
    it.chatPlayer("What are you doing here?")
    it.chatNpc(
        "Do you mean what am I doing here in Abyssal space, or are",
        "you asking me what I consider my ultimate role to be",
        "in this voyage that we call life?",
    )
    it.chatPlayer("Um... the first one.")
    it.chatNpc(
        "By remaining here and holding this portal open,",
        "I am providing a permanent link between normal space",
        "and this strange dimension that we call Abyssal space.",
    )

    it.chatNpc(
        "As long as this spell remains in effect, we have the",
        "capability to teleport into abyssal space at will.",
        "Now leave me be! I can afford no distraction in my task!",
    )
    val option3 = it.options("Why not?", "Ok, Sorry")
    when (option3) {
        1 -> whyNot(it)
        2 -> okSorry(it)
    }
}

suspend fun okSorry(it: QueueTask) {
    it.chatPlayer("Ok, sorry.")
    it.chatNpc(
        "I am attempting to subdue the elemental mechanisms of the",
        "universe to my will. Inane chatter from random idiots is not",
        "helping me achieve this!",
    )
}

suspend fun needHelp(it: QueueTask) {
    it.chatPlayer(
        "Sorry to disturb you, I just needed your help",
        "with something quickly.",
    )
    it.chatNpc("What? Oh... Very well. What did you want?")
    // Check if the player has an abyssal book in the inventory.
    if (it.player.inventory.contains(Items.ABYSSAL_BOOK)) {
        it.chatPlayer("Actually, I can't think of anything right now...")
        it.chatNpc(
            "THEN STOP DISTRACTING ME!",
            "Honestly, you have no idea of the pressure I am under",
            "attempting to keep this portal open!",
        )
    } else {
        val option4 = it.options("Can I have another Abyssal book?", "Actually, I can't think of anything right now...")
        when (option4) {
            1 -> {
                it.chatPlayer(
                    "Can I have another Abyssal book?",
                    "I seem to have misplaced the one I was given.",
                )
                it.chatNpc(
                    "Here, take it, it is important to pool our research.",
                    "Now leave me be, I must concentrate!",
                )
                it.player.inventory.add(Items.ABYSSAL_BOOK)
            }
            2 -> {
                it.chatPlayer("Actually, I can't think of anything right now...")
                it.chatNpc(
                    "THEN STOP DISTRACTING ME!",
                    "Honestly, you have no idea of the pressure I am under",
                    "attempting to keep this portal open!",
                )
            }
        }
    }
}
