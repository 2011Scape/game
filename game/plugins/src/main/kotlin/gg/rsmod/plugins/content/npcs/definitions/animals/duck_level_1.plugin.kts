package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val landDucks = intArrayOf(Npcs.DUCK_6113, Npcs.DRAKE)
val waterDucks = intArrayOf(Npcs.DUCK, Npcs.DUCK_2693)
val ids = landDucks + waterDucks

val table = DropTableFactory
val landDuckTable =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
    }

table.register(landDuckTable, *landDucks)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.DUCK_DEATH)
}

on_npc_death(*landDucks) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

(landDucks + waterDucks).forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 25
        }
        stats {
            hitpoints = 30
            attack = 1
            strength = 1
            defence = 1
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = -47
            attackCrush = -42
            defenceStab = -42
            defenceSlash = -42
            defenceCrush = -42
            defenceMagic = -42
            defenceRanged = -42
        }
        anims {
            attack = 747
            death = 750
            block = 1014
        }
        slayer {
            assignment = SlayerAssignment.BIRD
            level = 1
            experience = 3.0
        }
    }
}
