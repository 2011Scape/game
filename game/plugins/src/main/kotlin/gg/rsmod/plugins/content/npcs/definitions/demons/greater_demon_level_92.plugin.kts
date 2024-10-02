package gg.rsmod.plugins.content.npcs.definitions.demons

import gg.rsmod.game.model.combat.SlayerAssignment
import gg.rsmod.game.model.combat.StyleType
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems


val ids =
    intArrayOf(
        Npcs.GREATER_DEMON,
        Npcs.GREATER_DEMON_4698,
        Npcs.GREATER_DEMON_4699,
        Npcs.GREATER_DEMON_4700,
        Npcs.GREATER_DEMON_4701,
    )

val table = DropTableFactory
val greaterdemon =
    table.build {
        guaranteed {
            obj(Items.ACCURSED_ASHES)
        }
        /** Running total: 246 **/
        main {
            total(246)
            // RUNES
            obj(Items.FIRE_RUNE, quantity = (37..75).random(), slots = 16)
            obj(Items.CHAOS_RUNE, quantity = 15, slots = 6)
            obj(Items.DEATH_RUNE, quantity = 5, slots = 6)
            // WEAPONS AND ARMOUR
            obj(Items.STEEL_2H_SWORD, quantity = 1, slots = 8)
            obj(Items.STEEL_HATCHET, quantity = 1, slots = 6)
            obj(Items.STEEL_BATTLEAXE, quantity = 1, slots = 6)
            obj(Items.MITHRIL_KITESHIELD, quantity = 1, slots = 2)
            obj(Items.ADAMANT_PLATELEGS, quantity = 1, slots = 2)
            obj(Items.RUNE_FULL_HELM, quantity = 1, slots = 2)
            // COINS
            obj(Items.COINS_995, quantity = 132, slots = 80)
            obj(Items.COINS_995, quantity = 44, slots = 58)
            obj(Items.COINS_995, quantity = 220, slots = 20)
            obj(Items.COINS_995, quantity = 11, slots = 14)
            obj(Items.COINS_995, quantity = 460, slots = 2)
            // OTHER
            obj(Items.TUNA, quantity = 1, slots = 6)
            obj(Items.GOLD_BAR, quantity = 1, slots = 4)
            obj(Items.THREAD, quantity = 10, slots = 4)
            // TABLES
            table(Gems.gemTable, slots = 10)
        }
        table("Charms") {
            total(1000)
            obj(Items.GOLD_CHARM, quantity = 1, slots = 70) // 7% drop rate
            obj(Items.GREEN_CHARM, quantity = 1, slots = 40) // 4% drop rate
            obj(Items.CRIMSON_CHARM, quantity = 1, slots = 260) // 26% drop rate
            obj(Items.BLUE_CHARM, quantity = 1, slots = 10) // 1% drop rate (estimation)
            nothing(slots = 620) // 62% drop rate for nothing
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
            experience = 87.0
        }
    }
}
