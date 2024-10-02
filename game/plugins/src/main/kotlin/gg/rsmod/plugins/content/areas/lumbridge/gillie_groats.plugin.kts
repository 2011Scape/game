package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.CooksAssistant

/**
@author Harley https://github.com/HarleyGilpin
 */

val cooksAssistant = CooksAssistant

on_npc_option(npc = Npcs.GILLIE_GROATS, option = "Talk-to") {
    player.queue {
        this.chatNpc("Hello, I'm Gillie. What can I do for you?")
        if (player.getCurrentStage(quest = cooksAssistant) == 2) {
            questDialogue(this, player)
        } else {
            optionsDialogue(this, player)
        }
    }
}

suspend fun questDialogue(
    task: QueueTask,
    player: Player,
) {
    when (
        task.options(
            "I'm after some top-quality milk.",
            "Who are you?",
            "Can you tell me how to milk a cow?",
            "I'm fine, thanks.",
        )
    ) {
        1 -> {
            task.chatPlayer("I'm after some top-quality milk.")
            task.chatNpc("Really? Is it for something special?")
            task.chatPlayer(
                "Most certainly! It's for the cook to make",
                "a cake for Duke Horacio!",
            )
            task.chatNpc(
                "Wow, it's quite an honour that you'd pick my cows.",
                "I'd suggest you get some milk from my prized cow.",
            )
            task.chatPlayer("Which one's that?")
            task.chatNpc(
                "She's on the east side of the field,",
                "over by the cliff. Be gentle!",
            )
            optionsDialogue(task, player)
        }
        2 -> whoAreYou(task, player)
        3 -> milkTutorial(task, player)
    }
}

suspend fun whoAreYou(
    task: QueueTask,
    player: Player,
) {
    task.chatNpc(
        "My name's Gillie Groats. My father is a farmer",
        "and I milk the cows for him.",
    )
    task.chatPlayer("Do you have any buckets of milk spare?")
    task.chatNpc(
        "I'm afraid not. We need all of our milk to sell to market,",
        "but you can milk the cow yourself if you need milk.",
    )
    task.chatPlayer("Thanks.")
    optionsDialogue(task, player)
}

suspend fun milkTutorial(
    task: QueueTask,
    player: Player,
) {
    task.chatNpc(
        "It's very easy. First, you need an empty",
        "bucket to hold the milk.",
    )
    if (player.getCurrentStage(quest = cooksAssistant) == 2) {
        task.chatNpc(
            "You can buy empty buckets from the general store",
            "in Lumbridge, south-west of here, or from general",
            "stores in RuneScape. You can also buy them from",
            "the Grand Exchange in Varrock.",
        )
        if (!player.inventory.hasItems(intArrayOf(Items.BUCKET))) {
            task.chatNpc(
                "You look like you could do with an empty bucket.",
                "Here, take this spare one.",
            )
            player.inventory.add(Items.BUCKET, 1)
        }
    }
    task.chatNpc("Then find a dairy cow to milk - you can't milk just any cow.")
    task.chatPlayer("How do I find a dairy cow?")
    task.chatNpc(
        "They are easy to spot - they have a cowbell around their",
        "neck and are tethered to a post to stop them wandering",
        "around all over the place. There are a couple in this field.",
    )
    task.chatNpc(
        "Then you just need to use your bucket on the cow and",
        "you'll get some tasty, nutritious milk.",
    )
    optionsDialogue(task, player)
}

suspend fun optionsDialogue(
    task: QueueTask,
    player: Player,
) {
    when (task.options("Who are you?", "Can you tell me how to milk a cow?", "I'm fine, thanks.")) {
        1 -> {
            whoAreYou(task, player)
        }
        2 -> {
            milkTutorial(task, player)
        }
        3 -> {
            task.chatPlayer("I'm fine, thanks.")
        }
    }
}
