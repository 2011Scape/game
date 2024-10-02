package gg.rsmod.plugins.content.skills.thieving.pickpocketing

import gg.rsmod.game.fs.def.NpcDef
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.DropTableType

object Pickpocketing {
    private const val waitTime = 2
    private val multiplierAnimations =
        mapOf(
            2 to 5074,
            3 to 5075,
            4 to 5078,
        )
    private val multiplierGfx =
        mapOf(
            2 to 873,
            3 to 874,
            4 to 875,
        )
    private val messages =
        mapOf(
            1 to "You successfully pick the {npc}'s pocket.",
            2 to "Your lightning-fast reactions allow you to steal double loot.",
            3 to "Your lightning-fast reactions allow you to steal triple loot.",
            4 to "Your lightning-fast reactions allow you to steal quadruple loot.",
        )

    suspend fun pickpocket(
        task: QueueTask,
        target: Npc,
        targetInfo: PickpocketTarget,
    ) {
        val player = task.player
        if (!canPickpocket(player, targetInfo)) {
            return
        }
        if (target.isDead()) {
            player.filterableMessage("Too late; they're dead.")
            return
        }
        player.animate(881)
        if (rollForSuccess(targetInfo, player)) {
            onSuccess(task, player, target, targetInfo)
        } else {
            onFailure(player, target, targetInfo)
        }
    }

    private fun rollForSuccess(
        targetInfo: PickpocketTarget,
        player: Player,
    ): Boolean {
        val adjustmentFactor = if (player.hasEquipped(EquipmentType.GLOVES, Items.GLOVES_OF_SILENCE)) 1.05 else 1.0
        return targetInfo.roll(player.skills.getCurrentLevel(Skills.THIEVING), adjustmentFactor)
    }

    private suspend fun onSuccess(
        task: QueueTask,
        player: Player,
        target: Npc,
        targetInfo: PickpocketTarget,
    ) {
        task.wait(waitTime)
        player.playSound(2581)
        val multiplier = getMultiplier(player, targetInfo)
        if (multiplier > 1) {
            player.animate(multiplierAnimations[multiplier]!!)
            player.graphic(multiplierGfx[multiplier]!!)
        }
        repeat(multiplier) { DropTableFactory.createDropInventory(player, target.id, DropTableType.PICKPOCKET) }
        player.addXp(Skills.THIEVING, targetInfo.xp)
        player.filterableMessage(
            messages[multiplier]!!.replace(
                "{npc}",
                player.world.definitions
                    .get(NpcDef::class.java, target.id)
                    .name
                    .lowercase(),
            ),
        )
    }

    private fun getMultiplier(
        player: Player,
        targetInfo: PickpocketTarget,
    ): Int {
        val thievingLevelOvershoot = player.skills.getCurrentLevel(Skills.THIEVING) - targetInfo.level
        val agilityLevelOvershoot = player.skills.getCurrentLevel(Skills.AGILITY) - targetInfo.level

        return if (thievingLevelOvershoot >= 10 && agilityLevelOvershoot >= 0 && player.world.random(7) == 1) {
            when {
                thievingLevelOvershoot >= 30 && agilityLevelOvershoot >= 20 -> 4
                thievingLevelOvershoot >= 20 && agilityLevelOvershoot >= 10 -> 3
                else -> 2
            }
        } else {
            1
        }
    }

    private fun onFailure(
        player: Player,
        target: Npc,
        targetInfo: PickpocketTarget,
    ) {
        if (isHamTeleport(player, targetInfo)) {
            handleHamFailure(player)
            return
        }
        target.facePawn(player)
        target.animate(422)
        target.forceChat(targetInfo.onCaught.random().replace("{name}", player.username))
        player.stun(targetInfo.stunnedTicks)
        player.hit(targetInfo.rollDamage(), HitType.REGULAR_HIT)
        player.facePawn(target)
        target.resetFacePawn()
    }

    private fun isHamTeleport(
        player: Player,
        targetInfo: PickpocketTarget,
    ): Boolean {
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

    private fun canPickpocket(
        player: Player,
        targetInfo: PickpocketTarget,
    ): Boolean {
        if (player.getCombatTarget() != null) {
            player.message("You can't pickpocket while in combat.")
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
        return true
    }

    private val hamWear =
        listOf(
            Items.HAM_SHIRT,
            Items.HAM_ROBE,
            Items.HAM_HOOD,
            Items.HAM_CLOAK,
            Items.HAM_LOGO,
            Items.HAM_GLOVES,
            Items.HAM_BOOTS,
        )

    private val hamDestinations =
        listOf(
            Tile(3185, 3211),
            Tile(3147, 3217),
            Tile(3140, 3228),
            Tile(3163, 3238),
            Tile(3139, 3261),
        )
}
