package gg.rsmod.plugins.content.mechanics.identitykit

import gg.rsmod.game.fs.def.EnumDef.Companion.FEMALE_HAIR_STRUCT
import gg.rsmod.game.fs.def.EnumDef.Companion.MALE_HAIR_STRUCT

/**
 * @author Alycia <https://github.com/alycii>
 */

val PARTS_VARBIT = 6084

val HAIR_PART = 0
val BEARD_PART = 1

val HAIR_COLOR_ENUM = 2345

val MALE_HAIR_ENUM = 2339
val FEMALE_HAIR_ENUM = 2342

val BEARD_ENUM = 703

val STRUCT_ID = 788

on_interface_open(interfaceId = 309) {
    setAppearanceVarcs(player)
    player.setComponentText(interfaceId = 309, component = 20, "Free!")
    player.setInterfaceEvents(interfaceId = 309, component = 10, from = 0, to = world.definitions.get(EnumDef::class.java, if(player.appearance.gender.isMale()) MALE_HAIR_ENUM else FEMALE_HAIR_ENUM).values.size * 2, setting = 6)
    player.setInterfaceEvents(interfaceId = 309, component = 16, from = 0, to = world.definitions.get(EnumDef::class.java, HAIR_COLOR_ENUM).values.size * 2, setting = 6)
    player.lock()
    player.queue(TaskPriority.STRONG) {
        player.graphic(1181)
        wait(2)
        while(player.isInterfaceVisible(309)) {
            player.graphic(1182)
            wait(1)
        }
    }
}

/**
 * Handle styles
 *
 */
on_button(interfaceId = 309, component = 10) {
    val slot = player.getInteractingSlot() / 2
    val gender = player.appearance.gender
    when(player.getVarbit(PARTS_VARBIT)) {
        HAIR_PART -> {
            val enumValue = world.definitions.get(EnumDef::class.java, if(gender.isMale()) MALE_HAIR_STRUCT else FEMALE_HAIR_STRUCT).getInt(slot)
            val structValue = world.definitions.get(StructDef::class.java, enumValue).getInt(STRUCT_ID)
            player.setVarc(MAKEOVER_HAIR_VARC, structValue)
        }
        BEARD_PART -> {
            player.setVarc(MAKEOVER_BEARD_VARC, world.definitions.get(EnumDef::class.java,BEARD_ENUM).getInt(slot))
        }
    }
}

/**
 * Handle facial hair selections
 */
on_button(interfaceId = 309, component = 6) {
    player.setVarbit(PARTS_VARBIT, 0)
}

on_button(interfaceId = 309, component = 7) {
    player.setVarbit(PARTS_VARBIT, 1)
}

on_interface_close(interfaceId = 309) {
    player.setVarbit(PARTS_VARBIT, 0)
    player.interruptQueues()
    player.graphic(1183)
    player.unlock()
}

/**
 * Handle colors
 */
on_button(interfaceId = 309, component = 16) {
    val slot = player.getInteractingSlot() / 2
    val value = world.definitions.get(EnumDef::class.java, HAIR_COLOR_ENUM).getInt(slot)
    player.setVarc(MAKEOVER_HAIR_COLOR_VARC, value)
}

/**
 * Handle confirming the appearance
 */
on_button(interfaceId = 309, component = 18) {
    player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)
    setAppearance(player)
    player.queue {
        chatNpc("Hope you like the new do!", npc = Npcs.HAIRDRESSER)
    }
}
