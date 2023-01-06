package gg.rsmod.plugins.content.drops.shared

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.drops.DropTableFactory

object Herbs {

    val minorHerbTable = DropTableFactory.build {
        main {
            total(1024)
            obj(Items.GRIMY_GUAM, quantity = 1, slots = 249)
            obj(Items.GRIMY_MARRENTILL, quantity = 1, slots = 198)
            obj(Items.GRIMY_TARROMIN, quantity = 1, slots = 146)
            obj(Items.GRIMY_HARRALANDER, quantity = 1, slots = 114)
            obj(Items.GRIMY_RANARR, quantity = 1, slots = 85)
            obj(Items.GRIMY_IRIT, quantity = 1, slots = 64)
            obj(Items.GRIMY_AVANTOE, quantity = 1, slots = 49)
            obj(Items.GRIMY_KWUARM, quantity = 1, slots = 39)
            obj(Items.GRIMY_CADANTINE, quantity = 1, slots = 32)
            obj(Items.GRIMY_LANTADYME, quantity = 1, slots = 24)
            obj(Items.GRIMY_DWARF_WEED, quantity = 1, slots = 24)
        }
    }

    val usefulHerbTable = DropTableFactory.build {
        main {
            total(1024)
            obj(Items.GRIMY_AVANTOE, quantity = 1, slots = 341)
            obj(Items.GRIMY_SNAPDRAGON, quantity = 1, slots = 239)
            obj(Items.GRIMY_RANARR, quantity = 1, slots = 239)
            obj(Items.GRIMY_TORSTOL, quantity = 1, slots = 205)
        }
    }
}

