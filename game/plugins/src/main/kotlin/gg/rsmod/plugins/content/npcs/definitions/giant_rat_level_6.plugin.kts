package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.GIANT_RAT, Npcs.GIANT_RAT_87, Npcs.GIANT_RAT_446)

val table = DropTableFactory
val rat = table.build {
    guaranteed {
        obj(Items.BONES)
        obj(Items.RAW_RAT_MEAT)
    }
}

table.register(rat, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
        }
        stats {
            hitpoints = 100
            attack = 6
            strength = 5
            defence = 2
        }
        anims {
            attack = 14859
            death = 14860
            block = 14861
        }

        slayerData {
            levelRequirement = 1
            xp = 10.0
        }
    }
}