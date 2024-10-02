package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.interpolate

enum class Fish(
    val id: Int,
    val level: Int,
    private val minChance: Int,
    private val maxChance: Int,
    val xp: Double,
    val strengthLevel: Int? = null,
    val strengthXp: Double? = null,
    val agilityLevel: Int? = null,
    val agilityXp: Double? = null,
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
    LEAPING_TROUT(
        id = Items.LEAPING_TROUT,
        level = 48,
        minChance = 20,
        maxChance = 40,
        xp = 50.0,
        strengthLevel = 15,
        strengthXp = 5.0,
        agilityLevel = 15,
        agilityXp = 5.0,
    ),
    LEAPING_SALMON(
        id = Items.LEAPING_SALMON,
        level = 58,
        minChance = 30,
        maxChance = 75,
        xp = 70.0,
        strengthLevel = 30,
        strengthXp = 6.0,
        agilityLevel = 30,
        agilityXp = 6.0,
    ),
    LEAPING_STURGEON(
        id = Items.LEAPING_STURGEON,
        level = 70,
        minChance = 25,
        maxChance = 70,
        xp = 90.0,
        strengthLevel = 45,
        strengthXp = 7.0,
        agilityLevel = 45,
        agilityXp = 7.0,
    ),
    RAW_KARAMBWAN(id = Items.RAW_KARAMBWAN, level = 65, minChance = 5, maxChance = 160, xp = 50.0),
    RAINBOW_FISH(id = Items.RAW_RAINBOW_FISH, level = 38, minChance = 8, maxChance = 64, xp = 80.0),
    MONKFISH(id = Items.RAW_MONKFISH, level = 62, minChance = 48, maxChance = 90, xp = 120.0),
    KARAMBWANJI(id = Items.RAW_KARAMBWANJI, level = 5, minChance = 100, maxChance = 256, xp = 5.0),
    SLIMY_EEL(id = Items.SLIMY_EEL, level = 38, minChance = 10, maxChance = 80, xp = 65.0),
    CAVE_EEL(id = Items.RAW_CAVE_EEL, level = 28, minChance = 10, maxChance = 80, xp = 80.0),
    LAVA_EEL(id = Items.RAW_LAVA_EEL, level = 53, minChance = 16, maxChance = 96, xp = 60.0),
    FROG_SPAWN(id = Items.FROG_SPAWN, level = 33, minChance = 16, maxChance = 96, xp = 75.0),
    CAVEFISH(id = Items.RAW_CAVEFISH, level = 85, minChance = 5, maxChance = 17, xp = 300.0), // 6.7% success rate = ~134/hour.
    ROCKTAIL(id = Items.RAW_ROCKTAIL, level = 90, minChance = 5, maxChance = 15, xp = 380.0), // 6% success rate = 120/hour.
    ;

    fun roll(level: Int) = level.interpolate(minChance, maxChance, 1, 99, 255)
}
