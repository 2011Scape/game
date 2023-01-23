package gg.rsmod.plugins.content.combat.formula

import gg.rsmod.game.model.combat.AttackStyle
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.*
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.combat.Combat
import gg.rsmod.plugins.content.combat.CombatConfigs

/**
 * @author Tom <rspsmods@gmail.com>
 */
object RangedCombatFormula : CombatFormula {

    private val BLACK_MASKS = intArrayOf(Items.BLACK_MASK,
            Items.BLACK_MASK_1, Items.BLACK_MASK_2, Items.BLACK_MASK_3, Items.BLACK_MASK_4,
            Items.BLACK_MASK_5, Items.BLACK_MASK_6, Items.BLACK_MASK_7, Items.BLACK_MASK_8,
            Items.BLACK_MASK_9, Items.BLACK_MASK_10)


    private val RANGED_VOID = intArrayOf(Items.VOID_RANGER_HELM, Items.VOID_KNIGHT_TOP, Items.VOID_KNIGHT_ROBE, Items.VOID_KNIGHT_GLOVES)

    override fun getAccuracy(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double): Double {
        val attack = getAttackRoll(pawn, target, specialAttackMultiplier)
        val defence = getDefenceRoll(pawn, target)

        val accuracy: Double
        if (attack > defence) {
            accuracy = 1.0 - (defence + 2.0) / (2.0 * (attack + 1.0))
        } else {
            accuracy = attack / (2.0 * (defence + 1))
        }
        return accuracy
    }

    override fun getMaxHit(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double, specialPassiveMultiplier: Double): Double {
        val a = if (pawn is Player) getEffectiveRangedLevel(pawn) else if (pawn is Npc) getEffectiveRangedLevel(pawn) else 0.0
        val b = getEquipmentRangedBonus(pawn)

        var base = 0.5 + a * (b + 64.0) / 640.0
        if (pawn is Player) {
            base = applyRangedSpecials(pawn, target, base, specialAttackMultiplier, specialPassiveMultiplier)
        }
        return base
    }

    private fun getAttackRoll(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double): Int {
        val a = if (pawn is Player) getEffectiveAttackLevel(pawn) else if (pawn is Npc) getEffectiveAttackLevel(pawn) else 0.0
        val b = getEquipmentAttackBonus(pawn)

        var maxRoll = a * (b + 64.0)
        if (pawn is Player) {
            maxRoll = applyAttackSpecials(pawn, target, maxRoll, specialAttackMultiplier)
        }
        return maxRoll.toInt()
    }

    private fun getDefenceRoll(pawn: Pawn, target: Pawn): Int {
        val a = if (pawn is Player) getEffectiveDefenceLevel(pawn) else if (pawn is Npc) getEffectiveDefenceLevel(pawn) else 0.0
        val b = getEquipmentDefenceBonus(target)

        var maxRoll = a * (b + 64.0)
        maxRoll = applyDefenceSpecials(target, maxRoll)
        return maxRoll.toInt()
    }

    private fun applyRangedSpecials(player: Player, target: Pawn, base: Double, specialAttackMultiplier: Double, specialPassiveMultiplier: Double): Double {
        var hit = base.toDouble()

        hit *= getEquipmentMultiplier(player)
        hit = Math.floor(hit)

        if (specialAttackMultiplier == 1.0) {
            val multiplier = 1.0
            hit *= multiplier
        } else {
            hit *= specialAttackMultiplier
        }

        if (target.hasPrayerIcon(PrayerIcon.PROTECT_FROM_MISSILES)) {
            hit *= 0.6
        }

        if (specialPassiveMultiplier == 1.0) {
            hit = applyPassiveMultiplier(player, target, hit)
        } else {
            hit *= specialPassiveMultiplier
        }

        hit *= getDamageDealMultiplier(player)

        hit *= getDamageTakeMultiplier(target)

        return hit
    }

    private fun applyAttackSpecials(player: Player, target: Pawn, base: Double, specialAttackMultiplier: Double): Double {
        var hit = base

        hit *= getEquipmentMultiplier(player)

        if (specialAttackMultiplier == 1.0) {
            val multiplier = 1.0
            hit *= multiplier
        } else {
            hit *= specialAttackMultiplier
        }

        return hit
    }

    private fun applyDefenceSpecials(target: Pawn, base: Double): Double {
        var hit = base

        /**
        if (target is Player && isWearingTorag(target) && target.hasEquipped(EquipmentType.AMULET, Items.AMULET_OF_THE_DAMNED_FULL)) {
            val lost = (target.getMaxHp() - target.getCurrentHp()) / 100.0
            val max = target.getMaxHp() / 100.0
            hit *= (1.0 + (lost * max))
            hit = Math.floor(hit)
        }**/

        return hit
    }

    private fun getEquipmentRangedBonus(pawn: Pawn): Double = when (pawn) {
        is Player -> pawn.getRangedStrengthBonus().toDouble()
        is Npc -> pawn.getRangedStrengthBonus().toDouble()
        else -> throw IllegalArgumentException("Invalid pawn type. $pawn")
    }

    private fun getEquipmentAttackBonus(pawn: Pawn): Double {
        return pawn.getBonus(BonusSlot.ATTACK_RANGED).toDouble()
    }

    private fun getEquipmentDefenceBonus(target: Pawn): Double {
        return target.getBonus(BonusSlot.DEFENCE_RANGED).toDouble()
    }

