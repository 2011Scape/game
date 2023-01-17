package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.combat.getLastHitBy
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.CHICKEN)

val table = DropTableFactory
val chicken = table.build {
    guaranteed {
        obj(Items.BONES)
        obj(Items.RAW_CHICKEN)
    }
    main {
        total(128)
        obj(Items.FEATHER, quantity = 5, slots = 64)
        obj(Items.FEATHER, quantity = 15, slots = 32)
        nothing(slots = 32)
    }
}

table.register(chicken, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.getLastHitBy()!!, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 25
        }
        stats {
            hitpoints = 30
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -47
            attackCrush = -42
            defenceStab = -42
            defenceSlash = -42
            defenceCrush = -42
            defenceMagic = -42
            defenceRanged = -42
        }
        anims {
            attack = 5387
            death = 5389
            block = 5388
        }
    }
}