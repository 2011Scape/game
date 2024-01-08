package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs
import gg.rsmod.plugins.content.drops.global.Rare

val ids = intArrayOf(Npcs.GREATER_DEMON, Npcs.GREATER_DEMON_4698, Npcs.GREATER_DEMON_4699, Npcs.GREATER_DEMON_4700, Npcs.GREATER_DEMON_4701)

val table = DropTableFactory
val greaterdemon = table.build {
    guaranteed {
        obj(Items.INFERNAL_ASHES)
    }
    main {
        total(182) // Total number of drops available

        obj(Items.STEEL_2H_SWORD, quantity = 1, slots = 4)
        obj(Items.STEEL_HATCHET, quantity = 1, slots = 3)
        obj(Items.STEEL_BATTLEAXE, quantity = 1, slots = 3)
        obj(Items.MITHRIL_KITESHIELD, quantity = 1, slots = 1)
        obj(Items.ADAMANT_PLATELEGS, quantity = 1, slots = 1)
        obj(Items.RUNE_FULL_HELM, quantity = 1, slots = 1)

        obj(Items.MITHRIL_KITESHIELD, quantity = 1, slots = 16)
        obj(Items.RUNE_CHAINBODY, quantity = 1, slots = 8)
        obj(Items.RUNE_MED_HELM, quantity = 1, slots = 8)
        obj(Items.RUNE_SQ_SHIELD, quantity = 1, slots = 8)
        obj(Items.DRAGON_MED_HELM, quantity = 1, slots = 16)

        obj(Items.FIRE_RUNE, quantity = 75, slots = 8)
        obj(Items.CHAOS_RUNE, quantity = 15, slots = 3)
        obj(Items.DEATH_RUNE, quantity = 5, slots = 3)
        obj(Items.FIRE_RUNE, quantity = 37, slots = 1)

        obj(Items.COINS_995, quantity = 132, slots = 40)
        obj(Items.COINS_995, quantity = 44, slots = 29)
        obj(Items.COINS_995, quantity = 220, slots = 10)
        obj(Items.COINS_995, quantity = 11, slots = 7)
        obj(Items.COINS_995, quantity = 460, slots = 1)

        obj(Items.TUNA, quantity = 1, slots = 3)
        obj(Items.GOLD_BAR, quantity = 1, slots = 2)
        obj(Items.THREAD, quantity = 10, slots = 1)

        table(Gems.gemTable, slots = 5)
    }
    table("Charms") {
        total(1000)
        obj(Items.GOLD_CHARM, quantity = 1, slots = 20)
        obj(Items.GREEN_CHARM, quantity = 1, slots = 8)
        obj(Items.CRIMSON_CHARM, quantity = 1, slots = 30)
        obj(Items.BLUE_CHARM, quantity = 1, slots = 7)
        nothing(slots = 935)
    }
}

table.register(greaterdemon, *ids)


on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}
ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 15
            attackStyle = StyleType.SLASH
        }
        stats {
            hitpoints = 870
            attack = 76
            strength = 78
            defence = 81
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceMagic = -10
        }
        anims {
            attack = 64
            death = 67
            block = 65
        }
        aggro {
            radius = 4
        }
        slayer {
            assignment = SlayerAssignment.GREATER_DEMON
            level = 1
            experience = 79.0
        }
    }
}