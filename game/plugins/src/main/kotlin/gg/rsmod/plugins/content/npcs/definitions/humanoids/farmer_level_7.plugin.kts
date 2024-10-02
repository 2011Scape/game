package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs.minorHerbTable
import gg.rsmod.plugins.content.drops.global.Seeds.allotmentSeedTable

val ids = intArrayOf(Npcs.FARMER, Npcs.FARMER_1757, Npcs.FARMER_1758)

val table = DropTableFactory
val farmer =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(128)
            obj(Items.EARTH_RUNE, quantity = 4, slots = 2)
            obj(Items.FIRE_RUNE, quantity = 6, slots = 2)
            obj(Items.MIND_RUNE, quantity = 9, slots = 2)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 1)
            table(minorHerbTable, slots = 11)
            table(allotmentSeedTable, slots = 27)
            obj(Items.COINS_995, quantity = 3, slots = 38)
            obj(Items.COINS_995, quantity = 25, slots = 1)
            obj(Items.FISHING_BAIT, slots = 5)
            obj(Items.RAKE, slots = 3)
            obj(Items.EARTH_TALISMAN, slots = 2)
            obj(Items.GARDENING_BOOTS, slots = 2)
            obj(Items.SEED_DIBBER, slots = 2)
            obj(Items.SECATEURS, slots = 1)
            obj(Items.WATERING_CAN_8, slots = 1)
            nothing(28)
        }
        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
            nothing(127)
        }
    }

table.register(farmer, *ids)

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
            attackSpeed = 6
            respawnDelay = 25
        }
        stats {
            hitpoints = 120
            attack = 3
            strength = 4
            defence = 8
            magic = 1
            ranged = 1
        }
        bonuses {
            attackStab = 5
            attackCrush = 6
            defenceStab = 0
            defenceSlash = 0
            defenceCrush = 0
            defenceMagic = 0
            defenceRanged = 0
        }
        anims {
            attack = if (it == Npcs.FARMER) 423 else 428
            death = 836
            block = if (it == Npcs.FARMER) 424 else 420
        }
    }
}
