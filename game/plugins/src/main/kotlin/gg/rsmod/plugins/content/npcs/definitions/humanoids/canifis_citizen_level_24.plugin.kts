package gg.rsmod.plugins.content.npcs.definitions.humanoids

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val ids =
    intArrayOf(
        Npcs.EDUARD,
        Npcs.LEV,
        Npcs.YURI,
        Npcs.BORIS,
        Npcs.GEORGY,
        Npcs.JOSEPH,
        Npcs.NIKOLAI,
        Npcs.IMRE,
        Npcs.VERA,
        Npcs.MILLA,
        Npcs.SOFIYA,
        Npcs.IRINA,
        Npcs.SVETLANA,
        Npcs.ZOJA,
        Npcs.YADVIGA,
        Npcs.NIKITA,
        Npcs.LILIYA,
        Npcs.ALEXIS,
        Npcs.KSENIA,
        Npcs.GALINA,
    )

val table = DropTableFactory
val citizen =
    table.build {
        guaranteed {
            obj(Items.WOLF_BONES)
        }

        main {
            total(128)
            // FOOD
            obj(Items.RAW_CHICKEN, quantity = 5, slots = 16)
            obj(Items.RAW_BEEF, quantity = 5, slots = 16)
            obj(Items.RAW_BEAR_MEAT, quantity = 5, slots = 16)
            obj(Items.JUG_OF_WINE, quantity = 1, slots = 16)
            // WEAPONS AND ARMOUR
            obj(Items.STEEL_SCIMITAR, quantity = 1, slots = 16)
            obj(Items.STEEL_HATCHET, quantity = 1, slots = 16)
            obj(Items.STEEL_ARROW_P, quantity = 5, slots = 4)
            obj(Items.STEEL_ARROW, quantity = 50, slots = 4)
            obj(Items.STEEL_FULL_HELM, quantity = 1, slots = 4)
            obj(Items.MITHRIL_SCIMITAR, quantity = 1, slots = 4)
            obj(Items.MITHRIL_SQ_SHIELD, quantity = 1, slots = 4)
            obj(Items.RUNE_MED_HELM, quantity = 1, slots = 4)
            obj(Items.RUNE_JAVELIN, quantity = 1, slots = 1)
            // OTHER
            table(Herbs.minorHerbTable, slots = 3)
            table(Gems.gemTable, slots = 3)
            nothing(1)
        }
    }

table.register(citizen, *ids)

on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HUMAN_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 25
        }
        stats {
            hitpoints = 600
            attack = 10
            strength = 10
            defence = 10
            magic = 1
            ranged = 1
        }
        bonuses {

            defenceStab = -21
            defenceSlash = -21
            defenceCrush = -21
            defenceMagic = -21
            defenceRanged = -21
        }
        anims {
            attack = 422
            death = 836
            block = 424
        }
    }
}
