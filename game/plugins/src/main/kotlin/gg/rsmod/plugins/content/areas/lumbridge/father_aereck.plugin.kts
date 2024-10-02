package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.TheRestlessGhost
import gg.rsmod.plugins.content.quests.startQuest

val theRestLessGhost = TheRestlessGhost

on_npc_option(Npcs.FATHER_AERECK, option = "talk-to") {
    player.queue {

        when (player.getCurrentStage(theRestLessGhost)) {
            0 -> preQuest(this)
            1 -> duringRestlessGhost(this)
            2 -> talkingToGhost(this)
            3 -> findingSkull(this)
            else -> postQuest(this)
        }
    }
}

suspend fun preQuest(it: QueueTask) {
    it.chatNpc("Welcome to the church of Saradomin.")
    when (it.options("Who's Saradomin?", "Nice place you've got here.", "I'm looking for a quest.")) {
        1 -> {
            it.chatPlayer("Who's Saradomin?")
            it.chatNpc("Surely you've heard of the god, Saradomin?")
            it.chatNpc(
                "He who creates the forces of goodness and purity in",
                "this world? I cannot believe your ignorance!",
            )
            it.chatNpc("This is the god with more followers than any other! ...At", "least in this part of the world.")
            it.chatNpc("He who created this world along with his brothers", "Guthix and Zamorak?")
            when (it.options("Oh, THAT Saradomin...", "Oh Sorry. I'm not from this world.")) {
                1 -> {
                    it.chatPlayer("Oh, THAT Saradomin...")
                    it.chatNpc("There... is only one Saradomin...")
                    it.chatPlayer("Yeah... I, uh thought you said something else.")
                }

                2 -> {
                    it.chatPlayer("Oh Sorry. I'm not from this world.")
                    it.chatNpc("...")
                    it.chatNpc("That's... strange.")
                    it.chatNpc("I thought things not from this world were all... You", "know. Slimes and tentacles.")
                    when (
                        it.options(
                            "You don't understand. This is an online game!",
                            "I am - do you like my disguise?",
                        )
                    ) {
                        1 -> {
                            it.chatPlayer("You don't understand. This is an online game!")
                            it.chatNpc("I... beg your pardon?")
                            it.chatPlayer("Never mind.")
                        }

                        2 -> {
                            it.chatPlayer("I am - do you like my disguise?")
                            it.chatNpc(
                                "Aargh! Avaunt foul creature from another dimension!",
                                "Avaunt! Begone in the name of Saradomin!",
                            )
                            it.chatPlayer("Ok, ok, I was only joking...")
                        }
                    }
                }
            }
        }

        2 -> {
            it.chatPlayer("Nice place you've got here.")
            it.chatNpc("It is, isn't it? It was built over 230 years ago.")
        }

        3 -> {
            it.chatPlayer("I'm looking for a quest.")
            it.chatNpc("That's lucky, I need someone to do a quest for me.")
            when (it.options("Okay, let me help then.", "Sorry, I don't have the time right now.")) {
                1 -> {
                    it.player.startQuest(theRestLessGhost)
                    it.chatPlayer("Okay, let me help then.")
                    it.chatNpc(
                        "Thank you. The problem is, there's a ghost in the",
                        "graveyard crypt just south of this church. I would like",
                        "you to get rid of it.",
                    )
                    it.chatNpc("You'll need the help of my friend, Father Urhney, who", "is a bit of a ghost expert.")
                    it.chatNpc(
                        "He's currently living in a little shack in the south of",
                        "Lumbridge Swamps, near the coast.",
                    )
                    it.chatNpc("My name is Father Aereck, by the way. Pleased to", "meet you.")
                    it.chatPlayer("Likewise.")
                    it.chatNpc(
                        "Take care travelling through the swamps. To get there",
                        "just follow the path south, through the graveyard.",
                    )
                    it.chatPlayer("I will, thanks")
                }

                2 -> {
                    it.chatPlayer("Sorry, I don't have the time right now.")
                    it.chatNpc("Oh well. If you do have some spare time on your", "hands, come back and talk to me.")
                }
            }
        }
    }
}

suspend fun duringRestlessGhost(it: QueueTask) {
    it.chatNpc("Have you gotten rid of the ghost as yet?")
    it.chatPlayer("How was I supposed to do that?")
    it.chatNpc("Well, you should go find Father Uhrney", "as he knows all about ghosts.")
    it.chatNpc("You can get to the swamp he lives in", "by going south through the cemetery.")
    it.chatNpc(
        "You'll have to head south through the swamp - stick",
        "to the coastline and you'll find his house in no time.",
    )
}

suspend fun talkingToGhost(it: QueueTask) {
    it.chatNpc("Have you gotten rid of the ghost as yet?")
    it.chatPlayer("I had a talk with Father Urhney. He has given me a funny", "looking amulet for talking to ghosts.")
    it.chatNpc(
        "I always wondered what the amulet was. Well, I hope it's",
        "useful. Tell me when you get rid of that ghost.",
    )
}

suspend fun findingSkull(it: QueueTask) {
    it.chatNpc("Have you gotten rid of the ghost as yet?")
    if (!it.player.inventory.contains(Items.MUDDY_SKULL)) {
        it.chatPlayer(
            "I've found out that the ghost's corpse has lost it's skull. If",
            "I can find the skull, the ghost should depart.",
        )
        it.chatNpc("That would explain it.")
        it.chatNpc("Hmm. Well, I haven't seen any skulls.")
        it.chatNpc("I think a warlock has stolen it.")
        it.chatNpc("I hate warlocks", npc = Npcs.FATHER_AERECK, facialExpression = FacialExpression.ANGRY)
        it.chatNpc("Ah, well, good luck", npc = Npcs.FATHER_AERECK, facialExpression = FacialExpression.CHEERFUL)
    } else {
        it.chatPlayer("I finally found the ghost's skull", facialExpression = FacialExpression.CHEERFUL)
        it.chatNpc("Great! Put it in the ghost's coffin and see what happens.")
    }
}

suspend fun postQuest(it: QueueTask) {
    it.chatNpc("You've gotten rid of the ghost, haven't you?")
    it.chatPlayer("Yes, I have.")
    it.chatNpc("Thank you so much. I don't have much money, so I can't pay you.")
}
