package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

val ids = intArrayOf(Npcs.BLOODVELD, Npcs.BLOODVELD_1619, Npcs.BLOODVELD_6215)

val table = DropTableFactory
val bloodveldTable =
    table.build {
        guaranteed {
            obj(Items.BONES)
        }
        main {
            total(124)
            // Weapons and armour
            obj(Items.STEEL_HATCHET, quantity = 1, slots = 8)
            obj(Items.STEEL_SCIMITAR, quantity = 1, slots = 4)
            obj(Items.RUNE_JAVELIN, quantity = 5, slots = 1)
            obj(Items.STEEL_FULL_HELM, quantity = 1, slots = 8)
            obj(Items.MITHRIL_SQ_SHIELD, quantity = 1, slots = 4)
            obj(Items.MITHRIL_CHAINBODY, quantity = 1, slots = 4)
            obj(Items.BLACK_BOOTS, quantity = 1, slots = 4)
            obj(Items.RUNE_MED_HELM, quantity = 1, slots = 4)
            obj(Items.RUNE_FULL_HELM, quantity = 1, slots = 1)
            // Runes
            obj(Items.FIRE_RUNE, quantity = 60, slots = 8)
            obj(Items.BLOOD_RUNE, quantity = 3, slots = 8)
            obj(Items.BLOOD_RUNE, quantity = 10, slots = 8)
            obj(Items.BLOOD_RUNE, quantity = 30, slots = 4)
            // Coins
            obj(Items.COINS_995, quantity = 10, slots = 7)
            obj(Items.COINS_995, quantity = 40, slots = 6)
            obj(Items.COINS_995, quantity = 120, slots = 5)
            obj(Items.COINS_995, quantity = 200, slots = 4)
            obj(Items.COINS_995, quantity = 460, slots = 3)
            // Other
            obj(Items.GOLD_ORE, quantity = 1, slots = 4)
            obj(Items.BONES_NOTED, quantityRange = 1..3, slots = 4)
            obj(Items.BIG_BONES_NOTED, quantity = 1, slots = 4)
            obj(Items.BIG_BONES_NOTED, quantityRange = 4..5, slots = 4)
            obj(Items.GOLD_RING, quantity = 1, slots = 1)
            obj(Items.MEAT_PIZZA, quantity = 1, slots = 1)
            obj(Items.JANGERBERRY_SEED, quantity = 1, slots = 4)
            // Tables
            table(Herbs.minorHerbTable, slots = 1)
            table(Gems.gemTable, slots = 8)
            table(Rare.rareTable, slots = 2)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 90)
            obj(Items.GREEN_CHARM, quantity = 1, slots = 31)
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 50)
            obj(Items.BLUE_CHARM, quantity = 1, slots = 10)
            nothing(slots = 819)
        }
    }

table.register(bloodveldTable, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.BLOODVELD_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 21
            attackStyle = StyleType.MAGIC_MELEE
        }
        stats {
            hitpoints = 1200
            attack = 75
            strength = 45
            defence = 30
            magic = 1
            ranged = 1
        }
        bonuses {
        }
        anims {
            attack = 9434
            death = 9430
            block = 9431
        }
        slayer {
            assignment = SlayerAssignment.BLOODVELD
            level = 50
            experience = 120.0
        }
    }
}
