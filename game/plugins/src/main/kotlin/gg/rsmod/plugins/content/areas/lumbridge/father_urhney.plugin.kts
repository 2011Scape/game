package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.TheRestlessGhost


val theRestLessGhost = TheRestlessGhost

on_npc_option(Npcs.FATHER_URHNEY, option = "talk-to") {
    player.queue {
        chatNpc("Go away! I'm meditating!", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.ANGRY)
        when (player.getCurrentStage(theRestLessGhost)) {
            0 -> preQuest(this)
            1 -> duringRestlessGhost(this)
            2, 3 -> talkingToGhost(this)
            else -> postQuest(this)
        }
    }
}

suspend fun preQuest(it: QueueTask) {
    when (it.options("Well, that's friendly.", "I've come to repossess your house.")) {
        1 -> {
            it.chatPlayer("Well, that's friendly.", facialExpression = FacialExpression.SNOBBY)
            it.chatNpc("I said go away!")
            it.chatPlayer("Okay, okay. Sheesh, what a grouch.")
        }

        2 -> {
            it.chatPlayer("I've come to repossess your house.", facialExpression = FacialExpression.TOUGH)
            it.chatNpc("On what grounds?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.SHOCK)
            when (
                it.options(
                    "Repeated failure to make mortgage repayments.",
                    "I don't know, I just wanted this house.",
                )
            ) {
                1 -> {
                    it.chatNpc("What?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.ANGRY)
                    it.chatNpc(
                        "But I don't have a mortgage - I built this house myself!",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer(
                        "Sorry, I must have the wrong address",
                        "All the houses here look the same around here.",
                    )
                    it.chatNpc(
                        "What? What houses? This is the only one.",
                        "What are you talking about?",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer("Never mind.")
                }

                2 -> {
                    it.chatPlayer("I don't know, I just wanted this house.")
                    it.chatNpc(
                        "Oh, go away and stop wasting my time.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                }
            }
        }
    }
}

suspend fun duringRestlessGhost(it: QueueTask) {
    when (
        it.options(
            "Well, that's friendly.",
            "Father Aereck sent me to talk to you.",
            "I've come to repossess your house.",
        )
    ) {
        1 -> {
            it.chatPlayer("Well, that's friendly", facialExpression = FacialExpression.NORMAL)
            it.chatNpc("I said go away!")
            it.chatPlayer("Okay, okay. Sheesh, what a grouch.")
        }

        2 -> {
            it.chatPlayer("Father Aereck sent me to talk to you.")
            it.chatNpc(
                "I suppose I'd better talk to you then. What has he got",
                "himself into this time?",
                npc = Npcs.FATHER_URHNEY,
                facialExpression = FacialExpression.ANGRY,
            )
            when (it.options("A ghost is haunting his graveyard.", "You mean he gets into lots of problems?")) {
                1 -> {
                    it.chatPlayer("A ghost is haunting his graveyard.")
                    it.chatNpc(
                        "Oh, the silly fool.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatNpc(
                        "I leave town for 5 months and he's already having",
                        "problems.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatNpc("*sigh*", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.REALLY_SAD)
                    it.chatNpc(
                        "Well I can't go back to exorcise it - I vowed not to leave",
                        "this place until I've spent a full two years praying and",
                        "meditating.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatNpc("I'll tell you what I can do, though - take this amulet.")
                    it.itemMessageBox("Father Urhney gives you an amulet.", item = Items.GHOSTSPEAK_AMULET)
                    it.player.inventory.add(Items.GHOSTSPEAK_AMULET)
                    it.chatNpc("It's a ghostspeak amulet.")
                    it.chatNpc(
                        "It's called that because, when you wear it, you can speak",
                        "to ghosts. Many ghosts are doomed to remain in this world",
                        "because they have some important task left uncompleted.",
                    )
                    it.chatNpc(
                        "If you know what the task is, you can get rid of the",
                        "ghost. I'm not making any guarantees, mind you, but it's",
                        "the best I can do right now.",
                    )
                    it.chatPlayer("Thank you. I'll give it a try.")
                    it.player.advanceToNextStage(theRestLessGhost)
                }

                2 -> {
                    it.chatPlayer("You mean he gets into lots of problems?")
                    it.chatNpc("Yeah. Even when we were trainee priests he kept getting", "stuck up bell ropes.")
                    it.chatNpc(
                        "Anyway, I don't have time for chit-chat. What's his",
                        "problem this time?",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer("A ghost is haunting his graveyard.")
                    it.chatNpc(
                        "Oh, the silly fool.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatNpc(
                        "I leave town for 5 months and he's already having",
                        "problems.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatNpc("*sigh*", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.REALLY_SAD)
                    it.chatNpc(
                        "Well I can't go back to exorcise it - I vowed not to leave",
                        "this place until I've spent a full two years praying and",
                        "meditating.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatNpc("I'll tell you what I can do, though - take this amulet.")
                    it.itemMessageBox("Father Urhney gives you an amulet.", item = Items.GHOSTSPEAK_AMULET)
                    it.player.inventory.add(Items.GHOSTSPEAK_AMULET)
                    it.chatNpc("It's a ghostspeak amulet.")
                    it.chatNpc(
                        "It's called that because, when you wear it, you can speak",
                        "to ghosts. Many ghosts are doomed to remain in this world",
                        "because they have some important task left uncompleted.",
                    )
                    it.chatNpc(
                        "If you know what the task is, you can get rid of the",
                        "ghost. I'm not making any guarantees, mind you, but it's",
                        "the best I can do right now.",
                    )
                    it.chatPlayer("Thank you. I'll give it a try.")
                    it.player.advanceToNextStage(theRestLessGhost)
                }
            }
        }

        3 -> {
            it.chatPlayer("I've come to repossess your house.", facialExpression = FacialExpression.TOUGH)
            it.chatNpc("On what grounds?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.CONFUSED)
            when (
                it.options(
                    "Repeated failure to make mortgage repayments.",
                    "I don't know, I just wanted this house.",
                )
            ) {
                1 -> {
                    it.chatNpc("What?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.ANGRY)
                    it.chatNpc(
                        "But I don't have a mortgage - I built this house myself!",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer(
                        "Sorry, I must have the wrong address",
                        "All the houses here look the same around here.",
                    )
                    it.chatNpc(
                        "What? What houses? This is the only one.",
                        "What are you talking about?",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer("Never mind.")
                }

                2 -> {
                    it.chatNpc(
                        "Oh, go away and stop wasting my time.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                }
            }
        }
    }
}

suspend fun talkingToGhost(it: QueueTask) {
    when (
        it.options(
            "Well, that's friendly.",
            "I've come to repossess your house.",
            "I've lost that ghostspeak amulet you gave me.",
        )
    ) {
        1 -> {
            it.chatPlayer("Well, that's friendly", facialExpression = FacialExpression.SNOBBY)
            it.chatNpc("I said go away!")
            it.chatPlayer("Okay, okay. Sheesh, what a grouch.", facialExpression = FacialExpression.ANGRY)
        }

        2 -> {
            it.chatPlayer("I've come to repossess your house.", facialExpression = FacialExpression.TOUGH)
            it.chatNpc("On what grounds?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.SHOCK)
            when (
                it.options(
                    "Repeated failure to make mortgage repayments.",
                    "I don't know, I just wanted this house.",
                )
            ) {
                1 -> {
                    it.chatNpc("What?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.ANGRY)
                    it.chatNpc(
                        "But I don't have a mortgage - I built this house myself!",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer(
                        "Sorry, I must have the wrong address",
                        "All the houses here look the same around here.",
                    )
                    it.chatNpc(
                        "What? What houses? This is the only one.",
                        "What are you talking about?",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer("Never mind.")
                }

                2 -> {
                    it.chatNpc(
                        "Oh, go away and stop wasting my time.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                }
            }
        }

        3 -> {
            it.chatPlayer("I've lost that ghostspeak amulet you gave me.", facialExpression = FacialExpression.SAD)
            it.messageBox("Father Urhney sighs.")
            if (it.player.inventory.contains(Items.GHOSTSPEAK_AMULET) ||
                it.player.hasEquipped(
                    EquipmentType.AMULET,
                    Items.GHOSTSPEAK_AMULET,
                )
            ) {
                it.chatNpc(
                    "What are you talking about? I can see you've got",
                    "it with you.",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
            } else if (it.player.bank.contains(Items.GHOSTSPEAK_AMULET)) {
                it.chatNpc(
                    "Why do you insist on wasting my time? Has it even",
                    "occurred to you to look in your bank? Now go away!",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
            } else if (!it.player.inventory.contains(Items.GHOSTSPEAK_AMULET) ||
                !it.player.hasEquipped(
                    EquipmentType.AMULET,
                    Items.GHOSTSPEAK_AMULET,
                )
            ) {
                it.chatNpc(
                    "How careless can you get? Those things aren't easy to",
                    "Come by, you know! It's a good job I've got a spare.",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
                it.itemMessageBox("Father Urhney hands you an amulet.", item = Items.GHOSTSPEAK_AMULET)
                it.player.inventory.add(Items.GHOSTSPEAK_AMULET)
                it.chatNpc(
                    "Be more careful this time.",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
                it.chatPlayer("Okay, I'll try to be.")
            }
        }
    }
}

suspend fun postQuest(it: QueueTask) {
    when (
        it.options(
            "Well, that's friendly.",
            "I've come to repossess your house.",
            "I've lost that ghostspeak amulet you gave me.",
        )
    ) {
        1 -> {
            it.chatPlayer("Well, that's friendly", facialExpression = FacialExpression.SNOBBY)
            it.chatNpc("I said go away!")
            it.chatPlayer("Okay, okay. Sheesh, what a grouch.", facialExpression = FacialExpression.ANGRY)
        }

        2 -> {
            it.chatPlayer("I've come to repossess your house.", facialExpression = FacialExpression.TOUGH)
            it.chatNpc("On what grounds?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.SHOCK)
            when (
                it.options(
                    "Repeated failure to make mortgage repayments.",
                    "I don't know, I just wanted this house.",
                )
            ) {
                1 -> {
                    it.chatNpc("What?", npc = Npcs.FATHER_URHNEY, facialExpression = FacialExpression.ANGRY)
                    it.chatNpc(
                        "But I don't have a mortgage - I built this house myself!",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer(
                        "Sorry, I must have the wrong address",
                        "All the houses here look the same around here.",
                    )
                    it.chatNpc(
                        "What? What houses? This is the only one.",
                        "What are you talking about?",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                    it.chatPlayer("Never mind.")
                }

                2 -> {
                    it.chatNpc(
                        "Oh, go away and stop wasting my time.",
                        npc = Npcs.FATHER_URHNEY,
                        facialExpression = FacialExpression.ANGRY,
                    )
                }
            }
        }

        3 -> {
            it.chatPlayer("I've lost that ghostspeak amulet you gave me.", facialExpression = FacialExpression.SAD)
            it.messageBox("Father Urhney sighs.")
            if (it.player.inventory.contains(Items.GHOSTSPEAK_AMULET) ||
                it.player.hasEquipped(
                    EquipmentType.AMULET,
                    Items.GHOSTSPEAK_AMULET,
                )
            ) {
                it.chatNpc(
                    "What are you talking about? I can see you've got",
                    "it with you.",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
            } else if (it.player.bank.contains(Items.GHOSTSPEAK_AMULET)) {
                it.chatNpc(
                    "Why do you insist on wasting my time? Has it even",
                    "occurred to you to look in your bank? Now go away!",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
            } else if (!it.player.inventory.contains(Items.GHOSTSPEAK_AMULET) ||
                !it.player.hasEquipped(
                    EquipmentType.AMULET,
                    Items.GHOSTSPEAK_AMULET,
                )
            ) {
                it.chatNpc(
                    "How careless can you get? Those things aren't easy to",
                    "Come by, you know! It's a good job I've got a spare.",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
                it.itemMessageBox("Father Urhney hands you an amulet.", item = Items.GHOSTSPEAK_AMULET)
                it.player.inventory.add(Items.GHOSTSPEAK_AMULET)
                it.chatNpc(
                    "Be more careful this time.",
                    npc = Npcs.FATHER_URHNEY,
                    facialExpression = FacialExpression.ANGRY,
                )
                it.chatPlayer("Okay, I'll try to be.")
            }
        }
    }
}
