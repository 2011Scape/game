package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory

/**
 * @author Harley <https://github.com/HarleyGilpin>
 */
val ids = intArrayOf(Npcs.WOLF_4414, Npcs.WOLF_11263)

val table = DropTableFactory
val whiteWolf =
    table.build {
        guaranteed {
            obj(Items.WOLF_BONES, quantity = 1)
            obj(Items.BONES, quantity = 1)
        }
        main {
            total(1000)
            obj(Items.GOLD_CHARM, slots = 20)
            obj(Items.GREEN_CHARM, slots = 30)
            obj(Items.CRIMSON_CHARM, slots = 50)
            obj(Items.BLUE_CHARM, slots = 10)
            nothing(slots = 890)
        }
    }

table.register(whiteWolf, *ids)

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
            attackStyle = StyleType.STAB
        }
        stats {
            hitpoints = 100
            attack = 10
            strength = 10
            defence = 10
            magic = 1
            ranged = 1
        }
        anims {
            attack = 6579
            death = 6576
            block = 6578
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.WOLF
            level = 1
            experience = 10.0
        }
    }
}
