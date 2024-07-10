package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.MONK_7727)

val table = DropTableFactory
val monk =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(monk, *ids)

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
            respawnDelay = 25
        }
        stats {
            hitpoints = 150
            attack = 2
            strength = 2
            defence = 3
            magic = 1
            ranged = 1
        }
        anims {
            attack = 422
            death = 836
            block = 424
        }
    }
}
