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
    SHRIMP(Items.RAW_SHRIMPS, 1, 48, 256, 10.0),
    SARDINE(Items.RAW_SARDINE, 5, 32, 192, 20.0),
    HERRING(Items.RAW_HERRING, 10, 24, 128, 30.0),
    ANCHOVIES(Items.RAW_ANCHOVIES, 15, 24, 128, 40.0);

    fun roll(level: Int) = level.interpolate(minChance, maxChance, 1, 99, 255)
}
