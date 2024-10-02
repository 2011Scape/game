package gg.rsmod.plugins.content.mechanics.identitykit

import gg.rsmod.game.sync.block.UpdateBlockType

/**
 * @author Alycia <https://github.com/alycii>
 */

val SKIN_VARBIT = 6099
val GENDER_VARBIT = 6098

val BEARD_ENUM = 703
val SKIN_COLOR_ENUM = 748

/**
 * Handles skin color selection
 */
for (button in 20..31) {
    on_button(interfaceId = 900, button) {
        player.setVarbit(SKIN_VARBIT, world.definitions.get(EnumDef::class.java, SKIN_COLOR_ENUM).getInt(button - 20))
    }
}

/**
 * Handles gender selection
 */

// male
on_button(interfaceId = 900, component = 16) {
    player.setVarbit(GENDER_VARBIT, 0)
}

// female
on_button(interfaceId = 900, component = 17) {
    player.setVarbit(GENDER_VARBIT, 1)
}

/**
 * Handles confirmation
 */
on_button(interfaceId = 900, component = 33) {
    player.closeInterface(interfaceId = 900)
    player.appearance.colors[5] = player.getVarbit(SKIN_VARBIT)
    if (player.appearance.gender.id != player.getVarbit(GENDER_VARBIT)) {
        swapSex(player, player.getVarbit(GENDER_VARBIT) == 0)
    }
    player.queue {
        if (world.random(2) == 1) {
            chatNpc("Woah!")
            chatPlayer("What?")
            chatNpc("You still look human!")
            chatPlayer("Uh... Thanks, I guess.")
        } else {
            chatNpc("Hmm.. you didn't feel any unexpected growths", "anywhere around your head just then did you?")
            chatPlayer("Uh... no...?")
            chatNpc("Good, good! I was worried for a second there!")
            chatPlayer("Uh... Thanks, I guess.")
        }
    }
    player.addBlock(UpdateBlockType.APPEARANCE)
}

on_interface_open(interfaceId = 900) {
    player.setComponentText(interfaceId = 900, component = 33, "CONFIRM")
    player.setVarbit(GENDER_VARBIT, if (player.appearance.gender.isMale()) 0 else 1)
}

/**
 * Swaps the gender and look of the player,
 * ensuring that the clothing stays as close as possible
 * and randomizes hair.
 *
 * @Credits Greg <https://github.com/GregHib>
 */
fun swapSex(
    player: Player,
    male: Boolean,
) {
    val enumValue =
        world.definitions
            .get(
                EnumDef::class.java,
                if (male) EnumDef.MALE_HAIR_STRUCT else EnumDef.FEMALE_HAIR_STRUCT,
            ).getRandomInt()
    val structValue = world.definitions.get(StructDef::class.java, enumValue).getInt(788)
    player.appearance.looks[0] = structValue // hair
    player.appearance.looks[1] =
        if (male) world.definitions.get(EnumDef::class.java, BEARD_ENUM).getRandomInt() else 1000 // beard
    swapLook(player, male, 2) // body
    swapLook(player, male, 5) // legs
    player.appearance.looks[6] = if (male) 42 else 79 // feet
    player.appearance.gender = if (male) Gender.MALE else Gender.FEMALE
    player.addBlock(UpdateBlockType.APPEARANCE)
}

/**
 * Helper function for looking up
 * clothing parts that match the gender swap
 *
 * @Credits Greg <https://github.com/GregHib>
 */
fun swapLook(
    player: Player,
    male: Boolean,
    bodyPart: Int,
) {
    val old =
        world.definitions.get(
            EnumDef::class.java,
            getBodyStyleEnum(
                if (male) Gender.FEMALE else Gender.MALE,
                bodyPart - 2,
            ),
        )
    val new =
        world.definitions.get(
            EnumDef::class.java,
            getBodyStyleEnum(
                if (male) Gender.MALE else Gender.FEMALE,
                bodyPart - 2,
            ),
        )
    val key = old.getKeyForValue(player.appearance.looks[bodyPart])
    player.appearance.looks[bodyPart] = new.getInt(key)

    if (bodyPart == 2) {
        var armStyle = 0
        var wristStyle = 0
        lookupStyle(new.getInt(key), player.world) {
            armStyle = it.getInt(armParam)
            if (armStyle == -1) {
                armStyle = it.getInt(topParam)
            }
            wristStyle = it.getInt(wristParam)
        }
        player.appearance.looks[3] = armStyle
        player.appearance.looks[4] = wristStyle
    }
}

/**
 * Handle the npc
 */

