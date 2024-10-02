package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.GIANT_RAT_4944, Npcs.GIANT_RAT_4945)

val table = DropTableFactory
val rat =
    table.build {
        guaranteed {
            obj(Items.BONES)
            obj(Items.RAW_RAT_MEAT)
        }
    }

table.register(rat, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.RAT_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
            deathDelay = 10
        }
        stats {
            hitpoints = 250
            attack = 22
            strength = 23
            defence = 22
        }
        anims {
            attack = 14859
            death = 14860
            block = 14861
        }
    }
}
