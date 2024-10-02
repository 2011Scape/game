package gg.rsmod.plugins.content.areas.morytania

import gg.rsmod.game.model.attr.RUNE_ESSENCE_REMAINING
import gg.rsmod.plugins.content.quests.advanceToNextStage
import gg.rsmod.plugins.content.quests.getCurrentStage
import gg.rsmod.plugins.content.quests.impl.PriestInPeril

// Drezel In Prison Cell
on_npc_option(npc = Npcs.DREZEL_1049, option = "talk-to", lineOfSightDistance = 3) {
    if (player.getCurrentStage(quest = PriestInPeril) == 4) {
        player.queue {
            findingDrezelDialogue(this, player)
        }
    }
    if (player.getCurrentStage(quest = PriestInPeril) == 5) {
        player.queue {
            cellKeyDialogue(this, player)
        }
    }
    if (player.getCurrentStage(quest = PriestInPeril) == 6) {
        player.queue {
            vampireDialogue(this, player)
        }
    }
    if (player.getCurrentStage(quest = PriestInPeril) >= 7 && player.tile.regionId == 13622) {
        player.queue {
            killedVampireDialogue(this)
        }
    }
}

// Drezel In Temple of Saradomin basement.
on_npc_option(npc = Npcs.DREZEL_7707, option = "talk-to") {
    if (player.getCurrentStage(quest = PriestInPeril) == 7 && player.tile.regionId == 13722) {
        player.queue {
            corruptionDialogue(this)
        }
    }
    if (player.getCurrentStage(quest = PriestInPeril) == 8 && player.tile.regionId == 13722) {
        player.queue {
            bringEssenceToDrezelDialogue(this, player)
            println("bringEssencetoDrezel Dialogue started.")
        }
    }
    if (player.getCurrentStage(quest = PriestInPeril) == 61 && player.tile.regionId == 13722) {
        player.queue {
            mainChat(this, player)
        }
    }
}

on_obj_option(obj = Objs.CELL_DOOR_3463, option = "open") {
    if (player.getCurrentStage(quest = PriestInPeril) <= 5) {
        player.queue {
            messageBox("The cell door is locked shut. You will need a key to open it.")
        }
    } else if (player.getCurrentStage(quest = PriestInPeril) >= 6) {
        handleCellDoor(player)
    }
}

fun handleCellDoor(player: Player) {
    val closedDoor =
        DynamicObject(id = Objs.CELL_DOOR_3463, type = 0, rot = 2, tile = Tile(x = 3413, z = 3487, height = 2))
    val invisibleDoor =
        DynamicObject(id = Objs.CELL_DOOR_3463, type = 1, rot = 2, tile = Tile(x = 3413, z = 3487, height = 2))
    val door = DynamicObject(id = Objs.CELL_DOOR_3463, type = 0, rot = 3, tile = Tile(x = 3414, z = 3487, height = 2))
    player.lock = LockState.DELAY_ACTIONS
    world.remove(closedDoor)
    world.spawn(invisibleDoor)
    player.playSound(Sfx.DOOR_OPEN)
    world.spawn(door)

    player.queue {
        val z = 3487
        val x = if (player.tile.x >= 3414) 3413 else 3414
        player.walkTo(tile = Tile(x = x, z = z), detectCollision = false)
        wait(3)
        world.remove(door)
        world.remove(invisibleDoor)
        player.lock = LockState.NONE
        world.spawn(closedDoor)
        player.playSound(Sfx.DOOR_CLOSE)
    }
}

on_obj_option(obj = Objs.CELL_DOOR_3463, option = "talk-through") {
    if (player.getCurrentStage(quest = PriestInPeril) == 4) {
        player.queue {
            findingDrezelDialogue(this, player)
        }
    }
    if (player.getCurrentStage(quest = PriestInPeril) == 5) {
        player.queue {
            cellKeyDialogue(this, player)
        }
    }
    if (player.getCurrentStage(quest = PriestInPeril) == 6) {
        player.queue {
            vampireDialogue(this, player)
        }
    }
}

