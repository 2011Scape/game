package gg.rsmod.plugins.content.npcs.definitions.other.goblin

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs.minorHerbTable

val ids = intArrayOf(Npcs.GOBLIN_4412)

val table = DropTableFactory
val goblin =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(128)
            obj(Items.BRONZE_SPEAR, slots = 9)
            obj(Items.BRONZE_HATCHET, slots = 3)
            obj(Items.BRONZE_SCIMITAR, slots = 1)
            obj(Items.BRONZE_ARROW, quantity = 7, slots = 3)
            obj(Items.MIND_RUNE, quantity = 2, slots = 3)
            obj(Items.EARTH_RUNE, quantity = 4, slots = 3)
            obj(Items.BODY_RUNE, quantity = 2, slots = 3)
            obj(Items.BRONZE_JAVELIN, quantity = 5, slots = 2)
            obj(Items.CHAOS_RUNE, slots = 1)
            obj(Items.NATURE_RUNE, slots = 1)
            obj(Items.COINS_995, quantity = 1, slots = 34)
            obj(Items.COINS_995, quantity = 3, slots = 13)
            obj(Items.COINS_995, quantity = 5, slots = 8)
            obj(Items.COINS_995, quantity = 16, slots = 7)
            obj(Items.COINS_995, quantity = 24, slots = 3)

            obj(Items.HAMMER, slots = 9)
            obj(Items.GOBLIN_BOOK, slots = 2)
            obj(Items.GOBLIN_MAIL, slots = 10)
            obj(Items.GRAPES, slots = 1)
            obj(Items.TIN_ORE, slots = 1)

            table(minorHerbTable, slots = 2)

            nothing(slots = 8)
        }
        table("Charms") {
            total(1024)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 82)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 21)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 9)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 1)
            nothing(slots = 911)
        }
        table("Tertiary") {
            total(1024)
            obj(Items.CLUE_SCROLL_EASY, quantity = 1, slots = 6)
            nothing(1018)
        }
    }

table.register(goblin, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.GOBLIN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 35
        }
        stats {
            hitpoints = 170
            attack = 16
            defence = 10
            strength = 13
        }
        bonuses {
            attackStab = 4
            attackCrush = 8
            attackSlash = 6
        }
        anims {
            attack = 6185
            death = 6182
            block = 6183
        }
        slayer {
            assignment = SlayerAssignment.GOBLIN
            level = 1
            experience = 17.0
        }
    }
}
