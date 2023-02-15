package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.plugins.api.cfg.Items
enum class RockType(val level: Int, val xp: Double, val ore: Int, val respawnTime: Int, val varrockArmourAffected: Int,
                    val minChance: Int, val maxChance: Int) {
    CLAY(level= 5, xp = 5.0, ore = Items.CLAY, respawnTime = 2, varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        minChance=128, maxChance = 392),
    COPPER(level = 1, xp = 17.5, ore = Items.COPPER_ORE,  respawnTime = 4, varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        minChance=102, maxChance = 379),
    TIN(level = 1, xp = 17.5, ore = Items.TIN_ORE, respawnTime = 4, varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        minChance=102, maxChance = 379),
    IRON(level = 15, xp = 35.0, ore = Items.IRON_ORE,  respawnTime = 9, varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        minChance=109, maxChance = 346),
    SILVER(level = 20, xp = 40.0, ore = Items.SILVER_ORE,  respawnTime = 100, varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        minChance=24, maxChance = 200),
    COAL(level = 30, xp = 50.0, ore = Items.COAL,  respawnTime = 50, varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        minChance=15, maxChance = 100),
    GOLD(level = 40, xp = 65.0, ore = Items.GOLD_ORE,  respawnTime = 100, varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        minChance=6, maxChance = 75),
    MITHRIL(level = 55, xp = 80.0, ore = Items.MITHRIL_ORE,  respawnTime = 200, varrockArmourAffected = Items.VARROCK_ARMOUR_2,
        minChance=2, maxChance = 50),
    ADAMANTITE(level = 75, xp = 95.0, ore = Items.ADAMANTITE_ORE,  respawnTime = 400, varrockArmourAffected = Items.VARROCK_ARMOUR_3,
        minChance=-1, maxChance = 25),
    RUNITE(level = 85, xp = 125.0, ore = Items.RUNITE_ORE,  respawnTime = 1200, varrockArmourAffected = Items.VARROCK_ARMOUR_4,
        minChance=-1, maxChance = 18);
    companion object {
        val values = enumValues<RockType>()
    }
}