package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Seeds.allotmentSeedTable

val level12 = Npcs.HAM_GUARD
val level18 = Npcs.HAM_GUARD_1711
val level22 = Npcs.HAM_GUARD_1712

val ids = level12 + level18 + level22

val table = DropTableFactory
val hamGuard =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(660)
            obj(Items.BRONZE_HATCHET, slots = 18)
            obj(Items.BRONZE_DAGGER, slots = 18)
            obj(Items.BRONZE_PICKAXE, slots = 18)
            obj(Items.IRON_HATCHET, slots = 18)
            obj(Items.IRON_DAGGER, slots = 18)
            obj(Items.IRON_PICKAXE, slots = 18)
            obj(Items.LEATHER_BODY, slots = 18)
            obj(Items.STEEL_HATCHET, slots = 12)
            obj(Items.STEEL_DAGGER, slots = 12)
            obj(Items.STEEL_PICKAXE, slots = 12)
            obj(Items.HAM_BOOTS, slots = 12)
            obj(Items.HAM_CLOAK, slots = 6)
            obj(Items.HAM_GLOVES, slots = 15)
            obj(Items.HAM_HOOD, slots = 6)
            obj(Items.HAM_LOGO, slots = 8)
            obj(Items.HAM_ROBE, slots = 8)
            obj(Items.HAM_SHIRT, slots = 8)
            obj(Items.BRONZE_ARROW, quantityRange = 1..12, slots = 16)
            obj(Items.STEEL_ARROW, quantityRange = 1..10, slots = 12)
            obj(Items.GRIMY_GUAM, slots = 6)
            obj(Items.GRIMY_MARRENTILL, slots = 3)
            obj(Items.GRIMY_TARROMIN, slots = 2)
            table(allotmentSeedTable, slots = 57)
            obj(Items.COWHIDE, quantityRange = 1..3, slots = 18)
            obj(Items.LOGS, quantityRange = 1..3, slots = 18)
            obj(Items.RAW_ANCHOVIES, quantityRange = 1..3, slots = 12)
            obj(Items.RAW_CHICKEN, quantityRange = 1..3, slots = 12)
            obj(Items.UNCUT_OPAL, slots = 12)
            obj(Items.UNCUT_JADE, slots = 12)
            obj(Items.IRON_ORE, slots = 12)
            obj(Items.COAL, slots = 12)
            obj(Items.COINS_995, quantityRange = 1..20, slots = 81)
            obj(Items.BUTTONS, slots = 24)
            obj(Items.DAMAGED_ARMOUR, slots = 24)
            obj(Items.RUSTY_SWORD, slots = 24)
            obj(Items.KNIFE, slots = 12)
            obj(Items.NEEDLE, slots = 12)
            obj(Items.TINDERBOX_590, slots = 12)
            obj(Items.FEATHER, quantityRange = 1..6, slots = 18)
            obj(Items.THREAD, quantityRange = 1..10, slots = 12)
        }
        table("Tertiary") {
            total(55)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
            nothing(54)
        }
    }

table.register(hamGuard, level12, level18, level22)

on_npc_pre_death(ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(level12, level18, level22) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

set_combat_def(level12) {
    configs {
        attackSpeed = 7
        respawnDelay = 25
    }
    stats {
        hitpoints = 150
        attack = 8
        strength = 12
        defence = 8
        magic = 1
        ranged = 1
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 5
        defenceSlash = 5
        defenceCrush = 0
        defenceMagic = 0
        defenceRanged = 5
    }
    anims {
        attack = 390
        block = 1156
        death = 836
    }
}

set_combat_def(level18) {
    configs {
        attackSpeed = 7
        respawnDelay = 25
    }
    stats {
        hitpoints = 200
        attack = 14
        strength = 18
        defence = 12
        magic = 1
        ranged = 1
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 6
        defenceSlash = 6
        defenceCrush = 0
        defenceMagic = 0
        defenceRanged = 6
    }
    anims {
        attack = 390
        block = 1156
        death = 836
    }
}

set_combat_def(level22) {
    configs {
        attackSpeed = 7
        respawnDelay = 25
    }
    stats {
        hitpoints = 300
        attack = 16
        strength = 20
        defence = 12
        magic = 1
        ranged = 1
    }
    bonuses {
        attackStab = 0
        attackCrush = 0
        defenceStab = 7
        defenceSlash = 7
        defenceCrush = 0
        defenceMagic = 0
        defenceRanged = 7
    }
    anims {
        attack = 390
        block = 1156
        death = 836
    }
}
