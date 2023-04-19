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
    CRAYFISH(id = Items.RAW_CRAYFISH, level = 1, minChance = 58, maxChance = 256, xp = 10.0),
    SHRIMP(id = Items.RAW_SHRIMPS, level = 1, minChance = 48, maxChance = 256, xp = 10.0),
    SARDINE(id = Items.RAW_SARDINE, level = 5, minChance = 32, maxChance = 192, xp = 20.0),
    HERRING(id = Items.RAW_HERRING, level = 10, minChance = 24, maxChance = 128, xp = 30.0),
    ANCHOVIES(id = Items.RAW_ANCHOVIES, level = 15, minChance = 24, maxChance = 128, xp = 40.0),
    MACKEREL(id = Items.RAW_MACKEREL, level = 16, minChance = 5, maxChance = 65, xp = 20.0),
    COD(id = Items.RAW_COD, level = 23, minChance = 4, maxChance = 55, xp = 45.0),
    BASS(id = Items.RAW_BASS, level = 46, minChance = 3, maxChance = 40, xp = 100.0),
    TROUT(id = Items.RAW_TROUT, level = 20, minChance = 32, maxChance = 192, xp = 50.0),
    PIKE(id = Items.RAW_PIKE, level = 25, minChance = 16, maxChance = 96, xp = 60.0),
    SALMON(id = Items.RAW_SALMON, level = 30, minChance = 16, maxChance = 96, xp = 70.0),
    TUNA(id = Items.RAW_TUNA, level = 35, minChance = 4, maxChance = 48, xp = 80.0),
    SWORDFISH(id = Items.RAW_SWORDFISH, level = 50, minChance = 4, maxChance = 48, xp = 100.0),
    LOBSTER(id = Items.RAW_LOBSTER, level = 40, minChance = 6, maxChance = 95, xp = 90.0),
    SHARK(id = Items.RAW_SHARK, level = 76, minChance = 3, maxChance = 40, xp = 110.0),
    CAVEFISH(id = Items.RAW_CAVEFISH, level = 85, minChance = 5, maxChance = 17, xp = 300.0), //6.7% success rate = ~134/hour.
    ROCKTAIL(id = Items.RAW_ROCKTAIL, level = 90, minChance = 5, maxChance = 15, xp = 380.0); //6% success rate = 120/hour.


    fun roll(level: Int) = level.interpolate(minChance, maxChance, 1, 99, 255)
}
