package gg.rsmod.plugins.content.skills.thieving

import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

object Thieving {

    private const val waitTime = 2

    suspend fun pickpocket(task: QueueTask, target: Npc, targetInfo: PickpocketTarget) {
        val player = task.player

        if (!canPickpocket(player, targetInfo)) {
            return
        }

        if (targetInfo.roll(player.getSkills().getCurrentLevel(Skills.THIEVING))) {
            player.animate(881)
            handleSuccess(task, player, target, targetInfo)
        } else {
            handleFailure(player, target, targetInfo)
        }
    }

    private suspend fun handleSuccess(task: QueueTask, player: Player, target: Npc, targetInfo: PickpocketTarget) {
        player.playSound(2581)
        task.wait(waitTime)
        DropTableFactory.createDropInventory(player, target.id, DropTableType.PICKPOCKET)
        player.addXp(Skills.THIEVING, targetInfo.xp)
    }

    private fun handleFailure(player: Player, target: Npc, targetInfo: PickpocketTarget) {
        target.facePawn(player)
        target.animate(422)
        player.playSound((518..521).random(), delay = 20)
        player.stun(targetInfo.stunnedTicks)
        player.hit(targetInfo.rollDamage(), HitType.REGULAR_HIT)
        player.facePawn(target)
    }

    private fun canPickpocket(player: Player, targetInfo: PickpocketTarget): Boolean {
        if (player.isBeingAttacked()) {
            player.message("You can't pickpocket while in combat.")
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
