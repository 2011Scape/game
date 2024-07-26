package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

val ids = intArrayOf(Npcs.ABYSSAL_DEMON, Npcs.ABYSSAL_DEMON_4230, Npcs.ABYSSAL_DEMON_9086)

val table = DropTableFactory
val abyssalDemon = table.build {
    guaranteed {
        obj(Items.INFERNAL_ASHES)
    }
    main {
        total(1024)
        //WEAPONS
        obj(Items.BLACK_SWORD, quantity = 1, slots = 32)
        obj(Items.BLACK_HATCHET, quantity = 1, slots = 16)
        obj(Items.STEEL_BATTLEAXE, quantity = 1, slots = 24)
        obj(Items.RUNE_BATTLEAXE, quantity = 1, slots = 8)
        obj(Items.RUNE_2H_SWORD, quantity = 1, slots = 8)
        obj(Items.ABYSSAL_WHIP, quantity = 1, slots = 2)
        //ARMOUR
        obj(Items.MITHRIL_KITESHIELD, quantity = 1, slots = 16)
        obj(Items.RUNE_CHAINBODY, quantity = 1, slots = 8)
        obj(Items.RUNE_MED_HELM, quantity = 1, slots = 8)
        obj(Items.RUNE_SQ_SHIELD, quantity = 1, slots = 8)
        obj(Items.DRAGON_MED_HELM, quantity = 1, slots = 16)
        //RUNES
        obj(Items.AIR_RUNE, quantity = 50, slots = 64)
        obj(Items.BLOOD_RUNE, quantity = listOf(7, 50).random(), slots = 32)
        obj(Items.CHAOS_RUNE, quantity = 10, slots = 56)
        obj(Items.LAW_RUNE, quantity = listOf(3, 45).random(), slots = 32)
        obj(Items.DEATH_RUNE, quantity = 45, slots = 8)
        obj(Items.NATURE_RUNE, quantity = (61..77).random(), slots = 8)
        //OTHER
        obj(Items.EARTH_TALISMAN, quantity = 1, slots = 41)
        obj(Items.COSMIC_TALISMAN, quantity = 1, slots = 41)
        obj(Items.COINS_995, quantity = listOf(9, 10, 486).random(), slots = 41)
        obj(Items.LOBSTER, quantity = 1, slots = 41)
        obj(Items.PURE_ESSENCE_NOTED, quantity = 60, slots = 41)
        obj(Items.ADAMANT_BAR, quantity = 1, slots = 32)
        obj(Items.FEROCIOUS_RING_5, quantity = 1, slots = 32)
        obj(Items.DEFENCE_POTION_3, quantity = 1, slots = 32)
        obj(Items.RUNE_BAR, quantity = 1, slots = 2)
        obj(Items.DRAGONSTONE, quantity = 1, slots = 2)
        obj(Items.COURT_SUMMONS, quantity = 1, slots = 8)
        obj(Items.CLUE_SCROLL_HARD, quantity = 1, slots = 8)
        obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 8)
        obj(Items.ABYSSAL_HEAD, quantity = 1, slots = 2)
        //TABLES
        table(Herbs.minorHerbTable, slots = 152)
        table(Rare.rareTable, slots = 16)
        //NOTHING
        nothing(179)
    }
}

table.register(abyssalDemon, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}
ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 3
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 1500
            attack = 97
            strength = 67
            defence = 135
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceStab = 20
            defenceSlash = 20
            defenceCrush = 20
            defenceMagic = 0
            defenceRanged = 20
        }
        anims {
            attack = 1537
            death = 1538
            block = 2309
        }
        slayer {
            assignment = SlayerAssignment.ABYSSAL_DEMON
            level = 85
            experience = 150.0
        }
    }
}