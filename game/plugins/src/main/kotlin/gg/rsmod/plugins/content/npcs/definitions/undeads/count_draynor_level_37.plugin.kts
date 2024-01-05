package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.plugins.content.drops.DropTableFactory

/**
 * Values used for Count Draynor
 */

val count = Npcs.COUNT_DRAYNOR


val table = DropTableFactory
val countTable = table.build {

    guaranteed {
        nothing(128)
    }
}

table.register(countTable)

on_npc_pre_death() {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.VAMPIRE_DEATH)
    npc.animate(1568, delay = 0, true, true)
}

on_npc_death() {

}

set_combat_def(count) {
    configs {
        attackSpeed = 4
        respawnDelay = 0
    }

    stats {
        hitpoints = 350
        attack = 30
        strength = 25
        defence = 30
        magic = 1
        ranged = 1
    }

    bonuses {
        defenceStab = 2
        defenceSlash = 1
        defenceCrush = 3
        defenceMagic = 0
        defenceRanged = 0
    }

    anims {
        attack = 3331
        block = 3332
        death = 12604
        // 1568 is the anim for when you have to hit
    }
}