package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.MARKET_GUARD_2236)

val table = DropTableFactory
val guard =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(guard, *ids)

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
            attackSpeed = 5
            respawnDelay = 50
        }
        stats {
            hitpoints = 220
            attack = 17
            strength = 18
            defence = 13
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 9
            attackCrush = 7
            defenceStab = 24
            defenceSlash = 14
            defenceCrush = 19
            defenceMagic = 4
            defenceRanged = 16
        }
        anims {
            attack = 390
            death = 836
            block = 1156
        }
    }
}
