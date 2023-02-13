package gg.rsmod.plugins.content.skills.mining

import gg.rsmod.plugins.api.cfg.Items
enum class RockType(val level: Int, val xp: Double, val ore: Int, val nonDepleteChance: Double, val respawnTime: IntRange) {
    COPPER(level = 1, xp = 17.5, ore = Items.COPPER_ORE, nonDepleteChance = 0.0, respawnTime = 15..25),
    TIN(level = 1, xp = 17.5, ore = Items.TIN_ORE, nonDepleteChance = 0.0, respawnTime = 15..25),
    CLAY(level= 5, xp = 5.0, ore = Items.CLAY, nonDepleteChance = 0.0, respawnTime = 1..2 ),
    IRON(level = 15, xp = 35.0, ore = Items.IRON_ORE, nonDepleteChance = 0.0, respawnTime = 15..25),
    SILVER(level = 20, xp = 40.0, ore = Items.SILVER_ORE, nonDepleteChance = 50.0, respawnTime = 22..68),
    COAL(level = 30, xp = 50.0, ore = Items.COAL, nonDepleteChance = 40.0, respawnTime = 22..68),
    GOLD(level = 40, xp = 65.0, ore = Items.GOLD_ORE, nonDepleteChance = 33.333, respawnTime = 22..68),
    MITHRIL(level = 55, xp = 80.0, ore = Items.MITHRIL_ORE, nonDepleteChance = 25.0, respawnTime = 22..68),
    ADAMANTITE(level = 75, xp = 95.0, ore = Items.ADAMANTITE_ORE, nonDepleteChance = 16.66, respawnTime = 22..68),
    RUNITE(level = 85, xp = 125.0, ore = Items.RUNITE_ORE, nonDepleteChance = 12.5, respawnTime = 22..68);
    companion object {
        val values = enumValues<RockType>()
    }
}