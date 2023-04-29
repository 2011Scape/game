package gg.rsmod.plugins.content.areas.barbarianoutpost

import gg.rsmod.game.model.timer.ACTIVE_COMBAT_TIMER

val ottoGodblessed = Npcs.OTTO_GODBLESSED

on_npc_option(ottoGodblessed, option = "talk-to") {
    player.queue {
        barbarianTrainingDialogue(this)
    }
}

suspend fun barbarianTrainingDialogue(it: QueueTask) {
    it.chatPlayer("Hello there. Are you Otto Godblessed?")
    it.chatNpc("Good day, you seem a hearty warrior. Maybe even",
                        "some barbarian blood in that body of yours?")
    val option = it.options("I really don't think I am related to any barbarian.", "You think so?", "Who are you?", "Sorry, I'm too busy to talk about genealogy today.")
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
    it.chatNpc("Who can tell? My forefathers weren't averse to",
                        "travelling, so it is possible. They tended to cause too",
                        "much trouble in your so-called civilised lands, however,",
                        "so most returned to their ancestral lands.")
    it.chatNpc("In any case, I think you are ready",
                        "to learn our more secret tribal feats for yourself.")
    it.chatPlayer("Oh, that sounds interesting, what sort of skills",
                            "would these be?")
    it.chatNpc("To begin with I can supply knowledge in the ways of",
                        "firemaking, our special rod fishing tricks and a selection",
                        "of spear skills.")
    it.chatPlayer("There are more advanced stages though, to judge by",
                            "your description?")
    it.chatNpc("Your perception is creditable. I can eventually teach of",
                        "more advanced firemaking techniques, and the rod",
                        "fishing skills are but a preliminary to our special potions",
                        "and brews.")
    it.chatNpc("These secrets must, however, wait until you have",
        "learned of the more basic skills.")

    val option2 = it.options("Please, supply me details of your cunning with harpoons.", "Are there any ways to use a fishing rod which I might learn?", "My mind is ready for your Firemaking wisdom, please instruct me.", "Who are you?")
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
        "so most returned to their ancestral lands."
    )
    it.chatPlayer("Oh, that sounds interesting, what sort of skills",
        "would these be?")
    it.chatNpc("To begin with I can supply knowledge in the ways of",
        "firemaking, our special rod fishing tricks and a selection",
        "of spear skills.")
    it.chatPlayer("There are more advanced stages though, to judge by",
        "your description?")
    it.chatNpc("Your perception is creditable. I can eventually teach of",
        "more advanced firemaking techniques, and the rod",
        "fishing skills are but a preliminary to our special potions",
        "and brews.")
    it.chatNpc("These secrets must, however, wait until you have",
        "learned of the more basic skills.")
    val option2 = it.options(
        "Please, supply me details of your cunning with harpoons.",
        "Are there any ways to use a fishing rod which I might learn?",
        "My mind is ready for your Firemaking wisdom, please instruct me.",
        "Who are you?"
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
        it.chatNpc("Very well, warrior. May the blood of the ancients guide your path.")
    }

    suspend fun detailsCunningHarpoons(it: QueueTask) {
        it.chatPlayer("Please, supply me details of your cunning with harpoons.")
        it.chatNpc("Ah, a true seeker of the spear arts! There are many secrets to learn, but first, you must show me your dedication to the spear. Bring me a fine weapon, and I will teach you the ways of the harpoon.")
    }

    suspend fun fishingRodLearn(it: QueueTask) {
        it.chatPlayer("Are there any ways to use a fishing rod which I might learn?")
        it.chatNpc("Indeed, there are. Our tribe has developed a unique way of fishing that allows us to catch fish that would elude other fishermen. But first, you must prove your worth by catching a mighty leaping fish. Bring it to me, and I shall share our secrets with you.")
    }

    suspend fun firemakingWisdom(it: QueueTask) {
        it.chatPlayer("My mind is ready for your Firemaking wisdom, please",
                                "instruct me.")
        it.chatNpc("The first point in your progression is that of lighting",
                            "fires without a tinderbox.")
        it.chatPlayer("That sounds pretty useful, tell me more.")
        it.chatNpc("For this process you will require a strung bow. You",
                            "use the bow to quickly rotate pieces of wood against one",
                            "another. As you rub the wood becomes hot, eventually",
                            "springing into flame.")
        it.chatPlayer("No more secret details?")
        it.chatNpc("The spirits will aid you, the power they supply will guide",
                            "your hands. Go and benefit from their guidance upon",
                            "an oaken log.")
        val option3 = it.options(
            "I seek more answers.",
            "I have no more questions at this time."
        )
        when (option3) {
            1 -> iSeekMoreAnswers(it)
            2 -> iHaveNoMoreQuestionsAtThisTime(it)
        }
    }

    suspend fun iHaveNoMoreQuestionsAtThisTime(it: QueueTask) {
        it.chatPlayer("I have no more questions at this time.")
        it.chatNpc("I see you have free space and no record of the tasks I",
                            "have set you. Please take this book as a record of your",
                            "progress - between the spirits and your diligence I",
                            "expect it will always be up to date.")
        if(!it.player.hasItem(Items.BARBARIAN_SKILLS) && !it.player.inventory.isFull) {
            it.player.inventory.add(Items.BARBARIAN_SKILLS)
        }
    }

    suspend fun iSeekMoreAnswers(it: QueueTask) {
        it.chatPlayer("I seek more answers.")
        val option2 = it.options(
            "Please, supply me details of your cunning with harpoons.",
            "Are there any ways to use a fishing rod which I might learn?",
            "My mind is ready for your Firemaking wisdom, please instruct me.",
            "Who are you?"
        )
        when (option2) {
            1 -> detailsCunningHarpoons(it)
            2 -> fishingRodLearn(it)
            3 -> firemakingWisdom(it)
            4 -> whoAreYou(it)
        }
    }

    suspend fun whoAreYou(it: QueueTask) {
        it.chatPlayer("Who are you?")
        it.chatNpc("I am Otto Godblessed, the last of the Godblessed line. I am the keeper of the ancient knowledge of my ancestors, and I am here to share it with those who prove themselves worthy.")
    }