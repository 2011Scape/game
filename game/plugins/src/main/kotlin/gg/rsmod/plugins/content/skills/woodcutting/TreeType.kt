package gg.rsmod.plugins.content.skills.woodcutting

/**
 * @author Tom <rspsmods@gmail.com>
 */
enum class TreeType(
    val level: Int,
    val xp: Double,
    val log: Int,
    val depleteChance: Int,
    val respawnTime: IntRange,
    val lowChance: Int,
    val highChance: Int
) {
    TREE(level = 1, xp = 25.0, log = 1511, depleteChance = 0, respawnTime = 15..25, lowChance = 64, highChance = 200),
    ACHEY(level = 1, xp = 25.0, log = 2862, depleteChance = 0, respawnTime = 15..25, lowChance = 64, highChance = 200),
    OAK(level = 15, xp = 37.5, log = 1521, depleteChance = 8, respawnTime = 15..25, lowChance = 32, highChance = 100),
    WILLOW(level = 30, xp = 67.5, log = 1519, depleteChance = 8, respawnTime = 22..68, lowChance = 16, highChance = 50),
    TEAK(level = 35, xp = 85.0, log = 6333, depleteChance = 8, respawnTime = 22..68, lowChance = 15, highChance = 46),
    MAPLE(level = 45, xp = 100.0, log = 1517, depleteChance = 8, respawnTime = 22..68, lowChance = 8, highChance = 25),
    HOLLOW(level = 45, xp = 82.0, log = 3239, depleteChance = 8, respawnTime = 22..68, lowChance = 18, highChance = 26),
    MAHOGANY(level = 50, xp = 125.0, log = 6332, depleteChance = 8, respawnTime = 22..68, lowChance = 8, highChance = 25),
    YEW(level = 60, xp = 175.0, log = 1515, depleteChance = 8, respawnTime = 22..68, lowChance = 4, highChance = 12),
    MAGIC(level = 75, xp = 250.0, log = 1513, depleteChance = 8, respawnTime = 22..68, lowChance = 6, highChance = 6),
}