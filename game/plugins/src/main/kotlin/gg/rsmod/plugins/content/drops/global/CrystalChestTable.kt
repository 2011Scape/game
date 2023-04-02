package gg.rsmod.plugins.content.drops.global

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.drops.DropTableFactory

object CrystalChestTable {

    val table = DropTableFactory
    val spinash_roll = table.build {
        guaranteed {
            obj(Items.SPINACH_ROLL)
            obj(Items.COINS_995, quantity = 2000)
        }
    }
    val runes = table.build {
        guaranteed {
            obj(Items.AIR_RUNE, quantity = 50)
            obj(Items.WATER_RUNE, quantity = 50)
            obj(Items.EARTH_RUNE, quantity = 50)
            obj(Items.FIRE_RUNE, quantity = 50)
            obj(Items.BODY_RUNE, quantity = 50)
            obj(Items.MIND_RUNE, quantity = 50)
            obj(Items.CHAOS_RUNE, quantity = 10)
            obj(Items.DEATH_RUNE, quantity = 10)
            obj(Items.COSMIC_RUNE, quantity = 10)
            obj(Items.NATURE_RUNE, quantity = 10)
            obj(Items.LAW_RUNE, quantity = 10)
        }
    }
    val gems = table.build {
        guaranteed {
            obj(Items.RUBY, quantity = 2)
            obj(Items.DIAMOND, quantity = 2)
        }
    }
    val bars = table.build {
        guaranteed {
            obj(Items.RUNE_BAR, quantity = 3) }
    }
    val key_pieces = table.build {
        guaranteed {
            obj(Items.LOOP_HALF_OF_A_KEY)
            obj(Items.TOOTH_HALF_OF_A_KEY)
            obj(Items.COINS_995, quantity = 750)
        }
    }
    val iron_ore = table.build {
        guaranteed {
            obj(Items.IRON_ORE_NOTED, quantity = 150)
        }
    }
    val coal = table.build {
        guaranteed {
            obj(Items.COAL_NOTED, quantity = 100)
        }
    }
    val swordfish = table.build {
        guaranteed {
            obj(Items.RAW_SWORDFISH, quantity = 5)
            obj(Items.COINS_995, quantity = 1000)
        }
    }
    val adamant_shield = table.build {
        guaranteed {
            obj(Items.ADAMANT_SQ_SHIELD)
        }
    }
    val rune_platelegs = table.build {
        guaranteed {
            obj(Items.RUNE_PLATELEGS)
        }
    }
    val rune_plateskirt = table.build {
        guaranteed {
            obj(Items.RUNE_PLATESKIRT)
        }
    }
}