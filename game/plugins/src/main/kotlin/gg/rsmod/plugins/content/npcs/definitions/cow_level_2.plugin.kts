package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.combat.getLastHitBy
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.COW, Npcs.COW_397, Npcs.COW_1767, Npcs.COW_3309, Npcs.COW_12362, Npcs.COW_12363, Npcs.COW_12365)

val table = DropTableFactory
val cow = table.build {
    guaranteed {
        obj(Items.BONES)
        obj(Items.RAW_BEEF)
        obj(Items.COWHIDE)
    }

    main {
        total(1024)
        obj(Items.SLING, quantity = 1, slots = 64)
        nothing(slots = 960)
    }
}

table.register(cow, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!!, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 45
        }
        stats {
            hitpoints = 80
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -15
            attackCrush = -15
            defenceStab = -21
            defenceSlash = -21
            defenceCrush = -21
            defenceMagic = -21
            defenceRanged = -21
        }
        anims {
            attack = 5849
            death = 5851
            block = 5850
        }
    }
}