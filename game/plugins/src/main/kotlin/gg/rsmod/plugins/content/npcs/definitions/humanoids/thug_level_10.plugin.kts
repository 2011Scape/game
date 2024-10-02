package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs.minorHerbTable

val ids =
    intArrayOf(
        Npcs.THUG_7106,
        Npcs.THUG_7107,
        Npcs.THUG_7109,
        Npcs.THUG_7110,
        Npcs.THUG_7112,
        Npcs.THUG_7113,
        Npcs.THUG_7114,
    )

val table = DropTableFactory
val thug =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(128)
            obj(Items.IRON_MED_HELM, slots = 4)
            obj(Items.IRON_BATTLEAXE, slots = 2)
            obj(Items.STEEL_HATCHET, slots = 1)
            obj(Items.NATURE_RUNE, quantity = 2, slots = 13)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 4)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 1)
            obj(Items.LAW_RUNE, quantity = 2, slots = 1)
            obj(Items.DEATH_RUNE, quantity = 2, slots = 1)
            obj(Items.IRON_ORE, slots = 4)
            obj(Items.IRON_BAR, slots = 3)
            obj(Items.COAL, slots = 2)
            obj(Items.COINS_995, quantity = 8, slots = 23)
            obj(Items.COINS_995, quantity = 15, slots = 12)
            obj(Items.COINS_995, quantity = 30, slots = 2)
            obj(Items.COINS_995, quantity = 20, slots = 1)

            table(minorHerbTable, slots = 24)
            nothing(slots = 30)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 120)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 10)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 8)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 5)
            nothing(slots = 857)
        }
        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
            nothing(127)
        }
    }

table.register(thug, *ids)

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
            respawnDelay = 50
        }
        stats {
            hitpoints = 180
            attack = 7
            strength = 5
            defence = 9
        }
        bonuses {
            attackStab = 5
            attackCrush = 5
            defenceStab = 2
            defenceSlash = 3
            defenceCrush = 3
        }
        anims {
            attack = 386
            block = 378
            death = 836
        }
    }
}
