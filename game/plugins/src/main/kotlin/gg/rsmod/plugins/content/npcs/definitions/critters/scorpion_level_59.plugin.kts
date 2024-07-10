package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.SCORPION_4402, Npcs.SCORPION_4403)

val table = DropTableFactory
val scorpion =
    table.build {
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 30)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 70)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 10)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 885)
        }
    }

table.register(scorpion, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.SCORPION_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(npc = it) {
        configs {
            attackSpeed = 4
            respawnDelay = 22
        }
        stats {
            hitpoints = 550
            attack = 50
            strength = 52
            defence = 50
        }
        bonuses {
            defenceStab = 5
            defenceSlash = 15
            defenceCrush = 15
            defenceRanged = 55
        }
        anims {
            attack = 6254
            block = 6255
            death = 6256
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.SCORPION
            level = 1
            experience = 55.0
        }
    }
}
