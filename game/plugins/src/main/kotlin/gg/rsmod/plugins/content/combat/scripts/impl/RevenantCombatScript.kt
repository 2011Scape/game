package gg.rsmod.plugins.content.combat.scripts.impl

import gg.rsmod.game.model.attr.POISON_TICKS_LEFT_ATTR
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.BonusSlot
import gg.rsmod.plugins.api.HitType
import gg.rsmod.plugins.api.ProjectileType
import gg.rsmod.plugins.api.cfg.Anims
import gg.rsmod.plugins.api.cfg.Gfx
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.cfg.Sfx
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula
import gg.rsmod.plugins.content.combat.strategy.MagicCombatStrategy
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy
import gg.rsmod.plugins.content.mechanics.prayer.Prayer
import gg.rsmod.plugins.content.mechanics.prayer.Prayers

object RevenantCombatScript {

    val ids =
        intArrayOf(
            Npcs.REVENANT_IMP,
            Npcs.REVENANT_GOBLIN,
            Npcs.REVENANT_GOBLIN_13468,
            Npcs.REVENANT_GOBLIN_13469,
            Npcs.REVENANT_ICEFIEND,
            Npcs.REVENANT_PYREFIEND,
            Npcs.REVENANT_HOBGOBLIN,
            Npcs.REVENANT_VAMPYRE,
            Npcs.REVENANT_WEREWOLF,
            Npcs.REVENANT_CYCLOPS,
            Npcs.REVENANT_HELLHOUND,
            Npcs.REVENANT_DEMON,
            Npcs.REVENANT_ORK,
            Npcs.REVENANT_DARK_BEAST,
            Npcs.REVENANT_KNIGHT,
            Npcs.REVENANT_DRAGON
        )

    val healsLeft: HashMap<Int, Int> = HashMap()

    suspend fun handleSpecialCombat(it: QueueTask) {
        val npc = it.npc
        var target = npc.getCombatTarget() ?: return
        while (npc.canEngageCombat(target)) {
            npc.facePawn(target)
            if (target is Player) {
                val player = target
                var attack = "magic"
                if (Prayers.isActive(player, Prayer.PROTECT_FROM_MAGIC)) {
                    var meleeDef = player.getBonus(BonusSlot.DEFENCE_STAB)
                    var rangedDef = player.getBonus(BonusSlot.DEFENCE_RANGED)
                    if (meleeDef <= rangedDef) {
                        attack = "melee"
                    }
                    else {
                        attack = "ranged"
                    }
                }
                if (!healsLeft.containsKey(it.npc.id)) {
                    healsLeft[it.npc.id] = 10
                }
                if (it.npc.getCurrentLifepoints() < it.npc.getMaximumLifepoints() / 2 && healsLeft[it.npc.id]!! > 0) {
                    attack = "heal"
                }
                val distance = if (attack == "melee") 1 else 6
                if (npc.moveToAttackRange(it, target, distance = distance, projectile = if (attack == "melee") false
                        else true) && npc
                    .isAttackDelayReady
                        ()) {
                    when (attack) {
                        "melee" -> meleeAttack(it, target)
                        "magic" -> magicAttack(it, target)
                        "ranged" -> rangedAttack(it, target)
                        "heal" -> heal(it, target)
                    }
                    npc.postAttackLogic(target)
                }
                it.wait(5)
                target = npc.getCombatTarget() ?: break
            }
        }
        npc.resetFacePawn()
        npc.removeCombatTarget()
    }

    fun rangedAttack(it: QueueTask, target: Player) {
        it.npc.prepareAttack(CombatClass.RANGED, StyleType.RANGED, WeaponStyle.NONE)
        val rev = Revenant.forId(it.npc.id)
        it.npc.animate(rev.rangedAnim)
        val rangedAttack = it.npc.createProjectile(target, Gfx.GFX_1278, type = ProjectileType.MAGIC)
        target.world.spawn(rangedAttack)
        val hitDelay = RangedCombatStrategy.getHitDelay(it.npc.getCentreTile(), target.getCentreTile())
        it.npc.dealHit(
            target = target,
            formula = RangedCombatFormula,
            delay = hitDelay,
            type = HitType.RANGE,
        )
    }

    fun magicAttack(it: QueueTask, target: Player) {
        it.npc.prepareAttack(CombatClass.MAGIC, StyleType.MAGIC, WeaponStyle.NONE)
        val rev = Revenant.forId(it.npc.id)
        it.npc.animate(rev.mageAnim)
        val magicAttack = it.npc.createProjectile(target, Gfx.GFX_1276, type = ProjectileType.MAGIC)
        target.world.spawn(magicAttack)
        val hitDelay = MagicCombatStrategy.getHitDelay(it.npc.getCentreTile(), target.getCentreTile())
        it.npc.dealHit(
            target = target,
            formula = MagicCombatFormula,
            delay = hitDelay,
            type = HitType.MAGIC,
        )
    }

    fun meleeAttack(it: QueueTask, target: Player) {
        it.npc.prepareAttack(CombatClass.MELEE, StyleType.STAB, WeaponStyle.NONE)
        it.npc.animate(it.npc.combatDef.attackAnimation)
        it.npc.dealHit(
            target = target,
            formula = MeleeCombatFormula,
            delay = 1,
            type = HitType.MELEE,
        )
    }

