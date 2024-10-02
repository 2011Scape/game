package gg.rsmod.plugins.content.drops.global

import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.content.drops.DropTableFactory
import gg.rsmod.plugins.content.drops.global.MegaRare.megaRareTable

/**
 * @author Alycia <https://github.com/alycii>
 */
object Gems {
    val gemTable =
        DropTableFactory.build {
            main {

                // TODO: check all 4 rings
                val hasRingOfWealth = player.equipment.hasAt(EquipmentType.RING.id, Items.RING_OF_WEALTH)

                total(1024)
                if (!hasRingOfWealth) nothing(512)
                obj(Items.UNCUT_SAPPHIRE, quantity = 1, slots = if (hasRingOfWealth) 492 else 240)
                obj(Items.UNCUT_EMERALD, quantity = 1, slots = if (hasRingOfWealth) 228 else 112)
                obj(Items.UNCUT_RUBY, quantity = 1, slots = if (hasRingOfWealth) 114 else 64)
                obj(Items.CHAOS_TALISMAN, quantity = 1, slots = if (hasRingOfWealth) 47 else 24)
                obj(Items.NATURE_TALISMAN, quantity = 1, slots = if (hasRingOfWealth) 47 else 24)
                obj(Items.UNCUT_DIAMOND, quantity = 1, slots = if (hasRingOfWealth) 32 else 16)
                obj(Items.RUNE_JAVELIN, quantity = 5, slots = if (hasRingOfWealth) 16 else 8)
                obj(Items.LOOP_HALF_OF_A_KEY, quantity = 1, slots = if (hasRingOfWealth) 16 else 8)
                obj(Items.TOOTH_HALF_OF_A_KEY, quantity = 1, slots = if (hasRingOfWealth) 16 else 8)
                table(megaRareTable, slots = if (hasRingOfWealth) 16 else 8)
            }
        }
}
