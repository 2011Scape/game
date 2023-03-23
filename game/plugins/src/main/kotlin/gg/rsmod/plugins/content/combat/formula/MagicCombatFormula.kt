package gg.rsmod.plugins.content.combat.formula

import gg.rsmod.game.model.combat.WeaponStyle
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.BonusSlot
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.NpcSkills
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.enumSetOf
import gg.rsmod.plugins.api.ext.getBonus
import gg.rsmod.plugins.api.ext.getMagicDamageBonus
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.content.combat.Combat
import gg.rsmod.plugins.content.combat.CombatConfigs
import gg.rsmod.plugins.content.combat.strategy.magic.CombatSpell

/**
 * @author Tom <rspsmods@gmail.com>
 */
object MagicCombatFormula : CombatFormula {

    private val BLACK_MASKS = intArrayOf(Items.BLACK_MASK,
            Items.BLACK_MASK_1, Items.BLACK_MASK_2, Items.BLACK_MASK_3, Items.BLACK_MASK_4,
            Items.BLACK_MASK_5, Items.BLACK_MASK_6, Items.BLACK_MASK_7, Items.BLACK_MASK_8,
            Items.BLACK_MASK_9, Items.BLACK_MASK_10)

    private val MAGE_VOID = intArrayOf(Items.VOID_MAGE_HELM, Items.VOID_KNIGHT_TOP, Items.VOID_KNIGHT_ROBE, Items.VOID_KNIGHT_GLOVES)

    private val BOLT_SPELLS = enumSetOf(CombatSpell.WIND_BOLT, CombatSpell.WATER_BOLT, CombatSpell.EARTH_BOLT, CombatSpell.FIRE_BOLT)

    private val FIRE_SPELLS = enumSetOf(CombatSpell.FIRE_STRIKE, CombatSpell.FIRE_BOLT, CombatSpell.FIRE_BLAST, CombatSpell.FIRE_WAVE, CombatSpell.FIRE_SURGE)

    override fun getAccuracy(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double): Double {
        val attack = getAttackRoll(pawn)
        val defence = if (target is Player) getDefenceRoll(target) else if (target is Npc) getDefenceRoll(pawn, target) else throw IllegalArgumentException("Unhandled pawn.")

        val accuracy: Double
        if (attack > defence) {
            accuracy = 1.0 - (defence + 2.0) / (2.0 * (attack + 1.0))
        } else {
            accuracy = attack / (2.0 * (defence + 1))
        }
        return accuracy
    }

    override fun getMaxHit(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double, specialPassiveMultiplier: Double): Double {
        val spell = pawn.attr[Combat.CASTING_SPELL]
        var hit = spell?.maxHit?.toDouble() ?: 1.0
        if (pawn is Player) {

            if (pawn.hasEquipped(EquipmentType.GLOVES, Items.CHAOS_GAUNTLETS) && spell != null && spell in BOLT_SPELLS) {
                hit += 3
            }

            var multiplier = 1.0 + (pawn.getMagicDamageBonus() / 100.0)

            hit *= multiplier
            hit = Math.floor(hit)

        } else if (pawn is Npc) {
            val multiplier = 1.0 + (pawn.getMagicDamageBonus() / 100.0)
            hit *= multiplier
            hit = Math.floor(hit)
        }

        hit *= getDamageDealMultiplier(pawn)
        hit = Math.floor(hit)

        return hit
    }

    private fun getAttackRoll(pawn: Pawn): Int {
        val a = if (pawn is Player) getEffectiveAttackLevel(pawn) else if (pawn is Npc) getEffectiveAttackLevel(pawn) else 0.0
        val b = getEquipmentAttackBonus(pawn)

        var maxRoll = a * (b + 64.0)
        if (pawn is Player) {
            maxRoll = applyAttackSpecials(pawn, maxRoll)
        }
        return maxRoll.toInt()
    }

