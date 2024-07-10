package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.BATTLE_MAGE_914)

val table = DropTableFactory
val battleMage =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(battleMage, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GNOME_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
            spell = 502
        }
        stats {
            hitpoints = 120
            attack = 1
            strength = 1
            defence = 1
            magic = 50
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
            attack = 211
            death = 196
            block = 193
        }
        aggro {
            radius = 7
        }
    }
}
