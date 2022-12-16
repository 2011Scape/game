package gg.rsmod.plugins.content.inter.skillguides

import gg.rsmod.game.model.attr.SKILL_MENU

val SKILL_ID_VARBIT = 965

SkillGuide.values.forEach { guide ->
    on_button(interfaceId = 320, component = guide.child) {
        if (!player.lock.canInterfaceInteract()) {
            return@on_button
        }

        player.setVarp(SKILL_ID_VARBIT, guide.bit)
        player.openInterface(interfaceId = 499, dest = InterfaceDestination.MAIN_SCREEN_FULL)
        player.attr[SKILL_MENU] = guide.bit
    }
}

