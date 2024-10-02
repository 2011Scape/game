package gg.rsmod.plugins.content.npcs.definitions.dragons

import gg.rsmod.plugins.content.drops.DropTableFactory

val id = Npcs.BABY_BLACK_DRAGON

val table = DropTableFactory
val baby_dragon =
    table.build {
        guaranteed {
            obj(Items.BABYDRAGON_BONES)
        }
    }

table.register(baby_dragon, id)

on_npc_pre_death(id) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BABYDRAGON_DEATH)
}

on_npc_death(id) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(id) {
    configs {
        attackSpeed = 4
        respawnDelay = 30
    }
    species {
        NpcSpecies.BASIC_DRAGON
    }
    stats {
        hitpoints = 800
        attack = 70
        strength = 70
        defence = 70
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
