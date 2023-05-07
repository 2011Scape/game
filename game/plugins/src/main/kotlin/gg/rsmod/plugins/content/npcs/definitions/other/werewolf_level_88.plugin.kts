package gg.rsmod.plugins.content.npcs.definitions.other

import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.Gems
import gg.rsmod.plugins.content.drops.global.Herbs

val ids = intArrayOf(Npcs.WEREWOLF_6006,
    Npcs.WEREWOLF_6007,
    Npcs.WEREWOLF_6008,
    Npcs.WEREWOLF_6009,
    Npcs.WEREWOLF_6010,
    Npcs.WEREWOLF_6011,
    Npcs.WEREWOLF_6012,
    Npcs.WEREWOLF_6013,
    Npcs.WEREWOLF_6014,
    Npcs.WEREWOLF_6015,
    Npcs.WEREWOLF_6016,
    Npcs.WEREWOLF_6017,
    Npcs.WEREWOLF_6018,
    Npcs.WEREWOLF_6019,
    Npcs.WEREWOLF_6020,
    Npcs.WEREWOLF_6021,
    Npcs.WEREWOLF_6022,
    Npcs.WEREWOLF_6023,
    Npcs.WEREWOLF_6024,
    Npcs.WEREWOLF_6025
)
val table = DropTableFactory
val werewolf = table.build {

    guaranteed {
        obj(Items.WOLF_BONES)
    }
    main {
        total(128)
        //FOOD
        obj(Items.RAW_CHICKEN, quantity = 5, slots = 16)
        obj(Items.RAW_BEEF, quantity = 5, slots = 16)
        obj(Items.RAW_BEAR_MEAT, quantity = 5, slots = 16)
        obj(Items.JUG_OF_WINE, quantity = 1, slots = 16)
        //WEAPONS AND ARMOUR
        obj(Items.STEEL_SCIMITAR, quantity = 1, slots = 16)
        obj(Items.STEEL_HATCHET, quantity = 1, slots = 16)
        obj(Items.STEEL_ARROW_P, quantity = 5, slots = 4)
        obj(Items.STEEL_ARROW, quantity = 50, slots = 4)
        obj(Items.STEEL_FULL_HELM, quantity = 1, slots = 4)
        obj(Items.MITHRIL_SCIMITAR, quantity = 1, slots = 4)
        obj(Items.MITHRIL_SQ_SHIELD, quantity = 1, slots = 4)
        obj(Items.RUNE_MED_HELM, quantity = 1, slots = 4)
        obj(Items.RUNE_JAVELIN, quantity = 1, slots = 1)
        //OTHER
        table(Herbs.minorHerbTable, slots = 3)
        table(Gems.gemTable, slots = 3)
        nothing(2)
    }
}

table.register(werewolf, *ids)


on_npc_pre_death(*ids) {
    val p = npc.damageMap.getMostDamage()!! as Player
    p.playSound(Sfx.HALF_WEREWOLF_DEATH)
}

on_npc_death(*ids) {
    table.getDrop(world, npc.damageMap.getMostDamage()!! as Player, npc.id, npc.tile)
}

ids.forEach {
    set_combat_def(it) {
        configs {
            attackSpeed = 4
            respawnDelay = 30
        }
        stats {
            hitpoints = 1000
            attack = 70
            strength = 70
            defence = 70
            magic = 1
            ranged = 1
        }
        bonuses {
            defenceMagic = 60
        }
        anims {
            attack = 6536
            death = 6537
            block = 6538
        }
        aggro {
            radius = 4
        }
    }
}