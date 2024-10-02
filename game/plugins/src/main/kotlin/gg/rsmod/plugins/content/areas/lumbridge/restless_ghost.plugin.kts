package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.TheRestlessGhost

/**
 * @author Tank <Tank#4733>
 */

val theRestLessGhost = TheRestlessGhost

on_npc_option(Npcs.RESTLESS_GHOST, option = "talk-to") {
    player.queue {
        chatPlayer("Hello, ghost, how are you?")
        when (player.getCurrentStage(quest = theRestLessGhost)) {
            2 -> talkingToGhost(this)
            3 -> findingSkull(this)
        }
    }
}

suspend fun talkingToGhost(it: QueueTask) {
    if (it.player.hasEquipped(EquipmentType.AMULET, Items.GHOSTSPEAK_AMULET)) {
        it.chatNpc("Not very good actually.", npc = Npcs.RESTLESS_GHOST, facialExpression = FacialExpression.SAD)
        it.chatPlayer("What's the problem?")
        it.chatNpc(
            "Did you understand what I just said?",
            npc = Npcs.RESTLESS_GHOST,
            facialExpression = FacialExpression.CONFUSED,
        )
        when (
            it.options(
                "Yep. Now, tell me what the problem is.",
                "No, you sound like you're talking nonsense to me.",
                "Wow, this amulet works!",
            )
        ) {
            1 -> { // Yep. Now, tell me what the problem is.
                it.chatPlayer("Yep. Now, tell me what the problem is.")
                it.chatNpc("Wow! This is incredible! I didn't expect anyone to ever", "understand me!")
                it.chatPlayer("Okay, okay, I can understand you.", facialExpression = FacialExpression.ANNOYED)
                it.chatPlayer(
                    "But have you any idea why you're doomed to be a ghost?",
                    facialExpression = FacialExpression.CONFUSED,
                )
                it.chatNpc("Well, to be honest, I'm not sure.")
                it.chatNpc("I should think it's because I've lost my head")
                it.chatPlayer("What? I can see your head perfectly fine. Well, see", "through it at least.")
                it.chatNpc(
                    "No, no, I mean my REAL body. If you look in",
                    "my coffin you'll see my corpse is without its skull. Last",
                    "think I remember was being attacked by a warlock while",
                    "I was mining.",
                )
                it.chatNpc("It was at the mine just south of this", "graveyard.")
                it.chatPlayer("Okay. I'll try to get your skull back for you", "so you can rest in peace.")
                it.player.advanceToNextStage(theRestLessGhost)
            }

            2 -> { // No, you sound like you're talking nonsense to me.
                it.chatNpc("Oh, that's a pity. You got my hopes up there.")
                it.chatPlayer("Yeah, it is a pity. Sorry about that.")
                it.chatNpc("Hang on a second - you can understand me!")
                when (it.options("No, I can't.", "Yep, clever, aren't I?")) {
                    1 -> { // No, I can't.
                        it.chatPlayer("No, I can't.", facialExpression = FacialExpression.MEAN_FACE)
                        it.chatNpc("Great.")
                        it.chatNpc("The first person I can talk to in ages...")
                        it.chatNpc("...and they're a moron.")
                    }

                    2 -> { // Yep, clever, aren't I?
                        it.chatNpc(
                            "I'm impressed. You must be very powerful. I don't suppose",
                            "you can stop me from being a ghost?",
                        )
                        when (it.options("Okay, but do you know why you're a ghost?", "No, you're scary!")) {
                            1 -> { // Okay, but do you know why you're a ghost?
                                it.chatPlayer("Okay, but do you know why you're a ghost?")
                                it.chatNpc("Nope. I just know I can't do much of anything like this.")
                                it.chatNpc("I should think it's because I've lost my head.")
                                it.chatPlayer(
                                    "What? I can see your head perfectly fine. Well, see",
                                    "through it at least.",
                                )
                                it.chatNpc(
                                    "No, no, I mean my REAL body. If you look in",
                                    "my coffin you'll see my corpse is without its skull. Last",
                                    "think I remember was being attacked by a warlock while",
                                    "I was mining.",
                                )
                                it.chatNpc("It was at the mine just south of this", "graveyard.")
                                it.chatPlayer(
                                    "Okay. I'll try to get your skull back for you",
                                    "so you can rest in peace.",
                                )
                                it.player.advanceToNextStage(theRestLessGhost)
                            }
                            2 -> { // No, you're scary!
                                it.chatPlayer("No, you're scary!", facialExpression = FacialExpression.AFRAID)
                                it.chatNpc("Great.")
                                it.chatNpc("The first person I can talk to in ages...")
                                it.chatNpc("...and they're an idiot.")
                            }
                        }
                    }
                }
            }

            3 -> { // Wow, this amulet works!
                it.chatNpc(
                    "Oh! It's your amulet that's doing it. I did wonder. I don't",
                    "suppose you can help me? I don't like being a ghost.",
                )
                when (it.options("Yes, okay. Do you know why you're a ghost?", "No, you're scary!")) {
                    1 -> { // Yes, okay. Do you know why you're a ghost?
                        it.chatPlayer("Yes, okay. Do you know why you're a ghost?")
                        it.chatNpc("Nope. I just know I can't do much of anything like this.")
                        it.chatNpc("I should think it's because I've lost my head")
                        it.chatPlayer("What? I can see your head perfectly fine. Well, see", "through it at least.")
                        it.chatNpc(
                            "No, no, I mean my REAL body. If you look in",
                            "my coffin you'll see my corpse is without its skull. Last",
                            "think I remember was being attacked by a warlock while",
                            "I was mining.",
                        )
                        it.chatNpc("It was at the mine just south of this", "graveyard")
                        it.chatPlayer("Okay. I'll try to get your skull back for you", "so you can rest in peace.")
                        it.player.advanceToNextStage(theRestLessGhost)
                    }
                    2 -> { // No, you're scary!
                        it.chatPlayer("No, you're scary!", facialExpression = FacialExpression.AFRAID)
                        it.chatNpc("Great.")
                        it.chatNpc("The first person I can talk to in ages...")
                        it.chatNpc("...and they're an idiot.")
                    }
                }
            }
        }
    } else if (it.player.inventory.contains(Items.GHOSTSPEAK_AMULET)) {
        it.chatNpc("Wooo wooo wooooo!")
        it.chatPlayer("Why can't I understand you? Oh, yeah, it might help if I", "wear this amulet!")
    } else if (!it.player.hasEquipped(EquipmentType.AMULET, Items.GHOSTSPEAK_AMULET)) {
        it.chatNpc("Wooo wooo wooooo!")
        when (
            it.options(
                "Sorry, I don't speak ghost.",
                "Ooh, that's interesting.",
                "Any hints as to where I can find some treasure?",
            )
        ) {
            1 -> {
                it.chatPlayer("Sorry, I don't speak ghost.")
                it.chatNpc("Woo woo?")
                it.chatPlayer("Nope, still don't understand you.")
                it.chatNpc("Woooooo!")
                it.chatPlayer("Never mind.")
            }

            2 -> {
                it.chatNpc("Wooo wooo Woooooooooooooooooo!")
                when (it.options("Did he really?", "Yeah, that's what I thought.")) {
                    1 -> {
                        it.chatPlayer("Did he really?")
                        it.chatNpc("Woo.")
                        when (it.options("My brother had exactly the same problem.", "Goodbye. Thanks for the chat.")) {
                            1 -> {
                                it.chatNpc("Woo wooooo!")
                                it.chatNpc("Wooooo woo woo woo!")
                                when (
                                    it.options(
                                        "Goodbye. Thanks for the chat.",
                                        "You'll have to give me the recipe some time.",
                                    )
                                ) {
                                    1 -> {
                                        it.chatPlayer("Goodbye. Thanks for the chat.")
                                        it.chatNpc("Wooo wooo.")
                                    }

                                    2 -> {
                                        it.chatPlayer("You'll have to give me the recipe some time.")
                                        it.chatNpc("Wooooooo woo woooooooo.")
                                        when (
                                            it.options(
                                                "Goodbye. Thanks for the chat.",
                                                "Hmm, I'm not sure about that.",
                                            )
                                        ) {
                                            1 -> {
                                                it.chatPlayer("Goodbye. Thanks for the chat.")
                                                it.chatNpc("Wooo wooo.")
                                            }

                                            2 -> {
                                                it.chatPlayer("Hmm, I'm not sure about that.")
                                                it.chatNpc("Wooo woo?")
                                                it.chatPlayer("Well, if you insist.")
                                                it.chatNpc("Wooooooooo!")
                                                it.chatPlayer("Ah, well, better be off now.")
                                                it.chatNpc("Woo.")
                                                it.chatPlayer("Bye.")
                                            }
                                        }
                                    }
                                }
                            }

                            2 -> {
                                it.chatPlayer("Goodbye. Thanks for the chat.")
                                it.chatNpc("Wooo wooo.")
                            }
                        }
                    }

                    2 -> {
                        it.chatPlayer("Yeah, that's what I thought.")
                        it.chatNpc("Wooo woooooooooooooo...")
                        when (it.options("Goodbye. Thanks for the chat.", "Hmm, I'm not sure about that.")) {
                            1 -> {
                                it.chatPlayer("Goodbye. Thanks for the chat.")
                                it.chatNpc("Wooo wooo.")
                            }

                            2 -> {
                                it.chatPlayer("Hmm, I'm not sure about that.")
                                it.chatNpc("Wooo woo?")
                                it.chatPlayer("Well, if you insist.")
                                it.chatNpc("Wooooooooo!")
                                it.chatPlayer("Ah, well, better be off now.")
                                it.chatNpc("Woo.")
                                it.chatPlayer("Bye.")
                            }
                        }
                    }
                }
            }

            3 -> {
                it.chatNpc(
                    "Wooooooo woo! Woooooo wooo wooooo woowoowoo woo",
                    "Woo",
                    "woo. Wooooo woo woo? Woooooooooooooooooo!",
                )
                when (it.options("Sorry, I don't speak ghost.", "Thank you. You've been very helpful.")) {
                    1 -> {
                        it.chatPlayer("Sorry, I don't speak ghost.")
                        it.chatNpc("Woo woo?")
                        it.chatPlayer("Nope, still don't understand you.")
                        it.chatNpc("Woooooo!")
                        it.chatPlayer("Never mind.")
                    }

                    2 -> {
                        it.chatPlayer("Thank you. You've been very helpful.")
                        it.chatNpc("Wooooooo.")
                    }
                }
            }
        }
    }
}

suspend fun findingSkull(it: QueueTask) {
    it.chatNpc("How are you doing with finding my skull?")
    if (!it.player.inventory.contains(Items.MUDDY_SKULL)) {
        it.chatPlayer("Sorry I can't find it at the moment", facialExpression = FacialExpression.SAD)
        it.chatNpc("Ah, well. Keep on looking.")
        it.chatNpc(
            "I'm pretty sure it's somewhere near the mining spot south",
            "of here. I really hope it's still there somewhere.",
        )
    } else {
        it.chatPlayer("I found it!", facialExpression = FacialExpression.HAPPY)
        it.chatNpc(
            "Hurrah! Now I can stop being a ghost! You just need to put",
            "it in my coffin there, then I'll be free!",
        )
    }
}