suspend fun mainChat(
    it: QueueTask,
    player: Player,
) {
    it.player.queue {
        chatPlayer("So can I pass through that barrier now?")
        chatNpc(
            "Ah, ${player.username}. For all the assistance you have given",
            "both myself and Misthalin in your acounts, I cannot let",
            "you pass without warning you.",
            npc = 1049,
        )
        chatNpc(
            "Morytania is an evil land, filled with creatures and monsters",
            "more terrifying than you have yet encountered. Although",
            "I will pray for you.",
            npc = 1049,
        )
        chatNpc(
            "you should take some basic precautions before heading over",
            "the Salve into it. The first place you will come across",
            "is the Werewolf trading post.",
            npc = 1049,
        )
        chatNpc(
            "In many ways Werewolves are like you and me, except",
            "never forget that they are evil vicious beasts at heart.",
            "The dagger I have given you is named 'Wolfbane'.",
            npc = 1049,
        )
        chatNpc(
            "and it is a holy relic that prevents the werewolf",
            "people from changing form. I suggest if you battle with",
            "them that you keep it always equipped, for their",
            npc = 1049,
        )
        chatNpc(
            "wolf form is incredibly powerful, and would savage",
            "you very quickly. Please adventurer, promise me this:",
            "I should hate for you to die foolishly.",
            npc = 1049,
        )
        chatPlayer("Okay, I will keep it equipped whenever I fight werewolves.")
    }
}

suspend fun cellKeyDialogue(
    it: QueueTask,
    player: Player,
) {
    it.player.queue {
        chatNpc(
            "How goes it adventurer? Any luck in finding the key",
            "to the cell or a way of stopping the vampyre yet?",
            npc = 1049,
        )
        if (player.hasItem(Items.GOLDEN_KEY)) {
            chatPlayer("I have this key from one of those Zamorakian monks!")
            chatNpc(
                "Excellent work adventurer! Quickly, try it on the door,",
                "and see if it will free me!",
                npc = 1049,
            )
        } else if (player.hasItem(Items.IRON_KEY)) {
            chatPlayer(
                "I have this key I took from one of the",
                "monuments underground.",
            )
            chatNpc(
                "Excellent work adventurer! Quickly, try it on the door,",
                "and see if it will free me!",
                npc = 1049,
            )
        }
    }
}

suspend fun vampireDialogue(
    it: QueueTask,
    player: Player,
) {
    it.player.queue {
        chatPlayer("The key fitted the lock! You're free to leave now!")
        chatNpc(
            "Well excellent work adventurer! Unfortunately, as you",
            "know, I cannot risk waking that vampire in the coffin.",
            npc = 1049,
        )
        if (player.inventory.contains(item = Items.BUCKET_OF_WATER_2953)) {
            chatPlayer(
                "I have some water from the Salve. It seems to have",
                "been desecrated though. Do you think you could bless",
                "it for me?",
            )
            chatNpc(
                "Yes, good thinking adventurer! Give it to me, I will bless",
                "it!",
                npc = 1049,
            )
            player.message("The priest blesses the water for you.")
            player.inventory.remove(Items.BUCKET_OF_WATER_2953)
            player.inventory.add(Items.BUCKET_OF_WATER_2954)
        } else {
            chatPlayer("Do you have any ideas about dealing with the vampire?")
            chatNpc(
                "Well, the water of the Salve should still have enough",
                "power to work against the vampire despite what those",
                "Zamorakians might have done to it...",
                npc = 1049,
            )
            chatNpc(
                "Maybe you should try and get hold of some from",
                "somewhere?",
                npc = 1049,
            )
        }
    }
}

suspend fun killedVampireDialogue(it: QueueTask) {
    it.player.queue {
        chatPlayer(
            "I poured the blessed water over the vampire's coffin. I",
            "think that should trap him in there long enough for you",
            "to escape.",
        )
        chatNpc(
            "Excellent work adventurer! I am free at last! Let me",
            "ensure that evil vampire is trapped for good. I will meet",
            "you down by the monument.",
            npc = 1049,
        )
        chatNpc(
            "Look for me down there, I need to assess what damage",
            "has been done to our holy barrier by those evil",
            "Zamorakians!",
            npc = 1049,
        )
    }
}

suspend fun passingHolyBarrierBeforeSecureDialogue(it: QueueTask) {
    it.player.queue {
        chatNpc(
            "STOP!",
            npc = 1049,
            facialExpression = FacialExpression.ANGRY,
        )
        chatPlayer("Can't I go through there?")
        chatNpc(
            "No you cannot! It is taking all of my willpower",
            "to hold that barrier in place. You must restore the",
            "sanctity of the Salve as soon as possible!",
            npc = 1049,
        )
    }
}

