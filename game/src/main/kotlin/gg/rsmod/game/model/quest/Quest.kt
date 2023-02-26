package gg.rsmod.game.model.quest

import gg.rsmod.game.model.entity.Player

/**
 * @author Alycia <https://github.com/alycii>
 */


/**
 * A class representing a quest in the game.
 *
 * @property name The name of the quest.
 * @property startPoint A string describing the starting point for the quest.
 * @property requirements A list of requirements needed to start the quest.
 * @property requiredItems A string listing any required items for the quest.
 * @property combat A string describing any combat requirements for the quest.
 * @property rewards A string listing the rewards for completing the quest.
 * @property pointReward The number of quest points rewarded for completing the quest.
 * @property varbit The varbit ID used to track the quest's progress.
 * @property spriteId The sprite ID to be displayed in the quest info.
 * @property slot The slot on the quest tab where this quest will be displayed.
 * @property stages A list of [QuestStage] objects representing the stages of the quest.
 */
abstract class Quest(
    val name: String,
    val startPoint: String,
    val requirements: List<Requirement>,
    val requiredItems: String,
    val combat: String,
    val rewards: String,
    val pointReward: Int,
    val varbit: Int,
    val spriteId: Int,
    val slot: Int,
    val stages: List<QuestStage>
) {
    /**
     * A companion object that maintains a list of all quests.
     */
    companion object {
        /**
         * A mutable list of all the quests.
         */
        val quests = mutableListOf<Quest>()

        /**
         * Adds a quest to the list of quests.
         *
         * @param quest The quest to add.
         */
        fun addQuest(quest: Quest) {
            quests.add(quest)
        }

        /**
         * Finds a quest in the list of quests by name.
         *
         * @param name The name of the quest to find.
         * @return The [Quest] object with the given name, or null if no quest is found.
         */
        fun getQuest(name: String): Quest? {
            return quests.find { it.name.equals(name, ignoreCase = true) }
        }

        /**
         * Finds a quest in the list of quests by its slot.
         *
         * @param slot The slot of the quest to find
         * @return The [Quest] object with the given slot, or null if no quest is found.
         */
        fun getQuest(slot: Int): Quest? {
            return quests.find { it.slot == slot }
        }

    }

    /**
     * An abstract function that is called when the quest is finished.
     */
    abstract fun finishQuest(player: Player)
}

/**
 * Represents a stage in a quest.
 *
 * @property objectives The list of objectives that must be completed to advance to the next stage.
 * @property value The value of the varbit that corresponds to this stage.
 */
data class QuestStage(
    val objectives: List<String>,
    val value: Int
)
