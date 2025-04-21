package gg.rsmod.plugins.content.combat.specialattack.weapons

import gg.rsmod.game.model.attr.HAMSTRING
import gg.rsmod.game.model.attr.SPEAR_WALL
import gg.rsmod.game.model.timer.HAMSTRING_TIMER
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks


on_timer(HAMSTRING_TIMER) {
    player.attr.remove(HAMSTRING)
}

SpecialAttacks.register(50, Items.MORRIGANS_THROWING_AXE) {
    player.animate(Anims.MORRIGANS_THROWING_AXE_SPECIAL)
    player.graphic(Gfx.MORRIGANS_THROWING_AXE_SPECIAL, 0, 0)
    player.playSound(Sfx.THROWINGAXE)

    val maxHit = RangedCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.2)
    val accuracy = RangedCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.0)
    val landHit = accuracy >= world.randomDouble()
    val delay = 1
    player.dealHit(target = target, maxHit = maxHit, landHit = landHit, delay = delay, hitType = HitType.RANGE)

    if (target is Player) {
        target.attr[HAMSTRING] = true
        target.timers[HAMSTRING_TIMER] = 100
    }
}
