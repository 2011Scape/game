package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.JAIL_GUARD, Npcs.JAIL_GUARD_448, Npcs.JAIL_GUARD_449, Npcs.JAIL_GUARD_917)

val table = DropTableFactory
val jailGuard = table.build {
    guaranteed {
        obj(Items.BONES)
    }
}

table.register(jailGuard, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 5
            respawnDelay = 30
        }
        stats {
            hitpoints = 320
            attack = 19
            strength = 23
            defence =21
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 5
            attackCrush = 9
            defenceStab = 8
            defenceSlash = 9
            defenceCrush = 10
            defenceMagic = 4
            defenceRanged = 9
        }
        anims {
            attack = 9055
            death = 836
            block = 403
        }
        aggro {
            radius = 3
            searchDelay = 1
        }
    }
}