package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

/**
 * @author Alycia <https://github.com/alycii>
 */
val ids = intArrayOf(Npcs.HELLHOUND)

val table = DropTableFactory
val hellhound =
    table.build {
        guaranteed {
            obj(Items.BONES, quantity = 1)
        }
        main {
            total(1000)
            obj(Items.GOLD_CHARM, slots = 690)
            obj(Items.GREEN_CHARM, slots = 50)
            obj(Items.CRIMSON_CHARM, slots = 50)
            obj(Items.BLUE_CHARM, slots = 10)
            nothing(slots = 200)
        }
    }

table.register(hellhound, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SKELETAL_HELLHOUND_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 89
        }
        stats {
            hitpoints = 1160
            attack = 105
            strength = 104
            defence = 102
            magic = 1
            ranged = 1
        }
        anims {
            attack = 6562
            death = 6564
            block = 6563
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.HELLHOUNDS
            level = 1
            experience = 116.0
        }
    }
}
