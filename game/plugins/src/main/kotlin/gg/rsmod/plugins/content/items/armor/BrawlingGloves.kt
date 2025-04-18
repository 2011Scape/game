package gg.rsmod.plugins.content.items.armor

import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items

enum class BrawlingGloves(val itemId: Int, val skill: Int, val maxCharges: Int, val wildernessBonus: Double, val nonWildernessBonus: Double) {
    AGILITY(Items.BRAWLING_GLOVES_AGILITY, Skills.AGILITY, 439, 5.0, 1.5),
    COOKING(Items.BRAWLING_GLOVES_COOKING, Skills.COOKING, 1202, 5.0, 1.5),
    FIREMAKING(Items.BRAWLING_GLOVES_FM, Skills.FIREMAKING, 1202, 4.0, 1.5),
    FISHING(Items.BRAWLING_GLOVES_FISHING, Skills.FISHING, 442, 5.0, 1.5),
    HUNTER(Items.BRAWLING_GLOVES_HUNTER, Skills.HUNTER, 336, 4.0, 1.5),
    MAGIC(Items.BRAWLING_GLOVES_MAGIC, Skills.MAGIC, 797, 4.0, 1.5),
    MELEE_STRENGTH(Items.BRAWLING_GLOVES_MELEE, Skills.STRENGTH, 3188, 4.0, 1.5),
    MELEE_ATTACK(Items.BRAWLING_GLOVES_MELEE, Skills.ATTACK, 3188, 4.0, 1.5),
    MELEE_DEFENCE(Items.BRAWLING_GLOVES_MELEE, Skills.DEFENCE, 3188, 4.0, 1.5),
    MINING(Items.BRAWLING_GLOVES_MINING, Skills.MINING, 700, 4.0, 1.5),
    PRAYER(Items.BRAWLING_GLOVES_PRAYER, Skills.PRAYER, 1274, 5.0, 1.5),
    RANGED(Items.BRAWLING_GLOVES_RANGED, Skills.RANGED, 797, 4.0, 1.5),
    SMITHING(Items.BRAWLING_GLOVES_SMITHING, Skills.SMITHING, 512, 5.0, 1.5),
    THIEVING(Items.BRAWLING_GLOVES_THIEVING, Skills.THIEVING, 555, 4.0, 1.5),
    WOODCUTTING(Items.BRAWLING_GLOVES_WC, Skills.WOODCUTTING, 832, 4.0, 1.5)
}
