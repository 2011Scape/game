package gg.rsmod.plugins.content.areas.asgarnia

import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.TheKnightsSword
import gg.rsmod.plugins.content.quests.startedQuest
import gg.rsmod.plugins.content.skills.Skillcapes

val knightsSword = TheKnightsSword

/**
 * Binds the "Talk-To" option for [Npcs.THURGO]
 * @author Kevin Senez <ksenez94@gmail.com>
 */
on_npc_option(npc = Npcs.THURGO, option = "talk-to") {
    player.queue {
        if (!player.startedQuest(knightsSword)) {
            when (options("Are you an Imcando dwarf? I need a special sword.", "What is that cape you're wearing?")) {
                1 -> getDialogue(this, player, option = "Are you an Imcando dwarf? I need a special sword.")
                2 -> getDialogue(this, player, option = "What is that cape you're wearing?")
            }
        } else if (player.getCurrentStage(knightsSword) == 2) {
            if (player.inventory.contains(Items.REDBERRY_PIE)) {
                when (
                    options(
                        "Are you an Imcando dwarf? I need a special sword.",
                        "Would you like a redberry pie?",
                        "What is that cape you're wearing?",
                    )
                ) {
                    1 -> getDialogue(this, player, option = "Are you an Imcando dwarf? I need a special sword.")
                    2 -> getDialogue(this, player, option = "Would you like a redberry pie?")
                    3 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                }
            } else {
                when (options("Are you an Imcando dwarf?", "What is that cape you're wearing?")) {
                    1 -> getDialogue(this, player, option = "Are you an Imcando dwarf?")
                    2 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                }
            }
        } else if (player.getCurrentStage(knightsSword) == 3) {
            when (options("Can you make a special sword for me?", "What is that cape you're wearing?")) {
                1 -> {
                    chatPlayer("Can you make a special sword for me?")
                    chatNpc(
                        *"Well, after bringing me my favorite food I guess I should give it a go. What sort of sword is it?"
                            .splitForDialogue(),
                    )
                    chatPlayer(
                        *"I need you to make a sword for one of Falador's knights. He had one which was passed down through five generations, but his squire has lost it."
                            .splitForDialogue(),
                    )
                    chatPlayer("So we need an identical one to replace it.")
                    chatNpc(
                        *"A knight's sword eh? Well, I'd need to know exactly how it looked before I could make a new one."
                            .splitForDialogue(),
                    )
                    chatNpc(
                        *"All the Faladian knights used to have swords with unique designs according to their position. Could you bring me a picture or something?"
                            .splitForDialogue(),
                    )
                    chatPlayer(*"I'll go and ask his squire and see if I can find one.".splitForDialogue())
                    player.advanceToNextStage(knightsSword)
                }

                2 -> getDialogue(this, player, option = "What is that cape you're wearing?")
            }
        } else if (player.getCurrentStage(knightsSword) == 5) {
            if (player.inventory.contains(Items.REDBERRY_PIE)) {
                when (
                    options(
                        "About that sword...",
                        "Would you like a redberry pie?",
                        "What is that cape you're wearing?",
                    )
                ) {
                    1 -> getDialogue(this, player, option = "About that sword...")
                    2 -> getDialogue(this, player, option = "Would you like a redberry pie?")
                    3 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                }
            } else {
                when (options("About that sword...", "What is that cape you're wearing?")) {
                    1 -> getDialogue(this, player, option = "About that sword...")
                    2 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                }
            }
        } else if (player.getCurrentStage(knightsSword) == 6) {
            if (!player.inventory.contains(Items.BLURITE_SWORD)) {
                if (player.inventory.contains(Items.REDBERRY_PIE)) {
                    when (
                        options(
                            "Can you make that replacement sword now?",
                            "Would you like a redberry pie?",
                            "What is that cape you're wearing?",
                        )
                    ) {
                        1 -> getDialogue(this, player, option = "Can you make that replacement sword now?")
                        2 -> getDialogue(this, player, option = "Would you like a redberry pie?")
                        3 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                    }
                } else {
                    when (options("Can you make that replacement sword now?", "What is that cape you're wearing?")) {
                        1 -> getDialogue(this, player, option = "Can you make that replacement sword now?")
                        2 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                    }
                }
            } else {
                if (player.inventory.contains(Items.REDBERRY_PIE)) {
                    when (
                        options(
                            "Thanks for making that sword for me!",
                            "Would you like a redberry pie?",
                            "What is that cape you're wearing?",
                        )
                    ) {
                        1 -> {
                            chatPlayer("Thanks for making that sword for me!")
                            chatNpc("You're welcome - thanks for the pie!")
                        }

                        2 -> getDialogue(this, player, option = "Would you like a redberry pie?")
                        3 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                    }
                } else {
                    when (options("Thanks for making that sword for me!", "What is that cape you're wearing?")) {
                        1 -> {
                            chatPlayer("Thanks for making that sword for me!")
                            chatNpc("You're welcome - thanks for the pie!")
                        }

                        2 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                    }
                }
            }
        } else {
            if (player.finishedQuest(knightsSword)) {
                when (options("What is that cape you're wearing?", "Something else.")) {
                    1 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                    2 -> {
                        chatPlayer(
                            *"About that sword... Thanks for all your help in getting it for me!".splitForDialogue(),
                        )
                        chatNpc("No worries mate.")
                    }
                }
            } else {
                when (options("What is that cape you're wearing?", "Nevermind.")) {
                    1 -> getDialogue(this, player, option = "What is that cape you're wearing?")
                    2 -> chatPlayer("Nevermind.")
                }
            }
        }
    }
}

