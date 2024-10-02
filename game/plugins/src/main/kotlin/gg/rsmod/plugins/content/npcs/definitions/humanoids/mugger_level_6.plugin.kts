package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs.minorHerbTable

val ids = intArrayOf(Npcs.MUGGER, Npcs.MUGGER_7161, Npcs.MUGGER_7162)

val table = DropTableFactory
val mugger =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(1024)

            obj(Items.BRONZE_BOLTS, quantityRange = 2..12, slots = 162)
            obj(Items.MIND_RUNE, quantity = 9, slots = 24)
            obj(Items.WATER_RUNE, quantity = 6, slots = 16)
            obj(Items.EARTH_RUNE, quantity = 5, slots = 16)

            table(minorHerbTable, slots = 104)

            obj(Items.COINS_995, quantity = 10, slots = 104)
            obj(Items.COINS_995, quantity = 5, slots = 96)
            obj(Items.COINS_995, quantity = 15, slots = 24)
            obj(Items.COINS_995, quantity = 25, slots = 8)

            obj(Items.ROPE, slots = 270)
            nothing(104)
            obj(Items.FISHING_BAIT, slots = 48)
            obj(Items.COPPER_ORE, slots = 16)
            obj(Items.BRONZE_MED_HELM, slots = 16)
            obj(Items.KNIFE, slots = 8)
            obj(Items.CABBAGE, slots = 8)
        }

        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
            nothing(127)
        }
    }

table.register(mugger, *ids)

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
            hitpoints = 80
            attack = 5
            strength = 5
            defence = 5
        }

        bonuses {
            strengthBonus = -21
        }

        aggro {
            radius = 4
        }

        anims {
            attack = 422
            block = 424
            death = 836
        }
    }
}