on_npc_option(npc = Npcs.MAKEOVER_MAGE_2676, option = "Makeover") {
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 900)
}

on_npc_option(npc = Npcs.MAKEOVER_MAGE_2676, option = "Talk-to") {
    player.queue {
        chatNpc(
            "Hello there! I am known as the Makeover Mage! I have",
            "spent many years researching magics that can change",
            "your physical appearance!",
        )
        chatNpc(
            "I can alter your physical form with my magic, there's",
            "no fee. Would you like me to perform my magics upon you?",
        )
        when (
            options(
                "Tell me more about this 'makeover'.",
                "Sure, I'll have a makeover.",
                "Cool amulet! Can I have one?",
                "No thanks.",
            )
        ) {
            1 -> makeoverInformation(this)
            2 -> makeoverContinue(this)
            3 -> purchaseAmulet(this)
            4 -> chatPlayer("No thanks.")
        }
    }
}

suspend fun makeoverInformation(it: QueueTask) {
    it.chatPlayer("Tell me more about this 'makeover'.")
    it.chatNpc(
        "Why, of course! Basically, and I will try and explain",
        "this so that you will understand it correctly,",
    )
    it.chatNpc("I use my secret magical technique to melt your body", "down into a puddle of its elements.")
    it.chatNpc("When I have broken down all trace of your body, I", "then rebuild it into the form I am thinking of!")
    it.chatNpc("Or, you know, somewhere vaguely close enough", "anyway.")
    it.chatPlayer("Uh... that doesn't sound particularly safe to me...")
    it.chatNpc("It's as safe as houses! Why, I have only had thirty-six", "major accidents this month!")
    it.chatNpc("So what do you say? Feel like a change? There's no", "fee.")
    when (it.options("Sure, I'll have a makeover.", "Cool amulet! Can I have one?", "No thanks.")) {
        1 -> makeoverContinue(it)
        2 -> purchaseAmulet(it)
        3 -> it.chatPlayer("No thanks.")
    }
}

suspend fun makeoverContinue(it: QueueTask) {
    it.chatPlayer("Sure, I'll have a makeover.")
    it.chatNpc("Good choice, good choice. You wouldn't want to carry", "on looking like that, I'm sure!")
    it.player.openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 900)
}

suspend fun purchaseAmulet(it: QueueTask) {
    it.chatPlayer("Cool amulet! Can I have one?")
    it.chatNpc(
        "No problem, but please remember that the amulet I will",
        "sell you is only a copy of my own. It contains no",
        "magical powers, and as such, will only cost you 100",
        "coins.",
    )
    if (it.player.inventory.getItemCount(Items.COINS_995) >= 100) {
        when (it.options("Sure, here you go.", "No way! That's far too expensive.")) {
            1 -> {
                it.chatPlayer("Sure, here you go.")
                if (it.player.inventory
                        .remove(Items.COINS_995, 100)
                        .hasSucceeded()
                ) {
                    it.player.inventory.add(Items.YIN_YANG_AMULET)
                    it.itemMessageBox("You receive an amulet in exchange for 100 coins.", item = Items.YIN_YANG_AMULET)
                    continueDialogue(it)
                }
            }
            2 -> {
                it.chatPlayer("No way! That's far too expensive.")
                continueDialogue(it)
            }
        }
    } else {
        it.chatPlayer("Oh, I don't have enough money for that.")
        continueDialogue(it)
    }
}

suspend fun continueDialogue(it: QueueTask) {
    it.chatNpc("Anyway, would you like me to alter your physical form?", "For you, I'll do it for free!")
    when (it.options("Tell me more about this 'makeover'.", "Sure, I'll have a makeover.", "No thanks.")) {
        1 -> makeoverInformation(it)
        2 -> makeoverContinue(it)
        3 -> it.chatPlayer("No thanks.")
    }
}

val TRANSFORM_TIMER = TimerKey()
val DELAY = 150..250

on_npc_spawn(npc = Npcs.MAKEOVER_MAGE_2676) {
    npc.timers[TRANSFORM_TIMER] = world.random(DELAY)
}

on_timer(TRANSFORM_TIMER) {
    if (npc.isActive() && npc.lock.canMove()) {
        val male = npc.getTransmogId() != Npcs.MAKEOVER_MAGE
        npc.setTransmogId(if (male) Npcs.MAKEOVER_MAGE else npc.id)
        npc.graphic(id = 110, height = 60, delay = 15)
        npc.animate(1161)
        npc.forceChat(if (male) "Ahah!" else "Ooh!")
    }
    npc.timers[TRANSFORM_TIMER] = world.random(DELAY)
}