    private fun getDefenceRoll(pawn: Pawn, target: Npc): Int {
        val a = if (pawn is Player) getEffectiveDefenceLevel(pawn) else if (pawn is Npc) getEffectiveDefenceLevel(pawn) else 0.0
        val b = getEquipmentDefenceBonus(target)

        val maxRoll = a * (b + 64.0)
        return maxRoll.toInt()
    }

    private fun getDefenceRoll(target: Player): Int {
        var effectiveLvl = getEffectiveDefenceLevel(target)

        effectiveLvl *= 0.3
        effectiveLvl = Math.floor(effectiveLvl)

        var magicLvl = target.getSkills().getCurrentLevel(Skills.MAGIC).toDouble()
        magicLvl *= getPrayerAttackMultiplier(target)
        magicLvl = Math.floor(magicLvl)

        magicLvl *= 0.7
        magicLvl = Math.floor(magicLvl)

        val a = Math.floor(effectiveLvl + magicLvl).toInt()
        val b = getEquipmentDefenceBonus(target)

        val maxRoll = a * (b + 64.0)
        return maxRoll.toInt()
    }

    private fun applyAttackSpecials(player: Player, base: Double): Double {
        var hit = base

        hit *= getEquipmentMultiplier(player)
        hit = Math.floor(hit)

        return hit
    }

    private fun getEffectiveAttackLevel(player: Player): Double {
        var effectiveLevel = Math.floor(player.getSkills().getCurrentLevel(Skills.MAGIC) * getPrayerAttackMultiplier(player))

        effectiveLevel += 8.0

        if (player.hasEquipped(MAGE_VOID)) {
            effectiveLevel *= 1.45
            effectiveLevel = Math.floor(effectiveLevel)
        }

        return Math.floor(effectiveLevel)
    }

    private fun getEffectiveDefenceLevel(player: Player): Double {
        var effectiveLevel = Math.floor(player.getSkills().getCurrentLevel(Skills.DEFENCE) * getPrayerDefenceMultiplier(player))

        effectiveLevel += when (CombatConfigs.getAttackStyle(player)){
            WeaponStyle.DEFENSIVE -> 3.0
            WeaponStyle.CONTROLLED -> 1.0
            WeaponStyle.LONG_RANGE -> 3.0
            else -> 0.0
        }

        effectiveLevel += 8.0

        return Math.floor(effectiveLevel)
    }

    private fun getEffectiveAttackLevel(npc: Npc): Double {
        var effectiveLevel = npc.stats.getCurrentLevel(NpcSkills.MAGIC).toDouble()
        effectiveLevel += 8
        return effectiveLevel
    }

    private fun getEffectiveDefenceLevel(npc: Npc): Double {
        var effectiveLevel = npc.stats.getCurrentLevel(NpcSkills.DEFENCE).toDouble()
        effectiveLevel += 8
        return effectiveLevel
    }

    private fun getEquipmentAttackBonus(pawn: Pawn): Double {
        return pawn.getBonus(BonusSlot.ATTACK_MAGIC).toDouble()
    }

    private fun getEquipmentDefenceBonus(target: Pawn): Double {
        return target.getBonus(BonusSlot.DEFENCE_MAGIC).toDouble()
    }

    private fun getEquipmentMultiplier(player: Player): Double = when {
        player.hasEquipped(EquipmentType.AMULET, Items.SALVE_AMULET) -> 7.0 / 6.0
        player.hasEquipped(EquipmentType.AMULET, Items.SALVE_AMULET_E) -> 1.2
        // TODO: this should only apply when target is slayer task?
        player.hasEquipped(EquipmentType.HEAD, *BLACK_MASKS) -> 7.0 / 6.0
        else -> 1.0
    }

    private fun getPrayerAttackMultiplier(player: Player): Double = when {
        else -> 1.0
    }

    private fun getPrayerDefenceMultiplier(player: Player): Double = when {
        else -> 1.0
    }

    private fun getDamageDealMultiplier(pawn: Pawn): Double = pawn.attr[Combat.DAMAGE_DEAL_MULTIPLIER] ?: 1.0
}