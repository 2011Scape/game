package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

/**
 * @author Alycia <https://github.com/alycii>
 */
val ids = intArrayOf(Npcs.BIG_WOLF_6046)

val table = DropTableFactory
val bigWolf =
    table.build {
        guaranteed {
            obj(Items.WOLF_BONES, quantity = 1)
        }
        main {
            total(1000)
            obj(Items.GOLD_CHARM, slots = 110)
            obj(Items.GREEN_CHARM, slots = 50)
            obj(Items.CRIMSON_CHARM, slots = 140)
            obj(Items.BLUE_CHARM, slots = 80)
            nothing(slots = 620)
        }
    }

table.register(bigWolf, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.WOLF_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 90
        }
        stats {
            hitpoints = 740
            attack = 60
            strength = 61
            defence = 62
            magic = 1
            ranged = 1
        }
        anims {
            attack = 6559
            death = 6558
            block = 6557
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.WOLF
            level = 1
            experience = 74.0
        }
    }
}
