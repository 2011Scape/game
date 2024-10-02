package gg.rsmod.game.model.combat

import gg.rsmod.game.model.queue.QueueTask

/**
 * @author Kevin Senez <ksenez94@gmail.com>
 * @since 21/03/2023
 */
abstract class CombatScript {
    /**
     * The list of npc ids to use with the combat script.
     * Used when binding the script in the combat script binding plugin.
     * [gg.rsmod.plugins.content.combat.scripts.combat_script_binding.plugin.kts]
     */
    abstract val ids: IntArray

    /**
     * The method for handling the special npc combat.
     */
    abstract suspend fun handleSpecialCombat(it: QueueTask)
}
