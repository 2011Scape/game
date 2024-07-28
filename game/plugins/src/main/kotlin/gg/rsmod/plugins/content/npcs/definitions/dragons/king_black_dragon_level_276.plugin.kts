package gg.rsmod.plugins.content.npcs.definitions.dragons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Rare

val KBD = Npcs.KING_BLACK_DRAGON

val table = DropTableFactory
val dragon = table.build {
    guaranteed {
        obj(Items.DRAGON_BONES)
        obj(Items.BLACK_DRAGONHIDE)
    }

    main {
        total(256) //running total 251
        // Weapons
        obj(Items.RUNE_LONGSWORD, quantity = 1, slots = 9)
        obj(Items.MITHRIL_BATTLEAXE, quantity = 1, slots = 9)
        obj(Items.MITHRIL_2H_SWORD, quantity = 1, slots = 9)
        obj(Items.ADAMANT_2H_SWORD, quantity = 1, slots = 8)
        obj(Items.ADAMANT_HATCHET, quantity = 1, slots = 8)
        obj(Items.BLACK_HATCHET, quantity = 1, slots = 8)
        obj(Items.RUNE_BATTLEAXE, quantity = 1, slots = 2)
        obj(Items.RUNE_2H_SWORD, quantity = 1, slots = 2)
        // Arrows and Runes
        obj(Items.BLOOD_RUNE, quantity = listOf(15, 50).random(), slots = 9)
        obj(Items.DEATH_RUNE, quantityRange = 7..45, slots = 9)
        obj(Items.AIR_RUNE, quantity = 105, slots = 9)
        obj(Items.FIRE_RUNE, quantity = 105, slots = 9)
        obj(Items.IRON_ARROW, quantity = 690, slots = 9)
        obj(Items.EARTH_RUNE, quantity = 105, slots = 8)
        obj(Items.LAW_RUNE, quantity = listOf(15, 45).random(), slots = 8)
        obj(Items.STEEL_ARROW, quantity = 150, slots = 8)
        obj(Items.RUNITE_BOLTS, quantityRange = 1..12, slots = 8)
        obj(Items.NATURE_RUNE, quantityRange = 20..78, slots = 2)
        obj(Items.RUNE_ARROW, quantity = 45, slots = 2)
        // Armour
        obj(Items.AMULET_OF_STRENGTH, quantity = 1, slots = 9)
        obj(Items.ADAMANT_PLATEBODY, quantity = 1, slots = 9)
        obj(Items.RUNE_PLATEBODY, quantity = 1, slots = 2)
        obj(Items.RUNE_SQ_SHIELD, quantity = 1, slots = 2)
        obj(Items.DRAGON_MED_HELM, quantity = 1, slots = 2)
        // Ores and bars
        obj(Items.COAL_NOTED, quantity = 100, slots = 2)
        obj(Items.IRON_ORE_NOTED, quantity = 100, slots = 8)
        obj(Items.ADAMANT_BAR, quantity = 1, slots = 8)
        obj(Items.SILVER_ORE_NOTED, quantity = 100, slots = 2)
        obj(Items.RUNE_BAR, quantity = 1, slots = 2)
        // Other
        obj(Items.COINS_995, quantityRange = 1..3000, slots = 9)
        obj(Items.YEW_LOGS_NOTED, quantityRange = 100..507, slots = 9)
        obj(Items.RUNITE_LIMBS, quantity = 1, slots = 9)
        obj(Items.SHARK, quantity = 4, slots = 8)
        obj(Items.OYSTER, quantity = 1, slots = 2)
        obj(Items.OYSTER_PEARLS, quantity = 1, slots = 2)
        obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 2)
        // Quests
        obj(Items.BLACK_DRAGON_TAILBONE, quantity = 1, slots = 8)

        table(Gems.gemTable, slots = 1)
        table(Rare.rareTable, slots = 4)

        nothing(5)
    }

    table("Charms") {
        total(1000)
        obj(Items.GOLD_CHARM, quantity = 4, slots = 100)
        obj(Items.GREEN_CHARM, quantity = 4, slots = 50)
        obj(Items.CRIMSON_CHARM, quantity = 4, slots = 790)
        obj(Items.BLUE_CHARM, quantity = 4, slots = 20)
        nothing(slots = 40)
    }

    table("Tertiary") {
        total(10_000)
        obj(Items.CLUE_SCROLL_HARD, slots = 78)
        obj(Items.CLUE_SCROLL_HARD, slots = 156)
        obj(Items.CLUE_SCROLL_ELITE, slots = 20)
        obj(Items.CLUE_SCROLL_ELITE, slots = 40)
        obj(Items.KBD_HEADS, slots = 80)
        obj(Items.DRAGON_PICKAXE, slots = 10) //TODO: Remove dragon pickaxe after adding Chaos Dwarf Battlefield.
        obj(Items.DRACONIC_VISAGE, slots = 2)
        nothing(9614)
    }
}

table.register(dragon, KBD)

on_npc_pre_death(KBD) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.DRAGON_DEATH)
}

on_npc_death(KBD) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(npc = KBD) {
    configs {
        attackSpeed = 4
        attackStyle = StyleType.STAB
        xpMultiplier = 1.075
        respawnDelay = 30
    }
    species {
        NpcSpecies.BASIC_DRAGON
        NpcSpecies.FIERY
    }
    stats {
        hitpoints = 2400
        attack = 240
        strength = 240
        defence = 240
        magic = 240
        ranged = 1
    }
    bonuses {
        defenceStab = 70
        defenceSlash = 90
        defenceCrush = 90
        defenceMagic = 80
        defenceRanged = 70
    }
    anims {
        attack = 25
        block = 26
        death = 28
    }
    aggro {
        radius = 25
    }
    slayer {
        level = 1
        experience = 258.0
        assignment = SlayerAssignment.BLACK_DRAGON
    }
}
