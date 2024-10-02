package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.game.model.attr.EXRTA_FINE_FLOUR
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.CooksAssistant

/**
 * @author Alycia <https://github.com/alycii>
 */

val cooksAssistant = CooksAssistant

on_npc_option(npc = Npcs.MILLIE_MILLER, option = "Talk-to") {
    player.queue {
        this.chatNpc("Hello, adventurer. Welcome to Mill Lane Mill. Can I", "help you?")
        if (player.getCurrentStage(quest = cooksAssistant) == 1 && !player.hasItem(Items.EXTRA_FINE_FLOUR)) {
            questDialogue(this)
        } else {
            optionsDialogue(this)
        }
    }
}

suspend fun questDialogue(task: QueueTask) {
    when (
        task.options(
            "I'm looking for extra fine flour.",
            "What is this place?",
            "How do I mill flour?",
            "I'm fine, thanks.",
        )
    ) {
        1 -> {
            task.chatPlayer("I'm looking for extra fine flour.")
            task.chatNpc("What's wrong with ordinary flour?")
            task.chatPlayer(
                "Well, I'm no expert chef, but apparently it makes better",
                "cakes. This cake, you see, is for Duke Horacio.",
            )
            task.chatNpc(
                "Really? How marvellous! Well, I can sure help you out",
                "there. Go ahead and use the mill, and I'll realign the",
                "millstones to produce extra fine flour. Anything else?",
            )
            task.player.attr[EXRTA_FINE_FLOUR] = true
            optionsDialogue(task)
        }
        2 -> place(task)
        3 -> tutorial(task)
    }
}

suspend fun place(task: QueueTask) {
    task.chatNpc(
        "This is Mill Lane Mill, source of the finest flour in Gielinor,",
        "and home to the Miller family for many generations.",
    )
    task.chatNpc("We take wheat from the field nearby and mill it into flour.")
    optionsDialogue(task)
}

suspend fun tutorial(task: QueueTask) {
    task.chatNpc(
        "Making flour is pretty easy. First of all, you need to get",
        "some wheat. You can pick some from the wheat fields. There",
        "is one just outside the mill, but there are many others",
        "scattered across the world.",
    )
    task.chatNpc("Feel free to pick wheat from our field! There always", "seems to be plenty of wheat there.")
    task.chatPlayer("Then I bring my wheat here?")
    task.chatNpc("Yes, or to one of the other mills in Gielinor. They all work", "the same way.")
    task.chatNpc("Just take your wheat up two levels to the top floor of the", "mill and place some into the hopper.")
    task.chatNpc(
        "Then you need to start the grinding process by pulling the",
        "lever near the hopper. You can add more wheat, but each",
        "time you add wheat you'll have to pull the hopper lever",
        "again.",
    )
    task.chatPlayer("So, where does the flour go then?")
    task.chatNpc(
        "The flour appears in this room here. You'll need an empty",
        "pot to put the flour into. One pot will hold the flour made",
        "by one load of wheat.",
    )
    task.chatNpc("That's all there is to it and you'll have a pot of flour.")
    task.chatPlayer("Great! Thanks for your help.")
    optionsDialogue(task)
}

suspend fun optionsDialogue(task: QueueTask) {
    when (task.options("What is this place?", "How do I mill flour?", "I'm fine, thanks.")) {
        1 -> {
            place(task)
        }
        2 -> {
            tutorial(task)
        }
    }
}
