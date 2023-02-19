package gg.rsmod.plugins.content.skills.thieving.stalls

import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

object Stalls {

    private const val waitTime = 2

    suspend fun stealFromStall(task: QueueTask, target: GameObject, targetInfo: StallTarget) {
        val player = task.player
        player.faceTile(target.tile)

        if (!canSteal(player, targetInfo)) {
            return
        }

        player.animate(832)
        task.wait(waitTime)
        handleSuccess(player, target, targetInfo)
        // TODO: have nearby npcs attack you if they are close enough
    }

    private fun handleSuccess(player: Player, target: GameObject, targetInfo: StallTarget) {
        DropTableFactory.createDropInventory(player, target.id, DropTableType.STALL)
        player.addXp(Skills.THIEVING, targetInfo.xp)
        player.world.let { world ->
            world.queue {
                val emptyStall = DynamicObject(target, targetInfo.getEmpty(target.id) ?: Objs.MARKET_STALL)
                world.remove(target)
                world.spawn(emptyStall)
                wait(targetInfo.respawnTicks)
                world.remove(emptyStall)
                world.spawn(DynamicObject(target))
            }
        }
        player.filterableMessage(targetInfo.message)
    }

    private fun canSteal(player: Player, targetInfo: StallTarget): Boolean {
        if (player.isBeingAttacked()) {
            player.message("You can't steal from the market stall during combat")
            return false
        }

        if (player.getSkills().getCurrentLevel(Skills.THIEVING) < targetInfo.level) {
            player.message("You need a Thieving level of ${targetInfo.level} to do that.")
            return false
        }

        if (!targetInfo.hasInventorySpace(player)) {
            player.message("You don't have enough inventory space to do that.")
            return false
        }

        return true
    }
}
