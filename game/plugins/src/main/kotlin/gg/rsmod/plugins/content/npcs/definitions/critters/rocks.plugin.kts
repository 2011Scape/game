package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.StyleType

val ids = intArrayOf(Npcs.ROCKS, Npcs.ROCKS_1268)

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 6
            respawnDelay = 15
            attackStyle = StyleType.STAB
        }
        stats {
            hitpoints = 510
            attack = 15
            strength = 15
            defence = 10
        }
        anims {
            attack = Anims.ROCK_CRAB_ATTACK
            block = Anims.ROCK_CRAB_BLOCK
            death = Anims.ROCK_CRAB_DEATH
        }
        aggro {
            radius = 4
            alwaysAggro()
        }
    }
}
