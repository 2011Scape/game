package gg.rsmod.plugins.content.areas.falador

import gg.rsmod.plugins.content.quests.*
import gg.rsmod.plugins.content.quests.impl.TheKnightsSword

val knightsSword = TheKnightsSword

/**
 * Binds the option "talk-to" for npc [Npcs.SQUIRE]
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_npc_option(npc = Npcs.SQUIRE, option = "talk-to") {
    player.queue {
        chatNpc("Hello. I am the squire to Sir Vyvin.")
        when (player.getCurrentStage(knightsSword)) {
            0 -> {
                when (options("And how is life as a squire?", "Wouldn't you prefer to be a squire for me?")) {
                    1 -> {
                        chatPlayer("And how is life as a squire?")
                        chatNpc(
                            *"Well, Sir Vyvin is a good guy to work for, however, I'm in a spot of trouble today. I've gone and lost Sir Vyvin's sword!"
                                .splitForDialogue(),
                        )
                        when (
                            options(
                                "Do you know where you lost it?",
                                "I can make a new sword if you like...",
                                "Is he angry?",
                            )
                        ) {
                            1 -> {
                                chatPlayer("Do you know where you lost it?")
                                chatNpc(
                                    *"Well now, if I knew THAT it wouldn't be lost, now would it?".splitForDialogue(),
                                )
                                when (
                                    options(
                                        "Well, do you know the VAGUE AREA you lost it?",
                                        "I can make a new sword if you like...",
                                    )
                                ) {
                                    1 ->
                                        sendCommonResponse(
                                            this,
                                            question = "Well, do you know the VAGUE AREA you lost it?",
                                        )

                                    2 -> sendCommonResponse(this, question = "I can make a new sword if you like...")
                                }
                            }

                            2 -> sendCommonResponse(this, question = "I can make a new sword if you like...")
                            3 -> sendCommonResponse(this, question = "Is he angry?")
                        }
                    }

                    2 -> {
                        chatPlayer("Wouldn't you prefer to be a squire for me?")
                        chatNpc("No, sorry, I'm loyal to Sir Vyvin.")
                    }
                }
            }

            1 -> { // When player has been sent to find Thurgo but hasn't gone yet.
                chatNpc("So how are you doing getting a sword?")
                chatPlayer("I'm looking for Imcando dwarves to help me.")
                chatNpc(*"Please try and find them quickly... I am scared Sir Vyvin will find out!".splitForDialogue())
            }

            4 -> { // Make player go find the portrait in the cupboard of Sir Vyvin's room
                chatNpc("So how are you doing getting a sword?")
                chatPlayer(
                    *"I've found an Imcando dwarf but he needs a picture of the sword before he can make it."
                        .splitForDialogue(),
                )
                chatNpc(
                    *"A picture eh? Hmmm.... The only one I can think of is in a small portrait of Sir Vyvin's father... Sir Vyvin keeps it in a cupboard in his room I think."
                        .splitForDialogue(),
                )
                chatPlayer("Ok, I'll try and get that then.")
                chatNpc(*"Please don't let him catch you! He MUSTN'T know what happened!".splitForDialogue())
                player.advanceToNextStage(knightsSword)
            }

            5 -> { // If player has found the portrait or not.
                if (!player.inventory.contains(Items.PORTRAIT)) {
                    chatNpc("So how are you doing getting a sword?")
                    chatPlayer("I didn't get the picture yet.")
                    chatNpc(*"Please try and get it quickly... I am scared Sir Vyvin will find out!".splitForDialogue())
                } else {
                    chatNpc("So how are you doing getting a sword?")
                    chatPlayer("I have the picture. I'll just take it to the dwarf now!")
                    chatNpc("Please hurry!")
                }
            }

            6 -> { // If player comes back with the Blurite sword.
                if (player.inventory.contains(Items.BLURITE_SWORD)) {
                    chatPlayer("I have retrieved your sword for you.")
                    chatNpc(
                        *"Thank you, thank you, thank you! I was seriously worried I would have to own up to Sir Vyvin!"
                            .splitForDialogue(),
                    )
                    itemMessageBox("You give the sword to the squire", item = Items.BLURITE_SWORD)
                    TheKnightsSword.finishQuest(player)
                } else if (player.equipment.hasAt(EquipmentType.WEAPON.id, Items.BLURITE_SWORD)) {
                    chatPlayer("I have retrieved your sword for you.")
                    chatNpc("So can you un-equip it and hand it over to me now please?")
                }
            }
        }
    }
}

/**
 * Sends the appropriate chats according to the option clicked.
 * Made it easier to manage all the different chat possibilities and answers.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
suspend fun sendCommonResponse(
    task: QueueTask,
    question: String,
) {
    when (question) {
        "I can make a new sword if you like..." -> {
            task.chatPlayer(*"I can make a new sword if you like...".splitForDialogue())
            task.chatNpc(*"Thanks for the offer. I'd be surprised if you could though.".splitForDialogue())
            task.chatNpc(
                *"The thing is, this sword if a family heirloom. It has been passed down through Vyvin's family for five generations! It was originally made by the Imcando dwarves, who were"
                    .splitForDialogue(),
            )
            task.chatNpc(
                *"a particularly skilled tribe of dwarven smiths. I doubt anyone could make it in the style they do."
                    .splitForDialogue(),
            )
            when (task.options("So would these dwarves make another one?", "Well I hope you find it soon.")) {
                1 -> sendCommonResponse(task, question = "So would these dwarves make another one?")
                2 -> sendCommonResponse(task, question = "Well I hope you find it soon.")
            }
        }

        "Well, I hope you find it soon.", "Well I hope you find it soon." -> {
            task.chatPlayer("Well, I hope you find it soon.")
            task.chatNpc(
                *"Yes, me too. I'm not looking forward to telling Vyvin I've lost it. He's going to want it for the parade next week as well."
                    .splitForDialogue(),
            )
        }

        "Well, the kingdom is fairly abundant with swords..." -> {
            task.chatPlayer("Well, the kingdom is fairly abundant with swords...")
            task.chatNpc(*"Yes, you can get bronze swords anywhere. But THIS isn't any old sword.".splitForDialogue())
            task.chatNpc(
                *"The thing is, this sword if a family heirloom. It has been passed down through Vyvin's family for five generations! It was originally made by the Imcando dwarves, who were"
                    .splitForDialogue(),
            )
            task.chatNpc(
                *"a particularly skilled tribe of dwarven smiths. I doubt anyone could make it in the style they do."
                    .splitForDialogue(),
            )
            when (task.options("So would these dwarves make another one?", "Well I hope you find it soon.")) {
                1 -> sendCommonResponse(task, question = "So would these dwarves make another one?")
                2 -> sendCommonResponse(task, question = "Well I hope you find it soon.")
            }
        }

        "Is he angry", "Is he angry?" -> {
            task.chatPlayer("Is he angry?")
            task.chatNpc(
                *"He doesn't know yet. I was hoping I could think of something to do before he does find out, But I find myself at a loss."
                    .splitForDialogue(),
            )
            when (
                task.options(
                    "Well, do you know the VAGUE AREA you lost it?",
                    "I can make a new sword if you like...",
                    "Well, the kingdom is fairly abundant with swords...",
                    "Well, I hope you find it soon.",
                )
            ) {
                1 -> sendCommonResponse(task, question = "Well, do you know the VAGUE AREA you lost it?")
                2 -> sendCommonResponse(task, question = "I can make a new sword if you like...")
                3 -> sendCommonResponse(task, question = "Well, the kingdom is fairly abundant with swords...")
                4 -> sendCommonResponse(task, question = "Well, I hope you find it soon.")
            }
        }

        "So would these dwarves make another one?" -> {
            task.chatPlayer("So would these dwarves make another one?")
            task.chatNpc(
                *"I'm not a hundred percent sure the Imcando tribe exists anymore. I should think Reldo, the palace librarian in Varrock, will know; he has done a lot of research on the races of Gielinor."
                    .splitForDialogue(),
            )
            task.chatNpc(
                *"I don't suppose you could try and track down the Imcando dwarves for me? I've got so much work to do..."
                    .splitForDialogue(),
            )
            when (task.options("Ok, I'll give it a go.", "No, I've got lots of mining work to do.")) {
                1 -> {
                    if (task.player.canStartQuest(knightsSword)) {
                        task.chatPlayer("Ok, I'll give it a go.")
                        task.chatNpc(
                            *"Thank you very much! As I say, the best place to start should be with Reldo..."
                                .splitForDialogue(),
                        )
                        task.player.startQuest(knightsSword)
                    } else {
                        task.chatNpc("I'm sorry but it doesn't seem that you are experienced enough for this quest.")
                        task.messageBox(
                            "You need a Mining level of 10 to be able to start ${red("The Knight's Sword")}.",
                        )
                    }
                }

                2 -> {
                    task.chatPlayer("No, I've got lots of mining work to do.")
                    task.chatNpc("Oh man... I'm in such trouble...")
                }
            }
        }

        "Well, do you know the VAGUE AREA you lost it?" -> {
            task.chatPlayer("Well, do you know the VAGUE AREA you lost it in?")
            task.chatNpc(
                *"No. I was carrying it for him all the way from where he had it stored in Lumbridge. It must have slipped from my pack during the trip, and you know what people are like these days..."
                    .splitForDialogue(),
            )
            task.chatNpc(*"Someone will have just picked it up and kept it for themselves.".splitForDialogue())
            when (
                task.options(
                    "I can make a new sword if you like...",
                    "Well, the kingdom is fairly abundant with swords...",
                    "Well, I hope you find it soon.",
                )
            ) {
                1 -> sendCommonResponse(task, question = "I can make a new sword if you like...")
                2 -> sendCommonResponse(task, question = "Well, the kingdom is fairly abundant with swords...")
                3 -> sendCommonResponse(task, question = "Well, I hope you find it soon.")
            }
        }
    }
}
