package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.Graphic
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
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy



/**
 * @author Eikenb00m <https://github.com/eikenb00m>
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


        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (npc.moveToAttackRange(it, target, distance = 1, projectile = true) && npc.isAttackDelayReady()) {
                if (world.random(5) > 0 && npc.canAttackMelee(it, target, moveIfNeeded = true)) {
                    sendMeleeAttack(npc, target)
                } else {
                    when (world.random(3)) {
                        0 -> sendRedFireAttack(npc, target)
                        1 -> sendWhiteFireAttack(npc, target)
                        2 -> sendBlueFireAttack(npc, target)
                        3 -> sendPoisionAttack(npc, target)
                    }
                }
                npc.postAttackLogic(target)
            }
            it.wait(4)
            target = npc.getCombatTarget() ?: break
        }

        npc.resetFacePawn()
        npc.removeCombatTarget()
    }

    private fun sendMeleeAttack(npc: Npc, target: Pawn) {
        npc.prepareAttack(CombatClass.MELEE, StyleType.SLASH, WeaponStyle.AGGRESSIVE)
        npc.animate(npc.combatDef.attackAnimation)
        if (target is Player) target.playSound(Sfx.DRAGON_ATTACK)
        npc.dealHit(target = target, formula = MeleeCombatFormula, delay = 1, type = HitType.MELEE)
    }

    private fun sendRedFireAttack(npc: Npc, target: Pawn) {
        val RED_SPELL = npc.createProjectile(target, 393, type = ProjectileType.MAGIC)
        val RED_SPELL_HIT_GFX = Graphic(-1, 110, RED_SPELL.lifespan)
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(id = 81, priority = true)
        target.world.spawn(RED_SPELL)
        target.graphic(RED_SPELL_HIT_GFX)
        if (target is Player) target.playSound(Sfx.TWOCATS_FRY_NOOB, delay = 2)
        npc.dealHit(target = target, formula = DragonfireFormula(maxHit = 650), delay = 2)

    }
    private fun sendBlueFireAttack(npc: Npc, target: Pawn) {
        val BLUE_SPELL = npc.createProjectile(target, 396, type = ProjectileType.MAGIC)
        val BLUE_SPELL_HIT_GFX = Graphic(-1, 110, BLUE_SPELL.lifespan)
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(id = 81, priority = true)
        target.world.spawn(BLUE_SPELL)
        target.graphic(BLUE_SPELL_HIT_GFX)
        if (target is Player) {
            val player = target
            target.playSound(Sfx.TWOCATS_FRY_NOOB, delay = 2)
        npc.dealHit(target = target, formula = DragonfireFormula(maxHit = 150), delay = 2)
        skills.forEach {
            val drain = 2
            player.skills.decrementCurrentLevel(it, drain, capped = false)
        }
            player.message("<col=990000>You are Shocked!</col>", ChatMessageType.GAME_MESSAGE)

    }
    }
    private fun sendWhiteFireAttack(npc: Npc, target: Pawn) {
        val WHITE_SPELL = npc.createProjectile(target, 395, type = ProjectileType.MAGIC)
        val WHITE_SPELL_HIT_GFX = Graphic(-1, 110, WHITE_SPELL.lifespan)
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(id = 81, priority = true)
        target.world.spawn(WHITE_SPELL)
        target.graphic(WHITE_SPELL_HIT_GFX)
        val hit = npc.dealHit(
            target = target,
            formula = DragonfireFormula(maxHit = 650),
            delay = RangedCombatStrategy.getHitDelay(npc.getFrontFacingTile(target), target.getCentreTile()) - 1
        ) {

                target.freeze(cycles = 6) {
                    if (target is Player) {
                        target.message("<col=990000>You have been frozen.</col>")
                    }
                }
    } }
    private fun sendPoisionAttack(npc: Npc, target: Pawn) {
        val GREEN_SPELL = npc.createProjectile(target, 394, type = ProjectileType.MAGIC)
        val GREEN_SPELL_HIT_GFX = Graphic(-1, 110, GREEN_SPELL.lifespan)
        npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.ACCURATE)
        npc.animate(id =80, priority = true)
        target.world.spawn(GREEN_SPELL)
        target.graphic(GREEN_SPELL_HIT_GFX)
        if (target is Player) target.playSound(Sfx.TWOCATS_FRY_NOOB, delay = 2)
        npc.dealHit(target = target, formula = DragonfireFormula(maxHit = 650), delay = 2, type = HitType.POISON)
    }

}
