package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment

val ids = intArrayOf(Npcs.GHOST, Npcs.GHOST_104, Npcs.GHOST_5349, Npcs.GHOST_5350, Npcs.GHOST_5351, Npcs.GHOST_5352)

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 40
        }
        stats {
            hitpoints = 250
            attack = 13
            strength = 13
            defence = 18
        }
        bonuses {
            defenceStab = 5
            defenceSlash = 5
            defenceCrush = 5
            defenceMagic = 5
            defenceRanged = 5
        }
        anims {
            attack = 5535
            death = 5534
            block = 5533
        }
        slayerData {
            slayerAssignment = SlayerAssignment.GHOST
            levelRequirement = 1
            xp = 25.0
        }
    }
}
