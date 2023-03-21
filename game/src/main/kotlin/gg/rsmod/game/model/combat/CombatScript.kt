package gg.rsmod.game.model.combat

import gg.rsmod.game.model.queue.QueueTask

abstract class CombatScript {

    abstract val ids: IntArray

    abstract suspend fun handleSpecialCombat(it: QueueTask)

}