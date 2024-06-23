package gg.rsmod.plugins.content.npcs.definitions.undeads

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare


val ids = intArrayOf(Npcs.JELLY, Npcs.JELLY_1638, Npcs.JELLY_1639,Npcs.JELLY_1640,Npcs.JELLY_1641,Npcs.JELLY_1642)

val table = DropTableFactory
val jelly = DropTableFactory.build {
    guaranteed {
        obj(Items.BONES, quantity = 1)
    }
    main {
        total(1018)
        obj(Items.CHAOS_RUNE, quantity = 15, slots = 52)
        obj(Items.DEATH_RUNE, quantity = 5, slots = 52)
        obj(Items.WATER_RUNE, quantity = 10, slots = 13)
        obj(Items.BLOOD_RUNE, quantity = 7, slots = 13)
        obj(Items.STEEL_BATTLEAXE, quantity = 1, slots = 210)
        obj(Items.STEEL_2H_SWORD, quantity = 1, slots = 280)
        obj(Items.STEEL_HATCHET, quantity = 1, slots = 210)
        obj(Items.MITHRIL_KITESHIELD, quantity = 1, slots = 60)
        obj(Items.MITHRIL_BOOTS, quantity = 1, slots = 13)
        obj(Items.RUNE_FULL_HELM, quantity = 1, slots = 13)
        obj(Items.STEEL_PLATEBODY, quantity = 1, slots = 13)
        obj(Items.RUNE_BATTLEAXE, quantity = 1, slots = 3)
        obj(Items.GOLD_BAR, quantity = 1, slots = 60)
        obj(Items.THREAD, quantity = 10, slots = 13)
        obj(Items.STARVED_ANCIENT_EFFIGY, quantity = 1, slots = 13)
    }
    table("Charms") {
        total(990)
        obj(Items.GOLD_CHARM, quantity = 1, slots = 75) // Adjusted Gold Charm drop rate
        obj(Items.GREEN_CHARM, quantity = 1, slots = 220)
        obj(Items.CRIMSON_CHARM, quantity = 1, slots = 35) // Adjusted Crimson Charm drop rate
        obj(Items.BLUE_CHARM, quantity = 1, slots = 10)
        nothing(slots = 650) // Adjusted nothing slots
    }
}

table.register(jelly, *ids)

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
            hitpoints = 750
            attack = 45
            strength = 45
            defence = 120
            magic = 45
            ranged = 1
        }
        anims {
            attack = 1537
            death = 1538
            block = 2309
        }
        slayer {
            assignment = SlayerAssignment.JELLYS
            level = 52
            experience = 75.0
        }
    }
}