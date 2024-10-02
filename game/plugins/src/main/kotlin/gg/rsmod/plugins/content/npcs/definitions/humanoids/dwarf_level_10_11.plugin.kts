package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems

val idsLevel10 = intArrayOf(Npcs.DWARF_118, Npcs.DWARF_3221, Npcs.DWARF_3272)
val idsLevel11 = intArrayOf(Npcs.DWARF_3219, Npcs.DWARF_3220, Npcs.DWARF_3268, Npcs.DWARF_3269, Npcs.DWARF_3270)
val ids = idsLevel10 + idsLevel11

val table = DropTableFactory
val dwarf =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }

        main {
            total(128)
            obj(Items.BRONZE_PICKAXE, slots = 13)
            obj(Items.BRONZE_MED_HELM, slots = 4)
            obj(Items.BRONZE_BATTLEAXE, slots = 2)
            obj(Items.IRON_BATTLEAXE, slots = 1)
            obj(Items.BRONZE_BOLTS, quantityRange = 2..12, slots = 7)
            obj(Items.CHAOS_RUNE, quantity = 2, slots = 4)
            obj(Items.NATURE_RUNE, quantity = 2, slots = 4)
            obj(Items.COINS_995, quantity = 4, slots = 20)
            obj(Items.COINS_995, quantity = 10, slots = 15)
            obj(Items.COINS_995, quantity = 30, slots = 2)
            obj(Items.HAMMER, slots = 10)
            obj(Items.BRONZE_BAR, slots = 7)
            obj(Items.IRON_ORE, slots = 4)
            obj(Items.TIN_ORE, slots = 3)
            obj(Items.COPPER_ORE, slots = 3)
            obj(Items.IRON_BAR, slots = 3)
            obj(Items.COAL, slots = 2)

            table(Gems.gemTable, slots = 1)
            nothing(23)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 40)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 20)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 7)
            nothing(slots = 903)
        }
        table("Tertiary") {
            total(128)
            obj(Items.CLUE_SCROLL_EASY, slots = 1)
            nothing(127)
        }
    }

table.register(dwarf, *ids)

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.DWARF_DEATH)
}

idsLevel10.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 5
            respawnDelay = 50
            deathDelay = 1
        }
        stats {
            hitpoints = 160
            attack = 8
            strength = 8
            defence = 6
        }
        bonuses {
            attackStab = 5
            attackCrush = 7
            defenceMagic = 5
        }
        anims {
            attack = 99
            death = 102
            block = 100
        }
        slayer {
            assignment = SlayerAssignment.DWARF
            level = 1
            experience = 16.0
        }
    }
}

idsLevel11.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 50
        }
        stats {
            hitpoints = 180
            attack = 8
            strength = 8
            defence = 8
        }
        bonuses {
            attackStab = 5
            attackCrush = 7
            defenceMagic = 5
            defenceRanged = 10
        }
        anims {
            attack = 99
            death = 102
            block = 100
        }
        slayer {
            assignment = SlayerAssignment.DWARF
            level = 1
            experience = 16.0
        }
    }
}
