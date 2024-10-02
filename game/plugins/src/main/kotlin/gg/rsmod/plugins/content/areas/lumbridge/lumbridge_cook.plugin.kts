package gg.rsmod.plugins.content.areas.lumbridge

import gg.rsmod.plugins.content.quests.*
import gg.rsmod.plugins.content.quests.impl.CooksAssistant

/**
 * @author Alycia <https://github.com/alycii>
 */

val cooksAssistant = CooksAssistant

set_ground_item_condition(item = Items.SUPER_LARGE_EGG) {
    val hasItem = player.hasItem(Items.SUPER_LARGE_EGG)
    val currentStage = player.getCurrentStage(cooksAssistant) == 1
    if (hasItem) {
        player.message("You already have an egg in your inventory.")
    } else if (!currentStage) {
        player.message("You've no reason to pick that up; eggs of that size are only useful for royal cakes.")
    }
    !hasItem && currentStage
}

on_global_item_pickup {
    if (player.getCurrentStage(
            cooksAssistant,
        ) != 1 &&
        player.getInteractingGroundItem().item == Items.SUPER_LARGE_EGG
    ) {
        player.message("You've no reason to pick that up; eggs of that size are only useful for royal cakes.")
        return@on_global_item_pickup
    }
}
on_npc_option(npc = Npcs.COOK, option = "Talk-to") {
    player.queue {
        if (!player.startedQuest(cooksAssistant) && player.canStartQuest(cooksAssistant)) {
            preQuest(this)
        } else {
            mainChat(this)
        }
    }
}

suspend fun preQuest(task: QueueTask) {
    task.chatNpc("What am I to do?", facialExpression = FacialExpression.UPSET)
    when (
        task.options(
            "What's wrong?",
            "You're a cook, why don't you bake me a cake?",
            "You don't look very happy.",
            "Nice hat!",
        )
    ) {
        1 -> {
            questStart(task)
        }
        2 -> {
            task.chatPlayer("You're a cook, why don't you bake me a cake?")
            task.chatNpc("*sniff* Don't talk to me about cakes...", facialExpression = FacialExpression.UPSET)
            questStart(task)
        }
        3 -> {
            task.chatPlayer("You don't look very happy.")
            task.chatNpc(
                "No, I'm not. The world is caving in around me - I am",
                "overcome by the dark feelings of impending doom.",
            )
            when (task.options("What's wrong?", "I'd take the rest of the day off if I were you.")) {
                1 -> questStart(task)
                2 -> {
                    task.chatPlayer("I'd take the rest of the day off if I were you.")
                    task.chatNpc("No, that's the worst thing I could do. I'd get in terrible", "trouble.")
                    task.chatPlayer("Well maybe you need to take a holiday...")
                    task.chatNpc("That would be nice, but the Duke doesn't allow holidays", "for core staff.")
                    task.chatPlayer("Hmm, why not run away to the sea and start a new", "life as a Pirate?")
                    task.chatNpc(
                        "My wife gets sea sick, and I have an irrational fear of",
                        "eyepatches. I don't see it working myself.",
                    )
                    task.chatPlayer("I'm afraid I've run out of ideas.")
                    task.chatNpc("I know I'm doomed.")
                    questStart(task)
                }
            }
        }
        4 -> {
            task.chatPlayer("Nice hat!")
            task.chatNpc("Err thank you. It's a pretty ordinary cooks hat really.")
            task.chatPlayer("Still, suits you. The trousers are pretty special too.")
            task.chatNpc("Its all standard cook's issue uniform...")
            task.chatPlayer(
                "The whole hat, apron, stripey trousers ensemble - it",
                "works. It makes you look like a real cook.",
            )
            task.chatNpc(
                "I am a real cook! I haven't got time to be chatting",
                "about Culinary Fashion. I am in desperate need of help!",
            )
            questStart(task)
        }
    }
}

suspend fun questStart(task: QueueTask) {
    task.chatPlayer("What's wrong?")
    task.chatNpc(
        "Oh dear, oh dear, oh dear, I'm in a terrible terrible",
        "mess! It's the Duke's birthday today, and I should be",
        "making him a lovely big birthday cake.",
        facialExpression = FacialExpression.AFRAID,
    )
    task.chatNpc(
        "I've forgotten to buy the ingredients. I'll never get",
        "them in time now. He'll sack me! What will I do? I have",
        "four children and a goat to look after. Would you help",
        "me? Please?",
        facialExpression = FacialExpression.UPSET,
    )
    when (task.options("Yes", "No")) {
        1 -> {
            task.chatPlayer("Yes, I'll help you.")
            task.player.startQuest(cooksAssistant)
            task.chatNpc(
                "Oh thank you, thank you. I need milk, an egg and",
                "flour. I'd be very grateful if you can get them for me.",
            )
            task.chatPlayer("So where do I find these ingredients then?", facialExpression = FacialExpression.DISREGARD)
            helpChat(task)
        }
        2 -> {
            task.chatPlayer("No, I don't feel like it. Maybe later.", facialExpression = FacialExpression.NORMAL)
            task.chatNpc(
                "Fine. I always knew you Adventurer types were callous",
                "beasts. Go on your merry way!",
                facialExpression = FacialExpression.ANGRY_2,
            )
        }
    }
}

