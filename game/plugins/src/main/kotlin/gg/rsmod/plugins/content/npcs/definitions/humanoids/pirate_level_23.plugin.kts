package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems

val ids = intArrayOf(Npcs.PIRATE, Npcs.PIRATE_183, Npcs.PIRATE_184)

val table = DropTableFactory
val pirate =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(1024)
            obj(Items.IRON_DAGGER, slots = 48)
            obj(Items.BRONZE_SCIMITAR, slots = 32)
            obj(Items.IRON_PLATEBODY, slots = 8)

            obj(Items.IRON_BOLTS, quantityRange = 2..12, slots = 80)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 48)
            obj(Items.NATURE_RUNE, quantity = 2, slots = 40)
            obj(Items.BRONZE_ARROW, quantity = 9, slots = 24)
            obj(Items.BRONZE_ARROW, quantity = 12, slots = 16)
            obj(Items.AIR_RUNE, quantity = 10, slots = 16)
            obj(Items.EARTH_RUNE, quantity = 9, slots = 16)
            obj(Items.FIRE_RUNE, quantity = 5, slots = 16)
            obj(Items.LAW_RUNE, quantity = 2, slots = 8)

            obj(Items.COINS_995, quantity = 4, slots = 232)
            obj(Items.COINS_995, quantity = 25, slots = 104)
            obj(Items.COINS_995, quantity = 7, slots = 64)
            obj(Items.COINS_995, quantity = 12, slots = 48)
            obj(Items.COINS_995, quantity = 35, slots = 32)
            obj(Items.COINS_995, quantity = 55, slots = 8)

            obj(Items.EYE_PATCH, slots = 96)
            nothing(64)
            obj(Items.CHEFS_HAT, slots = 8)
            obj(Items.IRON_BAR, slots = 8)

            table(Gems.gemTable, slots = 8)
        }
    }

table.register(pirate, *ids)

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
            attackSpeed = 5
            respawnDelay = 25
        }
        stats {
            hitpoints = 200
            attack = 21
            strength = 21
            defence = 21
        }
        bonuses {
            attackBonus = 8
            strengthBonus = 10
            defenceStab = 3
            defenceSlash = 2
        }
        anims {
            attack = 428
            block = 424
            death = 836
        }
    }
}
