package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val table = DropTableFactory
val thug =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(thug, Npcs.WEAPONSMASTER)

on_npc_pre_death(Npcs.WEAPONSMASTER) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(Npcs.WEAPONSMASTER) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(Npcs.WEAPONSMASTER) {
    configs {
        attackSpeed = 5
        respawnDelay = 350
    }
    stats {
        hitpoints = 200
        attack = 21
        strength = 21
        defence = 21
    }
    bonuses {
        attackStab = 8
        attackCrush = 10
        defenceStab = 21
        defenceSlash = 23
        defenceCrush = 21
        defenceMagic = -6
        defenceRanged = 20
    }
    anims {
        attack = 386
        block = 378
        death = 836
    }
}
