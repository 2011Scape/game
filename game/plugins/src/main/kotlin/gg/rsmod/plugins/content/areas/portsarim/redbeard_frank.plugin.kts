package gg.rsmod.plugins.content.areas.portsarim

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PiratesTreasure
import gg.rsmod.plugins.content.quests.startQuest

val piratesTreasure = PiratesTreasure

on_npc_option(npc = Npcs.REDBEARD_FRANK, option = "talk-to") {
    player.queue {
        chatNpc("Arr, Matey!")
        beginningDialogue(this)
    }
}

suspend fun beginningDialogue(it: QueueTask) {
    when (it.player.getCurrentStage(piratesTreasure)) {
        0 -> beforeQuestDialogue(it)
        4 -> afterQuestDialogue(it)
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
        else -> {
            it.chatNpc("Have ye brought some rum for yer ol' mate Frank?")
            if (it.player.inventory.contains(Items.KARAMJAN_RUM)) {
                // TODO Update quest
            }
            else {
                it.chatPlayer("No, not yet.")
                it.chatNpc("Not surprising, 'tis no east task to get it off Karamja.")
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
