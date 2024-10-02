package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.LostCity
import gg.rsmod.plugins.content.quests.startQuest

val lostCity = LostCity

on_npc_option(npc = Npcs.WARRIOR, option = "talk-to") {
    player.queue {
        when (player.getCurrentStage(lostCity)) {
            LostCity.NOT_STARTED -> beforeLostCity(this)
            LostCity.FINDING_SHAMUS -> findingShamus(this)
            LostCity.FOUND_SHAMUS -> afterShamus(this)
            LostCity.ENTRANA_DUNGEON, LostCity.CUT_DRAMEN_TREE, LostCity.CREATE_DRAMEN_BRANCH -> afterShamus(this)
            LostCity.QUEST_COMPLETE -> afterLostCity(this)
        }
    }
}

suspend fun beforeLostCity(it: QueueTask) {
    it.chatNpc("Hello there, traveler", facialExpression = FacialExpression.CALM_TALK)
    when (it.options("What are you camped out here for?", "Do you know any good adventures I can go on?")) {
        1 -> {
            it.chatPlayer("What are you camped out here for?")
            it.chatNpc(
                "We're looking for Zanaris...GAH! I mean we're not",
                "here for any particular reason at all.",
            )
            lookingForZanaris(it)
        }
        2 -> {
            it.chatPlayer("Do you know any good adventures I can go on?")
            it.chatNpc(
                "Well we're on an adventure right now. Mind you, this",
                "is OUR adventure and we don't want to share it - find",
                "your own!",
            )
            when (it.options("Please tell me.", "I don't think you've found a good adventure at all!")) {
                1 -> {
                    it.chatPlayer("Please tell me.")
                    it.chatNpc("No.")
                    it.chatPlayer("Please?")
                    it.chatNpc("No!")
                    it.chatPlayer("PLEEEEEEEEEEEEEEEEEEEEEEASE???")
                    it.chatNpc("NO!")
                }
                2 -> {
                    it.chatPlayer("I don't think you've found a good adventure at all!")
                    it.chatNpc(
                        "Hah! Adventurers of our calibre don't just hang around",
                        "in forests for fun, whelp!",
                    )
                    it.chatPlayer("Oh really?")
                    it.chatPlayer("Why are you camped out here?")
                    it.chatNpc(
                        "We're looking for Zanaris...GAH! I mean we're not",
                        "here for any particular reason at all.",
                    )
                    lookingForZanaris(it)
                }
            }
        }
    }
}

suspend fun lookingForZanaris(it: QueueTask) {
    when (it.options("Who's Zanaris?", "What's Zanaris?", "What makes you think its out here?")) {
        1 -> {
            it.chatPlayer("Who's Zanaris?")
            it.chatNpc(
                "Ahahahahaha! Zanaris isn't a person! It's a magical hidden",
                "city filled with treasures and rich... uh, nothing. It's",
                "nothing.",
            )
            when (it.options("If it's hidden, how are you planning to find it?", "There's no such thing.")) {
                1 -> {
                    lookingforShamus(it)
                }
                2 -> {
                    it.chatPlayer("There's no such thing!")
                    it.chatNpc(
                        "When we've found Zanaris, you'll... GAH! I mean, we're",
                        "not here for any particular reason at all",
                    )
                    lookingForZanaris(it)
                }
            }
        }
        2 -> {
            it.chatPlayer("What's Zanaris?")
            it.chatNpc(
                "I don't think we want other people competing with us",
                "to find it. Forget I said anything.",
            )
        }
        3 -> {
            it.chatPlayer("What makes you think it's out here?")
            it.chatNpc(
                "Don't you know of the legends that tell of the magical",
                "city, hidden in the swam...",
            )
            it.chatNpc("Uh, no, you're right, we're wasting our time here.")
            lookingforShamus(it)
        }
    }
}

suspend fun lookingforShamus(it: QueueTask) {
    it.chatPlayer("If it's hidden, how are you planning to find it?")
    it.chatNpc(
        "Well, we don't want to tell anyone else about that,",
        "because we don't want anyone else sharing in all the",
        "glory and treasure",
    )
    when (it.options("Please tell me.", "Looks like you don't know either.")) {
        1 -> {
            it.chatPlayer("Please tell me.")
            it.chatNpc("No.")
            it.chatPlayer("Please?")
            it.chatNpc("No!")
            it.chatPlayer("PLEEEEEEEEEEEEEEEEEEEEEEASE???")
            it.chatNpc("NO!")
        }
        2 -> {
            it.chatPlayer(
                "Well, it looks to me like YOU don't know EITHER",
                "seeing as you're all just sat around here.",
            )
            it.chatNpc(
                "Of course we know! We just haven't figured out which",
                "tree the stupid leprechaun's hiding in yet! GAH!",
                "I didn't mean to tell you that! Look, just forget",
                "I said anything okay?",
            )
            it.chatPlayer("So a leprechaun knows where Zanaris is, eh?")
            it.chatNpc(
                "Ye... uh, no. No, not at all. And even if he did - ",
                "which he doesn't - he DEFINITELY ISN'T hiding in",
                "some tree around here. Nope, definitely not.",
                "Honestly.",
            )
            when (it.options("Yes.", "No.", title = "     Start Lost City?     ")) {
                1 -> {
                    if (it.player.skills.getMaxLevel(Skills.CRAFTING) >= 31 &&
                        it.player.skills.getMaxLevel(Skills.WOODCUTTING) > 36
                    )
                        {
                            it.chatPlayer("Thanks for the help!")
                            it.chatNpc(
                                "Help? What help? I didn't help! Please Don't say I",
                                "did, I'll get in trouble!",
                            )
                            it.player.startQuest(LostCity)
                        } else {
                        it.messageBox("You don't have the skill levels to start this quest...")
                        if (it.player.skills.getMaxLevel(Skills.CRAFTING) < 31) {
                            it.messageBox("You need 31 crafting.")
                        }
                        if (it.player.skills.getMaxLevel(Skills.WOODCUTTING) < 36) {
                            it.messageBox("You need 36 woodcutting.")
                        }
                    }
                }
                2 -> {
                    it.chatPlayer("No.")
                }
            }
        }
    }
}

suspend fun findingShamus(it: QueueTask) {
    it.chatPlayer(
        "So let me get this straight. I need to search the",
        "trees around here for a leprechaun. Then he will",
        "tell me where this 'Zanaris' is?",
    )
    it.chatNpc(
        "What? How did you know that Uh... I mean, no, no",
        "you're very wrong. Very wrong, and not right at",
        "all, and I definitely didn't tell you about that",
        "at all.",
    )
}

suspend fun afterShamus(it: QueueTask) {
    it.chatPlayer("Have you found anything yet?")
    it.chatNpc(
        "We're still searching for Zanaris... GAH! I mean we're",
        "not doing anything here at all.",
    )
    it.chatPlayer("I haven't found it yet either.")
}

suspend fun afterLostCity(it: QueueTask) {
    it.chatPlayer(
        "Hey, thanks for all the information. It REALLY helped",
        "me out in finding the lost city of Zanaris and all.",
    )
    it.chatNpc(
        "Oh, please don't say that anymore! If the rest of my",
        "party knew I'd helped you they'd probably throw me",
        "out and make me walk home by myself!",
    )
    it.chatNpc(
        "So anyway, what have you found out? Where is the",
        "fabled Zanaris? Is it all the legends say it is?",
    )
    it.chatPlayer("You know... I think I'll keep that to myself.")
}