    fun heal(it: QueueTask, target: Player) {
        val rev = Revenant.forId(it.npc.id)
        it.npc.animate(rev.healAnim)
        if (it.npc.isPoisoned()) {
            target.playSound(Sfx.DRINK)
            it.npc.attr[POISON_TICKS_LEFT_ATTR] = 0
        }
        else {
            target.playSound(Sfx.EAT)
            val healAmt = it.npc.getMaximumLifepoints() / 6
            it.npc.setCurrentLifepoints(it.npc.getCurrentLifepoints() + healAmt)
            val leftNow = healsLeft[it.npc.id]!! - 1
            healsLeft[it.npc.id] = leftNow
        }
    }
}

enum class Revenant(
    val id: Int,
    val mageAnim: Int,
    val rangedAnim: Int,
    val healAnim: Int
) {
    REVENANT_IMP(Npcs.REVENANT_IMP, Anims.REVENANT_IMP_MAGE_ATTACK, Anims.REVENANT_IMP_RANGED_ATTACK,
        Anims.REVENANT_IMP_HEAL),
    REVENANT_GOBLIN_15(Npcs.REVENANT_GOBLIN, Anims.REVENANT_GOBLIN_MAGE_ATTACK, Anims.REVENANT_GOBLIN_RANGED_ATTACK,
        Anims.REVENANT_GOBLIN_HEAL),
    REVENANT_GOBLIN_30(Npcs.REVENANT_GOBLIN_13468, Anims.REVENANT_GOBLIN_MAGE_ATTACK, Anims.REVENANT_GOBLIN_RANGED_ATTACK,
        Anims.REVENANT_GOBLIN_HEAL),
    REVENANT_GOBLIN_37(Npcs.REVENANT_GOBLIN_13469, Anims.REVENANT_GOBLIN_MAGE_ATTACK, Anims.REVENANT_GOBLIN_RANGED_ATTACK,
        Anims.REVENANT_GOBLIN_HEAL),
    REVENANT_ICEFIEND(Npcs.REVENANT_ICEFIEND, Anims.REVENANT_FIEND_MAGE_ATTACK, Anims.REVENANT_FIEND_RANGED_ATTACK,
        Anims.REVENANT_FIEND_HEAL),
    REVENANT_PYREFIEND(Npcs.REVENANT_PYREFIEND, Anims.REVENANT_FIEND_MAGE_ATTACK, Anims.REVENANT_FIEND_RANGED_ATTACK,
        Anims.REVENANT_FIEND_HEAL),
    REVENANT_HOBGOBLIN(Npcs.REVENANT_HOBGOBLIN, Anims.REVENANT_HOBGOBLIN_MAGE_ATTACK, Anims.REVENANT_HOBGOBLIN_RANGED_ATTACK,
        Anims.REVENANT_HOBGOBLIN_HEAL),
    REVENANT_VAMPYRE(Npcs.REVENANT_VAMPYRE, Anims.REVENANT_VAMPYRE_MAGE_ATTACK, Anims.REVENANT_VAMPYRE_RANGED_ATTACK,
        Anims.REVENANT_VAMPYRE_HEAL),
    REVENANT_WEREWOLF(Npcs.REVENANT_WEREWOLF, Anims.REVENANT_WEREWOLF_MAGE_ATTACK, Anims.REVENANT_WEREWOLF_RANGED_ATTACK,
        Anims.REVENANT_WEREWOLF_HEAL),
    REVENANT_CYCLOPS(Npcs.REVENANT_CYCLOPS, Anims.REVENANT_CYCLOPS_MAGE_ATTACK, Anims.REVENANT_CYCLOPS_RANGED_ATTACK,
        Anims.REVENANT_CYCLOPS_HEAL),
    REVENANT_HELLHOUND(Npcs.REVENANT_HELLHOUND, Anims.REVENANT_HELLHOUND_MAGE_ATTACK, Anims.REVENANT_HELLHOUND_RANGED_ATTACK,
        Anims.REVENANT_HELLHOUND_HEAL),
    REVENANT_DEMON(Npcs.REVENANT_DEMON, Anims.REVENANT_DMEON_MAGE_ATTACK, Anims.REVENANT_DEMON_RANGED_ATTACK,
        Anims.REVENANT_DEMON_HEAL),
    REVENANT_ORK(Npcs.REVENANT_ORK, Anims.REVENANT_ORK_MAGE_ATTACK, Anims.REVENANT_ORK_RANGED_ATTACK,
        Anims.REVENANT_ORK_HEAL),
    REVENANT_DARK_BEAST(Npcs.REVENANT_DARK_BEAST, Anims.REVENANT_DARK_BEAST_MAGE_ATTACK, Anims.REVENANT_DARK_BEAST_RANGED_ATTACK,
        Anims.REVENANT_DARK_BEAST_HEAL),
    REVENANT_KNIGHT(Npcs.REVENANT_KNIGHT, Anims.REVENANT_KNIGHT_MAGE_ATTACK, Anims.REVENANT_KNIGHT_RANGED_ATTACK,
        Anims.REVENANT_KNIGHT_HEAL),
    REVENANT_DRAGON(Npcs.REVENANT_DRAGON, Anims.REVENANT_DRAGON_PROJ, Anims.REVENANT_DRAGON_PROJ,
        Anims.REVENANT_DRAGON_HEAL)
    ;

    companion object {
        fun forId(id: Int): Revenant {
            Revenant.values().forEach {
                if (it.id == id) {
                    return it
                }
            }
            return REVENANT_IMP
        }
    }

}
