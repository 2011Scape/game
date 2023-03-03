package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.UNICORN)

val table = DropTableFactory
val unicorn = table.build {
    guaranteed {
        obj(Items.BONES)
        obj(Items.UNICORN_HORN)
    }
}

table.register(unicorn, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 90
        }
        stats {
            hitpoints = 190
            attack = 11
            strength = 13
            defence = 13
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 0
            attackCrush = 0
            defenceStab = 0
            defenceSlash = 0
            defenceCrush = 0
            defenceMagic = 0
            defenceRanged = 0
        }
        anims {
            attack = 6376
            death = 6377
            block = 6375
        }
    }
}