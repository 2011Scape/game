package gg.rsmod.plugins.content.skills.thieving.stalls

import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GameObject
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.content.combat.isAttacking
import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

object Stalls {
    private const val waitTime = 2

    val stallGuards = mutableListOf<Npc>()

    suspend fun stealFromStall(
        task: QueueTask,
        target: GameObject,
        targetInfo: StallTarget,
    ) {
        val player = task.player
        if (canSteal(player, target, targetInfo) && !caughtByGuard(player, targetInfo)) {
            player.animate(832)
            player.playSound(Sfx.PICK2)
            task.wait(waitTime)
            handleSuccess(player, target, targetInfo)
        }
    }

    fun handleSuccess(
        player: Player,
        target: GameObject,
        targetInfo: StallTarget,
    ) {
        player.world.let { world ->
            world.queue {
                if (world.isSpawned(target)) {
                    val emptyStall = DynamicObject(target, targetInfo.getEmpty(target.id) ?: Objs.MARKET_STALL)
                    world.remove(target)
                    world.spawn(emptyStall)
                    wait(targetInfo.respawnTicks)
                    world.remove(emptyStall)
                    world.spawn(DynamicObject(target))
                }
            }
            DropTableFactory.createDropInventory(player, target.id, DropTableType.STALL)
            player.addXp(Skills.THIEVING, targetInfo.xp)
            player.filterableMessage(targetInfo.message)
        }
    }

    private fun caughtByGuard(
        player: Player,
        targetInfo: StallTarget,
    ): Boolean {
        val guard =
            stallGuards
                .filter { it.id in targetInfo.guards && !it.isAttacking() && !it.invisible && it.sees(player, 8) }
                .minByOrNull { it.tile.getDistance(player.tile) }
        return if (guard == null) {
            false
        } else {
            guard.attack(player)
            guard.forceChat("Hey! Get your hands off there!")
            true
        }
    }

    private fun canSteal(
        player: Player,
        target: GameObject,
        targetInfo: StallTarget,
    ): Boolean {
        if (!player.world.isSpawned(target)) {
            return false
        }
        if (player.skills.getCurrentLevel(Skills.THIEVING) < targetInfo.level) {
            player.message("You need a Thieving level of ${targetInfo.level} to do that.")
            return false
        }
        if (!targetInfo.hasInventorySpace(player)) {
            player.message("You don't have enough inventory space to do that.")
            return false
        }
        if (player.isBeingAttacked()) {
            player.message("You can't steal from the market stall during combat")
            return false
        }
        return true
    }
}
