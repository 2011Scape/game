package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.interpolate

enum class Fish(
    val id: Int,
    val level: Int,
    private val minChance: Int,
    private val maxChance: Int,
    val xp: Double
) {
    SHRIMP(id = Items.RAW_SHRIMPS, level = 1, minChance = 48, maxChance = 256, xp = 10.0),
    SARDINE(id = Items.RAW_SARDINE, level = 5, minChance = 32, maxChance = 192, xp = 20.0),
    HERRING(id = Items.RAW_HERRING, level = 10, minChance = 24, maxChance = 128, xp = 30.0),
    ANCHOVIES(id = Items.RAW_ANCHOVIES, level = 15, minChance = 24, maxChance = 128, xp = 40.0),
    TROUT(id = Items.RAW_TROUT, level = 20, minChance = 32, maxChance = 192, xp = 50.0),
    PIKE(id = Items.RAW_PIKE, level = 25, minChance = 16, maxChance = 96, xp = 60.0),
    SALMON(id = Items.RAW_SALMON, level = 30, minChance = 16, maxChance = 96, xp = 70.0);

    fun roll(level: Int) = level.interpolate(minChance, maxChance, 1, 99, 255)
}
