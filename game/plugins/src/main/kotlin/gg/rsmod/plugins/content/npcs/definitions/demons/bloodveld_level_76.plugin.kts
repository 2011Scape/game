package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

val ids = intArrayOf(Npcs.BLOODVELD, Npcs.BLOODVELD_1619, Npcs.BLOODVELD_6215)

val table = DropTableFactory
val bloodveldTable = table.build {
    guaranteed {
        obj(Items.BONES)
    }
    main {
        total(total = 1024)
        //WEAPONS
        obj(Items.STEEL_HATCHET, quantity = 1, slots = 32)
        obj(Items.STEEL_SCIMITAR, quantity = 1, slots = 16)
        //ARMOUR
        obj(Items.STEEL_FULL_HELM, quantity = 1, slots = 32)
        obj(Items.MITHRIL_SQ_SHIELD, quantity = 1, slots = 8)
        obj(Items.MITHRIL_CHAINBODY, quantity = 1, slots = 8)
        obj(Items.BLACK_BOOTS, quantity = 1, slots = 8)
        obj(Items.RUNE_MED_HELM, quantity = 1, slots = 8)
        obj(Items.RUNE_FULL_HELM, quantity = 1, slots = 8)
        //RUNES
        obj(Items.FIRE_RUNE, quantity = 60, slots = 64)
        obj(Items.BLOOD_RUNE, quantity = 3, slots = 40)
        obj(Items.BLOOD_RUNE, quantity = 10, slots = 24)
        obj(Items.BLOOD_RUNE, quantity = 30, slots = 8)
        //SEEDS
        obj(Items.JANGERBERRY_SEED, quantity = 1, slots = 36)
        //COINS
        obj(Items.COINS_995, quantity = 10, slots = 144)
        obj(Items.COINS_995, quantity = 40, slots = 140)
        obj(Items.COINS_995, quantity = 120, slots = 48)
        obj(Items.COINS_995, quantity = 200, slots = 34)
        obj(Items.COINS_995, quantity = 460, slots = 5)
        //OTHER
        obj(Items.GOLD_ORE, quantity = 1, slots = 16)
        obj(Items.BONES, quantity = 2, slots = 80)
        obj(Items.BIG_BONES, quantity = 1, slots = 30)
        obj(Items.BIG_BONES, quantity = 4, slots = 40)
        obj(Items.BIG_BONES, quantity = 5, slots = 50)
        obj(Items.MEAT_PIZZA, quantity = 1, slots = 24)
        obj(Items.GOLD_RING, quantity = 1, slots = 4)
        obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 4)
        //RARE
        table(Herbs.minorHerbTable, slots = 8)
        table(Gems.gemTable, slots = 32)
        table(Rare.rareTable, slots = 53)
        //NOTHING
        nothing(slots = 42)
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