package gg.rsmod.plugins.content.combat.specialattack.weapons

import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.specialattack.SpecialAttacks

SpecialAttacks.register(35, Items.STATIUSS_WARHAMMER, Items.STATIUS_WARHAMMER_DEG) {
    player.animate(Anims.STATIUSS_WARHAMMER_SPECIAL)
    player.graphic(Gfx.STATIUSS_WARHAMMER_SPECIAL, 0, 16)
    player.playSound(Sfx.SHATTER)

    val maxHit = MeleeCombatFormula.getMaxHit(player, target, specialAttackMultiplier = 1.25)
    val accuracy = MeleeCombatFormula.getAccuracy(player, target, specialAttackMultiplier = 1.25)
    val landHit = accuracy >= world.randomDouble()
    val delay = 1
    player.dealHit(target = target, maxHit = maxHit, landHit = landHit, delay = delay, hitType = HitType.MELEE)

    if (landHit) {
        if (target is Player) {
            val p = target as Player
            val defenceLevel = p.skills.getCurrentLevel(Skills.DEFENCE)
            val thirtyPercentDefence = defenceLevel * 0.3
            // We take away another 1% because the formula is 1 + 30%
            val newLevel = 0.coerceAtLeast((defenceLevel - (1 + thirtyPercentDefence)).toInt())
            p.skills.setCurrentLevel(Skills.DEFENCE, newLevel)
        }
        else {
            val npc = target as Npc
            val defenceLevel = npc.stats.getCurrentLevel(NpcSkills.DEFENCE)
            val thirtyPercentDefence = defenceLevel * 0.3
            // We take away another 1% because the formula is 1 + 30%
            val newLevel = 0.coerceAtLeast((defenceLevel - (1 + thirtyPercentDefence)).toInt())
            npc.stats.setCurrentLevel(NpcSkills.DEFENCE, newLevel)
        }
    }
}
