package gg.rsmod.plugins.content.skills.thieving.pickpocketing

import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.isBeingAttacked
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

object Pickpocketing {

    private const val waitTime = 2

    suspend fun pickpocket(task: QueueTask, target: Npc, targetInfo: PickpocketTarget) {
        val player = task.player

        if (!canPickpocket(player, targetInfo)) {
            return
        }

        if (rollForSuccess(targetInfo, player)) {
            player.animate(881)
            handleSuccess(task, player, target, targetInfo)
        } else {
            handleFailure(player, target, targetInfo)
        }
    }

    private fun rollForSuccess(targetInfo: PickpocketTarget, player: Player): Boolean {
        val capAdjustmentFactor = if (player.hasEquipped(EquipmentType.GLOVES, Items.GLOVES_OF_SILENCE)) 0.95 else 1.0
        return targetInfo.roll(player.getSkills().getCurrentLevel(Skills.THIEVING), capAdjustmentFactor)
    }

    private suspend fun handleSuccess(task: QueueTask, player: Player, target: Npc, targetInfo: PickpocketTarget) {
        player.playSound(2581)
        task.wait(waitTime)
        DropTableFactory.createDropInventory(player, target.id, DropTableType.PICKPOCKET)
        player.addXp(Skills.THIEVING, targetInfo.xp)
    }

    private fun handleFailure(player: Player, target: Npc, targetInfo: PickpocketTarget) {
        if (isHamTeleport(player, targetInfo)) {
            handleHamFailure(player)
            return
        }

        target.facePawn(player)
        target.animate(422)
        target.forceChat(targetInfo.onCaught.random().replace("{name}", player.username))
        player.playSound(2727, delay = 20)
        player.stun(targetInfo.stunnedTicks)
        player.hit(targetInfo.rollDamage(), HitType.REGULAR_HIT)
        player.facePawn(target)
        target.resetFacePawn()
    }

    private fun isHamTeleport(player: Player, targetInfo: PickpocketTarget): Boolean {
        return if (targetInfo == PickpocketTarget.FemaleHamMember || targetInfo == PickpocketTarget.MaleHamMember) {
            val chance = 0.2 * (1 - hamWear.count { player.hasEquipped(intArrayOf(it)) } * 0.04)
            player.world.randomDouble() < chance
        } else {
            false
        }
    }

    private fun handleHamFailure(player: Player) {
        player.message("You're beaten unconscious and bundled out of the HAM camp.")
        player.moveTo(hamDestinations.random())
        // TODO: find the correct animation for this
        // TODO: chance to be locked up instead
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

    private val hamWear = listOf(
        Items.HAM_SHIRT,
        Items.HAM_ROBE,
        Items.HAM_HOOD,
        Items.HAM_CLOAK,
        Items.HAM_LOGO,
        Items.HAM_GLOVES,
        Items.HAM_BOOTS,
    )

    private val hamDestinations = listOf(
        Tile(3185, 3211),
        Tile(3147, 3217),
        Tile(3140, 3228),
        Tile(3163, 3238),
        Tile(3139, 3261),
    )
}
