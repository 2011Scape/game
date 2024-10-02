package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems.gemTable
import gg.rsmod.plugins.content.drops.global.Herbs.minorHerbTable
import gg.rsmod.plugins.content.drops.global.Seeds.allotmentSeedTable

val ids = intArrayOf(Npcs.BLACK_KNIGHT)

val table = DropTableFactory
val guard =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(128)
            obj(Items.IRON_SWORD, slots = 4)
            obj(Items.IRON_FULL_HELM, slots = 2)
            obj(Items.STEEL_MACE, slots = 1)
            obj(Items.MITHRIL_ARROW, quantity = 3, slots = 4)
            obj(Items.BODY_RUNE, quantity = 9, slots = 3)
            obj(Items.CHAOS_RUNE, quantity = 6, slots = 3)
            obj(Items.EARTH_RUNE, quantity = 10, slots = 3)
            obj(Items.DEATH_RUNE, quantity = 2, slots = 2)
            obj(Items.LAW_RUNE, quantity = 3, slots = 2)
            obj(Items.COSMIC_RUNE, quantity = 7, slots = 1)
            obj(Items.MIND_RUNE, quantity = 2, slots = 1)
            obj(Items.STEEL_BAR, slots = 6)
            obj(Items.TIN_ORE, slots = 1)
            obj(Items.POT_OF_FLOUR, slots = 1)
            obj(Items.COINS_995, quantity = 35, slots = 21)
            obj(Items.COINS_995, quantity = 6, slots = 11)
            obj(Items.COINS_995, quantity = 58, slots = 10)
            obj(Items.COINS_995, quantity = 12, slots = 9)
            obj(Items.COINS_995, quantity = 80, slots = 2)
            obj(Items.COINS_995, quantity = 1, slots = 1)
            obj(Items.COINS_995, quantity = 13, slots = 1)
            obj(Items.BREAD, slots = 1)

            table(minorHerbTable, slots = 3)
            table(allotmentSeedTable, slots = 18)
            table(gemTable, slots = 3)
            nothing(2)
        }

        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 30)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 10)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 8)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 8)
            nothing(slots = 944)
        }
    }

table.register(guard, *ids)

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
            hitpoints = 420
            attack = 25
            strength = 25
            defence = 25
        }
        bonuses {
            attackStab = 18
            attackCrush = 16
            defenceStab = 73
            defenceSlash = 76
            defenceCrush = 70
            defenceMagic = -11
            defenceRanged = 72
        }
        anims {
            attack = 390
            death = 836
            block = 1156
        }
    }
}