on_obj_option(obj = Objs.HOLY_BARRIER, option = "pass-through") {
    if (player.getCurrentStage(quest = PriestInPeril) == 61) {
        player.moveTo(Tile(x = 3423, z = 3484, height = 0))
    } else {
        player.queue {
            passingHolyBarrierBeforeSecureDialogue(this)
        }
    }
}

suspend fun bringEssenceToDrezelDialogue(
    it: QueueTask,
    player: Player,
) {
    it.player.queue {
        val essenceNeeded = player.attr[RUNE_ESSENCE_REMAINING]
        if (essenceNeeded != null && essenceNeeded >= 0) {
            if (essenceNeeded == 0) {
                chatNpc(
                    "Excellent! That should do it! I will bless these stones",
                    "and place them within the well, and Misthalin should be",
                    "protected once more!",
                )
                chatNpc(
                    "Please take this dagger; it has been handed down within",
                    "my family for generations and is filled with the power of",
                    "Saradomin. You will find that",
                )
                chatNpc(
                    "it has the power to prevent werewolves from adopting",
                    "their wolf form in combat as long as you have it",
                    "equipped.",
                )
                PriestInPeril.finishQuest(player = player)
            }
            if (player.inventory.contains(Items.RUNE_ESSENCE)) {
                val runeEssenceCount = player.inventory.getItemCount(Items.RUNE_ESSENCE)
                if (runeEssenceCount >= essenceNeeded!!) {
                    player.inventory.remove(Items.RUNE_ESSENCE, amount = essenceNeeded)
                    player.attr[RUNE_ESSENCE_REMAINING] = 0
                    chatPlayer("I brought you the rune essence you needed.")
                    chatNpc("Quickly, give them to me!", npc = 1049)
                } else {
                    player.inventory.remove(Items.RUNE_ESSENCE, amount = runeEssenceCount)
                    player.attr[RUNE_ESSENCE_REMAINING] = essenceNeeded - runeEssenceCount
                    chatPlayer("I brought you some rune essence.")
                    chatNpc("Quickly, give them to me!", npc = 1049)
                    chatPlayer("I still need to find ${player.attr[RUNE_ESSENCE_REMAINING]} more.")
                }
            }
            if (player.inventory.contains(Items.PURE_ESSENCE)) {
                val pureEssenceCount = player.inventory.getItemCount(Items.PURE_ESSENCE)
                if (pureEssenceCount >= essenceNeeded!!) {
                    player.inventory.remove(Items.PURE_ESSENCE, amount = essenceNeeded)
                    player.attr[RUNE_ESSENCE_REMAINING] = 0
                    chatPlayer("I brought you the pure essence you needed.")
                    chatNpc("Quickly, give them to me!", npc = 1049)
                    chatNpc(
                        "Excellent! That should do it! I will bless these stones",
                        "and place them within the well, and Misthalin should be",
                        "protected once more!",
                    )
                    chatNpc(
                        "Please take this dagger; it has been handed down within",
                        "my family for generations and is filled with the power of",
                        "Saradomin. You will find that",
                    )
                    chatNpc(
                        "it has the power to prevent werewolves from adopting",
                        "their wolf form in combat as long as you have it",
                        "equipped.",
                    )
                    PriestInPeril.finishQuest(player = player)
                } else {
                    player.inventory.remove(Items.PURE_ESSENCE, amount = pureEssenceCount)
                    player.attr[RUNE_ESSENCE_REMAINING] = essenceNeeded - pureEssenceCount
                    chatPlayer("I brought you some pure essence.")
                    chatNpc("Quickly, give them to me!", npc = 1049)
                    chatPlayer("I still need to find ${player.attr[RUNE_ESSENCE_REMAINING]} more.")
                }
            } else if (!player.inventory.contains(Items.PURE_ESSENCE) &&
                !player.inventory.contains(Items.RUNE_ESSENCE)
            ) {
                chatPlayer("What am I supposed to do again?")
                chatNpc(
                    "Bring me ${player.attr[RUNE_ESSENCE_REMAINING]} rune essence so that I can undo",
                    "the damage done by those Zamorakians.",
                    npc = 1049,
                )
            }
        }
    }
}

