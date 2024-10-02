package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Seeds.allotmentSeedTable

val varrockId = listOf(Npcs.GUARD_5919, Npcs.GUARD_5920)
val faladorSwordId = listOf(Npcs.GUARD, Npcs.GUARD_3228)
val faladorBattleaxeId = listOf(Npcs.GUARD_3230, Npcs.GUARD_3241)
val faladorCrossbowId = listOf(Npcs.GUARD_3229) // no definition yet, first npc ranged should be supported
val edgevilleId =
    listOf(Npcs.GUARD_296, Npcs.GUARD_297, Npcs.GUARD_298, Npcs.GUARD_299, Npcs.GUARD_3407, Npcs.GUARD_3408)
val allIds = (faladorSwordId + varrockId + faladorBattleaxeId + faladorCrossbowId + edgevilleId).toIntArray()

val table = DropTableFactory
val guard =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(128)
            obj(Items.IRON_BOLTS, quantityRange = 2..12, slots = 10)
            obj(Items.STEEL_ARROW, slots = 4)
            obj(Items.BRONZE_ARROW, slots = 3)
            obj(Items.AIR_RUNE, quantity = 6, slots = 2)
            obj(Items.EARTH_RUNE, quantity = 3, slots = 2)
            obj(Items.FIRE_RUNE, quantity = 2, slots = 2)
            obj(Items.BRONZE_ARROW, quantity = 2, slots = 2)
            obj(Items.BLOOD_RUNE, slots = 1)
            obj(Items.CHAOS_RUNE, slots = 1)
            obj(Items.NATURE_RUNE, slots = 1)
            obj(Items.STEEL_ARROW, quantity = 5, slots = 1)
            obj(Items.COINS_995, quantity = 1, slots = 19)
            obj(Items.COINS_995, quantity = 4, slots = 8)
            obj(Items.COINS_995, quantity = 5, slots = 18)
            obj(Items.COINS_995, quantity = 7, slots = 16)
            obj(Items.COINS_995, quantity = 12, slots = 9)
            obj(Items.COINS_995, quantity = 17, slots = 4)
            obj(Items.COINS_995, quantity = 25, slots = 4)
            obj(Items.COINS_995, quantity = 30, slots = 2)
            obj(Items.IRON_DAGGER, slots = 6)
            obj(Items.BODY_TALISMAN, slots = 2)
            obj(Items.LAW_TALISMAN, slots = 1)
            obj(Items.GRAIN, slots = 1)
            obj(Items.IRON_ORE, slots = 1)

            table(allotmentSeedTable, slots = 18)
            nothing(8)
        }

        table("Tertiary") {
            total(128)
            nothing(slots = 127)
            obj(Items.CLUE_SCROLL_MEDIUM, slots = 1)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 70)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 9)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 5)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 914)
        }
    }

table.register(guard, *allIds)

on_npc_pre_death(*allIds) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(*allIds) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

varrockId.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 220
            attack = 19
            strength = 18
            defence = 14
        }
        bonuses {
            attackStab = 4
            attackCrush = 5
            defenceStab = 24
            defenceSlash = 30
            defenceCrush = 25
            defenceMagic = -9
            defenceRanged = 25
        }
        anims {
            attack = 390
            death = 836
            block = 1156
        }
    }
}

edgevilleId.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 220
            attack = 19
            strength = 18
            defence = 14
        }
        bonuses {
            attackStab = 4
            attackCrush = 5
            defenceStab = 18
            defenceSlash = 25
            defenceCrush = 19
            defenceMagic = -4
            defenceRanged = 20
        }
        anims {
            attack = 7218
            block = 7214
            death = 7213
        }
    }
}

faladorSwordId.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 220
            attack = 19
            strength = 18
            defence = 14
        }
        bonuses {
            attackStab = 4
            attackCrush = 5
            defenceStab = 18
            defenceSlash = 25
            defenceCrush = 19
            defenceMagic = -4
            defenceRanged = 20
        }
        anims {
            attack = 390
            death = 836
            block = 1156
        }
    }
}

faladorBattleaxeId.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 220
            attack = 15
            strength = 15
            defence = 16
        }
        bonuses {
            attackStab = 6
            attackCrush = 10
            defenceStab = 5
            defenceSlash = 5
            defenceCrush = 5
            defenceMagic = -4
            defenceRanged = 5
        }
        anims {
            attack = 401
            death = 836
            block = 1156
        }
    }
}

// faladorCrossbowId.forEach {
//    set_combat_def(it) {
//        configs {
//            attackSpeed = 4
//            respawnDelay = 50
//        }
//        stats {
//            hitpoints = 220
//            attack = 15
//            strength = 15
//            defence = 16
//            ranged = 26
//        }
//        bonuses {
//            attackStab = 6
//            attackCrush = 10
//            rangedStrengthBonus = 10
//            defenceStab = 13
//            defenceSlash = 17
//            defenceCrush = 14
//            defenceMagic = -4
//            defenceRanged = 15
//        }
//        anims {
//            attack = 4230
//            death = 836
//            block = 424
//        }
//    }
// }
