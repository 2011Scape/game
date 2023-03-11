package gg.rsmod.plugins.content.npcs.definitions

import gg.rsmod.plugins.content.drops.DropTableFactory

val id = Npcs.DEADLY_RED_SPIDER

val table = DropTableFactory
val spider = table.build {
    table("Charms") {
        total(1000)
        obj(Items.GOLD_CHARM, quantity = 1, slots = 260)
        obj(Items.GREEN_CHARM, quantity = 1, slots = 11)
        obj(Items.CRIMSON_CHARM, quantity = 1, slots = 20)
        obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
        nothing(slots = 707)
    }
}

table.register(spider, id)

on_npc_death(id) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(id) {
    configs {
        attackSpeed = 4
        respawnDelay = 30
    }
    stats {
        hitpoints = 350
        attack = 30
        strength = 25
        defence = 30
    }
    bonuses {
        defenceStab = 15
        defenceSlash = 16
        defenceCrush = 7
        defenceMagic = 12
        defenceRanged = 16
    }
    anims {
        attack = 5327
        death = 5329
    }
}