suspend fun corruptionDialogue(it: QueueTask) {
    it.player.queue {
        chatNpc(
            "Ah, ${player.username}, I see you finally made it down here.",
            "Things are worse than I feared. I'm not sure if I will",
            "be able to repair the damage.",
            npc = 1049,
        )
        chatPlayer("Why, what happened?")
        chatNpc(
            "From what I can tell, after you killed the guard dog",
            "who protected the entrance to the monuments,",
            "those Zamorakians forced the door into the main chamber",
            npc = 1049,
        )
        chatNpc(
            "and have used some kind of evil potion upon the well",
            "which leads to the source of the river Salve. As they",
            "have done this at the very source of the river",
            npc = 1049,
        )
        chatNpc(
            "it will spread along the entire river, disrupting the",
            "blessing placed upon it and allowing evil creatures of",
            "Morytania to invade at their leisure.",
            npc = 1049,
        )
        chatPlayer("What can we do to prevent that?")
        chatNpc(
            "This passage is currently the only route between Morytania",
            "and Misthalin. The barrier here draws power from the river.",
            npc = 1049,
        )
        chatNpc(
            "I have managed to reinforce the barrier, but I must",
            "continue focussing on it to keep it intact. Although the",
            "passage is safe for now, I do not know how",
            "long I can keep it that way.",
            npc = 1049,
        )
        chatNpc(
            "This passage could be the least of our worries soon though.",
            "Before long, the vampyres will be able to cross at any point",
            "of the river. We won't be able to reinforce all of it",
            npc = 1049,
        )
        chatPlayer(
            "Couldn't you bless the river to purify it? Like you",
            "did with the water I took from the well?",
        )
        chatNpc(
            "No, that would not work. The power I have from",
            "Saradomin is not great enough to cleanse an entire",
            "river of this foul Zamorakian pollutant",
            npc = 1049,
        )
        chatNpc(
            "However, I do have one other idea.",
            npc = 1049,
        )
        chatPlayer("What's that?")
        chatNpc(
            "I believe we might be able to soak up the",
            "evil magic that the potion has released into the river",
            npc = 1049,
        )
        chatNpc(
            "I'm sure you know of runestones, used by mages to power",
            "their spells. The essence used to create these stones absorbs",
            "magical potential from runic altars to be released later.",
        )
        chatPlayer(
            "And you think we could use some rune essence to",
            "absorb the magic they've released into the Salve?",
        )
        chatNpc(
            "Exactly. If you could bring me fifty essence, I should",
            "be able to reverse the damage done. Be quick though.",
            "The longer we wait, the worse things will get.",
        )
        player.attr[RUNE_ESSENCE_REMAINING] = 50
        player.advanceToNextStage(PriestInPeril)
    }
}

