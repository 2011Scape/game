package gg.rsmod.plugins.content.combat.specialattack.weapons

import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks

SpecialAttacks.register(25, Items.VESTAS_LONGSWORD, Items.VESTAS_LONGSWORD_DEG) {
    player.animate(Anims.VESTAS_LONGSWORD_SPECIAL)
    player.playSound(Sfx.STABSWORD_STAB)

    val maxHit = MeleeCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.2)
    val accuracy = MeleeCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.75)
    val landHit = accuracy >= world.randomDouble()
    val delay = 1
    player.dealHit(target = target, maxHit = maxHit, landHit = landHit, delay = delay, hitType = HitType.MELEE)
}
