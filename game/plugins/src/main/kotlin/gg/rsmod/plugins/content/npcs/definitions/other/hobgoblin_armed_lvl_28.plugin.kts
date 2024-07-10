package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Seeds

val ids = intArrayOf(Npcs.HOBGOBLIN_2688, Npcs.HOBGOBLIN_4898)
val table = DropTableFactory
val hobgoblin =
    table.build {

        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(total = 128)

            obj(Items.IRON_SWORD, quantity = 1, slots = 3)
            obj(Items.STEEL_DAGGER, quantity = 1, slots = 3)
            obj(Items.STEEL_LONGSWORD, quantity = 1, slots = 1)

            obj(Items.LAW_RUNE, quantity = 2, slots = 3)
            obj(Items.WATER_RUNE, quantity = 2, slots = 2)
            obj(Items.FIRE_RUNE, quantity = 7, slots = 2)
            obj(Items.BODY_RUNE, quantity = 6, slots = 2)
            obj(Items.CHAOS_RUNE, quantity = 3, slots = 2)
            obj(Items.NATURE_RUNE, quantity = 4, slots = 2)
            obj(Items.COSMIC_RUNE, quantity = 2, slots = 2)
            obj(Items.IRON_JAVELIN, quantity = 5, slots = 2)

            table(Herbs.minorHerbTable, slots = 7)
            table(Seeds.uncommonSeedTable, slots = 12)

            obj(Items.COINS_995, quantity = 15, slots = 34)
            obj(Items.COINS_995, quantity = 5, slots = 10)
            obj(Items.COINS_995, quantity = 28, slots = 4)
            obj(Items.COINS_995, quantity = 62, slots = 4)
            obj(Items.COINS_995, quantity = 42, slots = 3)
            obj(Items.COINS_995, quantity = 1, slots = 1)

            nothing(slots = 4)
            obj(Items.LIMPWURT_ROOT, quantity = 1, slots = 21)
            obj(Items.GOBLIN_MAIL, quantity = 1, slots = 2)

            table(Gems.gemTable, slots = 2)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 150)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 10)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 10)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 2)
            nothing(slots = 828)
        }
    }

table.register(hobgoblin, *ids)

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
            attackSpeed = 6
            respawnDelay = 30
        }
        stats {
            hitpoints = 490
            attack = 33
            strength = 31
            defence = 36
        }
        bonuses {
            attackStab = 8
            attackCrush = 10
            defenceStab = 1
            defenceSlash = 1
        }
        anims {
            attack = 12408
            death = 4784
            block = 12406
        }
        aggro {
            radius = 4
        }
    }
}
