package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.HOBGOBLIN, Npcs.HOBGOBLIN_2686, Npcs.HOBGOBLIN_2687)

val table = DropTableFactory
val unarmedGoblin =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(128)
            obj(Items.BRONZE_SPEAR, slots = 3)
            obj(Items.IRON_SWORD, slots = 3)
            obj(Items.STEEL_DAGGER, slots = 3)
            obj(Items.IRON_SPEAR, slots = 2)
            obj(Items.STEEL_SPEAR, slots = 2)
            obj(Items.STEEL_LONGSWORD, slots = 1)
            obj(Items.LAW_RUNE, quantity = 2, slots = 3)
            obj(Items.WATER_RUNE, quantity = 2, slots = 2)
            obj(Items.FIRE_RUNE, quantity = 7, slots = 2)
            obj(Items.BODY_RUNE, quantity = 6, slots = 2)
            obj(Items.CHAOS_RUNE, quantity = 3, slots = 2)
            obj(Items.NATURE_RUNE, quantity = 4, slots = 2)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 1)
            obj(Items.COINS_995, quantity = 15, slots = 16)
            obj(Items.COINS_995, quantity = 28, slots = 12)
            obj(Items.COINS_995, quantity = 5, slots = 11)
            obj(Items.COINS_995, quantity = 62, slots = 4)
            obj(Items.COINS_995, quantity = 42, slots = 3)
            obj(Items.COINS_995, quantity = 1, slots = 1)
            obj(Items.LIMPWURT_ROOT, slots = 22)
            obj(Items.GOBLIN_MAIL, slots = 2)

            table(Herbs.minorHerbTable, slots = 7)
            table(Seeds.allotmentSeedTable, slots = 18)
            table(Gems.gemTable, slots = 2)

            nothing(1)
        }
        table("Charms") {
            total(1024)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 160)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 6)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 11)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 821)
        }
    }

table.register(unarmedGoblin, *ids)

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
            respawnDelay = 50
        }
        stats {
            hitpoints = 290
            attack = 22
            strength = 24
            defence = 24
        }
        anims {
            attack = 12405
            death = 4784
            block = 12406
        }
    }
}