suspend fun helpChat(task: QueueTask) {
    when (
        task.options(
            "Where do I find some flour?",
            "How about milk?",
            "And eggs? Where are they found?",
            "Actually, I know where to find this stuff.",
        )
    ) {
        1 -> {
            task.chatNpc(
                "There is a Mill fairly close, go North and then West.",
                "Mill Lane Mill is just off the road to Draynor. I",
                "usually get my flour from there.",
            )
            task.chatNpc(
                "Talk to Millie, she'll help, she's a lovely girl and a fine",
                "Miller. Make sure you take a pot with you for the flour.",
            )
            helpChat(task)
        }
        2 -> {
            task.chatNpc(
                "There is a cattle field on the other side of the river,",
                "just across the road from the Groat's Farm.",
            )
            task.chatNpc(
                "Talk to Gillie Groats, she looks after the Dairy cows - ",
                "she'll tell you everything you need to know about milking cows!",
            )
            task.chatNpc("You'll need an empty bucket for the milk itself.")
            helpChat(task)
        }
        3 -> {
            task.chatNpc("I normally get my eggs from the Groat's farm, on the", "other side of the river.")
            task.chatNpc("But any chicken should lay eggs.")
            helpChat(task)
        }
        4 -> {
            task.chatPlayer("Actually, I know where to find this stuff.")
        }
    }
}

suspend fun mainChat(task: QueueTask) {
    if (!task.player.finishedQuest(cooksAssistant)) {
        task.chatNpc("How are you getting on with finding the ingredients?", facialExpression = FacialExpression.UPSET)
        if (task.player.inventory.contains(Items.EXTRA_FINE_FLOUR) &&
            task.player.inventory.contains(Items.TOPQUALITY_MILK) &&
            task.player.inventory.contains(Items.SUPER_LARGE_EGG)
        ) {
            task.chatPlayer("Here's a bucket of top-quality milk.")
            task.chatPlayer("Here's a super-large egg.")
            task.chatPlayer("Here's a pot of extra-fine flour.")
            task.chatNpc("You've brought me everything I need! I am saved!", "Thank you!")
            task.chatPlayer("So do I get to go to the Duke's Party?")
            task.chatNpc(
                "I'm afraid not, only the big cheeses get to dine with the",
                "Duke.",
                facialExpression = FacialExpression.UPSET,
            )
            task.chatPlayer(
                "Well, maybe one day I'll be important enough to sit at",
                "the Duke's table.",
                facialExpression = FacialExpression.CALM_TALK,
            )
            task.chatNpc("Maybe, but I won't be holding my breath.", facialExpression = FacialExpression.CALM_TALK)
            cooksAssistant.finishQuest(task.player)
        } else {
            task.chatPlayer("I haven't got them all yet, I'm still looking.", facialExpression = FacialExpression.UPSET)
            task.chatNpc(
                "Please get the ingredients quickly - I'm running out of",
                "time! The Duke will throw me into the streets!",
                facialExpression = FacialExpression.UPSET,
            )
            when (task.options("I'll get right on it.", "Can you remind me how to find these things again?")) {
                1 -> {
                    task.chatPlayer("I'll get right on it")
                }
                2 -> {
                    task.chatPlayer("Can you remind me how to find these things again?")
                    helpChat(task)
                }
            }
        }
    } else {
        task.chatNpc("How is the adventuring going, my friend?")
        when (
            task.options(
                "Do you have any other quests for me?",
                "I am getting strong and mighty.",
                "I keep on dying.",
                "Can I use your range?",
            )
        ) {
            1 -> {
                task.chatNpc("I don't have anything for you to do right now, sorry.")
            }
            2 -> {
                task.chatPlayer("I am getting strong and mighty. Grrr")
                task.chatNpc("Glad to hear it.")
            }
            3 -> {
                task.chatPlayer("I keep on dying.")
                task.chatNpc("Ah well, at least you keep coming back to life!")
            }
            4 -> {
                task.chatNpc("Go ahead - it's a very good range. It's easier to use", "than most other ranges.")
                task.chatNpc(
                    "Its called the Cook-o-matic 100. It uses a combination of",
                    "state of the art temperature regulation and magic.",
                )
                task.chatPlayer("Will it mean my food will burn less often?")
                task.chatNpc("Well, that's what the salesman told us anyway...")
                task.chatPlayer("Thanks?")
            }
        }
    }
}