suspend fun findingDrezelDialogue(
    it: QueueTask,
    player: Player,
) {
    it.player.queue {
        chatPlayer("Hello.")
        chatNpc(
            "Oh! You do not appear to be one of those Zamorakians",
            "who imprisoned me here! Who are you and why are",
            "you here?",
        )
        chatPlayer(
            "My name's ${player.username}. King Roald sent me to find out what",
            "was going on at the temple. I take it you are Drezel?",
        )
        chatNpc(
            "That is right! Oh, praise be to Saradomin! All is not yet",
            "lost! I feared that when those Zamorakians attacked this",
            "place and imprisoned",
        )
        chatNpc(
            "me up here, Misthalin would be doomed! If they should",
            "manage to desecrate the holy river Salve we would be",
            "defenceless against Morytania!",
        )
        chatPlayer("How is a river a good defence then?")
        chatNpc("Well, it is a long tale, and I am not sure we have time!")
        when (
            options(
                "Tell me anyway.",
                "You're right, we don't.",
            )
        ) {
            FIRST_OPTION -> {
                chatPlayer(
                    "Tell me anyway. I'd like to know the full facts before",
                    "acting any further.",
                )
                chatNpc(
                    "Ah, Saradomin has granted you wisdom I see. Well, the",
                    "story of the river Salbe and of how it protects Misthalin",
                    "is the story of this temple,",
                )
                chatNpc(
                    "and of the seven warrior priests who died here long ago,",
                    "from whom I am descended. Once long ago Misthalin",
                    "did not have the borders that",
                )
                chatNpc(
                    "it currently does. This entire area, as far West as",
                    "Varrock itself was under the control of an evil god.",
                    "There was frequent skirmishing",
                )
                chatNpc(
                    "along the borders, as the brave heroes of Varrock",
                    "fought to keep the evil creatures that now are tapped",
                    "on the eastern side of the River Salve from over",
                    "running",
                )
                chatNpc(
                    "the human encampments, who worshipped Saradomin.",
                    "Then one day, Saradomin himself appeared to one of",
                    "our mighty heroes, whose name has been forgotten by",
                    "history,",
                )
                chatNpc(
                    "and told him that should we be able to take the pass that",
                    "this temple now stands in, Saradomin would use his",
                    "power to bless this river, and make it",
                )
                chatNpc(
                    "impassable to all creatures with evil in their hearts. This",
                    "unknown hero grouped together all of the mightiest",
                    "fighters whose hearts were pure",
                )
                chatNpc(
                    "that he could find, and the seven of them rode here to",
                    "make a final stand. The enemies swarmed across the",
                    "Salve but they did not yield.",
                )
                chatNpc(
                    "For ten days and nights they fought, never sleeping,",
                    "never eating, fuelled by their desire to make the world a",
                    "better place for humans to live.",
                )
                chatNpc(
                    "On the eleventh day they were to be joined by",
                    "reinforcements from a neighbouring encampment, but",
                    "when those reinforcements arrived all they found",
                )
                chatNpc(
                    "were the bodies of these seven brave but unknown",
                    "heroes, surrounded by the piles of the dead creatures of",
                    "evil that had tried to defeat them.",
                )
                chatNpc(
                    "The men were saddened at the loss of such pure and",
                    "mighty warriors, yet their sacrifice had not been in",
                    "vain; for the water of the Salve",
                )
                chatNpc(
                    "had indeed been filled with the power if Saradomin, and",
                    "the evil creatures of Morytania were trapped beyond",
                    "the river banks forever, by their own evil.",
                )
                chatNpc(
                    "who wish to destroy it, and laid the bodies of those brave",
                    "warriors in tombs of honour below this temple with",
                    "golden gifts on the tombs as marks of respect.",
                )
                chatNpc(
                    "They also built a statue on the river source so that all",
                    "who might try and cross into Misthalin from Morytania",
                    "would know that these lands are protected",
                )
                chatNpc(
                    "by the glory of Saradomin and that good will always",
                    "defeat evil, no matter how how the odds are stacked against",
                    "them.",
                )
                chatPlayer(
                    "Ok, I can see how the river protects the border, but I",
                    "can't see how anything could affect that from this",
                    "temple.",
                )
                chatNpc(
                    "Well, as much as it saddens me to say so adventurer,",
                    "Lord Saradomin's presence has not been felt on the",
                    "land for many years now, and even",
                )
                chatNpc(
                    "though all true Saradominists know that he watches over.",
                    "us, his power upon the land is not has strong as it once",
                    "was.",
                )
                chatNpc(
                    "the army of evil that lurks to the east, longing for the",
                    "opportunity to invade and destroy us all!",
                )
                chatNpc(
                    "So what do say adventurer? Will you aid me and",
                    "all of Misthalin in foiling this Zamorakian plot?",
                )
                when (
                    options(
                        "Yes.",
                        "No.",
                    )
                ) {
                    FIRST_OPTION -> {
                        chatPlayer(
                            "Yes, of course Any threat to Misthalin must be",
                            "neutralised immediately. So what can I do to help?",
                        )
                        chatNpc(
                            "Well, the immediate problem is that I am trapped in this",
                            "cell I know that the key to free me is nearby, for none",
                            "of the Zamorakians",
                        )
                        chatNpc(
                            "Who imprisoned me here were ever gone for long",
                            "periods of time. Should you find the key, however, as",
                            "you may have noticed,",
                        )
                        chatNpc(
                            "there is a vampire in that coffin over there. I do not",
                            "know how they managed to find it, but it is one of the",
                            "ones that somehow",
                        )
                        chatNpc(
                            "survived the battle here all those years agom and is by",
                            "now quite, quite, mad. It has been trapped on this side",
                            "of the river for centuries,",
                        )
                        chatNpc(
                            "and as those fiendish Zamorakians pointed out to me",
                            "with delight, as a descendant of one of those who",
                            "trapped it here, it will recognise",
                        )
                        chatNpc(
                            "the smell of my blood should I come anywhere near it.",
                            "It will of course then wake up and kill me, very",
                            "probably slowly and painfully",
                        )
                        chatPlayer("Maybe I could kill it somehow then while it is asleep?")
                        chatNpc(
                            "No adventurer, I do not think it would be wise for you",
                            "to wake it at all. As I say, it is little more than a wild",
                            "animal, and must",
                        )
                        chatNpc(
                            "be extremely powerful to have survived until today. I",
                            "suspect your best chance would be to incapacitate it",
                            "somehow.",
                        )
                        chatPlayer(
                            "Okay, find the key to your cell, and do something about",
                            "the vampire",
                        )
                        chatNpc(
                            "When you have done both of those I will be able to",
                            "inspect the damage which those Zamorakians have done",
                            "to the purity of the Salve",
                        )
                        chatNpc(
                            "Depending on the severity of the damage, I may",
                            "require further assistance from you in restoring its",
                            "purity.",
                        )
                        chatPlayer("Okay, well first thing's first, let's get you out of here.")
                        player.advanceToNextStage(PriestInPeril)
                    }
                    SECOND_OPTION -> {
                        chatPlayer("No.")
                        chatNpc(
                            "Oooooh... I knew it was too good to be true...",
                            "Then leave me to my fate villain, there's no need",
                            "to taunt me as well as keeping me imprisoned.",
                        )
                    }
                }
            }
            SECOND_OPTION -> {
                chatPlayer("You're right, we don't. it doesn't matter anyway.")
                chatNpc(
                    "Well, let's just say if we cannot undo whatever damage",
                    "has been done here, the entire land is in grave peril!",
                )
                chatNpc(
                    "So what do you say adventurer? Will you aid me and",
                    "all of Misthalin in foiling this Zamorakian plot?",
                )
                when (
                    options(
                        "Yes.",
                        "No.",
                    )
                ) {
                    FIRST_OPTION -> {
                        chatPlayer(
                            "Yes, of course Any threat to Misthalin must be",
                            "neutralised immediately. So what can I do to help?",
                        )
                        chatNpc(
                            "Well, the immediate problem is that I am trapped in this",
                            "cell I know that the key to free me is nearby, for none",
                            "of the Zamorakians",
                        )
                        chatNpc(
                            "Who imprisoned me here were ever gone for long",
                            "periods of time. Should you find the key, however, as",
                            "you may have noticed,",
                        )
                        chatNpc(
                            "there is a vampire in that coffin over there. I do not",
                            "know how they managed to find it, but it is one of the",
                            "ones that somehow",
                        )
                        chatNpc(
                            "survived the battle here all those years agom and is by",
                            "now quite, quite, mad. It has been trapped on this side",
                            "of the river for centuries,",
                        )
                        chatNpc(
                            "and as those fiendish Zamorakians pointed out to me",
                            "with delight, as a descendant of one of those who",
                            "trapped it here, it will recognise",
                        )
                        chatNpc(
                            "the smell of my blood should I come anywhere near it.",
                            "It will of course then wake up and kill me, very",
                            "probably slowly and painfully",
                        )
                        chatPlayer("Maybe I could kill it somehow then while it is asleep?")
                        chatNpc(
                            "No adventurer, I do not think it would be wise for you",
                            "to wake it at all. As I say, it is little more than a wild",
                            "animal, and must",
                        )
                        chatNpc(
                            "be extremely powerful to have survived until today. I",
                            "suspect your best chance would be to incapacitate it",
                            "somehow.",
                        )
                        chatPlayer(
                            "Okay, find the key to your cell, and do something about",
                            "the vampire",
                        )
                        chatNpc(
                            "When you have done both of those I will be able to",
                            "inspect the damage which those Zamorakians have done",
                            "to the purity of the Salve",
                        )
                        chatNpc(
                            "Depending on the severity of the damage, I may",
                            "require further assistance from you in restoring its",
                            "purity.",
                        )
                        chatPlayer("Okay, well first thing's first, let's get you out of here.")
                        player.advanceToNextStage(PriestInPeril)
                    }
                    SECOND_OPTION -> {
                        chatPlayer("No.")
                        chatNpc(
                            "Oooooh... I knew it was too good to be true...",
                            "Then leave me to my fate villain, there's no need",
                            "to taunt me as well as keeping me imprisoned.",
                        )
                    }
                }
            }
        }
    }
}
