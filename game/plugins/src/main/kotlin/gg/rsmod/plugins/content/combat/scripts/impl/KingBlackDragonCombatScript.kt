package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.Graphic
import gg.rsmod.game.model.World
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.CombatScript
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.ChatMessageType
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.DragonfireFormula
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy

/**
 * @author:
 * - Eikenb00m <https://github.com/eikenb00m>
 * - Harley <https://github.com/HarleyGilpin>
 *
 * @description: King Black Dragon combat script.
 *
 * @date: 11/1/2023
 */

object KingBlackDragonCombatScript : CombatScript() {
    override val ids = intArrayOf(Npcs.KING_BLACK_DRAGON)

    /**
     * List of affected skills
     */
    private val skills = intArrayOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGED, Skills.MAGIC)

    /**
     * Method to handle all special combat attacks & etc.
     */
    override suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        val world = it.npc.world

        while (npc.canEngageCombat(target) && npc.isAttackDelayReady()) {
            npc.facePawn(target)
            val distance = npc.getFrontFacingTile(target).getDistance(target.tile)
            // If the target is within 4 tiles, attempt to move into melee range
            if (distance < 2 && npc.moveToAttackRange(it, target, distance = 1, projectile = false)) {
                // 1 in 3 chance to perform a melee attack
                if (world.random(3) == 0) {
                    sendMeleeAttack(npc, target)
                } else {
                    fireAttack(npc, target, world)
                }
            } else if (npc.moveToAttackRange(it, target, distance = 9, projectile = true)) {
                // If not in melee range, proceed with ranged attack
                fireAttack(npc, target, world)
            }

            npc.postAttackLogic(target)
            it.wait(4)
            target = npc.getCombatTarget() ?: break
        }

        npc.resetFacePawn()
        npc.removeCombatTarget()
    }

    private fun fireAttack(
        npc: Npc,
        target: Pawn,
        world: World,
    ) {
        when (world.random(6)) { // Random number between 0 and 5
            0, 1, 2 -> sendRedFireAttack(npc, target) // 50% chance (3 out of 6)
            3 -> sendWhiteFireAttack(npc, target) // Special fire attack approximately 16.7% chance
            4 -> sendBlueFireAttack(npc, target)
            5 -> sendPoisonAttack(npc, target)
        }
    }

    private fun sendMeleeAttack(
        npc: Npc,
        target: Pawn,
    ) {
        npc.prepareAttack(CombatClass.MELEE, StyleType.STAB, WeaponStyle.AGGRESSIVE)
        npc.animate(npc.combatDef.attackAnimation)
        if (target is Player) target.playSound(Sfx.DRAGON_ATTACK)
        npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
    }

    private fun sendRedFireAttack(
        npc: Npc,
        target: Pawn,
    ) {
        val RED_FIRE = npc.createProjectile(target, 393, type = ProjectileType.FIERY_BREATH)
        val RED_FIRE_HIT_GFX = Graphic(-1, 110, RED_FIRE.lifespan)
        val hitDelay = MagicCombatStrategy.getHitDelay(npc.getFrontFacingTile(target), target.getCentreTile())
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(81, priority = true)
        target.world.spawn(RED_FIRE)
        target.graphic(RED_FIRE_HIT_GFX)
        if (target is Player) target.playSound(Sfx.TWOCATS_FRY_NOOB, delay = 2)
        npc.dealHit(target = target, formula = DragonfireFormula(65), delay = hitDelay)
    }

    private fun sendBlueFireAttack(
        npc: Npc,
        target: Pawn,
    ) {
        val BLUE_FIRE = npc.createProjectile(target, 396, type = ProjectileType.FIERY_BREATH)
        val BLUE_FIRE_HIT_GFX = Graphic(-1, 110, BLUE_FIRE.lifespan)
        val hitDelay = MagicCombatStrategy.getHitDelay(npc.getFrontFacingTile(target), target.getCentreTile())
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(id = 81, priority = true)
        target.world.spawn(BLUE_FIRE)
        target.graphic(BLUE_FIRE_HIT_GFX)
        if (target is Player) {
            val player = target
            target.playSound(Sfx.TWOCATS_FRY_NOOB, delay = 2)
            npc.dealHit(target = target, formula = DragonfireFormula(maxHit = 15), delay = hitDelay)
            skills.forEach {
                val drain = 2
                player.skills.decrementCurrentLevel(it, drain, capped = false)
            }
            player.message("You are Shocked!", ChatMessageType.GAME_MESSAGE)
            npc.postAttackLogic(target)
        }
    }

    private fun sendWhiteFireAttack(
        npc: Npc,
        target: Pawn,
    ) {
        val WHITE_FIRE = npc.createProjectile(target, 395, type = ProjectileType.FIERY_BREATH)
        val WHITE_FIRE_HIT_GFX = Graphic(-1, 110, WHITE_FIRE.lifespan)
        val hitDelay = MagicCombatStrategy.getHitDelay(npc.getFrontFacingTile(target), target.getCentreTile())
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(81, priority = true)
        target.world.spawn(WHITE_FIRE)
        target.graphic(WHITE_FIRE_HIT_GFX)
        npc.dealHit(
            target = target,
            formula = DragonfireFormula(65),
            delay = hitDelay,
        ) {
            target.freeze(cycles = 6) {
                if (target is Player) {
                    target.message("You have been frozen.")
                }
            }
        }
    }

    private fun sendPoisonAttack(
        npc: Npc,
        target: Pawn,
    ) {
        val GREEN_FIRE = npc.createProjectile(target, 394, type = ProjectileType.FIERY_BREATH)
        val GREEN_FIRE_HIT_GFX = Graphic(-1, 110, GREEN_FIRE.lifespan)
        val hitDelay = MagicCombatStrategy.getHitDelay(npc.getFrontFacingTile(target), target.getCentreTile())
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(81, priority = true)
        target.world.spawn(GREEN_FIRE)
        target.graphic(GREEN_FIRE_HIT_GFX)
        if (target is Player) target.playSound(Sfx.TWOCATS_FRY_NOOB, delay = 2)
        npc.dealHit(
            target = target,
            formula = DragonfireFormula(65),
            delay = hitDelay,
            type = HitType.REGULAR_HIT,
        )
        target.poison(8)
    }
}
