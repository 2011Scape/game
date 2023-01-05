package gg.rsmod.plugins.content.combat.specialattack.weapons.dragondagger

import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks

val SPECIAL_REQUIREMENT = 25

SpecialAttacks.register(SPECIAL_REQUIREMENT, Items.DRAGON_DAGGER, Items.DRAGON_DAGGER_P, Items.DRAGON_DAGGER_P_5680, Items.DRAGON_DAGGER_P_5698, Items.CORRUPT_DRAGON_DAGGER, Items.C_DRAGON_DAGGER_DEG) {
    player.animate(id = 1062)
    player.graphic(id = 252, height = 92)
    world.spawn(AreaSound(tile = player.tile, id = 2537, radius = 10, volume = 1))

    for (i in 0 until 2) {
        val maxHit = MeleeCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.15)
        val accuracy = MeleeCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.25)
        val landHit = accuracy >= world.randomDouble()
        val delay = if (target.entityType.isNpc) i + 1 else 1
        player.dealHit(
            target = target,
            maxHit = maxHit,
            landHit = landHit,
            delay = delay,
            hitType = HitType.MELEE
        )
    }
}