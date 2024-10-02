package gg.rsmod.plugins.content.npcs.definitions.animals

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.BLACK_BEAR)

val table = DropTableFactory
val blackBear =
    table.build {

        guaranteed {
            obj(Items.BONES)
            obj(Items.BEAR_FUR)
            obj(Items.RAW_BEAR_MEAT)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 180)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 8)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 11)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 796)
        }
    }

table.register(blackBear, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BEAR_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 250
            attack = 15
            strength = 16
            defence = 13
        }
        anims {
            attack = 4925
            death = 4929
            block = 4927
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.BEAR
            level = 1
            experience = 25.0
        }
    }
}
