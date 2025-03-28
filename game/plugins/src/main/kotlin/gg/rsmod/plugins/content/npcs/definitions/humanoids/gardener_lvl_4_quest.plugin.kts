package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory


val table = DropTableFactory
val gardener =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(gardener, Npcs.GARDENER_3914)

on_npc_pre_death(Npcs.GARDENER_3914) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(Npcs.GARDENER_3914) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(Npcs.GARDENER_3914) {
    configs {
        attackSpeed = 6
        respawnDelay = 25
    }
    stats {
        hitpoints = 70
        attack = 4
        strength = 1
        defence = 1
        magic = 1
        ranged = 1
    }
    bonuses {
        attackStab = 5
        attackCrush = 6
        defenceStab = 0
        defenceSlash = 0
        defenceCrush = 0
        defenceMagic = 0
        defenceRanged = 0
    }
    anims {
        attack = 428
        death = 836
        block = 420
    }
}
