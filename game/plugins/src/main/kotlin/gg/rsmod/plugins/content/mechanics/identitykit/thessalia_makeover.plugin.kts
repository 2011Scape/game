package gg.rsmod.plugins.content.mechanics.identitykit

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
    setAppearanceVarcs(player)
    player.setComponentText(interfaceId = 729, component = 21, "Free!")
    player.setInterfaceEvents(interfaceId = 729, component = 12, from = 0, to = 100, setting = 2)
    player.setInterfaceEvents(interfaceId = 729, component = 17, from = 0, to = world.definitions.get(EnumDef::class.java, BODY_COLOR_ENUM).values.size * 2, setting = 6)
    player.lock()
    player.queue(TaskPriority.STRONG) {
        player.graphic(1181)
        wait(2)
        while(player.isInterfaceVisible(729)) {
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
    if((part == ARM_PART || part == WRIST_PART) && previousLook) {
        return@on_button
    }

    val enum = world.definitions.get(EnumDef::class.java, getBodyStyleEnum(gender, part))
    val value = enum.getInt(slot / 2)
    if (part == BODY_PART) {
        val currentLook = fullBodyStyle(value, gender)
        if (previousLook && !currentLook) {
            player.setVarc(MAKEOVER_ARMS_VARC, if(gender.isMale()) 26 else 61) // sets to default arms
            player.setVarc(MAKEOVER_WRISTS_VARC, if(gender.isMale()) 34 else 68) // sets to default wrists
        } else if (currentLook) {
            player.setVarc(MAKEOVER_ARMS_VARC, lookupStyle(value, armParam, player.world))
            player.setVarc(MAKEOVER_WRISTS_VARC, lookupStyle(value, wristParam, player.world))
        }
    }
    player.setVarc(makeoverStyleVars[part + 2], value)
}

/**
 * Handle selecting the parts
 */

// Body
on_button(interfaceId = 729, component = 6) {
    player.setInterfaceEvents(interfaceId = 729, component = 17, from = 0, to = world.definitions.get(EnumDef::class.java, BODY_COLOR_ENUM).values.size * 2, setting = 6)
    player.setVarbit(PARTS_VARBIT, 0)
}

// Arms
on_button(interfaceId = 729, component = 7) {
    if(fullBodyStyle(player.getVarc(MAKEOVER_BODY_VARC), player.appearance.gender)) {
        return@on_button
    }
    player.setVarbit(PARTS_VARBIT, 1)
}

// Wrists
on_button(interfaceId = 729, component = 8) {
    if(fullBodyStyle(player.getVarc(MAKEOVER_BODY_VARC), player.appearance.gender)) {
        return@on_button
    }
    player.setVarbit(PARTS_VARBIT, 2)
}

// Legs
on_button(interfaceId = 729, component = 9) {
    player.setInterfaceEvents(interfaceId = 729, component = 17, from = 0, to = world.definitions.get(EnumDef::class.java, LEG_COLOR_ENUM).values.size * 2, setting = 6)
    player.setVarbit(PARTS_VARBIT, 3)
}

/**
 * Handle colors
 */
on_button(interfaceId = 729, component = 17) {
    val slot = player.getInteractingSlot()
    when(player.getVarbit(6091)) {
        BODY_PART, ARM_PART -> player.setVarc(MAKEOVER_TOP_COLOR_VARC, world.definitions.get(EnumDef::class.java, BODY_COLOR_ENUM).getInt(slot / 2))
        LEG_PART -> player.setVarc(MAKEOVER_LEGS_COLOR_VARC, world.definitions.get(EnumDef::class.java, LEG_COLOR_ENUM).getInt(slot / 2))
        else -> return@on_button
    }
}

/**
 * Handle confirming the appearance
 */
on_button(interfaceId = 729, component = 19) {
    player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)

    // double check wrist/arms
    if(fullBodyStyle(player.getVarc(MAKEOVER_BODY_VARC), player.appearance.gender)) {
        player.setVarc(MAKEOVER_ARMS_VARC, lookupStyle(player.getVarc(MAKEOVER_BODY_VARC), armParam, player.world))
        player.setVarc(MAKEOVER_WRISTS_VARC, lookupStyle(player.getVarc(MAKEOVER_BODY_VARC), wristParam, player.world))
    }

    setAppearance(player)
}

/**
 * Clean up after closing the interface
 */
on_interface_close(interfaceId = 729) {
    player.setVarbit(PARTS_VARBIT, 0)
    player.interruptQueues()
    player.graphic(1183)
    player.unlock()
    player.queue {
        chatNpc("Woah! Fabulous! You look absolutely great!", npc = Npcs.THESSALIA)
    }
}

/**
 * Looks up the wrist/arm struct value for the given chest style
 *
 * @Credits Greg <https://github.com/GregHib>
 */
fun lookupStyle(top: Int, param: Int, world: World) : Int {
    for(i in 0 until 64) {
        val style = world.definitions.get(StructDef::class.java, struct + i)
        if(style.getInt(topParam) == top) {
            return style.getInt(param)
        }
    }
    return -1
}

/**
 * Checks whether the chest style is a "full-body" style or not
 *
 * Most of the newer styles are full-body, meaning they include arms/wrists
 * on their own, while some retro styles only update the "chest" portion of the character
 *
 * @Credits Greg <https://github.com/GregHib>
 */
fun fullBodyStyle(look: Int, gender: Gender) = look in if (gender.isMale()) 443..474 else 556..587
