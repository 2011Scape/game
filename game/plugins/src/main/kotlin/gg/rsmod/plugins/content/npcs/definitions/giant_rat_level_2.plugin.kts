package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.GIANT_RAT)

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
            respawnDelay = 1
        }
        stats {
            hitpoints = 50
            attack = 2
            strength = 3
            defence = 2
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
            attack = 14859
            death = 14860
            block = 14861
        }

        slayerData {
            levelRequirement = 1
            xp = 5.0
        }
    }
}