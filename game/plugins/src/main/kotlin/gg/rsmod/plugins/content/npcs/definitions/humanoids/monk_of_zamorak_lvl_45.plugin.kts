package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.MONK_OF_ZAMORAK_190)

val table = DropTableFactory
val monkOfZamorak =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 20)
            obj(Items.ZAMORAK_ROBE_1035, slots = 1)
            obj(Items.ZAMORAK_ROBE, slots = 1)
            nothing(slots = 18)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 10)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 100)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 20)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 10)
            nothing(slots = 860)
        }
    }

table.register(monkOfZamorak, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 250
        }
        stats {
            hitpoints = 400
            attack = 38
            strength = 38
            defence = 42
            magic = 40
            ranged = 1
        }
        anims {
            attack = 422
            death = 836
            block = 404
        }
        aggro {
            radius = 4
        }
    }
}
