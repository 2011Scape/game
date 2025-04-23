package gg.rsmod.game.model.queue.impl

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTaskSet
import gg.rsmod.game.model.queue.TaskPriority
import kotlin.coroutines.resume

/**
 * A [QueueTaskSet] implementation for [gg.rsmod.game.model.entity.Pawn]s.
 * Each [gg.rsmod.game.model.queue.QueueTask] is handled one at a time.
 *
 * @author Tom <rspsmods@gmail.com>
 */
class PawnQueueTaskSet : QueueTaskSet() {
    override fun cycle() {

        val strongOrSoft = queue.firstOrNull { q ->
            q.priority == TaskPriority.STRONG ||
                q.priority == TaskPriority.SOFT }
        /**
         *  If there is a strong/soft queue in our queue list,
         *  remove all weak queues
         */
        if (strongOrSoft != null) {
            removeWeakTasks()
        }

        val iterator = queue.iterator()
        while (iterator.hasNext()) {
            val task = iterator.next()

            if (task.priority == TaskPriority.STANDARD && task.ctx is Player && task.ctx.hasMenuOpen()) {
                val nonStandardTask = queue.firstOrNull { q -> q.priority != TaskPriority.STANDARD }
                if (nonStandardTask != null) {
                    continue
                }
                else {
                    break
                }
            }

            if (!task.invoked) {
                task.invoked = true

                /**
                 *  If there is a strong/soft queue about to execute
                 *  close modal interface
                 */
                if (task.ctx is Player &&
                    (task.priority == TaskPriority.STRONG || task.priority == TaskPriority.SOFT)) {
                    task.ctx.closeInterfaceModal()
                }

                task.coroutine.resume(Unit)
            }

            task.cycle()

            if (!task.suspended()) {
                /*
                 * Task is no longer in a suspended state, which means its job is
                 * complete.
                 */
                iterator.remove()

                /*
                 * If the task locked the player, then unlock them on complete
                 */
                if (task.lock && task.ctx is Player) {
                    task.ctx.unlock()
                }

                /*
                 * Since this task is complete, let's handle any upcoming
                 * task now instead of waiting until next cycle.
                 */
                continue
            }
        }
    }

    private fun Player.hasMenuOpen(): Boolean = world.plugins.isMenuOpened(this)
}
