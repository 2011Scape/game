package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.ImpCatcher
import gg.rsmod.plugins.content.quests.startQuest

val impCatcher = ImpCatcher

create_shop(
    "Mizgog shop",
    currency = CoinCurrency(),
    purchasePolicy = PurchasePolicy.BUY_STOCK,
    containsSamples = false,
) {
    items[0] = ShopItem(Items.AMULET_OF_ACCURACY, amount = 30, sellPrice = 5000, buyPrice = 3000)
}

on_npc_option(npc = Npcs.WIZARD_MIZGOG, option = "trade") {
    if (player.finishedQuest(ImpCatcher)) {
        player.openShop("Mizgog shop")
    } else {
        player.message("Wizard Mizgog doesn't want to trade with you right now.")
    }
}

on_npc_option(npc = Npcs.WIZARD_MIZGOG, option = "talk-to") {
    player.queue {
        when (player.getCurrentStage(ImpCatcher)) {
            1 -> questDialogue(this)
            2 -> afterQuestDialogue(this)
            else -> beforeQuestDialogue(this)
        }
    }
}

suspend fun beforeQuestDialogue(it: QueueTask) {
    it.chatPlayer("Give me a quest!")
    it.chatNpc("Give me a quest what?")
    when (
        it.options(
            "Give me a quest please.",
            "Give me a quest or else!",
            "Just stop messing around and give me a quest!",
        )
    ) {
        1 -> {
            it.chatPlayer("Give me a quest please.")
            it.chatNpc("Well seeing as you asked nicely... I could do with some help.")
            it.chatNpc(
                "The wizard Grayzag next door decided he didn't like me so",
                "he enlisted an army of hundreds of imps.",
            )
            it.chatNpc(
                "These imps stole all sorts of things. Most of these",
                "things I don't really care about, just eggs and balls of",
                "string and things.",
            )
            it.chatNpc(
                "But they stole my four magical beads. There was a red",
                "one, a yellow one, a black one, and a white one.",
            )
            it.chatNpc(
                "These imps have now spread out all over the kingdom by now.",
                "Could you get my beads back for me?",
            )
            when (it.options("I'll try.", "I've better things to do than chase imps.")) {
                1 -> {
                    it.chatPlayer("I'll try.")
                    it.chatNpc("That's great, thank you.")
                    it.player.startQuest(impCatcher)
                }
                2 -> {
                    it.chatPlayer("I've better things to do than chase imps.")
                    it.chatNpc(
                        "Well if you're not interested in the quests I have to give",
                        "you, don't waste my time by asking me for them.",
                    )
                }
            }
        }
        2 -> {
            it.chatPlayer("Give me a quest or else!")
            it.chatNpc("Or else what? You'll attack me?")
            it.chatNpc("Hahaha!", facialExpression = FacialExpression.LAUGHING)
        }
        3 -> {
            it.chatPlayer("Just stop messing around and give me a quest!")
            it.chatNpc("Ah now you're assuming I have one to give.")
        }
    }
}

suspend fun questDialogue(it: QueueTask) {
    it.chatNpc("So how are you doing finding my beads?")
    when (impCatcher.beadsCount(it.player)) {
        0 -> {
            it.chatPlayer("I've not found any yet.")
            it.chatNpc(
                "Well get on with it. I've lost a white bead",
                ", a red bead, a black bead, and a yellow bead. Go kill some imps!",
            )
        }
        4 -> {
            it.chatPlayer("I've got all four beads. It was hard work I can tell you.")
            it.chatNpc(
                "Give them here and I'll check that they really are MY",
                "beads, before I give you your reward. You'll like it, it's an",
                "amulet of accuracy.",
            )
            it.messageBox("You give four coloured beads to Wizard Mizgog.")
            // TODO: cutscene of Mizgog putting down the beads
            ImpCatcher.finishQuest(it.player)
        }
        else -> {
            it.chatPlayer("I have found some of your beads.")
            it.chatNpc(
                "Come back when you have them all. The colour of",
                "the four beads that I need are red, yellow, black, and white.",
                "Go chase some imps!",
            )
        }
    }
}

suspend fun afterQuestDialogue(it: QueueTask) {
    when (
        it.options(
            "Got any more quests?",
            "Do you know any interesting spells you could teach me?",
            "Have you got another one of those fancy schmancy amulets?",
        )
    ) {
        1 -> {
            it.chatPlayer("Got any more quests?")
            it.chatNpc("No, everything is good with the world today.")
        }
        2 -> {
            it.chatPlayer("Do you know any interesting spells you could teach", "me?")
            it.chatNpc("I don't think so, the type of magic I study involves", "years of meditation and research.")
        }
        3 -> {
            it.chatPlayer("Have you got another one of those fancy schmancy", "amulets?")
            it.chatNpc(
                "I have a few spare. I'd like one of each coloured bead",
                "again in return, though! Black, white, yellow and red.",
            )
            when (impCatcher.beadsCount(it.player)) {
                4 -> {
                    when (it.options("I have them with me!", "Maybe later.")) {
                        1 -> {
                            it.chatPlayer("I have them with me! Here you go.")
                            impCatcher.exchangeBeads(it.player)
                            it.chatPlayer("Thanks, Mizgog!")
                            it.chatPlayer("What are you going to do with all of these extra beads, anyway?")
                            it.chatNpc("You don't want to know. Take care!")
                        }
                        2 -> {
                            it.chatPlayer("Maybe later.")
                            it.chatNpc("Perhaps some other time, then.")
                        }
                    }
                }
                else -> {
                    it.chatPlayer("I don't have them on me at the moment. I'll come", "back when I have them!")
                    it.chatNpc("Very well. See you soon!")
                }
            }
        }
    }
}
