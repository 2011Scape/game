package gg.rsmod.plugins.content.npcs.definitions.dragons

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids =
    intArrayOf(
        Npcs.BABY_BLUE_DRAGON,
        Npcs.BABY_RED_DRAGON,
        Npcs.BABY_RED_DRAGON_3588,
        Npcs.BABY_BLUE_DRAGON_4665,
        Npcs.BABY_BLUE_DRAGON_4666,
        Npcs.BABY_RED_DRAGON_4667,
        Npcs.BABY_RED_DRAGON_4668,
    )

val table = DropTableFactory
val baby_dragon =
    table.build {
        guaranteed {
            obj(Items.BABYDRAGON_BONES)
        }
    }

table.register(baby_dragon, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BABYDRAGON_DEATH)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
        }
        species {
            NpcSpecies.BASIC_DRAGON
        }
        stats {
            hitpoints = 500
            attack = 40
            strength = 40
            defence = 40
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 30
            defenceSlash = 50
            defenceCrush = 50
            defenceMagic = 40
            defenceRanged = 30
        }
        anims {
            attack = 14270
            block = 14269
            death = 14271
        }
        aggro {
            radius = 4
        }
    }
}
