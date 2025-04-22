package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.items.AncientArtifacts

on_npc_option(Npcs.NASTROTH, "Talk-to") {
    player.queue {
        if (artefactCount(player) > 0) {
            exchangeArtefacts(this)
        }
        else {
            chatNpc("Greetings, brave warrior.", "What can I do for you?")
            mainOptions(this)
        }
    }
}

on_any_item_on_npc(Npcs.NASTROTH) {
    val item = player.getInteractingItem()
    player.queue {
        if (item.id in AncientArtifacts.notedItems()) {
            chatNpc("If you brought me the item itself, rather than a picture of it, I could give you some money for " +
                "it.",
                wrap = true)
        }
        else if (item.id in AncientArtifacts.unnotedItems()) {
            exchangeArtefacts(this)
        }
        else {
            chatNpc("I can't do anything with that.")
        }
    }
}

fun artefactCount(player: Player): Int {
    return player.inventory.count { it != null && it.id in AncientArtifacts.unnotedItems() }
}

suspend fun exchangeArtefacts(it: QueueTask) {
    val count = artefactCount(it.player)
    val oneSome = if (count == 1) "one" else "some"
    val itThem = if (count == 1) "it" else "them"
    it.chatNpc("Ah! I see you have found $oneSome of the ancient artefacts my brother and I are seeking.",
        wrap = true)
    it.chatNpc("Say, you wouldn't be willing to sell $itThem to me?")
    when (it.options(
        "Sure, I can do that.",
        "Who are you?",
        "No, sorry."
    )) {
        FIRST_OPTION -> {
            val artefactPlural = if (count == 1) "artefact" else "artefacts"
            var total = 0
            for (item in it.player.inventory) {
                if (item != null) {
                    val artefact = AncientArtifacts.forUnnoted(item.id)
                    if (artefact != null) {
                        it.player.inventory.remove(item)
                        it.player.inventory.add(Items.COINS_995, artefact.value)
                        total += artefact.value
                    }
                }
            }
            val totalFormatted = "%,d".format(total)
            it.messageBox("You sold the $artefactPlural for $totalFormatted coins.")
        }
        SECOND_OPTION -> {
            it.chatNpc("I am Nastroth. Like my brother, Mandrith, I'm a collector of ancient artefacts. I'm just not as " +
                "excited about it as he is.", wrap = true)
            whoAreYouDialogue(it)
        }
        THIRD_OPTION -> it.chatPlayer("No, sorry.")
    }
}

suspend fun mainOptions(it: QueueTask) {
    when (it.options(
        "Who are you?",
        "What do you do here?"
    )) {
        FIRST_OPTION -> {
            it.chatNpc("I am Nastroth. Like my brother, Mandrith, I'm a collector of ancient artefacts. I'm just not as " +
                "excited about it as he is.", wrap = true)
            whoAreYouDialogue(it)
        }
        SECOND_OPTION -> whatDoYouDoDialogue(it)
    }
}

suspend fun whoAreYouDialogue(it: QueueTask) {
    when (it.options(
        "Why aren't you excited about it?",
        "What are these ancient artefacts?",
        "Who is Mandrith?",
        "Let's talk about something else."
    )) {
        FIRST_OPTION -> whyNotExcitedDialogue(it)
        SECOND_OPTION -> whatArtefactsDialogue(it)
        THIRD_OPTION -> whosMandrithDialogue(it)
        FOURTH_OPTION -> whoAreYouDialogue(it)
    }
}

suspend fun whyNotExcitedDialogue(it: QueueTask) {
    it.chatNpc("Truth be told, I'd much rather be out there with the rest of you, breaking bones and cracking skulls" +
        ".", wrap = true)
    when (it.options(
        "Then why aren't you?",
        "That's not what I do."
    )) {
        FIRST_OPTION -> {
            it.chatNpc("My days of battle are over. Now I spend my time here, collecting ancient artefacts.", wrap =
                true)
            when (it.options(
                "What are these ancient artefacts?",
                "Have fun with that."
            )) {
                FIRST_OPTION -> whatArtefactsDialogue(it)
                SECOND_OPTION -> {
                    it.chatPlayer("Have fun with that.")
                }
            }
        }
        SECOND_OPTION -> {
            it.chatNpc("Oh. My apologies.", facialExpression = FacialExpression.SAD, wrap = true)
            whoAreYouDialogue(it)
        }
    }
}

suspend fun whatArtefactsDialogue(it: QueueTask) {
    it.chatNpc("As the blood and sweat of warriors is spilled on the ground, relics of the God Wars are drawn out " +
        "from the dirt where they were once left forgotten. If you happen to come acoss any of these ancient items, " +
        "bring them", wrap = true)
    it.chatNpc("to me or my brother Mandrith in the Edgeville bank, and we will pay you a fair price for" +
        " them. We don't accept them in noted form, though, remember that. Also, we don't want to buy any weapons or armour."
        , wrap = true)
    when (it.options(
        "Who is Mandrith?",
        "Why don't you buy weapons or armour?",
        "That sounds great. Goodbye."
    )) {
        FIRST_OPTION -> whosMandrithDialogue(it)
        SECOND_OPTION -> {
            it.chatNpc("They should be used as they were meant to be used, not traded for money. Mandrith and I only " +
                "collect ancient artefacts.", wrap = true)
            somethingElseOptions(it)
        }
        THIRD_OPTION -> it.chatPlayer("That sounds great. Goodbye.")
    }
}

suspend fun whosMandrithDialogue(it: QueueTask) {
    it.chatNpc("Mandrith is my overly excited brother and the one who made me wear this outfit. We share the same " +
        "purpose, collecting ancient artefacts, but he is located in the Edgeville bank.",
        wrap = true)
    somethingElseOptions(it)
}

suspend fun somethingElseOptions(it: QueueTask) {
    when (it.options(
        "What are these ancient artefacts?",
        "Let's talk about something else."
    )) {
        FIRST_OPTION -> whatArtefactsDialogue(it)
        SECOND_OPTION -> mainOptions(it)
    }
}

suspend fun whatDoYouDoDialogue(it: QueueTask) {
    it.chatNpc("I collect ancient artefacts acquired by warriors in return for money.", wrap =  true)
    somethingElseOptions(it)
}
