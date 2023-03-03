package gg.rsmod.plugins.content.drops.global

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.drops.DropTableFactory

object Seeds {

    val generalSeedTable1 = DropTableFactory.build {
        main {
            total(90)
            obj(Items.POTATO_SEED, quantity = 4, slots = 32)
            obj(Items.ONION_SEED, quantity = 4, slots = 25)
            obj(Items.CABBAGE_SEED, quantity = 4, slots = 16)
            obj(Items.TOMATO_SEED, quantity = 3, slots = 9)
            obj(Items.SWEETCORN_SEED, quantity = 3, slots = 5)
            obj(Items.STRAWBERRY_SEED, quantity = 2, slots = 2)
            obj(Items.WATERMELON_SEED, quantity = 2, slots = 1)
        }
    }
}

