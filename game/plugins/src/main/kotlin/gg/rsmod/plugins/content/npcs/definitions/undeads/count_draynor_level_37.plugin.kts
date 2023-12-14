package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.plugins.content.drops.DropTableFactory


val count = Npcs.COUNT_DRAYNOR

// 9356 - Count Draynor (37) - {5=2, 6=1, 7=3}
// 9357 - Count Draynor (37) - {5=2, 6=1, 7=3}
// 9356 (Count Draynor) - [1568 (1.2 secs), 2990 (0.6 secs), 3111 (2.0 secs), 3112 (3.0 secs), 3322 (7.8 secs), 3328 (3.6 secs), 3330 (1.28 secs), 3331 (1.34 secs), 3332 (1.0 secs), 3333 (0.96 secs), 12604 (12.0 secs)]
//9357 (Count Draynor) - [1568 (1.2 secs), 2990 (0.6 secs), 3111 (2.0 secs), 3112 (3.0 secs), 3322 (7.8 secs), 3328 (3.6 secs), 3330 (1.28 secs), 3331 (1.34 secs), 3332 (1.0 secs), 3333 (0.96 secs), 12604 (12.0 secs)]
//9356 - Count Draynor (37) - Anims[ Stand:3330 Walk: 3333]
//9357 - Count Draynor (37) - Anims[ Stand:3330 Walk: 3333]



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