package gg.rsmod.plugins.content.inter.skillguides

import gg.rsmod.game.model.attr.SKILL_MENU

val SKILL_ID_VARBIT = 965

val LEVELLED_SKILL_VARBIT = 4729

SkillGuide.values.forEach { guide ->
    on_button(interfaceId = 320, component = guide.child) {
        if (!player.lock.canInterfaceInteract()) {
            return@on_button
        }

        val skill = guide.id
        val bit = guide.bit

        if(player.getVarbit(LevelUp.FLASHING_ICON_VARBITS[skill]) > 0) {
            player.setVarbit(LEVELLED_SKILL_VARBIT, bit)
            player.setVarc(LevelUp.LEVELLED_AMOUNT_VARC[bit - 1], player.getSkills().getLastLevel(skill))
            player.setVarbit(LevelUp.FLASHING_ICON_VARBITS[skill], 0)
            player.getSkills().setLastLevel(skill, player.getSkills().getCurrentLevel(skill))
            player.openInterface(interfaceId = 741, dest = InterfaceDestination.MAIN_SCREEN_FULL)
        } else {
            player.setVarp(SKILL_ID_VARBIT, bit)
            player.openInterface(interfaceId = 499, dest = InterfaceDestination.MAIN_SCREEN_FULL)
            player.attr[SKILL_MENU] = guide.bit
        }
    }
}

