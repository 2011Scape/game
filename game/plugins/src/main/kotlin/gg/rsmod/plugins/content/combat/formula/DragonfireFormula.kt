package gg.rsmod.plugins.content.combat.formula

import gg.rsmod.game.model.attr.ANTIFIRE_POTION_CHARGES_ATTR
import gg.rsmod.game.model.attr.DRAGONFIRE_IMMUNITY_ATTR
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.NpcSpecies
import gg.rsmod.plugins.api.PrayerIcon
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.filterableMessage
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.hasPrayerIcon
import gg.rsmod.plugins.api.ext.isSpecies
import kotlin.math.floor

/**
 * @author Tom <rspsmods@gmail.com>
 */
class DragonfireFormula(private val maxHit: Int, private val minHit: Double = 0.0) : CombatFormula {

    override fun getAccuracy(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double): Double {
        return MagicCombatFormula.getAccuracy(pawn, target, specialAttackMultiplier)
    }

    override fun getMaxHit(
        pawn: Pawn,
        target: Pawn,
        specialAttackMultiplier: Double,
        specialPassiveMultiplier: Double
    ): Double {
        var max = maxHit.toDouble()

        if (target is Player) {
            val magicProtection = target.hasPrayerIcon(PrayerIcon.PROTECT_FROM_MAGIC)
            val antiFirePotion = (target.attr[ANTIFIRE_POTION_CHARGES_ATTR] ?: 0) > 0
            val dragonFireImmunity = target.attr[DRAGONFIRE_IMMUNITY_ATTR] ?: false
            val antiFireShield = target.hasEquipped(EquipmentType.SHIELD, *ANTI_DRAGON_SHIELDS)
            val dragonfireShield = target.hasEquipped(EquipmentType.SHIELD, *DRAGONFIRE_SHIELDS)

            if (pawn is Npc) {
                val message: String = when {
                    dragonFireImmunity || ((antiFireShield || dragonfireShield) && antiFirePotion) -> {
                        max = minHit
                        "You are completely immune to dragonfire."
                    }

                    antiFireShield || dragonfireShield -> {
                        max = if (magicProtection) minHit else (max * 0.20)
                        "Your shield absorbs most of the dragon's fiery breath."
                    }

                    else -> {
                        if (antiFirePotion) {
                            max = if (magicProtection) max * 0.335 else max * 0.665
                            if (magicProtection) "You are partially protected by your potion and prayer." else "You manage to resist some of the dragonfire."
                        } else {
                            if (magicProtection)
                                max *= 0.665
                            if (magicProtection) "Your prayer absorbs some of the dragonfire." else "You are horribly burned by the dragon's breath!"
                        }
                    }
                }

                target.filterableMessage(message)
            }
        }
        return minHit.coerceAtLeast(floor(max))
    }

    companion object {
        private val ANTI_DRAGON_SHIELDS = intArrayOf(Items.ANTIDRAGON_SHIELD)
        private val DRAGONFIRE_SHIELDS = intArrayOf(Items.DRAGONFIRE_SHIELD, Items.DRAGONFIRE_SHIELD_11284)
    }
}