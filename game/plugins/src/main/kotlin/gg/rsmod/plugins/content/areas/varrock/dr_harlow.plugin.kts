package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.game.model.attr.gaveHarlowBeer
import gg.rsmod.plugins.content.quests.*
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer

on_npc_option(npc = Npcs.DR_HARLOW, option = "talk-to") {
    player.queue {
        if (player.startedQuest(VampyreSlayer)) {
            if (player.finishedQuest(VampyreSlayer)) {
                drHarlowDialogueAfterVampyreSlayer(this)
            } else {
                when (player.getCurrentStage(VampyreSlayer)) {
                    1 -> drHarlowDialogueAfterAcceptingQuest(this)
                    2 ->
                        if (!player.attr.has(gaveHarlowBeer)) {
                            drHarlowDialogueQuestStage2(this)
                        } else {
                            drHarlowDialogueAfterBuyingHimADrink(this)
                        }
                    else -> drHarlowDialogueAfterBuyingHimADrink(this)
                }
            }
        } else {
            drHarlowDialogue(this)
        }
    }
}

suspend fun drHarlowDialogue(it: QueueTask) {
    it.chatNpc("Buy me a drrink pleassh.", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
    when (
        it.options(
            "No, you've had enough.",
            "Okay, here you go.",
        )
    ) {
        1 -> {
            it.chatPlayer("No, you've had enough.")
            it.chatNpc("Pssssh, I never have enough!")
        }
        2 -> {
            if (it.player.inventory.contains(item = Items.BEER)) {
                it.chatPlayer("Okay, here you go.")
                it.itemMessageBox("You give a beer to Dr Harlow.", item = Items.BEER)
                it.player.inventory.remove(Items.BEER)
            } else {
                it.chatPlayer("I'll just go and buy one.")
            }
        }
    }
}

suspend fun drHarlowDialogueAfterAcceptingQuest(it: QueueTask) {
    it.chatNpc("Buy me a drrink pleassh.", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
    when (
        it.options(
            "No, you've had enough.",
            "Are you Dr Harlow, the famous vampyre slayer?",
            "You couldn't possibly be Dr Harlow, you're just a drunk.",
        )
    ) {
        1 -> {
            it.chatPlayer("No, you've had enough.")
            it.chatNpc("Pssssh, I never have enough!", facialExpression = FacialExpression.ANNOYED)
        }
        2 -> {
            it.chatPlayer(
                "Are you Dr Harlow, the famous vampyre slayer?",
                facialExpression = FacialExpression.UNCERTAIN,
            )
            it.chatNpc("Dependsh whose ashking.", facialExpression = FacialExpression.SUSPICIOUS)
            it.chatPlayer(
                "Your friend Morgan sent me. He said you could teach",
                "me how to slay a vampyre.",
                facialExpression = FacialExpression.WORRIED,
            )
            it.chatNpc(
                "Shure, I can teash you. I wash the best vampyre",
                "shhlayer ever.",
                facialExpression = FacialExpression.DRUNK_HAPPY_TIRED,
            )
            it.chatNpc("Buy me a beer and I'll teash you.", facialExpression = FacialExpression.HAPPY_TALKING)
            it.chatPlayer(
                "Your good friend Morgan is living in fear of a",
                "vampyre and all you can think about is beer?",
                facialExpression = FacialExpression.ANGRY,
            )
            it.chatNpc("Buy ush a drink anyway.", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
            it.player.advanceToNextStage(VampyreSlayer)
        }
        3 -> {
            it.chatPlayer("You couldn't possibly be Dr Harlow, you're just a drunk.")
            it.chatNpc(
                "Of coursh I'm Dr Harlow. I'm famoush! I've shlayed more vampyresh than a pup like you " +
                    "could imagine.",
                wrap = true,
                facialExpression = FacialExpression.DRUNK_HAPPY_TIRED,
            )
            it.chatPlayer(
                "I think Morgan must have been mistaken. There is no way you could teach me to slay a" +
                    "vampyre, you can barely walk!",
                wrap = true,
            )
            it.chatNpc(
                "I'm the besht in the business. No one could teash you what I know. No one else has sheen" +
                    " what I've sheen.",
                facialExpression = FacialExpression.DRUNK_HAPPY_TIRED,
                wrap = true,
            )
            it.chatNpc("Buy me a beer and I'll teash you.", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
            it.chatPlayer(
                "Your good friend Morgan is living in fear of a vampyre and all you can think about is" +
                    " beer?",
                facialExpression = FacialExpression.ANGRY,
                wrap = true,
            )
            it.chatNpc("Buy ush a drink anyway.", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
            it.player.advanceToNextStage(VampyreSlayer)
        }
    }
}

suspend fun drHarlowDialogueQuestStage2(it: QueueTask) {
    it.chatNpc("Did you buy ush a drrink?", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
    it.chatPlayer("Okay, here you go.", facialExpression = FacialExpression.HAPPY)
    if (it.player.inventory.contains(item = Items.BEER)) {
        it.chatPlayer("Okay, here you go.")
        it.itemMessageBox("You give a beer to Dr Harlow.", item = Items.BEER)
        if (it.player.inventory
                .remove(Items.BEER)
                .hasSucceeded()
        ) {
            it.player.attr.put(gaveHarlowBeer, true)
            it.chatNpc("Cheersh, matey.", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
            it.chatPlayer("So, tell me how to kill vampires then.")
            it.chatNpc(
                "Yes, yes, vampyres. I was very good at",
                "killing 'em once.",
                facialExpression = FacialExpression.DRUNK_HAPPY_TIRED,
            )
            it.chatNpc(
                "Vampyre slaying is not to be undertaken lightly. You",
                "must go in prepared, or you will die.",
            )
            it.chatNpc("*Sigh*", facialExpression = FacialExpression.DWELL)
            it.chatNpc(
                "A stake is an essential tool for any vampyre slayer. The",
                "stake must be used in the final blow against the",
                "vampyre or his dark magic will regenerate him to full",
                "health.",
            )
            if (it.player.inventory.freeSlotCount >= 2) {
                it.chatNpc("I always carry a spare so you can have one.")
                it.itemMessageBox("Dr Harlow hands you a stake.", Items.STAKE)
                it.player.inventory.add(Items.STAKE)
                it.chatNpc(
                    "You'll need a special hammer as well, to drive it in",
                    "properly.",
                )
                it.chatNpc(
                    "Hmm, I think I have a spare hammer you can have.",
                    facialExpression = FacialExpression.THINKING,
                )
                it.itemMessageBox("Dr Harlow hands you a strange hammer.", Items.STAKE_HAMMER)
                it.player.inventory.add(Items.STAKE_HAMMER)
                it.chatNpc(
                    "One last thing. It's wise to carry garlic with you;",
                    "vampyres are slightly weakened if they can smell garlic.",
                )
                it.chatNpc(
                    "Garlic is pretty common, I know I always advised",
                    "Morgan to keep a supply, so you might be able to get",
                    "some from him. If not, I know they sell it in Port",
                    "Sarim.",
                )
                it.chatPlayer(
                    "Okay, so those are the supplied I need, but how do I",
                    "actually kill him?",
                )
                it.chatNpc("You are an eager one.")
                it.chatNpc(
                    "Killing a vampyre is DANGEROUS! Never forget that.",
                    "Go in prepared, with food and armour, but understand",
                    "that you may die. It's a risk we all take in this business.",
                )
                it.chatNpc(
                    "I've seen many fine men and women die at the hands",
                    "of vampyres.",
                )
                it.chatNpc(
                    "Enter the vampyre's lair and attempt to open the coffin.",
                    "He should be asleep in there, so try to use the stake on",
                    "him. As you're new at this, you'll probably just wake him",
                    "up and the real fight begins.",
                )
                it.chatNpc(
                    "Fight him until he's nearly dead, and, when the moment",
                    "is right, stake him through the heart and hammer it in.",
                )
                it.chatNpc(
                    "It's gruesome, but it's the only way. Once he's dead,",
                    "speak to Morgan so he can notify the village.",
                )
                it.chatPlayer("Thank you very much!")
            } else {
                it.chatNpc(
                    "I have some equipment to give you, but you don't have",
                    "enough space. Speak to me again when you do.",
                )
            }
        }
    } else {
        it.chatPlayer("I'll just go and buy one.")
    }
}

suspend fun drHarlowDialogueAfterBuyingHimADrink(it: QueueTask) {
    it.chatPlayer("So, tell me how to kill vampires then.")
    it.chatNpc(
        "Yes, yes, vampyres. I was very good at",
        "killing 'em once.",
        facialExpression = FacialExpression.DRUNK_HAPPY_TIRED,
    )
    it.chatNpc(
        "Vampyre slaying is not to be undertaken lightly. You",
        "must go in prepared, or you will die.",
    )
    it.chatNpc("*Sigh*", facialExpression = FacialExpression.DWELL)
    it.chatNpc(
        "A stake is an essential tool for any vampyre slayer. The",
        "stake must be used in the final blow against the",
        "vampyre or his dark magic will regenerate him to full",
        "health.",
    )
    if (it.player.inventory.freeSlotCount >= 2) {
        it.chatNpc("I always carry a spare so you can have one.")
        it.itemMessageBox("Dr Harlow hands you a stake.", Items.STAKE)
        it.player.inventory.add(Items.STAKE)
        it.chatNpc(
            "You'll need a special hammer as well, to drive it in",
            "properly.",
        )
        it.chatNpc("Hmm, I think I have a spare hammer you can have.", facialExpression = FacialExpression.THINKING)
        it.itemMessageBox("Dr Harlow hands you a strange hammer.", Items.STAKE_HAMMER)
        it.player.inventory.add(Items.STAKE_HAMMER)
        it.chatNpc(
            "One last thing. It's wise to carry garlic with you;",
            "vampyres are slightly weakened if they can smell garlic.",
        )
        it.chatNpc(
            "Garlic is pretty common, I know I always advised",
            "Morgan to keep a supply, so you might be able to get",
            "some from him. If not, I know they sell it in Port",
            "Sarim.",
        )
        it.chatPlayer(
            "Okay, so those are the supplied I need, but how do I",
            "actually kill him?",
        )
        it.chatNpc("You are an eager one.")
        it.chatNpc(
            "Killing a vampyre is DANGEROUS! Never forget that.",
            "Go in prepared, with food and armour, but understand",
            "that you may die. It's a risk we all take in this business.",
        )
        it.chatNpc(
            "I've seen many fine men and women die at the hands",
            "of vampyres.",
        )
        it.chatNpc(
            "Enter the vampyre's lair and attempt to open the coffin.",
            "He should be asleep in there, so try to use the stake on",
            "him. As you're new at this, you'll probably just wake him",
            "up and the real fight begins.",
        )
        it.chatNpc(
            "Fight him until he's nearly dead, and, when the moment",
            "is right, stake him through the heart and hammer it in.",
        )
        it.chatNpc(
            "It's gruesome, but it's the only way. Once he's dead,",
            "speak to Morgan so he can notify the village.",
        )
        it.chatPlayer("Thank you very much!")
    } else {
        it.chatNpc(
            "I have some equipment to give you, but you don't have",
            "enough space. Speak to me again when you do.",
        )
    }
}

suspend fun drHarlowDialogueAfterVampyreSlayer(it: QueueTask) {
    it.chatNpc("Buy me a drrink pleassh.", facialExpression = FacialExpression.DRUNK_HAPPY_TIRED)
    it.chatNpc("Oh, itsh you. What do you want?")
    it.chatPlayer(
        "I successfully slayed the vampyre! Morgan's village is now free of threat and all is well",
        wrap = true,
    )
    it.chatNpc("Well, that calls for a drink to celebrate! How 'bout yoush buy me one?", wrap = true)
}
