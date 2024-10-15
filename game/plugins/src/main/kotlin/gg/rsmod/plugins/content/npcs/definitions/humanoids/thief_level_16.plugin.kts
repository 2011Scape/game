package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs.minorHerbTable

val ids = intArrayOf(Npcs.THIEF_5926, Npcs.THIEF_5927, Npcs.THIEF_5928, Npcs.THIEF_5929)

val table = DropTableFactory
val thief =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(128)
            obj(Items.BRONZE_MED_HELM, slots = 2)
            obj(Items.IRON_DAGGER, slots = 1)
            obj(Items.BRONZE_BOLTS, quantityRange = 2..12, slots = 22)
            obj(Items.BRONZE_ARROW, quantity = 7, slots = 3)
            obj(Items.EARTH_RUNE, quantity = 4, slots = 2)
            obj(Items.FIRE_RUNE, quantity = 6, slots = 2)
            obj(Items.MIND_RUNE, quantity = 9, slots = 2)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 1)
            obj(Items.COINS_995, quantity = 3, slots = 38)
            obj(Items.COINS_995, quantity = 5, slots = 9)
            obj(Items.COINS_995, quantity = 15, slots = 4)
            obj(Items.COINS_995, quantity = 25, slots = 1)
            obj(Items.FISHING_BAIT, slots = 5)
            obj(Items.COPPER_ORE, slots = 2)
            obj(Items.EARTH_TALISMAN, slots = 2)
            obj(Items.CABBAGE, slots = 1)

            table(minorHerbTable, slots = 23)
            nothing(slots = 8)
        }
        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
            nothing(127)
        }
    }

table.register(thief, *ids)

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
            respawnDelay = 25
        }
        stats {
            hitpoints = 170
            attack = 14
            strength = 13
            defence = 12
        }
        anims {
            attack = 422
            block = 424
            death = 836
        }
    }
}
