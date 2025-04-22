package gg.rsmod.plugins.content.combat.specialattack.weapons

import gg.rsmod.game.model.attr.PHANTOM_STRIKE_BLEED
import gg.rsmod.game.model.timer.PHANTOM_STRIKE_TIMER
import gg.rsmod.plugins.content.combat.createProjectile
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks
import gg.rsmod.plugins.content.combat.strategy.ranged.RangedProjectile

on_timer(PHANTOM_STRIKE_TIMER) {
    val bleedLeft = player.attr.getOrDefault(PHANTOM_STRIKE_BLEED, 0)
    val damage = 50.coerceAtMost(bleedLeft)
    if (bleedLeft > 0) {
        player.hit(damage, HitType.REGULAR_HIT)
    }
    val leftNow = bleedLeft - damage
    player.attr[PHANTOM_STRIKE_BLEED] = leftNow
    player.timers[PHANTOM_STRIKE_TIMER] = 3
}


SpecialAttacks.register(50, *RangedProjectile.MORRIGANS_JAVELIN.items.toIntArray()) {
    player.animate(Anims.MORRIGANS_JAVELIN_SPECIAL)
    val proj = player.createProjectile(player.tile, target.tile, RangedProjectile.MORRIGANS_JAVELIN.gfx, ProjectileType
        .MORRIGANS_JAVELIN)
    player.world.spawn(proj)
    player.playSound(Sfx.JAVELIN)

    val maxHit = RangedCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.0)
    val accuracy = RangedCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.0)
    val landHit = accuracy >= world.randomDouble()
    val delay = 1
    val hit = player.dealHit(target = target, maxHit = maxHit, landHit = landHit, delay = delay, hitType = HitType
        .RANGE)

    if (hit.landed) {
        val damage = hit.hit.hitmarks.get(0).damage
        target.attr[PHANTOM_STRIKE_BLEED] = damage
        target.timers[PHANTOM_STRIKE_TIMER] = 3
    }
}
