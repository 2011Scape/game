package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer

on_npc_option(Npcs.MISS_SCHISM, "Talk-to") {
    player.queue {
        chatNpc("Oooh, my dear, have you heard the news?", facialExpression = FacialExpression.CONFUSED)
        when (options(
            "Ok, tell me about the news.",
            "Who are you?",
            "I'm not talking to you, you horrible woman."
        )) {
            FIRST_OPTION -> newsDialogue(this)
            SECOND_OPTION -> whoAreYouDialogue(this)
            THIRD_OPTION -> notTalkingDialogue(this)
        }
    }
}

suspend fun newsDialogue(it: QueueTask) {
    it.chatPlayer("Ok, tell me about the news.")
    it.chatNpc("Well, there's just too much to tell at once! What would you like to hear first: the vampire or the " +
        "bank?", wrap = true)
    when (it.options(
        "Tell me about the vampire.",
        "Tell me about the bank.",
    )) {
        FIRST_OPTION -> {
            if (it.player.finishedQuest(VampyreSlayer)) {
                it.chatNpc("Well, there's nothing to tell NOW. You killed it.", facialExpression = FacialExpression
                    .ANNOYED, wrap = true)
                it.chatPlayer("You could sound a little grateful.")
                it.chatNpc("I'm sure I could, but I don't see why. The vampire wasn't bothering me.",
                    facialExpression = FacialExpression.ANNOYED, wrap = true)
                it.chatPlayer("...", facialExpression = FacialExpression.ANNOYED)
            }
            else {
                it.chatNpc("It's terrible, absolutely terrible! Those poor people!", facialExpression =
                    FacialExpression.SHOCK, wrap =
                    true)
                it.chatPlayer("What is terrible?")
                it.chatNpc("Why, the attacks!", facialExpression = FacialExpression.SHOCK)
                it.chatPlayer("A vampire is attacking people?")
                it.chatNpc("Yes, haven't you been listening?", facialExpression = FacialExpression.ANNOYED, wrap = true)
                it.chatNpc("And no one is lifting a finger to do anything about it. Well, that new couple, Morgan and" +
                    " Maris, they think we should find a vampire slayer, but I'm not sure I trust them.",
                    facialExpression = FacialExpression.SUSPICIOUS, wrap = true)
                it.chatNpc("They aren't local.", facialExpression = FacialExpression.SUSPICIOUS, wrap = true)
            }
        }
        SECOND_OPTION -> {
            it.chatPlayer("What about the bank?")
            it.chatNpc("It's terrible, absolutely terrible! Those poor people!", facialExpression =
                FacialExpression.SHOCK, wrap = true)
            it.chatPlayer("Ok, yeah.")
            it.chatNpc("And who'd have ever thought such a sweet old gentleman would do such a thing?",
                facialExpression = FacialExpression.CONFUSED, wrap = true)
            // TODO Change below dialogue after draynor bank robbery cutscene is added in
            it.chatPlayer("I really don't know what you're talking about.", wrap = true)
            it.chatNpc("Oooh, my dear, had you not heard?", facialExpression = FacialExpression.CONFUSED)
            it.chatPlayer("At this rate I don't think I want to know...", wrap = true)
            it.chatNpc("Oh, you must quickly go and speak to the bank guard outside the bank. He'll tell you all " +
                "about it, oooh, such a shock it was...", facialExpression = FacialExpression.SHOCK, wrap = true)
            it.chatPlayer("...")
        }
    }
}

suspend fun whoAreYouDialogue(it: QueueTask) {
    it.chatNpc(("I, my dear, am a concerned citizen of Draynor Village. Ever since the Council allowed those farmers " +
        "to set up their stalls here, we've had a constant flow of thieves and murderers through our fair village,"), wrap = true)
    it.chatNpc("and I decided that someone HAD to stand up and keep an eye on the situation.", wrap = true)
    it.chatNpc("I also do voluntary work for the Draynor Manor Restoration Fund. We're campaigning to have Draynor " +
        "Manor turned into a museum before the wet-rot destroys it completely.", wrap = true)
    if (!it.player.finishedQuest(VampyreSlayer)) {
        it.chatPlayer("Right...", facialExpression = FacialExpression.SUSPICIOUS)
    }
    else {
        it.chatPlayer("Well now that I've cleared the vampire out of the manor, I guess you wont have too much " +
            "trouble turning it into a mueseum.", wrap = true)
        it.chatNpc("That's all very well dear, but no vampire was ever going to stop me making it a museum.",
            facialExpression = FacialExpression.ANNOYED, wrap = true)
    }
}

suspend fun notTalkingDialogue(it: QueueTask) {
    it.chatNpc("Oooh.", facialExpression = FacialExpression.REALLY_SAD)
}
