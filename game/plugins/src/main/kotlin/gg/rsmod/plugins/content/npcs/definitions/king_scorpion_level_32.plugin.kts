package gg.rsmod.plugins.content.npcs.definitions

set_combat_def(npc = Npcs.KING_SCORPION) {
    configs {
        attackSpeed = 4
        respawnDelay = 25
    }
    stats {
        hitpoints = 300
        attack = 30
        strength = 29
        defence = 23
    }
    bonuses {
        defenceStab = 5
        defenceSlash = 15
        defenceCrush = 15
        defenceRanged = 5
    }
    anims {
        attack = 6254
        block = 6255
        death = 6256
    }
}