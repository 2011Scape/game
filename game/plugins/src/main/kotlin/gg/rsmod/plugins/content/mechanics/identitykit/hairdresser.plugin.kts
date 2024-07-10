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
    player.setVarbit(PARTS_VARBIT, 0)
    setAppearanceVarcs(player)
    player.setComponentText(interfaceId = 309, component = 20, "Free!")
    player.setEvents(
        interfaceId = 309,
        component = 10,
        to =
            world.definitions
                .get(
                    EnumDef::class.java,
                    if (player.appearance.gender.isMale()) MALE_HAIR_ENUM else FEMALE_HAIR_ENUM,
                ).values.size *
                2,
        setting = 6,
    )
    player.setEvents(
        interfaceId = 309,
        component = 16,
        to =
            world.definitions
                .get(EnumDef::class.java, HAIR_COLOR_ENUM)
                .values.size * 2,
        setting = 6,
    )
    player.lockingQueue(TaskPriority.STRONG) {
        player.graphic(1181)
        wait(2)
        while (player.isInterfaceVisible(309)) {
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
    when (player.getVarbit(PARTS_VARBIT)) {
        HAIR_PART -> {
            val enumValue =
                world.definitions
                    .get(
                        EnumDef::class.java,
                        if (gender.isMale()) MALE_HAIR_STRUCT else FEMALE_HAIR_STRUCT,
                    ).getInt(slot)
            val structValue = world.definitions.get(StructDef::class.java, enumValue).getInt(STRUCT_ID)
            player.setVarc(MAKEOVER_HAIR_VARC, structValue)
        }
        BEARD_PART -> {
            player.setVarc(MAKEOVER_BEARD_VARC, world.definitions.get(EnumDef::class.java, BEARD_ENUM).getInt(slot))
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

/**
 * Handle the npc
 */

on_npc_option(npc = Npcs.HAIRDRESSER, "Talk-to") {
    val male = player.appearance.gender.isMale()
    player.queue {
        chatNpc(
            "Good afternoon ${if (male) "sir" else "madam"}. In need of a haircut${if (male) " or shave" else ""} are",
            "we?",
            facialExpression = FacialExpression.HAPPY,
        )
        when (options("Yes, please.", "No, thank you.")) {
            1 -> {
                chatPlayer("Yes, please.")
                chatNpc(
                    "Please select the hairstyle you would like from this",
                    "brochure. I'll even throw in a free recolour.",
                    facialExpression = FacialExpression.HAPPY,
                )
                openMakeover(player)
            }
            2 -> {
                chatPlayer("No, thank you.")
                chatNpc("Very well. Come back if you change your mind.")
            }
        }
    }
}

on_npc_option(npc = Npcs.HAIRDRESSER, "Hair-cut") {
    openMakeover(player)
}

fun openMakeover(player: Player) {
    if (player.equipment[EquipmentType.WEAPON.id] != null || player.equipment[EquipmentType.SHIELD.id] != null) {
        player.queue {
            chatNpc(
                "I don't feel comfortable cutting hair when you are",
                "wielding something. Please remove what you are holding",
                "first.",
                facialExpression = FacialExpression.AFRAID,
            )
        }
        return
    }
    if (player.equipment[EquipmentType.HEAD.id] != null) {
        player.queue {
            chatNpc("I can't cut your hair with that on your head.", facialExpression = FacialExpression.UPSET)
        }
        return
    }
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 309)
}