    private fun getEffectiveRangedLevel(player: Player): Double {
        var effectiveLevel = Math.floor(player.getSkills().getCurrentLevel(Skills.RANGED) * getPrayerRangedMultiplier(player))

        effectiveLevel += when (CombatConfigs.getAttackStyle(player)){
            AttackStyle.ACCURATE -> 3.0
            else -> 0.0
        }

        effectiveLevel += 8.0

        if (player.hasEquipped(RANGED_VOID)) {
            effectiveLevel *= 1.10
            effectiveLevel = Math.floor(effectiveLevel)
        }

        return effectiveLevel
    }

    private fun getEffectiveAttackLevel(player: Player): Double {
        var effectiveLevel = Math.floor(player.getSkills().getCurrentLevel(Skills.RANGED) * getPrayerAttackMultiplier(player))

        effectiveLevel += when (CombatConfigs.getAttackStyle(player)){
            AttackStyle.ACCURATE -> 3.0
            else -> 0.0
        }

        effectiveLevel += 8.0

        if (player.hasEquipped(RANGED_VOID)) {
            effectiveLevel *= 1.10
            effectiveLevel = Math.floor(effectiveLevel)
        }

        return effectiveLevel
    }

    private fun getEffectiveDefenceLevel(player: Player): Double {
        var effectiveLevel = Math.floor(player.getSkills().getCurrentLevel(Skills.DEFENCE) * getPrayerDefenceMultiplier(player))

        effectiveLevel += when (CombatConfigs.getAttackStyle(player)){
            AttackStyle.DEFENSIVE -> 3.0
            AttackStyle.CONTROLLED -> 1.0
            AttackStyle.LONG_RANGE -> 3.0
            else -> 0.0
        }

        effectiveLevel += 8.0

        return effectiveLevel
    }

    private fun getEffectiveRangedLevel(npc: Npc): Double {
        var effectiveLevel = npc.stats.getCurrentLevel(NpcSkills.RANGED).toDouble()
        effectiveLevel += 8
        return effectiveLevel
    }

    private fun getEffectiveAttackLevel(npc: Npc): Double {
        var effectiveLevel = npc.stats.getCurrentLevel(NpcSkills.RANGED).toDouble()
        effectiveLevel += 8
        return effectiveLevel
    }

    private fun getEffectiveDefenceLevel(npc: Npc): Double {
        var effectiveLevel = npc.stats.getCurrentLevel(NpcSkills.DEFENCE).toDouble()
        effectiveLevel += 8
        return effectiveLevel
    }

    private fun getPrayerRangedMultiplier(player: Player): Double = when {
        else -> 1.0
    }

    private fun getPrayerAttackMultiplier(player: Player): Double = when {
        else -> 1.0
    }

    private fun getPrayerDefenceMultiplier(player: Player): Double = when {
        else -> 1.0
    }

    private fun getEquipmentMultiplier(player: Player): Double = when {
        player.hasEquipped(EquipmentType.AMULET, Items.SALVE_AMULET) -> 7.0 / 6.0
        player.hasEquipped(EquipmentType.AMULET, Items.SALVE_AMULET_E) -> 1.2
        // TODO: this should only apply when target is slayer task?
        player.hasEquipped(EquipmentType.HEAD, *BLACK_MASKS) -> 7.0 / 6.0
        else -> 1.0
    }

    private fun applyPassiveMultiplier(player: Player, target: Pawn, base: Double): Double {
        return base
    }

    private fun getDamageDealMultiplier(pawn: Pawn): Double = pawn.attr[Combat.DAMAGE_DEAL_MULTIPLIER] ?: 1.0

    private fun getDamageTakeMultiplier(pawn: Pawn): Double = pawn.attr[Combat.DAMAGE_TAKE_MULTIPLIER] ?: 1.0

    private fun isDragon(pawn: Pawn): Boolean {
        if (pawn.entityType.isNpc) {
            return (pawn as Npc).isSpecies(NpcSpecies.DRAGON)
        }
        return false
    }

    private fun isFiery(pawn: Pawn): Boolean {
        if (pawn.entityType.isNpc) {
            return (pawn as Npc).isSpecies(NpcSpecies.FIERY)
        }
        return false
    }

    private fun isWearingTorag(player: Player): Boolean {
        return player.hasEquipped(EquipmentType.HEAD, Items.TORAGS_HELM, Items.TORAGS_HELM_25, Items.TORAGS_HELM_50, Items.TORAGS_HELM_75, Items.TORAGS_HELM_100)
                && player.hasEquipped(EquipmentType.WEAPON, Items.TORAGS_HAMMERS, Items.TORAGS_HAMMERS_25, Items.TORAGS_HAMMERS_50, Items.TORAGS_HAMMERS_75, Items.TORAGS_HAMMERS_100)
                && player.hasEquipped(EquipmentType.CHEST, Items.TORAGS_PLATEBODY, Items.TORAGS_PLATEBODY_25, Items.TORAGS_PLATEBODY_50, Items.TORAGS_PLATEBODY_75, Items.TORAGS_PLATEBODY_100)
                && player.hasEquipped(EquipmentType.LEGS, Items.TORAGS_PLATELEGS, Items.TORAGS_PLATELEGS_25, Items.TORAGS_PLATELEGS_50, Items.TORAGS_PLATELEGS_75, Items.TORAGS_PLATELEGS_100)
    }
}