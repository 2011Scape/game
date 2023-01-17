package gg.rsmod.plugins.content.npcs.definitions

val ids = intArrayOf(Npcs.GIANT_SPIDER, Npcs.GIANT_SPIDER_60, Npcs.GIANT_SPIDER_12352)

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
        }
        stats {
            hitpoints = 50
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -10
            attackCrush = -10
            defenceStab = -10
            defenceSlash = -10
            defenceCrush = -10
            defenceMagic = -10
            defenceRanged = -10
        }
        anims {
            attack = 5327
            death = 5329
        }
    }
}