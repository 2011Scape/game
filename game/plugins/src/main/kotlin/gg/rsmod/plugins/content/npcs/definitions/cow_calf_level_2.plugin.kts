package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.combat.getLastHitBy
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.COW_CALF, Npcs.COW_CALF_12364, Npcs.COW_CALF_12366)

val table = DropTableFactory
val cowCalf = table.build {
    guaranteed {
        obj(Items.BONES)
        obj(Items.RAW_BEEF)
        obj(Items.COWHIDE)
    }
}

table.register(cowCalf, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.getLastHitBy()!!, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 45
        }
        stats {
            hitpoints = 60
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -20
            attackCrush = -20
            defenceStab = -26
            defenceSlash = -26
            defenceCrush = -26
            defenceMagic = -26
            defenceRanged = -26
        }
        anims {
            attack = 5849
            death = 5851
            block = 5850
        }
    }
}