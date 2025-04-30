package gg.rsmod.plugins.content.areas.voidoutpost

import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.game.model.queue.QueueTask

on_npc_option(npc = Npcs.SQUIRE_NOVICE, option = "talk-to") {
    player.queue { squireDialogue(this, Npcs.SQUIRE_NOVICE) }
}

on_npc_option(npc = Npcs.SQUIRE_INTERMEDIATE, option = "talk-to") {
    player.queue { squireDialogue(this, Npcs.SQUIRE_INTERMEDIATE) }
}

on_npc_option(npc = Npcs.SQUIRE_VETERAN, option = "talk-to") {
    player.queue { squireDialogue(this, Npcs.SQUIRE_VETERAN) }
}

suspend fun squireDialogue(task: QueueTask, npcId: Int) {
    val landerType = getLanderType(npcId)
    val combatRequirement = getCombatRequirement(npcId)

    task.chatNpc("Hi, how can I help you?")

    when (task.options(
        "Who are you?",
        "What's going on here?",
        "I'm fine thanks."
    )) {
        1 -> {
            task.chatPlayer("Who are you?")
            task.chatNpc("I'm a Squire for the Void Knights.")
            task.chatPlayer("The who?")
            task.chatNpc(
                "The Void Knights, they are great warriors of balance who",
                "do Guthix's work here in Gielinor."
            )

            when (task.options(
                "Wow, can I join?",
                "What kind of work?",
                "What's 'Gielinor'?",
                "Uh huh, sure."
            )) {
                1 -> {
                    task.chatPlayer("Wow, can I join?")
                    task.chatNpc(
                        "Entry is strictly invite only, however we do need help",
                        "continuing Guthix's work."
                    )

                    when (task.options(
                        "What kind of work?",
                        "Good luck with that."
                    )) {
                        1 -> offerHelpDialogue(task)
                        2 -> task.chatPlayer("Good luck with that.")
                    }
                }
                2 -> {
                    task.chatPlayer("What kind of work?")
                    offerHelpDialogue(task)
                }
                3 -> {
                    task.chatPlayer("What's 'Gielinor'?")
                    task.chatNpc(
                        "It is the name that Guthix gave to this world,",
                        "so we honour him with its use."
                    )
                }
                4 -> task.chatPlayer("Uh huh, sure.")
            }
        }

        2 -> {
            task.chatPlayer("What's going on here?")
            task.chatNpc(
                "This is where we launch our landers to combat the",
                "invasion of the nearby islands. You'll see three landers —",
                "one for novice, intermediate, and veteran adventurers.",
                "Each has a different difficulty, but they offer varying rewards."
            )

            task.chatPlayer("And this lander is...?")
            task.chatNpc("The $landerType.")

            task.chatPlayer("So how do they work?")
            task.chatNpc(
                "Simple. We send a lander out every few minutes,",
                "however we need at least 5 volunteers or it's a suicide",
                "mission. The lander can only hold a maximum of twenty",
                "five people though, so we do send them out early if they"
            )
            task.chatNpc(
                "get full. If you'd be willing to help us then just wait in the",
                "lander and we'll launch it as soon as it's ready. However",
                "you will need a combat level of $combatRequirement to use this lander."
            )

            if (task.player.combatLevel < combatRequirement) {
                task.chatNpc("It looks like you're not strong enough to join this lander yet.")
            }
        }

        3 -> task.chatPlayer("I'm fine thanks.")
    }
}

suspend fun offerHelpDialogue(task: QueueTask) {
    task.chatNpc(
        "Ah well you see we try to keep Gielinor as Guthix intended,",
        "it's very challenging. Actually we've been having some",
        "problems recently, maybe you could help us?"
    )

    when (task.options(
        "What's the problem?",
        "What's 'Gielinor'?",
        "I'd rather not, sorry."
    )) {
        1 -> {
            task.chatPlayer("What's the problem?")
            task.chatNpc(
                "Well the order has become quite diminished over the",
                "years, it's a very long process to learn the skills of a Void",
                "Knight. Recently there have been breaches into our realm",
                "from somewhere else, and strange creatures have been"
            )
            task.chatNpc(
                "pouring through. We can't let that happen, and we'd be",
                "very grateful if you'd help us."
            )

            when (task.options(
                "How can I help?",
                "Sorry, but I can't."
            )) {
                1 -> {
                    task.chatPlayer("How can I help?")
                    task.chatNpc(
                        "We send launchers from our outpost to the nearby islands.",
                        "If you go and wait in the lander there, that'd really help."
                    )
                }
                2 -> task.chatPlayer("Sorry, but I can't.")
            }
        }

        2 -> {
            task.chatPlayer("What's 'Gielinor'?")
            task.chatNpc(
                "It is the name that Guthix gave to this world,",
                "so we honour him with its use."
            )
        }

        3 -> task.chatPlayer("I'd rather not, sorry.")
    }
}

fun getLanderType(npcId: Int): String = when (npcId) {
    Npcs.SQUIRE_NOVICE -> "novice"
    Npcs.SQUIRE_INTERMEDIATE -> "intermediate"
    Npcs.SQUIRE_VETERAN -> "veteran"
    else -> "unknown"
}

fun getCombatRequirement(npcId: Int): Int = when (npcId) {
    Npcs.SQUIRE_NOVICE -> 40
    Npcs.SQUIRE_INTERMEDIATE -> 70
    Npcs.SQUIRE_VETERAN -> 100
    else -> 0
}
