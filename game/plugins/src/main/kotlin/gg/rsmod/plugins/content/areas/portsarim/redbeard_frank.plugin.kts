package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PiratesTreasure
import gg.rsmod.plugins.content.quests.startQuest

val piratesTreasure = PiratesTreasure

on_item_option(item = Items.CASKET_7956, option = "open") {
    player.inventory.remove(Items.CASKET_7956)
    player.message("You open the casket, and find One-Eyed Hector's treasure.")

    val coins = Item(Items.COINS_995, 450)
    val ring = Item(Items.GOLD_RING, 1)
    val emerald = Item(Items.EMERALD, 1)

    val coinsGiven = player.inventory.add(coins)
    if (!coinsGiven.hasSucceeded()) {
        world.spawn(GroundItem(coins, player.tile, player))
    }
    val ringGiven = player.inventory.add(ring)
    if (!ringGiven.hasSucceeded()) {
        world.spawn(GroundItem(ring, player.tile, player))
    }
    val emeraldGiven = player.inventory.add(emerald)
    if (!emeraldGiven.hasSucceeded()) {
        world.spawn(GroundItem(emerald, player.tile, player))
    }
}

on_item_on_npc(Items.CASKET_7956, Npcs.REDBEARD_FRANK) {
    player.queue {
        shareTreasureDialogue(this)
    }
}

on_npc_option(npc = Npcs.REDBEARD_FRANK, option = "talk-to") {
    player.queue {
        chatNpc("Arr, Matey!")
        beginningDialogue(this)
    }
}

suspend fun beginningDialogue(it: QueueTask) {
    when (it.player.getCurrentStage(piratesTreasure)) {
        0 -> beforeQuestDialogue(it)
        3, 4 -> afterQuestDialogue(it)
        else -> questDialogue(it)
    }
}

suspend fun beforeQuestDialogue(it: QueueTask) {
    when (it.options(
        "I'm in search of treasure.",
        "Arr!",
        "Do you have anything for trade?",
        "About the Task System...")) {
        FIRST_OPTION -> startQuestDialogue(it)
        SECOND_OPTION -> arrDialogue(it, beforeQuest = true)
        THIRD_OPTION -> tradeDialogue(it)
        FOURTH_OPTION -> taskDialogue(it)
    }
}

suspend fun afterQuestDialogue(it: QueueTask) {
    when (it.options(
        "Arr!",
        "Do you have anything for trade?",
        "About the Task System...")) {
        FIRST_OPTION -> arrDialogue(it)
        SECOND_OPTION -> tradeDialogue(it)
        THIRD_OPTION -> taskDialogue(it)
    }
}

