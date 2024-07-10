package gg.rsmod.plugins.content.quests

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.CombatRequirement
import gg.rsmod.plugins.api.cfg.QuestRequirement
import gg.rsmod.plugins.api.cfg.SkillRequirement
import gg.rsmod.plugins.api.ext.*

val QUEST_POINT_VARP = 101
val MAX_QUEST_POINT_VARP = 904
/**
 * @author Alycia <https://github.com/alycii>
 */

/**
 * Gets the current stage of the specified quest for the player.
 * If the quest's varbit is greater than 2384, `getVarbit` is used instead of `getVarp`.
 * @param quest The quest to get the current stage of.
 * @return The current stage of the quest for the player.
 */
fun Player.getCurrentStage(quest: Quest): Int {
    return if (quest.usesVarbits) getVarbit(quest.questId) else getVarp(quest.questId)
}

/**
 * Checks if the specified [Quest] has been started by this [Player],
 * based on the current stage of the quest. Returns true if the current
 * stage is the first stage in the list of stages for the quest.
 *
 * @param quest The quest to check.
 * @return true if the quest has been started, false otherwise.
 */
fun Player.startedQuest(quest: Quest): Boolean {
    return getCurrentStage(quest) > 0
}

/**
 * Advances the player to the next stage of the specified quest.
 * If the quest's varbit is greater than 2384, `setVarbit` is used instead of `setVarp`.
 * @param quest The quest to advance the stage of.
 * @param amount The amount to advance the quest stage by (default is 1).
 */
fun Player.advanceToNextStage(
    quest: Quest,
    amount: Int = 1,
) {
    // hack way to "refresh" quest list
    toggleVarbit(4536)
    toggleVarbit(4536)

    if (quest.usesVarbits) {
        setVarbit(quest.questId, getVarbit(quest.questId).plus(amount))
    } else {
        setVarp(quest.questId, getVarp(quest.questId).plus(amount))
    }
}

/**
 * Starts the specified [Quest] for this [Player], setting the current stage
 * to the first stage in the list of stages for the quest, and sending a
 * message to the player indicating that they have started the quest.
 *
 * @param quest The quest to start.
 */
fun Player.startQuest(quest: Quest) {
    message("You've started a new quest: <col=5861e9>${quest.name}")
    advanceToNextStage(quest)
}

/**
 * Checks if the specified [Quest] has been finished by this [Player],
 * based on the current stage of the quest. Returns true if the current
 * varbit value is greater than or equal to the number of stages in the
 * list of stages for the quest.
 *
 * @param quest The quest to check.
 * @return true if the quest has been finished, false otherwise.
 */
fun Player.finishedQuest(quest: Quest): Boolean {
    return if (quest.usesVarbits) getVarbit(quest.questId) >= quest.stages else getVarp(quest.questId) >= quest.stages
}

/**
 * Checks if the specified [Quest] can be started by this [Player], based
 * on the list of requirements for the quest. Returns true if all the
 * requirements are met, false otherwise.
 *
 * @param quest The quest to check.
 * @return true if the quest can be started, false otherwise.
 */
fun Player.canStartQuest(quest: Quest): Boolean {
    return quest.requirements.all { it.hasRequirement(this) }
}

/**
 * Builds and displays the quest overview for the specified quest to the player.
 *
 * @param quest The quest to build the stages for.
 */
fun Player.buildQuestOverview(quest: Quest) {
    setComponentText(interfaceId = 178, component = 14, text = quest.name)
    setComponentText(interfaceId = 178, component = 52, text = quest.startPoint)
    if (quest.requirements.isEmpty()) {
        setComponentText(interfaceId = 178, component = 55, text = "None.")
    } else {
        setComponentText(interfaceId = 178, component = 55, text = getRequirements(this, quest))
    }
    setComponentText(interfaceId = 178, component = 59, text = "<br>${quest.requiredItems}")
    setComponentText(interfaceId = 178, component = 62, text = quest.combat)
    setComponentText(interfaceId = 178, component = 66, text = "<br>${quest.rewards}")

    openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 178)
    setComponentHidden(interfaceId = 275, component = 14, hidden = true)
    setComponentText(interfaceId = 275, component = 2, quest.name)

    setComponentHidden(interfaceId = 178, component = 27, hidden = true)
    setComponentHidden(interfaceId = 178, component = 28, hidden = false)

    var progress = "Not started"
    if (startedQuest(quest)) {
        progress = "In progress"
    }
    setComponentText(interfaceId = 178, component = 70, text = progress)
    setComponentSprite(interfaceId = 178, component = 79, sprite = quest.spriteId)
    openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 178)
}

