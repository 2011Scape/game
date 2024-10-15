package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val table = DropTableFactory
val citizen =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(citizen, Npcs.JONNY_THE_BEARD)

on_npc_pre_death(Npcs.JONNY_THE_BEARD) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(Npcs.JONNY_THE_BEARD) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(Npcs.JONNY_THE_BEARD) {
    configs {
        attackSpeed = 4
        respawnDelay = 37
    }
    stats {
        hitpoints = 80
    }
    anims {
        attack = 422
        death = 836
        block = 404
    }
}
