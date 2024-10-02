package gg.rsmod.plugins.content.areas.barbarianoutpost

import gg.rsmod.game.model.attr.*

val ottoGodblessed = Npcs.OTTO_GODBLESSED

on_npc_option(ottoGodblessed, option = "talk-to") {
    player.queue {
        if (player.attr.has(talkedToOtto)) {
            iSeekMoreAnswers(this)
        } else {
            barbarianTrainingDialogue(this)
        }
    }
}

on_obj_option(obj = Objs.BARBARIAN_BED, option = "search") {
    if (player.hasItem(Items.BARBARIAN_ROD)) {
        player.message("You find nothing under the bed.")
        return@on_obj_option
    }
    player.queue {
        // Animate the player milking the cow
        player.animate(12740, idleOnly = true)
        wait(world.getAnimationDelay(12740))
        player.inventory.add(Items.BARBARIAN_ROD)
        // Send a message to the player indicating that they have milked the cow
        player.filterableMessage("You find a heavy fishing rod under the bed and take it.")
    }
}

suspend fun barbarianTrainingDialogue(it: QueueTask) {
    it.chatPlayer("Hello there. Are you Otto Godblessed?")
    it.chatNpc(
        "Good day, you seem a hearty warrior. Maybe even",
        "some barbarian blood in that body of yours?",
    )
    val option =
        it.options(
            "I really don't think I am related to any barbarian.",
            "You think so?",
            "Who are you?",
            "Sorry, I'm too busy to talk about genealogy today.",
        )
    when (option) {
        1 -> notRelatedToBarbarian(it)
        2 -> youThinkSo(it)
        3 -> whoAreYou(it)
        4 -> tooBusyToTalk(it)
    }
}

suspend fun notRelatedToBarbarian(it: QueueTask) {
    it.chatPlayer("I really don't think I am related to any barbarian.")
    it.chatNpc("Your scepticism will be your loss.")
    it.chatPlayer("You think so?")
    it.chatNpc(
        "Who can tell? My forefathers weren't averse to",
        "travelling, so it is possible. They tended to cause too",
        "much trouble in your so-called civilised lands, however,",
        "so most returned to their ancestral lands.",
    )
    it.chatNpc(
        "In any case, I think you are ready",
        "to learn our more secret tribal feats for yourself.",
    )
    it.chatPlayer(
        "Oh, that sounds interesting, what sort of skills",
        "would these be?",
    )
    it.chatNpc(
        "To begin with I can supply knowledge in the ways of",
        "firemaking, our special rod fishing tricks and a selection",
        "of spear skills.",
    )
    it.chatPlayer(
        "There are more advanced stages though, to judge by",
        "your description?",
    )
    it.chatNpc(
        "Your perception is creditable. I can eventually teach of",
        "more advanced firemaking techniques, and the rod",
        "fishing skills are but a preliminary to our special potions",
        "and brews.",
    )
    it.chatNpc(
        "These secrets must, however, wait until you have",
        "learned of the more basic skills.",
    )
    it.player.attr[talkedToOtto] = true
    val option2 =
        it.options(
            "Please, supply me details of your cunning with harpoons.",
            "Are there any ways to use a fishing rod which I might learn?",
            "My mind is ready for your Firemaking wisdom, please instruct me.",
            "Who are you?",
        )
    when (option2) {
        1 -> detailsCunningHarpoons(it)
        2 -> fishingRodLearn(it)
        3 -> firemakingWisdom(it)
        4 -> whoAreYou(it)
    }
}

suspend fun youThinkSo(it: QueueTask) {
    it.chatPlayer("You think so?")
    it.chatNpc(
        "Who can tell? My forefathers weren't averse to",
        "travelling, so it is possible. They tended to cause too",
        "much trouble in your so-called civilised lands, however,",
        "so most returned to their ancestral lands.",
    )
    it.chatPlayer(
        "Oh, that sounds interesting, what sort of skills",
        "would these be?",
    )
    it.chatNpc(
        "To begin with I can supply knowledge in the ways of",
        "firemaking, our special rod fishing tricks and a selection",
        "of spear skills.",
    )
    it.chatPlayer(
        "There are more advanced stages though, to judge by",
        "your description?",
    )
    it.chatNpc(
        "Your perception is creditable. I can eventually teach of",
        "more advanced firemaking techniques, and the rod",
        "fishing skills are but a preliminary to our special potions",
        "and brews.",
    )
    it.chatNpc(
        "These secrets must, however, wait until you have",
        "learned of the more basic skills.",
    )
    it.player.attr[talkedToOtto] = true
    val option2 =
        it.options(
            "Please, supply me details of your cunning with harpoons.",
            "Are there any ways to use a fishing rod which I might learn?",
            "My mind is ready for your Firemaking wisdom, please instruct me.",
            "Who are you?",
        )
    when (option2) {
        1 -> detailsCunningHarpoons(it)
        2 -> fishingRodLearn(it)
        3 -> firemakingWisdom(it)
        4 -> whoAreYou(it)
    }
}

