package gg.rsmod.plugins.content.areas.varrock

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PriestInPeril
import gg.rsmod.plugins.content.quests.startQuest
import gg.rsmod.plugins.content.quests.startedQuest

val priestInPeril = PriestInPeril

on_npc_option(npc = Npcs.KING_ROALD, option = "talk-to") {
    player.queue {
        if (player.startedQuest(PriestInPeril)) {
            when (player.getCurrentStage(PriestInPeril)) {
                1 -> kingRoaldDialogueAfterAcceptingQuest(this)
                2 -> kingRoaldDialogueAfterAcceptingQuestButBeforeKillingTheDog(this)
                3 -> kingRoaldDialogueAfterKillingTheDog(this)
                else -> Nothing(this)
            }
        } else {
            kingRoaldDialogue(this)
        }
    }
}

suspend fun kingRoaldDialogue(it: QueueTask) {
    it.chatPlayer("Greetings, your majesty.")
    it.chatNpc("Well hello there. What do you want?")
    it.chatPlayer("I am looking for a quest!")
    it.chatNpc(
        "A quest you say? Hmm, what an odd request to make",
        "of the king. It's funny you should mention it though, as",
        "there is something you can do for me.",
    )
    it.chatNpc(
        "Are you aware of the temple east of here? It stands on",
        "the river Salve and guards the entrance to the lands of",
        "Morytania?",
    )
    it.chatPlayer("No, I don't think I know it...", facialExpression = FacialExpression.DISAGREE)
    it.chatNpc(
        "Hmm, how strange that you don't. Well anyway, it has",
        "been some days since last I heard from Drezel, the",
        "priest who lives there.",
    )
    it.chatNpc(
        "Be a sport and go make sure that nothing untoward",
        "has happened to the silly old codger for me, would you?",
    )
    it.options("Sure.", "No, that sounds boring.")
    it.chatPlayer("Sure. I don't have anything better to do right now.")
    it.chatNpc(
        "Many thanks adventurer! I would have sent one of my",
        "squires but they wanted payment for it!",
    )
    it.player.startQuest(PriestInPeril)
}

suspend fun Nothing(it: QueueTask) {
    it.chatPlayer("Greetings, your majesty.")
    it.chatNpc("Well hello there. What do you want?")
    it.chatPlayer("Nothing.")
}

suspend fun kingRoaldDialogueAfterAcceptingQuest(it: QueueTask) {
    it.chatNpc("You have news of Drezel for me?")
    when (
        it.options(
            "Who's Drezel?",
            "Where am I supposed to go again?",
            "Why do you care about Drezel anyway?",
            "Do I get a reward for this?",
        )
    ) {
        1 -> {
            it.chatPlayer("Who's Drezel?")
            it.chatNpc(
                "Drezel is the priest who lives in the Temple to",
                "the east of here. You're supposed to go make sure",
                "nothing's happened to him. Remember?",
            )
            it.chatPlayer("Ooooooooh, THAT Drezel. Yup, I'll go do that then.")
        }

        2 -> {
            it.chatPlayer("Where am I supposed to go again?")
            it.chatNpc(
                "The temple where Drezel lives is but a short journey",
                "east from here. It lies south of the cliffs, on",
                "the source of the river Salve. Don't worry, you can't ",
                "miss it.",
            )
        }

        3 -> {
            it.chatPlayer("Why do you care about Drezel anyway?")
            it.chatNpc(
                "Well, that is a slightly impertinent question to ask of",
                "your King, but I shall overlook it this time. As you are",
                "no doubt aware, this kingdom worships Saradomin, and is",
                "a peaceful place to live and prosper.",
            )
            it.chatNpc(
                "The temple where Drezel lives stands on the Eastern border",
                "of Misthalin, and further East lie the evil lands of Morytania,",
                "a fearful land of undead monsters and Zamorakians. The sacred river",
                "Salve marks a natural border between our kingdoms, and the temple",
                "prevents any invasions to this land from Morytania.",
            )
            it.chatNpc(
                "By keeping the water of the river blessed, our defences",
                "remain strong, as the fiends that inhabit Morytania cannot",
                "cross such a holy barrier. Drezel is the descendant of one",
                "of the original Saradominist priests who first blessed the",
                "river, and built the temple there.",
            )
            it.chatNpc(
                "His job is to ensure nothing happens to the river",
                "at the source that might allow the evil Morytanians",
                "to invade this land. This is the reason why the lack",
                "of communication from himbothers me somewhat, although",
                "I am sure nobody would dare to try and attack our kingdom!",
            )
        }

        4 -> {
            it.chatPlayer("Do I get a reward for this?")
            it.chatNpc(
                "You will be rewarded in the knowledge that you have done the right thing and assisted the King of Misthalin.",
            )
            it.chatPlayer("Soooooo...... that would be a 'no' then?")
            it.chatNpc("That is correct.")
        }
    }
}

suspend fun kingRoaldDialogueAfterAcceptingQuestButBeforeKillingTheDog(it: QueueTask) {
    it.chatPlayer("Greetings, your majesty.")
    it.chatNpc("Well hello there. What do you want?")
    it.chatNpc("You have news of Drezel for me?")
    it.chatPlayer(
        "Well... I went to the temple like you asked me to",
        "... and I spoke to someone inside...",
    )
    it.chatNpc(
        "Ah, well that must have been Drezel then.",
        "What did he say?",
    )
    it.chatPlayer(
        "Well... he seemed to be having some kind of",
        "trouble with a dog...",
    )
    it.chatNpc(
        "I expect you to offer him your full assistance",
        "in whatever he needs.",
    )
    it.chatPlayer("Well okay then.")
}

suspend fun kingRoaldDialogueAfterKillingTheDog(it: QueueTask) {
    it.chatPlayer("Greetings, your majesty.")
    it.chatNpc("Well hello there. What do you want?")
    it.chatNpc("You have news of Drezel for me?")
    it.chatPlayer(
        "Yeah, I spoke to the guys at the temple and they said",
        "they were being bothered by that dog in the crypt, so I",
        "went and killed it for them. No problem.",
    )
    it.chatNpc("YOU DID WHAT???", facialExpression = FacialExpression.DISBELIEF)
    it.chatNpc(
        "Are you mentally deficient??? That guard dog was",
        "protecting the route to Morytania! Without it we could",
        "be in severe peril of attack!",
        facialExpression = FacialExpression.ANGRY,
    )
    it.chatPlayer("Did I make a mistake?", facialExpression = FacialExpression.WORRIED)
    it.chatNpc(
        "YES YOU DID!!!!! You need to get there right now",
        "and find out what is happening! Before it is too late for",
        "us all!",
        facialExpression = FacialExpression.ANGRY,
    )
    it.chatPlayer("B-but Drezel TOLD me to...!", facialExpression = FacialExpression.SAD)
    it.chatNpc(
        "No, you absolute cretin! Obviously some fiend has done",
        "something to Drezel and tricked your feeble intellect",
        "into helping them kill the guard dog!",
        facialExpression = FacialExpression.DISDAIN,
    )
    it.chatNpc(
        "You get back there and do whatever is necessary to",
        "safeguard my kingdom from attack, or I will se you",
        "beheaded for high treason!",
        facialExpression = FacialExpression.ANGRY,
    )
    it.chatPlayer("Y-yes your Highness.", facialExpression = FacialExpression.DEPRESSED)
    it.player.advanceToNextStage(PriestInPeril)
}
