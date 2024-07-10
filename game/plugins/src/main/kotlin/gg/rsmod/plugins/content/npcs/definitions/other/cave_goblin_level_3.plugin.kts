package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory

val ids = intArrayOf(Npcs.CAVE_GOBLIN, Npcs.CAVE_GOBLIN_1823, Npcs.CAVE_GOBLIN_1824, Npcs.CAVE_GOBLIN_1825)

val table = DropTableFactory
val goblin =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(50)
            obj(Items.BODY_RUNE, quantity = 7, slots = 5)
            obj(Items.WATER_RUNE, quantity = 6, slots = 5)
            obj(Items.EARTH_RUNE, quantity = 4, slots = 5)
            obj(Items.COINS_995, quantity = 15, slots = 6)
            obj(Items.COINS_995, quantity = 20, slots = 5)
            obj(Items.COINS_995, quantity = 5, slots = 4)
            obj(Items.COINS_995, quantity = 9, slots = 4)
            obj(Items.COINS_995, quantity = 1, slots = 1)
            obj(Items.HAMMER, slots = 4)
            obj(Items.TINDERBOX_590, slots = 4)
            obj(Items.BRASS_NECKLACE, slots = 1)

            nothing(slots = 16)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 60)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 30)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 15)
            nothing(slots = 865)
        }
        table("Tertiary") {
            total(128)
            nothing(slots = 127)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
        }
    }

table.register(goblin, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.CAVE_GOBLIN_DEATH)
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
            hitpoints = 100
        }
        anims {
            attack = 6001
            death = 6003
            block = 6002
        }
        slayer {
            assignment = SlayerAssignment.GOBLIN
            level = 1
            experience = 10.0
        }
    }
}