suspend fun tooBusyToTalk(it: QueueTask) {
    it.chatPlayer("Sorry, I'm too busy to talk about genealogy today.")
    it.chatNpc("We will talk when you learn to be less impetuous.")
}

suspend fun detailsCunningHarpoons(it: QueueTask) {
    it.chatPlayer("Please, supply me details of your cunning with harpoons.")
    it.chatNpc(
        "First you must know more of harpoons through special",
        "study of fish that are usually caught with such a device.",
    )
    it.chatPlayer("What do I need to know?")
    it.chatNpc(
        "You must catch fish which are usually harpooned,",
        "without a harpoon. You will be using your skill and",
        "strength",
    )
    it.chatPlayer("How do you expect me to do that?")
    it.chatNpc(
        "Use your arm as bait. Wriggle your fingers as if they",
        "are a tasty snack and hungry tuna and swordfish will",
        "throng to be caught by you.",
    )
    it.chatPlayer("I'm glad you didn't mention sharks!", facialExpression = FacialExpression.WORRIED)
    it.chatNpc(
        "Oh, my mind slipped for a moment, this method does",
        "indeed work with shark - though in this case the action",
        "must be more of a frenzied thrashing of the arm than a",
        "wriggle",
    )
    it.chatPlayer(
        "...and I thought Fishing was a safe way to pass the time.",
        facialExpression = FacialExpression.CONFLICTED,
    )
    it.player.attr[learnedBarbarianHarpooning] = true
    val option3 =
        it.options(
            "I seek more answers.",
            "I have no more questions at this time.",
        )
    when (option3) {
        1 -> iSeekMoreAnswers(it)
        2 -> iHaveNoMoreQuestionsAtThisTime(it)
    }
}

suspend fun detailsHerblore(it: QueueTask) {
    if (it.player.hasItem(Items.ATTACK_MIX_2)) {
        it.chatPlayer(
            "What was that secret knowledge of Herblore we talked",
            "of?",
        )
        it.chatNpc(
            "I see you have my potion. I will say no more than that",
            "I am eternally grateful.",
        )
        it.chatPlayer(
            "I feel I am missing some vital information about your",
            "need for this potion, though I often have this suspicion.",
        )
        it.chatNpc(
            "I will not reveal all of my private matters to you. Some",
            "secrets are best kept rather than revealed.",
        )
        it.player.attr[learnedBarbarianHerblore] = true
    } else {
        it.chatPlayer(
            "What was that secret knowledge of Herblore we talked",
            "of?",
        )
        it.chatNpc("Do you have my potion?")
        it.chatPlayer("What was it you needed again?")
        it.chatNpc(
            "Bring me a lesser attack potion combined with fish roe.",
            "There is more importance in this than you will ever",
            "know.",
        )
    }
    val option3 =
        it.options(
            "I seek more answers.",
            "I have no more questions at this time.",
        )
    when (option3) {
        1 -> iSeekMoreAnswers(it)
        2 -> iHaveNoMoreQuestionsAtThisTime(it)
    }
}

