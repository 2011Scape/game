package gg.rsmod.plugins.content.areas.falador

/**
 * @author Alycia <https://github.com/alycii>
 */


on_npc_option(npc = Npcs.HAIRDRESSER, "Talk-to") {
    val male = player.appearance.gender.isMale()
    player.queue {
        chatNpc("Good afternoon ${if(male) "sir" else "madam"}. In need of a haircut${if(male) " or shave" else ""} are", "we?", facialExpression = FacialExpression.HAPPY)
        when(options("Yes, please.", "No, thank you.")) {
            1 -> {
                chatPlayer("Yes, please.")
                chatNpc("Please select the hairstyle you would like from this", "brochure. I'll even throw in a free recolour.", facialExpression = FacialExpression.HAPPY)
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
    if(player.equipment[EquipmentType.WEAPON.id] != null || player.equipment[EquipmentType.SHIELD.id] != null) {
        player.queue {
            chatNpc("I don't feel comfortable cutting hair when you are", "wielding something. Please remove what you are holding", "first.", facialExpression = FacialExpression.AFRAID)
        }
        return
    }
    if(player.equipment[EquipmentType.HEAD.id] != null) {
        player.queue {
            chatNpc("I can't cut your hair with that on your head.", facialExpression = FacialExpression.UPSET)
        }
        return
    }
    player.openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 309)
}