package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.HIGHWAYMAN, Npcs.HIGHWAYMAN_2677, Npcs.HIGHWAYMAN_7443)

val table = DropTableFactory
val highwayman =
    table.build {
        guaranteed {
            obj(Items.BONES)
            obj(Items.CAPE_1019)
        }
        main {
            total(20)
            obj(Items.IRON_BOLTS, quantityRange = 2..12, slots = 1)
            nothing(19)
        }
    }

table.register(highwayman, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 60
        }

        stats {
            hitpoints = 130
            attack = 2
            strength = 2
            defence = 4
            magic = 1
            ranged = 1
        }

        bonuses {
            attackBonus = 6
            strengthBonus = 7
            defenceSlash = 3
            defenceCrush = 2
            defenceRanged = 2
        }

        aggro {
            radius = 4
        }

        anims {
            attack = 428
            block = 424
            death = 836
        }
    }
}
