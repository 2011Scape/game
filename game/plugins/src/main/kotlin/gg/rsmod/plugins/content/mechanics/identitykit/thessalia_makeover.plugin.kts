package gg.rsmod.plugins.content.mechanics.identitykit

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

/**
 * @author Alycia <https://github.com/alycii>
 */

val PARTS_VARBIT = 6091
val BODY_PART = 0
val ARM_PART = 1
val WRIST_PART = 2
val LEG_PART = 3

val BODY_COLOR_ENUM = 3282
val LEG_COLOR_ENUM = 3284

on_interface_open(interfaceId = 729) {
    player.setVarbit(PARTS_VARBIT, 0)
    setAppearanceVarcs(player)
    player.setComponentText(interfaceId = 729, component = 21, "Free!")
    player.setEvents(interfaceId = 729, component = 12, from = 0, to = 100, setting = 2)
    player.setEvents(
        interfaceId = 729,
        component = 17,
        from = 0,
        to =
            world.definitions
                .get(EnumDef::class.java, BODY_COLOR_ENUM)
                .values.size * 2,
        setting = 6,
    )
    player.lockingQueue(TaskPriority.STRONG) {
        player.graphic(1181)
        wait(2)
        while (player.isInterfaceVisible(729)) {
            player.graphic(1182)
            wait(1)
        }
    }
}

/**
 * Handle styles for the given part
 *
 * @Credits Greg <https://github.com/GregHib>
 *     Logic on arm/wrists
 */
on_button(interfaceId = 729, component = 12) {
    val part = player.getVarbit(PARTS_VARBIT)
    val slot = player.getInteractingSlot()
    val gender = player.appearance.gender
    val previousLook = fullBodyStyle(player.getVarc(MAKEOVER_BODY_VARC), gender)
    if ((part == ARM_PART || part == WRIST_PART) && previousLook) {
        return@on_button
    }

    val enum = world.definitions.get(EnumDef::class.java, getBodyStyleEnum(gender, part))
    val value = enum.getInt(slot / 2)
    if (part == BODY_PART) {
        val currentLook = fullBodyStyle(value, gender)
        if (previousLook && !currentLook) {
            player.setVarc(MAKEOVER_ARMS_VARC, if (gender.isMale()) 26 else 61) // sets to default arms
            player.setVarc(MAKEOVER_WRISTS_VARC, if (gender.isMale()) 34 else 68) // sets to default wrists
        } else if (currentLook) {
            var armStyle = 0
            var wristStyle = 0
            lookupStyle(value, player.world) {
                armStyle = it.getInt(armParam)
                if (armStyle == -1) {
                    armStyle = it.getInt(topParam)
                }
                wristStyle = it.getInt(wristParam)
            }
            player.setVarc(MAKEOVER_ARMS_VARC, armStyle)
            player.setVarc(MAKEOVER_WRISTS_VARC, wristStyle)
        }
    }
    player.setVarc(makeoverStyleVars[part + 2], value)
}

/**
 * Handle selecting the parts
 */

// Body
on_button(interfaceId = 729, component = 6) {
    player.setEvents(
        interfaceId = 729,
        component = 17,
        to =
            world.definitions
                .get(EnumDef::class.java, BODY_COLOR_ENUM)
                .values.size * 2,
        setting = 6,
    )
    player.setVarbit(PARTS_VARBIT, 0)
}

// Arms
on_button(interfaceId = 729, component = 7) {
    if (fullBodyStyle(player.getVarc(MAKEOVER_BODY_VARC), player.appearance.gender)) {
        return@on_button
    }
    player.setVarbit(PARTS_VARBIT, 1)
}

// Wrists
on_button(interfaceId = 729, component = 8) {
    if (fullBodyStyle(player.getVarc(MAKEOVER_BODY_VARC), player.appearance.gender)) {
        return@on_button
    }
    player.setVarbit(PARTS_VARBIT, 2)
}

// Legs
on_button(interfaceId = 729, component = 9) {
    player.setEvents(
        interfaceId = 729,
        component = 17,
        to =
            world.definitions
                .get(EnumDef::class.java, LEG_COLOR_ENUM)
                .values.size * 2,
        setting = 6,
    )
    player.setVarbit(PARTS_VARBIT, 3)
}

/**
 * Handle colors
 */
