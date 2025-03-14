package gg.rsmod.plugins.content.items.lamps

import gg.rsmod.plugins.content.quests.finishedQuest
import gg.rsmod.plugins.content.quests.impl.DruidicRitual

/**
 * @author Alycia <https://github.com/alycii>
 * This script handles the interactions with the lamp interface in a game.
 * It allows a player to select a skill and gain experience points in that skill.
 */

// Variable to store the chosen skill
val chosenSkillVarp = 261

// List of skills that are not allowed to be chosen
val blockedSkills = listOf(Skills.CONSTRUCTION, Skills.DUNGEONEERING, Skills.HUNTER)

/**
 * Handles the button clicks for skill selection buttons (components 30 to 54).
 * Sets the chosen skill in the player's varp and prints it.
 */
for (component in 30..54) {
    on_button(interfaceId = 1139, component = component) {
        val interfaceEntry = LampInterface.findByComponent(component) ?: return@on_button
        player.setVarp(chosenSkillVarp, interfaceEntry.varp)
    }
}

/**
 * Handles the click of the confirm button (component 2).
 * Grants experience points to the chosen skill if it's allowed and not blocked.
 */
on_button(interfaceId = 1139, component = 2) {
    if (player.getVarp(chosenSkillVarp) == 0) {
        return@on_button
    }

    val interfaceEntry = LampInterface.findByVarp(player.getVarp(chosenSkillVarp)) ?: return@on_button

    val skillName = Skills.getSkillName(world, interfaceEntry.skillId)

    if (interfaceEntry == LampInterface.HERBLORE && !player.finishedQuest(DruidicRitual)) {
        player.queue {
            doubleMessageBox("You need to complete Druidic Ritual before you can", "earn experience in Herblore.")
        }
        player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)
        return@on_button
    }

    if (blockedSkills.contains(interfaceEntry.skillId)) {
        player.queue {
            messageBox("$skillName is currently unavailable and cannot be chosen.")
        }
        player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)
        return@on_button
    }

    player.closeInterface(dest = InterfaceDestination.MAIN_SCREEN)
    if (player.inventory.remove(Items.LAMP).hasSucceeded()) {
        val experience = player.skills.getMaxLevel(interfaceEntry.skillId) * 10.0
        var modifier = player.interpolate(1.0, 5.0, player.skills.getMaxLevel(interfaceEntry.skillId))
        var totalExperience = experience * modifier
        player.addXp(skill = interfaceEntry.skillId, xp = experience, disableBonusExperience = true)
        player.playSound(2655)
        player.queue {
            doubleMessageBox(
                "Your wish has been granted!",
                "You have been awarded ${totalExperience.toInt()} $skillName experience!",
            )
        }
    }
}

/**
 * Resets the chosen skill varp when the interface (interfaceId 1139) is closed.
 */
on_interface_close(interfaceId = 1139) {
    player.setVarp(chosenSkillVarp, 0)
}

/**
 * Enum class representing the skills available in the lamp interface.
 * Each entry has a component number, skill ID, and varp value.
 */
enum class LampInterface(
    val component: Int,
    val skillId: Int,
    val varp: Int,
) {
    ATTACK(component = 30, skillId = Skills.ATTACK, varp = 1),
    STRENGTH(component = 31, skillId = Skills.STRENGTH, varp = 2),
    RANGED(component = 33, skillId = Skills.RANGED, varp = 3),
    MAGIC(component = 36, skillId = Skills.MAGIC, varp = 4),
    DEFENCE(component = 32, skillId = Skills.DEFENCE, varp = 5),
    CRAFTING(component = 40, skillId = Skills.CRAFTING, varp = 11),
    CONSTITUTION(component = 35, Skills.CONSTITUTION, varp = 6),

    PRAYER(component = 34, skillId = Skills.PRAYER, varp = 7),
    AGILITY(component = 37, skillId = Skills.AGILITY, varp = 8),
    HERBLORE(component = 38, skillId = Skills.HERBLORE, varp = 9),
    THIEVING(component = 39, skillId = Skills.THIEVING, varp = 10),
    FISHING(component = 44, skillId = Skills.FISHING, varp = 15),
    RUNECRAFTING(component = 48, skillId = Skills.RUNECRAFTING, varp = 12),
    SLAYER(component = 49, skillId = Skills.SLAYER, varp = 20),

    FARMING(component = 51, skillId = Skills.FARMING, varp = 21),
    MINING(component = 42, skillId = Skills.MINING, varp = 13),
    SMITHING(component = 43, skillId = Skills.SMITHING, varp = 14),
    HUNTER(component = 50, skillId = Skills.HUNTER, varp = 23),
    COOKING(component = 46, skillId = Skills.COOKING, varp = 16),
    FIREMAKING(component = 45, skillId = Skills.FIREMAKING, varp = 17),
    WOODCUTTING(component = 47, skillId = Skills.WOODCUTTING, varp = 18),

    FLETCHING(component = 41, skillId = Skills.FLETCHING, varp = 19),
    CONSTRUCTION(component = 52, skillId = Skills.CONSTRUCTION, varp = 22),
    SUMMONING(component = 53, skillId = Skills.SUMMONING, varp = 24),
    DUNGEONEERING(component = 54, skillId = Skills.DUNGEONEERING, varp = 25),
    ;

    companion object {
        /**
         * Returns the corresponding LampInterface entry for the given component, or null if not found.
         * @param component The component number to search for.
         * @return The matching LampInterface entry, or null if not found.
         */
        fun findByComponent(component: Int): LampInterface? {
            return values().firstOrNull { it.component == component }
        }

        /**
         * Returns the corresponding LampInterface entry for the given varp
         */
        fun findByVarp(varp: Int): LampInterface? {
            return values().firstOrNull { it.varp == varp }
        }
    }
}
