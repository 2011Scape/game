package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.CombatScript
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.npc
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.prepareAttack
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.DragonfireFormula
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula

/**
 * Handles the script for Regular Dragon Combat
 * TODO Add proper sounds to fire attack once found.
 * @author Kevin Senez <ksenez94@gmail.com>
 */
object DragonCombatScript : CombatScript() {
    /**
     * List of dragon ids to use this special combat script.
     */
    override val ids =
        intArrayOf(
            Npcs.GREEN_DRAGON,
            Npcs.GREEN_DRAGON_4677,
            Npcs.GREEN_DRAGON_4678,
            Npcs.GREEN_DRAGON_4679,
            Npcs.GREEN_DRAGON_4680,
            Npcs.BLUE_DRAGON,
            Npcs.BLUE_DRAGON_4681,
            Npcs.BLUE_DRAGON_4682,
            Npcs.BLUE_DRAGON_4683,
            Npcs.BLUE_DRAGON_4684,
            Npcs.BLUE_DRAGON_5178,
            Npcs.RED_DRAGON,
            Npcs.RED_DRAGON_4669,
            Npcs.RED_DRAGON_4670,
            Npcs.RED_DRAGON_4671,
            Npcs.RED_DRAGON_4672,
            Npcs.BLACK_DRAGON,
            Npcs.BLACK_DRAGON_4673,
            Npcs.BLACK_DRAGON_4674,
            Npcs.BLACK_DRAGON_4675,
            Npcs.BLACK_DRAGON_4676,
        )

    /**
     * Method to handle all special combat attacks & etc.
     */
    override suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        val world = it.npc.world

        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 1, projectile = true) && npc.isAttackDelayReady()) {
                if (world.random(10) > 0 && npc.canAttackMelee(it, target, moveIfNeeded = true)) {
                    sendMeleeAttack(npc, target)
                } else {
                    sendFireAttack(npc, target)
                }
                npc.postAttackLogic(target)
            }
            it.wait(4)
            target = npc.getCombatTarget() ?: break
        }

        npc.resetFacePawn()
        npc.removeCombatTarget()
    }

    private fun sendMeleeAttack(
        npc: Npc,
        target: Pawn,
    ) {
        npc.prepareAttack(CombatClass.MELEE, StyleType.SLASH, WeaponStyle.AGGRESSIVE)
        npc.animate(npc.combatDef.attackAnimation)
        if (target is Player) target.playSound(Sfx.DRAGON_ATTACK)
        npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
    }

    private fun sendFireAttack(
        npc: Npc,
        target: Pawn,
    ) {
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(id = 14245, priority = true)
        npc.graphic(id = 2465)
        if (target is Player) target.playSound(Sfx.TWOCATS_FRY_NOOB, delay = 2) // TODO Make sure the sound is correct
        npc.dealHit(
            target = target,
            formula = DragonfireFormula(maxHit = 50),
            delay = 2,
        )
    }
}