/**
 * Builds and displays the quest stages for the specified quest to the player.
 *
 * @param quest The quest to build the stages for.
 */
fun Player.buildQuestStages(quest: Quest) {
    setComponentHidden(interfaceId = 275, component = 14, hidden = true)
    setComponentText(interfaceId = 275, component = 2, "<col=8A0808>${quest.name}")
    for (i in 16..315) {
        setComponentText(interfaceId = 275, component = i, "")
    }

    val stage = getCurrentStage(quest)

    setComponentText(interfaceId = 275, component = 16, text = "<br>")

    val objectives = quest.getObjective(this, stage).objectives
    objectives.forEachIndexed { index, string ->
        runClientScript(4265, 275 shl 16 or 17 + index, 0x000080)
        setComponentText(interfaceId = 275, component = 17 + index, text = string)
    }
    val scrollbarComponent = 275 shl 16 or 7
    val mainTextComponent = 275 shl 16 or 6
    runClientScript(id = 1208, scrollbarComponent, mainTextComponent, (objectives.size) * 25, 0)
    openInterface(dest = InterfaceDestination.MAIN_SCREEN_FULL, interfaceId = 275)
}

/**
 *
 * Builds and displays a quest finish interface for the player, showing the
 * name of the completed quest, an item to represent the quest, a reward, and
 * a message displaying the player's current varp value.
 * @param quest The quest that has been completed.
 * @param item The item ID to represent the completed quest.
 * @param rewards The rewards to be given to the player upon completion of the quest.
 */
fun Player.buildQuestFinish(
    quest: Quest,
    item: Int,
    vararg rewards: String,
) {
    setComponentText(interfaceId = 277, component = 4, "You have completed ${quest.name}!")
    setComponentItem(interfaceId = 277, component = 5, item = item, amountOrZoom = 1)
    setComponentText(interfaceId = 277, component = 7, "${getVarp(QUEST_POINT_VARP)}")
    for (i in 10..17) {
        setComponentText(interfaceId = 277, component = i, "")
    }
    rewards.forEachIndexed { index, string ->
        setComponentText(interfaceId = 277, component = index + 10, text = string)
    }
    openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = 277)
}

fun getRequirements(
    player: Player,
    quest: Quest,
): String {
    val requirementList = mutableListOf<String>()
    quest.requirements.forEach {
        when (it) {
            is QuestRequirement -> {
                requirementList.add(
                    if (player.finishedQuest(quest)) {
                        striked(it.quest.name)
                    } else {
                        it.quest.name
                    },
                )
            }

            is SkillRequirement -> {
                val skillString = "${it.level} ${Skills.getSkillName(world = player.world, skill = it.skill)}"
                requirementList.add(
                    if (player.skills.getMaxLevel(it.skill) >= it.level) {
                        striked(skillString)
                    } else {
                        skillString
                    },
                )
            }

            is CombatRequirement -> {
                val combatString = "Combat level ${it.combatLevel}"
                requirementList.add(
                    if (player.combatLevel >= it.combatLevel) {
                        striked(combatString)
                    } else {
                        combatString
                    },
                )
            }
        }
    }
    return requirementList.joinToString(separator = ", ")
}

fun red(text: String) = "<col=8A0808>$text</col>"

fun blue(text: String) = "<col=08088A>$text</col>"

fun striked(text: String) = "<str>$text</str>"
