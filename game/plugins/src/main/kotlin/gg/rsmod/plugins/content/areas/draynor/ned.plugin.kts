package gg.rsmod.plugins.content.areas.draynor

import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.VampyreSlayer
import gg.rsmod.plugins.content.quests.startedQuest

on_npc_option(Npcs.NED, "Talk-to") {
    player.queue {
        when (options(
            "About the Task System...",
            "Talk about something else."
        )) {
            FIRST_OPTION -> {
                chatNpc("No tasks for you to do yet, are there? Come back later.",
                    facialExpression = FacialExpression.LAUGHING,
                    wrap = true)
            }
            SECOND_OPTION -> {
                val lassLad = if (player.appearance.gender == Gender.MALE) "lad" else "lass"
                chatNpc("Why, hello there $lassLad. Me friends call me Ned. I was a man of the sea, but it's past me " +
                    "now. Could I be making or selling you some rope?",
                    facialExpression = FacialExpression.NORMAL,
                    wrap = true)
                somethingElse(this)
            }
        }
    }
}

suspend fun somethingElse(it: QueueTask) {
    if (it.player.startedQuest(VampyreSlayer) || it.player.finishedQuest(VampyreSlayer)) {
        when (it.options(
            "Yes, I would like some rope.",
            "No thanks, Ned, I don't need any.",
            "Talk about Vampyre Slayer."
        )) {
            FIRST_OPTION -> yesRope(it)
            SECOND_OPTION -> noRope(it)
            THIRD_OPTION -> {
                if (it.player.finishedQuest(VampyreSlayer)) {
                    it.chatNpc("So, I hear you managed to kill that vampyre. Good on yah.",
                        facialExpression = FacialExpression.CALM_TALK,
                        wrap = true)
                    it.chatPlayer("Thanks, Ned.")
                }
                else {
                    it.chatPlayer("Can you tell me anything about vampyres? I told Morgan I would slay the one in " +
                        "Draynor Manor.",
                        facialExpression = FacialExpression.CONFUSED,
                        wrap = true)
                    it.chatNpc("I know to stay well out of their way if you value your life.",
                        facialExpression = FacialExpression.CALM_TALK,
                        wrap = true)
                    it.chatPlayer("That's not very helpful!",
                        facialExpression = FacialExpression.MAD,
                        wrap = true)
                }
            }
        }
    }
    else {
        when (it.options(
            "Yes, I would like some rope.",
            "No thanks, Ned, I don't need any."
        )) {
            FIRST_OPTION -> yesRope(it)
            SECOND_OPTION -> noRope(it)
        }
    }
}

suspend fun yesRope(it: QueueTask) {
    it.chatPlayer("Yes, I would like some rope.",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
    it.chatNpc("Well, I can sell you some rope for 15 coins. Or I can be making you some if you gets me 4 balls of " +
        "wool. I strands them together I does, makes em strong.",
        facialExpression = FacialExpression.NORMAL,
        wrap = true)
    it.chatPlayer("You make rope from wool?",
        facialExpression = FacialExpression.CONFUSED,
        wrap = true)
    it.chatNpc("Of course you can!",
        facialExpression = FacialExpression.SUSPICIOUS,
        wrap = true)
    it.chatPlayer("I thought you needed hemp or jute.",
        facialExpression = FacialExpression.CONFUSED,
        wrap = true)
    it.chatNpc("Do you want some rope or not?",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
    val thirdOption = if (it.player.inventory.getItemCount(Items.BALL_OF_WOOL) < 4)
        "I will go and get some wool."
    else
        "I have some balls of wool."
    when (it.options(
        "Okay, please sell me some rope.",
        "That's a little more than I want to pay.",
        thirdOption
    )) {
        FIRST_OPTION -> {
            it.chatPlayer("Okay, please sell me some rope.",
                facialExpression = FacialExpression.CALM_TALK,
                wrap = true)
            if (it.player.inventory.getItemCount(Items.COINS_995) < 15) {
                it.messageBox("You don't have enough coins to buy any rope!")
            }
            else {
                it.player.inventory.remove(Items.COINS_995, 15)
                val added = it.player.inventory.add(Items.ROPE)
                if (added.hasFailed()) {
                    world.spawn(GroundItem(Items.ROPE, 1, it.player.tile))
                }
                it.chatNpc("There you go.", "Finest rope in Gielinor.")
                it.itemMessageBox(item = Items.ROPE, message =  "You hand Ned 15 coins. Ned gives you a coil of rope.")
            }
        }
        SECOND_OPTION -> {
            it.chatPlayer("That's a little more than I want to pay.",
                facialExpression = FacialExpression.CALM_TALK,
                wrap = true)
            it.chatNpc("Well, if you ever need rope that's the price. Sorry. An old sailor needs money for a little " +
                "drop o' rum.",
                facialExpression = FacialExpression.CALM_TALK,
                wrap = true)
        }
        THIRD_OPTION -> {
            if (it.player.inventory.getItemCount(Items.BALL_OF_WOOL) < 4) {
                it.chatPlayer("I will go and get some wool.",
                    facialExpression = FacialExpression.CALM_TALK,
                    wrap = true)
                it.chatNpc("Aye, you do that. Remember, it takes 4 balls of wool to make strong rope.",
                    facialExpression = FacialExpression.NORMAL,
                    wrap = true)
            }
            else {
                it.chatPlayer("I have some balls of wool. Could you make me some rope?",
                    facialExpression = FacialExpression.NORMAL,
                    wrap = true)
                it.chatNpc("Sure I can.", facialExpression = FacialExpression.NORMAL)
                it.player.inventory.remove(Items.BALL_OF_WOOL, 4)
                it.player.inventory.add(Items.ROPE)
                it.itemMessageBox(item = Items.ROPE, message =  "You hand over 4 balls of wool. Ned gives you a coil " +
                    "of rope.")

            }
        }
    }
}

suspend fun noRope(it: QueueTask) {
    it.chatPlayer("No thanks, Ned, I don't need any.",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
    it.chatNpc("Well, old Neddy is always here if you do. Tell your friend. I can always be using the business.",
        facialExpression = FacialExpression.CALM_TALK,
        wrap = true)
}
