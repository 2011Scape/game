package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer
import gg.rsmod.plugins.content.quests.startQuest

/**
 * @author Tank <https://github.com/reeeccoo>
 */
val vSlayer = VampyreSlayer

on_npc_option(Npcs.MORGAN, option = "talk-to") {
    player.queue {
        when(player.getCurrentStage(vSlayer)) {
            0 -> preQuest(this)
            1 -> beforeHarlow(this)
            2 -> afterHarlow(this)
            else -> postQuest(this)
        }
    }
}

suspend fun preQuest(it: QueueTask) {
    it.chatNpc("Praise Saradomin! He has brought you here to save us all!", facialExpression = FacialExpression.HAPPY_TALKING, wrap = true)
    it.chatPlayer("Wha-", facialExpression = FacialExpression.CONFUSED)
    it.chatNpc("He has guided your steps to my door, so that I may beseech you to save my village from a terrible threat!", facialExpression = FacialExpression.HAPPY_TALKING, wrap = true)
    when(it.options("Why don't you save your own village?", "What terrible threat?", "Anything else I can do for you?")) {
        1 -> { // Why don't you save your own village?
            it.chatPlayer("Why don't you save your own village?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("I, I, I couldn't. The fear grips me like a hand. I tremble at the the idea of leaving my house!", facialExpression = FacialExpression.AFRAID, wrap = true)
            it.chatPlayer("What is this terrible threat?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("Our village is plagued by a vampyre. He visits us frequently and demands blood payments or he will terrorise us all!", facialExpression = FacialExpression.AFRAID, wrap = true)
            it.chatPlayer("The vampyre showed up all of a sudden and started attacking your village?", facialExpression = FacialExpression.CONFUSED, wrap = true)
            it.chatNpc("I don't know, I just moved here with my wife. We'd move on again, but we're down on our luck and can't afford to. ", facialExpression = FacialExpression.SAD, wrap = true)
            it.chatNpc("Besides, I don't want to abandon other innocents to this fate. This could be a good community, if only that vampyre would leave us.", wrap = true)
            it.chatNpc("Will you help me, brave adventurer?", facialExpression = FacialExpression.CONFLICTED)

        }
        2 -> { // What terrible threat?
            it.chatPlayer("What terrible threat?", facialExpression = FacialExpression.CONFUSED)
            it.chatNpc("Our village is plagued by a vampyre. He visits us frequently and demands blood payments or he will terrorise us all!", facialExpression = FacialExpression.AFRAID, wrap = true)
            it.chatPlayer("The vampyre showed up all of a sudden and started attacking your village?", facialExpression = FacialExpression.CONFUSED, wrap = true)
            it.chatNpc("I don't know, I just moved here with my wife. We'd move on again, but we're down on our luck and can't afford to. ", facialExpression = FacialExpression.SAD, wrap = true)
            it.chatNpc("Besides, I don't want to abandon other innocents to this fate. This could be a good community, if only that vampyre would leave us.", wrap = true)
            it.chatNpc("Will you help me, brave adventurer?", facialExpression = FacialExpression.CONFLICTED)
            when(it.options("Yes.", "No.")) {
                1 -> { // Yes.
                    it.chatPlayer("Yes I'll help you.", facialExpression = FacialExpression.HAPPY_TALKING)
                    it.player.startQuest(vSlayer)
                    it.chatNpc("Wonderful! You will succeed, I'm sure of it. You are very brave to take this on, but I think you should speak to my friend Harlow before you do anything else.", facialExpression = FacialExpression.HAPPY_TALKING, wrap = true)
                    it.chatPlayer("Who is this Harlow?", facialExpression = FacialExpression.UNCERTAIN)
                    it.chatNpc("He is a retired vampyre slayer! I met him when I was a missionary, long ago. He will be able to advise you on the best methods to vanquish this vampyre.", wrap = true)
                    it.chatPlayer("You already know a vampyre slayer? What do you need me for?", facialExpression = FacialExpression.ANGRY, wrap = true)
                    it.chatNpc("Harlow is...past his prime. He's seen too many evil things in his life and, to forget them, he drinks himself into oblivion. I fear he will slay vampyres no more.", facialExpression = FacialExpression.SAD, wrap = true)
                    it.chatPlayer("Where can I find this Harlow?")
                    it.chatNpc("He spends his time at the Blue Moon inn, located in Varrock. If you enter Varrock from the south, it is the second building on your right. I'm sure it's fill with lively people, so you shouldn't miss it.", facialExpression = FacialExpression.HAPPY_TALKING, wrap = true)
                    it.chatPlayer("Okay, I'll go find Harlow.",facialExpression = FacialExpression.UNCERTAIN)
                    it.chatNpc("May Saradomin protect you, my friend!", facialExpression = FacialExpression.HAPPY_TALKING)
                }
                2 -> { // No.
                    it.chatPlayer("No, I think not.")
                    it.chatNpc("Oh. Then I guess Saradomin did not send you in my time of need.")
                }
            }
        }
        3 -> { // Anything else I can do for you?
            it.chatPlayer("Anything else I can do for you?")
            it.chatNpc("Actually you can, if you have some skill as a crafter. I hear one can have clay rings blessed to defend against foes; I could do with some of those. I'll buy as many as you can provide!", facialExpression = FacialExpression.HAPPY_TALKING, wrap = true)
            when(it.options("I've brought clay rings for you.", "Right. Well, see you.")) {
                1 -> { // I've brought clay rings for you.
                    it.chatPlayer("I've brought clay rings for you.")
                    if(it.player.inventory.contains(Items.CLAY_RING)) {
                        it.chatNpc("Ah, excellent. You can never be too careful, you know.")
                        it.doubleItemMessageBox("Morgan trades your ring for a small pile of coins, and stashes it safely away for later.", Items.COINS, Items.CLAY_RING)
                        it.player.inventory.remove(Items.CLAY_RING, 1)
                        it.player.inventory.add(Items.COINS, 10)
                    } else {
                        it.chatNpc("No, you haven't. Feel free to return with one, though.")
                    }

                }
                2 -> { // Right. Well, see you.
                    it.chatPlayer("Right. Well, see you.", facialExpression = FacialExpression.GRUMPY)
                }
            }
        }
    }
}

suspend fun beforeHarlow(it: QueueTask) {
    it.chatNpc("How can I help, brave adventurer?", facialExpression = FacialExpression.CONFUSED)
    when(it.options("What do I need to do?", "How do I find Harlow?", "Anything else I can do for you?", "Never mind.")) {
        1 -> { // What do I need to do?
            it.chatPlayer("What do I need to do?")
            it.chatNpc("Well, ultimately, you need to slay the vampyre plaguing my village. But, right now, you should speak to Harlow in Varrock. He will tell you the secrets to vampyre slaying.", wrap = true)
            when(it.options("How do I find Harlow?", "Right, I'll get on that.")) {
                1 -> { // How do I find Harlow?
                    it.chatPlayer("How do I find Harlow?")
                    it.chatNpc("Leave Draynor Village heading north-east. Cross the River Lum and head north to Varrock.", wrap = true)
                    it.chatNpc("Enter Varrock from the south and on your right should be a pub called the Blue Moon inn. Harlow should be in there: drinking, no doubt.", wrap = true)
                }
                2 -> { // Right, I'll get on that.
                    it.chatPlayer("Right, I'll get on that.")
                    it.chatNpc("Please Hurry!", facialExpression = FacialExpression.AFRAID)
                }
            }
        }
        2 -> { // How do I find Harlow?
            it.chatPlayer("How do I find Harlow?")
            it.chatNpc("Leave Draynor Village heading north-east. Cross the River Lum and head north to Varrock.", wrap = true)
            it.chatNpc("Enter Varrock from the south and on your right should be a pub called the Blue Moon inn. Harlow should be in there: drinking, no doubt.", wrap = true)
        }
        3 -> { // Anything else I can do for you?
            it.chatPlayer("Anything else I can do for you?")
            it.chatNpc(
                "Actually you can, if you have some skill as a crafter. I hear one can have clay rings blessed to defend against foes; I could do with some of those. I'll buy as many as you can provide!",
                facialExpression = FacialExpression.HAPPY_TALKING,
                wrap = true
            )
            when (it.options("I've brought clay rings for you.", "Right. Well, see you.")) {
                1 -> { // I've brought clay rings for you.
                    it.chatPlayer("I've brought clay rings for you.")
                    if (it.player.inventory.contains(Items.CLAY_RING)) {
                        it.chatNpc("Ah, excellent. You can never be too careful, you know.")
                        it.doubleItemMessageBox(
                            "Morgan trades your ring for a small pile of coins, and stashes it safely away for later.",
                            Items.COINS,
                            Items.CLAY_RING
                        )
                        it.player.inventory.remove(Items.CLAY_RING, 1)
                        it.player.inventory.add(Items.COINS, 10)
                    } else {
                        it.chatNpc("No, you haven't. Feel free to return with one, though.")
                    }
                }
                2 -> { // Right. Well, see you.
                    it.chatPlayer("Right. Well, see you.", facialExpression = FacialExpression.GRUMPY)
                }
            }
        }
        4 -> { // Never mind.
            it.chatPlayer("Never mind.")
            it.chatNpc("Please hurry and slay that vampyre!", facialExpression = FacialExpression.AFRAID)
        }
    }
}
suspend fun afterHarlow(it: QueueTask) {
    it.chatNpc()
}
suspend fun postQuest(it: QueueTask) {
    it.chatNpc(
        "I cannot thank you enough, Player. My wife and I can truly start a new life here in Draynor, now that the threat of the vampyre is gone.",
        facialExpression = FacialExpression.HAPPY_TALKING,
        wrap = true
    )
    when (it.options("I'm glad I could help.", "Next time, you're on your own!", "Anything else I can do for you?")) {
        1 -> { // I'm glad I could help
            it.chatPlayer("I'm glad I could help.")
        }

        2 -> { // Next time, you're on your own!
            it.chatPlayer("Next time, you're on your own!", facialExpression = FacialExpression.GRUMPY)
            it.chatNpc(
                "I know it was an arduous task, and we can never truly repay you.",
                facialExpression = FacialExpression.HAPPY_TALKING
            )
        }

        3 -> { // Anything else I can do for you?
            it.chatPlayer("Anything else I can do for you?")
            it.chatNpc(
                "Actually you can, if you have some skill as a crafter. I hear one can have clay rings blessed to defend against foes; I could do with some of those. I'll buy as many as you can provide!",
                facialExpression = FacialExpression.HAPPY_TALKING,
                wrap = true
            )
            when (it.options("I've brought clay rings for you.", "Right. Well, see you.")) {
                1 -> { // I've brought clay rings for you.
                    it.chatPlayer("I've brought clay rings for you.")
                    if (it.player.inventory.contains(Items.CLAY_RING)) {
                        it.chatNpc("Ah, excellent. You can never be too careful, you know.")
                        it.doubleItemMessageBox(
                            "Morgan trades your ring for a small pile of coins, and stashes it safely away for later.",

                            Items.COINS,
                            Items.CLAY_RING
                        )
                        it.player.inventory.remove(Items.CLAY_RING, 1)
                        it.player.inventory.add(Items.COINS, 10)
                    } else {
                        it.chatNpc("No, you haven't. Feel free to return with one, though.")
                    }

                }

                2 -> { // Right. Well, see you.
                    it.chatPlayer("Right. Well, see you.", facialExpression = FacialExpression.GRUMPY)

                }
            }
        }
    }
}