/**
 * Returns the appropriate dialogue corresponding with the question asked.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
suspend fun getDialogue(
    task: QueueTask,
    player: Player,
    option: String,
) {
    when (option) {
        "Are you an Imcando dwarf? I need a special sword." -> {
            task.chatPlayer("Are you an Imcando dwarf? I need a special sword.")
            task.chatNpc(*"I don't talk about that sort of thing anymore. I'm getting old.".splitForDialogue())
            task.chatPlayer("I'll come back another time.")
        }

        "Would you like a redberry pie?" -> {
            task.chatPlayer("Would you like a redberry pie?")
            task.messageBox("You see Thurgo's eyes light up.")
            task.chatNpc(
                *"I'd never say no to a redberry pie! We Imcando dwarves love them - They're GREAT!".splitForDialogue(),
            )
            if (player.inventory.remove(item = Items.REDBERRY_PIE, assureFullRemoval = true).hasSucceeded()) {
                if (player.getCurrentStage(knightsSword) == 2) {
                    player.advanceToNextStage(knightsSword)
                }
                task.messageBox("You hand over the pie.")
                task.messageBox("Thurgo eats the pie.")
                task.messageBox("Thurgo pats his stomach.")
                task.chatNpc(
                    *"By Guthix! THAT was good pie! Anyone who makes pie like that has got to be alright!"
                        .splitForDialogue(),
                )
            }
        }

        "What is that cape you're wearing?" -> {
            task.chatPlayer("What is that cape you're wearing?")
            task.chatNpc(
                *"It's a Skillcape of Smithing. It shows that I'm a master blacksmith, but that's only to be expected - after all, my ancestors were the greatest blacksmiths in dwarven history."
                    .splitForDialogue(),
            )
            if (player.skills.getMaxLevel(Skills.SMITHING) < 99) {
                task.chatNpc(
                    *"If you ever achieve level 99 Smithing you'll be able to wear a cape like this.".splitForDialogue(),
                )
            } else {
                task.chatNpc(
                    *"I reckon so; us master smiths must stick together, so I'll give it to you for just 99,000 coins."
                        .splitForDialogue(),
                )
                when (
                    task.options(
                        "99,000 coins? That's much too expensive.",
                        "I think I have the money right here, actually.",
                    )
                ) {
                    1 -> {
                        task.chatPlayer("99,000 coins? That's too much expensive.")
                        task.chatNpc(
                            *"Not at all; there are many other adventurers who would love the opportunity to purchase such a prestigious item! You can find me here if you change your mind."
                                .splitForDialogue(),
                        )
                    }

                    2 -> {
                        task.chatPlayer("I think I have the money right here, actually.")
                        if (player.inventory.getItemCount(Items.COINS_995) >= 99_000) {
                            Skills.purchaseSkillcape(player, data = Skillcapes.SMITHING)
                            task.chatNpc("Excellent! Wear that cape with pride my friend.")
                        } else {
                            task.chatPlayer("But, unfortunately, I was mistaken.")
                            task.chatNpc("Well, come back and see me when you do.")
                        }
                    }
                }
            }
        }

        "About that sword..." -> {
            if (player.getCurrentStage(knightsSword) == 5) {
                if (!player.inventory.contains(Items.PORTRAIT)) {
                    task.chatPlayer("About that sword...")
                    task.chatNpc("Have you got a picture of the sword for me yet?")
                    task.chatPlayer("Sorry, not yet.")
                    task.chatNpc("Well, come back when you do.")
                } else {
                    task.chatPlayer("About that sword...")
                    task.chatNpc("Have you got a picture of the sword for me yet?")
                    task.chatPlayer(*"I have found a picture of the sword I would like you to make.".splitForDialogue())
                    task.messageBox("You give the Portrait to Thurgo. Thurgo studies the portrait.")
                    if (player.inventory.remove(Items.PORTRAIT, assureFullRemoval = true).hasSucceeded()) {
                        player.advanceToNextStage(knightsSword)
                    }
                    task.chatNpc(
                        *"You'll need to get me some stuff to make this. I'll need two iron bars to make the sword, to start with. I'll also need an ore call blurite."
                            .splitForDialogue(),
                    )
                    task.chatNpc(
                        *"Blurite is useless for making actual weapons, except crossbows, but I'll need some as decoration for the hilt."
                            .splitForDialogue(),
                    )
                    task.chatNpc(
                        *"It is a fairly rare ore. The only place I know to get it is under this cliff here, but it is guarded by a very powerful ice giant."
                            .splitForDialogue(),
                    )
                    task.chatNpc(
                        *"Most of the rocks in that cliff are pretty useless, and don't contain much of anything, but there's DEFINITELY some blurite in there."
                            .splitForDialogue(),
                    )
                    task.chatNpc(
                        *"You'll need a little bit of mining experience to be able to find it.".splitForDialogue(),
                    )
                    task.chatPlayer("Okay. I'll go and find them then.")
                }
            }
        }

        "Can you make that replacement sword now?" -> {
            val ironBars = Item(Items.IRON_BAR, amount = 2)
            if (player.inventory.contains(Items.BLURITE_ORE) && player.inventory.getItemCount(Items.IRON_BAR) >= 2) {
                task.chatPlayer("Can you make that replacement sword now?")
                task.chatNpc("How are you doing finding those sword materials?")
                task.chatPlayer("I have them right here.")
                task.doubleMessageBox("You give the blurite ore and iron bars to Thurgo.", "Thurgo makes you a sword.")
                if (player.inventory.remove(Items.BLURITE_ORE).hasSucceeded() &&
                    player.inventory
                        .remove(ironBars)
                        .hasSucceeded()
                ) {
                    player.inventory.add(Items.BLURITE_SWORD, assureFullInsertion = true)
                    task.chatPlayer("Thank you very much!")
                    task.chatNpc("Just remember to call in with more pie some time!")
                }
            } else {
                task.chatPlayer("Can you make that replacement sword now?")
                task.chatNpc("How are you doing finding those sword materials?")
                if (!player.inventory.contains(Items.BLURITE_ORE) &&
                    player.inventory.getItemCount(Items.IRON_BAR) < 2
                ) {
                    task.chatPlayer("I don't have any of them yet.")
                    task.chatNpc(
                        *"Well, I need a blurite ore and two iron bars. The only place I know to get blurite is under this cliff here, but it is guarded by a very powerful ice giant."
                            .splitForDialogue(),
                    )
                } else if (player.inventory.contains(Items.BLURITE_ORE) &&
                    player.inventory.getItemCount(Items.IRON_BAR) < 2
                ) {
                    task.chatPlayer("I don't have two iron bars.")
                    task.chatNpc("Better go get some then, huh?")
                } else if (player.inventory.getItemCount(Items.IRON_BAR) >= 2 &&
                    !player.inventory.contains(Items.BLURITE_ORE)
                ) {
                    task.chatPlayer("I don't have any blurite ore yet.")
                    task.chatNpc(
                        *"Better go get some then, huh? The only place I know to get blurite is under this cliff here, but it is guarded by a very powerful ice giant."
                            .splitForDialogue(),
                    )
                }
            }
        }
    }
}