suspend fun fishingRodLearn(it: QueueTask) {
    it.chatPlayer("Are there any ways to use a fishing rod which I might learn?")
    it.chatNpc(
        "While you civilised folk use small, weak fishing rods, we",
        "barbarians are skilled with heavier tackle. We fish in the",
        "lake nearby.",
    )
    it.chatPlayer("So can you teach me of this fishing method?")
    it.chatNpc(
        "Certaintly. Take the rod from under the bed in my",
        "dwelling and fish in the lake. When you have caught a",
        "few fish I am sure you will be ready to talk more with",
        "me.",
    )
    it.chatNpc(
        "You will know when you are ready, since inspiration will",
        "fill your mind.",
    )
    it.chatPlayer("So I can obtain new foods from these Fishing spots?")
    it.chatNpc(
        "We do not use these fish quite as you might expect.",
        "When you return from Fishing we can speak more of",
        "this matter.",
    )
    it.player.attr[learnedBarbarianRod] = true
    val option3 =
        it.options(
            "I seek more answers.",
            "I have no more questions at this time.",
        )
    when (option3) {
        1 -> iSeekMoreAnswers(it)
        2 -> iHaveNoMoreQuestionsAtThisTime(it)
    }
}

suspend fun firemakingWisdom(it: QueueTask) {
    it.chatPlayer(
        "My mind is ready for your Firemaking wisdom, please",
        "instruct me.",
    )
    it.chatNpc(
        "The first point in your progression is that of lighting",
        "fires without a tinderbox.",
    )
    it.chatPlayer("That sounds pretty useful, tell me more.")
    it.chatNpc(
        "For this process you will require a strung bow. You",
        "use the bow to quickly rotate pieces of wood against one",
        "another. As you rub the wood becomes hot, eventually",
        "springing into flame.",
    )
    it.chatPlayer("No more secret details?")
    it.chatNpc(
        "The spirits will aid you, the power they supply will guide",
        "your hands. Go and benefit from their guidance upon",
        "an oaken log.",
    )
    it.player.attr[learnedBarbarianFiremaking] = true
    val option3 =
        it.options(
            "I seek more answers.",
            "I have no more questions at this time.",
        )
    when (option3) {
        1 -> iSeekMoreAnswers(it)
        2 -> iHaveNoMoreQuestionsAtThisTime(it)
    }
}

suspend fun iHaveNoMoreQuestionsAtThisTime(it: QueueTask) {
    it.chatPlayer("I have no more questions at this time.")
    it.chatNpc(
        "I see you have free space and no record of the tasks I",
        "have set you. Please take this book as a record of your",
        "progress - between the spirits and your diligence I",
        "expect it will always be up to date.",
    )
    if (!it.player.hasItem(Items.BARBARIAN_SKILLS) && !it.player.inventory.isFull) {
        it.player.inventory.add(Items.BARBARIAN_SKILLS)
    }
}

suspend fun iSeekMoreAnswers(it: QueueTask) {
    if (it.player.attr.has(learnedBarbarianHarpooning) &&
        it.player.attr.has(learnedBarbarianFiremaking) &&
        (
            it.player.attr.has(
                learnedBarbarianRod,
            ) &&
                it.player.attr.has(learnedBarbarianHerblore)
        )
    ) {
        whoAreYou(it)
    } else {
        it.chatPlayer("I seek more answers.")
        val optionsText = mutableListOf<String>()
        val optionsFunc = mutableListOf<suspend (QueueTask) -> Unit>()

        if (!it.player.attr.has(learnedBarbarianHarpooning)) {
            optionsText.add("Please, supply me details of your cunning with harpoons.")
            optionsFunc.add(::detailsCunningHarpoons)
        }

        if (!it.player.attr.has(learnedBarbarianRod)) {
            optionsText.add("Are there any ways to use a fishing rod which I might learn?")
            optionsFunc.add(::fishingRodLearn)
        }

        if (it.player.attr.has(learnedBarbarianRod) && !it.player.attr.has(learnedBarbarianHerblore)) {
            optionsText.add("What was that secret knowledge of Herblore?")
            optionsFunc.add(::detailsHerblore)
        }

        if (!it.player.attr.has(learnedBarbarianFiremaking)) {
            optionsText.add("My mind is ready for your Firemaking wisdom, please instruct me.")
            optionsFunc.add(::firemakingWisdom)
        }

        optionsText.add("Who are you?")
        optionsFunc.add(::whoAreYou)

        val selectedOption = it.options(*optionsText.toTypedArray())

        optionsFunc[selectedOption - 1](it)
    }
}

suspend fun whoAreYou(it: QueueTask) {
    it.chatPlayer("Who are you?")
    it.chatNpc(
        "I am Otto Godblessed, the last of the Godblessed line.",
        "I am the keeper of the ancient knowledge of my ancestors,",
        "and I am here to share it with those who prove themselves",
        "worthy.",
    )
}