on_button(interfaceId = 729, component = 17) {
    val slot = player.getInteractingSlot()
    when (player.getVarbit(6091)) {
        BODY_PART, ARM_PART ->
            player.setVarc(
                MAKEOVER_TOP_COLOR_VARC,
                world.definitions.get(EnumDef::class.java, BODY_COLOR_ENUM).getInt(
                    slot / 2,
                ),
            )
        LEG_PART ->
            player.setVarc(
                MAKEOVER_LEGS_COLOR_VARC,
                world.definitions.get(EnumDef::class.java, LEG_COLOR_ENUM).getInt(
                    slot / 2,
                ),
            )
        else -> return@on_button
    }
}

/**
 * Handle confirming the appearance
 */
on_button(interfaceId = 729, component = 19) {
    player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)
    setAppearance(player)
    player.queue {
        chatNpc("Woah! Fabulous! You look absolutely great!", npc = Npcs.THESSALIA)
    }
}

/**
 * Clean up after closing the interface
 */
on_interface_close(interfaceId = 729) {
    player.setVarbit(PARTS_VARBIT, 0)
    player.interruptQueues()
    player.graphic(1183)
    player.unlock()
}

/**
 * Handle the npc
 */
create_shop("Thessalia's Fine Clothes", CoinCurrency(), containsSamples = false) {
    items[0] = ShopItem(Items.WHITE_APRON, 10)
    items[1] = ShopItem(Items.LEATHER_BODY, 10)
    items[2] = ShopItem(Items.LEATHER_GLOVES, 10)
    items[3] = ShopItem(Items.LEATHER_BOOTS, 10)
    items[4] = ShopItem(Items.BROWN_APRON, 10)
    items[5] = ShopItem(Items.PINK_SKIRT, 10)
    items[6] = ShopItem(Items.BLACK_SKIRT, 10)
    items[7] = ShopItem(Items.BLUE_SKIRT, 10)
    items[8] = ShopItem(Items.CAPE, 10)
    items[9] = ShopItem(Items.SILK, 10)
    items[10] = ShopItem(Items.PRIEST_GOWN_428, 10)
    items[11] = ShopItem(Items.PRIEST_GOWN, 10)
    items[12] = ShopItem(Items.NEEDLE, 10)
    items[13] = ShopItem(Items.THREAD, 1000)
}

on_npc_option(npc = Npcs.THESSALIA, option = "Talk-to") {
    player.queue {
        chatNpc("Would you like to buy any fine clothes?")
        chatNpc("Or if you're more after fancy dress costumes or", "commemorative capes, talk to granny Iffie.")
        when (options("What do you have?", "No, thank you.")) {
            1 -> {
                chatPlayer("What do you have?")
                chatNpc(
                    "Well, I have a number of fine pieces of clothing on sale or,",
                    "if you prefer, I can offer you an exclusive, total clothing",
                    "makeover?",
                )
                when (options("Tell me more about this makeover.", "I'd just like to buy some clothes.")) {
                    1 -> {
                        chatPlayer("Tell me more about this makeover.")
                        chatNpc("Certainly!")
                        chatNpc(
                            "Here at Thessalia's Fine Clothing Boutique we offer a",
                            "unique service, where we will totally revamp your outfit to",
                            " your choosing. Tired of always wearing the same old",
                            "outfit, day-in, day-out? Then this is the service for you!",
                        )
                        chatNpc("So, what do you say? Interested?")
                        when (
                            options(
                                "I'd like to change my outfit, please.",
                                "I'd just like to buy some clothes.",
                                "No, thank you.",
                            )
                        ) {
                            1 -> {
                                chatPlayer("I'd like to change my outfit, please.")
                                chatNpc(
                                    "Wonderful. Feel free to try on some items and see if",
                                    "there's anything you would like.",
                                )
                                chatPlayer("Okay, thanks.")
                                openMakeover(player)
                            }
                            2 -> {
                                chatPlayer("I'd just like to buy some clothes.")
                                player.openShop("Thessalia's Fine Clothes")
                            }
                        }
                    }
                    2 -> {
                        chatPlayer("I'd just like to buy some clothes.")
                        player.openShop("Thessalia's Fine Clothes")
                    }
                }
            }
            2 -> chatPlayer("No, thank you.")
        }
    }
}

on_npc_option(npc = Npcs.THESSALIA, option = "Trade") {
    player.openShop("Thessalia's Fine Clothes")
}

on_npc_option(npc = Npcs.THESSALIA, option = "Change-clothes") {
    openMakeover(player)
}

fun openMakeover(player: Player) {
    if (!player.equipment.isEmpty) {
        player.queue {
            chatNpc("You're not able to try on my clothes with all that armour.", "Take it off and speak to me again.")
        }
        return
    }
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 729)
}
