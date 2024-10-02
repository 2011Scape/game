package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Rare

val ids = intArrayOf(Npcs.TZHAARMEJ, Npcs.TZHAARMEJ_2592, Npcs.TZHAARMEJ_2596, Npcs.TZHAARMEJ_2597)

val table = DropTableFactory
val tzhaarMej =
    table.build {
        main {
            total(128)

            obj(Items.TOKKUL, quantity = world.random(150..990), slots = 32)

            obj(Items.FIRE_BATTLESTAFF, slots = 1)
            obj(Items.STAFF_OF_FIRE, slots = 1)

            obj(Items.EARTH_RUNE, quantity = world.random(10..80), slots = 6)
            obj(Items.FIRE_RUNE, quantity = world.random(10..80), slots = 12)
            obj(Items.AIR_RUNE, quantity = world.random(10..80), slots = 6)
            obj(Items.CHAOS_RUNE, quantity = world.random(5..10), slots = 4)
            obj(Items.NATURE_RUNE, quantity = world.random(1..8), slots = 6)
            obj(Items.LAW_RUNE, quantity = world.random(1..5), slots = 6)
            obj(Items.DEATH_RUNE, quantity = world.random(1..6), slots = 5)
            obj(Items.BLOOD_RUNE, quantity = world.random(1..6), slots = 6)
            obj(Items.SMOKE_RUNE, quantity = world.random(1..10), slots = 4)

            obj(Items.FIRE_TALISMAN, slots = 1)

            obj(Items.UNCUT_SAPPHIRE, slots = 12)
            obj(Items.UNCUT_EMERALD, slots = 12)
            obj(Items.UNCUT_RUBY, slots = 1)
            obj(Items.UNCUT_DIAMOND, slots = 1)
            obj(Items.PURE_ESSENCE_NOTED, quantity = world.random(4..10), slots = 11)

            table(Rare.rareTable, slots = 1)
        }
        table("Obsidian") {
            total(total = 4096)
            obj(Items.OBSIDIAN_CAPE, slots = 1)
            obj(Items.TOKTZMEJTAL, slots = 1)
            nothing(slots = 4094)
        }
        table("Charms") {
            total(1000)
            obj(Items.OBSIDIAN_CHARM, quantity = 1, slots = 360)
            obj(Items.OBSIDIAN_CHARM, quantity = 2, slots = 360)
            nothing(slots = 280)
        }
    }

table.register(tzhaarMej, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.TZHAAR_MEJ_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 5
            respawnDelay = 50
        }
        stats {
            hitpoints = 1000
            attack = 80
            strength = 80
            defence = 80
            magic = 120
        }
        anims {
            attack = 9260
            death = 9291
            block = 9287
        }
    }
}
