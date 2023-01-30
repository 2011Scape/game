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

        if(player.getVarbit(Skills.FLASHING_ICON_VARBITS[skill]) > 0) {

            // set the varbit for the skill advance guide we're viewing
            player.setVarbit(LEVELLED_SKILL_VARBIT, bit)

            // set the varc for our last viewed level
            player.setVarc(Skills.LEVELLED_AMOUNT_VARC[bit - 1], player.getSkills().getLastLevel(skill))

            // disable the flashing icon
            player.setVarbit(Skills.FLASHING_ICON_VARBITS[skill], 0)

            // set our last viewed level to our current level
            player.getSkills().setLastLevel(skill, player.getSkills().getCurrentLevel(skill))

            // open the skill advance guide
            player.openInterface(interfaceId = 741, dest = InterfaceDestination.MAIN_SCREEN_FULL)
        } else {

            // TODO: finish sub-menus

            player.setVarp(SKILL_ID_VARBIT, bit)
            player.openInterface(interfaceId = 499, dest = InterfaceDestination.MAIN_SCREEN_FULL)
            player.attr[SKILL_MENU] = guide.bit
        }
    }
}

