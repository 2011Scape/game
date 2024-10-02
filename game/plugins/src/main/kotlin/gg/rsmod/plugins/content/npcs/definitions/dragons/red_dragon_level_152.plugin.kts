package gg.rsmod.plugins.content.npcs.definitions.dragons

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val ids =
    intArrayOf(Npcs.RED_DRAGON, Npcs.RED_DRAGON_4669, Npcs.RED_DRAGON_4670, Npcs.RED_DRAGON_4671, Npcs.RED_DRAGON_4672)

val table = DropTableFactory
val dragon =
    table.build {
        guaranteed {
            obj(Items.DRAGON_BONES)
            obj(Items.RED_DRAGONHIDE)
        }

        main {
            total(1024)

            obj(Items.MITHRIL_2H_SWORD, slots = 32)
            obj(Items.MITHRIL_HATCHET, slots = 24)
            obj(Items.MITHRIL_BATTLEAXE, slots = 24)
            obj(Items.RUNE_DART, quantity = 8, slots = 24)
            obj(Items.MITHRIL_JAVELIN, quantity = 20, slots = 8)
            obj(Items.MITHRIL_KITESHIELD, slots = 8)
            obj(Items.ADAMANT_PLATEBODY, slots = 8)
            obj(Items.RUNE_LONGSWORD, slots = 8)

            obj(Items.RUNE_ARROW, quantity = 4, slots = 64)
            obj(Items.LAW_RUNE, quantity = 4, slots = 40)
            obj(Items.BLOOD_RUNE, quantity = 2, slots = 32)
            obj(Items.DEATH_RUNE, quantity = 5, slots = 24)

            table(Herbs.minorHerbTable, slots = 16)

            obj(Items.COINS_995, quantity = 196, slots = 320)
            obj(Items.COINS_995, quantity = 66, slots = 232)
            obj(Items.COINS_995, quantity = 330, slots = 80)
            obj(Items.COINS_995, quantity = 690, slots = 8)

            obj(Items.CHOCOLATE_CAKE, quantity = 3, slots = 24)
            obj(Items.ADAMANT_BAR, slots = 8)

            table(Gems.gemTable, slots = 40)
        }

        table("Tertiary") {
            total(1024)
            obj(Items.CLUE_SCROLL_HARD, slots = 8)
            obj(Items.CLUE_SCROLL_HARD, slots = 16)
            nothing(1000)
        }
    }

table.register(dragon, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.DRAGON_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
        }
        species {
            NpcSpecies.BASIC_DRAGON
            NpcSpecies.FIERY
        }
        stats {
            hitpoints = 1400
            attack = 130
            strength = 130
            defence = 130
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 50
            defenceSlash = 70
            defenceCrush = 70
            defenceMagic = 60
            defenceRanged = 50
        }
        anims {
            attack = 12252
            block = 12251
            death = 12250
        }
        aggro {
            radius = 4
        }
    }
}
