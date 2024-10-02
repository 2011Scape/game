package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.JAILER)

val table = DropTableFactory
val jailer =
    table.build {
        guaranteed {
            obj(Items.BONES)
            obj(Items.JAIL_KEY)
        }
    }

table.register(jailer, *ids)

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
            respawnDelay = 50
        }
        stats {
            hitpoints = 470
            attack = 40
            strength = 40
            defence = 40
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 79
            defenceSlash = 63
            defenceCrush = 47
        }
        anims {
            attack = 422
            death = 836
            block = 404
        }
    }
}