suspend fun questDialogue(it: QueueTask) {
    when (it.player.getCurrentStage(piratesTreasure)) {
        1 -> {
            it.chatNpc("Have ye brought some rum for yer ol' mate Frank?")
            if (it.player.inventory.contains(Items.KARAMJAN_RUM)) {
                it.chatPlayer("Yes, I've got some.")
                it.chatNpc(*("Now a deal's a deal, I'll tell ye about the treasure. I used to serve under a pirate " +
                    "captain called One-Eyed Hector.").splitForDialogue())
                it.chatNpc(*("Hector were very successful and became very rich. But about a year ago we were boarded " +
                    "by the Customs and Excise Agents.").splitForDialogue())
                it.chatNpc(*("Hector were killed along with many of the crew, I were one of the few to escape and I " +
                    "escaped with this.")
                    .splitForDialogue())
                it.player.inventory.remove(Items.KARAMJAN_RUM)
                it.player.inventory.add(Items.CHEST_KEY)
                it.player.advanceToNextStage(PiratesTreasure)
                it.itemMessageBox("Frank happily takes the rum... ...and hands you a key.", Items.CHEST_KEY)
                it.chatNpc(*("This be Hector's key. I believe it opens his chest in his old room in the Blue Moon Inn in " +
                    "Varrock.").splitForDialogue())
                it.chatNpc("With any luck his treasure will be there.")
                when (it.options(
                    "Ok thanks, I'll go and get it.",
                    "So why didn't you ever get it?"
                )) {
                    FIRST_OPTION -> it.chatPlayer("Ok thanks, I'll go and get it.")
                    SECOND_OPTION -> {
                        it.chatPlayer("So why didn't you ever get it?")
                        it.chatNpc(*("I'm not allowed in the Blue Moon Inn. Apparently I'm a drunken trouble " +
                            "maker.").splitForDialogue())
                    }
                }
            }
            else {
                it.chatPlayer("No, not yet.")
                it.chatNpc("Not surprising, 'tis no easy task to get it off Karamja.")
                it.chatPlayer("What do you mean?")
                it.chatNpc(
                    "The Customs office has been clampin' down on the export",
                    "of spirits. You seem like a resourceful young lass, I'm sure",
                    "ye'll be able to find a way to slip the stuff past them."
                )
                it.chatPlayer("Well, I'll give it another shot.")
                it.chatNpc("Was there anything else?")
                afterQuestDialogue(it)
            }
        }
        2 -> {
            if (it.player.inventory.contains(Items.CHEST_KEY)) {
                afterQuestDialogue(it)
            }
            else {
                it.chatPlayer("I seem to have lost my chest key...")
                it.chatNpc(*"Arr, silly you. Fortunately I took the precaution to have another one made.".splitForDialogue())
                it.itemMessageBox("Frank hands you a key", Items.CHEST_KEY)
                it.player.inventory.add(Items.CHEST_KEY)
                afterQuestDialogue(it)
            }
        }
    }
}

suspend fun tradeDialogue(it: QueueTask) {
    it.chatPlayer("Do you have anything for trade?")
    it.chatNpc(
        "Nothin' at the moment, but then again the Customs",
        "Agents are on the warpath right now."
    )
}

suspend fun arrDialogue(it: QueueTask, beforeQuest: Boolean = false) {
    it.chatPlayer("Arr!")
    it.chatNpc("Arr!")
    if (beforeQuest) {
        beginningDialogue(it)
    }
    else {
        afterQuestDialogue(it)
    }
}

suspend fun taskDialogue(it: QueueTask) {
    // TODO when implementing task system
    it.chatNpc("I can't help ye' with tasks right now, ask me later.")
}

suspend fun startQuestDialogue(it: QueueTask) {
    it.chatPlayer("I'm in search of treasure.")
    it.chatNpc(
        "Arr, treasure you be after eh? Well I might be able to tell",
        "you where to find some... For a price..."
    )
    it.chatPlayer("What sort of price?")
    it.chatNpc(
        "Well for example if you can get me a bottle of rum... Not",
        "just any rum mind..."
    )
    it.chatNpc(
        "I'd like some rum made on Karamja Island. There's no rum",
        "like Karamja Rum!"
    )
    when (it.options(
        "Not right now.",
        "Ok, I will bring you some rum.")) {
        FIRST_OPTION -> {
            it.chatPlayer("Not right now.")
            it.chatNpc(
                "Fair enough. I'll still be here and thirsty whenever you feel",
                "like helpin' out."
            )
        }
        SECOND_OPTION -> {
            it.chatPlayer("Ok, I will bring you some rum.")
            it.chatNpc(
                "Yer a saint, although it'll take a miracle to get it off",
                "Karamja."
            )
            it.chatPlayer("What do you mean?")
            it.chatNpc(
                "The Customs office has been clampin' down on the export",
                "of spirits. You seem like a resourceful young lass, I'm sure",
                "ye'll be able to find a way to slip the stuff past them."
            )
            it.chatPlayer("Well I'll give it a shot.")
            it.chatNpc("Arr, that's the spirit!")
            it.player.startQuest(piratesTreasure)
        }
    }
}

suspend fun shareTreasureDialogue(it: QueueTask) {
    it.chatPlayer("I have the treasure, would you like a share?")
    val tag = if (it.player.appearance.gender == Gender.MALE) "lad" else "lass"
    it.chatNpc("No $tag, you got it fair and square.")
    it.chatNpc("You enjoy it. It's what Hector would have wanted.")
}
