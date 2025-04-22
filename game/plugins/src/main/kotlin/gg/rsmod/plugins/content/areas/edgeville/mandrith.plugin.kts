package gg.rsmod.plugins.content.areas.edgeville

import gg.rsmod.plugins.content.items.AncientArtifacts

on_npc_option(Npcs.MANDRITH, "Talk-to") {
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

on_any_item_on_npc(Npcs.MANDRITH) {
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
    it.chatNpc("Glorious, brave warrior! I see you have found $oneSome of the ancient artefacts my brother and " +
        "I are seeking.",
        wrap = true)
    it.chatNpc("You wouldn't be willing to sell $itThem to me?")
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
            whoAreYouOptions(it)
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
            it.chatNpc("Why, I'm Mandrith! Inspiration to combatants both mighty and puny!",
                facialExpression = FacialExpression.LAUGH_EXCITED,
                wrap = true)
            it.chatPlayer("Okay...fair enough.",
                facialExpression = FacialExpression.CONFUSED,
                wrap = true)
            whoAreYouOptions(it)
        }
        SECOND_OPTION -> whatDoYouDoDialogue(it)
    }
}

suspend fun whoAreYouOptions(it: QueueTask) {
    when (it.options(
        "What do you do here?",
        "Erm, what's with the outfit?",
        "I have to go now."
    )) {
        FIRST_OPTION -> whatDoYouDoDialogue(it)
        SECOND_OPTION -> {
            it.chatPlayer("Erm, what's with the outfit?",
                facialExpression = FacialExpression.CONFUSED,
                wrap = true)
            it.chatNpc("You like not my kingly robes? They were my father's, and his father's before him, and his " +
                "father's before him, and his father's before him, and-",
                wrap = true)
            it.chatPlayer("Okay! Okay! I get the picture", wrap = true)
        }
        THIRD_OPTION -> whoAreYouOptions(it)
    }
}

suspend fun whatArtefactsDialogue(it: QueueTask) {
    it.chatNpc("Haha! I can tell you are new to these parts.",
        facialExpression = FacialExpression.LAUGHING,
        wrap = true)
    it.chatNpc("As the blood of warriors is spilled on the ground, as it once was during the God Wars, relics of" +
        " that age feel the call of battle and are drawn into the rays of sun once more. If you happen to come across",
        wrap = true)
    it.chatNpc("any of these ancient items, bring them to me or my brother Nastroth in Lumbridge, and we will pay you" +
        " a fair price for them. We don't accept them in noted form, though, remember that. Also, we don't want to",
        wrap = true)
    it.chatNpc("buy any weapons or armour.")
    when (it.options(
        "You have a brother?",
        "Why don't you buy weapons or armour?",
        "That sounds great. Goodbye."
    )) {
        FIRST_OPTION -> youHaveABrotherDialogue(it)
        SECOND_OPTION -> {
            it.chatNpc("They should be used as they were meant to be used, not traded for money. Mandrith and I only " +
                "collect ancient artefacts.", wrap = true)
            whoAreYouOptions(it)
        }
        THIRD_OPTION -> it.chatPlayer("That sounds great. Goodbye.")
    }
}

suspend fun youHaveABrotherDialogue(it: QueueTask) {
    it.chatPlayer("You have a brother?", facialExpression = FacialExpression.CONFUSED, wrap = true)
    it.chatNpc("Yes, why else would I have referred to him as such?",
        wrap = true)
    it.chatPlayer("You make a good point.")
    whoAreYouOptions(it)
}

suspend fun whatDoYouDoDialogue(it: QueueTask) {
    it.chatNpc("I am here to collect ancient artefacts acquired by adventurers in return for some well-deserved" +
        " money.",
        wrap =  true)
    when (it.options(
        "What ancient artefacts?",
        "That sounds great. Goodbye."
    )) {
        FIRST_OPTION -> whatArtefactsDialogue(it)
        SECOND_OPTION -> it.chatPlayer("That sounds great. Goodbye.")
    }
}
