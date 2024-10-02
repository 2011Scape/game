package gg.rsmod.plugins.content.drops.global

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.drops.DropTableFactory

/**
 * @author Alycia <https://github.com/alycii>
 */
object Rare {
    val rareTable =
        DropTableFactory.build {
            main {

                total(1024)
                // runes and ammunition
                obj(Items.NATURE_RUNE, quantity = 67, 24)
                obj(Items.ADAMANT_JAVELIN, quantity = 20, 16)
                obj(Items.DEATH_RUNE, quantity = 45, 16)
                obj(Items.LAW_RUNE, quantity = 45, 16)
                obj(Items.RUNE_ARROW, quantity = 42, 16)
                obj(Items.STEEL_ARROW, quantity = 150, 16)
                // weapons and armour
                obj(Items.RUNE_2H_SWORD, quantity = 1, 24)
                obj(Items.RUNE_BATTLEAXE, quantity = 1, 24)
                obj(Items.RUNE_SQ_SHIELD, quantity = 1, 16)
                obj(Items.DRAGON_MED_HELM, quantity = 1, 8)
                obj(Items.RUNE_KITESHIELD, quantity = 1, 8)
                // other
                obj(Items.COINS_995, quantity = 3000, 168)
                obj(Items.LOOP_HALF_OF_A_KEY, quantity = 1, 160)
                obj(Items.TOOTH_HALF_OF_A_KEY, quantity = 1, 160)
                obj(Items.RUNE_BAR, quantity = 1, 40)
                obj(Items.DRAGONSTONE, quantity = 1, 16)
                obj(Items.SILVER_ORE_NOTED, quantity = 100, 16)
                // sub-tables
                table(Gems.gemTable, slots = 160)
                table(MegaRare.megaRareTable, slots = 120)
            }
        }
}
