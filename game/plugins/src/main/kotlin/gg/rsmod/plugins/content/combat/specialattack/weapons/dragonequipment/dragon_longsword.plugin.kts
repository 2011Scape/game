package gg.rsmod.plugins.content.combat.specialattack.weapons.dragonequipment

import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks

val SPECIAL_REQUIREMENT = 25

SpecialAttacks.register(SPECIAL_REQUIREMENT, Items.DRAGON_LONGSWORD) {
    player.animate(id = 12033)
    player.graphic(id = 2117, height = 92)

    for (i in 0 until 1) {
        val maxHit = MeleeCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.25)
        val accuracy = MeleeCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.25)
        val landHit = accuracy >= world.randomDouble()
        val delay = if (target.entityType.isNpc) i + 1 else 1
        player.dealHit(
            target = target,
            maxHit = maxHit,
            landHit = landHit,
            delay = delay,
            hitType = HitType.MELEE,
        )
    }
}
