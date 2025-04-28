package gg.rsmod.plugins.content.npcs.definitions.critters

import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems

val ids = intArrayOf(Npcs.ROCK_CRAB, Npcs.ROCK_CRAB_1267)

val table = DropTableFactory
val crab = table.build {
    main {
        total(128)

        // 1/128
        obj(Items.EMPTY_OYSTER, 3, 1)
        obj(Items.OYSTER_PEARL, 1, 1)
        obj(Items.CASKET, 1, 1)
        obj(Items.SPINACH_ROLL, 1, 1)

        // 1/64
        obj(Items.SEAWEED_NOTED, 5, 2)
        obj(Items.EDIBLE_SEAWEED, 2, 2)
        obj(Items.OPAL_BOLT_TIPS, 5, 2)
        obj(Items.FISHING_BAIT, 10, 2)

        // 1/42.7
        obj(Items.EMPTY_OYSTER, 1, 3)

        // 1/32
        obj(Items.SEAWEED_NOTED, 1, 4)
        obj(Items.SEAWEED_NOTED, 2, 4)

        // 1/21.3
        obj(Items.COINS_995, 8, 6)

        // 1/16
        obj(Items.COINS_995, 8, 8)

        // 1/14.2
        obj(Items.OYSTER, 1, 9)

        // 1/10.7
        obj(Items.OYSTER, 2, 12)

        // 1/4.41
        obj(Items.COINS_995, 4, 29)

        table(Gems.gemTable, 1)

        nothing(40)
    }
    table("Charms") {
        total(1000)
        obj(Items.GOLD_CHARM, quantity = 1, slots = 100)
        obj(Items.GREEN_CHARM, quantity = 1, slots = 5)
        obj(Items.CRIMSON_CHARM, quantity = 1, slots = 10)
        obj(Items.BLUE_CHARM, quantity = 1, slots = 3)
        nothing(slots = 882)
    }
}
table.register(crab, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.ROCK_CRAB_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)

    val spawn = npc.spawnTile
    val id = if (npc.id == Npcs.ROCK_CRAB) Npcs.ROCKS else Npcs.ROCKS_1268
    var npc = Npc(id, spawn, world)
    npc.walkRadius = 0
    npc.static = true
    world.queue {
        wait(25)
        world.spawn(npc)
    }
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 6
            respawnDelay = 0
            attackStyle = StyleType.STAB
        }
        stats {
            hitpoints = 510
            attack = 15
            strength = 15
            defence = 10
        }
        anims {
            attack = Anims.ROCK_CRAB_ATTACK
            block = Anims.ROCK_CRAB_BLOCK
            death = Anims.ROCK_CRAB_DEATH
        }
        aggro {
            radius = 6
            alwaysAggro()
        }
    }
}
